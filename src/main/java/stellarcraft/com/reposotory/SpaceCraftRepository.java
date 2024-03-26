package stellarcraft.com.reposotory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stellarcraft.com.model.Spacecraft;

import java.util.Optional;

@Repository
public interface SpaceCraftRepository extends JpaRepository<Spacecraft, Long> {

    Optional<Object> findByName(String name);
}