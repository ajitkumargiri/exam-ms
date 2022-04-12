package com.ajit.examms.web.rest;

import com.ajit.examms.domain.ExamCenter;
import com.ajit.examms.repository.ExamCenterRepository;
import com.ajit.examms.service.ExamCenterService;
import com.ajit.examms.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.ajit.examms.domain.ExamCenter}.
 */
@RestController
@RequestMapping("/api")
public class ExamCenterResource {

    private final Logger log = LoggerFactory.getLogger(ExamCenterResource.class);

    private static final String ENTITY_NAME = "examCenter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExamCenterService examCenterService;

    private final ExamCenterRepository examCenterRepository;

    public ExamCenterResource(ExamCenterService examCenterService, ExamCenterRepository examCenterRepository) {
        this.examCenterService = examCenterService;
        this.examCenterRepository = examCenterRepository;
    }

    /**
     * {@code POST  /exam-centers} : Create a new examCenter.
     *
     * @param examCenter the examCenter to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new examCenter, or with status {@code 400 (Bad Request)} if the examCenter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/exam-centers")
    public ResponseEntity<ExamCenter> createExamCenter(@Valid @RequestBody ExamCenter examCenter) throws URISyntaxException {
        log.debug("REST request to save ExamCenter : {}", examCenter);
        if (examCenter.getId() != null) {
            throw new BadRequestAlertException("A new examCenter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExamCenter result = examCenterService.save(examCenter);
        return ResponseEntity
            .created(new URI("/api/exam-centers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /exam-centers/:id} : Updates an existing examCenter.
     *
     * @param id the id of the examCenter to save.
     * @param examCenter the examCenter to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated examCenter,
     * or with status {@code 400 (Bad Request)} if the examCenter is not valid,
     * or with status {@code 500 (Internal Server Error)} if the examCenter couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/exam-centers/{id}")
    public ResponseEntity<ExamCenter> updateExamCenter(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ExamCenter examCenter
    ) throws URISyntaxException {
        log.debug("REST request to update ExamCenter : {}, {}", id, examCenter);
        if (examCenter.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, examCenter.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!examCenterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ExamCenter result = examCenterService.save(examCenter);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, examCenter.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /exam-centers/:id} : Partial updates given fields of an existing examCenter, field will ignore if it is null
     *
     * @param id the id of the examCenter to save.
     * @param examCenter the examCenter to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated examCenter,
     * or with status {@code 400 (Bad Request)} if the examCenter is not valid,
     * or with status {@code 404 (Not Found)} if the examCenter is not found,
     * or with status {@code 500 (Internal Server Error)} if the examCenter couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/exam-centers/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ExamCenter> partialUpdateExamCenter(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ExamCenter examCenter
    ) throws URISyntaxException {
        log.debug("REST request to partial update ExamCenter partially : {}, {}", id, examCenter);
        if (examCenter.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, examCenter.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!examCenterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ExamCenter> result = examCenterService.partialUpdate(examCenter);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, examCenter.getId().toString())
        );
    }

    /**
     * {@code GET  /exam-centers} : get all the examCenters.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of examCenters in body.
     */
    @GetMapping("/exam-centers")
    public List<ExamCenter> getAllExamCenters() {
        log.debug("REST request to get all ExamCenters");
        return examCenterService.findAll();
    }

    /**
     * {@code GET  /exam-centers/:id} : get the "id" examCenter.
     *
     * @param id the id of the examCenter to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the examCenter, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/exam-centers/{id}")
    public ResponseEntity<ExamCenter> getExamCenter(@PathVariable Long id) {
        log.debug("REST request to get ExamCenter : {}", id);
        Optional<ExamCenter> examCenter = examCenterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(examCenter);
    }

    /**
     * {@code DELETE  /exam-centers/:id} : delete the "id" examCenter.
     *
     * @param id the id of the examCenter to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/exam-centers/{id}")
    public ResponseEntity<Void> deleteExamCenter(@PathVariable Long id) {
        log.debug("REST request to delete ExamCenter : {}", id);
        examCenterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
