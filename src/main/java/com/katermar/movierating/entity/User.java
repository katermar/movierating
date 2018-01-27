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
    private String avatar;
    private Timestamp dateOfRegistration;
    private Date dateOfBirth;
    private Date banExpirationDate;
    private UserRole role;
    private UserStatus status;
    private int levelPoints;
    private UserLevel level;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getLevelPoints() {
        return levelPoints;
    }

    public void setLevelPoints(int levelPoints) {
        this.levelPoints = levelPoints;
    }

    public void incrementLevelPoints() {
        levelPoints++;
    }

    public void incrementLevelPoints(int points) {
        levelPoints += points;
    }

    public void decrementLevelPoints(int points) {
        levelPoints -= points;
    }

    public void decrementLevelPoints() {
        levelPoints--;
    }

    public UserLevel getLevel() {
        return level;
    }

    public void setLevel(UserLevel level) {
        this.level = level;
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

    /**
     * Created by katermar on 12/31/2017.
     */
    public enum UserLevel {
        BEGINNER, INTERMEDIATE, ADVANCED, EXPERT;

        public static UserLevel getLevel(int points) {
            if (points < 10) {
                return BEGINNER;
            } else if (points < 20) {
                return INTERMEDIATE;
            } else if (points < 30) {
                return ADVANCED;
            } else {
                return EXPERT;
            }
        }
    }
}
