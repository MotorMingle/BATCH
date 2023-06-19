package fr.motormingle.writer;

import fr.motormingle.entity.Encounter;
import fr.motormingle.repository.EncounterRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EncounterWriter implements ItemWriter<List<Encounter>> {

    @Autowired
    private EncounterRepository encounterRepository;

    @Override
    public void write(@NotNull Chunk<? extends List<Encounter>> encounters) {
        if (encounters.getItems().size() == 0) {
            return;
        }
        encounterRepository.saveAll(encounters.getItems().stream().flatMap(List::stream).toList());
    }
}
