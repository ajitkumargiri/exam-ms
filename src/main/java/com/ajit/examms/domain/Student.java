package com.ajit.examms.domain;

import com.ajit.examms.domain.enumeration.BloodGroup;
import com.ajit.examms.domain.enumeration.Gender;
import com.ajit.examms.domain.enumeration.MaritialStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "reg_number", unique = true)
    private String regNumber;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "dob")
    private Instant dob;

    @Column(name = "father_name")
    private String fatherName;

    @Column(name = "mother_name")
    private String motherName;

    @NotNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;

    @NotNull
    @Column(name = "nationality", nullable = false)
    private String nationality;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "religion")
    private String religion;

    @Column(name = "catagory")
    private String catagory;

    @Enumerated(EnumType.STRING)
    @Column(name = "maritial_status")
    private MaritialStatus maritialStatus;

    @NotNull
    @Column(name = "adhar_number", nullable = false, unique = true)
    private String adharNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_group")
    private BloodGroup bloodGroup;

    @Column(name = "father_mobile_number")
    private String fatherMobileNumber;

    @Column(name = "father_email_id")
    private String fatherEmailId;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Lob
    @Column(name = "signature")
    private byte[] signature;

    @Column(name = "signature_content_type")
    private String signatureContentType;

    @Lob
    @Column(name = "adhar")
    private byte[] adhar;

    @Column(name = "adhar_content_type")
    private String adharContentType;

    @OneToMany(mappedBy = "student")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "correspondingAddress", "student", "exam", "examCenter" }, allowSetters = true)
    private Set<ExamApplicationForm> examApplicationForms = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "sessions", "students", "branch" }, allowSetters = true)
    private AcademicBatch academicBatch;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student id(Long id) {
        this.id = id;
        return this;
    }

    public String getRegNumber() {
        return this.regNumber;
    }

    public Student regNumber(String regNumber) {
        this.regNumber = regNumber;
        return this;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Student firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public Student middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Student lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Instant getDob() {
        return this.dob;
    }

    public Student dob(Instant dob) {
        this.dob = dob;
        return this;
    }

    public void setDob(Instant dob) {
        this.dob = dob;
    }

    public String getFatherName() {
        return this.fatherName;
    }

    public Student fatherName(String fatherName) {
        this.fatherName = fatherName;
        return this;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return this.motherName;
    }

    public Student motherName(String motherName) {
        this.motherName = motherName;
        return this;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getEmail() {
        return this.email;
    }

    public Student email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public Student mobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        return this;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getNationality() {
        return this.nationality;
    }

    public Student nationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Gender getGender() {
        return this.gender;
    }

    public Student gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getReligion() {
        return this.religion;
    }

    public Student religion(String religion) {
        this.religion = religion;
        return this;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getCatagory() {
        return this.catagory;
    }

    public Student catagory(String catagory) {
        this.catagory = catagory;
        return this;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public MaritialStatus getMaritialStatus() {
        return this.maritialStatus;
    }

    public Student maritialStatus(MaritialStatus maritialStatus) {
        this.maritialStatus = maritialStatus;
        return this;
    }

    public void setMaritialStatus(MaritialStatus maritialStatus) {
        this.maritialStatus = maritialStatus;
    }

    public String getAdharNumber() {
        return this.adharNumber;
    }

    public Student adharNumber(String adharNumber) {
        this.adharNumber = adharNumber;
        return this;
    }

    public void setAdharNumber(String adharNumber) {
        this.adharNumber = adharNumber;
    }

    public BloodGroup getBloodGroup() {
        return this.bloodGroup;
    }

    public Student bloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
        return this;
    }

    public void setBloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getFatherMobileNumber() {
        return this.fatherMobileNumber;
    }

    public Student fatherMobileNumber(String fatherMobileNumber) {
        this.fatherMobileNumber = fatherMobileNumber;
        return this;
    }

    public void setFatherMobileNumber(String fatherMobileNumber) {
        this.fatherMobileNumber = fatherMobileNumber;
    }

    public String getFatherEmailId() {
        return this.fatherEmailId;
    }

    public Student fatherEmailId(String fatherEmailId) {
        this.fatherEmailId = fatherEmailId;
        return this;
    }

    public void setFatherEmailId(String fatherEmailId) {
        this.fatherEmailId = fatherEmailId;
    }

    public byte[] getImage() {
        return this.image;
    }

    public Student image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return this.imageContentType;
    }

    public Student imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public byte[] getSignature() {
        return this.signature;
    }

    public Student signature(byte[] signature) {
        this.signature = signature;
        return this;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public String getSignatureContentType() {
        return this.signatureContentType;
    }

    public Student signatureContentType(String signatureContentType) {
        this.signatureContentType = signatureContentType;
        return this;
    }

    public void setSignatureContentType(String signatureContentType) {
        this.signatureContentType = signatureContentType;
    }

    public byte[] getAdhar() {
        return this.adhar;
    }

    public Student adhar(byte[] adhar) {
        this.adhar = adhar;
        return this;
    }

    public void setAdhar(byte[] adhar) {
        this.adhar = adhar;
    }

    public String getAdharContentType() {
        return this.adharContentType;
    }

    public Student adharContentType(String adharContentType) {
        this.adharContentType = adharContentType;
        return this;
    }

    public void setAdharContentType(String adharContentType) {
        this.adharContentType = adharContentType;
    }

    public Set<ExamApplicationForm> getExamApplicationForms() {
        return this.examApplicationForms;
    }

    public Student examApplicationForms(Set<ExamApplicationForm> examApplicationForms) {
        this.setExamApplicationForms(examApplicationForms);
        return this;
    }

    public Student addExamApplicationForm(ExamApplicationForm examApplicationForm) {
        this.examApplicationForms.add(examApplicationForm);
        examApplicationForm.setStudent(this);
        return this;
    }

    public Student removeExamApplicationForm(ExamApplicationForm examApplicationForm) {
        this.examApplicationForms.remove(examApplicationForm);
        examApplicationForm.setStudent(null);
        return this;
    }

    public void setExamApplicationForms(Set<ExamApplicationForm> examApplicationForms) {
        if (this.examApplicationForms != null) {
            this.examApplicationForms.forEach(i -> i.setStudent(null));
        }
        if (examApplicationForms != null) {
            examApplicationForms.forEach(i -> i.setStudent(this));
        }
        this.examApplicationForms = examApplicationForms;
    }

    public AcademicBatch getAcademicBatch() {
        return this.academicBatch;
    }

    public Student academicBatch(AcademicBatch academicBatch) {
        this.setAcademicBatch(academicBatch);
        return this;
    }

    public void setAcademicBatch(AcademicBatch academicBatch) {
        this.academicBatch = academicBatch;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        return id != null && id.equals(((Student) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            ", regNumber='" + getRegNumber() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", dob='" + getDob() + "'" +
            ", fatherName='" + getFatherName() + "'" +
            ", motherName='" + getMotherName() + "'" +
            ", email='" + getEmail() + "'" +
            ", mobileNumber='" + getMobileNumber() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", gender='" + getGender() + "'" +
            ", religion='" + getReligion() + "'" +
            ", catagory='" + getCatagory() + "'" +
            ", maritialStatus='" + getMaritialStatus() + "'" +
            ", adharNumber='" + getAdharNumber() + "'" +
            ", bloodGroup='" + getBloodGroup() + "'" +
            ", fatherMobileNumber='" + getFatherMobileNumber() + "'" +
            ", fatherEmailId='" + getFatherEmailId() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", signature='" + getSignature() + "'" +
            ", signatureContentType='" + getSignatureContentType() + "'" +
            ", adhar='" + getAdhar() + "'" +
            ", adharContentType='" + getAdharContentType() + "'" +
            "}";
    }
}
