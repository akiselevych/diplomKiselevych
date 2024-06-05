package com.coyjiv.isocial.resource.rest;

import com.coyjiv.isocial.dto.request.dia.ValidatePassportRequestDto;
import com.coyjiv.isocial.service.dia.IDiaService;
import com.coyjiv.isocial.service.user.IUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.ServiceUnavailableException;
import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
  private final IUserService userService;

  @GetMapping
  public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") @Min(0) Integer page,
                                   @RequestParam(defaultValue = "10") @Min(0) Integer size) {
    return ResponseEntity.ok(userService.findAllActive(page, size));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> findById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(userService.findActiveById(id));
  }

  @GetMapping("/search")
  public ResponseEntity<?> findByName(@RequestParam String name, @RequestParam(defaultValue = "0") @Min(0) int page,
                                      @RequestParam(defaultValue = "10") @Min(0) int size) {
    return ResponseEntity.ok(userService.findByName(name, page, size));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable("id") @Min(0) Long id, @RequestBody Map<Object, Object> fields)
          throws IllegalAccessException {
    userService.update(id, fields);
    return ResponseEntity.status(204).build();
  }

  @PostMapping("/verify")
  public ResponseEntity<?> verify(@Valid @RequestBody ValidatePassportRequestDto dto)
          throws ServiceUnavailableException, JsonProcessingException {
    boolean res = userService.verify(dto);
    return res ? ResponseEntity.status(201).build() : ResponseEntity.status(400).body("Документи не верифіковані");
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable("id") @Min(0) Long id)
          throws IllegalAccessException {
    userService.delete(id);
    return ResponseEntity.status(204).build();
  }

}
