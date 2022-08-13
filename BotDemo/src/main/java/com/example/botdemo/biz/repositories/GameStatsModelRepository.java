package com.example.botdemo.biz.repositories;

import com.example.botdemo.biz.entities.GameStatsModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameStatsModelRepository extends JpaRepository<GameStatsModel, Long> {
}