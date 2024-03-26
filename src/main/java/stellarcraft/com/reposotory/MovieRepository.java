package stellarcraft.com.reposotory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stellarcraft.com.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
