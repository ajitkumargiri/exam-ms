package com.ajit.examms.service;

import com.ajit.examms.domain.College;
import com.ajit.examms.domain.Student;
import com.ajit.examms.repository.CollegeRepository;
import com.ajit.examms.repository.StudentRepository;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ExcelService {

    private final Logger log = LoggerFactory.getLogger(ExcelService.class);

    @Autowired
    StudentRepository repository;

    public void save(MultipartFile file) {
        try {
            List<Student> studentList = ExcelHelper.excelToStudents(file.getInputStream());
            repository.saveAll(studentList);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    public void saveCSV(MultipartFile file) {
        try {
            List<Student> studentList = loadObjectList(Student.class, file);
            repository.saveAll(studentList);
        } catch (Exception e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public List<Student> getAllTutorials() {
        return repository.findAll();
    }

    public <T> List<T> loadObjectList(Class<T> type, MultipartFile file) {
        try {
            CsvSchema csvSchema = CsvSchema
                .builder()
                .setUseHeader(true)
                .addColumn("regNumber", CsvSchema.ColumnType.STRING)
                .addColumn("firstName", CsvSchema.ColumnType.STRING)
                .addColumn("middleName", CsvSchema.ColumnType.STRING)
                .addColumn("lastName", CsvSchema.ColumnType.STRING)
                //.addColumn("dob", CsvSchema.ColumnType.STRING)
                .addColumn("fatherName")
                .addColumn("motherName", CsvSchema.ColumnType.STRING)
                .addColumn("email", CsvSchema.ColumnType.STRING)
                .addColumn("mobileNumber", CsvSchema.ColumnType.STRING)
                .addColumn("nationality", CsvSchema.ColumnType.STRING)
                //.addColumn("gender", CsvSchema.ColumnType.STRING)
                .addColumn("religion", CsvSchema.ColumnType.STRING)
                .addColumn("catagory", CsvSchema.ColumnType.STRING)
                //.addColumn("maritialStatus", CsvSchema.ColumnType.STRING)
                .addColumn("adharNumber", CsvSchema.ColumnType.STRING)
                //.addColumn("bloodGroup", CsvSchema.ColumnType.STRING)
                .addColumn("fatherMobileNumber", CsvSchema.ColumnType.STRING)
                .addColumn("fatherEmailId", CsvSchema.ColumnType.STRING)
                .build()
                .withHeader();

            CsvMapper mapper = new CsvMapper();
            MappingIterator<T> readValues = mapper.readerFor(type).with(csvSchema).readValues(file.getInputStream());
            //MappingIterator<College> collegeMappingIterator = new CsvMapper().readerFor(type).with(csvSchema).readValues(file.getInputStream());
            return readValues.readAll();
        } catch (Exception e) {
            log.error("Error occurred while loading object list from file " + file.getName(), e);
            return Collections.emptyList();
        }
    }

    public static void main(String[] args) {
        try {
            File initialFile = new File("src/main/resources/studentWorkBook.xlsx");
            InputStream targetStream = new FileInputStream(initialFile);
            List<Student> studentList = ExcelHelper.excelToStudents(targetStream);
            for (Student student : studentList) {
                System.out.println(student);
            }
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }
}
