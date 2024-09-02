package com.company.charging.api.controller;

import com.company.charging.api.dto.OrderDTO;
import com.company.charging.api.dto.TransactionDTO;
import com.company.charging.api.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListOfTransactions() throws Exception {
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .id(1L)
                .chargingPlanName("plan1")
                .userId(5L)
                .amount(new BigDecimal(200))
                .build();
        Page<TransactionDTO> page = new PageImpl<>(Collections.singletonList(transactionDTO));

        when(transactionService.getAllTransaction(any(), any(), any(), any()))
                .thenReturn(page);

        Page<TransactionDTO> result = transactionController.listOfTransactions(
                "user1", "plan1", 1, 10);

        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
        verify(transactionService, times(1))
                .getAllTransaction("user1", "plan1", 1, 10);


    }
    @Test
    void testGetTransaction() {
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .id(1L)
                .chargingPlanName("plan1")
                .userId(5L)
                .amount(new BigDecimal(200))
                .build();
        when(transactionService.getTransaction(anyLong()))
                .thenReturn(Optional.of(transactionDTO));

        Optional<TransactionDTO> result = transactionController.getTransaction(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(transactionDTO.getId());
        verify(transactionService, times(1)).getTransaction(1L);
    }

    @Test
    void testCreateTransaction() {
        OrderDTO orderDTO = new OrderDTO();
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .id(1L)
                .chargingPlanName("plan1")
                .userId(5L)
                .amount(new BigDecimal(200))
                .build();
        transactionDTO.setId(1L);

        when(transactionService.createTransaction(any(OrderDTO.class)))
                .thenReturn(Optional.of(transactionDTO));

        ResponseEntity<TransactionDTO> response = transactionController.createTransaction(orderDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().get("Location").get(0)).isEqualTo("/api/v1/transactions/1");
        assertThat(response.getBody()).isEqualTo(transactionDTO);
        verify(transactionService, times(1)).createTransaction(orderDTO);
    }

}