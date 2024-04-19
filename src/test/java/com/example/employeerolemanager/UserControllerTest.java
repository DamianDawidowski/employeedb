package com.example.employeerolemanager;

import com.example.employeerolemanager.dtos.RegisterUserDto;
import com.example.employeerolemanager.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void testRegisterUser() throws Exception {
        RegisterUserDto userDto = new RegisterUserDto();
        userDto.setEmail("test@example.com");
        userDto.setPassword("Sdsdadf33");
        userDto.setFullName("DamianD");
   

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@example.com")); 
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testUpgradeToSenior() throws Exception {
        String fullName = "DamianDD";
        
        RegisterUserDto userDto = new RegisterUserDto();
        userDto.setEmail("test2@example.com");
        userDto.setPassword("ds33sds");
        userDto.setFullName(fullName);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/makeSenior/{fullName}", fullName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
 
    }
  
}