package com.authenticate.Infosys_EDoctor.Service.Impl;

import com.authenticate.Infosys_EDoctor.Entity.Admin;
import com.authenticate.Infosys_EDoctor.Entity.Appointment;
import com.authenticate.Infosys_EDoctor.Entity.Doctor;
import com.authenticate.Infosys_EDoctor.Entity.Patient;
import com.authenticate.Infosys_EDoctor.Repository.AdminRepository;
import com.authenticate.Infosys_EDoctor.Service.AdminService;
import com.authenticate.Infosys_EDoctor.Service.AppointmentService;
import com.authenticate.Infosys_EDoctor.Service.DoctorService;
import com.authenticate.Infosys_EDoctor.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    PatientService patientService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    AppointmentService appointmentService;

    // 1. Add Admin
    @Override
    public Admin addAdmin(Admin admin) {
        Optional<Admin> exists = adminRepository.findByEmail(admin.getEmail());
        if (exists.isPresent()) {
            throw new RuntimeException("Admin already exists");
        }

        String id = "ADM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        admin.setAdminId(id);

        return adminRepository.save(admin);
    }

    // 2. Update Admin Profile
    @Override
    @Transactional
    public Admin updateAdmin(String adminId, Admin updatedAdmin) {
        Admin existingAdmin = adminRepository.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found with ID: " + adminId));

        existingAdmin.setName(updatedAdmin.getName());
        existingAdmin.setEmail(updatedAdmin.getEmail());
        existingAdmin.setPassword(passwordEncoder.encode(updatedAdmin.getPassword()));
        existingAdmin.setMobileNo(updatedAdmin.getMobileNo());

        return adminRepository.save(existingAdmin);
    }

    // 3. Verify Admin
    @Override
    public String verifyAdmin(String adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found with ID: " + adminId));
        return "Admin with ID " + adminId + " verified successfully.";
    }

    // 4. Delete Admin
    @Override
    @Transactional
    public void deleteAdmin(String adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found with ID: " + adminId));

        adminRepository.delete(admin);
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @Override
    public Patient updatePatient(String patientId, Patient patient) {
        return patientService.updateProfile(patientId, patient);
    }

    @Override
    public void deletePatient(String patientId) {
        patientService.deletePatient(patientId);
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorService.findAllDoctors();
    }

    @Override
    public Doctor updateDoctor(String doctorId, Doctor doctor) {
        return doctorService.updateDoctor(doctorId, doctor);
    }

    @Override
    public void deleteDoctor(String doctorId) {
        doctorService.deleteDoctor(doctorId);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @Override
    public List<Appointment> getAppointmentByPatientId(String patientId) {
        return appointmentService.getAppointmentsForPatient(patientId);
    }

    @Override
    public List<Appointment> getAppointmentByDoctorId(String doctorId) {
        return appointmentService.getAppointmentsForDoctor(doctorId);
    }

    @Override
    public void deleteAppointment(Long id) {
        appointmentService.cancelAppointment(id, "Cancelled by Admin");
    }

    @Override
    public Appointment updateAppointment(Long id, Appointment appointment) {
        return appointmentService.updateAppointment(id, appointment);
    }

    @Override
    public Appointment addAppointment(Appointment appointment) {
        return appointmentService.scheduleAppointment(appointment);
    }
}
