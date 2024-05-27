package com.coyjiv.isocial.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@ToString(exclude = "fundraisings")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "volunteers")
public class Volunteer extends AbstractEntity {


  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @OneToMany(mappedBy = "volunteer",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
  private List<Fundraising> fundraisings;
}
