package fr.motormingle.model;

import lombok.*;
import org.springframework.batch.item.Chunk;

import java.time.LocalDate;
import java.util.UUID;
import java.util.function.Consumer;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Encounter extends Chunk<Encounter> {
    private UUID userId1;
    private UUID userId2;
    private String hash;
    private LocalDate date;
    private Integer count;
    private String status;

    @Override
    public void forEach(Consumer<? super Encounter> action) {
        super.forEach(action);
    }
}
