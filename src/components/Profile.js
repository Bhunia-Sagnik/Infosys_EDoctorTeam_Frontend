import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import '../CSS/Profile.css';
import '../CSS/Add_Doct.css';

const Profile = () => {
  const [doctors, setDoctors] = useState([]);
  const [showForm, setShowForm] = useState(false);
  const [formData, setFormData] = useState({
    name: '',
    specialization: '',
    location: '',
    hospitalName: '',
    mobileNo: '',
    email: '',
    password: '',
    chargedPerVisit: 0,
  });
  const [errors, setErrors] = useState({});

  const handleAddDoctorClick = () => {
    setShowForm(!showForm);
    resetForm();
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const validateForm = () => {
    const newErrors = {};
    const mobilePattern = /^[0-9]{10}$/;
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!formData.name.trim()) newErrors.name = 'Name is mandatory';
    if (!formData.location.trim()) newErrors.location = 'Location is mandatory';
    if (!formData.mobileNo || !mobilePattern.test(formData.mobileNo)) {
      newErrors.mobileNo = 'Mobile number must be exactly 10 digits';
    }
    if (!formData.email || !emailPattern.test(formData.email)) {
      newErrors.email = 'Invalid email address';
    }
    if (formData.password.length < 8) {
      newErrors.password = 'Password must be at least 8 characters long';
    }
    if (!formData.chargedPerVisit || formData.chargedPerVisit <= 0) {
      newErrors.chargedPerVisit = 'Charged Per Visit must be a positive number';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validateForm()) return;

    try {
      const response = await fetch('http://localhost:8080/doctors', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
      });

      if (response.ok) {
        const newDoctor = await response.json();
        alert('Doctor Profile Created Successfully!');
        setDoctors([...doctors, newDoctor]);
        setShowForm(false);
        resetForm();
      } else {
        const errorData = await response.json();
        setErrors((prev) => ({ ...prev, server: errorData.message || 'Failed to create doctor profile' }));
      }
    } catch (error) {
      setErrors((prev) => ({ ...prev, server: 'Failed to connect to the server. Please try again.' }));
    }
  };

  const resetForm = () => {
    setFormData({
      name: '',
      specialization: '',
      location: '',
      hospitalName: '',
      mobileNo: '',
      email: '',
      password: '',
      chargedPerVisit: 0,
    });
    setErrors({});
  };

  return (
    <div className="doctor-list-container animated-page">
      <div className="navbar">
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
        <h1 className="title">Doctor List</h1>

        <button className="add-doctor-btn animated-button" onClick={handleAddDoctorClick}>
          {showForm ? 'Close Form' : 'Create Doctor'}
        </button>

        <table className="doctor-table">
          <thead>
            <tr>
              <th>Doctor ID</th>
              <th>Doctor Name</th>
              <th>Specialization</th>
              <th>Location</th>
              <th>Hospital Name</th>
              <th>Email</th>
              <th>Mobile No</th>
              <th>Charged Per Visit</th>
            </tr>
          </thead>
          <tbody>
            {doctors.map((doctor, index) => (
              <tr key={index}>
                <td>{doctor.doctorId}</td>
                <td>{doctor.name}</td>
                <td>{doctor.specialization}</td>
                <td>{doctor.location}</td>
                <td>{doctor.hospitalName}</td>
                <td>{doctor.email}</td>
                <td>{doctor.mobileNo}</td>
                <td>{doctor.chargedPerVisit}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {showForm && (
        <div className="create-doctor-form animated-form">
          <h2>Create Doctor</h2>
          <form onSubmit={handleSubmit}>
            <label>Name</label>
            <input type="text" name="name" value={formData.name} onChange={handleChange} required />
            {errors.name && <p className="error-message">{errors.name}</p>}

            <label>Specialization</label>
            <input type="text" name="specialization" value={formData.specialization} onChange={handleChange} />

            <label>Location</label>
            <input type="text" name="location" value={formData.location} onChange={handleChange} required />
            {errors.location && <p className="error-message">{errors.location}</p>}

            <label>Hospital Name</label>
            <input type="text" name="hospitalName" value={formData.hospitalName} onChange={handleChange} />

            <label>Mobile No</label>
            <input type="text" name="mobileNo" value={formData.mobileNo} onChange={handleChange} required />
            {errors.mobileNo && <p className="error-message">{errors.mobileNo}</p>}

            <label>Email</label>
            <input type="email" name="email" value={formData.email} onChange={handleChange} required />
            {errors.email && <p className="error-message">{errors.email}</p>}

            <label>Password</label>
            <input type="password" name="password" value={formData.password} onChange={handleChange} required />
            {errors.password && <p className="error-message">{errors.password}</p>}

            <label>Charged Per Visit</label>
            <input
              type="number"
              name="chargedPerVisit"
              value={formData.chargedPerVisit}
              onChange={handleChange}
              required
            />
            {errors.chargedPerVisit && <p className="error-message">{errors.chargedPerVisit}</p>}

            {errors.server && <p className="error-message">{errors.server}</p>}

            <button type="submit" className="submit-btn">Submit</button>
          </form>
        </div>
      )}

      <footer className="footer animated-footer">
        <p>All rights Reserved 2024 ©DoctorApp Module</p>
      </footer>
    </div>
  );
};

export default Profile;
