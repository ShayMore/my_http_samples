package com.moore.util;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLInitializationException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class HttpClientUtil {

  private static final String HTTP = "http";
  private static final String HTTPS = "https";
  private static final String UTF_8 = "UTF-8";
  private static final String APPLICATION_JSON = "application/json";
  private static final String X_AUTHORIZATION = "X-Authorization";
  private static final String CONTENT_TYPE = "Content-Type";

  private static final int SOCKET_TIMEOUT = 30 * 1000;
  private static final int CONNECTION_TIMEOUT = 5 * 1000;
  private static final int CONNECT_REQUEST_TIMEOUT = 2 * 1000;

  private static final RequestConfig CONFIG;
  private static final HttpClient CLIENT;
  private static final Executor EXECUTOR;
  private static final PoolingHttpClientConnectionManager CONNECTION_MANAGER;

  static {
    LayeredConnectionSocketFactory ssl = null;
    try {
      ssl = SSLConnectionSocketFactory.getSystemSocketFactory();
    } catch (final SSLInitializationException ex) {
      final SSLContext sslcontext;
      try {
        sslcontext = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);
        sslcontext.init(null, null, null);
        ssl = new SSLConnectionSocketFactory(sslcontext);
      } catch (final SecurityException ignore) {
      } catch (final KeyManagementException ignore) {
      } catch (final NoSuchAlgorithmException ignore) {
      }
    }
    final Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
        .register(HTTP, PlainConnectionSocketFactory.getSocketFactory())
        .register(HTTPS, ssl != null ? ssl : SSLConnectionSocketFactory.getSocketFactory())
        .build();

    CONFIG = RequestConfig.copy(RequestConfig.DEFAULT)
                 .setSocketTimeout(SOCKET_TIMEOUT)
                 .setConnectTimeout(CONNECTION_TIMEOUT)
                 .setConnectionRequestTimeout(CONNECT_REQUEST_TIMEOUT)
                 .build();

    CONNECTION_MANAGER = new PoolingHttpClientConnectionManager(registry);
    CONNECTION_MANAGER.setDefaultMaxPerRoute(100);
    CONNECTION_MANAGER.setMaxTotal(200);
    CONNECTION_MANAGER.setValidateAfterInactivity(1000);
    CLIENT = HttpClientBuilder.create()
                              .setConnectionManager(CONNECTION_MANAGER)
                              .setDefaultRequestConfig(CONFIG)
                              .disableAutomaticRetries()
                              .build();

    EXECUTOR = Executor.newInstance(CLIENT);
  }

  private HttpClientUtil() {

  }

  public static HttpClientUtil build() {
    return new HttpClientUtil();
  }

  public String getWithAuth(String url, String token) throws IOException {
    return execute(Request.Get(url).addHeader(X_AUTHORIZATION, token));
  }

  public String postWithAuth(String url, String token, NameValuePair... params) throws IOException {
    return execute(
        Request.Post(url).addHeader(X_AUTHORIZATION, token).bodyForm(Arrays.asList(params), Consts.UTF_8));
  }

  public String postJsonWithAuth(String url, Object obj, String token) throws IOException {
    return execute(Request.Post(url).addHeader(CONTENT_TYPE, APPLICATION_JSON).addHeader(X_AUTHORIZATION, token)
                          .body(new StringEntity(JsonUtils.toJsonNotNullKey(obj), UTF_8)));
  }

  public String putFormDataWithAuth(String url, HttpEntity entity, String token) throws IOException {
    return execute(Request.Put(url).addHeader(X_AUTHORIZATION, token).body(entity));
  }

  private String execute(Request request) throws IOException {
    return returnContentString(EXECUTOR.execute(request).returnResponse());
  }

  public String returnContentString(HttpResponse response) throws IOException {
    return handleResponseString(response);
  }

  public String handleResponseString(final HttpResponse response) throws IOException {
    final HttpEntity entity = response.getEntity();
    Charset charset = Consts.UTF_8;
    return null == entity ? "" : new String(EntityUtils.toByteArray(entity), charset);
  }
}
