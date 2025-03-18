const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const User = require('../models/User');
const AuthInterface = require('../interfaces/AuthInterface');

/**
 * Authentication Operations
 * Implements the AuthInterface methods
 */
class AuthOperations extends AuthInterface {
  /**
   * Registers a new user
   * @param {Object} userData - User registration data
   * @returns {Promise<Object>} - Registered user and token
   */
  static async registerUser(userData) {
    const { name, email, password, role = 'user' } = userData;

    // Check if user exists
    const userExists = await User.findOne({ email });
    if (userExists) {
      const error = new Error('User already exists');
      error.statusCode = 400;
      throw error;
    }

    // Create user
    const user = await User.create({
      name,
      email,
      password,
      role
    });

    // Generate token
    const token = this.generateToken(user._id);

    return {
      _id: user._id,
      name: user.name,
      email: user.email,
      role: user.role,
      token
    };
  }

  /**
   * Logs in a user
   * @param {string} email - User email
   * @param {string} password - User password
   * @returns {Promise<Object>} - Logged in user and token
   */
  static async loginUser(email, password) {
    // Check for user email
    const user = await User.findOne({ email });
    if (!user) {
      const error = new Error('Invalid credentials');
      error.statusCode = 401;
      throw error;
    }

    // Check if user is active
    if (!user.isActive) {
      const error = new Error('User account is deactivated');
      error.statusCode = 401;
      throw error;
    }

    // Check password
    const isMatch = await user.matchPassword(password);
    if (!isMatch) {
      const error = new Error('Invalid credentials');
      error.statusCode = 401;
      throw error;
    }

    // Generate token
    const token = this.generateToken(user._id);

    return {
      _id: user._id,
      name: user.name,
      email: user.email,
      role: user.role,
      token
    };
  }

  /**
   * Validates a token
   * @param {string} token - JWT token
   * @returns {Promise<Object>} - Decoded token data
   */
  static async validateToken(token) {
    try {
      const decoded = jwt.verify(token, process.env.JWT_SECRET);
      
      // Check if user exists and is active
      const user = await User.findById(decoded.id).select('-password');
      if (!user || !user.isActive) {
        const error = new Error('User not found or inactive');
        error.statusCode = 401;
        throw error;
      }
      
      return { user, decoded };
    } catch (error) {
      error.statusCode = 401;
      error.message = 'Invalid token';
      throw error;
    }
  }

  /**
   * Refreshes a token
   * @param {string} token - Current JWT token
   * @returns {Promise<Object>} - New token and expiry
   */
  static async refreshToken(token) {
    try {
      // Verify current token
      const { user } = await this.validateToken(token);
      
      // Generate new token
      const newToken = this.generateToken(user._id);
      
      return { token: newToken };
    } catch (error) {
      error.statusCode = 401;
      error.message = 'Could not refresh token';
      throw error;
    }
  }

  /**
   * Logs out a user (invalidates token)
   * @param {string} token - JWT token
   * @returns {Promise<boolean>} - Success status
   */
  static async logoutUser(token) {
    // Note: In a stateless JWT system, logout is handled client-side by removing the token
    // For a more secure implementation, we'd use a token blacklist or Redis cache
    return true;
  }

  /**
   * Helper method to generate a JWT token
   * @param {string} userId - User ID
   * @returns {string} JWT token
   */
  static generateToken(userId) {
    return jwt.sign({ id: userId }, process.env.JWT_SECRET, {
      expiresIn: '30d'
    });
  }
}

module.exports = AuthOperations; 