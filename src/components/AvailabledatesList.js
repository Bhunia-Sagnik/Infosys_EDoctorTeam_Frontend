import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';  // Import axios for API requests
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

  const API_URL = 'http://localhost:8080/api/availability'; // Your backend API URL

  // Fetch available dates from the backend
  useEffect(() => {
    axios.get(API_URL)
      .then(response => {
        setAvailableDates(response.data);  // Set the available dates from backend
      })
      .catch(error => {
        console.error("There was an error fetching the available dates!", error);
      });
  }, []);

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

    const availabilityData = {
      doctorId: formData.doctorID,
      fromDate: formData.fromDate,
      endDate: formData.endDate,
    };

    // Send POST request to backend to add availability
    axios.post(API_URL, availabilityData)
      .then(response => {
        alert('Availability Added Successfully!');
        setAvailableDates([...availableDates, response.data]); // Add the new availability to the state
        setShowForm(false);
        resetForm();
      })
      .catch(error => {
        console.error("There was an error adding the availability!", error);
      });
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
              <tr key={date.availabilityId}>
                <td>{date.availabilityId}</td>
                <td>{date.doctorId}</td>
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
