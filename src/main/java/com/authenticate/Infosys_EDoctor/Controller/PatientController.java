package com.authenticate.Infosys_EDoctor.Controller;

import com.authenticate.Infosys_EDoctor.Entity.*;
import com.authenticate.Infosys_EDoctor.Service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    private UserService userService;

    @Autowired
    private PatientService patientService;

    @Autowired
    NotificationService notificationService;

    // 1. Add Profile
    @PostMapping("/addProfile")
    public ResponseEntity<?> addProfile(@RequestBody Patient patient, HttpSession session) {
        // Check if the doctor is logged in
        if (session.getAttribute("userId") == null) {
            return ResponseEntity.status(403).body("You must be logged in.");
        }

        // Check if profile already exists
        User user = userService.getUserById((long)session.getAttribute("userId"));
        Optional<Patient> existingPatient = patientService.getPatientByEmail(user.getEmail());
        if (existingPatient.isPresent()) {
            return ResponseEntity.badRequest().body("You already have a profile, you can update it.");
        }

        // Create profile
        patient.setPassword(user.getPassword());
        patient.setEmail(user.getEmail());
        Patient savedPatient = patientService.addPatient(patient);

        notificationService.sendPatientProfileCreatedNotification(savedPatient.getEmail(), savedPatient.getPatientId());

        return ResponseEntity.ok(savedPatient);
    }

    // 2. Update Profile
    @PutMapping("/updateProfile")
    public ResponseEntity<?> updateProfile(@RequestBody Patient patient, HttpSession session) {
        // Check if the doctor is logged in
        if (session.getAttribute("userId") == null) {
            return ResponseEntity.status(403).body("You must be logged in.");
        }

        // Check if profile already exists
        User user = userService.getUserById((long)session.getAttribute("userId"));
        Optional<Patient> existingPatient = patientService.getPatientByEmail(user.getEmail());
        if (existingPatient.isEmpty()) {
            return ResponseEntity.badRequest().body("You don't have a profile. Create one first.");
        }

        Patient oldPatient = existingPatient.get();

        // Update profile
        Patient updatedPatient = patientService.updateProfile(oldPatient.getPatientId(), patient);
        return ResponseEntity.ok(updatedPatient);
    }

    // 3. View Patient Details
    @GetMapping("/viewProfile")
    public ResponseEntity<?> viewPatientDetails(HttpSession session) {
        // Check if the doctor is logged in
        if (session.getAttribute("userId") == null) {
            return ResponseEntity.status(403).body("You must be logged in.");
        }

        // Check if profile already exists
        User user = userService.getUserById((long)session.getAttribute("userId"));
        Optional<Patient> existingPatient = patientService.getPatientByEmail(user.getEmail());
        if (existingPatient.isEmpty()) {
            return ResponseEntity.badRequest().body("You don't have a profile. Create one first.");
        }

        Patient oldPatient = existingPatient.get();

        return ResponseEntity.ok(patientService.getPatientById(oldPatient.getPatientId()));
    }

    // 3. Find Doctors
    @GetMapping("/findDoctors")
    public ResponseEntity<List<Doctor>> findDoctors() {
        List<Doctor> doctors = patientService.findDoctors();
        return ResponseEntity.ok(doctors);
    }

    // Find Doctors by Specialization
    @GetMapping("/findDoctorsBySpecialization")
    public ResponseEntity<List<Doctor>> findDoctorsBySpecialization(@RequestParam String specialization) {
        return ResponseEntity.ok(patientService.findDoctorsBySpecialization(specialization));
    }

    // Check Available Dates for a Particular Doctor
    @GetMapping("/doctorAvailableDates")
    public ResponseEntity<List<DoctorAvailability>> getAvailableDates(@RequestParam String doctorId) {
        return ResponseEntity.ok(patientService.getAvailableDates(doctorId));
    }

    // 4. Make Appointment
    @PostMapping("/makeAppointment")
    public ResponseEntity<?> makeAppointment(@RequestBody Appointment appointment, HttpSession session) {
        // Check if the doctor is logged in
        if (session.getAttribute("userId") == null) {
            return ResponseEntity.status(403).body("You must be logged in.");
        }

        // Check if profile already exists
        User user = userService.getUserById((long)session.getAttribute("userId"));
        Optional<Patient> existingPatient = patientService.getPatientByEmail(user.getEmail());
        if (existingPatient.isEmpty()) {
            return ResponseEntity.badRequest().body("You don't have a profile. Create one first.");
        }

        Appointment scheduled = patientService.makeAppointment(appointment);

        notificationService.sendNewAppointmentNotificationToDoctor(scheduled);

        return ResponseEntity.ok(scheduled);
    }

    @GetMapping("/viewAppointments")
    public ResponseEntity<?> viewAppointments(HttpSession session) {
        // Check if the doctor is logged in
        if (session.getAttribute("userId") == null) {
            return ResponseEntity.status(403).body("You must be logged in.");
        }

        // Check if profile already exists
        User user = userService.getUserById((long)session.getAttribute("userId"));
        Optional<Patient> existingPatient = patientService.getPatientByEmail(user.getEmail());
        if (existingPatient.isEmpty()) {
            return ResponseEntity.badRequest().body("You don't have a profile. Create one first.");
        }

        Patient oldPatient = existingPatient.get();

        return ResponseEntity.ok(patientService.viewAppointments(oldPatient.getPatientId()));
    }

    // 5. Update Appointment
    @PutMapping("/updateAppointment/{appointmentId}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long appointmentId, @RequestBody Appointment updatedAppointment) {
        return ResponseEntity.ok(patientService.updateAppointment(appointmentId, updatedAppointment));
    }

    // 6. Cancel Appointment
    @PutMapping("/cancelAppointment/{appointmentId}")
    public ResponseEntity<String> cancelAppointment(@PathVariable Long appointmentId, @RequestParam String reason) {
        patientService.cancelAppointment(appointmentId, reason);

        notificationService.sendAppointmentCancellationNotification(appointmentId, reason, false);

        return ResponseEntity.ok("Appointment canceled successfully");
    }
}

