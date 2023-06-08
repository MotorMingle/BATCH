package fr.motormingle.reader;

import fr.motormingle.mapper.PositionRowMapper;
import fr.motormingle.model.Position;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class PositionReader implements ItemReader<List<Position>>, StepExecutionListener {

    @Autowired
    private PositionRowMapper positionRowMapper;

    @Autowired
    private DataSource datasource;

    private LocalDateTime localDateTime;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        localDateTime = stepExecution.getJobParameters().getLocalDateTime("localDateTime");
    }

    @Override
    public List<Position> read() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
        List<Position> result = jdbcTemplate.query("SELECT * FROM position WHERE date = to_timestamp(?, 'YYYY-MM-DD HH24:MI:SS.MS')", positionRowMapper, Timestamp.valueOf(localDateTime).toString());
        jdbcTemplate.update("DELETE FROM position WHERE date = to_timestamp(?, 'YYYY-MM-DD HH24:MI:SS.MS')", Timestamp.valueOf(localDateTime).toString());
        return result.size() == 0 ? null : result;
    }

}
