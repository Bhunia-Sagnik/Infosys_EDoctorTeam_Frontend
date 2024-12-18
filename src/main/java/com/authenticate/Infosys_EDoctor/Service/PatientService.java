package com.authenticate.Infosys_EDoctor.Service;

import com.authenticate.Infosys_EDoctor.Entity.Appointment;
import com.authenticate.Infosys_EDoctor.Entity.Doctor;
import com.authenticate.Infosys_EDoctor.Entity.DoctorAvailability;
import com.authenticate.Infosys_EDoctor.Entity.Patient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    public Patient addPatient(Patient patient);
    public Patient updateProfile(String patientId, Patient updatedPatient);
    public List<Doctor> findDoctors();
    public Appointment updateAppointment(Long appointmentId, Appointment updatedAppointment);
    public void cancelAppointment(Long appointmentId, String reason);
    public Appointment makeAppointment(Appointment appointment);
    public List<Doctor> findDoctorsBySpecialization(String specialization);
    public List<DoctorAvailability> getAvailableDates(String doctorId);

    public Patient getPatientById(String patientId);

    public Patient getPatientByUserId(Long userId);

    List<Appointment> viewAppointments(String patientId);

    Optional<Patient> getPatientByEmail(String email);
}
