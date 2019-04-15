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

package com.google.codeu.data;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import org.codehaus.jackson.annotate.JsonTypeInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/** Provides access to the data stored in Datastore. */

public class Datastore {

  private DatastoreService datastore;

  public Datastore() {
    datastore = DatastoreServiceFactory.getDatastoreService();
  }

  public void storeUser(User user) {
    Entity userEntity = new Entity("User", user.getEmail());
    userEntity.setProperty("email", user.getEmail());
    userEntity.setProperty("aboutMe", user.getAboutMe());
    datastore.put(userEntity);
  }

  /**
   * Returns the User owned by the email address, or
   * null if no matching User was found.
   */
  public User getUser(String email) {
    Query query = new Query("User")
        .setFilter(new Query.FilterPredicate("email", FilterOperator.EQUAL, email));
    PreparedQuery results = datastore.prepare(query);
    Entity userEntity = results.asSingleEntity();
    if (userEntity == null) {
      return null;
    }

    String aboutMe = (String) userEntity.getProperty("aboutMe");
    User user = new User(email, aboutMe);

    return user;
  }

  //#region Messages

  /** Stores the Message in Datastore. */
  public void storeMessage(Message message) {
    Entity messageEntity = new Entity("Message", message.getId().toString());
    messageEntity.setProperty("user", message.getUser());
    messageEntity.setProperty("text", message.getText());
    messageEntity.setProperty("timestamp", message.getTimestamp());
    messageEntity.setProperty("recipient", message.getRecipient());
    if (message.getImageUrl() != null) {
      messageEntity.setProperty("imageUrl", message.getImageUrl());
    }

    datastore.put(messageEntity);
  }

