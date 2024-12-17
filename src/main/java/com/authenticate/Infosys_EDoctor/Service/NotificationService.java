package com.authenticate.Infosys_EDoctor.Service;

import com.authenticate.Infosys_EDoctor.Entity.Appointment;

public interface NotificationService {
    public void sendDoctorProfileCreatedNotification(String email, String doctorId);
    public void sendPatientProfileCreatedNotification(String email, String patientId);
    public void sendNewAppointmentNotificationToDoctor(Appointment appointment);
    public void sendAppointmentConfirmationToPatient(Appointment appointment);
    public void sendAppointmentCancellationNotification(Long appointmentId, String reason, boolean notifyPatient);
    public void sendAppointmentReminder(Appointment appointment);
}
