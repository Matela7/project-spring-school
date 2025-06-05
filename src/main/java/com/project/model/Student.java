package com.project.model;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder // Dodatkowa adnotacja generująca implementację wzorca projektowego Builder. Dzięki niej można tworzyć obiekty
// korzystając np. z Zadanie.builder().nazwa("Nazwa testowa").opis("Opis testowy").kolejnosc(1).build();
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class Student {


    private Integer studentId;
    @NotBlank(message = "Pole imię nie może być puste!")
    @Size(min = 2, max = 50, message = "Imię musi zawierać od {min} do {max} znaków!")
    private String imie;

    private String nazwisko;

    private String nrIndeksu;

    private String email;

    private Boolean stacjonarny;

    private Set<Projekt> projekty;

//    public Student() {}
//
//    public Student(String imie, String nazwisko, String nrIndeksu, String email, Boolean stacjonarny) {
//        this.imie = imie;
//        this.nazwisko = nazwisko;
//        this.nrIndeksu = nrIndeksu;
//        this.email = email;
//        this.stacjonarny = stacjonarny;
//    }
//
//
//    // Gettery i Settery
//    public Integer getStudentId() {
//        return studentId;
//    }
//
//    public void setStudentId(Integer studentId) {
//        this.studentId = studentId;
//    }
//
//    public String getImie() {
//        return imie;
//    }
//
//    public void setImie(String imie) {
//        this.imie = imie;
//    }
//
//    public String getNazwisko() {
//        return nazwisko;
//    }
//
//    public void setNazwisko(String nazwisko) {
//        this.nazwisko = nazwisko;
//    }
//
//    public String getNrIndeksu() {
//        return nrIndeksu;
//    }
//
//    public void setNrIndeksu(String nrIndeksu) {
//        this.nrIndeksu = nrIndeksu;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public Boolean getStacjonarny() {
//        return stacjonarny;
//    }
//
//    public void setStacjonarny(Boolean stacjonarny) {
//        this.stacjonarny = stacjonarny;
//    }
}
