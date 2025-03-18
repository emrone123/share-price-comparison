/**
 * Authentication Interface
 * Defines methods for user authentication operations
 */
class AuthInterface {
  /**
   * Registers a new user
   * @param {Object} userData - User data including name, email, password
   * @returns {Promise<Object>} - Newly created user object
   */
  static async registerUser(userData) {
    throw new Error('Method not implemented');
  }

  /**
   * Authenticates a user
   * @param {string} email - User email
   * @param {string} password - User password
   * @returns {Promise<Object>} - Authentication result with token and user info
   */
  static async loginUser(email, password) {
    throw new Error('Method not implemented');
  }

  /**
   * Validates a JWT token
   * @param {string} token - JWT token
   * @returns {Promise<Object>} - Decoded token data
   */
  static async validateToken(token) {
    throw new Error('Method not implemented');
  }

  /**
   * Refreshes an authentication token
   * @param {string} refreshToken - Refresh token
   * @returns {Promise<Object>} - New tokens
   */
  static async refreshToken(refreshToken) {
    throw new Error('Method not implemented');
  }

  /**
   * Logs out a user
   * @param {string} userId - User ID
   * @returns {Promise<boolean>} - Success status
   */
  static async logoutUser(userId) {
    throw new Error('Method not implemented');
  }
}

module.exports = AuthInterface; 