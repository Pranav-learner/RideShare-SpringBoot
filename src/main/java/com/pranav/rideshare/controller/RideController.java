package com.pranav.rideshare.controller;

import com.pranav.rideshare.dto.CreateRideRequest;
import com.pranav.rideshare.model.Ride;
import com.pranav.rideshare.service.RideService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    // USER - Create Ride Request
    @PostMapping("/rides")
    public ResponseEntity<Ride> createRide(@Valid @RequestBody CreateRideRequest request) {
        return ResponseEntity.ok(rideService.createRide(request));
    }

    // USER - View My Rides
    @GetMapping("/user/rides")
    public ResponseEntity<List<Ride>> getMyRides() {
        return ResponseEntity.ok(rideService.getMyRides());
    }

    // DRIVER - View Pending Rides
    @GetMapping("/driver/rides/requests")
    public ResponseEntity<List<Ride>> getPendingRides() {
        return ResponseEntity.ok(rideService.getPendingRidesForDriver());
    }

    // DRIVER - Accept Ride
    @PostMapping("/driver/rides/{rideId}/accept")
    public ResponseEntity<Ride> acceptRide(@PathVariable String rideId) {
        return ResponseEntity.ok(rideService.acceptRide(rideId));
    }

    // USER or DRIVER - Complete Ride
    @PostMapping("/rides/{rideId}/complete")
    public ResponseEntity<Ride> completeRide(@PathVariable String rideId) {
        return ResponseEntity.ok(rideService.completeRide(rideId));
    }
}
