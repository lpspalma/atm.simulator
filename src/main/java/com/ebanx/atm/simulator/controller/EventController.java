package com.ebanx.atm.simulator.controller;

import com.ebanx.atm.simulator.entity.Account;
import com.ebanx.atm.simulator.service.EventHandler;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EventController {

    @Autowired
    private EventHandler eventHandler;

    @PostMapping("/event")
    ResponseEntity<Object> eventDeposit(@RequestBody JsonNode request) {
        Map<String, Account> response = eventHandler.eventHandler(request);
        return response != null && response.size() > 0 ?
                new ResponseEntity<>(response, HttpStatus.CREATED) :
                new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
