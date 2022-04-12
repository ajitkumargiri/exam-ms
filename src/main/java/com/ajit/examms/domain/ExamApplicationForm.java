package com.ajit.examms.domain;

import com.ajit.examms.domain.enumeration.BloodGroup;
import com.ajit.examms.domain.enumeration.Gender;
import com.ajit.examms.domain.enumeration.IdentityType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ExamApplicationForm.
 */
@Entity
@Table(name = "exam_application_form")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ExamApplicationForm implements Serializable {

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

    @NotNull
    @Column(name = "adhar_number", nullable = false, unique = true)
    private String adharNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_group")
    private BloodGroup bloodGroup;

    @Column(name = "is_approved")
    private Boolean isApproved;

    @Column(name = "catagory")
    private String catagory;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "identity_type", nullable = false)
    private IdentityType identityType;

    @NotNull
    @Column(name = "identity_number", nullable = false, unique = true)
    private String identityNumber;

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

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "sign_path")
    private String signPath;

    @Column(name = "adhar_path")
    private String adharPath;

    @OneToOne
    @JoinColumn(unique = true)
    private Address correspondingAddress;

    @ManyToOne
    @JsonIgnoreProperties(value = { "examApplicationForms", "academicBatch" }, allowSetters = true)
    private Student student;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "correspondingAddress", "permanentAddress", "applicationForms", "examCenters", "session" },
        allowSetters = true
    )
    private Exam exam;

    @ManyToOne
    @JsonIgnoreProperties(value = { "address", "examApplicationForms", "exam" }, allowSetters = true)
    private ExamCenter examCenter;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExamApplicationForm id(Long id) {
        this.id = id;
        return this;
    }

    public String getRegNumber() {
        return this.regNumber;
    }

    public ExamApplicationForm regNumber(String regNumber) {
        this.regNumber = regNumber;
        return this;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public ExamApplicationForm firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public ExamApplicationForm middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public ExamApplicationForm lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Instant getDob() {
        return this.dob;
    }

    public ExamApplicationForm dob(Instant dob) {
        this.dob = dob;
        return this;
    }

    public void setDob(Instant dob) {
        this.dob = dob;
    }

    public String getFatherName() {
        return this.fatherName;
    }

    public ExamApplicationForm fatherName(String fatherName) {
        this.fatherName = fatherName;
        return this;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getEmail() {
        return this.email;
    }

    public ExamApplicationForm email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public ExamApplicationForm mobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        return this;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getNationality() {
        return this.nationality;
    }

    public ExamApplicationForm nationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Gender getGender() {
        return this.gender;
    }

    public ExamApplicationForm gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getReligion() {
        return this.religion;
    }

    public ExamApplicationForm religion(String religion) {
        this.religion = religion;
        return this;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getAdharNumber() {
        return this.adharNumber;
    }

    public ExamApplicationForm adharNumber(String adharNumber) {
        this.adharNumber = adharNumber;
        return this;
    }

    public void setAdharNumber(String adharNumber) {
        this.adharNumber = adharNumber;
    }

    public BloodGroup getBloodGroup() {
        return this.bloodGroup;
    }

    public ExamApplicationForm bloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
        return this;
    }

    public void setBloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public Boolean getIsApproved() {
        return this.isApproved;
    }

    public ExamApplicationForm isApproved(Boolean isApproved) {
        this.isApproved = isApproved;
        return this;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public String getCatagory() {
        return this.catagory;
    }

    public ExamApplicationForm catagory(String catagory) {
        this.catagory = catagory;
        return this;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public IdentityType getIdentityType() {
        return this.identityType;
    }

    public ExamApplicationForm identityType(IdentityType identityType) {
        this.identityType = identityType;
        return this;
    }

    public void setIdentityType(IdentityType identityType) {
        this.identityType = identityType;
    }

    public String getIdentityNumber() {
        return this.identityNumber;
    }

    public ExamApplicationForm identityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
        return this;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public byte[] getImage() {
        return this.image;
    }

    public ExamApplicationForm image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return this.imageContentType;
    }

    public ExamApplicationForm imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public byte[] getSignature() {
        return this.signature;
    }

    public ExamApplicationForm signature(byte[] signature) {
        this.signature = signature;
        return this;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public String getSignatureContentType() {
        return this.signatureContentType;
    }

    public ExamApplicationForm signatureContentType(String signatureContentType) {
        this.signatureContentType = signatureContentType;
        return this;
    }

    public void setSignatureContentType(String signatureContentType) {
        this.signatureContentType = signatureContentType;
    }

    public byte[] getAdhar() {
        return this.adhar;
    }

    public ExamApplicationForm adhar(byte[] adhar) {
        this.adhar = adhar;
        return this;
    }

    public void setAdhar(byte[] adhar) {
        this.adhar = adhar;
    }

    public String getAdharContentType() {
        return this.adharContentType;
    }

    public ExamApplicationForm adharContentType(String adharContentType) {
        this.adharContentType = adharContentType;
        return this;
    }

    public void setAdharContentType(String adharContentType) {
        this.adharContentType = adharContentType;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public ExamApplicationForm imagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getSignPath() {
        return this.signPath;
    }

    public ExamApplicationForm signPath(String signPath) {
        this.signPath = signPath;
        return this;
    }

    public void setSignPath(String signPath) {
        this.signPath = signPath;
    }

    public String getAdharPath() {
        return this.adharPath;
    }

    public ExamApplicationForm adharPath(String adharPath) {
        this.adharPath = adharPath;
        return this;
    }

    public void setAdharPath(String adharPath) {
        this.adharPath = adharPath;
    }

    public Address getCorrespondingAddress() {
        return this.correspondingAddress;
    }

    public ExamApplicationForm correspondingAddress(Address address) {
        this.setCorrespondingAddress(address);
        return this;
    }

    public void setCorrespondingAddress(Address address) {
        this.correspondingAddress = address;
    }

    public Student getStudent() {
        return this.student;
    }

    public ExamApplicationForm student(Student student) {
        this.setStudent(student);
        return this;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Exam getExam() {
        return this.exam;
    }

    public ExamApplicationForm exam(Exam exam) {
        this.setExam(exam);
        return this;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public ExamCenter getExamCenter() {
        return this.examCenter;
    }

    public ExamApplicationForm examCenter(ExamCenter examCenter) {
        this.setExamCenter(examCenter);
        return this;
    }

    public void setExamCenter(ExamCenter examCenter) {
        this.examCenter = examCenter;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExamApplicationForm)) {
            return false;
        }
        return id != null && id.equals(((ExamApplicationForm) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExamApplicationForm{" +
            "id=" + getId() +
            ", regNumber='" + getRegNumber() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", dob='" + getDob() + "'" +
            ", fatherName='" + getFatherName() + "'" +
            ", email='" + getEmail() + "'" +
            ", mobileNumber='" + getMobileNumber() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", gender='" + getGender() + "'" +
            ", religion='" + getReligion() + "'" +
            ", adharNumber='" + getAdharNumber() + "'" +
            ", bloodGroup='" + getBloodGroup() + "'" +
            ", isApproved='" + getIsApproved() + "'" +
            ", catagory='" + getCatagory() + "'" +
            ", identityType='" + getIdentityType() + "'" +
            ", identityNumber='" + getIdentityNumber() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", signature='" + getSignature() + "'" +
            ", signatureContentType='" + getSignatureContentType() + "'" +
            ", adhar='" + getAdhar() + "'" +
            ", adharContentType='" + getAdharContentType() + "'" +
            ", imagePath='" + getImagePath() + "'" +
            ", signPath='" + getSignPath() + "'" +
            ", adharPath='" + getAdharPath() + "'" +
            "}";
    }
}
