package com.example.desafioCaju.service;

import com.example.desafioCaju.model.Transaction;
import com.example.desafioCaju.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction createTransaction(Transaction transaction) {
        if (transaction.getAmount() < 0) {
            throw new IllegalArgumentException("Transaction amount cannot be negative");
        }
        return transactionRepository.save(transaction);
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    public Transaction updateTransaction(Long id, Transaction transactionDetails) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found for this id :: " + id));

        transaction.setAccountId(transactionDetails.getAccountId());
        transaction.setAmount(transactionDetails.getAmount());
        transaction.setMcc(transactionDetails.getMcc());
        transaction.setMerchant(transactionDetails.getMerchant());

        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found for this id :: " + id));
        transactionRepository.delete(transaction);
    }

    public String processTransaction(Transaction transaction) {
        // Lógica para processar a transação baseado nos desafios L1 a L3
        // Para simplicidade, assumimos um saldo fictício
        double foodBalance = 1000.0; // saldo para FOOD
        double mealBalance = 1000.0; // saldo para MEAL
        double cashBalance = 1000.0; // saldo para CASH

        if (transaction.getAmount() < 0) {
            return "{ \"code\": \"07\" }"; // Erro genérico
        }

        // L1. Autorizador simples
        String mcc = transaction.getMcc();
        double amount = transaction.getAmount();
        if (mcc.equals("5411") || mcc.equals("5412")) {
            if (foodBalance >= amount) {
                foodBalance -= amount;
                return "{ \"code\": \"00\" }"; // Transação aprovada
            } else {
                return "{ \"code\": \"51\" }"; // Saldo insuficiente
            }
        } else if (mcc.equals("5811") || mcc.equals("5812")) {
            if (mealBalance >= amount) {
                mealBalance -= amount;
                return "{ \"code\": \"00\" }"; // Transação aprovada
            } else {
                return "{ \"code\": \"51\" }"; // Saldo insuficiente
            }
        }

        // L2. Autorizador com fallback
        if (cashBalance >= amount) {
            cashBalance -= amount;
            return "{ \"code\": \"00\" }"; // Transação aprovada
        } else {
            return "{ \"code\": \"51\" }"; // Saldo insuficiente
        }
    }
}
