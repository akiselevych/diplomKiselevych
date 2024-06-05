package com.coyjiv.isocial.transfer.fundraising;

import com.coyjiv.isocial.auth.EmailPasswordAuthProvider;
import com.coyjiv.isocial.dao.VolunteerRepository;
import com.coyjiv.isocial.domain.Fundraising;
import com.coyjiv.isocial.domain.Volunteer;
import com.coyjiv.isocial.dto.request.fundraising.FundraisingRequestDto;
import com.coyjiv.isocial.transfer.DtoMapperFacade;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FundraisingRequestMapper extends DtoMapperFacade<Fundraising,FundraisingRequestDto> {

  private final VolunteerRepository volunteerRepository;
  private final EmailPasswordAuthProvider authProvider;


  public FundraisingRequestMapper(VolunteerRepository volunteerRepository,
                                  EmailPasswordAuthProvider authProvider) {
    super(Fundraising.class, FundraisingRequestDto.class);
    this.volunteerRepository = volunteerRepository;
    this.authProvider = authProvider;
  }


  @Override
  protected void decorateEntity(Fundraising entity, FundraisingRequestDto dto) {
    Volunteer volunteer = volunteerRepository.findByUserId(authProvider.getAuthenticationPrincipal())
            .orElseThrow(() -> new EntityNotFoundException("Volunteer not found"));
    entity.setVolunteer(volunteer);
    entity.setClosed(false);
    entity.setActualAmount(0);
  }

}
