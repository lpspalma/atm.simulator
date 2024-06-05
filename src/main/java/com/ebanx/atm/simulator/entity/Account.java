package com.ebanx.atm.simulator.entity;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
public class Account {
    private Long id;
    private int balance;
}
