package fr.motormingle.batch;

import fr.motormingle.model.Encounter;
import fr.motormingle.model.Position;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan(basePackages = {"fr.motormingle.batch", "fr.motormingle.reader", "fr.motormingle.writer", "fr.motormingle.processor", "fr.motormingle.mapper"})
public class BatchConfiguration {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private ItemReader<List<Position>> reader;

    @Autowired
    private ItemProcessor<List<Position>, List<Encounter>> processor;

    @Autowired
    private ItemWriter<List<Encounter>> writer;


    @Bean
    public void run() throws Exception {
        LocalDateTime startDateTime = LocalDateTime.now().minusDays(1).withHour(18).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endDateTime = LocalDateTime.now().withHour(18).withMinute(0).withSecond(0).withNano(0);

        List<LocalDateTime> localDateTimes = generateDateTimeList(startDateTime, endDateTime);
        JobParametersBuilder parametersBuilder = new JobParametersBuilder();
        parametersBuilder.addString("runId", new RunIdIncrementer().toString());
        for (LocalDateTime localDateTime : localDateTimes) {
            parametersBuilder.addLocalDateTime("localDateTime", localDateTime);
            jobLauncher.run(
                    job1(),
                    parametersBuilder.toJobParameters()
            );
        }
    }

    private List<LocalDateTime> generateDateTimeList(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<LocalDateTime> dateTimeList = new ArrayList<>();
        LocalDateTime currentDateTime = startDateTime;

        while (currentDateTime.isBefore(endDateTime) || currentDateTime.equals(endDateTime)) {
            dateTimeList.add(currentDateTime);
            currentDateTime = currentDateTime.plusSeconds(20);
        }

        return dateTimeList;
    }

    private Job job1() {
        return new JobBuilder("job1", jobRepository)
                .start(step1())
                .build();
    }

    private Step step1() {
        return new StepBuilder("step1", jobRepository)
                .<List<Position>, List<Encounter>>chunk(1, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}