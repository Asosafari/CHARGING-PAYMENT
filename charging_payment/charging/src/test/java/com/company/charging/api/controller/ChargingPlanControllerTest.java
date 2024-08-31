package com.company.charging.api.controller;

import com.company.charging.api.dto.ChargingPlanDTO;

import com.company.charging.api.service.ChargingService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ChargingPlanController.class)
class ChargingPlanControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private ChargingPlanDTO chargingPlanDTO;

    @MockBean
    ChargingService planService;

    @Captor
    ArgumentCaptor<Long> idArgumentCaptor;

    @Captor
    ArgumentCaptor<ChargingPlanDTO> userDTOArgumentCaptor;

    @BeforeEach
    void setUp() {
        chargingPlanDTO = ChargingPlanDTO.builder()
                .planName("premiumA")
                .pricePerUnit(new BigDecimal(20))
                .ratePerUnit(new BigDecimal(2))
                .id(1L)
                .build();
    }

    @Test
    void listOfUsers() throws Exception {
        Page<ChargingPlanDTO> plans = new PageImpl<>(Collections.singletonList(chargingPlanDTO));
        given(planService.listOfChargingPlan(any(),any(),any(),any(),any())).willReturn(plans);
        mockMvc.perform(get("/api/v1/plans")
                        .queryParam("graterThanRate","")
                        .queryParam("lessThanPricePerUnit","")
                        .queryParam("pageNumber", String.valueOf(0))
                        .queryParam("pageSize", String.valueOf(10))
                        .queryParam("sortProperty", chargingPlanDTO.getPlanName())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].planName").value("premiumA"));

    }

}