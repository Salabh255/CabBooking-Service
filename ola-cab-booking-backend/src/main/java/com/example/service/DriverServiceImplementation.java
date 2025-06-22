package com.example.service;

import com.example.config.JwtUtil;
import com.example.domain.RideStatus;
import com.example.domain.UserRole;
import com.example.exception.DriverException;
import com.example.model.Driver;
import com.example.model.License;
import com.example.model.Ride;
import com.example.model.Vehicle;
import com.example.repository.DriverRepository;
import com.example.repository.LicenseRepository;
import com.example.repository.RideRepository;
import com.example.repository.VehicleRepository;
import com.example.request.DriverSignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DriverServiceImplementation implements DriverService{

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private Calculators distanceCalculators;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private RideRepository rideRepository;


    @Override
    public Driver registerDriver(DriverSignupRequest driverSignupRequest) {
        License license = driverSignupRequest.getLicense();
        Vehicle vehicle = driverSignupRequest.getVehicle();

        License createdLicense = new License();

        createdLicense.setLicenseState(license.getLicenseState());
        createdLicense.setLicenseNumber(license.getLicenseNumber());
        createdLicense.setLicenseExpirationDate(license.getLicenseExpirationDate());
        createdLicense.setId(license.getId());

        License savedLicense = licenseRepository.save(createdLicense);

        Vehicle createdVehicle = new Vehicle();

        createdVehicle.setCapacity(vehicle.getCapacity());
        createdVehicle.setColor(vehicle.getColor());
        createdVehicle.setId(vehicle.getId());
        createdVehicle.setLicensePlate(vehicle.getLicensePlate());
        createdVehicle.setMake(vehicle.getMake());
        createdVehicle.setModel(vehicle.getModel());
        createdVehicle.setYear(vehicle.getYear());

        Vehicle savedVehicle = vehicleRepository.save(createdVehicle);

        Driver driver = new Driver();

        String encodedPassword = passwordEncoder.encode(driverSignupRequest.getPassword());

        driver.setEmail(driverSignupRequest.getEmail());
        driver.setName(driverSignupRequest.getName());
        driver.setMobile(driverSignupRequest.getMobile());
        driver.setPassword(encodedPassword);
        driver.setLicense(savedLicense);
        driver.setVehicle(savedVehicle);
        driver.setUserRole(UserRole.DRIVER);
        driver.setLatitude(driverSignupRequest.getLatitude());
        driver.setLongitude(driverSignupRequest.getLongitude());

        Driver createdDriver = driverRepository.save(driver);

        savedLicense.setDriver(createdDriver);
        savedVehicle.setDriver(createdDriver);

        licenseRepository.save(savedLicense);
        vehicleRepository.save(savedVehicle);

        return createdDriver;
    }


    @Override
    public List<Driver> getAvailableDrivers(double pickupLatitude, double pickupLongitude, Ride ride) {
        List<Driver>allDrivers = driverRepository.findAll();
        List<Driver> availableDriver = new ArrayList<>();

        for(Driver driver:allDrivers){
            if(driver.getCurrentRide()!=null && driver.getCurrentRide().getRideStatus()!= RideStatus.COMPLETED){
                continue;
            }
            if(ride.getDeclinedDrivers().contains(driver.getId())){
                System.out.println("its contained");
                continue;
            }

            double driverLatitude = driver.getLatitude();
            double driverLongitude = driver.getLongitude();

            availableDriver.add(driver);
        }
        return availableDriver;
    }

    @Override
    public Driver findNearestDriver(List<Driver> availableDrivers, double pickupLatitude, double pickupLongitude) {
        double min= Double.MAX_VALUE;
        Driver nearestDriver = null;

        for(Driver driver: availableDrivers){
            double driverLatitude = driver.getLatitude();
            double driverLongitude = driver.getLongitude();

            double distance = distanceCalculators.calculateDistance(pickupLatitude,pickupLongitude,driverLatitude,driverLongitude);

            if(min>distance){
                min=distance;
                nearestDriver=driver;
            }
        }
        return nearestDriver;
    }

    @Override
    public Driver getReqDriverProfile(String jwt) throws DriverException {
        String email = jwtUtil.getEmailFromJwt(jwt);
        Driver driver = driverRepository.findByEmail(email);
        if (driver == null) {
            throw new DriverException("driver not exist with email " + email);
        }
        return driver;
    }

    @Override
    public Ride getDriversCurrentRide(Integer driverId) throws DriverException {
        Driver driver = findDriverById(driverId);
        return driver.getCurrentRide();
    }

    @Override
    public List<Ride> getAllocatedRides(Integer driverId) throws DriverException {
        List<Ride>allocatedRides=driverRepository.getAllocatedRides(driverId);
        return allocatedRides;
    }

    @Override
    public Driver findDriverById(Integer driverId) throws DriverException {
        Optional<Driver> opt = driverRepository.findById(driverId);

        if(opt.isPresent()){
            return opt.get();
        }
        throw new DriverException("Driver did not exist with id"+driverId);
    }

    @Override
    public List<Ride> completedRides(Integer driverId) throws DriverException {
        List<Ride>rides=driverRepository.getCompletedRides(driverId);
        return rides;
    }
}
