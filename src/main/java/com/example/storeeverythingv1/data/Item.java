package com.example.storeeverythingv1.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 3, max = 20, message = "Nazwa powinna składać się od 3 do 20 liter")
    private String name;

    @Size(min = 5, max = 500, message = "Opis powinien składać się z od 5 do 500 liter")
    private String description;

    @Future(message = "Data powinna być przyszła")
    @NotNull(message = "Uzupełnij datę")
    private LocalDateTime deadline;

    @NotNull
    private Boolean isDone = false;

    @ManyToOne
    private Category category;

   // @NotNull
    private  Long user_id;

    public Item(String name, String description, LocalDateTime deadline, Boolean isDone, Category category, Long user_id) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.isDone = isDone;
        this.category = category;
        this.user_id = user_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(Boolean done) {
        isDone = done;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long id) {
        user_id = id;
    }
}
