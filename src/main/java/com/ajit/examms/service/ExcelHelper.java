package com.ajit.examms.service;

import com.ajit.examms.domain.College;
import com.ajit.examms.domain.Student;
import com.ajit.examms.domain.enumeration.BloodGroup;
import com.ajit.examms.domain.enumeration.Gender;
import com.ajit.examms.domain.enumeration.MaritialStatus;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

public class ExcelHelper {

    private static final Logger log = LoggerFactory.getLogger(ExcelHelper.class);
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static String CSV_TYPE = "text/csv";

    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static boolean hasCsvFormat(MultipartFile file) {
        log.info(file.getContentType());
        if (CSV_TYPE.equals(file.getContentType()) || "application/vnd.ms-excel".equals(file.getContentType())) {
            return true;
        }

        return false;
    }

    public static List<Student> excelToStudents(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            workbook.createDataFormat().getFormat("dd-m-yyyy;@");
            Sheet sheet = workbook.getSheetAt(0);
            List<Student> studentList = new ArrayList<>();
            for (int index = 0; index < sheet.getPhysicalNumberOfRows(); index++) {
                if (index > 0) {
                    Student student = new Student();
                    Row row = sheet.getRow(index);
                    DataFormatter formatter = new DataFormatter();
                    Cell regCell = row.getCell(0);
                    String regValue = formatter.formatCellValue(regCell).toString();
                    student.setRegNumber(regValue);
                    student.setFirstName(row.getCell(1).getStringCellValue());
                    student.setMiddleName(row.getCell(2).getStringCellValue());
                    student.setLastName(row.getCell(3).getStringCellValue());
                    // student.setDob(instant);

                    DataFormatter df = new DataFormatter();
                    df.addFormat("m/d/yy", new java.text.SimpleDateFormat("dd/MM/yyyy"));
                    Cell cell = row.getCell(4);
                    String dob = df.formatCellValue(cell).toString();
                    System.out.println("dob: " + dob);
                    DateTimeFormatter dtformatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

                    //convert String to LocalDate
                    LocalDate localDate = LocalDate.parse(dob, dtformatter);

                    System.out.println("Localdate: " + localDate);
                    Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
                    System.out.println("instant: " + instant); //2019-05-15T18:30:00Z
                    student.setDob(instant);
                    student.setFatherName(row.getCell(5).getStringCellValue());
                    student.setMotherName(row.getCell(6).getStringCellValue());
                    student.setEmail(row.getCell(7).getStringCellValue());

                    DataFormatter df1 = new DataFormatter();
                    Cell mobilwcell = row.getCell(8);
                    String mobilwcellValue = df1.formatCellValue(mobilwcell).toString();
                    student.setMobileNumber(mobilwcellValue);
                    student.setNationality(row.getCell(9).getStringCellValue());
                    String gender = row.getCell(10).getStringCellValue();
                    student.setGender(Gender.valueOf(gender.toUpperCase()));
                    student.setReligion(row.getCell(11).getStringCellValue());
                    student.setCatagory(row.getCell(12).getStringCellValue());
                    student.setMaritialStatus(MaritialStatus.valueOf(row.getCell(13).getStringCellValue().toUpperCase()));
                    DataFormatter df2 = new DataFormatter();
                    Cell aadharCell = row.getCell(14);
                    String aadharValue = df2.formatCellValue(aadharCell).toString();
                    student.setAdharNumber(aadharValue);
                    student.setBloodGroup(BloodGroup.B_POS);
                    DataFormatter df3 = new DataFormatter();
                    Cell fatherMobileCell = row.getCell(14);
                    String fatherMobileValue = df3.formatCellValue(fatherMobileCell).toString();
                    student.setFatherMobileNumber(fatherMobileValue);
                    student.setFatherEmailId(row.getCell(17).getStringCellValue());
                    studentList.add(student);
                }
            }
            workbook.close();

            return studentList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            FileInputStream file = new FileInputStream(new ClassPathResource("studentWorkBook1.xlsx").getFile());

            excelToStudents(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
