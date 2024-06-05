package com.coyjiv.isocial.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class DiaCache {
  private static final Cache<String, String> diaCache = CacheBuilder.newBuilder()
          .expireAfterWrite(2, TimeUnit.HOURS)
          .build();
  private static final String key = "DIA_TOKEN";

  public static void putSessionToken(String token) {
   diaCache.put(key,token);
  }

  public static String getToken() {
   return diaCache.getIfPresent(key);
  }
}
