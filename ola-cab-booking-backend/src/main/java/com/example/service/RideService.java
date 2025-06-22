package com.example.service;
import com.example.exception.RideException;
import com.example.exception.DriverException;
import com.example.model.Driver;
import com.example.model.Ride;
import com.example.model.User;
import com.example.request.RideRequest;

public interface RideService {

    public Ride requestRide(RideRequest rideRequest, User user) throws DriverException;

    public Ride createRideRequest(
            User user,
            Driver nearestDriver,
            double pickupLatitude,
            double pickupLongitude,
            double destinationLatitude,
            double destinationLongitude,
            String pickupArea,
            String destinationArea
    );

    public void acceptRide(Integer rideId) throws RideException;

    public void declineRide(Integer rideId, Integer driverId) throws RideException;

    public void startRide(Integer rideId, int otp) throws RideException;

    public void completeRide(Integer rideId) throws RideException;

    public void cancelRide(Integer rideId) throws RideException;

    public Ride findRideById(Integer rideId) throws RideException;



}
