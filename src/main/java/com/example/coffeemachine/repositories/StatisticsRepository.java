package com.example.coffeemachine.repositories;

import com.example.coffeemachine.entities.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {

    @Query("SELECT s.coffeeName FROM Statistics s ORDER BY s.orderCount DESC LIMIT 1")
    Optional<String> getByMaxOrderCount();

    Optional<Statistics> findByCoffeeName(String coffeeName);
}
