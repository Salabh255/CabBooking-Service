package com.example.repository;

import com.example.model.Ride;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    public User findByEmail(String email);

    @Query("SELECT R FROM Ride R WHERE R.rideStatus = com.example.domain.RideStatus.COMPLETED AND R.user.id = :userId")
    List<Ride> getCompletedRides(@Param("userId") Integer userId);
}
