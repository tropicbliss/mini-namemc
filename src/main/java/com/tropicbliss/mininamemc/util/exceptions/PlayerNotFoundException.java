package com.tropicbliss.mininamemc.util.exceptions;

public class PlayerNotFoundException extends Exception {

  public PlayerNotFoundException(String name) {
    super(name + " not found");
  }
}
