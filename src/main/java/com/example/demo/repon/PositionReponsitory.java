package com.example.demo.repon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Position;

@Repository
public interface PositionReponsitory extends JpaRepository<Position, Integer> {

}
