const jwt = require('jsonwebtoken');
const { AppError, asyncHandler } = require('./errorHandler');
const User = require('../models/User');
const AuthOperations = require('../operations/AuthOperations');

/**
 * Middleware to protect routes that require authentication
 */
const protect = asyncHandler(async (req, res, next) => {
  let token;

  // Get token from header
  if (req.headers.authorization && req.headers.authorization.startsWith('Bearer')) {
    token = req.headers.authorization.split(' ')[1];
  }

  // Check if token exists
  if (!token) {
    return next(new AppError('Not authorized to access this route', 401));
  }

  try {
    // Validate token
    const { user } = await AuthOperations.validateToken(token);
    
    // Set user to req.user
    req.user = user;
    next();
  } catch (error) {
    return next(new AppError('Not authorized to access this route', 401));
  }
});

/**
 * Middleware to authorize specific roles
 * @param  {...string} roles - Allowed roles
 */
const authorize = (...roles) => {
  return (req, res, next) => {
    if (!req.user) {
      return next(new AppError('User not authenticated', 401));
    }
    
    if (!roles.includes(req.user.role)) {
      return next(new AppError(`User role ${req.user.role} is not authorized to access this route`, 403));
    }
    
    next();
  };
};

module.exports = {
  protect,
  authorize
}; 