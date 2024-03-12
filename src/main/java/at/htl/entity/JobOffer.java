package at.htl.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class JobOffer extends PanacheEntity {

    public String title;
    public String descr;
    public String category;
    public String condition;
    public Double salary;

    public JobOffer() {
    }

    @JoinColumn(nullable = false)
    @ManyToOne
    public Company company;

    public JobOffer(String title, String descr, String category, String condition, Double salary, Company company) {
        this.title = title;
        this.descr = descr;
        this.category = category;
        this.condition = condition;
        this.salary = salary;
        this.company = company;
    }
}
