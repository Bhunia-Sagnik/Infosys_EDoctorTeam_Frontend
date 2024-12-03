package com.authenticate.Infosys_EDoctor.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "appointments")
public class Appointment {
    public enum Status {
        Pending, Confirmed, Canceled
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @NotNull(message = "Appointment date and time is mandatory")
    private LocalDateTime appointmentDateTime;

    @NotBlank(message = "Reason for appointment is mandatory")
    private String reason;

    @Enumerated(EnumType.STRING)
    @NotNull()
    private Status status;
}
