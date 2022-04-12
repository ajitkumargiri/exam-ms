package com.ajit.examms.web.rest;

import com.ajit.examms.domain.FileInfo;
import com.ajit.examms.domain.ResponseMessage;
import com.ajit.examms.domain.Student;
import com.ajit.examms.repository.StudentRepository;
import com.ajit.examms.service.ExcelHelper;
import com.ajit.examms.service.ExcelService;
import com.ajit.examms.service.FilesStorageService;
import com.ajit.examms.service.StudentService;
import com.ajit.examms.web.rest.errors.BadRequestAlertException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.ajit.examms.domain.Student}.
 */
@RestController
@RequestMapping("/api")
public class StudentResource {

    private final Logger log = LoggerFactory.getLogger(StudentResource.class);

    private static final String ENTITY_NAME = "student";

    @Autowired
    ExcelService excelService;

    @Autowired
    FilesStorageService storageService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StudentService studentService;

    private final StudentRepository studentRepository;

    public StudentResource(StudentService studentService, StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }

    /**
     * {@code POST  /students} : Create a new student.
     *
     * @param student the student to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new student, or with status {@code 400 (Bad Request)} if the student has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/students")
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) throws URISyntaxException {
        log.debug("REST request to save Student : {}", student);
        if (student.getId() != null) {
            throw new BadRequestAlertException("A new student cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Student result = studentService.save(student);
        return ResponseEntity
            .created(new URI("/api/students/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /* @PostMapping("/upload-csv-file")
    public String uploadCSVFile(@RequestParam("file") MultipartFile file, Model model) {

        // validate file
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a CSV file to upload.");
            model.addAttribute("status", false);
        } else {

            // parse CSV file to create a list of `User` objects
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

                // create csv bean reader
                CsvToBean<User> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(User.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

                // convert `CsvToBean` object to list of users
                List<User> users = csvToBean.parse();

                // TODO: save users in DB?

                // save users list on model
                model.addAttribute("users", users);
                model.addAttribute("status", true);

            } catch (Exception ex) {
                model.addAttribute("message", "An error occurred while processing the CSV file.");
                model.addAttribute("status", false);
            }
        }

        return "file-upload-status";
    }*/

    @PostMapping("/students/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                excelService.save(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                e.printStackTrace();
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }
        if (ExcelHelper.hasCsvFormat(file)) {
            try {
                excelService.saveCSV(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                e.printStackTrace();
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }
        message = "Please upload an excel/csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @GetMapping("/students/files")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = storageService
            .loadAll()
            .map(
                path -> {
                    String filename = path.getFileName().toString();
                    String url = MvcUriComponentsBuilder
                        .fromMethodName(FilesController.class, "getFile", path.getFileName().toString())
                        .build()
                        .toString();

                    return new FileInfo(filename, url);
                }
            )
            .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/students/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.load(filename);
        return ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
            .body(file);
    }

    /**
     * {@code PUT  /students/:id} : Updates an existing student.
     *
     * @param id the id of the student to save.
     * @param student the student to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated student,
     * or with status {@code 400 (Bad Request)} if the student is not valid,
     * or with status {@code 500 (Internal Server Error)} if the student couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Student student
    ) throws URISyntaxException {
        log.debug("REST request to update Student : {}, {}", id, student);
        if (student.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, student.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!studentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Student result = studentService.save(student);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, student.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /students/:id} : Partial updates given fields of an existing student, field will ignore if it is null
     *
     * @param id the id of the student to save.
     * @param student the student to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated student,
     * or with status {@code 400 (Bad Request)} if the student is not valid,
     * or with status {@code 404 (Not Found)} if the student is not found,
     * or with status {@code 500 (Internal Server Error)} if the student couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/students/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Student> partialUpdateStudent(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Student student
    ) throws URISyntaxException {
        log.debug("REST request to partial update Student partially : {}, {}", id, student);
        if (student.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, student.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!studentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Student> result = studentService.partialUpdate(student);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, student.getId().toString())
        );
    }

    /**
     * {@code GET  /students} : get all the students.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of students in body.
     */
    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents(Pageable pageable) {
        log.debug("REST request to get a page of Students");
        Page<Student> page = studentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /students/:id} : get the "id" student.
     *
     * @param id the id of the student to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the student, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        log.debug("REST request to get Student : {}", id);
        Optional<Student> student = studentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(student);
    }

    /**
     * {@code DELETE  /students/:id} : delete the "id" student.
     *
     * @param id the id of the student to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        log.debug("REST request to delete Student : {}", id);
        studentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
