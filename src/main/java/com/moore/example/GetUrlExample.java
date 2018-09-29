package com.moore.example;

import com.moore.service.GetUrlService;
import java.io.IOException;

/**
 * Created by moore 7/16/2018
 * <p>
 * GET 字符串, 如：url
 */
public class GetUrlExample {

  public static void main(String[] args) throws IOException {

    String businessId = "123"; //业务上的id, 如用户id, 订单id ...

    GetUrlService signatureUrlService = new GetUrlService();
    signatureUrlService.getUrl(businessId);
  }
}
