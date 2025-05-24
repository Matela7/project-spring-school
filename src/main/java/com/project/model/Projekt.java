package com.project.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Set;
import java.util.List;
import java.sql.Date;


@Entity
@Table(name="projekt") //TODO Indeksować kolumny, które są najczęściej wykorzystywane do wyszukiwania projektów
public class Projekt {
    @Id
    @GeneratedValue
    @Column(name="projekt_id") //tylko jeżeli nazwa kolumny w bazie danych ma być inna od nazwy zmiennej
    private Integer projektId;

    @NotBlank(message = "Pole nazwa nie może być puste!")
    @Size(min = 3, max = 50, message = "Nazwa musi zawierać od {min} do {max} znaków!")
    @Column(name="nazwa", nullable = false, length = 50)
    private String nazwa;

    @Column(name="opis", length = 1000)
    private String opis;

    @Column(name="dataczas_utworzenia")
    private Date dataUtworzenia;

    @Column(name="dataczas_oddania")
    private Date dataOddania;

    @ManyToMany
    @JoinTable(name = "projekt_student",
            joinColumns = {@JoinColumn(name="projekt_id")},
            inverseJoinColumns = {@JoinColumn(name="student_id")})
    private Set<Student> studenci;

    @OneToMany(mappedBy = "projekt")
    private List<Zadanie> zadania;

    // Konstruktor domyślny
    public Projekt() {
    }

    // Konstruktor z parametrami nazwa i opis
    public Projekt(String nazwa, String opis) {
        this.nazwa = nazwa;
        this.opis = opis;
    }

    // Gettery i Settery
    public Integer getProjektId() {
        return projektId;
    }

    public void setProjektId(Integer projektId) {
        this.projektId = projektId;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Date getDataUtworzenia() {
        return dataUtworzenia;
    }

    public void setDataUtworzenia(Date dataUtworzenia) {
        this.dataUtworzenia = dataUtworzenia;
    }

    public Date getDataOddania() {
        return dataOddania;
    }

    public void setDataOddania(Date dataOddania) {
        this.dataOddania = dataOddania;
    }

    public Set<Student> getStudenci() {
        return studenci;
    }

    public void setStudenci(Set<Student> studenci) {
        this.studenci = studenci;
    }

    public List<Zadanie> getZadania() {
        return zadania;
    }

    public void setZadania(List<Zadanie> zadania) {
        this.zadania = zadania;
    }
}