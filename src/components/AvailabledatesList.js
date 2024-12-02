import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import '../CSS/Availabledates.css';
import '../CSS/Add_Availability.css';


const AvailabledatesList = () => {
  const [availableDates, setAvailableDates] = useState([]);
  const [showForm, setShowForm] = useState(false);

  const [formData, setFormData] = useState({
    doctorID: '',
    fromDate: '',
    endDate: '',
  });

  const [errors, setErrors] = useState({});

  // Toggle form visibility
  const handleAddAvailabilityClick = () => {
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
    if (formData.doctorID.trim() === '') {
      newErrors.doctorID = 'Doctor ID is required';
    }
    if (formData.fromDate.trim() === '') {
      newErrors.fromDate = 'From Date is required';
    }
    if (formData.endDate.trim() === '') {
      newErrors.endDate = 'End Date is required';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  // Handle form submission
  const handleSubmit = (e) => {
    e.preventDefault();
    if (!validateForm()) return;

    const availableDateID = `AD${Date.now()}`; // Auto-generate Available Date ID
    const availableDateData = {
      availableDateID,
      ...formData,
    };

    alert('Availability Added Successfully!');
    setAvailableDates([...availableDates, availableDateData]);
    setShowForm(false);
    resetForm();
  };

  // Reset form fields
  const resetForm = () => {
    setFormData({
      doctorID: '',
      fromDate: '',
      endDate: '',
    });
    setErrors({});
  };

  return (
    <div className="availabledates-container animated-page">
      <div className="navbar animated-navbar">
        <h2>Doctor App</h2>
        <div className="nav-links">
          <Link to="/Profile">Profile</Link>
          <Link to="/AvailabledatesList">Availability</Link>
          <Link to="/AppointmentList">Appointment List</Link>
          <Link to="/Feedbacks">Feedbacks</Link>
          <Link to="/Actions">Actions</Link>
          <button className="logout-btn">Logout</button>
        </div>
      </div>

      <div className="content">
        <h1 className="title">Available Dates</h1>

        <button
          className="add-availability-btn animated-button"
          onClick={handleAddAvailabilityClick}
        >
          {showForm ? 'Close Form' : 'Add Availability'}
        </button>

        <table className="availabledates-table">
          <thead>
            <tr>
              <th>Availabledates ID</th>
              <th>Doctor ID</th>
              <th>From Date</th>
              <th>End Date</th>
            </tr>
          </thead>
          <tbody>
            {availableDates.map((date, index) => (
              <tr key={index}>
                <td>{date.availableDateID}</td>
                <td>{date.doctorID}</td>
                <td>{date.fromDate}</td>
                <td>{date.endDate}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {showForm && (
        <div className="add-availability-form animated-form">
          <h2>Add Availability</h2>
          <form onSubmit={handleSubmit}>
            <label>Doctor ID</label>
            <input
              type="text"
              name="doctorID"
              value={formData.doctorID}
              onChange={handleChange}
              required
            />
            {errors.doctorID && <p className="error-message">{errors.doctorID}</p>}

            <label>From Date</label>
            <input
              type="date"
              name="fromDate"
              value={formData.fromDate}
              onChange={handleChange}
              required
            />
            {errors.fromDate && <p className="error-message">{errors.fromDate}</p>}

            <label>End Date</label>
            <input
              type="date"
              name="endDate"
              value={formData.endDate}
              onChange={handleChange}
              required
            />
            {errors.endDate && <p className="error-message">{errors.endDate}</p>}

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

export default AvailabledatesList;
