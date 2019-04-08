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
import com.google.codeu.data.ItemSchedule;
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
@WebServlet("/schedule")
public class ItemScheduleServlet extends HttpServlet {


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

    List<ItemSchedule> itemSchedule = datastore.getItemSchedule(user);
    Gson gson = new Gson();
    String json = gson.toJson(itemSchedule);
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
    /*
    String text = Jsoup.clean(request.getParameter("text"), Whitelist.none());
    final String recipient = request.getParameter("recipient");

    Message message = new Message(user, text, recipient);
    datastore.storeMessage(message);

    response.sendRedirect("/user-page.html?user=" + recipient);
    */
  }
}

