package fr.motormingle.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Position {
    private UUID userId;
    private double latitude;
    private double longitude;
    private LocalDateTime date;
}
