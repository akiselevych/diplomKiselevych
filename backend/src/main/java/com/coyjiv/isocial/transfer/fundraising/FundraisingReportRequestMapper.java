package com.coyjiv.isocial.transfer.fundraising;

import com.coyjiv.isocial.dao.FundraisingRepository;
import com.coyjiv.isocial.domain.Fundraising;
import com.coyjiv.isocial.domain.FundraisingReport;
import com.coyjiv.isocial.dto.request.fundraising.FundraisingReportRequestDto;
import com.coyjiv.isocial.transfer.DtoMapperFacade;
import org.springframework.stereotype.Service;

@Service
public class FundraisingReportRequestMapper extends DtoMapperFacade<FundraisingReport, FundraisingReportRequestDto> {

  private final FundraisingRepository fundraisingRepository;

  public FundraisingReportRequestMapper(FundraisingRepository fundraisingRepository) {
    super(FundraisingReport.class,FundraisingReportRequestDto.class);
    this.fundraisingRepository = fundraisingRepository;
  }

  @Override
  protected void decorateEntity(FundraisingReport entity, FundraisingReportRequestDto dto) {
    Fundraising fundraising = fundraisingRepository.findById(dto.getFundraisingId())
            .orElseThrow(() -> new RuntimeException("Fundraising not found"));
    entity.setFundraising(fundraising);
  }
}
