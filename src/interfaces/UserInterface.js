/**
 * User Interface
 * Defines methods for user management operations
 */
class UserInterface {
  /**
   * Gets a list of all users
   * @param {Object} filter - Optional filters to apply
   * @param {Object} options - Pagination and sorting options
   * @returns {Promise<Array>} - List of users
   */
  static async getUsers(filter = {}, options = {}) {
    throw new Error('Method not implemented');
  }

  /**
   * Gets a user by ID
   * @param {string} userId - User ID
   * @returns {Promise<Object>} - User object
   */
  static async getUserById(userId) {
    throw new Error('Method not implemented');
  }

  /**
   * Creates a new user
   * @param {Object} userData - User data
   * @returns {Promise<Object>} - Created user object
   */
  static async createUser(userData) {
    throw new Error('Method not implemented');
  }

  /**
   * Updates a user's information
   * @param {string} userId - User ID
   * @param {Object} updates - User data to update
   * @returns {Promise<Object>} - Updated user object
   */
  static async updateUser(userId, updates) {
    throw new Error('Method not implemented');
  }

  /**
   * Deletes a user
   * @param {string} userId - User ID
   * @returns {Promise<boolean>} - Success status
   */
  static async deleteUser(userId) {
    throw new Error('Method not implemented');
  }

  /**
   * Changes a user's password
   * @param {string} userId - User ID
   * @param {string} currentPassword - Current password
   * @param {string} newPassword - New password
   * @returns {Promise<boolean>} - Success status
   */
  static async changePassword(userId, currentPassword, newPassword) {
    throw new Error('Method not implemented');
  }
}

module.exports = UserInterface; 