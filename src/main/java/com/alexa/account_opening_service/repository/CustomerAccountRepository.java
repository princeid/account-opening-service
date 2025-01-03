package com.alexa.account_opening_service.repository;

import com.alexa.account_opening_service.entity.AccountRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAccountRepository extends JpaRepository<AccountRequest, Long> {
    AccountRequest findByRequestId(String requestId);
}
