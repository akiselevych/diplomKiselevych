package com.coyjiv.isocial.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fundraisings")
public class Fundraising  extends AbstractEntity {

  @Column(name = "name")
  private String name;

  @Column(name = "is_closed")
  private boolean isClosed;

  @Column(name = "final_amount")
  private BigDecimal finalAmount;

  @Column(name = "actual_amount")
  private BigDecimal actualAmount;

  @Column(name = "text_content")
  private String textContent;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "volunteer_id")
  private Volunteer volunteer;

  @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
  @JoinColumn(name = "report_id", referencedColumnName = "id")
  private FundraisingReport fundraisingReport;
}
