package com.hacheery.accountbe.controller;

import com.hacheery.accountbe.entity.Account;
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

    /**
     * GET /api/account/list
     * Trả về mảng trực tiếp (không wrap trong object).
     */
    @GetMapping("/list")
    public ResponseEntity<@NonNull List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    /**
     * GET /api/account/list/{id}
     */
    @GetMapping("/list/{id}")
    public ResponseEntity<@NonNull Account> getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/account/list
     * Frontend gửi object, backend trả về object kèm id đã generate.
     */
    @PostMapping("/list")
    public ResponseEntity<@NonNull Account> createAccount(@RequestBody Account account) {
        return ResponseEntity.ok(accountService.createAccount(account));
    }

    /**
     * PUT /api/account/list/{id}
     * Frontend gửi object cập nhật, backend trả về object đã update.
     */
    @PutMapping("/list/{id}")
    public ResponseEntity<@NonNull Account> updateAccount(@PathVariable Long id, @RequestBody Account account) {
        return ResponseEntity.ok(accountService.updateAccount(id, account));
    }

    /**
     * DELETE /api/account/list/{id}
     * Trả về JSON message.
     */
    @DeleteMapping("/list/{id}")
    public ResponseEntity<@NonNull Map<String, String>> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok(Map.of("message", "Account deleted successfully"));
    }
}
