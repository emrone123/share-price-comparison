const User = require('../models/User');
const UserInterface = require('../interfaces/UserInterface');

/**
 * User Operations
 * Implements the UserInterface methods
 */
class UserOperations extends UserInterface {
  /**
   * Gets a list of all users
   * @param {Object} filter - Optional filters to apply
   * @param {Object} options - Pagination and sorting options
   * @returns {Promise<Array>} - List of users
   */
  static async getUsers(filter = {}, options = {}) {
    const { page = 1, limit = 10, sort = '-createdAt' } = options;
    
    const skip = (page - 1) * limit;
    
    // Don't return password in the results
    const users = await User.find(filter)
      .select('-password')
      .sort(sort)
      .skip(skip)
      .limit(limit);
    
    const total = await User.countDocuments(filter);
    
    return {
      users,
      pagination: {
        total,
        page,
        limit,
        pages: Math.ceil(total / limit)
      }
    };
  }

  /**
   * Gets a user by ID
   * @param {string} userId - User ID
   * @returns {Promise<Object>} - User object
   */
  static async getUserById(userId) {
    const user = await User.findById(userId).select('-password');
    
    if (!user) {
      const error = new Error('User not found');
      error.statusCode = 404;
      throw error;
    }
    
    return user;
  }

  /**
   * Creates a new user
   * @param {Object} userData - User data
   * @returns {Promise<Object>} - Created user object
   */
  static async createUser(userData) {
    const { email } = userData;
    
    // Check if user exists
    const userExists = await User.findOne({ email });
    if (userExists) {
      const error = new Error('User with that email already exists');
      error.statusCode = 400;
      throw error;
    }
    
    // Create user
    const user = await User.create(userData);
    
    return {
      _id: user._id,
      name: user.name,
      email: user.email,
      role: user.role,
      isActive: user.isActive
    };
  }

  /**
   * Updates a user's information
   * @param {string} userId - User ID
   * @param {Object} updates - User data to update
   * @returns {Promise<Object>} - Updated user object
   */
  static async updateUser(userId, updates) {
    // Don't allow password updates through this method
    if (updates.password) {
      delete updates.password;
    }
    
    // Find user and update
    const user = await User.findByIdAndUpdate(
      userId,
      updates,
      { new: true, runValidators: true }
    ).select('-password');
    
    if (!user) {
      const error = new Error('User not found');
      error.statusCode = 404;
      throw error;
    }
    
    return user;
  }

  /**
   * Deletes a user
   * @param {string} userId - User ID
   * @returns {Promise<boolean>} - Success status
   */
  static async deleteUser(userId) {
    const user = await User.findById(userId);
    
    if (!user) {
      const error = new Error('User not found');
      error.statusCode = 404;
      throw error;
    }
    
    await user.remove();
    return true;
  }

  /**
   * Changes a user's password
   * @param {string} userId - User ID
   * @param {string} currentPassword - Current password
   * @param {string} newPassword - New password
   * @returns {Promise<boolean>} - Success status
   */
  static async changePassword(userId, currentPassword, newPassword) {
    const user = await User.findById(userId);
    
    if (!user) {
      const error = new Error('User not found');
      error.statusCode = 404;
      throw error;
    }
    
    // Check current password
    const isMatch = await user.matchPassword(currentPassword);
    if (!isMatch) {
      const error = new Error('Current password is incorrect');
      error.statusCode = 400;
      throw error;
    }
    
    // Update password
    user.password = newPassword;
    await user.save();
    
    return true;
  }
}

module.exports = UserOperations; 