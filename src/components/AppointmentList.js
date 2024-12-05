import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import '../CSS/AppointmentList.css';
import '../CSS/Add_Appoin.css';

const AppointmentList = () => {
  const [appointments, setAppointments] = useState([]);
  const [showForm, setShowForm] = useState(false);

  const [formData, setFormData] = useState({
    patientID: '',
    doctorID: '',
    appointmentDateTime: '',
    reason: '',
    status: 'Pending',
  });

  const [errors, setErrors] = useState({});

  // Fetch existing appointments
  useEffect(() => {
    fetch('/api/appointments') // Replace with actual backend API endpoint
      .then((response) => response.json())
      .then((data) => setAppointments(data))
      .catch((error) => console.error('Error fetching appointments:', error));
  }, []);

  // Toggle form visibility
  const handleAddAppointmentClick = () => {
    setShowForm(!showForm);
    resetForm();
  };

  // Handle form input changes
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  // Validate form fields
  const validateForm = () => {
    const newErrors = {};
    if (formData.patientID.trim() === '') {
      newErrors.patientID = 'Patient ID is required';
    }
    if (formData.doctorID.trim() === '') {
      newErrors.doctorID = 'Doctor ID is required';
    }
    if (formData.appointmentDateTime.trim() === '') {
      newErrors.appointmentDateTime = 'Appointment date and time is required';
    }
    if (formData.reason.trim() === '') {
      newErrors.reason = 'Reason is required';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  // Handle form submission
  const handleSubmit = (e) => {
    e.preventDefault();
    if (!validateForm()) return;

    const appointmentData = {
      doctor: { doctorId: formData.doctorID }, // Backend expects nested object
      patient: { patientId: formData.patientID }, // Backend expects nested object
      appointmentDateTime: formData.appointmentDateTime,
      reason: formData.reason,
      status: formData.status,
    };

    // POST API call to save appointment
    fetch('/api/appointments', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(appointmentData),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error('Failed to schedule appointment');
        }
        return response.json();
      })
      .then((newAppointment) => {
        setAppointments([...appointments, newAppointment]);
        alert('Appointment Scheduled Successfully!');
        setShowForm(false);
        resetForm();
      })
      .catch((error) => {
        console.error('Error scheduling appointment:', error);
        alert('Failed to schedule appointment. Please try again.');
      });
  };

  // Reset form fields
  const resetForm = () => {
    setFormData({
      patientID: '',
      doctorID: '',
      appointmentDateTime: '',
      reason: '',
      status: 'Pending',
    });
    setErrors({});
  };

  return (
    <div className="appointment-list-container animated-page">
      <div className="navbar animated-navbar">
        <h2>Doctor App</h2>
        <div className="nav-links">
          <Link to="/Profile">Profile</Link>
          <Link to="/AvailabledatesList">Availability</Link>
          <Link to="/AppointmentList">Appointment List</Link>
          <Link to="/Feedbacks">Feedbacks</Link>
          <button className="logout-btn">Logout</button>
        </div>
      </div>

      <div className="content">
        <h1 className="title">Appointment List</h1>

        <button
          className="add-appointment-btn animated-button"
          onClick={handleAddAppointmentClick}
        >
          {showForm ? 'Close Form' : 'Schedule Appointment'}
        </button>

        <table className="appointment-table">
          <thead>
            <tr>
              <th>Appointment ID</th>
              <th>Patient ID</th>
              <th>Doctor ID</th>
              <th>Date & Time</th>
              <th>Reason</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {appointments.map((appointment, index) => (
              <tr key={index}>
                <td>{appointment.appointmentId}</td>
                <td>{appointment.patient.patientId}</td>
                <td>{appointment.doctor.doctorId}</td>
                <td>{appointment.appointmentDateTime}</td>
                <td>{appointment.reason}</td>
                <td
                  style={{
                    color:
                      appointment.status === 'Confirmed'
                        ? 'green'
                        : appointment.status === 'Cancelled'
                        ? 'red'
                        : 'orange',
                  }}
                >
                  {appointment.status}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {showForm && (
        <div className="schedule-appointment-form animated-form">
          <h2>Schedule Appointment</h2>
          <form onSubmit={handleSubmit}>
            <label>Patient ID</label>
            <input
              type="text"
              name="patientID"
              value={formData.patientID}
              onChange={handleChange}
              required
            />
            {errors.patientID && <p className="error-message">{errors.patientID}</p>}

            <label>Doctor ID</label>
            <input
              type="text"
              name="doctorID"
              value={formData.doctorID}
              onChange={handleChange}
              required
            />
            {errors.doctorID && <p className="error-message">{errors.doctorID}</p>}

            <label>Date & Time</label>
            <input
              type="datetime-local"
              name="appointmentDateTime"
              value={formData.appointmentDateTime}
              onChange={handleChange}
              required
            />
            {errors.appointmentDateTime && (
              <p className="error-message">{errors.appointmentDateTime}</p>
            )}

            <label>Reason</label>
            <textarea
              name="reason"
              value={formData.reason}
              onChange={handleChange}
              required
            />
            {errors.reason && <p className="error-message">{errors.reason}</p>}

            <label>Status</label>
            <select
              name="status"
              value={formData.status}
              onChange={handleChange}
            >
              <option value="Pending">Pending</option>
              <option value="Confirmed">Confirmed</option>
              <option value="Cancelled">Cancelled</option>
            </select>

            <button type="submit" className="submit-btn">
              Submit
            </button>
          </form>
        </div>
      )}

      <footer className="footer animated-footer">
        <p>All rights Reserved 2024 ©DoctorApp Module</p>
      </footer>
    </div>
  );
};

export default AppointmentList;
