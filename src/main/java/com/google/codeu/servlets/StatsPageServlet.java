package com.google.codeu.servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import com.google.codeu.data.Datastore;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.JsonObject;

/**
 * Handles fetching site statistics.
 */
@WebServlet("/stats")
public class StatsPageServlet extends HttpServlet{

  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();
  }

  /**
   * Responds with site statistics in JSON.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    response.setContentType("application/json");

    int messageCount = datastore.getTotalMessageCount();

    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("messageCount", messageCount);
    response.getOutputStream().println(jsonObject.toString());
  }
}