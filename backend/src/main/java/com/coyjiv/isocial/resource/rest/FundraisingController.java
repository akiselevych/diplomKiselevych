package com.coyjiv.isocial.resource.rest;


import com.coyjiv.isocial.domain.Fundraising;
import com.coyjiv.isocial.domain.FundraisingReport;
import com.coyjiv.isocial.dto.request.fundraising.FundraisingReportRequestDto;
import com.coyjiv.isocial.dto.request.fundraising.FundraisingRequestDto;
import com.coyjiv.isocial.service.fundraising.IFundraisingService;
import com.coyjiv.isocial.service.fundraisingReport.IFundraisingReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fundraising")
public class FundraisingController {

  private final IFundraisingService fundraisingService;
  private final IFundraisingReportService fundraisingReportService;


  @GetMapping
  public ResponseEntity<?> findAll(@RequestParam("page") int page, @RequestParam("size") int size) {
    return ResponseEntity.ok(fundraisingService.findAll(page, size));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> findById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(fundraisingService.findById(id));
  }

  @GetMapping("/user/{id}")
  public ResponseEntity<?> findUser(@PathVariable("id") Long id) {
    return ResponseEntity.ok(fundraisingService.findAllForUser(id));
  }

  @PostMapping
  public ResponseEntity<?> create(@Valid @RequestBody FundraisingRequestDto requestDto) {
    fundraisingService.create(requestDto);
    return ResponseEntity.status(201).build();
  }

  @PostMapping("/donate/{id}")
  public ResponseEntity<?> donate(@RequestParam("amount") int amount,
                                  @PathVariable("id") Long id) {
    fundraisingService.donate(id,amount);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/close/{id}")
  public ResponseEntity<?> close(@PathVariable("id") Long id) throws IllegalAccessException {
    fundraisingService.close(id);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable("id") Long id) {
    fundraisingService.delete(id);
    return ResponseEntity.status(204).build();
  }

  @PostMapping(value = "/report",consumes = {"multipart/form-data"},
          produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> createReport(@Valid @RequestPart(value = "dto") FundraisingReportRequestDto requestDto,
                                        @RequestPart(value = "file") MultipartFile file
                                        ) throws FileUploadException {
    return ResponseEntity.status(201).body(fundraisingReportService.create(requestDto,file));
  }

  @PutMapping(value = "/report/{id}",consumes = {"multipart/form-data"},
          produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> updateReport(@Valid @RequestPart(value = "dto") FundraisingReportRequestDto requestDto,
                                        @RequestPart(value = "file") MultipartFile file,
                                        @PathVariable("id") Long id
  ){
    return ResponseEntity.status(201).body(fundraisingReportService.update(requestDto,file,id));
  }

  @DeleteMapping("/report/{id}")
  public ResponseEntity<?> deleteReport(@PathVariable("id") Long id) {
    fundraisingReportService.delete(id);
    return ResponseEntity.status(204).build();
  }

  @GetMapping("/report/download/{fileId}")
  public ResponseEntity<Resource> downloadDoc(@PathVariable("fileId") Long fileId) {
    FundraisingReport report = fundraisingReportService.findById(fileId);

    return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(report.getFileType()))
            .header(HttpHeaders.CONTENT_DISPOSITION,
                    String.format("attachment; filename=\"%s\"", report.getFileName()))
            .body(new ByteArrayResource(report.getTransactionReport()));
  }


}
