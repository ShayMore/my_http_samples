package com.moore.example;

import com.moore.domain.User;
import com.moore.service.PostUserInfoService;

/**
 * Created by Moore on 2018/8/10.
 * 提交RequestBody, 如：提交用户信息
 */
public class PostUserInfoExample {
  public static void main(String[] args) throws Exception {

    String businessId = "1234";

    Long userId = 234L;
    String userName = "Moore";
    User user = new User(userId, userName);

    PostUserInfoService applicationsService = new PostUserInfoService();
    applicationsService.postUser(businessId, user);
  }
}
