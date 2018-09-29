package com.moore.util;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by Fei.Huang on 2017/11/10.
 */
public class JsonUtils {

  private static final String EMPTY_JSON = "{}";
  private static final String EMPTY_JSON_ARRAY = "[]";

  private static final ObjectMapper MAPPER;
  private static final ObjectMapper MAPPER_HAS_NULL;

  static {
    MAPPER = new ObjectMapper();
    MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    MAPPER_HAS_NULL = new ObjectMapper();
    MAPPER_HAS_NULL.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    MAPPER_HAS_NULL.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
  }

  public static String toJsonNotNullKey(Object obj) {
    return JsonUtils.toJson(obj, MAPPER);
  }

  private static String toJson(Object obj, ObjectMapper mapper) {
    if (obj == null) {
      return StringUtils.EMPTY;
    }

    try {
      StringWriter writer = new StringWriter();
      mapper.writeValue(writer, obj);
      return writer.toString();
    } catch (IOException e) {
      System.out.println("JsonUtils transformation obj to string has error! " + e.getMessage());
    }
    return StringUtils.EMPTY;
  }

  public static boolean isEmpty(String content) {
    return StringUtils.isBlank(content) || EMPTY_JSON.equals(content) || EMPTY_JSON_ARRAY.equals(content);
  }
}
