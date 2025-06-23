package com.project.service;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.project.model.Projekt;
import java.util.List;
public interface ProjektService {
    // istniejące metody
    Page<Projekt> getProjekty(Pageable pageable);
    Optional<Projekt> getProjekt(Integer projektId);
    Projekt setProjekt(Projekt projekt);
    void deleteProjekt(Integer projektId);
    Page<Projekt> searchByNazwa(String nazwa, Pageable pageable);

    // Dodaj tę metodę
    List<Projekt> getProjekty();
}