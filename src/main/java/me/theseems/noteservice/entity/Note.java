package me.theseems.noteservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString(callSuper = true)
public class Note extends BaseEntity {
    @Column(nullable = false)
    private String title;

    @Column(length = 10_000, nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Note() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Note note = (Note) o;
        return getId() != null && Objects.equals(getId(), note.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
