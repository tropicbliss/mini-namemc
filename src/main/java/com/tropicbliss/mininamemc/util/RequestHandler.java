package com.tropicbliss.mininamemc.util;

import com.google.gson.Gson;
import com.tropicbliss.mininamemc.util.exceptions.PlayerNotFoundException;
import com.tropicbliss.mininamemc.util.exceptions.RequestException;
import com.tropicbliss.mininamemc.util.mojang.MojangResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

// Singleton
public class RequestHandler {

  private static RequestHandler instance;
  private final HttpClient client;

  private RequestHandler() {
    client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();
  }

  public static RequestHandler getInstance() {
    if (instance == null) {
      instance = new RequestHandler();
    }
    return instance;
  }

  public MojangResponse sendMojangRequest(String name)
      throws URISyntaxException, IOException, InterruptedException, PlayerNotFoundException, RequestException {
    URI url = new URI("https://api.ashcon.app/mojang/v2/user/" + name);
    HttpRequest req = HttpRequest.newBuilder().uri(url).build();
    HttpResponse<String> res = client.send(req, BodyHandlers.ofString());
    int status = res.statusCode();
    if (status == 400 || status == 404) {
      throw new PlayerNotFoundException(name);
    }
    if (status != 200) {
      throw new RequestException(res.statusCode());
    }
    String body = res.body();
    return new Gson().fromJson(body, MojangResponse.class);
  }
}
