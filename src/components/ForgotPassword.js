import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';
import '../CSS/ForgotPassword.css'

function ForgotPassword() {
  const [email, setEmail] = useState('');
  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  const handleForgotPassword = async (e) => {
    e.preventDefault();
    try {
      const response = await api.post('/reset-password', null, { params: { email } });
      setMessage(response.data);

      setTimeout(() => {
        navigate('/reset-password', { state: { email } });
      }, 2000);
    } catch (error) {
      setMessage(error.response?.data || 'Failed to send reset password token');
    }
  };

  return (
    <div className='forgot-password-container'>
      <h1>Forgot Password</h1>
      <form onSubmit={handleForgotPassword}>
        <div className="form-group">
          <label>Email:</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            placeholder="Enter your email"
            required
          />
        </div>
        <button className="btn-primary" type="submit">Send Reset Link</button>
      </form>
      {message && <p className="message">{message}</p>}
    </div>
  );
}

export default ForgotPassword;
