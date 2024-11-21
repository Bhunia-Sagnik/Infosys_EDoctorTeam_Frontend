import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import api from '../services/api';
import '../CSS/Login.css'; 

function Login() {
  const navigate = useNavigate();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [message, setMessage] = useState('');

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await api.post('/login', { username, password });
      setMessage(`${response.data} \nRedirecting to Profile...`);

      
      setTimeout(() => {
        navigate('/profile');
      }, 2000);
    } catch (error) {
      setMessage(error.response?.data || 'Login failed');
    }
  };

  return (
    <body>
      <div className="login-container">
        <h1>Login</h1>
        <form onSubmit={handleLogin}>
          <div className="form-group">
            <label>Username:</label>
            <input
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              placeholder="Enter your username"
              required
            />
          </div>
          <div className="form-group">
            <label>Password:</label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="Enter your password"
              required
            />
          </div>
          <button className="btn-primary" type="submit">Login</button>
        </form>
        <div className="login-footer">
          <Link to="/forgot-password" className="forgot-password-link">Forgot Password?</Link>
          <Link to="/register" className="register-link">Don't have an account?</Link>
        </div>
        {message && <p className="message">{message}</p>}
      </div>
    </body>
    
  );
}

export default Login;
