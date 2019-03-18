package com.google.codeu.data;

public class Pair<T> {
  private T key;
  private T value;

  public Pair(T key, T value) {
    this.value = value;
    this.key = key;
  }

  public T getKey() {
    return key;
  }

  public T getValue() {
    return value;
  }

}