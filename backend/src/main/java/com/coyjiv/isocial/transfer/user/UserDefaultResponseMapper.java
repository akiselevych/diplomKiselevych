package com.coyjiv.isocial.transfer.user;

import com.coyjiv.isocial.domain.User;
import com.coyjiv.isocial.dto.respone.user.UserDefaultResponseDto;
import com.coyjiv.isocial.service.friend.IFriendService;
import com.coyjiv.isocial.transfer.DtoMapperFacade;
import org.springframework.stereotype.Service;

@Service
public class UserDefaultResponseMapper extends DtoMapperFacade<User, UserDefaultResponseDto> {
  private final IFriendService friendService;

  public UserDefaultResponseMapper(IFriendService friendService) {
    super(User.class, UserDefaultResponseDto.class);
    this.friendService = friendService;
  }

  @Override
  protected void decorateDto(UserDefaultResponseDto dto, User entity) {
    dto.setFriendsCount(friendService.getFriendsCount(entity.getId()));
  }
}
