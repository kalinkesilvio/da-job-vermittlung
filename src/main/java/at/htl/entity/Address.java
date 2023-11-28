package at.htl.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Address extends PanacheEntity {

    public String street;
    public String streetNo;
    public int zipNo;
    public String city;
    public String country;
    public String state;

    public Address() {
    }

    public Address(String street, String streetNo, int zipNo, String city, String country, String state) {
        this.street = street;
        this.streetNo = streetNo;
        this.zipNo = zipNo;
        this.city = city;
        this.country = country;
        this.state = state;
    }
}
