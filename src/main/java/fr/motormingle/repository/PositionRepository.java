package fr.motormingle.repository;

import fr.motormingle.entity.Position;
import fr.motormingle.entity.PositionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<Position, PositionId> {
    List<Position> findAllById_Date(LocalDateTime date);

    void deleteAllById_Date(LocalDateTime date);
}
