package com.ccb.referencedata.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReferenceDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void returnsAllProvinces() throws Exception {
        mockMvc.perform(get("/reference-data/provinces"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(13))
                .andExpect(jsonPath("$[?(@.code == 'ON')].name").value("Ontario"))
                .andExpect(jsonPath("$[?(@.code == 'ON')].countryCode").value("CA"));
    }

    @Test
    void returnsAllCreditExceptions() throws Exception {
        mockMvc.perform(get("/reference-data/exceptions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(6))
                .andExpect(jsonPath("$[?(@.code == 'EXC-DSCR')].severity").value("HIGH"))
                .andExpect(jsonPath("$[?(@.code == 'EXC-DSCR')].category").value("CREDIT"));
    }

    @Test
    void unknownPathReturnsUniformErrorShape() throws Exception {
        mockMvc.perform(get("/reference-data/nonsense"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.path").value("/reference-data/nonsense"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}
