package com.ajit.examms.web.rest;

import com.ajit.examms.domain.AcademicBatch;
import com.ajit.examms.repository.AcademicBatchRepository;
import com.ajit.examms.service.AcademicBatchService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.ajit.examms.domain.AcademicBatch}.
 */
@RestController
@RequestMapping("/api")
public class AcademicBatchResource {

    private final Logger log = LoggerFactory.getLogger(AcademicBatchResource.class);

    private static final String ENTITY_NAME = "academicBatch";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AcademicBatchService academicBatchService;

    private final AcademicBatchRepository academicBatchRepository;

    public AcademicBatchResource(AcademicBatchService academicBatchService, AcademicBatchRepository academicBatchRepository) {
        this.academicBatchService = academicBatchService;
        this.academicBatchRepository = academicBatchRepository;
    }

    /**
     * {@code POST  /academic-batches} : Create a new academicBatch.
     *
     * @param academicBatch the academicBatch to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new academicBatch, or with status {@code 400 (Bad Request)} if the academicBatch has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/academic-batches")
    public ResponseEntity<AcademicBatch> createAcademicBatch(@Valid @RequestBody AcademicBatch academicBatch) throws URISyntaxException {
        log.debug("REST request to save AcademicBatch : {}", academicBatch);
        if (academicBatch.getId() != null) {
            throw new BadRequestAlertException("A new academicBatch cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AcademicBatch result = academicBatchService.save(academicBatch);
        return ResponseEntity
            .created(new URI("/api/academic-batches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /academic-batches/:id} : Updates an existing academicBatch.
     *
     * @param id the id of the academicBatch to save.
     * @param academicBatch the academicBatch to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated academicBatch,
     * or with status {@code 400 (Bad Request)} if the academicBatch is not valid,
     * or with status {@code 500 (Internal Server Error)} if the academicBatch couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/academic-batches/{id}")
    public ResponseEntity<AcademicBatch> updateAcademicBatch(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AcademicBatch academicBatch
    ) throws URISyntaxException {
        log.debug("REST request to update AcademicBatch : {}, {}", id, academicBatch);
        if (academicBatch.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, academicBatch.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!academicBatchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AcademicBatch result = academicBatchService.save(academicBatch);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, academicBatch.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /academic-batches/:id} : Partial updates given fields of an existing academicBatch, field will ignore if it is null
     *
     * @param id the id of the academicBatch to save.
     * @param academicBatch the academicBatch to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated academicBatch,
     * or with status {@code 400 (Bad Request)} if the academicBatch is not valid,
     * or with status {@code 404 (Not Found)} if the academicBatch is not found,
     * or with status {@code 500 (Internal Server Error)} if the academicBatch couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/academic-batches/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AcademicBatch> partialUpdateAcademicBatch(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AcademicBatch academicBatch
    ) throws URISyntaxException {
        log.debug("REST request to partial update AcademicBatch partially : {}, {}", id, academicBatch);
        if (academicBatch.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, academicBatch.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!academicBatchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AcademicBatch> result = academicBatchService.partialUpdate(academicBatch);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, academicBatch.getId().toString())
        );
    }

    /**
     * {@code GET  /academic-batches} : get all the academicBatches.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of academicBatches in body.
     */
    @GetMapping("/academic-batches")
    public ResponseEntity<List<AcademicBatch>> getAllAcademicBatches(Pageable pageable) {
        log.debug("REST request to get a page of AcademicBatches");
        Page<AcademicBatch> page = academicBatchService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /academic-batches/:id} : get the "id" academicBatch.
     *
     * @param id the id of the academicBatch to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the academicBatch, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/academic-batches/{id}")
    public ResponseEntity<AcademicBatch> getAcademicBatch(@PathVariable Long id) {
        log.debug("REST request to get AcademicBatch : {}", id);
        Optional<AcademicBatch> academicBatch = academicBatchService.findOne(id);
        return ResponseUtil.wrapOrNotFound(academicBatch);
    }

    /**
     * {@code DELETE  /academic-batches/:id} : delete the "id" academicBatch.
     *
     * @param id the id of the academicBatch to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/academic-batches/{id}")
    public ResponseEntity<Void> deleteAcademicBatch(@PathVariable Long id) {
        log.debug("REST request to delete AcademicBatch : {}", id);
        academicBatchService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
