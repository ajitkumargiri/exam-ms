package com.ajit.examms.web.rest;

import com.ajit.examms.domain.College;
import com.ajit.examms.repository.CollegeRepository;
import com.ajit.examms.service.CollegeService;
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
 * REST controller for managing {@link com.ajit.examms.domain.College}.
 */
@RestController
@RequestMapping("/api")
public class CollegeResource {

    private final Logger log = LoggerFactory.getLogger(CollegeResource.class);

    private static final String ENTITY_NAME = "college";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CollegeService collegeService;

    private final CollegeRepository collegeRepository;

    public CollegeResource(CollegeService collegeService, CollegeRepository collegeRepository) {
        this.collegeService = collegeService;
        this.collegeRepository = collegeRepository;
    }

    /**
     * {@code POST  /colleges} : Create a new college.
     *
     * @param college the college to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new college, or with status {@code 400 (Bad Request)} if the college has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/colleges")
    public ResponseEntity<College> createCollege(@Valid @RequestBody College college) throws URISyntaxException {
        log.debug("REST request to save College : {}", college);
        if (college.getId() != null) {
            throw new BadRequestAlertException("A new college cannot already have an ID", ENTITY_NAME, "idexists");
        }
        College result = collegeService.save(college);
        return ResponseEntity
            .created(new URI("/api/colleges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /colleges/:id} : Updates an existing college.
     *
     * @param id the id of the college to save.
     * @param college the college to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated college,
     * or with status {@code 400 (Bad Request)} if the college is not valid,
     * or with status {@code 500 (Internal Server Error)} if the college couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/colleges/{id}")
    public ResponseEntity<College> updateCollege(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody College college
    ) throws URISyntaxException {
        log.debug("REST request to update College : {}, {}", id, college);
        if (college.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, college.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!collegeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        College result = collegeService.save(college);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, college.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /colleges/:id} : Partial updates given fields of an existing college, field will ignore if it is null
     *
     * @param id the id of the college to save.
     * @param college the college to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated college,
     * or with status {@code 400 (Bad Request)} if the college is not valid,
     * or with status {@code 404 (Not Found)} if the college is not found,
     * or with status {@code 500 (Internal Server Error)} if the college couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/colleges/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<College> partialUpdateCollege(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody College college
    ) throws URISyntaxException {
        log.debug("REST request to partial update College partially : {}, {}", id, college);
        if (college.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, college.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!collegeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<College> result = collegeService.partialUpdate(college);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, college.getId().toString())
        );
    }

    /**
     * {@code GET  /colleges} : get all the colleges.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of colleges in body.
     */
    @GetMapping("/colleges")
    public ResponseEntity<List<College>> getAllColleges(Pageable pageable) {
        log.debug("REST request to get a page of Colleges");
        Page<College> page = collegeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /colleges/:id} : get the "id" college.
     *
     * @param id the id of the college to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the college, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/colleges/{id}")
    public ResponseEntity<College> getCollege(@PathVariable Long id) {
        log.debug("REST request to get College : {}", id);
        Optional<College> college = collegeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(college);
    }

    /**
     * {@code DELETE  /colleges/:id} : delete the "id" college.
     *
     * @param id the id of the college to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/colleges/{id}")
    public ResponseEntity<Void> deleteCollege(@PathVariable Long id) {
        log.debug("REST request to delete College : {}", id);
        collegeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
