package com.authenticate.Infosys_EDoctor.Controller;

import com.authenticate.Infosys_EDoctor.Entity.*;
import com.authenticate.Infosys_EDoctor.Repository.UserRepository;
import com.authenticate.Infosys_EDoctor.Service.AdminService;
import com.authenticate.Infosys_EDoctor.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/{username}/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    UserService userService;

    private boolean checkLoginAndValid(String username) {
        if(username == null) {
            return false;
        }

        User user = userService.getUserByUsername(username);
        return user != null;
    }

    @PostMapping("/addAdmin/{username}")
    public ResponseEntity<?> addAdmin(@RequestBody Admin admin, @PathVariable String username) {
        if(username == null) {
            ResponseEntity.badRequest().body("Enter username to add admin.");
        }

        User user = userService.getUserByUsername(username);
        if(user == null) {
            ResponseEntity.badRequest().body("Enter valid username to add admin.");
        }

        admin.setEmail(user.getEmail());
        admin.setPassword(user.getPassword());

        Admin savedAdmin = adminService.addAdmin(admin);

        return ResponseEntity.ok(savedAdmin);
    }

    @PutMapping("/addAdmin/{adminId}")
    public ResponseEntity<?> updateAdmin(@PathVariable String adminId, @RequestBody Admin admin) {
        Admin updatedAdmin = adminService.updateAdmin(adminId, admin);

        return ResponseEntity.ok(updatedAdmin);
    }

    @DeleteMapping("/deleteAdmin/{adminId}")
    public ResponseEntity<?> deleteAdmin(@PathVariable String adminId) {
        adminService.deleteAdmin(adminId);

        return ResponseEntity.ok("Admin with adminId " + adminId + " deleted successfully.");
    }

    @GetMapping("/verifyAdmin")
    public ResponseEntity<?> verifyAdmin(@RequestParam String adminId, @PathVariable String username) {
        if(checkLoginAndValid(username)) {
            String name = adminService.verifyAdmin(adminId);

            if(name != null) {
                return ResponseEntity.ok("Welcome " + name);
            }

            return ResponseEntity.badRequest().body("Your adminId is invalid. Enter valid id to access dashboard");
        }

        return ResponseEntity.badRequest().body("Create an admin profile to access. \nAlready have a profile? Login.");
    }

    // --- Patient Management ---
    @GetMapping("/patients")
    public ResponseEntity<?> getAllPatients(@PathVariable String username) {
        if(!checkLoginAndValid(username)) {
            return ResponseEntity.badRequest().body("Create an admin profile to access. \nAlready have a profile? Login.");
        }

        List<Patient> patients = adminService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    @PutMapping("/patientUpdate/{patientId}")
    public ResponseEntity<?> updatePatient(@PathVariable String username, @PathVariable String patientId, @RequestBody Patient patient) {
        if(!checkLoginAndValid(username)) {
            return ResponseEntity.badRequest().body("Create an admin profile to access. \nAlready have a profile? Login.");
        }

        Patient updatedPatient = adminService.updatePatient(patientId, patient);
        return ResponseEntity.ok(updatedPatient);
    }

    @DeleteMapping("/patientDelete/{patientId}")
    public ResponseEntity<String> deletePatient(@PathVariable String username, @PathVariable String patientId) {
        if(!checkLoginAndValid(username)) {
            return ResponseEntity.badRequest().body("Create an admin profile to access. \nAlready have a profile? Login.");
        }

        adminService.deletePatient(patientId);
        return ResponseEntity.ok("Patient with patientId '" + patientId + "' deleted successfully.");
    }

    // --- Doctor Management ---
    @GetMapping("/doctors")
    public ResponseEntity<?> getAllDoctors(@PathVariable String username) {
        if(!checkLoginAndValid(username)) {
            return ResponseEntity.badRequest().body("Create an admin profile to access. \nAlready have a profile? Login.");
        }

        List<Doctor> doctors = adminService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }

    @PutMapping("/doctorUpdate/{doctorId}")
    public ResponseEntity<?> updateDoctor(@PathVariable String username, @PathVariable String doctorId, @RequestBody Doctor doctor) {
        if(!checkLoginAndValid(username)) {
            return ResponseEntity.badRequest().body("Create an admin profile to access. \nAlready have a profile? Login.");
        }

        Doctor updatedDoctor = adminService.updateDoctor(doctorId, doctor);
        return ResponseEntity.ok(doctor);
    }

    @DeleteMapping("/doctorDelete/{doctorId}")
    public ResponseEntity<String> deleteDoctor(@PathVariable String username, @PathVariable String doctorId) {
        if(!checkLoginAndValid(username)) {
            return ResponseEntity.badRequest().body("Create an admin profile to access. \nAlready have a profile? Login.");
        }

        adminService.deleteDoctor(doctorId);
        return ResponseEntity.ok("Patient with doctorId '" + doctorId + "' deleted successfully.");
    }

    // --- Appointment Management ---
    @PostMapping("/appointmentAdd")
    public ResponseEntity<?> addAppointment(@PathVariable String username, @RequestBody Appointment appointment) {
        if(!checkLoginAndValid(username)) {
            return ResponseEntity.badRequest().body("Create an admin profile to access. \nAlready have a profile? Login.");
        }

        Appointment createdAppointment = adminService.addAppointment(appointment);
        return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
    }

    @PutMapping("/appointmentUpdate/{id}")
    public ResponseEntity<?> updateAppointment(@PathVariable String username, @PathVariable Long id, @RequestBody Appointment appointment) {
        if(!checkLoginAndValid(username)) {
            return ResponseEntity.badRequest().body("Create an admin profile to access. \nAlready have a profile? Login.");
        }

        Appointment updatedAppointment = adminService.updateAppointment(id, appointment);
        return ResponseEntity.ok(updatedAppointment);
    }

    @DeleteMapping("/appointmentDelete")
    public ResponseEntity<String> deleteAppointment(@PathVariable String username, @PathVariable Long id) {
        if(!checkLoginAndValid(username)) {
            return ResponseEntity.badRequest().body("Create an admin profile to access. \nAlready have a profile? Login.");
        }

        adminService.deleteAppointment(id);
        return ResponseEntity.ok("Appointment with ID " + id + " deleted successfully.");
    }

    @GetMapping("/appointmentByPatientId")
    public ResponseEntity<?> getAppointmentByPatientId(@PathVariable String username, @RequestParam String patientId) {
        if(!checkLoginAndValid(username)) {
            return ResponseEntity.badRequest().body("Create an admin profile to access. \nAlready have a profile? Login.");
        }

        List<Appointment> appointments = adminService.getAppointmentByPatientId(patientId);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/appointmentByDoctorId")
    public ResponseEntity<?> getAppointmentByDoctorId(@PathVariable String username, @RequestParam String doctorId) {
        if(!checkLoginAndValid(username)) {
            return ResponseEntity.badRequest().body("Create an admin profile to access. \nAlready have a profile? Login.");
        }

        List<Appointment> appointments = adminService.getAppointmentByDoctorId(doctorId);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/appointments")
    public ResponseEntity<?> getAllAppointments(@PathVariable String username) {
        if(!checkLoginAndValid(username)) {
            return ResponseEntity.badRequest().body("Create an admin profile to access. \nAlready have a profile? Login.");
        }

        List<Appointment> appointments = adminService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    // --- Website Stats ---
//    @GetMapping("/allPatientStats")
//    public ResponseEntity<?> getPatientStats() {
//
//    }
//
//    @GetMapping("/allDoctorStats")
//    public ResponseEntity<?> getDoctorStats() {
//
//    }
//
//    @GetMapping("/patientStats")
//    public ResponseEntity<?> getPatientStatsById(@PathVariable String username, @RequestParam String patientId) {
//
//    }
//
//    @GetMapping("/doctorStats")
//    public ResponseEntity<?> getDoctorStatsById(@PathVariable String username, @RequestParam String doctorId) {
//
//    }
//
//    @GetMapping("/webStats")
//    public ResponseEntity<?> getPatientStats(@RequestParam Date startDate, @RequestParam Date endDate) {
//
//    }
}
