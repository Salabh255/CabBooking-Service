package com.example.Dtos.mapper;

import com.example.Dtos.DriverDTO;
import com.example.Dtos.RideDTO;
import com.example.Dtos.UserDTO;
import com.example.model.Driver;
import com.example.model.Ride;
import com.example.model.User;

public class DtoMapper {

    public static DriverDTO toDriverDto(Driver driver) {
        DriverDTO driverDto = new DriverDTO();

        driverDto.setEmail(driver.getEmail());
        driverDto.setId(driver.getId());
        driverDto.setLatitude(driver.getLatitude());
        driverDto.setLongitude(driver.getLongitude());
        driverDto.setMobile(driver.getMobile());
        driverDto.setName(driver.getName());
        driverDto.setRating(driver.getRating());
        driverDto.setRole(driver.getUserRole());
        driverDto.setVehicle(driver.getVehicle());

        return driverDto;
    }

    public static UserDTO toUserDto(User user){
        UserDTO userDTO=new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setId(user.getId());
        userDTO.setMobile(user.getMobile());
        userDTO.setName(user.getFullName());

        return userDTO;
    }

    public static RideDTO toRideDto(Ride ride) {
        DriverDTO driverDTO = toDriverDto(ride.getDriver());
        UserDTO userDto = toUserDto(ride.getUser());

        RideDTO rideDto = new RideDTO();

        rideDto.setDestinationLatitude(ride.getDestinationLatitude());
        rideDto.setDestinationLongitude(ride.getDestinationLongitude());
        rideDto.setDistance(ride.getDistance());
        rideDto.setDriver(driverDTO);
        rideDto.setDuration(ride.getDuration());
        rideDto.setEndTime(ride.getEndTime());
        rideDto.setFare(ride.getFare());
        rideDto.setId(ride.getId());
        rideDto.setPickupLatitude(ride.getPickupLatitude());
        rideDto.setPickupLongitude(ride.getPickupLongitude());
        rideDto.setStartTime(ride.getStartTime());
        rideDto.setStatus(ride.getRideStatus());
        rideDto.setUser(userDto);
        rideDto.setPickupArea(ride.getPickupArea());
        rideDto.setDestinationArea(ride.getDestinationArea());
        rideDto.setOtp(ride.getOtp());

        return rideDto;
    }

}

