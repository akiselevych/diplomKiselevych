package com.coyjiv.isocial.transfer.favorite;

import com.coyjiv.isocial.dao.PostRepository;
import com.coyjiv.isocial.dao.UserRepository;
import com.coyjiv.isocial.domain.Favorite;
import com.coyjiv.isocial.domain.Message;
import com.coyjiv.isocial.domain.Post;
import com.coyjiv.isocial.domain.User;
import com.coyjiv.isocial.dto.request.favorite.FavoriteRequestDto;
import com.coyjiv.isocial.dto.request.post.PostRequestDto;
import com.coyjiv.isocial.dto.respone.favorite.FavoriteResponseDto;
import com.coyjiv.isocial.dto.respone.message.MessageNotificationDto;
import com.coyjiv.isocial.dto.respone.post.PostResponseDto;
import com.coyjiv.isocial.dto.respone.user.UserDefaultResponseDto;
import com.coyjiv.isocial.service.post.IPostService;
import com.coyjiv.isocial.service.post.PostService;
import com.coyjiv.isocial.transfer.DtoMapperFacade;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteResponseMapper extends DtoMapperFacade<Favorite, FavoriteResponseDto> {
  private final PostRepository postRepository;
  private final UserRepository userRepository;

  public FavoriteResponseMapper(PostRepository postRepository, UserRepository userRepository) {
    super(Favorite.class, FavoriteResponseDto.class);
    this.postRepository = postRepository;
    this.userRepository = userRepository;
  }

  protected void decorateDto(FavoriteResponseDto dto, Favorite entity) {
    Post post = postRepository.findActiveById(entity.getSelectedPostId()).orElseThrow();
    User author = userRepository.findActiveById(post.getAuthorId()).orElseThrow();
    dto.setAuthorAvatar(author.getAvatar());
    dto.setAuthorFullName(author.getFullName());
    dto.setAuthorLastSeen(author.getLastSeen());
    dto.setAuthorPremium(author.isPremium());
    dto.setAuthorPremiumNickname(author.getPremiumNickname());
    dto.setAuthorPremiumEmoji(author.getPremiumEmoji());
  }
}
