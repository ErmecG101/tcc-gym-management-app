package br.com.matotvron.tccgymmanagementapp.background.models;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

import br.com.matotvron.tccgymmanagementapp.background.dtos.GymDTO;

public class User implements Serializable {

    private String id;
    private String name;
    private String userName;
    private String document;
    private String email;
    private String phoneNumber;
    private GymDTO gymDTO;


    public User() {

    }

    public User(String id, String name, String userName, String password, String document, String email, String phoneNumber, Gym gym) {
        super();
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.document = document;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gymDTO = new GymDTO(gym);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public GymDTO getGymDTO() {
        return gymDTO;
    }

    public void setGymDTO(GymDTO gymDTO) {
        this.gymDTO = gymDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", document='" + document + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gymDTO=" + gymDTO +
                '}';
    }
}

