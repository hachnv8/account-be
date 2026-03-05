package com.hacheery.accountbe.service.impl;

import com.hacheery.accountbe.dto.AccountRequestDTO;
import com.hacheery.accountbe.dto.AccountResponseDTO;
import com.hacheery.accountbe.entity.Account;
import com.hacheery.accountbe.entity.Project;
import com.hacheery.accountbe.repository.AccountRepository;
import com.hacheery.accountbe.repository.ProjectRepository;
import com.hacheery.accountbe.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ProjectRepository projectRepository;

    @Override
    public List<AccountResponseDTO> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AccountResponseDTO getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
        return mapToDTO(account);
    }

    @Override
    @Transactional
    public AccountResponseDTO createAccount(AccountRequestDTO request) {
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + request.getProjectId()));
        
        Account account = new Account();
        account.setProjectId(request.getProjectId());
        account.setName(request.getName());
        account.setUrl(request.getUrl());
        account.setPlatformIcon(request.getPlatformIcon());
        account.setTags(request.getTags());
        account.setLoginDetails(request.getLoginDetails());
        account.setLastUpdated(LocalDate.now());

        Account savedAccount = accountRepository.save(account);

        project.setCount(project.getCount() + 1);
        project.setLastUpdated(LocalDate.now());
        projectRepository.save(project);

        return mapToDTO(savedAccount);
    }

    @Override
    @Transactional
    public AccountResponseDTO updateAccount(Long id, AccountRequestDTO request) {
        Account existing = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));

        if (!existing.getProjectId().equals(request.getProjectId())) {
            // Project changed
            Project oldProject = projectRepository.findById(existing.getProjectId()).orElse(null);
            if (oldProject != null && oldProject.getCount() > 0) {
                oldProject.setCount(oldProject.getCount() - 1);
                oldProject.setLastUpdated(LocalDate.now());
                projectRepository.save(oldProject);
            }

            Project newProject = projectRepository.findById(request.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project not found with id: " + request.getProjectId()));
            newProject.setCount(newProject.getCount() + 1);
            newProject.setLastUpdated(LocalDate.now());
            projectRepository.save(newProject);
            
            existing.setProjectId(request.getProjectId());
        }

        existing.setName(request.getName());
        existing.setUrl(request.getUrl());
        existing.setPlatformIcon(request.getPlatformIcon());
        existing.setTags(request.getTags());
        existing.setLoginDetails(request.getLoginDetails());
        existing.setLastUpdated(LocalDate.now());

        return mapToDTO(accountRepository.save(existing));
    }

    @Override
    @Transactional
    public void deleteAccount(Long id) {
        Account existing = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));

        Project project = projectRepository.findById(existing.getProjectId()).orElse(null);
        if (project != null && project.getCount() > 0) {
            project.setCount(project.getCount() - 1);
            project.setLastUpdated(LocalDate.now());
            projectRepository.save(project);
        }

        accountRepository.deleteById(id);
    }

    private AccountResponseDTO mapToDTO(Account account) {
        AccountResponseDTO dto = new AccountResponseDTO();
        dto.setId(account.getId());
        dto.setProjectId(account.getProjectId());
        dto.setName(account.getName());
        dto.setUrl(account.getUrl());
        dto.setPlatformIcon(account.getPlatformIcon());
        dto.setTags(account.getTags());
        dto.setLoginDetails(account.getLoginDetails());
        dto.setLastUpdated(account.getLastUpdated());
        return dto;
    }
}
