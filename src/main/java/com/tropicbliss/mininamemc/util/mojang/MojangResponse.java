package com.tropicbliss.mininamemc.util.mojang;

public class MojangResponse {

  private String uuid;
  private Username[] username_history;
  private Textures textures;
  private String created_at;

  public String getUuid() {
    return uuid;
  }

  public Username[] getUsernameHistory() {
    return username_history;
  }

  public Textures getTextures() {
    return textures;
  }

  public String getCreatedAt() {
    return created_at;
  }

  public static class Textures {

    private boolean slim;
    private boolean custom;
    private Skin skin;
    private Cape cape;

    public boolean isSlim() {
      return slim;
    }

    public boolean isCustom() {
      return custom;
    }

    public Skin getSkin() {
      return skin;
    }

    public Cape getCape() {
      return cape;
    }
  }

  public static class Skin {

    private String url;

    public String getUrl() {
      return url;
    }
  }

  public static class Cape {

    private String url;

    public String getUrl() {
      return url;
    }
  }

  public static class Username {

    private String username;
    private String changed_at;

    public String getUsername() {
      return username;
    }

    public String getChangedAt() {
      return changed_at;
    }
  }
}
