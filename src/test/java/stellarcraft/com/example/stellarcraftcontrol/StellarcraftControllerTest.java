package stellarcraft.com.example.stellarcraftcontrol;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import stellarcraft.com.common.StellarcraftExceptionHandler;
import stellarcraft.com.controller.SpacecraftController;
import stellarcraft.com.service.SpacecraftService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class SpacecraftControllerTest {

    @Mock
    private SpacecraftService spacecraftService;

    @Mock
    private StellarcraftExceptionHandler exceptionHandler;

    @InjectMocks
    private SpacecraftController spacecraftController;

    private MockMvc mockMvc;


    @Test
    public void shouldDeleteSpacecraft() throws Exception {

        Long id = 1L;
        mockMvc = MockMvcBuilders.standaloneSetup(spacecraftController).build();
        mockMvc.perform(delete("/api/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldSearchSpacecraftsByNameContaining() throws Exception {
        String keyword = "keyword";

        when(spacecraftService.searchByNameContaining(keyword)).thenReturn(List.of());
        mockMvc = MockMvcBuilders.standaloneSetup(spacecraftController).build();
        mockMvc.perform(get("/api/search")
                        .param("keyword", keyword)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
