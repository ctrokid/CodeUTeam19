package com.google.codeu.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import java.io.IOException;
import java.util.Scanner;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Returns NYC-museum data as a JSON array, e.g. [{"lat": 38.4404675, "lng": -122.7144313}]
 */
public class MapServlet extends HttpServlet {
  JsonArray museumArray;

  @Override
  public void init() {
    museumArray = new JsonArray();
    Gson gson = new Gson();
    Scanner scanner = new Scanner(getServletContext()
            .getResourceAsStream("/WEB-INF/nyc-museums.csv"));

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      if (line == "") {
        break;
      }
      String[] parts = line.split(",");
      String[] coord = (parts[0].substring(7, parts[0].length() - 1)).split(" ");
      Double lng = Double.parseDouble(coord[0]);
      Double lat = Double.parseDouble((coord[1]));
      String name = parts[1];
      museumArray.add(gson.toJsonTree(new Museum(name, lat, lng)));
    }
    scanner.close();
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    response.getOutputStream().println(museumArray.toString());
  }

  private static class Museum {
    String name;
    double lat;
    double lng;

    private Museum(String name, double lat, double lng) {
      this.name = name;
      this.lat = lat;
      this.lng = lng;
    }
  }
}
