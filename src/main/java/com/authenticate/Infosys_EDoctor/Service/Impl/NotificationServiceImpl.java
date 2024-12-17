package com.authenticate.Infosys_EDoctor.Service.Impl;

import com.authenticate.Infosys_EDoctor.Entity.Appointment;
import com.authenticate.Infosys_EDoctor.Service.AppointmentService;
import com.authenticate.Infosys_EDoctor.Service.DoctorService;
import com.authenticate.Infosys_EDoctor.Service.NotificationService;
import com.authenticate.Infosys_EDoctor.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private EmailService emailService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    PatientService patientService;

    @Autowired
    private AppointmentService appointmentService;

    public void sendDoctorProfileCreatedNotification(String email, String doctorId) {
        String subject = "Doctor Profile Created";
        String content = "Welcome to EDoctor Application: " +
                 "Your one stop solution for all outpatient appointments" +
                 "\n\nYour Doctor ID is: " + doctorId + "\nSave this ID for further references.";
        emailService.sendEmail(email, subject, content);
    }

    public void sendPatientProfileCreatedNotification(String email, String patientId) {
        String subject = "Patient Profile Created";
        String content = "Welcome to EDoctor Application: " +
                "Your one stop solution for all outpatient appointments" +
                "\n\nYour Patient ID is: " + patientId + "\nSave this ID for further references.";
        emailService.sendEmail(email, subject, content);
    }

    public void sendNewAppointmentNotificationToDoctor(Appointment appointment) {
        String subject = "New Appointment Scheduled";

        LocalDateTime dateTime = appointment.getAppointmentDateTime();

        String email = doctorService.getDoctorById(appointment.getDoctor().getDoctorId()).getEmail();
        String name = patientService.getPatientById(appointment.getPatient().getPatientId()).getName();

        String content = "Dear Dr. " + doctorService.getDoctorById(appointment.getDoctor().getDoctorId()).getName() +
                ",\n\nA new appointment has been scheduled by patient " +
                name + " on " + dateTime.toLocalDate() + " at " + dateTime.toLocalTime() +
                ".\n\nThank you.";

        emailService.sendEmail(email, subject, content);
    }

    public void sendAppointmentConfirmationToPatient(Appointment appointment) {
        String subject = "Appointment Confirmed";
        LocalDateTime dateTime = appointment.getAppointmentDateTime();

        String content = "Dear " + appointment.getPatient().getName() + ",\n\nYour appointment with Dr. " +
                appointment.getDoctor().getName() + " on " +
                dateTime.toLocalDate() + " at " + dateTime.toLocalTime() + " has been confirmed.\n\nThank you.";
        emailService.sendEmail(appointment.getPatient().getEmail(), subject, content);
    }

    public void sendAppointmentCancellationNotification(Long appointmentId, String reason, boolean notifyPatient) {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        String subject = "Appointment Canceled";
        LocalDateTime dateTime = appointment.getAppointmentDateTime();

        String content = "Dear " + (notifyPatient ? appointment.getPatient().getName() : "Dr. " + appointment.getDoctor().getName()) + ",\n\n" +
                "The appointment scheduled on " + dateTime.toLocalDate() + " at " +
                dateTime.toLocalTime() + " with " + (notifyPatient ? "Dr. " + appointment.getDoctor().getName()
                : appointment.getPatient().getName()) + " has been canceled.\n\nReason: " + reason +
                "\n\nSorry for the inconvenience caused.";

        String email = notifyPatient ? appointment.getPatient().getEmail() : appointment.getDoctor().getEmail();
        emailService.sendEmail(email, subject, content);
    }

    public void sendAppointmentReminder(Appointment appointment) {
        LocalDateTime dateTime = appointment.getAppointmentDateTime();

        String patientContent = "Dear " + appointment.getPatient().getName() + ",\n\nThis is a reminder for your appointment with Dr. " +
                appointment.getDoctor().getName() + " scheduled for " +
                dateTime.toLocalDate() + " at " + dateTime.toLocalTime() + ".\n\nThank you.";
        String doctorContent = "Dear Dr. " + appointment.getDoctor().getName() + ",\n\nThis is a reminder for your appointment with patient " +
                appointment.getPatient().getName() + " scheduled for " +
                dateTime.toLocalDate() + " at " + dateTime.toLocalTime() + ".\n\nThank you.";

        emailService.sendEmail(appointment.getPatient().getEmail(), "Appointment Reminder", patientContent);
        emailService.sendEmail(appointment.getDoctor().getEmail(), "Appointment Reminder", doctorContent);
    }
}
