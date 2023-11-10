package com.w4t3rcs.todolist.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.type.NumericBooleanConverter;
import org.hibernate.type.YesNoConverter;
import org.hibernate.validator.constraints.Length;

import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "todo_schema", name = "todos")
@Entity
public class TodoList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    private Long id;
    @NotEmpty(message = "Name is invalid!")
    private String name;
    @Length(max = 1024, message = "Description is invalid!")
    private String description;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @NotNull(message = "Deadline date is invalid!")
    @Future(message = "Deadline date is invalid!")
    private Timestamp deadline;
    private String username;
    @Convert(converter = NumericBooleanConverter.class)
    @Column(name = "is_finished")
    private boolean finished;

    public TodoList(String name, String description, Timestamp deadline) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
    }

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<?> oEffectiveClass = object instanceof HibernateProxy ? ((HibernateProxy) object).getHibernateLazyInitializer().getPersistentClass() : object.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        TodoList todoList = (TodoList) object;
        return getId() != null && Objects.equals(getId(), todoList.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
