package com.authenticate.Infosys_EDoctor.Controller;

import com.authenticate.Infosys_EDoctor.Entity.Appointment;
import com.authenticate.Infosys_EDoctor.Entity.Doctor;
import com.authenticate.Infosys_EDoctor.Entity.DoctorAvailability;
import com.authenticate.Infosys_EDoctor.Entity.Patient;
import com.authenticate.Infosys_EDoctor.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    // 1. Add Profile
    @PostMapping("/addProfile")
    public ResponseEntity<Patient> addProfile(@RequestBody Patient patient) {
        return ResponseEntity.ok(patientService.addPatient(patient));
    }

    // 2. Update Profile
    @PutMapping("/updateProfile")
    public ResponseEntity<Patient> updateProfile(@RequestParam String patientId, @RequestBody Patient updatedPatient) {
        return ResponseEntity.ok(patientService.updateProfile(patientId, updatedPatient));
    }

    // 3. View Patient Details
    @GetMapping("/viewProfile")
    public ResponseEntity<Patient> viewPatientDetails(@RequestParam String patientId) {
        return ResponseEntity.ok(patientService.getPatientById(patientId));
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
    public ResponseEntity<Appointment> makeAppointment(@RequestBody Appointment appointment) {
        return ResponseEntity.ok(patientService.makeAppointment(appointment));
    }

    // 5. Update Appointment
    @PutMapping("/updateAppointment/{appointmentId}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long appointmentId, @RequestBody Appointment updatedAppointment) {
        return ResponseEntity.ok(patientService.updateAppointment(appointmentId, updatedAppointment));
    }

    // 6. Cancel Appointment
    @PutMapping("/cancelAppointment/{appointmentId}")
    public ResponseEntity<String> cancelAppointment(@PathVariable Long appointmentId, @RequestBody String reason) {
        patientService.cancelAppointment(appointmentId, reason);
        return ResponseEntity.ok("Appointment canceled successfully");
    }
}

