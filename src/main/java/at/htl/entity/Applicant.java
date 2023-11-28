package at.htl.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Date;

@Entity
public class Applicant extends PanacheEntity {

    public String firstName;
    public String lastName;

    public String email;

    public String password;

    public String resumeUrl;
    public String descr;
    public String skillDescr;
    public String interestDescr;
    public String jobField;
    public String jobBranche;
    public String preferableWork;

    @JsonbDateFormat("dd-MM-yyyy")
    public Date retirement;

    public int hoursPerWeek;
    public boolean commute;
    public String imageUrl;

    @JoinColumn
    @ManyToOne(cascade = CascadeType.ALL)
    public Address address;

    public Applicant() {
    }

    public Applicant(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Applicant(String firstName, String lastName, String email, String password, String resumeUrl, String descr, String skillDescr, String interestDescr, String jobField, String jobBranche, String preferableWork, Date retirement, int hoursPerWeek, boolean commute, String imageUrl, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.resumeUrl = resumeUrl;
        this.descr = descr;
        this.skillDescr = skillDescr;
        this.interestDescr = interestDescr;
        this.jobField = jobField;
        this.jobBranche = jobBranche;
        this.preferableWork = preferableWork;
        this.retirement = retirement;
        this.hoursPerWeek = hoursPerWeek;
        this.commute = commute;
        this.imageUrl = imageUrl;
        this.address = address;
    }
}
