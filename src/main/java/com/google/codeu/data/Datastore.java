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

  /** Stores the Event in Datastore. */
  public void storeEvent(MvpConcreteEvent event) {
    Entity messageEntity = new Entity("Event", event.getId().toString());
    messageEntity.setProperty("description", event.getDescription());
    messageEntity.setProperty("location", event.getLocation());
    messageEntity.setProperty("startTime", event.getStartTime());
    messageEntity.setProperty("endTime", event.getEndTime());
    datastore.put(messageEntity);
  }

  /**
   * Gets messages sent to {@code recipient}.
   *
   * @return a list of messages sent to the recipient, or empty list if user has never posted a
   *     message. List is sorted by time descending.
   */
  public List<MvpConcreteEvent> getEvents(String recipient) {
    List<MvpConcreteEvent> schedule = new ArrayList<>();

    Query query =
        new Query("Event")
            .setFilter(new Query.FilterPredicate("recipient", FilterOperator.EQUAL, recipient))
            .addSort("startTime", SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        String description = (String) entity.getProperty("description");
        String location = (String) entity.getProperty("location");
        String startTime = (String) entity.getProperty("startTime");
        String endTime = (String) entity.getProperty("endTime");
        MvpConcreteEvent event = new MvpConcreteEvent(description, location, startTime, endTime);

        schedule.add(event);
      } catch (Exception e) {
        System.err.println("Error reading message.");
        System.err.println(entity.toString());
        e.printStackTrace();
      }
    }
    return schedule;
  }


  /**
   * Gets all Events
   *
   * @return a list of messages posted, or empty list if user has never posted a
   *     message. List is sorted by user and time descending.
   */
  public List<MvpConcreteEvent> getAllMessages() {
    List<MvpConcreteEvent> schedule = new ArrayList<>();
    Query query = new Query("Event")
        .addSort("user", SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      String user = (String) entity.getProperty("user");
      schedule.addAll(getEvents(user));
    }
    return schedule;
  }
  /** 
   *Stores the User in Datastore.
   */

  /** Returns the total number of messages for all users. */
  public int getTotalMessageCount() {
    Query query = new Query("Event");
    PreparedQuery results = datastore.prepare(query);
    return results.countEntities(FetchOptions.Builder.withLimit(1000));
  }
  
}
