package stellarcraft.com.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import stellarcraft.com.model.Movie;
import stellarcraft.com.model.Serie;
import stellarcraft.com.model.Spacecraft;

import java.util.List;


public interface SpacecraftService {
    Page<Spacecraft> getAllSpacecrafts(Pageable pageable);

    Spacecraft getSpacecraftById(Long id);

    Spacecraft getSpacecraftByName(String name);

    Spacecraft createSpacecraft(Spacecraft spacecraft);

    Spacecraft updateSpacecraft(Long id, Spacecraft spacecraft);

    void deleteSpacecraft(Long id);

    List<Spacecraft> searchByNameContaining(String keyword);

    Spacecraft createSpacecraftWithMovieAndSerie(Spacecraft spacecraft, Movie movie, Serie serie);
}

