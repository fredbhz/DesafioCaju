package com.example.desafioCaju;

import com.example.desafioCaju.model.Transaction;
import com.example.desafioCaju.repository.TransactionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        transactionRepository.deleteAll();
    }

    @Test
    public void testCreateTransaction() throws Exception {
        Transaction transaction = new Transaction("123", 50.0, "5811", "BAKERY OF JOHN");
        String transactionJson = objectMapper.writeValueAsString(transaction);

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson))
                .andExpect(status().isOk())
                .andExpect(content().json("{ \"code\": \"00\" }"));
    }

    @Test
    public void testGetTransactionById() throws Exception {
        Transaction transaction = new Transaction("123", 50.0, "5811", "BAKERY OF JOHN");
        transaction = transactionRepository.save(transaction);

        mockMvc.perform(get("/transactions/" + transaction.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(transaction)));
    }

    @Test
    public void testUpdateTransaction() throws Exception {
        Transaction transaction = new Transaction("123", 50.0, "5811", "BAKERY OF JOHN");
        transaction = transactionRepository.save(transaction);

        transaction.setAmount(100.0);
        String transactionJson = objectMapper.writeValueAsString(transaction);

        mockMvc.perform(put("/transactions/" + transaction.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(transaction)));
    }

    @Test
    public void testDeleteTransaction() throws Exception {
        Transaction transaction = new Transaction("123", 50.0, "5811", "BAKERY OF JOHN");
        transaction = transactionRepository.save(transaction);

        mockMvc.perform(delete("/transactions/" + transaction.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testCreateTransactionWithError() throws Exception {
        // Test creation with negative amount
        Transaction transaction = new Transaction("123", -50.0, "5811", "BAKERY OF JOHN");
        String transactionJson = objectMapper.writeValueAsString(transaction);

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson))
                .andExpect(status().isBadRequest());
    }
}
