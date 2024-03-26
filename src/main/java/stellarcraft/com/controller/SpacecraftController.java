package stellarcraft.com.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stellarcraft.com.common.BadRequestException;
import stellarcraft.com.common.ResourceNotFoundException;
import stellarcraft.com.common.StellarcraftExceptionHandler;
import stellarcraft.com.controller.request.SpacecraftRequest;
import stellarcraft.com.model.Movie;
import stellarcraft.com.model.Serie;
import stellarcraft.com.model.Spacecraft;
import stellarcraft.com.service.SpacecraftService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
@Tag(name = "Spacecraft", description = "Endpoints para operaciones relacionadas con naves espaciales")
public class SpacecraftController {

    @Autowired
    private SpacecraftService spacecraftService;

    @Autowired
    private StellarcraftExceptionHandler exceptionHandler;

    @Operation(summary = "Obtener todas las naves espaciales")
    @GetMapping
    public ResponseEntity<Page<Spacecraft>> getAllSpacecrafts() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Spacecraft> spacecrafts = spacecraftService.getAllSpacecrafts(pageable);
        return new ResponseEntity<>(spacecrafts, HttpStatus.OK);
    }

    @Operation(summary = "Obtener una nave espacial por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getSpacecraftById(@PathVariable Long id) {
        try {
            Spacecraft spacecraft = spacecraftService.getSpacecraftById(id);
            return new ResponseEntity<>(spacecraft, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return exceptionHandler.handleBadRequestException(new BadRequestException("El ID no puede ser negativo"));
        } catch (NoSuchElementException e) {
            return exceptionHandler.handleResourceNotFoundException(new ResourceNotFoundException("Nave espacial no encontrada"));
        }
    }
    @Operation(summary = "Obtener una nave espacial por su nombre")
    @GetMapping("/byname/{name}")
    public ResponseEntity<?> getSpacecraftByName(@PathVariable String name) {
        try {
            Spacecraft spacecraft = spacecraftService.getSpacecraftByName(name);
            return new ResponseEntity<>(spacecraft, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return exceptionHandler.handleResourceNotFoundException(new ResourceNotFoundException("Nave espacial no encontrada"));
        }
    }

    @Operation(summary = "Crear una nave espacial")
    @PostMapping("/spacecraft")
    public ResponseEntity<?> createSpacecraft(@RequestBody SpacecraftRequest spacecraftRequest) {
        try {
            Spacecraft spacecraft = spacecraftRequest.getSpacecraft();
            Movie movie = spacecraftRequest.getMovie();
            Serie serie = spacecraftRequest.getSerie();

            Spacecraft createdSpacecraft = spacecraftService.createSpacecraftWithMovieAndSerie(spacecraft, movie, serie);

            return new ResponseEntity<>(createdSpacecraft, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return exceptionHandler.handleBadRequestException(new BadRequestException("Se proporcionó un argumento no válido para la creación de la nave espacial"));
        } catch (Exception e) {
            return new ResponseEntity<>("Ocurrió un error al crear la nave espacial: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Actualizar una nave espacial")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSpacecraft(@PathVariable Long id, @RequestBody Spacecraft spacecraft) {
        try {
            Spacecraft updatedSpacecraft = spacecraftService.updateSpacecraft(id, spacecraft);
            return new ResponseEntity<>(updatedSpacecraft, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return exceptionHandler.handleResourceNotFoundException(new ResourceNotFoundException("Nave espacial no encontrada"));
        }
    }

    @Operation(summary = "Eliminar una nave espacial")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSpacecraft(@PathVariable Long id) {
        try {
            spacecraftService.deleteSpacecraft(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            return exceptionHandler.handleResourceNotFoundException(new ResourceNotFoundException("Nave espacial no encontrada"));
        }
    }
    @Operation(summary = "Buscar una nave espacial por una keyword específica")
    @Cacheable("spacecrafts")
    @GetMapping("/search")
    public ResponseEntity<List<Spacecraft>> searchSpacecraftsByNameContaining(@RequestParam String keyword) {
        List<Spacecraft> matchingSpacecrafts = spacecraftService.searchByNameContaining(keyword);
        if (matchingSpacecrafts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(matchingSpacecrafts, HttpStatus.OK);
        }
    }
}

