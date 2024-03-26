package stellarcraft.com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import stellarcraft.com.model.Movie;
import stellarcraft.com.model.Serie;
import stellarcraft.com.model.Spacecraft;
import stellarcraft.com.reposotory.MovieRepository;
import stellarcraft.com.reposotory.SerieRepository;
import stellarcraft.com.reposotory.SpaceCraftRepository;
import stellarcraft.com.service.SpacecraftService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class SpacecraftServiceImpl implements SpacecraftService {

    @Autowired
    private SpaceCraftRepository spaceCraftRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private SerieRepository serieRepository;

    @Override
    public Page<Spacecraft> getAllSpacecrafts(Pageable pageable) {
        return spaceCraftRepository.findAll(pageable);

    }
    @Override
    public Spacecraft getSpacecraftById(Long id) {
        if (id < 0) {
            throw new IllegalArgumentException("El ID no puede ser negativo");
        }

        Optional<Spacecraft> optionalSpacecraft = spaceCraftRepository.findById(id);
        return optionalSpacecraft.orElseThrow(() -> new NoSuchElementException("Spacecraft not found with id: " + id));
    }


    @Override
    public Spacecraft getSpacecraftByName(String name) {
        return (Spacecraft) spaceCraftRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Spacecraft not found with name: " + name));
    }

    @Override
    public Spacecraft createSpacecraft(Spacecraft spacecraft) {

        return spaceCraftRepository.save(spacecraft);
    }

    @Override
    public Spacecraft updateSpacecraft(Long id, Spacecraft spacecraft) {
        Optional<Spacecraft> optionalSpacecraft = spaceCraftRepository.findById(id);
        if (optionalSpacecraft.isPresent()) {
            spacecraft.setId(id);
            return spaceCraftRepository.save(spacecraft);
        } else {
            throw new NoSuchElementException("Spacecraft not found with id: " + id);
        }
    }

    @Override
    public void deleteSpacecraft(Long id) {
        Optional<Spacecraft> optionalSpacecraft = spaceCraftRepository.findById(id);
        if (optionalSpacecraft.isPresent()) {
            spaceCraftRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Spacecraft not found with id: " + id);
        }
    }

    @Override
    public List<Spacecraft> searchByNameContaining(String keyword) {
        List<Spacecraft> allSpacecrafts = spaceCraftRepository.findAll();
        return allSpacecrafts.stream()
                .filter(spacecraft -> spacecraft.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Serie saveSerie(Serie serie) {
        return serieRepository.save(serie);
    }

    public Spacecraft createSpacecraftWithMovieAndSerie(Spacecraft spacecraft, Movie movie, Serie serie) {

        if (movie != null && movie.getId() == null) {
            movie = saveMovie(movie);
        }

        if (serie!=null && serie.getId() == null) {
            serie = saveSerie(serie);
        }

        spacecraft.setMovie(movie);
        spacecraft.setSerie(serie);

        return spaceCraftRepository.save(spacecraft);
    }
}


