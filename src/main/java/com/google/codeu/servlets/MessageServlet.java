/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.codeu.servlets;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.codeu.data.Datastore;
import com.google.codeu.data.Message;
import com.google.codeu.data.Pair;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/** Handles fetching and saving {@link Message} instances. */
@WebServlet("/messages")
public class MessageServlet extends HttpServlet {

  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();
  }

  /**
   * Responds with a JSON representation of {@link Message} data for a specific user. Responds with
   * an empty array if the user is not provided.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    response.setContentType("application/json");

    String user = request.getParameter("user");

    if (user == null || user.equals("")) {
      // Request is invalid, return empty array
      response.getWriter().println("[]");
      return;
    }

    List<Message> messages = datastore.getMessages(user);

    String targetLanguageCode = request.getParameter("language");

    if (targetLanguageCode != null) {
      translateMessages(messages, targetLanguageCode);
    }

    Gson gson = new Gson();
    String json = gson.toJson(messages);
    response.getWriter().println(json);
  }

  /** Stores a new {@link Message}. */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    UserService userService = UserServiceFactory.getUserService();
    if (!userService.isUserLoggedIn()) {
      response.sendRedirect("/index.html");
      return;
    }    
    final String user = userService.getCurrentUser().getEmail();
    //Cleans the user input
    String text = Jsoup.clean(request.getParameter("text"), Whitelist.basicWithImages());
    final String recipient = request.getParameter("recipient");


    Pair<Integer> captionRange = captionValidator(text);
    Pair<Integer> urlRange = urlValidator(text);
    if (captionRange == null) {
      captionRange = new Pair<>(0,0);
    }
    if (urlRange == null) {
      urlRange = new Pair<>(0,0);
    }
    text = messageFormat(text,captionRange,urlRange);

    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
    List<BlobKey> blobKeys = blobs.get("image");

    Message message = new Message(user, text, recipient);

    if (blobKeys != null && !blobKeys.isEmpty()) {
      BlobKey blobKey = blobKeys.get(0);
      ImagesService imagesService = ImagesServiceFactory.getImagesService();
      ServingUrlOptions options = ServingUrlOptions.Builder.withBlobKey(blobKey);
      String imageUrl = imagesService.getServingUrl(options);
      message.setImageUrl(imageUrl);
    }

    System.out.print("URL: ");
    System.out.println(message.getImageUrl() + "\n");

    datastore.storeMessage(message);

    response.sendRedirect("/user-page.html?user=" + recipient);
  }

  private void translateMessages(List<Message> messages, String targetLanguageCode) {
    Translate translate = TranslateOptions.getDefaultInstance().getService();

    for (Message message : messages) {
      String originalText = message.getText();

      Translation translation = translate.translate(
          originalText, TranslateOption.targetLanguage(targetLanguageCode));
      String translatedText = translation.getTranslatedText();

      message.setText(translatedText);
    }
  }

  /**
   *
   * @param message receive the raw string message entered by the user.
   * @return null if url is invalid, otherwise return start and end point of url.
   */
  private Pair<Integer> urlValidator(String message) {
    int i = -1;
    boolean containS = true;
    i = message.indexOf("https://");
    if (i == -1) {
      i = message.indexOf("http://");
      containS = false;
    }
    if (i == -1) {
      return null;
    }
    int start = containS ? 8 : 7;
    start += i;

    String [] ext = {".jpg",".png",".gif",".bpm"};
    int end = -1;
    String urlExt = "";
    for (String s : ext) {
      end = message.indexOf(s,i);
      if (end != -1) {
        urlExt = s;
        break;
      }
    }
    char prior = message.charAt(start);
    if (prior == '.' || prior == '/') {
      return null;
    }

    for (int j = start + 1; j < end; j++) {
      char actual = message.charAt(j);
      if (actual == '.' || actual == '/') {
        if (prior == '.' || prior == '/') {
          return null;
        }
      }
      prior = actual;
    }
    return new Pair<>(i,end + urlExt.length());
  }

  /**
   *
   * @param message receive the raw string message entered by the user.
   * @return null if caption is invalid, otherwise return start and end point of caption.
   */
  private Pair<Integer> captionValidator(String message) {
    int i = -1;
    i = message.indexOf("![");
    if (i == -1) {
      return null;
    }
    int end = message.indexOf("]", i);
    if (end == -1) {
      return null;
    }
    return new Pair<>(i + 2,end);
  }

  /**
   *
   * @param msg receive the message containing the raw text.
   * @param capInterv receive the interval containing the caption fo the image.
   * @param urlInterv receive the interval containing the ulr of the image.
   * @return message formated with the HTML code to show caption and image.
   */
  private String messageFormat(String msg, Pair<Integer> capInterv, Pair<Integer> urlInterv) {
    String result = "";
    int endOfText = Math.min(capInterv.getKey(),urlInterv.getKey());
    if (endOfText == 0) {
      endOfText = Math.max(capInterv.getKey(),urlInterv.getKey());
    }
    if (endOfText == 0) {
      return msg;
    }
    if (capInterv.getValue() - capInterv.getKey() != 0) {
      result += msg.substring(0, endOfText - 2);
    } else {
      result += msg.substring(0, endOfText);
    }
    result += "<figure> ";
    String caption = "";
    if (capInterv.getValue() - capInterv.getKey() != 0) {
      caption = "<figcaption> ";
      caption = caption + msg.substring(capInterv.getKey(), capInterv.getValue());
      caption = caption + " </figcaption> ";
    }
    String image = "";
    if (urlInterv.getValue() - urlInterv.getKey() != 0) {
      image = "<img src=\"";
      image = image + msg.substring(urlInterv.getKey(), urlInterv.getValue());
      image = image + "\"/>";
    }
    result += image + caption + "</figure>";
    return result;
  }
}
