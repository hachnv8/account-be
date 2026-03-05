package com.hacheery.accountbe.repository;

import com.hacheery.accountbe.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByProjectId(Long projectId);
}
