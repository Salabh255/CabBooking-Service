package com.example.controller;

import com.example.Dtos.RideDTO;
import com.example.Dtos.mapper.DtoMapper;
import com.example.exception.DriverException;
import com.example.exception.RideException;
import com.example.exception.UserException;
import com.example.model.Driver;
import com.example.model.Ride;
import com.example.model.User;
import com.example.request.RideRequest;
import com.example.request.startRideRequest;
import com.example.response.MessageResponse;
import com.example.service.DriverService;
import com.example.service.RideService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rides")
public class RideController {

    @Autowired
    private RideService rideService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private UserService userService;

    @PostMapping("/request")
    public ResponseEntity<RideDTO>userRideRequestHandler(@RequestBody RideRequest rideRequest, @RequestHeader("Authorization") String jwt) throws UserException, DriverException {
        User user=userService.getReqUserProfile(jwt);
        Ride ride=rideService.requestRide(rideRequest,user);
        RideDTO rideDTO= DtoMapper.toRideDto(ride);
        return new ResponseEntity<>(rideDTO, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{rideId}/accept")
    public ResponseEntity<MessageResponse> acceptRideHandler(@PathVariable Integer rideId)
            throws UserException, RideException {

        rideService.acceptRide(rideId);

        MessageResponse res = new MessageResponse("Ride Accepted By Driver!");
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{rideId}/decline")
    public ResponseEntity<MessageResponse> declineRideHandler(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Integer rideId) throws UserException, RideException, DriverException {

        Driver driver = driverService.getReqDriverProfile(jwt);

        rideService.declineRide(rideId, driver.getId());

        MessageResponse res = new MessageResponse("Ride Declined By Driver!");
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{rideId}/start")
    public ResponseEntity<MessageResponse> rideStartHandler(
            @PathVariable Integer rideId, @RequestBody startRideRequest req) throws UserException, RideException, DriverException {

        rideService.startRide(rideId, req.getOtp());

        MessageResponse res = new MessageResponse("Ride is started!");
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{rideId}/complete")
    public ResponseEntity<MessageResponse> rideCompleteHandler(@PathVariable Integer rideId)
            throws UserException, RideException {

        rideService.completeRide(rideId);

        MessageResponse res = new MessageResponse("Ride Is Completed. Thank You For Booking Cab!");
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{rideId}")
    public ResponseEntity<RideDTO>findRideByIdHandler(@PathVariable Integer rideId, @RequestHeader("Authorization") String jwt) throws UserException, RideException {
        User user=userService.getReqUserProfile(jwt);
        Ride ride=rideService.findRideById(rideId);
        RideDTO rideDTO= DtoMapper.toRideDto(ride);
        return new ResponseEntity<>(rideDTO, HttpStatus.ACCEPTED);
    }

}
