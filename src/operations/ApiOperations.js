const axios = require('axios');
const ApiInterface = require('../interfaces/ApiInterface');

/**
 * API Operations 
 * Implements the ApiInterface methods using axios
 */
class ApiOperations extends ApiInterface {
  /**
   * Make a GET request to an external API
   * @param {string} url - The URL to call
   * @param {Object} params - Optional query parameters
   * @param {Object} headers - Optional request headers
   * @returns {Promise<Object>} - Response data
   */
  static async get(url, params = {}, headers = {}) {
    try {
      const response = await axios.get(url, {
        params,
        headers: {
          'Content-Type': 'application/json',
          ...headers
        }
      });
      
      return response.data;
    } catch (error) {
      this.handleApiError(error);
    }
  }

  /**
   * Make a POST request to an external API
   * @param {string} url - The URL to call
   * @param {Object} data - Request body data
   * @param {Object} headers - Optional request headers
   * @returns {Promise<Object>} - Response data
   */
  static async post(url, data = {}, headers = {}) {
    try {
      const response = await axios.post(url, data, {
        headers: {
          'Content-Type': 'application/json',
          ...headers
        }
      });
      
      return response.data;
    } catch (error) {
      this.handleApiError(error);
    }
  }

  /**
   * Make a PUT request to an external API
   * @param {string} url - The URL to call
   * @param {Object} data - Request body data
   * @param {Object} headers - Optional request headers
   * @returns {Promise<Object>} - Response data
   */
  static async put(url, data = {}, headers = {}) {
    try {
      const response = await axios.put(url, data, {
        headers: {
          'Content-Type': 'application/json',
          ...headers
        }
      });
      
      return response.data;
    } catch (error) {
      this.handleApiError(error);
    }
  }

  /**
   * Make a DELETE request to an external API
   * @param {string} url - The URL to call
   * @param {Object} headers - Optional request headers
   * @returns {Promise<Object>} - Response data
   */
  static async delete(url, headers = {}) {
    try {
      const response = await axios.delete(url, {
        headers: {
          'Content-Type': 'application/json',
          ...headers
        }
      });
      
      return response.data;
    } catch (error) {
      this.handleApiError(error);
    }
  }

  /**
   * Handle API errors
   * @param {Error} error - The error object
   * @throws {Error} Formatted error
   */
  static handleApiError(error) {
    let errorMessage = 'API request failed';
    let statusCode = 500;
    
    if (error.response) {
      // The request was made and the server responded with a status code
      // that falls out of the range of 2xx
      errorMessage = error.response.data.message || 'Server responded with an error';
      statusCode = error.response.status;
    } else if (error.request) {
      // The request was made but no response was received
      errorMessage = 'No response received from server';
    } else {
      // Something happened in setting up the request that triggered an Error
      errorMessage = error.message;
    }
    
    const apiError = new Error(errorMessage);
    apiError.statusCode = statusCode;
    apiError.originalError = error;
    
    throw apiError;
  }
}

module.exports = ApiOperations; 