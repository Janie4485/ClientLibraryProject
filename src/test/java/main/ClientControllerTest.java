package main;

import main.model.ClientUser;
import main.repository.AnotherClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnotherClientRepository clientRepository;

    private ClientUser clientUser;

    @BeforeEach
    public void setup() {
        clientUser = new ClientUser();
        clientUser.setId(1);
        clientUser.setName("Jane");
        clientUser.setHetonghao("123456789");
    }

    @Test
    public void testGetAllClients() throws Exception {
        when(clientRepository.findAll()).thenReturn(Arrays.asList(clientUser));

        mockMvc.perform(MockMvcRequestBuilders.get("/clients/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Jane"))
                .andExpect(jsonPath("$[0].hetonghao").value("123456789"));
    }

    @Test
    public void testGetClientById() throws Exception {
        when(clientRepository.findById(1)).thenReturn(Optional.of(clientUser));

        mockMvc.perform(MockMvcRequestBuilders.get("/clients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane"))
                .andExpect(jsonPath("$.hetonghao").value("123456789"));
    }

    @Test
    public void testCreateClient() throws Exception {
        when(clientRepository.save(any(ClientUser.class))).thenReturn(clientUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/clients/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Jane\",\"hetonghao\":\"123456789\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }


    @Test
    public void testDeleteClient() throws Exception {
        doNothing().when(clientRepository).deleteById(1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/clients/1"))
                .andExpect(status().isOk());

        verify(clientRepository, times(1)).deleteById(1);
    }
}