  /**
   * Gets messages sent to {@code recipient}.
   *
   * @return a list of messages sent to the recipient, or empty list if user has never posted a
   *     message. List is sorted by time descending.
   */
  public List<Message> getMessages(String recipient) {
    List<Message> messages = new ArrayList<>();

    Query query =
        new Query("Message")
            .setFilter(new Query.FilterPredicate("recipient", FilterOperator.EQUAL, recipient))
            .addSort("timestamp", SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        String idString = entity.getKey().getName();
        UUID id = UUID.fromString(idString);
        String user = (String) entity.getProperty("user");
        String text = (String) entity.getProperty("text");
        long timestamp = (long) entity.getProperty("timestamp");
        String imageUrl = (String) entity.getProperty("imageUrl");
        Message message = new Message(id, user, text, timestamp, recipient, imageUrl);
        messages.add(message);
      } catch (Exception e) {
        System.err.println("Error reading message.");
        System.err.println(entity.toString());
        e.printStackTrace();
      }
    }
    return messages;
  }


  /**
   * Gets all messages posted.
   *
   * @return a list of messages posted, or empty list if user has never posted a
   *     message. List is sorted by user and time descending.
   */
  public List<Message> getAllMessages() {
    List<Message> messages = new ArrayList<>();
    Query query = new Query("Message")
        .addSort("user", SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      String user = (String) entity.getProperty("user");
      messages.addAll(getMessages(user));
    }
    return messages;
  }
  /** 
   *Stores the User in Datastore.
   */

  /** Returns the total number of messages for all users. */
  public int getTotalMessageCount() {
    Query query = new Query("Message");
    PreparedQuery results = datastore.prepare(query);
    return results.countEntities(FetchOptions.Builder.withLimit(1000));
  }

  //endregion


  //#region Schedule

  /** Stores the ItemSchedule in Datastore. */
  public void storeItemSchedule(ItemSchedule itemSchedule) {

    String kind = "";
    if(itemSchedule instanceof Course) { kind = "Course"; }
    else if (itemSchedule instanceof Task) { kind = "Task"; }
    else { kind = "Event"; }

    //Save ItemSchedule Properties
    Entity itemScheduleEntity = new Entity(kind, itemSchedule.getId().toString());
    itemScheduleEntity.setProperty("creator",itemSchedule.getCreator());
    itemScheduleEntity.setProperty("startTime",itemSchedule.getStartTime());
    itemScheduleEntity.setProperty("endTime",itemSchedule.getEndTime());
    itemScheduleEntity.setProperty("description",itemSchedule.getDescription());

    if(itemSchedule.location != null) {
      itemScheduleEntity.setProperty("location_title", itemSchedule.getLocation().getTitle());
      itemScheduleEntity.setProperty("location_description", itemSchedule.getLocation().getDescription());
      itemScheduleEntity.setProperty("location_lat", itemSchedule.getLocation().getLat());
      itemScheduleEntity.setProperty("location_lng", itemSchedule.getLocation().getLng());
    } else {
      itemScheduleEntity.setProperty("location_title", "");
      itemScheduleEntity.setProperty("location_description", "");
      itemScheduleEntity.setProperty("location_lat", 0.0);
      itemScheduleEntity.setProperty("location_lng", 0.0);
    }

    if(kind.equals("Course")) {
      Course temp = (Course) itemSchedule;
      String daysOfWeek = "";
      int size_DaysOfWeek = temp.getDaysOfWeek().size();
      for (Days d : temp.getDaysOfWeek()) {
        daysOfWeek = daysOfWeek + d.getValue();
        if(size_DaysOfWeek > 0) { daysOfWeek = daysOfWeek + " "; }
        size_DaysOfWeek--;
      }
      itemScheduleEntity.setProperty("daysOfWeek", daysOfWeek);
      itemScheduleEntity.setProperty("course_assignments_size",temp.getAssignments().size());
      int i = 0;
      for (Assignment a : temp.getAssignments()) {
        itemScheduleEntity.setProperty("course_assignment_"+i+"_dueDate",a.getDueDate());
        itemScheduleEntity.setProperty("course_assignment_"+i+"_course",a.getCourse());
        itemScheduleEntity.setProperty("course_assignment_"+i+"_completed",a.isCompleted());
        i++;
      }
      itemScheduleEntity.setProperty("grade", temp.getGrade());
    } else {
      Event temp = (Event) itemSchedule;
      itemScheduleEntity.setProperty("priorityLevel", temp.getPriorityLevel());
      itemScheduleEntity.setProperty("collaborators_size", temp.getCollaborators().size());
      int i = 0;
      for (User u : temp.getCollaborators()) {
        itemScheduleEntity.setProperty(("collaborators_"+i), u.getEmail());
        i++;
      }
      if(kind.equals("Task")) {
        itemScheduleEntity.setProperty(("completed"), ((Task)itemSchedule).isCompleted());
      }
    }
    datastore.put(itemScheduleEntity);
  }

  /**
   * Gets messages sent to {@code recipient}.
   *
   * @return a list of itemSchedule sent to the recipient.
   */
  public List<ItemSchedule> getItemSchedule(String user) {
    List<ItemSchedule> items = new ArrayList<>();
    String [] kinds = {"Course","Event","Task"};
    for (int i = 0; i < kinds.length ; i++) {
      Query query =
          new Query(kinds[i]);
              //.setFilter(new Query.FilterPredicate("creator", FilterOperator.EQUAL, user))
              //.addSort("startTime", SortDirection.DESCENDING);
      PreparedQuery results = datastore.prepare(query);
      for (Entity entity : results.asIterable()) {
        try {
          //ItemSchedule
          String idString = entity.getKey().getName();
          UUID id = UUID.fromString(idString);
          String creator = (String) entity.getProperty("creator");
          long startTime = (long) entity.getProperty("startTime");
          long endTime = (long) entity.getProperty("endTime");
          String description = (String) entity.getProperty("description");

          //Location
          String location_title = (String) entity.getProperty("location_title");
          String location_description = (String) entity.getProperty("location_description");
          float location_lat = Float.parseFloat(entity.getProperty("location_lat").toString());
          float location_lng = Float.parseFloat(entity.getProperty("location_lng").toString());
          Location tempLocation = new Location(location_title,location_description,location_lat,location_lng);

          //Course
          if(i == 0) {
            //Read Days of week
            ArrayList<Days> daysOfWeek = new ArrayList<>();
            String strDaysOfWeek = (String) entity.getProperty("daysOfWeek");
            for (String s : strDaysOfWeek.split(" ")) {
              daysOfWeek.add(Days.getValueEnum(s));
            }
            //Read Assignments
            /*
            ArrayList<Assignment> assignments = new ArrayList<>();
            int assignmentsSize = (int) entity.getProperty("course_assignments_size");
            System.out.println(assignmentsSize);
            for (int j = 0 ; j < assignmentsSize; j++) {
              System.out.println(j);
              String dueDate = (String) entity.getProperty("course_assignment_"+j+"_dueDate");
              String course = (String) entity.getProperty("course_assignment_"+j+"_course");
              boolean completed = (boolean) entity.getProperty("course_assignment_"+j+"completed");
              assignments.add(new Assignment(course,dueDate,completed));
            }
            */
            String grade = (String) entity.getProperty("grade");

            Course tempCourse = new Course(creator,id,startTime,endTime);
            tempCourse.setDaysOfWeek(daysOfWeek);
            //tempCourse.setAssignments(assignments);
            tempCourse.setLocation(tempLocation);
            items.add(tempCourse);
          } else {
            //Event
            int priorityLevel = (int) entity.getProperty("priorityLevel");
            int collaborators_size = (int) entity.getProperty("collaborators_size");
            ArrayList<User> collaborators = new ArrayList<>();
            for (int j = 0 ; j < collaborators_size; j++) {
              String userEmail= (String) entity.getProperty("collaborators_"+j);
              collaborators.add(new User(userEmail));
            }
            //Task
            if(i == 3) {
              boolean completed = (boolean) entity.getProperty("completed");
              Task tempTask = new Task(creator,id,startTime,endTime,priorityLevel);
              tempTask.setCollaborators(collaborators);
              tempTask.setCompleted(completed);
              tempTask.setLocation(tempLocation);
              items.add(tempTask);
            } else {
              //Event
              Event tempEvent = new Event(creator,id,startTime,endTime,priorityLevel);
              tempEvent.setCollaborators(collaborators);
              tempEvent.setLocation(tempLocation);
              items.add(tempEvent);
            }
          }
        } catch (Exception e) {
          System.err.println("Error reading item.");
          System.err.println(entity.toString());
          e.printStackTrace();
        }
      }
    }
    return items;
  }

  public List<ItemSchedule> getAllItemSchedule() {
    ArrayList<ItemSchedule> items = new ArrayList<>();

    Query query = new Query("Task");
        //.addSort("user", SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);
    items.addAll(getAllItemSchedule(results));

    query = new Query("Course");
        //.addSort("user", SortDirection.DESCENDING);
    results = datastore.prepare(query);
    items.addAll(getAllItemSchedule(results));

    query = new Query("Event");
        //.addSort("user", SortDirection.DESCENDING);
    results = datastore.prepare(query);
    items.addAll(getAllItemSchedule(results));

    return items;
  }

  private List<ItemSchedule> getAllItemSchedule(PreparedQuery results) {
    ArrayList<ItemSchedule> result = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
      String user = (String) entity.getProperty("user");
      result.addAll(getItemSchedule(user));
    }
    return result;
  }

  /** Returns the total number of messages for all users. */
  public int getTotalItemScheduleCount() {
    int total = 0;
    Query query = new Query("Task");
    PreparedQuery results = datastore.prepare(query);
    total += results.countEntities(FetchOptions.Builder.withLimit(1000));

    query = new Query("Course");
    results = datastore.prepare(query);
    total += results.countEntities(FetchOptions.Builder.withLimit(1000));

    query = new Query("Event");
    results = datastore.prepare(query);
    total += results.countEntities(FetchOptions.Builder.withLimit(1000));

    return total;
  }

  //endregion
  
}
