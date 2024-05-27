package com.coyjiv.isocial.service.volunteer;


import com.coyjiv.isocial.dao.UserRepository;
import com.coyjiv.isocial.dao.VolunteerRepository;
import com.coyjiv.isocial.domain.User;
import com.coyjiv.isocial.domain.Volunteer;
import com.coyjiv.isocial.dto.respone.VolunteerResponseDto;
import com.coyjiv.isocial.dto.respone.page.PageWrapper;
import com.coyjiv.isocial.exceptions.EntityNotFoundException;
import com.coyjiv.isocial.transfer.user.UserSearchResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VolunteerService implements IVolunteerService{

  private final VolunteerRepository volunteerRepository;
  private final UserRepository userRepository;
  private final UserSearchResponseMapper userSearchResponseMapper;

  @Override
  @Transactional(readOnly = true)
  public PageWrapper<VolunteerResponseDto> findAll(int page, int size) {
    Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "id"));
    Pageable pageable = PageRequest.of(page, size, sort);

    Page<Volunteer> volunteerPage = volunteerRepository.findAll(pageable);
    List<VolunteerResponseDto> dtos = volunteerPage.getContent().stream().map(v -> {
      VolunteerResponseDto dto = new VolunteerResponseDto();
      dto.setFundraisings(v.getFundraisings());
      dto.setUser(userSearchResponseMapper.convertToDto(v.getUser()));
      return dto;
    }).toList();


    return new PageWrapper<>(dtos,volunteerPage.hasNext());
  }

  @Override
  @Transactional(readOnly = true)
  public VolunteerResponseDto findById(Long id) throws EntityNotFoundException {
    Volunteer volunteer = volunteerRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Not found"));
    VolunteerResponseDto dto = new VolunteerResponseDto();
    dto.setUser(userSearchResponseMapper.convertToDto(volunteer.getUser()));
    dto.setFundraisings(volunteer.getFundraisings());
    return dto;
  }

  @Override
  public VolunteerResponseDto findByUserId(Long userId) throws EntityNotFoundException {
    User user = userRepository.findActiveById(userId)
            .orElseThrow(() -> new EntityNotFoundException("Not found"));
    if (user.isVolunteer()){
      Volunteer volunteer = user.getVolunteerEntity();
      VolunteerResponseDto dto = new VolunteerResponseDto();
      dto.setFundraisings(volunteer.getFundraisings());
      dto.setUser(userSearchResponseMapper.convertToDto(volunteer.getUser()));
      return dto;
    } else {
      throw new EntityNotFoundException("Not found");
    }
  }

  @Override
  @Transactional
  public void create(Long userId) throws EntityNotFoundException {
    User user = userRepository.findActiveById(userId)
            .orElseThrow(() -> new EntityNotFoundException("Not found"));
    user.setVolunteer(true);
    Volunteer volunteer = new Volunteer();
    volunteer.setUser(user);
    user.setVolunteerEntity(volunteer);
    userRepository.save(user);
    volunteerRepository.save(volunteer);
  }

  @Override
  @Transactional
  public void delete(Long id) throws EntityNotFoundException {
   Volunteer volunteer = volunteerRepository.findById(id)
           .orElseThrow(() -> new EntityNotFoundException("Not found"));
   User user = volunteer.getUser();
   user.setVolunteer(false);
   user.setVolunteerEntity(null);
   volunteerRepository.delete(volunteer);
  }
}
