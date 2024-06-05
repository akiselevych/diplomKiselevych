package com.coyjiv.isocial.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@ToString(exclude = {"chats"})
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends AbstractEntity {
  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "email")
  private String email;

  @Column(name = "city")
  private String city;

  @Column(name = "birth_place")
  private String birthPlace;

  @Column(name = "study_place")
  private String studyPlace;

  @Column(name = "password")
  private String password;

  @Column(name = "avatars_url")
  private List<String> avatarsUrl;

  @Column(name = "banner_url")
  private String bannerUrl;

  @Column(name = "bio")
  private String bio;

  @Enumerated(EnumType.STRING)
  @Column(name = "gender")
  private UserGender gender;

  @Column(name = "is_private")
  private boolean isPrivate;

  @Enumerated(EnumType.STRING)
  @Column(name = "activity_status")
  private UserActivityStatus activityStatus;

  @Column(name = "last_seen")
  private Date lastSeen;

  @Column(name = "date_of_birth")
  private Date dateOfBirth;

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
  private Set<Role> roles;

  @ManyToMany(mappedBy = "users")
  private List<Chat> chats;

  @Column(name = "is_premium")
  private boolean isPremium;

  @Column(name = "premium_nickname")
  private String premiumNickname;

  @Column(name = "premium_emoji")
  private String premiumEmoji;

  @Column(name = "is_verified")
  private boolean isVerified;

  @Column(name = "is_volunteer")
  private boolean isVolunteer;

  @OneToOne(mappedBy = "user")
  private Volunteer volunteerEntity;

  public String getAvatar() {
    return !avatarsUrl.isEmpty() ? avatarsUrl.get(0) : "";
  }

  public String getFullName() {
    return firstName + " " + lastName;
  }

}
