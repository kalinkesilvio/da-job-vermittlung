package at.htl.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Company extends PanacheEntity {

    public String email;
    public String password;
    public String companyName;
    public String websiteUrl;
    public String branche;
    public String imageUrl;

    @JoinColumn
    @ManyToOne
    public Address address;

    public Company() {
    }

    public Company(String email, String password, String companyName) {
        this.email = email;
        this.password = password;
        this.companyName = companyName;
    }

    public Company(String email, String password, String companyName, String websiteUrl, String branche, Address address, String imageUrl) {
        this.email = email;
        this.password = password;
        this.companyName = companyName;
        this.websiteUrl = websiteUrl;
        this.branche = branche;
        this.address = address;
        this.imageUrl = imageUrl;
    }
}
