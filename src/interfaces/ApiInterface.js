/**
 * API Interface
 * Defines methods for external API communication
 */
class ApiInterface {
  /**
   * Make a GET request to an external API
   * @param {string} url - The URL to call
   * @param {Object} params - Optional query parameters
   * @param {Object} headers - Optional request headers
   * @returns {Promise<Object>} - Response data
   */
  static async get(url, params = {}, headers = {}) {
    throw new Error('Method not implemented');
  }

  /**
   * Make a POST request to an external API
   * @param {string} url - The URL to call
   * @param {Object} data - Request body data
   * @param {Object} headers - Optional request headers
   * @returns {Promise<Object>} - Response data
   */
  static async post(url, data = {}, headers = {}) {
    throw new Error('Method not implemented');
  }

  /**
   * Make a PUT request to an external API
   * @param {string} url - The URL to call
   * @param {Object} data - Request body data
   * @param {Object} headers - Optional request headers
   * @returns {Promise<Object>} - Response data
   */
  static async put(url, data = {}, headers = {}) {
    throw new Error('Method not implemented');
  }

  /**
   * Make a DELETE request to an external API
   * @param {string} url - The URL to call
   * @param {Object} headers - Optional request headers
   * @returns {Promise<Object>} - Response data
   */
  static async delete(url, headers = {}) {
    throw new Error('Method not implemented');
  }
}

module.exports = ApiInterface; 