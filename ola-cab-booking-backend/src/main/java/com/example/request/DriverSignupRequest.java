package com.example.request;

import com.example.model.License;
import com.example.model.Vehicle;
import jakarta.persistence.OneToOne;

public class DriverSignupRequest {

    private String name;
    private String email;
    private String mobile;
    private String password;
    private double latitude;
    private double longitude;

    @OneToOne(mappedBy="driver")
    private License license;

    @OneToOne(mappedBy="driver")
    private Vehicle vehicle;

    public DriverSignupRequest() {}

    // Getters and Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public License getLicense() {
        return license;
    }
    public void setLicense(License license) {
        this.license = license;
    }
    public Vehicle getVehicle() {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}








































//
//import com.example.model.License;
//import com.example.model.Vehicle;
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.OneToOne;
//
//public class DriverSignupRequest {
//
//    private String name;
//    private String email;
//    private String mobile;
//    private String password;
//    private double latitude;
//    private double longitude;
//
//    @OneToOne(mappedBy = "driver", cascade = CascadeType.ALL)
//    private License license;
//
//    @OneToOne(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Vehicle vehicle;
//
//    public DriverSignupRequest(){
//
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getMobile() {
//        return mobile;
//    }
//
//    public void setMobile(String mobile) {
//        this.mobile = mobile;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public double getLatitude() {
//        return latitude;
//    }
//
//    public void setLatitude(double latitude) {
//        this.latitude = latitude;
//    }
//
//    public double getLongitude() {
//        return longitude;
//    }
//
//    public void setLongitude(double longitude) {
//        this.longitude = longitude;
//    }
//
//    public License getLicense() {
//        return license;
//    }
//
//    public void setLicense(License license) {
//        this.license = license;
//    }
//
//    public Vehicle getVehicle() {
//        return vehicle;
//    }
//
//    public void setVehicle(Vehicle vehicle) {
//        this.vehicle = vehicle;
//    }
//}
