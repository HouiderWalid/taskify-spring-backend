package com.taskify.taskifyspringback;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.taskify.taskifyspringback.repositories.UserRepository;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
class TaskifySpringBackApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSignUp() throws Exception {

        String json = """
                {
                  "full_name": "John Doe",
                  "email": "john@example.com",
                  "password": "12345678"
                }
                """;

        mockMvc.perform(post("/api/sign_up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john@example.com"));

        assert userRepository.findByEmail("john@example.com").isPresent();
    }

}
