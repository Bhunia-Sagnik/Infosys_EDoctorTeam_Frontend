import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import '../CSS/AppointmentList.css';
import '../CSS/Add_Appoin.css';

const AppointmentList = () => {
  const [appointments, setAppointments] = useState([]);
  const [showForm, setShowForm] = useState(false);

  const [formData, setFormData] = useState({
    patientID: '',
    doctorID: '',
    date: '',
    time: '',
    status: 'Waiting',
    remarks: '',
  });

  const [errors, setErrors] = useState({});

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
    if (formData.date.trim() === '') {
      newErrors.date = 'Date is required';
    }
    if (formData.time.trim() === '') {
      newErrors.time = 'Time is required';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  // Handle form submission
  const handleSubmit = (e) => {
    e.preventDefault();
    if (!validateForm()) return;

    const appointmentID = `APT${Date.now()}`; // Auto-generate Appointment ID
    const appointmentData = {
      ...formData,
      appointmentID,
    };

    alert('Appointment Scheduled Successfully!');
    setAppointments([...appointments, appointmentData]);
    setShowForm(false);
    resetForm();
  };

  // Reset form fields
  const resetForm = () => {
    setFormData({
      patientID: '',
      doctorID: '',
      date: '',
      time: '',
      status: 'Waiting',
      remarks: '',
    });
    setErrors({});
  };

  return (
    <div className="appointment-list-container animated-page">
      {/* Updated Navbar to match Navbar.js */}
      <div className="navbar animated-navbar">
        <h2>Doctor App</h2>
        <div className="nav-links">
          <Link to="/Profile">Profile</Link>
          <Link to="/Availability">Availability</Link>
          <Link to="/AppointmentList">Appointment List</Link>
          <Link to="/Feedbacks">Feedbacks</Link>
          <Link to="/Actions">Actions</Link>
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
              <th>Date</th>
              <th>Time</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {appointments.map((appointment, index) => (
              <tr key={index}>
                <td>{appointment.appointmentID}</td>
                <td>{appointment.patientID}</td>
                <td>{appointment.doctorID}</td>
                <td>{appointment.date}</td>
                <td>{appointment.time}</td>
                <td
                  style={{
                    color: appointment.status === 'Approved' ? 'green' : 'red',
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

            <label>Date</label>
            <input
              type="date"
              name="date"
              value={formData.date}
              onChange={handleChange}
              required
            />
            {errors.date && <p className="error-message">{errors.date}</p>}

            <label>Time</label>
            <input
              type="time"
              name="time"
              value={formData.time}
              onChange={handleChange}
              required
            />
            {errors.time && <p className="error-message">{errors.time}</p>}

            <label>Status</label>
            <select
              name="status"
              value={formData.status}
              onChange={handleChange}
            >
              <option value="Approved" style={{ color: 'green' }}>
                Approved
              </option>
              <option value="Waiting" style={{ color: 'red' }}>
                Waiting
              </option>
            </select>

            <label>Remarks</label>
            <textarea
              name="remarks"
              value={formData.remarks}
              onChange={handleChange}
            />

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
