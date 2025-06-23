package com.project.service;
import java.net.URI;
import java.util.Optional;

import com.project.repository.ProjektRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import com.project.exception.HttpException;
import com.project.model.Projekt;
import java.util.List;

@Service
public class ProjektServiceImpl implements ProjektService {
    private static final Logger logger = LoggerFactory.getLogger(ProjektServiceImpl.class);
    private final RestClient restClient;
    private final ProjektRepository projektRepository;

    // obiekt wstrzykiwany poprzez konstruktor, dzięki adnotacjom
    // @Configuration i @Bean zawartym w klasie SecurityConfig
// Spring utworzy wcześniej obiekt, a adnotacja @Autowired
// tej klasy wskaże element docelowy wstrzykiwania
// (adnotacja @Autowired może być pomijana jeżeli w klasie
// jest tylko jeden konstruktor)
    public ProjektServiceImpl(RestClient restClient, ProjektRepository projektRepository) {
        this.restClient = restClient;
        this.projektRepository = projektRepository;
    }
    private String getResourcePath() {
        return "/api/projekty";
    }
    private String getResourcePath(Integer id) {
        return String.format("%s/%d", getResourcePath(), id);
    }
//    @Override
//    public Optional<Projekt> getProjekt(Integer projektId) {
//        String resourcePath = getResourcePath(projektId);
//        logger.info("REQUEST -> GET {}", resourcePath);
//        Projekt projekt = restClient
//                .get()
//                .uri(resourcePath) //można też używać .uri("/api/projekty/{projektId}", projektId)
//                .retrieve()
//                .onStatus(HttpStatusCode::isError, (req, res) -> {
//                    throw new HttpException(res.getStatusCode(), res.getHeaders());
//                })
//                .body(Projekt.class);
//        return Optional.ofNullable(projekt);
//    }
    @Override
    public synchronized Projekt setProjekt(Projekt projekt) {
        if (projekt == null) {
            throw new IllegalArgumentException("Projekt nie może być null");
        }
        if (projekt.getProjektId() != null) { // Modyfikacja istniejącego projektu
            logger.info("Updating project with ID: {}", projekt.getProjektId());
            return projektRepository.save(projekt); // Bezpośrednie użycie repozytorium
        } else { // Tworzenie nowego projektu
            logger.info("Creating new project");
            return projektRepository.save(projekt); // Bezpośrednie użycie repozytorium
        }
    }
    @Override
    public Optional<Projekt> getProjekt(Integer projektId) {
        logger.info("Pobieranie projektu z ID: {}", projektId);
        return projektRepository.findById(projektId);
    }

    @Override
    public void deleteProjekt(Integer projektId) {
        logger.info("Usuwanie projektu z ID: {}", projektId);
        projektRepository.deleteById(projektId);
    }
    public Page<Projekt> getProjekty(Pageable pageable) {
        URI uri = ServiceUtil.getURI(getResourcePath(), pageable);
        logger.info("REQUEST -> GET {}", uri);
        return getPage(uri);
    }
    @Override
    public Page<Projekt> searchByNazwa(String nazwa, Pageable pageable) {
        URI uri = ServiceUtil
                .getUriComponent(getResourcePath(), pageable)
                .queryParam("nazwa", nazwa)
                .build().toUri();
        logger.info("REQUEST -> GET {}", uri);
        return getPage(uri);
    }

    private Page<Projekt> getPage(URI uri) {
        return restClient.get()
                .uri(uri.toString())
                .retrieve()
                .body(new ParameterizedTypeReference<RestResponsePage<Projekt>>(){});
    }
    @Override
    public List<Projekt> getProjekty() {
        logger.info("Pobieranie wszystkich projektów z bazy danych");
        return projektRepository.findAll();
    }
}