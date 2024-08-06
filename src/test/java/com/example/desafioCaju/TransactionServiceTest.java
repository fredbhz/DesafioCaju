package com.example.desafioCaju;

import com.example.desafioCaju.model.Transaction;
import com.example.desafioCaju.repository.TransactionRepository;
import com.example.desafioCaju.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTransaction() {
        Transaction transaction = new Transaction("123", 50.0, "5811", "BAKERY OF JOHN");
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction result = transactionService.createTransaction(transaction);
        assertNotNull(result);
        assertEquals("123", result.getAccountId());
        assertEquals(50.0, result.getAmount());
    }

    @Test
    public void testGetTransactionById() {
        Transaction transaction = new Transaction("123", 50.0, "5811", "BAKERY OF JOHN");
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transaction));

        Optional<Transaction> result = transactionService.getTransactionById(1L);
        assertTrue(result.isPresent());
        assertEquals("123", result.get().getAccountId());
    }

    @Test
    public void testUpdateTransaction() {
        Transaction transaction = new Transaction("123", 50.0, "5811", "BAKERY OF JOHN");
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transaction));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        transaction.setAmount(100.0);
        Transaction result = transactionService.updateTransaction(1L, transaction);
        assertNotNull(result);
        assertEquals(100.0, result.getAmount());
    }

    @Test
    public void testDeleteTransaction() {
        Transaction transaction = new Transaction("123", 50.0, "5811", "BAKERY OF JOHN");
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transaction));
        doNothing().when(transactionRepository).delete(any(Transaction.class));

        transactionService.deleteTransaction(1L);
        verify(transactionRepository, times(1)).delete(transaction);
    }

    @Test
    public void testCreateTransactionWithError() {
        // Test creation with negative amount
        Transaction transaction = new Transaction("123", -50.0, "5811", "BAKERY OF JOHN");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.createTransaction(transaction);
        });

        String expectedMessage = "Transaction amount cannot be negative";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
