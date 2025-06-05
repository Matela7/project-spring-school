package com.project.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Projekt {

    private Integer projektId;

    @NotBlank(message = "Pole nazwa nie może być puste\\!")
    @Size(min = 3, max = 50, message = "Nazwa musi zawierać od {min} do {max} znaków\\!")
    private String nazwa;

    private String opis;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataUtworzenia;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataOddania;

    private Set<Student> studenci;

    private List<Zadanie> zadania;
}