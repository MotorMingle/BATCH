package fr.motormingle.reader;

import fr.motormingle.entity.Position;
import fr.motormingle.entity.TreatmentStatus;
import fr.motormingle.repository.PositionRepository;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class PositionReader implements ItemReader<List<Position>>, StepExecutionListener {

    @Autowired
    private PositionRepository positionRepository;

    private LocalDateTime localDateTime;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        localDateTime = stepExecution.getJobParameters().getLocalDateTime("localDateTime");
    }

    @Override
    public List<Position> read() {
        List<Position> result = positionRepository.findAllById_DateAndTreatmentStatus(localDateTime, TreatmentStatus.NOT_TREATED);
        result.forEach(r -> r.setTreatmentStatus(TreatmentStatus.MATCHED));
        positionRepository.saveAll(result);
        return result.isEmpty() ? null : result;
    }

}
