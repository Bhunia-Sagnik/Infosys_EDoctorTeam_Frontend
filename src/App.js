import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './components/Home';
import Register from './components/Register';
import VerifyEmail from './components/VerifyEmail';
import Login from './components/Login';
import ForgotPassword from './components/ForgotPassword';
import ResetPassword from './components/ResetPassword';
import Profile from './components/Profile';
import Navbar from './components/Navbar';
import AppointmentList from './components/AppointmentList';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/register" element={<Register />} />
        <Route path="/verify-email" element={<VerifyEmail />} />
        <Route path="/login" element={<Login />} />
        <Route path="/forgot-password" element={<ForgotPassword />} />
        <Route path="/reset-password" element={<ResetPassword />} />
        <Route path="/navbar" element={<Navbar/>} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/AppointmentList" element={<AppointmentList />} />
      </Routes>
    </Router>
  );
}

export default App;
