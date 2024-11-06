package at.htl.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
public class Applicant extends PanacheEntityBase {

    //public static DateTimeFormatter CUSTOM_DATE = DateTimeFormatter.ISO_LOCAL_DATE;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    private String email;

    private String password_login;

    @Column(length = 10485768)
    private String resumeUrl;
    private String descr;
    private String skillDescr;
    private String interestDescr;
    private String jobField;
    private String jobBranche;
    private String preferableWork;

    //@JsonbDateFormat("dd-MM-yyyy")
    //public LocalDateTime retirement;

    private int hoursPerWeek;
    private boolean commute;

    @Column(length = 10485768)
    private String imageUrl;

    @JoinColumn
    @ManyToOne
    private Address address;

    public Applicant() {
    }

    public Applicant(String email, String password_login) {
        this.setEmail(email);
        this.setPassword(password_login
);
    }

    public Applicant(String firstName, String lastName, String email, String password_login, String resumeUrl, String descr, String skillDescr, String interestDescr, String jobField, String jobBranche, String preferableWork, int hoursPerWeek, boolean commute, String imageUrl, Address address) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPassword(password_login
);
        this.setResumeUrl(resumeUrl);
        this.setDescr(descr);
        this.setSkillDescr(skillDescr);
        this.setInterestDescr(interestDescr);
        this.setJobField(jobField);
        this.setJobBranche(jobBranche);
        this.setPreferableWork(preferableWork);
        //this.retirement = LocalDateTime.parse(retirement, CUSTOM_DATE);
        this.setHoursPerWeek(hoursPerWeek);
        this.setCommute(commute);
        this.setImageUrl(imageUrl);
        this.setAddress(address);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password_login
;
    }

    public void setPassword(String password_login) {
        this.password_login
 = password_login
;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getSkillDescr() {
        return skillDescr;
    }

    public void setSkillDescr(String skillDescr) {
        this.skillDescr = skillDescr;
    }

    public String getInterestDescr() {
        return interestDescr;
    }

    public void setInterestDescr(String interestDescr) {
        this.interestDescr = interestDescr;
    }

    public String getJobField() {
        return jobField;
    }

    public void setJobField(String jobField) {
        this.jobField = jobField;
    }

    public String getJobBranche() {
        return jobBranche;
    }

    public void setJobBranche(String jobBranche) {
        this.jobBranche = jobBranche;
    }

    public String getPreferableWork() {
        return preferableWork;
    }

    public void setPreferableWork(String preferableWork) {
        this.preferableWork = preferableWork;
    }

    public int getHoursPerWeek() {
        return hoursPerWeek;
    }

    public void setHoursPerWeek(int hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }

    public boolean isCommute() {
        return commute;
    }

    public void setCommute(boolean commute) {
        this.commute = commute;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
