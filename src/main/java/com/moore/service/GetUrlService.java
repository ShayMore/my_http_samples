package com.moore.service;

import com.moore.constants.Constants;
import com.moore.util.HttpClientUtil;

import java.io.IOException;

/**
 * Created by moore 7/16/2018
 * GET 字符串, 如：url
 */
public class GetUrlService {

  public String getUrl(String businessId) throws IOException {

    String url = String.format(Constants.URI_GET_X_URL, Constants.ROOT_GATE_WAY, businessId);
    System.out.println(url);

    String response = HttpClientUtil.build().getWithAuth(url, Constants.token);
//    String response1 = HttpClientUtil.build().postWithAuth(url, Constants.token);// Post方法调用
    System.out.println(response);
    return response;
  }
}
