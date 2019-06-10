package com.turing.backendapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class JsonUtil {
  public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static String writeValueAsString(Map map){
    try {
      return OBJECT_MAPPER.writeValueAsString(map);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
