package com.example.devopsproject.integration;

import com.example.devopsproject.models.Owner;
import com.example.devopsproject.repositories.OwnerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration-test") // Use your integration test profile
public class OwnerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAddOwner() throws Exception {
        Owner newOwner = new Owner();
        newOwner.setName("John Doe");
        newOwner.setAge(12);

        String ownerJson = objectMapper.writeValueAsString(newOwner);

        // Send POST request to create a new owner
        mockMvc.perform(post("/owner/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ownerJson))
                .andExpect(status().isOk());

        // Retrieve the owner from the database and verify
        List<Owner> owners = ownerRepository.findAll();
        Optional<Owner> retrievedOwner = owners.stream()
                                               .filter(owner -> "John Doe".equals(owner.getName()))
                                               .findFirst();
        assertTrue(retrievedOwner.isPresent(), "Owner should exist in the database.");
        assertEquals("John Doe", retrievedOwner.get().getName(), "Owner name should match.");
        assertEquals("12", retrievedOwner.get().getAge(), "Owner address should match.");
    }
}