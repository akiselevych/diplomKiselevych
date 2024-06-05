package com.coyjiv.isocial.service.favorite;

import com.coyjiv.isocial.domain.Favorite;
import com.coyjiv.isocial.dto.request.favorite.FavoriteRequestDto;
import com.coyjiv.isocial.dto.respone.favorite.FavoriteResponseDto;
import com.coyjiv.isocial.dto.respone.page.PageWrapper;
import com.coyjiv.isocial.exceptions.RequestValidationException;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IFavoriteService {
  PageWrapper<FavoriteResponseDto> findAllActive(int page, int size);

  Optional<Favorite> findActiveById(Long id);

  Optional<Favorite> findActiveBySelectorIdPostId(Long postId);

  PageWrapper<FavoriteResponseDto> findActiveBySelectorId(int page, int size, Long id);

  List<FavoriteResponseDto> findActiveByPostId( Long id);

  Favorite create(FavoriteRequestDto favoriteRequestDto) throws IllegalAccessException;

  void delete(Long id);

  boolean isFavorite(Long postId) throws EntityNotFoundException;

  boolean toggle(Long postId) throws IllegalAccessException;
}
