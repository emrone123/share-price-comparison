const express = require('express');
const router = express.Router();
const UserOperations = require('../operations/UserOperations');
const { asyncHandler } = require('../utils/errorHandler');
const { protect, authorize } = require('../utils/authMiddleware');

/**
 * @route   GET /api/users
 * @desc    Get all users
 * @access  Private/Admin
 */
router.get('/', protect, authorize('admin'), asyncHandler(async (req, res) => {
  // Parse query parameters for filtering and pagination
  const page = parseInt(req.query.page, 10) || 1;
  const limit = parseInt(req.query.limit, 10) || 10;
  const sort = req.query.sort || '-createdAt';

  // Build filter object
  const filter = {};
  if (req.query.role) filter.role = req.query.role;
  if (req.query.isActive) filter.isActive = req.query.isActive === 'true';
  if (req.query.search) {
    filter.$or = [
      { name: { $regex: req.query.search, $options: 'i' } },
      { email: { $regex: req.query.search, $options: 'i' } }
    ];
  }

  const result = await UserOperations.getUsers(filter, { page, limit, sort });

  res.json({
    success: true,
    data: result
  });
}));

/**
 * @route   GET /api/users/:id
 * @desc    Get user by ID
 * @access  Private/Admin or Self
 */
router.get('/:id', protect, asyncHandler(async (req, res) => {
  // Check if user is admin or requesting their own profile
  if (req.user.role !== 'admin' && req.user._id.toString() !== req.params.id) {
    return res.status(403).json({
      success: false,
      error: 'Not authorized to access this user profile'
    });
  }

  const user = await UserOperations.getUserById(req.params.id);

  res.json({
    success: true,
    data: user
  });
}));

/**
 * @route   POST /api/users
 * @desc    Create a new user
 * @access  Private/Admin
 */
router.post('/', protect, authorize('admin'), asyncHandler(async (req, res) => {
  const user = await UserOperations.createUser(req.body);

  res.status(201).json({
    success: true,
    data: user
  });
}));

/**
 * @route   PUT /api/users/:id
 * @desc    Update user
 * @access  Private/Admin or Self
 */
router.put('/:id', protect, asyncHandler(async (req, res) => {
  // Check if user is admin or updating their own profile
  if (req.user.role !== 'admin' && req.user._id.toString() !== req.params.id) {
    return res.status(403).json({
      success: false,
      error: 'Not authorized to update this user'
    });
  }

  // If not admin, prevent role update
  if (req.user.role !== 'admin' && req.body.role) {
    delete req.body.role;
  }

  const user = await UserOperations.updateUser(req.params.id, req.body);

  res.json({
    success: true,
    data: user
  });
}));

/**
 * @route   DELETE /api/users/:id
 * @desc    Delete user
 * @access  Private/Admin
 */
router.delete('/:id', protect, authorize('admin'), asyncHandler(async (req, res) => {
  await UserOperations.deleteUser(req.params.id);

  res.json({
    success: true,
    data: { message: 'User deleted successfully' }
  });
}));

/**
 * @route   POST /api/users/change-password
 * @desc    Change user password
 * @access  Private
 */
router.post('/change-password', protect, asyncHandler(async (req, res) => {
  const { currentPassword, newPassword } = req.body;

  if (!currentPassword || !newPassword) {
    return res.status(400).json({
      success: false,
      error: 'Please provide both current and new password'
    });
  }

  await UserOperations.changePassword(req.user._id, currentPassword, newPassword);

  res.json({
    success: true,
    data: { message: 'Password changed successfully' }
  });
}));

module.exports = router; 