package com.coyjiv.isocial.service.volunteer;


import com.coyjiv.isocial.auth.EmailPasswordAuthProvider;
import com.coyjiv.isocial.dao.UserRepository;
import com.coyjiv.isocial.dao.VolunteerRepository;
import com.coyjiv.isocial.domain.User;
import com.coyjiv.isocial.domain.Volunteer;
import com.coyjiv.isocial.dto.respone.volunteer.VolunteerResponseDto;
import com.coyjiv.isocial.dto.respone.page.PageWrapper;
import com.coyjiv.isocial.transfer.volunteer.VolunteerResponseMapper;
import jakarta.persistence.EntityNotFoundException;
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
  private final VolunteerResponseMapper volunteerResponseMapper;
  private final EmailPasswordAuthProvider authProvider;

  @Override
  @Transactional(readOnly = true)
  public PageWrapper<VolunteerResponseDto> findAll(int page, int size) {
    Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "id"));
    Pageable pageable = PageRequest.of(page, size, sort);

    Page<Volunteer> volunteerPage = volunteerRepository.findAll(pageable);
    List<VolunteerResponseDto> dtos = volunteerPage.getContent().stream().map(volunteerResponseMapper::convertToDto).toList();

    return new PageWrapper<>(dtos,volunteerPage.hasNext());
  }

  @Override
  @Transactional(readOnly = true)
  public VolunteerResponseDto findById(Long id) {
    Volunteer volunteer = volunteerRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Not found"));

    return volunteerResponseMapper.convertToDto(volunteer);
  }

  @Override
  public VolunteerResponseDto findByUserId(Long userId) {
    User user = userRepository.findActiveById(userId)
            .orElseThrow(() -> new EntityNotFoundException("Not found"));
    if (user.isVolunteer()){
      Volunteer volunteer = user.getVolunteerEntity();

      return volunteerResponseMapper.convertToDto(volunteer);
    } else {
      throw new EntityNotFoundException("Not found");
    }
  }

  @Override
  @Transactional
  public void create() throws IllegalAccessException {
    User user = userRepository.findActiveById(authProvider.getAuthenticationPrincipal())
            .orElseThrow(() -> new EntityNotFoundException("Not found"));
    if (!user.isVerified()){
      throw new IllegalAccessException("User not verified");
    }
    user.setVolunteer(true);
    Volunteer volunteer = new Volunteer();
    volunteer.setUser(user);
    user.setVolunteerEntity(volunteer);
    userRepository.save(user);
    volunteerRepository.save(volunteer);
  }

  @Override
  @Transactional
  public void delete(Long id) {
   Volunteer volunteer = volunteerRepository.findById(id)
           .orElseThrow(() -> new EntityNotFoundException("Not found"));
   User user = volunteer.getUser();
   user.setVolunteer(false);
   user.setVolunteerEntity(null);
   volunteerRepository.delete(volunteer);
  }
}
