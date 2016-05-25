package com.insidecoding.opium.rest.api.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.insidecoding.opium.entity.OpiumCallResponse;

@Component
public class RemoteExecutionService {
  private static final HttpClient httpClient;

  static {
    int connTimeout = Integer.parseInt(System.getProperty("connTimeout", "3"));

    RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connTimeout * 1000)
        .build();

    httpClient = HttpClientBuilder.create().useSystemProperties()
        .setDefaultRequestConfig(requestConfig)
        .setConnectionManager(new PoolingHttpClientConnectionManager()).build();

  }

  public OpiumCallResponse stop(String ip, String port) throws IOException {
    HttpDelete delete = new HttpDelete("http://" + ip + ":2021" + "/appium/" + port);
    HttpResponse httpResp = httpClient.execute(delete);

    String body = "";
    try (InputStream stream = httpResp.getEntity().getContent();) {
      body = IOUtils.toString(stream, Charset.forName("UTF-8"));
    }

    return new OpiumCallResponse(httpResp.getStatusLine().getStatusCode(), body);

  }

  public OpiumCallResponse start(String ip, String commands) throws IOException {
    HttpPost post = new HttpPost("http://" + ip + ":2021" + "/appium");
    post.addHeader("Content-Type", "application/json");
    JsonObject obj = new JsonObject();
    obj.addProperty("command", commands);
    post.setEntity(new StringEntity(obj.toString()));

    HttpResponse httpResp = httpClient.execute(post);
    String body = "";
    try (InputStream stream = httpResp.getEntity().getContent();) {
      body = IOUtils.toString(stream, Charset.forName("UTF-8"));
    }

    return new OpiumCallResponse(httpResp.getStatusLine().getStatusCode(), body);
  }

  public boolean healthy(String ip, int retry) {
    int count = 0;
    boolean alive = false;
    while (count <= retry && !alive) {
      count++;
      alive = ping(ip);

      if (!alive) {
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          // ignore
        }
      }
    }
    return alive;
  }

  private static boolean ping(String ip) {
    try {
      HttpHead delete = new HttpHead("http://" + ip + ":2021/ping");
      HttpResponse resp = httpClient.execute(delete);
      return resp.getStatusLine().getStatusCode() == 200;
    } catch (IOException e) {
      return false;
    }
  }
}
