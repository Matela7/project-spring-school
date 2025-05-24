package com.project.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.sql.Date;
import jakarta.validation.constraints.Min;
import java.util.List;
@Entity
@Table(name="zadanie")
public class Zadanie {
    @Id
    @GeneratedValue
    @Column(name="zadanie_id")
    private Integer zadanieId;
    @NotBlank(message = "Pole nazwa nie może być puste!")
    @Size(min = 3, max = 50, message = "Nazwa musi zawierać od {min} do {max} znaków!")
    @Column(name="nazwa", nullable = false, length = 50)
    private String nazwa;
    @NotNull(message = "Pole kolejność nie może być puste!")
    @Min(value = 1, message = "Kolejność musi być liczbą większą od {value}!")
    @Column(name="kolejnosc", nullable = false, length = 50)
    private Integer kolejnosc;
    @Column(name="opis", length = 1000)
    private String opis;
    @Column(name="dataczas_oddania")
    private Date dataOddania;
    @ManyToOne
    @JoinColumn(name = "projekt_id")
    private Projekt projekt;

    // Pusty konstruktor
    public Zadanie() {
    }

    // Konstruktor ze zmiennymi nazwa, opis i kolejnosc
    public Zadanie(String nazwa, String opis, Integer kolejnosc) {
        this.nazwa = nazwa;
        this.opis = opis;
        this.kolejnosc = kolejnosc;
    }

    // Gettery i Settery
    public Integer getZadanieId() {
        return zadanieId;
    }

    public void setZadanieId(Integer zadanieId) {
        this.zadanieId = zadanieId;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Integer getKolejnosc() {
        return kolejnosc;
    }

    public void setKolejnosc(Integer kolejnosc) {
        this.kolejnosc = kolejnosc;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Date getDataOddania() {
        return dataOddania;
    }

    public void setDataOddania(Date dataOddania) {
        this.dataOddania = dataOddania;
    }

    public Projekt getProjekt() {
        return projekt;
    }

    public void setProjekt(Projekt projekt) {
        this.projekt = projekt;
    }
}

