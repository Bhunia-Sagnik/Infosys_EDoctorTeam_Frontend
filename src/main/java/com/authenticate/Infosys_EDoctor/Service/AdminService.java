package com.authenticate.Infosys_EDoctor.Service;

import com.authenticate.Infosys_EDoctor.Entity.Admin;
import com.authenticate.Infosys_EDoctor.Entity.Appointment;
import com.authenticate.Infosys_EDoctor.Entity.Doctor;
import com.authenticate.Infosys_EDoctor.Entity.Patient;

import java.util.List;

public interface AdminService {
    Admin addAdmin(Admin admin);

    String verifyAdmin(String adminId);

    Admin updateAdmin(String adminId, Admin admin);

    void deleteAdmin(String adminId);

    List<Patient> getAllPatients();

    Patient updatePatient(String patientId, Patient patient);

    void deletePatient(String patientId);

    List<Doctor> getAllDoctors();

    Doctor updateDoctor(String doctorId, Doctor doctor);

    void deleteDoctor(String doctorId);

    List<Appointment> getAllAppointments();

    List<Appointment> getAppointmentByPatientId(String patientId);

    List<Appointment> getAppointmentByDoctorId(String doctorId);

    void deleteAppointment(Long id);

    Appointment updateAppointment(Long id, Appointment appointment);

    Appointment addAppointment(Appointment appointment);
}
