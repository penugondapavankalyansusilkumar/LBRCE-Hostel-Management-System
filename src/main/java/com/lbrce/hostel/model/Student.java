package com.lbrce.hostel.model;

public class Student {

    private int id;
    private int userId;
    private String rollNumber;
    private String name;
    private String email;
    private String phone;
    private String department;
    private int yearOfStudy;
    private String gender;
    private String address;
    private int roomId;

    public Student() {
    }

    public Student(int id, String rollNumber, String name, String email,
                   String phone, String department, int yearOfStudy,
                   String gender, String address, int roomId) {
        this.id = id;
        this.rollNumber = rollNumber;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.yearOfStudy = yearOfStudy;
        this.gender = gender;
        this.address = address;
        this.roomId = roomId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Compatibility methods for existing DAO and API code
    public String getFullName() {
        return name;
    }

    public void setFullName(String fullName) {
        this.name = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCourse() {
        return department;
    }

    public void setCourse(String course) {
        this.department = course;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public String getYear() {
        return String.valueOf(yearOfStudy);
    }

    public void setYear(String year) {
        if (year != null && !year.trim().isEmpty()) {
            this.yearOfStudy = Integer.parseInt(year.trim());
        }
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}