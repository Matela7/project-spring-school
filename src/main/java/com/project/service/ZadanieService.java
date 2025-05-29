package com.project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.project.model.Zadanie;

public interface ZadanieService {
    Page<Zadanie> getZadania(Pageable pageable);
}