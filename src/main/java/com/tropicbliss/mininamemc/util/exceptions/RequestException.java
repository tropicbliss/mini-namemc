package com.tropicbliss.mininamemc.util.exceptions;

public class RequestException extends Exception {

  public RequestException(int status) {
    super("HTTP " + status);
  }
}
