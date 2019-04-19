package com.google.codeu.servlets;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeServlet;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CalendarOAuthServlet extends AbstractAppEngineAuthorizationCodeServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws IOException {

    // Initializes Flow
    GoogleAuthorizationCodeFlow flow = Utils.newFlow();

    // Creates authorization url for prompting OAuth consent screen
    AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();

    // Set redirect uri to callback
    authorizationUrl.setRedirectUri(getRedirectUri(request));

    // Redirect to authorization url
    response.sendRedirect(authorizationUrl.build());
  }

  @Override
  protected String getRedirectUri(HttpServletRequest req) {
    return Utils.getRedirectUri(req);
  }

  @Override
  protected AuthorizationCodeFlow initializeFlow() throws IOException {
    return Utils.newFlow();
  }
}
