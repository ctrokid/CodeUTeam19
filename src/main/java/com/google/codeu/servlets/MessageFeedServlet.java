package com.google.codeu.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.codeu.data.*;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles fetching all messages for the public feed.
 */
public class MessageFeedServlet extends HttpServlet {

  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();
  }

  /**
   * Responds with a JSON representation of Message data for all users.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
          throws IOException {

    response.setContentType("application/json");


    List<Event> events = datastore.getAllMessages();

    Gson gson = new Gson();
    String json = gson.toJson(events);
    response.getWriter().println(json);
  }
}
