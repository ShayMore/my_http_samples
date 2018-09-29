package com.moore.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import com.moore.constants.Constants;
import com.moore.util.HttpClientUtil;

import java.io.File;
import java.io.IOException;

/**
 * Created by Moore on 2018/8/16.
 * 上传multiPartFile, 如：用户头像
 */
public class PutUserFileService {

  Log log = LogFactory.getLog(PutUserFileService.class);

  public String putUserFile(String businessId, File file) throws IOException {

    String url = String.format(Constants.URI_PUT_USER_FILE, Constants.ROOT_GATE_WAY, businessId);
    log.info(url);

    MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
    multipartEntityBuilder.addBinaryBody(Constants.FACE_PIC, file).setMode(HttpMultipartMode.RFC6532);

    String response =
        HttpClientUtil.build().putFormDataWithAuth(url, multipartEntityBuilder.build(), Constants.token);
    log.info(response);
    return response;
  }
}
