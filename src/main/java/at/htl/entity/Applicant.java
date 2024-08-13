package at.htl.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
public class Applicant extends PanacheEntityBase {

    //public static DateTimeFormatter CUSTOM_DATE = DateTimeFormatter.ISO_LOCAL_DATE;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String firstName;
    public String lastName;

    public String email;

    public String password;

    @Column(length = 10485768)
    public String resumeUrl;
    public String descr;
    public String skillDescr;
    public String interestDescr;
    public String jobField;
    public String jobBranche;
    public String preferableWork;

    //@JsonbDateFormat("dd-MM-yyyy")
    //public LocalDateTime retirement;

    public int hoursPerWeek;
    public boolean commute;

    @Column(length = 10485768)
    public String imageUrl;

    @JoinColumn
    @ManyToOne
    public Address address;

    public Applicant() {
    }

    public Applicant(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Applicant(String firstName, String lastName, String email, String password, String resumeUrl, String descr, String skillDescr, String interestDescr, String jobField, String jobBranche, String preferableWork, int hoursPerWeek, boolean commute, String imageUrl, Address address) {
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
        //this.retirement = LocalDateTime.parse(retirement, CUSTOM_DATE);
        this.hoursPerWeek = hoursPerWeek;
        this.commute = commute;
        this.imageUrl = imageUrl;
        this.address = address;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
