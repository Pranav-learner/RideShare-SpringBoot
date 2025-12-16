package com.pranav.rideshare.service;

import com.pranav.rideshare.dto.CreateRideRequest;
import com.pranav.rideshare.exception.BadRequestException;
import com.pranav.rideshare.exception.NotFoundException;
import com.pranav.rideshare.model.Ride;
import com.pranav.rideshare.model.User;
import com.pranav.rideshare.repository.RideRepository;
import com.pranav.rideshare.repository.UserRepository;
import com.pranav.rideshare.util.JwtUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RideService {

    private final RideRepository rideRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public RideService(RideRepository rideRepository, UserRepository userRepository, JwtUtil jwtUtil) {
        this.rideRepository = rideRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    private User getLoggedInUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public Ride createRide(CreateRideRequest request) {
        User user = getLoggedInUser();

        if (!user.getRole().equals("ROLE_USER")) {
            throw new BadRequestException("Only passengers can request a ride");
        }

        Ride ride = Ride.builder()
                .userId(user.getId())
                .pickupLocation(request.getPickupLocation())
                .dropLocation(request.getDropLocation())
                .status("REQUESTED")
                .createdAt(new Date())
                .build();

        return rideRepository.save(ride);
    }

    public List<Ride> getMyRides() {
        User user = getLoggedInUser();
        return rideRepository.findByUserId(user.getId());
    }

    public List<Ride> getPendingRidesForDriver() {
        User user = getLoggedInUser();
        if (!user.getRole().equals("ROLE_DRIVER")) {
            throw new BadRequestException("Only drivers can view pending rides");
        }
        return rideRepository.findByStatus("REQUESTED");
    }

    public Ride acceptRide(String rideId) {
        User driver = getLoggedInUser();
        if (!driver.getRole().equals("ROLE_DRIVER")) {
            throw new BadRequestException("Only drivers can accept rides");
        }

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));

        if (!ride.getStatus().equals("REQUESTED")) {
            throw new BadRequestException("Ride is not available to accept");
        }

        ride.setStatus("ACCEPTED");
        ride.setDriverId(driver.getId());

        return rideRepository.save(ride);
    }

    public Ride completeRide(String rideId) {
        User user = getLoggedInUser();

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));

        if (!ride.getStatus().equals("ACCEPTED")) {
            throw new BadRequestException("Ride not in progress");
        }

        ride.setStatus("COMPLETED");

        return rideRepository.save(ride);
    }
}
