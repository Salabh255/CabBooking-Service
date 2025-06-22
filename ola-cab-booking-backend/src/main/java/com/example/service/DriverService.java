package com.example.service;

import com.example.exception.DriverException;
import com.example.model.Driver;
import com.example.model.Ride;
import com.example.request.DriverSignupRequest;

import java.util.List;

public interface DriverService {

    public Driver registerDriver(DriverSignupRequest driverSignupRequest);
    public List<Driver> getAvailableDrivers(double pickupLatitude,
                                            double pickupLongitude, Ride ride);

    public Driver findNearestDriver(List<Driver> availableDrivers,
                                    double pickupLatitude, double pickupLongitude);

    public Driver getReqDriverProfile(String jwt) throws DriverException;
    public Ride getDriversCurrentRide(Integer driverId) throws DriverException;
    public List<Ride> getAllocatedRides(Integer driverId) throws DriverException;
    public Driver findDriverById(Integer driverId) throws DriverException;
    public List<Ride> completedRides(Integer driverId) throws DriverException;

}
