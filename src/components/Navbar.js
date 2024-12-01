import React from 'react';
import { Link } from 'react-router-dom';
import '../CSS/Navbar.css';
import '../components/Profile.js';
import '../components/AppointmentList.js';

const Navbar = () => {
  return (
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
  );
};

export default Navbar;
