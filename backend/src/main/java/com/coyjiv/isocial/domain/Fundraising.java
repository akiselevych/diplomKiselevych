package com.coyjiv.isocial.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString(exclude = "volunteer")
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
  private int finalAmount;

  @Column(name = "actual_amount")
  private int actualAmount;

  @Column(name = "text_content")
  private String textContent;

  @ManyToOne
  @JoinColumn(name = "volunteer_id",referencedColumnName = "id")
  private Volunteer volunteer;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "report_id", referencedColumnName = "id")
  private FundraisingReport fundraisingReport;

  public void donate(int sum){
    this.actualAmount += sum;
  }
}
