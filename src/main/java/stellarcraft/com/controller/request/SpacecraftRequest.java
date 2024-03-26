package stellarcraft.com.controller.request;

import stellarcraft.com.model.Movie;
import stellarcraft.com.model.Serie;
import stellarcraft.com.model.Spacecraft;

public class SpacecraftRequest {
    private Spacecraft spacecraft;
    private Movie movie;
    private Serie serie;

    public Spacecraft getSpacecraft() {
        return spacecraft;
    }

    public void setSpacecraft(Spacecraft spacecraft) {
        this.spacecraft = spacecraft;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }
}
