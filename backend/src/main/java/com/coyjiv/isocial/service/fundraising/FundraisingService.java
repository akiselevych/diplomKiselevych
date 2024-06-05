package com.coyjiv.isocial.service.fundraising;

import com.coyjiv.isocial.auth.EmailPasswordAuthProvider;
import com.coyjiv.isocial.dao.FundraisingRepository;
import com.coyjiv.isocial.dao.UserRepository;
import com.coyjiv.isocial.domain.Fundraising;
import com.coyjiv.isocial.domain.User;
import com.coyjiv.isocial.dto.request.fundraising.FundraisingRequestDto;
import com.coyjiv.isocial.dto.respone.fundraising.FundraisingResponseDto;
import com.coyjiv.isocial.dto.respone.page.PageWrapper;
import com.coyjiv.isocial.transfer.fundraising.FundraisingRequestMapper;
import com.coyjiv.isocial.transfer.fundraising.FundraisingResponseMapper;
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
public class FundraisingService implements IFundraisingService{

  private final UserRepository userRepository;
  private final FundraisingRepository fundraisingRepository;
  private final FundraisingResponseMapper fundraisingResponseMapper;
  private final FundraisingRequestMapper fundraisingRequestMapper;
  private final EmailPasswordAuthProvider authProvider;

  @Override
  @Transactional(readOnly = true)
  public PageWrapper<FundraisingResponseDto> findAll(int page, int size) {
    Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "id"));
    Pageable pageable = PageRequest.of(page, size, sort);

    Page<Fundraising> pageResp = fundraisingRepository.findAll(pageable);

    List<FundraisingResponseDto> dtos = pageResp.getContent().stream().map(fundraisingResponseMapper::convertToDto).toList();

    return new PageWrapper<>(dtos,pageResp.hasNext());
  }

  @Override
  @Transactional(readOnly = true)
  public List<FundraisingResponseDto> findAllForUser(Long userId) {

    List<Fundraising> pageResp = fundraisingRepository.findAllForUser(userId);
    List<FundraisingResponseDto> dtos = pageResp.stream().map(fundraisingResponseMapper::convertToDto).toList();

    return dtos;
  }

  @Override
  @Transactional(readOnly = true)
  public FundraisingResponseDto findById(Long id) {
    Fundraising fundraising = fundraisingRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Fundraising not found"));

    return fundraisingResponseMapper.convertToDto(fundraising);
  }

  @Override
  @Transactional
  public void create(FundraisingRequestDto dto) {
    Fundraising fundraising = fundraisingRequestMapper.convertToEntity(dto);
    fundraisingRepository.save(fundraising);
  }

  @Override
  @Transactional
  public void donate(Long fundId, int sum) {
    Fundraising fundraising = fundraisingRepository.findById(fundId)
            .orElseThrow(() -> new EntityNotFoundException("Not Found"));
    if (!fundraising.isClosed()){
      fundraising.donate(sum);
      fundraisingRepository.save(fundraising);
    }
  }

  @Override
  @Transactional
  public void close(Long id) throws IllegalAccessException {
    Fundraising fundraising = fundraisingRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Not Found"));
    if (!authProvider.getAuthenticationPrincipal().equals(fundraising.getVolunteer().getUser().getId())){
      throw new IllegalAccessException("ERROR");
    }
    fundraising.setClosed(true);
    fundraisingRepository.save(fundraising);
  }


  @Override
  @Transactional
  public void delete(Long id) {
    fundraisingRepository.deleteById(id);
  }
}
