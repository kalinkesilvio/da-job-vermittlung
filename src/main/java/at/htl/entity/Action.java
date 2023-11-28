package at.htl.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity
public class Action extends PanacheEntity {

    public String actionName;

    @JoinColumn
    @ManyToOne
    public Company company;

    @JoinColumn
    @ManyToOne
    public Applicant applicant;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    public LocalDateTime actionDate;

    public Action() {
    }

    public Action(String actionName, Company company, Applicant applicant, LocalDateTime actionDate) {
        this.actionName = actionName;
        this.company = company;
        this.applicant = applicant;
        this.actionDate = actionDate;
    }
}
