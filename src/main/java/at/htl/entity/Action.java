package at.htl.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Action extends PanacheEntity {

    public String actionName;

    @JoinColumn
    @ManyToOne
    public Company company;

    @JoinColumn
    @ManyToOne
    public Applicant applicant;

    @Column(columnDefinition = "TIMESTAMP")
    @JsonbDateFormat(value = "yyyy-MM-dd HH:mm:ss")
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
