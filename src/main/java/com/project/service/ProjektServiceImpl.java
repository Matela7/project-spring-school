package com.project.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.model.Projekt;
import com.project.repository.ProjektRepository;
import com.project.model.Zadanie;
import com.project.repository.ZadanieRepository;

/**
 * Implementacja serwisu obsługującego operacje na projektach
 *
 * Adnotacja @Service oznacza, że klasa będzie zarządzana przez kontener Springa,
 * pełni taką samą rolę co adnotacja @Component z dodatkowym wskazaniem
 * na klasę warstwy logiki biznesowej.
 */
@Service
public class ProjektServiceImpl implements ProjektService {

    private ProjektRepository projektRepository;
    private ZadanieRepository zadanieRepository;

    /**
     * Konstruktor z wstrzykiwaniem zależności.
     * Adnotacja @Autowired oznacza, że Spring zajmie się wstrzyknięciem instancji
     * klasy ProjektRepository do zmiennej projektRepository oraz ZadanieRepository
     * do zmiennej zadanieRepository (wcześniej oczywiście też sam utworzy obiekty tych klas).
     * W najnowszych wersjach Springa adnotacja przed konstruktorem może być pomijana,
     * ponieważ wstrzykiwanie przez konstruktor jest działaniem domyślnym.
     */
    @Autowired
    public ProjektServiceImpl(ProjektRepository projektRepository, ZadanieRepository zadanieRepository) {
        this.projektRepository = projektRepository;
        this.zadanieRepository = zadanieRepository;
    }

    @Override
    public Optional<Projekt> getProjekt(Integer projektId) {
        return projektRepository.findById(projektId);
    }

    @Override
    @Transactional
    public Projekt setProjekt(Projekt projekt) {
        // Zapisuje projekt do bazy danych
        return projektRepository.save(projekt);
    }

    /**
     * Metoda do usuwania projektu wraz z powiązanymi zadaniami.
     * Adnotacja @Transactional zapewnia, że wszystkie operacje bazodanowe
     * wykonają się w ramach jednej transakcji. Jeśli którakolwiek operacja
     * nie powiedzie się, wszystkie zmiany zostaną wycofane.
     */
    @Override
    @Transactional
    public void deleteProjekt(Integer projektId) {
        // Najpierw usuwamy wszystkie zadania powiązane z projektem
        for (Zadanie zadanie : zadanieRepository.findZadaniaProjektu(projektId)) {
            zadanieRepository.delete(zadanie);
        }
        // Następnie usuwamy sam projekt
        projektRepository.deleteById(projektId);
    }

    @Override
    public Page<Projekt> getProjekty(Pageable pageable) {
        // Zwraca stronę z projektami
        return projektRepository.findAll(pageable);
    }

    @Override
    public Page<Projekt> searchByNazwa(String nazwa, Pageable pageable) {
        // Wyszukuje projekty zawierające podaną frazę w nazwie
        return projektRepository.findByNazwaContainingIgnoreCase(nazwa, pageable);
    }
}