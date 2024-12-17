package com.authenticate.Infosys_EDoctor.Controller;

import com.authenticate.Infosys_EDoctor.Entity.Appointment;
import com.authenticate.Infosys_EDoctor.Entity.Doctor;
import com.authenticate.Infosys_EDoctor.Entity.DoctorAvailability;
import com.authenticate.Infosys_EDoctor.Service.DoctorService;
import com.authenticate.Infosys_EDoctor.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/addProfile")
    public ResponseEntity<Doctor> addDoctor(@RequestBody Doctor doctor) {
        Doctor newDoctor = doctorService.addDoctor(doctor);

        notificationService.sendDoctorProfileCreatedNotification(newDoctor.getEmail(), newDoctor.getDoctorId());

        return ResponseEntity.ok(newDoctor);
    }

    @GetMapping("/viewProfile")
    public ResponseEntity<Doctor> getDoctorById(@RequestParam String doctorId) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        return ResponseEntity.ok(doctor);
    }

    @PutMapping("/updateProfile")
    public ResponseEntity<Doctor> updateDoctor(@RequestParam String doctorId, @RequestBody Doctor doctor) {
        Doctor updatedDoctor = doctorService.updateDoctor(doctorId, doctor);
        return ResponseEntity.ok(updatedDoctor);
    }

    @DeleteMapping("/deleteProfile")
    public ResponseEntity<String> deleteDoctor(@RequestParam String doctorId) {
        doctorService.deleteDoctor(doctorId);
        return ResponseEntity.ok("Doctor " + doctorId + " deleted successfully.");
    }

    @GetMapping("/{doctorId}/available-slots")
    public ResponseEntity<List<DoctorAvailability>> getAvailableSlots(@PathVariable String doctorId) {
        List<DoctorAvailability> availableSlots = doctorService.getAvailableSlots(doctorId);
        return ResponseEntity.ok(availableSlots);
    }

    @GetMapping("/specialized-doctors")
    public ResponseEntity<List<Doctor>> findDoctorBySpecialization(@RequestParam String specialization) {
        List<Doctor> doctors = doctorService.findDoctorsBySpecialization(specialization);
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/all-appointments/{doctorId}")
    public ResponseEntity<List<Appointment>> getAllAppointments(@PathVariable String doctorId) {
        List<Appointment> appointments = doctorService.getAllAppointments(doctorId);
        return ResponseEntity.ok(appointments);
    }

    @PutMapping("/confirm-appointment/{appointmentId}")
    public ResponseEntity<Appointment> confirmAppointment(@PathVariable Long appointmentId) {
        Appointment appointment = doctorService.confirmAppointment(appointmentId);

        notificationService.sendAppointmentConfirmationToPatient(appointment);

        return ResponseEntity.ok(appointment);
    }

    @PutMapping("/cancel-appointment/{appointmentId}")
    public ResponseEntity<String> cancelAppointment(@PathVariable Long appointmentId, @RequestParam String reason) {
        doctorService.cancelAppointment(appointmentId, reason);

        notificationService.sendAppointmentCancellationNotification(appointmentId, reason, true);

        return ResponseEntity.ok("Appointment cancelled successfully");
    }
}

