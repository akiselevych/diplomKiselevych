package com.coyjiv.isocial.resource.rest;

import com.coyjiv.isocial.dto.respone.volunteer.VolunteerResponseDto;
import com.coyjiv.isocial.dto.respone.page.PageWrapper;
import com.coyjiv.isocial.service.volunteer.IVolunteerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/volunteers")
public class VolunteerController {

  private final IVolunteerService volunteerService;

  @GetMapping
  public ResponseEntity<PageWrapper<VolunteerResponseDto>> findAll(@RequestParam("page") int page,
                                                                   @RequestParam("size") int size
  ) {
    return ResponseEntity.ok(volunteerService.findAll(page, size));
  }

  @GetMapping("/{id}")
  public ResponseEntity<VolunteerResponseDto> findById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(volunteerService.findById(id));
  }

  @GetMapping("/user/{user_id}")
  public ResponseEntity<VolunteerResponseDto> findByUserId(@PathVariable("user_id") Long id)
          {
    return ResponseEntity.ok(volunteerService.findByUserId(id));
  }

  @PostMapping
  public ResponseEntity<?> create() throws IllegalAccessException {
    volunteerService.create();
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable("id") Long id) {
    volunteerService.delete(id);
    return ResponseEntity.ok().build();
  }

}
