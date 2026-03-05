package com.hacheery.accountbe.controller;

import com.hacheery.accountbe.dto.AccountRequestDTO;
import com.hacheery.accountbe.dto.AccountResponseDTO;
import com.hacheery.accountbe.service.AccountService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/account")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/list")
    public ResponseEntity<@NonNull List<AccountResponseDTO>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<@NonNull AccountResponseDTO> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @PostMapping("/list")
    public ResponseEntity<@NonNull AccountResponseDTO> createAccount(@RequestBody AccountRequestDTO request) {
        return ResponseEntity.ok(accountService.createAccount(request));
    }

    @PutMapping("/list/{id}")
    public ResponseEntity<@NonNull AccountResponseDTO> updateAccount(@PathVariable Long id, @RequestBody AccountRequestDTO request) {
        return ResponseEntity.ok(accountService.updateAccount(id, request));
    }

    @DeleteMapping("/list/{id}")
    public ResponseEntity<@NonNull Map<String, String>> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok(Map.of("message", "Account deleted successfully"));
    }
}
