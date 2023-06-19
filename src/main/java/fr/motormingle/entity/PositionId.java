package fr.motormingle.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Embeddable
public class PositionId implements Serializable {
    @Serial
    private static final long serialVersionUID = 4877203349573100342L;

    @Column(name = "user_id", nullable = false, length = 50)
    private UUID userId;

    @Column(name = "date", insertable = false, nullable = false)
    private LocalDateTime date;

    /**
     * @param o Object to compare
     * @return true if both of user IDs are the same either way
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PositionId entity = (PositionId) o;
        return Objects.equals(this.userId, entity.userId) &&
                Objects.equals(this.date, entity.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, date);
    }
}
