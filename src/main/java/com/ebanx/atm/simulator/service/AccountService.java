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

    /**
     * Retrieves an account by its ID.
     *
     * @param accountId The ID of the account to retrieve.
     * @return The Account object if found, or null if not found.
     */
    public Account getAccountById(String accountId) {
        Account account;
        try {
            long id = Long.parseLong(accountId);
            account = accountRepository.findById(id).orElseThrow();
        } catch (NumberFormatException e) {
            log.error("Invalid account ID format: {}", accountId);
            return null;
        } catch (NoSuchElementException e) {
            log.error("Account with ID {} does not exist", accountId);
            return null;
        }

        return account;
    }

    /**
     * Creates a new account with the specified ID and balance.
     *
     * @param id      The ID of the new account.
     * @param balance The initial balance of the new account.
     * @return The created Account object.
     */
    public Account createNewAccount(Long id, int balance) {
        return accountRepository.save(
                Account.builder()
                        .id(id)
                        .balance(balance)
                        .build());
    }

    public void deleteAllAccounts() {
        accountRepository.deleteAll();
    }
}
