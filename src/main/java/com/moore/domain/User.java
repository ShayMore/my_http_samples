package com.moore.domain;

import lombok.Data;
import java.util.Date;

@Data
public class User {
  private Long userId;
  private String userName;
  private Date registerTime;

  public User(Long userId, String userName) {
    this.userId = userId;
    this.userName = userName;
  }

}
