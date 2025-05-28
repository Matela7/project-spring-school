package com.project.project_rest_api;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.project.controller.ProjektRestController;
import com.project.model.Projekt;
import com.project.service.ProjektService;
@ExtendWith(MockitoExtension.class)
public class ProjektRestControllerUnitTest {
    @Mock
    private ProjektService mockProjektService;
    @InjectMocks
    private ProjektRestController projectController;

    @Test
    void getProject_whenValidId_shouldReturnGivenProject() {
        // Tworzymy nowy projekt z odpowiednimi parametrami
        Projekt projekt = new Projekt("Nazwa1", "Opis1");
        projekt.setProjektId(1);
        // Ustawiamy datę oddania (konwertując z LocalDate na java.sql.Date)
        projekt.setDataOddania(Date.valueOf("2024-06-07"));

        when(mockProjektService.getProjekt(projekt.getProjektId())).thenReturn(Optional.of(projekt));
        ResponseEntity<Projekt> responseEntity = projectController.getProjekt(projekt.getProjektId());

        assertAll(() -> assertEquals(responseEntity.getStatusCode().value(), HttpStatus.OK.value()),
                () -> assertEquals(responseEntity.getBody(), projekt));
    }

    @Test
    void getProject_whenInvalidId_shouldReturnNotFound() {
        Integer projektId = 2;
        when(mockProjektService.getProjekt(projektId)).thenReturn(Optional.empty());
        ResponseEntity<Projekt> responseEntity = projectController.getProjekt(projektId);
        assertEquals(responseEntity.getStatusCode().value(), HttpStatus.NOT_FOUND.value());
    }

    @Test
    void getProjects_shouldReturnPageWithProjects() {
        // Tworzymy przykładową listę projektów
        Projekt projekt1 = new Projekt("Nazwa1", "Opis1");
        projekt1.setProjektId(1);
        projekt1.setDataOddania(Date.valueOf("2024-06-07"));

        Projekt projekt2 = new Projekt("Nazwa2", "Opis2");
        projekt2.setProjektId(2);
        projekt2.setDataOddania(Date.valueOf("2024-06-07"));

        Projekt projekt3 = new Projekt("Nazwa3", "Opis3");
        projekt3.setProjektId(3);
        projekt3.setDataOddania(Date.valueOf("2024-06-07"));

        List<Projekt> list = List.of(projekt1, projekt2, projekt3);
        PageRequest pageable = PageRequest.of(1, 5);
        Page<Projekt> page = new PageImpl<>(list, pageable, 5);

        when(mockProjektService.getProjekty(pageable)).thenReturn(page);
        Page<Projekt> pageWithProjects = projectController.getProjekty(pageable);

        assertNotNull(pageWithProjects);
        List<Projekt> projects = pageWithProjects.getContent();
        assertNotNull(projects);
        assertThat(projects, hasSize(3));
        assertAll(() -> assertTrue(projects.contains(list.get(0))),
                () -> assertTrue(projects.contains(list.get(1))),
                () -> assertTrue(projects.contains(list.get(2))));
    }

    @Test
    void createProject_whenValidData_shouldCreateProject() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Projekt projekt = new Projekt("Nazwa1", "Opis1");
        projekt.setProjektId(1);
        projekt.setDataOddania(Date.valueOf("2024-06-07"));

        when(mockProjektService.setProjekt(any(Projekt.class))).thenReturn(projekt);
        ResponseEntity<Void> responseEntity = projectController.createProjekt(projekt);

        assertThat(responseEntity.getStatusCode().value(), is(HttpStatus.CREATED.value()));
        assertThat(responseEntity.getHeaders().getLocation().getPath(), is("/" + projekt.getProjektId()));
    }

    @Test
    void updateProject_whenValidData_shouldUpdateProject() {
        Projekt projekt = new Projekt("Nazwa1", "Opis1");
        projekt.setProjektId(1);
        projekt.setDataOddania(Date.valueOf("2024-06-07"));

        when(mockProjektService.getProjekt(projekt.getProjektId())).thenReturn(Optional.of(projekt));
        ResponseEntity<Void> responseEntity = projectController.updateProjekt(projekt, projekt.getProjektId());

        assertThat(responseEntity.getStatusCode().value(), is(HttpStatus.OK.value()));
    }

    @Test
    void deleteProject_whenValidId_shouldDeleteProject() {
        Projekt projekt = new Projekt("Nazwa1", "Opis1");
        projekt.setProjektId(1);
        projekt.setDataOddania(Date.valueOf("2024-06-07"));

        when(mockProjektService.getProjekt(projekt.getProjektId())).thenReturn(Optional.of(projekt));
        ResponseEntity<Void> responseEntity = projectController.deleteProjekt(projekt.getProjektId());

        assertThat(responseEntity.getStatusCode().value(), is(HttpStatus.OK.value()));
    }

    @Test
    void deleteProject_whenInvalidId_shouldReturnNotFound() {
        Integer projektId = 1;
        ResponseEntity<Void> responseEntity = projectController.deleteProjekt(projektId);
        assertThat(responseEntity.getStatusCode().value(), is(HttpStatus.NOT_FOUND.value()));
    }
}