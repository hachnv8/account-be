package com.hacheery.accountbe.service.impl;

import com.hacheery.accountbe.entity.Account;
import com.hacheery.accountbe.repository.AccountRepository;
import com.hacheery.accountbe.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Account createAccount(Account account) {
        account.setLastUpdated(LocalDate.now());
        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(Long id, Account account) {
        Account existing = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));

        existing.setName(account.getName());
        existing.setUrl(account.getUrl());
        existing.setPlatformIcon(account.getPlatformIcon());
        existing.setTags(account.getTags());
        existing.setLoginDetails(account.getLoginDetails());
        existing.setLastUpdated(LocalDate.now());

        return accountRepository.save(existing);
    }

    @Override
    public void deleteAccount(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new RuntimeException("Account not found with id: " + id);
        }
        accountRepository.deleteById(id);
    }
}
