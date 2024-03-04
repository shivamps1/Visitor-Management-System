package com.example.demo.repos;


import com.example.demo.domain.Flat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FlatRepository extends JpaRepository<Flat, Long> {
}
