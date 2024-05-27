package com.coyjiv.isocial.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString(exclude = "fundraising")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fundraisings_reports")
public class FundraisingReport extends AbstractEntity {


  @Column(name = "text_content")
  private String textContent;

  @Lob
  @Column(name = "transaction_report")
  private byte[] transactionReport;

  @OneToOne(mappedBy = "fundraisingReport",fetch = FetchType.EAGER)
  private Fundraising fundraising;
}
