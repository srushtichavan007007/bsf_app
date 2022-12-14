package com.bsf.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bsf.app.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
