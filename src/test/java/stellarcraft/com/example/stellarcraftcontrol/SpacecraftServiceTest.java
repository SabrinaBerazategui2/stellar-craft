package stellarcraft.com.example.stellarcraftcontrol;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import stellarcraft.com.model.Spacecraft;
import stellarcraft.com.reposotory.SpaceCraftRepository;
import stellarcraft.com.service.impl.SpacecraftServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpacecraftServiceImplTest {

    @Mock
    private SpaceCraftRepository spaceCraftRepository;

    @InjectMocks
    private SpacecraftServiceImpl spacecraftService;

    @Test
    public void shouldReturnAllSpacecrafts() {

        List<Spacecraft> spacecraftList = new ArrayList<>();
        spacecraftList.add(new Spacecraft());
        Page<Spacecraft> page = mock(Page.class);
        when(page.getContent()).thenReturn(spacecraftList);
        when(spaceCraftRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<Spacecraft> result = spacecraftService.getAllSpacecrafts(Pageable.unpaged());
        assertEquals(spacecraftList, result.getContent());
    }

    @Test
    public void shouldReturnSpacecraftById() {

        Spacecraft spacecraft = new Spacecraft();
        spacecraft.setId(1L);
        when(spaceCraftRepository.findById(1L)).thenReturn(Optional.of(spacecraft));

        Spacecraft result = spacecraftService.getSpacecraftById(1L);
        assertEquals(spacecraft, result);
    }

    @Test
    public void shouldThrowNoSuchElementExceptionWhenSpacecraftNotFoundById() {

        when(spaceCraftRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> spacecraftService.getSpacecraftById(1L));
    }
}
