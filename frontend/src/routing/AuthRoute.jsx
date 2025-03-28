import React from 'react';
import { Navigate } from 'react-router-dom';

const AuthRoute = ({ element: Element }) => {
  const isAuthenticated = !!localStorage.getItem('token');

  return isAuthenticated ? <Element /> : <Navigate to="/" />;
};

export default AuthRoute;