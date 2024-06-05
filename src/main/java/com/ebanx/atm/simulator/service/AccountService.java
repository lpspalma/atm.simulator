package com.ebanx.atm.simulator.service;

import com.ebanx.atm.simulator.entity.Account;
import com.ebanx.atm.simulator.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account getAccountBalanceById(String accountId) {
        Account account;
        try{
            account = accountRepository.findById(Long.parseLong(accountId)).orElseThrow();
        }catch (NoSuchElementException e){
            log.error("Account not exist");
            return null;
        }

        return account;
    }

    public Account createNewAccount(Long id, int balance) {
        return accountRepository.save(
                Account.builder()
                        .id(id)
                        .balance(balance)
                        .build());
    }
}
