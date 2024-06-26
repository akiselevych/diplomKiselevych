package com.coyjiv.isocial.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import jakarta.persistence.EntityNotFoundException;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class OnlineUsersCache {

  private static final Cache<String, String> onlineUsersCache = CacheBuilder.newBuilder()
          .expireAfterWrite(24, TimeUnit.HOURS)
          .build();

  public static void putUserId(String sessionId, Long userId) {
    onlineUsersCache.put(sessionId, String.valueOf(userId));
  }

  public static Long getUserId(String uuid) {
    String userId = onlineUsersCache.getIfPresent(uuid);
    onlineUsersCache.invalidate(uuid);
    if (Objects.equals(userId, "null") || userId == null) {
      throw new EntityNotFoundException("User not found");
    }
    return Long.valueOf(userId);
  }
}
