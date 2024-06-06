package com.ebanx.atm.simulator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventType {
    DEPOSIT_EVENT("deposit"),
    WITHDRAW_EVENT("withdraw"),
    TRANSFER_EVENT("transfer");

    private final String name;

    public static EventType fromString(String name) {
        for (EventType eventType : EventType.values()) {
            if (eventType.getName().equalsIgnoreCase(name)) {
                return eventType;
            }
        }
        throw new IllegalArgumentException("Unknown event type: " + name);
    }

}
