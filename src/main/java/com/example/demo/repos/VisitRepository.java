package com.example.demo.repos;


import com.example.demo.domain.Flat;
import com.example.demo.domain.Visit;
import com.example.demo.model.UserStatus;
import com.example.demo.model.VisitStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    List<Visit> findByStatusAndFlat(VisitStatus visitStatus, Flat flat);


}
