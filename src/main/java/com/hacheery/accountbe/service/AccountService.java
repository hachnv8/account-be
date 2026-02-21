package com.hacheery.accountbe.service;

import com.hacheery.accountbe.entity.Account;
import java.util.List;
import java.util.Optional;

public interface AccountService {
    List<Account> getAllAccounts();
    
    Optional<Account> getAccountById(Long id);
    
    Account createAccount(Account account);
    
    Account updateAccount(Long id, Account account);
    
    void deleteAccount(Long id);
}
