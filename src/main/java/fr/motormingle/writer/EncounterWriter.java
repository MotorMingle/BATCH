package fr.motormingle.writer;

import fr.motormingle.model.Encounter;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.List;

@Component
public class EncounterWriter implements ItemWriter<List<Encounter>> {

    @Autowired
    private DataSource datasource;

    @Override
    public void write(@NotNull Chunk<? extends List<Encounter>> encounters) {
        if (encounters.getItems().size() == 0) {
            return;
        }
        JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
        encounters.getItems().get(0).forEach((encounter) -> jdbcTemplate.update("INSERT INTO encounter(hash, count, date, status, user_id_1, user_id_2) " +
                        "VALUES (?, ?, ?, ?, ?, ?) " +
                        "ON CONFLICT ON CONSTRAINT pk_encounter " +
                        "DO UPDATE SET count = (SELECT count FROM encounter WHERE user_id_1 = ? AND user_id_2 = ?) + 1, date = ?, hash = ?",
                encounter.getHash(),
                encounter.getCount(),
                Timestamp.valueOf(encounter.getDate().atStartOfDay()),
                encounter.getStatus(),
                encounter.getUserId1(),
                encounter.getUserId2(),
                encounter.getUserId1(),
                encounter.getUserId2(),
                Timestamp.valueOf(encounter.getDate().atStartOfDay()),
                encounter.getHash()));
    }
}
