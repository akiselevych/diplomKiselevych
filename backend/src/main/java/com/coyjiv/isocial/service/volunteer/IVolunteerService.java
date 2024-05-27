package com.coyjiv.isocial.service.volunteer;

import com.coyjiv.isocial.dto.respone.VolunteerResponseDto;
import com.coyjiv.isocial.dto.respone.page.PageWrapper;
import com.coyjiv.isocial.exceptions.EntityNotFoundException;


public interface IVolunteerService {

  PageWrapper<VolunteerResponseDto> findAll(int page, int size);

  VolunteerResponseDto findById(Long id) throws EntityNotFoundException;
  VolunteerResponseDto findByUserId(Long userId) throws EntityNotFoundException;

  void create(Long userId) throws EntityNotFoundException;

  void delete(Long id) throws EntityNotFoundException;


}
