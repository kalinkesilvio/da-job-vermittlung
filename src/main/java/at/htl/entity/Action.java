package at.htl.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity
public class Action extends PanacheEntity {

    public String actionName;

    @JoinColumn
    @ManyToOne(cascade = CascadeType.ALL)
    public Company company;

    @JoinColumn
    @ManyToOne(cascade = CascadeType.ALL)
    public Applicant applicant;

    public LocalDateTime actionDate;

    public Action() {
    }

    public Action(String actionName, Company company, Applicant applicant, LocalDateTime actionDate) {
        this.actionName = actionName;
        this.company = company;
        this.applicant = applicant;
        this.actionDate = actionDate;
    }

    //    public void setActionDate(LocalDateTime localDateTime) {
//        String serializedDateTime = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
//        this.actionDate = LocalDateTime.parse(serializedDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
//    }
}
