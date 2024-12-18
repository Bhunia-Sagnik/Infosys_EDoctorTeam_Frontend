package com.authenticate.Infosys_EDoctor.Controller;

import com.authenticate.Infosys_EDoctor.Entity.Doctor;
import com.authenticate.Infosys_EDoctor.Entity.DoctorAvailability;
import com.authenticate.Infosys_EDoctor.Entity.User;
import com.authenticate.Infosys_EDoctor.Service.DoctorAvailabilityService;
import com.authenticate.Infosys_EDoctor.Service.DoctorService;
import com.authenticate.Infosys_EDoctor.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctor/availability")
public class DoctorAvailabilityController {

    @Autowired
    private DoctorAvailabilityService availabilityService;

    @Autowired
    private UserService userService;

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/add")
    public ResponseEntity<?> addAvailability(@RequestBody DoctorAvailability availability, HttpSession session) {
        // Check if the doctor is logged in
        if (session.getAttribute("userId") == null) {
            return ResponseEntity.status(403).body("You must be logged in to add a profile.");
        }

        // Check if profile already exists
        User user = userService.getUserById((long)session.getAttribute("userId"));
        Doctor existingDoctor = doctorService.getDoctorByEmail(user.getEmail());
        if (existingDoctor == null) {
            return ResponseEntity.badRequest().body("You already have a profile, you can update it.");
        }

        availability.setDoctorId(existingDoctor.getDoctorId());

        DoctorAvailability savedAvailability = availabilityService.addAvailability(availability);
        return ResponseEntity.ok(savedAvailability);
    }

    @GetMapping("/view")
    public ResponseEntity<?> getAvailability(@RequestParam String doctorId) {
        List<DoctorAvailability> availabilities = availabilityService.getAvailabilityByDoctorId(doctorId);
        return ResponseEntity.ok(availabilities);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAvailability(@RequestParam int id, @RequestBody DoctorAvailability availability) {
        DoctorAvailability updatedAvailability = availabilityService.updateAvailability(id, availability);
        return ResponseEntity.ok(updatedAvailability);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAvailability(@RequestParam int id) {
        boolean deleted = availabilityService.deleteAvailability(id);
        return deleted? ResponseEntity.ok("Availability deleted successfully"): ResponseEntity.badRequest().body("Enter valid credentials");
    }
}

