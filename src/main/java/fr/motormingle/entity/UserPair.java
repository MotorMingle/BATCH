package fr.motormingle.entity;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class UserPair implements Serializable {
    @Serial
    private static final long serialVersionUID = 5225502204442041057L;

    @Column(name = "user_id_1", nullable = false, length = 50)
    private UUID userId1;

    @Column(name = "user_id_2", nullable = false, length = 50)
    private UUID userId2;

    /**
     * @param o Object to compare
     * @return true if both of user IDs are the same either way
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserPair entity = (UserPair) o;
        return Objects.equals(this.userId1, entity.userId1) &&
                Objects.equals(this.userId2, entity.userId2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId1, userId2);
    }

}
