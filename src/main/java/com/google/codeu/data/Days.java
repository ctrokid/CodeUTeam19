package com.google.codeu.data;

import java.lang.reflect.Array;

public enum Days {
  Monday("Monday"), Tuesday("Tuesday"), Wednesday("Wednesday"),
  Thursday("Thursday"), Friday("Friday"), Saturday("Saturday"), Sunday("Sunday");
  
  private String value;

  Days(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static Days getValueEnum(String day) {
    Days[] enumVal = {Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday};
    String[] rawVal = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
    for (int i =0; i < 7 ; i++) {
      if(day.compareTo(rawVal[i]) == 0 ) { return enumVal[i]; }
    }
    throw new RuntimeException("Error: String value for day of the week entered invalid.");
  }


}
