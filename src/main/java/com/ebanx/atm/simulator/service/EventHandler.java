package com.ebanx.atm.simulator.service;

import com.ebanx.atm.simulator.dto.EventDTO;
import com.ebanx.atm.simulator.dto.EventType;
import com.ebanx.atm.simulator.entity.Account;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.ebanx.atm.simulator.dto.EventType.DEPOSIT_EVENT;
import static com.ebanx.atm.simulator.dto.EventType.WITHDRAW_EVENT;

@Slf4j
@Service
public class EventHandler {
    @Autowired
    private EventService eventService;

    private final ObjectMapper mapper = new ObjectMapper();

    public Map<String, Account>  eventHandler(JsonNode request){
        EventDTO dto;
        try{
            dto = mapper.treeToValue(request, EventDTO.class);
            EventType eventType = EventType.fromString(dto.getType());
            switch (eventType) {
                case DEPOSIT_EVENT -> {
                    return eventService.getAccountBalanceById(DEPOSIT_EVENT, dto.getDestination(), dto.getAmount());
                }
                case WITHDRAW_EVENT -> {
                    return eventService.withdrawFromAccount(WITHDRAW_EVENT, dto.getDestination(), dto.getAmount());
                }
                default -> throw new IllegalArgumentException("Unexpected value: " + eventType);
            }
        }catch (IllegalArgumentException | JsonProcessingException e) {
            log.error(e.getMessage());
        }

        return null;
    }

}
