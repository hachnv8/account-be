package com.hacheery.accountbe.service;

import com.hacheery.accountbe.entity.Account;
import com.hacheery.accountbe.dto.AccountRequestDTO;
import com.hacheery.accountbe.dto.AccountResponseDTO;
import java.util.List;

public interface AccountService {
    List<AccountResponseDTO> getAllAccounts();
    
    AccountResponseDTO getAccountById(Long id);
    
    AccountResponseDTO createAccount(AccountRequestDTO request);
    
    AccountResponseDTO updateAccount(Long id, AccountRequestDTO request);
    
    void deleteAccount(Long id);
}
