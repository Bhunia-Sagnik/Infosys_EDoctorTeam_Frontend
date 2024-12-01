import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import '../CSS/Profile.css';
import '../CSS/Add_Doct.css';

const Profile = () => {
  const [doctors, setDoctors] = useState([]);
  const [showForm, setShowForm] = useState(false);

  const [formData, setFormData] = useState({
    username: '',
    speciality: '',
    location: '',
    hospital: '',
    mobile: '',
    profilePhoto: null,
  });

  const [errors, setErrors] = useState({});
  const maxPhotoSize = 2 * 1024 * 1024; // 2MB limit for photo

  const handleAddDoctorClick = () => {
    setShowForm(!showForm);
    resetForm();
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handlePhotoChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      const fileSizeValid = file.size <= maxPhotoSize;
      const fileTypeValid = file.type.startsWith("image/");
      const validFace = file.name.toLowerCase().includes("face");

      if (!fileSizeValid) {
        setErrors((prev) => ({ ...prev, profilePhoto: 'Photo size must not exceed 2MB' }));
      } else if (!fileTypeValid) {
        setErrors((prev) => ({ ...prev, profilePhoto: 'Invalid photo format. Only images are allowed.' }));
      } else if (!validFace) {
        setErrors((prev) => ({ ...prev, profilePhoto: 'Photo must clearly show the face.' }));
      } else {
        setErrors((prev) => ({ ...prev, profilePhoto: '' }));
        setFormData({ ...formData, profilePhoto: file });
      }
    }
  };

  const validateForm = () => {
    const newErrors = {};
    const usernamePattern = /^(?=.*[A-Z])[A-Za-z0-9]{3,}$/;
    const mobilePattern = /^[0-9]{10}$/;

    if (!usernamePattern.test(formData.username)) {
      newErrors.username = 'Username must be alphanumeric and contain at least one uppercase letter.';
    }
    if (formData.speciality.trim() === '') {
      newErrors.speciality = 'Speciality is required';
    }
    if (formData.location.trim() === '') {
      newErrors.location = 'Location is required';
    }
    if (formData.hospital.trim() === '') {
      newErrors.hospital = 'Hospital name is required';
    }
    if (!mobilePattern.test(formData.mobile)) {
      newErrors.mobile = 'Mobile number must be exactly 10 digits.';
    }
    if (!formData.profilePhoto) {
      newErrors.profilePhoto = 'Profile photo is required.';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!validateForm()) return;

    const doctorId = `DOC${Date.now()}`;

    const doctorData = {
      ...formData,
      id: doctorId,
    };

    alert('Doctor Profile Created! ID sent to the registered email.');
    setDoctors([...doctors, doctorData]);
    setShowForm(false);
    resetForm();
  };

  const resetForm = () => {
    setFormData({
      username: '',
      speciality: '',
      location: '',
      hospital: '',
      mobile: '',
      profilePhoto: null,
    });
    setErrors({});
  };

  return (
    <div className="doctor-list-container animated-page">
      <div className="navbar">
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
        <h1 className="title">Doctor List</h1>

        <button className="add-doctor-btn animated-button" onClick={handleAddDoctorClick}>
          {showForm ? 'Close Form' : 'Create Doctor'}
        </button>

        <table className="doctor-table">
          <thead>
            <tr>
              <th>Doctor ID</th>
              <th>Doctor Name</th>
              <th>Speciality</th>
              <th>Location</th>
              <th>Hospital Name</th>
            </tr>
          </thead>
          <tbody>
            {doctors.map((doctor, index) => (
              <tr key={index}>
                <td>{doctor.id}</td>
                <td>{doctor.username}</td>
                <td>{doctor.speciality}</td>
                <td>{doctor.location}</td>
                <td>{doctor.hospital}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {showForm && (
        <div className="create-doctor-form animated-form">
          <h2>Create Doctor</h2>
          <form onSubmit={handleSubmit}>
            <label>Username</label>
            <input
              type="text"
              name="username"
              value={formData.username}
              onChange={handleChange}
              required
            />
            {errors.username && <p className="error-message">{errors.username}</p>}

            <label>Speciality</label>
            <input
              type="text"
              name="speciality"
              value={formData.speciality}
              onChange={handleChange}
              required
            />
            {errors.speciality && <p className="error-message">{errors.speciality}</p>}

            <label>Location</label>
            <input
              type="text"
              name="location"
              value={formData.location}
              onChange={handleChange}
              required
            />
            {errors.location && <p className="error-message">{errors.location}</p>}

            <label>Hospital Name</label>
            <input
              type="text"
              name="hospital"
              value={formData.hospital}
              onChange={handleChange}
              required
            />
            {errors.hospital && <p className="error-message">{errors.hospital}</p>}

            <label>Mobile No</label>
            <input
              type="text"
              name="mobile"
              value={formData.mobile}
              onChange={handleChange}
              required
            />
            {errors.mobile && <p className="error-message">{errors.mobile}</p>}

            <label>Profile Photo (Max 2MB, Face Picture)</label>
            <input
              type="file"
              name="profilePhoto"
              onChange={handlePhotoChange}
              accept="image/*"
            />
            {errors.profilePhoto && <p className="error-message">{errors.profilePhoto}</p>}

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
