package com.example.repository;

import com.example.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.model.Ride;

import java.util.List;

public interface DriverRepository extends JpaRepository<Driver,Integer> {
    public Driver findByEmail(String email);

    @Query("SELECT R FROM Ride R WHERE R.rideStatus = com.example.domain.RideStatus.REQUESTED AND R.driver.id = :driverId")
    List<Ride> getAllocatedRides(@Param("driverId") Integer driverId);

    @Query("SELECT R FROM Ride R WHERE R.rideStatus = com.example.domain.RideStatus.COMPLETED AND R.driver.id = :driverId")
    List<Ride> getCompletedRides(@Param("driverId") Integer driverId);

}
