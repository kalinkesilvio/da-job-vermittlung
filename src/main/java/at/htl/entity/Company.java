package at.htl.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
public class Company extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String companyName;
    private String websiteUrl;
    private String branche;
    private String imageUrl;

    @JoinColumn
    @ManyToOne
    private Address address;

    public Company() {
    }

    public Company(String email, String password, String companyName) {
        this.setEmail(email);
        this.setPassword(password);
        this.setCompanyName(companyName);
    }

    public Company(String email, String password, String companyName, String websiteUrl, String branche, Address address, String imageUrl) {
        this.setEmail(email);
        this.setPassword(password);
        this.setCompanyName(companyName);
        this.setWebsiteUrl(websiteUrl);
        this.setBranche(branche);
        this.setAddress(address);
        this.setImageUrl(imageUrl);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getBranche() {
        return branche;
    }

    public void setBranche(String branche) {
        this.branche = branche;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
