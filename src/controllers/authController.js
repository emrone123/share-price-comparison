const express = require('express');
const router = express.Router();
const AuthOperations = require('../operations/AuthOperations');
const { asyncHandler } = require('../utils/errorHandler');
const { protect } = require('../utils/authMiddleware');

/**
 * @route   POST /api/auth/register
 * @desc    Register a new user
 * @access  Public
 */
router.post('/register', asyncHandler(async (req, res) => {
  const result = await AuthOperations.registerUser(req.body);
  res.status(201).json({
    success: true,
    data: result
  });
}));

/**
 * @route   POST /api/auth/login
 * @desc    Authenticate user & get token
 * @access  Public
 */
router.post('/login', asyncHandler(async (req, res) => {
  const { email, password } = req.body;
  
  const result = await AuthOperations.loginUser(email, password);
  
  res.json({
    success: true,
    data: result
  });
}));

/**
 * @route   GET /api/auth/me
 * @desc    Get current user profile
 * @access  Private
 */
router.get('/me', protect, asyncHandler(async (req, res) => {
  res.json({
    success: true,
    data: req.user
  });
}));

/**
 * @route   POST /api/auth/refresh
 * @desc    Refresh token
 * @access  Private
 */
router.post('/refresh', asyncHandler(async (req, res) => {
  const { token } = req.body;
  
  if (!token) {
    res.status(400).json({
      success: false,
      error: 'Please provide a token'
    });
    return;
  }
  
  const result = await AuthOperations.refreshToken(token);
  
  res.json({
    success: true,
    data: result
  });
}));

/**
 * @route   POST /api/auth/logout
 * @desc    Logout user / clear token
 * @access  Private
 */
router.post('/logout', protect, asyncHandler(async (req, res) => {
  const token = req.headers.authorization.split(' ')[1];
  
  await AuthOperations.logoutUser(token);
  
  res.json({
    success: true,
    data: { message: 'Logged out successfully' }
  });
}));

module.exports = router; 