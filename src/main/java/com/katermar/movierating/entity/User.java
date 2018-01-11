package com.katermar.movierating.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by katermar on 12/31/2017.
 */
public class User implements Serializable {
    private int id;
    private String login;
    private String password;
    private String email;
    private String realName;
    private Timestamp dateOfRegistration;
    private Date dateOfBirth;
    private Date banExpirationDate;
    private UserRole role;
    private UserStatus status;

    public User() {
    }

    public User(int id, String login, String password, String email, String realName, Timestamp dateOfRegistration, Date dateOfBirth, Date banExpirationDate, UserRole role, UserStatus status) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.realName = realName;
        this.dateOfRegistration = dateOfRegistration;
        this.dateOfBirth = dateOfBirth;
        this.banExpirationDate = banExpirationDate;
        this.role = role;
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", realName='" + realName + '\'' +
                ", dateOfRegistration=" + dateOfRegistration +
                ", dateOfBirth=" + dateOfBirth +
                ", banExpirationDate=" + banExpirationDate +
                ", role=" + role +
                ", status=" + status +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Timestamp getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(Timestamp dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getBanExpirationDate() {
        return banExpirationDate;
    }

    public void setBanExpirationDate(Date banExpirationDate) {
        this.banExpirationDate = banExpirationDate;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public boolean isBaned() {
        return status.equals(UserStatus.BANED);
    }

    /**
     * Created by katermar on 12/31/2017.
     */
    public enum UserRole {
        ADMIN, USER
    }

    /**
     * Created by katermar on 12/31/2017.
     */
    public enum UserStatus {
        BANED, UNBANED, UNCONFIRMED
    }
}
