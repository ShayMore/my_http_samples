package com.moore.example;

import com.moore.service.PutUserFileService;

import java.io.File;
import java.io.IOException;

/**
 * Created by Moore on 2018/8/16.
 * 上传multiPartFile, 如：用户头像
 */
public class PutUserFileExample {
  public static void main(String[] args) throws IOException {

    String businessId = "17130260";
    File file = new File("D:/http/face.jpg");

    PutUserFileService userFileService = new PutUserFileService();
    userFileService.putUserFile(businessId, file);
  }
}
