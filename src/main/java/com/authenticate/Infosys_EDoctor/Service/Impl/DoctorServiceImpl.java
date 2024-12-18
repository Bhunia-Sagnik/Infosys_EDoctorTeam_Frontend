package com.authenticate.Infosys_EDoctor.Service.Impl;

import com.authenticate.Infosys_EDoctor.Entity.Appointment;
import com.authenticate.Infosys_EDoctor.Entity.Doctor;
import com.authenticate.Infosys_EDoctor.Entity.DoctorAvailability;
import com.authenticate.Infosys_EDoctor.Repository.DoctorRepository;
import com.authenticate.Infosys_EDoctor.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    UserService userService;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DoctorAvailabilityService doctorAvailabilityService;

    @Autowired
    private AppointmentService appointmentService;

    @Override
    public Doctor addDoctor(Doctor doctor) {
        Doctor exists = doctorRepository.findByEmail(doctor.getEmail());
        if(exists != null) {
            throw new RuntimeException("Doctor profile already exists");
        }

        String id = "DOC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        doctor.setDoctorId(id);
        Doctor savedDoc = doctorRepository.save(doctor);

        //notificationService.sendDoctorProfileCreatedNotification(savedDoc.getEmail(), savedDoc.getDoctorId());

        return savedDoc;
    }

    @Override
    public Doctor getDoctorById(String doctorId) {
        Doctor doctor = doctorRepository.findByDoctorId(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        return doctor;
    }

    @Override
    public Doctor updateDoctor(String doctorId, Doctor updatedDoctor) {
        Doctor doctor = doctorRepository.findByDoctorId(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found."));

        doctor.setName(updatedDoctor.getName());
        doctor.setSpecialization(updatedDoctor.getSpecialization());
        doctor.setLocation(updatedDoctor.getLocation());
        doctor.setHospitalName(updatedDoctor.getHospitalName());
        doctor.setMobileNo(updatedDoctor.getMobileNo());
        doctor.setEmail(updatedDoctor.getEmail());

        return doctorRepository.save(doctor);
    }

    @Override
    public void deleteDoctor(String doctorId) {
        Doctor doctor = doctorRepository.findByDoctorId(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found."));
        doctorRepository.delete(doctor);
    }

    @Override
    public List<DoctorAvailability> getAvailableSlots(String doctorId) {
        Doctor doctor = doctorRepository.findByDoctorId(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found."));
        return doctorAvailabilityService.getAvailabilityByDoctorId(doctorId);
    }

    @Override
    public List<Doctor> findDoctorsBySpecialization(String specialization) {
        return doctorRepository.findBySpecialization(specialization);
    }

    @Override
    public List<Doctor> findAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public List<Appointment> getAllAppointments(String doctorId) {
        return appointmentService.getAppointmentsForDoctor(doctorId);
    }

    @Override
    public Appointment confirmAppointment(Long appointmentId) {
        return appointmentService.confirmAppointment(appointmentId);
    }

    @Override
    public void cancelAppointment(Long appontmentId, String reason) {
        appointmentService.cancelAppointment(appontmentId, reason);
    }

    @Override
    public Doctor getDoctorByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }
}

