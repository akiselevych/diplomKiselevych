package com.coyjiv.isocial.service.fundraisingReport;

import com.coyjiv.isocial.dao.FundraisingReportRepository;
import com.coyjiv.isocial.dao.FundraisingRepository;
import com.coyjiv.isocial.domain.Fundraising;
import com.coyjiv.isocial.domain.FundraisingReport;
import com.coyjiv.isocial.dto.request.fundraising.FundraisingReportRequestDto;
import com.coyjiv.isocial.dto.respone.fundraising.FundraisingReportResponseDto;
import com.coyjiv.isocial.dto.respone.page.PageWrapper;
import com.coyjiv.isocial.transfer.fundraising.FundraisingReportRequestMapper;
import com.coyjiv.isocial.transfer.fundraising.FundraisingReportResponseMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FundraisingReportService implements IFundraisingReportService {

  private final FundraisingReportResponseMapper reportResponseMapper;
  private final FundraisingReportRequestMapper reportRequestMapper;
  private final FundraisingReportRepository fundraisingReportRepository;
  private final FundraisingRepository fundraisingRepository;


  @Override
  @Transactional(readOnly = true)
  public FundraisingReport findById(Long id) {
    return fundraisingReportRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Fundraising report not found"));
  }

  @Override
  @Transactional
  public FundraisingReportResponseDto create(FundraisingReportRequestDto reportRequestDto, MultipartFile file) throws FileUploadException {
    if (Objects.requireNonNull(file.getOriginalFilename()).contains("..")) {
      throw new FileUploadException("Could not upload file");
    }
    FundraisingReport fundraisingReport = reportRequestMapper.convertToEntity(reportRequestDto);
    Fundraising fundraising = fundraisingRepository.findById(reportRequestDto.getFundraisingId())
            .orElseThrow(() -> new EntityNotFoundException("Fundraising not found"));
    fundraising.setClosed(true);
    try {
      fundraisingReport.setFundraising(fundraising);
      fundraising.setFundraisingReport(fundraisingReport);
      fundraisingReport.setTransactionReport(file.getBytes());
      fundraisingReport.setFileName(file.getOriginalFilename());
      fundraisingReport.setFileType(file.getContentType());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    fundraisingReport = fundraisingReportRepository.save(fundraisingReport);
    fundraisingRepository.save(fundraising);
    return reportResponseMapper.convertToDto(fundraisingReport);
  }

  @Override
  @Transactional
  public FundraisingReportResponseDto update(FundraisingReportRequestDto reportRequestDto,MultipartFile file, Long id) {
    FundraisingReport fundraisingReport = fundraisingReportRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("NOT FOUND"));

    fundraisingReport.setTextContent(reportRequestDto.getTextContent());
    try {
      fundraisingReport.setTransactionReport(file.getBytes());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    fundraisingReport = fundraisingReportRepository.save(fundraisingReport);
    return reportResponseMapper.convertToDto(fundraisingReport);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    fundraisingReportRepository.deleteById(id);
  }
}
