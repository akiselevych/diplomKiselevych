package com.coyjiv.isocial.service.volunteer;

import com.coyjiv.isocial.dto.respone.volunteer.VolunteerResponseDto;
import com.coyjiv.isocial.dto.respone.page.PageWrapper;
import jakarta.persistence.EntityNotFoundException;


public interface IVolunteerService {

  PageWrapper<VolunteerResponseDto> findAll(int page, int size);

  VolunteerResponseDto findById(Long id) throws EntityNotFoundException;
  VolunteerResponseDto findByUserId(Long userId) throws EntityNotFoundException;

  void create() throws IllegalAccessException;

  void delete(Long id) throws EntityNotFoundException;


}
