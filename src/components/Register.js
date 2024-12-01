import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../CSS/Register.css'; // Updated CSS file for registration page

function Register() {
  const [formData, setFormData] = useState({
    role: 'PATIENT', // Default role
    username: '',
    password: '',
    email: '',
  });
  const navigate = useNavigate();

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch('http://localhost:8080/api/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData), // Send data as JSON
      });

      if (response.ok) {
        alert('Registration successful! Redirecting to Verify Email...');
        navigate('/verify-email', { state: { username: formData.username } }); // Pass username to verify-email page
      } else {
        alert('Registration failed!');
      }
    } catch (error) {
      alert('Error occurred during registration.');
    }
  };

  return (
    <body>
      <div className="form-container">
        <h1>Register</h1>
        <form onSubmit={handleRegister}>
          <div className="form-group">
            <label>Username:</label>
            <input
              type="text"
              name="username"
              placeholder="Enter your username"
              value={formData.username}
              onChange={handleInputChange}
              required
            />
          </div>
          <div className="form-group">
            <label>Email:</label>
            <input
              type="email"
              name="email"
              placeholder="Enter your email"
              value={formData.email}
              onChange={handleInputChange}
              required
            />
          </div>
          <div className="form-group">
            <label>Password:</label>
            <input
              type="password"
              name="password"
              placeholder="Enter your password"
              value={formData.password}
              onChange={handleInputChange}
              required
            />
          </div>
          <div className="form-group">
            <label>Role:</label>
            <select name="role" value={formData.role} onChange={handleInputChange}>
              <option value="PATIENT">Patient</option>
              <option value="DOCTOR">Doctor</option>
            </select>
          </div>
          <button type="submit" className="btn">Register</button>
        </form>
      </div>
    </body>
  );
}

export default Register;
