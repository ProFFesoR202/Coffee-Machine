package com.example.coffeemachine.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "statistics")
@Data
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String coffeeName;

    private int orderCount;

}
