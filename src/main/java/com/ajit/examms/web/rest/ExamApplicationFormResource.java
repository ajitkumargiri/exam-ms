package com.ajit.examms.web.rest;

import com.ajit.examms.domain.ExamApplicationForm;
import com.ajit.examms.repository.ExamApplicationFormRepository;
import com.ajit.examms.service.ExamApplicationFormService;
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
 * REST controller for managing {@link com.ajit.examms.domain.ExamApplicationForm}.
 */
@RestController
@RequestMapping("/api")
public class ExamApplicationFormResource {

    private final Logger log = LoggerFactory.getLogger(ExamApplicationFormResource.class);

    private static final String ENTITY_NAME = "examApplicationForm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExamApplicationFormService examApplicationFormService;

    private final ExamApplicationFormRepository examApplicationFormRepository;

    public ExamApplicationFormResource(
        ExamApplicationFormService examApplicationFormService,
        ExamApplicationFormRepository examApplicationFormRepository
    ) {
        this.examApplicationFormService = examApplicationFormService;
        this.examApplicationFormRepository = examApplicationFormRepository;
    }

    /**
     * {@code POST  /exam-application-forms} : Create a new examApplicationForm.
     *
     * @param examApplicationForm the examApplicationForm to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new examApplicationForm, or with status {@code 400 (Bad Request)} if the examApplicationForm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/exam-application-forms")
    public ResponseEntity<ExamApplicationForm> createExamApplicationForm(@Valid @RequestBody ExamApplicationForm examApplicationForm)
        throws URISyntaxException {
        log.debug("REST request to save ExamApplicationForm : {}", examApplicationForm);
        if (examApplicationForm.getId() != null) {
            throw new BadRequestAlertException("A new examApplicationForm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExamApplicationForm result = examApplicationFormService.save(examApplicationForm);
        return ResponseEntity
            .created(new URI("/api/exam-application-forms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /exam-application-forms/:id} : Updates an existing examApplicationForm.
     *
     * @param id the id of the examApplicationForm to save.
     * @param examApplicationForm the examApplicationForm to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated examApplicationForm,
     * or with status {@code 400 (Bad Request)} if the examApplicationForm is not valid,
     * or with status {@code 500 (Internal Server Error)} if the examApplicationForm couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/exam-application-forms/{id}")
    public ResponseEntity<ExamApplicationForm> updateExamApplicationForm(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ExamApplicationForm examApplicationForm
    ) throws URISyntaxException {
        log.debug("REST request to update ExamApplicationForm : {}, {}", id, examApplicationForm);
        if (examApplicationForm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, examApplicationForm.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!examApplicationFormRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ExamApplicationForm result = examApplicationFormService.save(examApplicationForm);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, examApplicationForm.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /exam-application-forms/:id} : Partial updates given fields of an existing examApplicationForm, field will ignore if it is null
     *
     * @param id the id of the examApplicationForm to save.
     * @param examApplicationForm the examApplicationForm to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated examApplicationForm,
     * or with status {@code 400 (Bad Request)} if the examApplicationForm is not valid,
     * or with status {@code 404 (Not Found)} if the examApplicationForm is not found,
     * or with status {@code 500 (Internal Server Error)} if the examApplicationForm couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/exam-application-forms/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ExamApplicationForm> partialUpdateExamApplicationForm(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ExamApplicationForm examApplicationForm
    ) throws URISyntaxException {
        log.debug("REST request to partial update ExamApplicationForm partially : {}, {}", id, examApplicationForm);
        if (examApplicationForm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, examApplicationForm.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!examApplicationFormRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ExamApplicationForm> result = examApplicationFormService.partialUpdate(examApplicationForm);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, examApplicationForm.getId().toString())
        );
    }

    /**
     * {@code GET  /exam-application-forms} : get all the examApplicationForms.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of examApplicationForms in body.
     */
    @GetMapping("/exam-application-forms")
    public ResponseEntity<List<ExamApplicationForm>> getAllExamApplicationForms(Pageable pageable) {
        log.debug("REST request to get a page of ExamApplicationForms");
        Page<ExamApplicationForm> page = examApplicationFormService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /exam-application-forms/:id} : get the "id" examApplicationForm.
     *
     * @param id the id of the examApplicationForm to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the examApplicationForm, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/exam-application-forms/{id}")
    public ResponseEntity<ExamApplicationForm> getExamApplicationForm(@PathVariable Long id) {
        log.debug("REST request to get ExamApplicationForm : {}", id);
        Optional<ExamApplicationForm> examApplicationForm = examApplicationFormService.findOne(id);
        return ResponseUtil.wrapOrNotFound(examApplicationForm);
    }

    /**
     * {@code DELETE  /exam-application-forms/:id} : delete the "id" examApplicationForm.
     *
     * @param id the id of the examApplicationForm to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/exam-application-forms/{id}")
    public ResponseEntity<Void> deleteExamApplicationForm(@PathVariable Long id) {
        log.debug("REST request to delete ExamApplicationForm : {}", id);
        examApplicationFormService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
