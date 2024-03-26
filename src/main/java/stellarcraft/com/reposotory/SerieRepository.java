package stellarcraft.com.reposotory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stellarcraft.com.model.Serie;

@Repository
public interface SerieRepository extends JpaRepository<Serie, Long> {
}
