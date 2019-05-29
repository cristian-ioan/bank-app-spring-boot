package com.banking.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "person", schema = "bank-app")
public class Person extends IdModel {

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="address", length = 50)
    private String address;

    @Column(name = "first_name", length = 16)
    private String firstName;

    @Column(name = "last_name", length = 16)
    private String lastName;

    @Column(name = "email", length = 50)
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(getUser(), person.getUser()) &&
                Objects.equals(getAddress(), person.getAddress()) &&
                Objects.equals(getFirstName(), person.getFirstName()) &&
                Objects.equals(getLastName(), person.getLastName()) &&
                Objects.equals(getEmail(), person.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getAddress(), getFirstName(), getLastName(), getEmail());
    }

}
