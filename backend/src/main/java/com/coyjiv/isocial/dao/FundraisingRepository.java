package com.coyjiv.isocial.dao;

import com.coyjiv.isocial.domain.Fundraising;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FundraisingRepository extends JpaRepository<Fundraising,Long> {

  @Query("SELECT f FROM Fundraising f JOIN f.volunteer v WHERE v.id = :id")
  List<Fundraising> findAllForUser(@Param("id") Long id);

}
