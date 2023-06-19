package fr.motormingle.repository;

import fr.motormingle.entity.Encounter;
import fr.motormingle.entity.UserPair;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EncounterRepository extends JpaRepository<Encounter, UserPair> {


    @Override
    <S extends Encounter> @NotNull List<S> saveAll(@NotNull Iterable<S> entities);
}
