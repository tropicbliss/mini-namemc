package com.tropicbliss.mininamemc.util;

import java.util.regex.Pattern;

public class Name {

  private final String name;

  public Name(String name) throws InvalidNameException {
    if (!nameValidationPredicate(name)) {
      throw new InvalidNameException(name);
    }
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }

  private static boolean nameValidationPredicate(String name) {
    return !(name.length() < 3 || name.length() > 16 || Pattern.matches("[^a-zA-Z0-9_.]", name));
  }

  public static class InvalidNameException extends Exception {

    public InvalidNameException(String name) {
      super(name + " is an invalid name");
    }
  }
}
