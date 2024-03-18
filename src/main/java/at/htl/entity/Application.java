package at.htl.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Application extends PanacheEntity {

    @JoinColumn
    @ManyToOne
    public Applicant applicant;

    @JoinColumn
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    public JobOffer jobOffer;

    public String statusText;

    public Application() {
    }

    public Application(Applicant applicant, JobOffer jobOffer, String statusText) {
        this.applicant = applicant;
        this.jobOffer = jobOffer;
        this.statusText = statusText;
    }
}
