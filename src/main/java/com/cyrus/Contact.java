package com.cyrus;

import java.time.LocalDate;

public class Contact {
    private Long id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String idNumber;
    private LocalDate dateOfBirth;
    private String gender;
    private String countyOfResidence;

    

    public Contact(Long id, String fullName, String phoneNumber, String email, String idNumber, LocalDate dateOfBirth,
            String gender, String countyOfResidence) {
                this.id = id;
                this.fullName = fullName;
                this.phoneNumber = phoneNumber;
                this.email = email;
                this.idNumber = idNumber;
                this.dateOfBirth = dateOfBirth;
                this.gender = gender;
                this.countyOfResidence = countyOfResidence;
    }
    
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getIdNumber() {
        return idNumber;
    }
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getCountyOfResidence() {
        return countyOfResidence;
    }
    public void setCountyOfResidence(String countyOfResidence) {
        this.countyOfResidence = countyOfResidence;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    


}
