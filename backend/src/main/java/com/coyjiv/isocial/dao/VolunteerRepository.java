package com.coyjiv.isocial.dao;

import com.coyjiv.isocial.domain.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer,Long> {
}
