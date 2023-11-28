package at.htl.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Application extends PanacheEntity {

    @JoinColumn
    @ManyToOne(cascade = CascadeType.ALL)
    public Applicant applicant;

    @JoinColumn
    @ManyToOne(cascade = CascadeType.ALL)
    public JobOffer jobOffer;
}
