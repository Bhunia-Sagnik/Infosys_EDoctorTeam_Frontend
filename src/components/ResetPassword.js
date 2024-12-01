import React, { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import api from '../services/api'; // Ensure this is correctly configured
import '../CSS/ResetPassword.css'; // Include styles if available

function ResetPassword() {
  const location = useLocation();
  const navigate = useNavigate();
  const [otp, setOtp] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [message, setMessage] = useState('');
  const [messageType, setMessageType] = useState(''); // For styling success or error messages
  const email = location.state?.email || '';

  const handleResetPassword = async (e) => {
    e.preventDefault();

    if (newPassword !== confirmPassword) {
      setMessage('Passwords do not match!');
      setMessageType('error');
      return;
    }

    try {
      const response = await api.post('/reset-password/confirm', null, {
        params: { email, token: otp, newPassword },
      });
      setMessage('Password reset successfully! Redirecting to login...');
      setMessageType('success');

      // Redirect to login after a short delay
      setTimeout(() => {
        navigate('/login');
      }, 2000);
    } catch (error) {
      setMessage(error.response?.data || 'Password reset failed');
      setMessageType('error');
    }
  };

  return (
    <div className="reset-password-container">
      <h1>Reset Password</h1>
      <form onSubmit={handleResetPassword}>
        <div className="form-group">
          <label>OTP:</label>
          <input
            type="text"
            value={otp}
            onChange={(e) => setOtp(e.target.value)}
            placeholder="Enter OTP"
            required
          />
        </div>
        <div className="form-group">
          <label>New Password:</label>
          <input
            type="password"
            value={newPassword}
            onChange={(e) => setNewPassword(e.target.value)}
            placeholder="Enter new password"
            required
          />
        </div>
        <div className="form-group">
          <label>Confirm Password:</label>
          <input
            type="password"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
            placeholder="Confirm new password"
            required
          />
        </div>
        <button className="btn-primary" type="submit">Reset Password</button>
      </form>
      {message && (
        <p className={`message ${messageType}`}>
          {message}
        </p>
      )}
    </div>
  );
}

export default ResetPassword;
