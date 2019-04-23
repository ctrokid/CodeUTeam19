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

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.codeu.data.*;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Handles fetching and saving {@link Message} instances. */
@WebServlet("/schedule")
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

    List<Event> events = datastore.getEvents(user);

    Gson gson = new Gson();
    String json = gson.toJson(events);
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
    String text = request.getParameter("text");
    final String recipient = request.getParameter("recipient");

    Message message = new Message(user, text, recipient);
    //datastore.storeMessage(message);

    testPostTask(text);

    response.sendRedirect("/user-page.html?user=" + recipient);
  }

  //Read From InputText field to create a ScheduleItem
  private void testPostTask(String line) {
    String[] split = line.split(" ");
    String startTime = split[1];
    String endTime = split[2];
    String description = split[3];

    String loc_Title = split[4];
    String loc_Description = split[5];
    float loc_Lat = Float.parseFloat(split[6]);
    float loc_Lng = Float.parseFloat(split[7]);
    Location loc = new Location(loc_Title,loc_Description,loc_Lat,loc_Lng);

    String creator = split[8];

    //Course
    if(split[0].compareTo("Course") == 0) {
      ArrayList<Days> daysOfWeek = new ArrayList<>();
      for (String s : split[9].split("_")) {
        daysOfWeek.add(Days.getValueEnum(s));
      }
      String grade = split[10];
      ArrayList<Assignment> assignments = new ArrayList<>();

      Course course = new Course(creator,startTime,endTime);
      course.setLocation(loc);
      course.setDescription(description);
      course.setDaysOfWeek(daysOfWeek);
      course.setGrade(grade);
      course.setAssignments(assignments);

      datastore.storeItemSchedule(course);
    }
    /*
    //Event || Task
    else {
      int priorityLevel = Integer.parseInt(split[9]);
      ArrayList<User> collaborators = new ArrayList<>();
      collaborators.add(new User("test1@test.com"));
      collaborators.add(new User("test2@test.com"));
      if(split[0].compareTo("Task") == 0) {
        Event event = new Event(creator,endTime,startTime,priorityLevel);
        event.setLocation(loc);
        event.setDescription(description);
        event.setCollaborators(collaborators);

        datastore.storeItemSchedule(event);
      }
      else {
        Task task = new Task(creator,endTime,startTime,priorityLevel);
        task.setLocation(loc);
        task.setDescription(description);
        task.setCollaborators(collaborators);
        boolean completed = Boolean.parseBoolean(split[10]);
        task.setCompleted(completed);

        datastore.storeItemSchedule(task);
      }

     */
   // }
  }

}
