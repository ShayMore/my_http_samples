package com.moore.constants;

/**
 * Created by Moore on 2018/8/1.
 * <p>
 * 常量类
 */
public class Constants {

  public static final String token = "x-x-x-x-x-"; //用于服务器检查客户端身份, 一般放在Http.Header里面

  /**
   * 通用网关
   */
  public static final String ROOT_GATE_WAY = "https://127.0.0.1/moore";

  // --------------------------- 业务api, 根据实际业务更改 ---------------------------

  public static final String URI_GET_X_URL = "%s/api/x/url";

  public static final String URI_POST_USER_INFO = "%s/api/user/modify";

  public static final String URI_PUT_USER_FILE = "%s/api/user/face-pic/upload";

  // --------------------------- 其他常量 ---------------------------

  public static final String FACE_PIC = "facePic";
}
