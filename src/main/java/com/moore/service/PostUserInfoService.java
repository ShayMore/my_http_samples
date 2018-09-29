package com.moore.service;

import com.moore.constants.Constants;
import com.moore.domain.User;
import com.moore.util.HttpClientUtil;

/**
 * Created by Moore on 2018/8/10.
 * 提交RequestBody, 如：提交用户信息
 */
public class PostUserInfoService {

  public String postUser(String businessId, User user) throws Exception {

    String url = String.format(Constants.URI_POST_USER_INFO, Constants.ROOT_GATE_WAY, businessId);
    System.out.println(url);

    String response = HttpClientUtil.build().postJsonWithAuth(url, user, Constants.token);
    System.out.println(response);
    return response;
  }
}
