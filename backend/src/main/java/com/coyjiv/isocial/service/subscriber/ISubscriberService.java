package com.coyjiv.isocial.service.subscriber;

import com.coyjiv.isocial.dto.respone.subscriber.DefaultSubscriberResponseDto;


public interface ISubscriberService {
  DefaultSubscriberResponseDto getSubscribersCount();

  DefaultSubscriberResponseDto getSubscriptionsCount();

  void deleteSubscriber(Long userId, Long subscriberId);

  void createSubscriber(Long userId, Long subscriberId);
}
