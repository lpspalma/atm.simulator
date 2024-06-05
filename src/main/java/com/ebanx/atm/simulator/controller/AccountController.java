package com.ebanx.atm.simulator.controller;

import com.ebanx.atm.simulator.entity.Account;
import com.ebanx.atm.simulator.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/balance")
    ResponseEntity<Object> getAccountBalance(@RequestParam String account_id){
        Account account = accountService.getAccountBalanceById(account_id);
        Map<String, Account> response = new HashMap<>();
        if(account != null){
            response.put("destination", account);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>("0", HttpStatus.NOT_FOUND);
    }
}
