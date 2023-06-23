package fr.motormingle.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "position")
@Entity
public class Position {

    @EmbeddedId
    private PositionId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "treatment_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private TreatmentStatus treatmentStatus;

}
