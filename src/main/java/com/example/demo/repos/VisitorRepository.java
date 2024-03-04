package com.example.demo.repos;


import com.example.demo.domain.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {
}
