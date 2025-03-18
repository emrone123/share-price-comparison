const Content = require('../models/Content');
const ContentInterface = require('../interfaces/ContentInterface');

/**
 * Content Operations
 * Implements the ContentInterface methods
 */
class ContentOperations extends ContentInterface {
  /**
   * Gets a list of content items
   * @param {Object} filter - Optional filters to apply
   * @param {Object} options - Pagination and sorting options
   * @returns {Promise<Array>} - List of content items
   */
  static async getContentList(filter = {}, options = {}) {
    const { page = 1, limit = 10, sort = '-createdAt', populate = true } = options;
    
    const skip = (page - 1) * limit;
    
    let query = Content.find(filter)
      .sort(sort)
      .skip(skip)
      .limit(limit);
    
    // Optionally populate author details
    if (populate) {
      query = query.populate({
        path: 'author',
        select: 'name email'
      });
    }
    
    const contents = await query;
    const total = await Content.countDocuments(filter);
    
    return {
      contents,
      pagination: {
        total,
        page,
        limit,
        pages: Math.ceil(total / limit)
      }
    };
  }

  /**
   * Gets a content item by ID
   * @param {string} contentId - Content ID
   * @returns {Promise<Object>} - Content object
   */
  static async getContentById(contentId) {
    const content = await Content.findById(contentId).populate({
      path: 'author',
      select: 'name email'
    });
    
    if (!content) {
      const error = new Error('Content not found');
      error.statusCode = 404;
      throw error;
    }
    
    return content;
  }

  /**
   * Creates a new content item
   * @param {Object} contentData - Content data
   * @returns {Promise<Object>} - Created content object
   */
  static async createContent(contentData) {
    const content = await Content.create(contentData);
    
    return content;
  }

  /**
   * Updates a content item
   * @param {string} contentId - Content ID
   * @param {Object} updates - Content data to update
   * @returns {Promise<Object>} - Updated content object
   */
  static async updateContent(contentId, updates) {
    const content = await Content.findByIdAndUpdate(
      contentId,
      updates,
      { new: true, runValidators: true }
    ).populate({
      path: 'author',
      select: 'name email'
    });
    
    if (!content) {
      const error = new Error('Content not found');
      error.statusCode = 404;
      throw error;
    }
    
    return content;
  }

  /**
   * Deletes a content item
   * @param {string} contentId - Content ID
   * @returns {Promise<boolean>} - Success status
   */
  static async deleteContent(contentId) {
    const content = await Content.findById(contentId);
    
    if (!content) {
      const error = new Error('Content not found');
      error.statusCode = 404;
      throw error;
    }
    
    await content.remove();
    return true;
  }

  /**
   * Publishes a content item
   * @param {string} contentId - Content ID
   * @returns {Promise<Object>} - Published content object
   */
  static async publishContent(contentId) {
    const content = await Content.findById(contentId);
    
    if (!content) {
      const error = new Error('Content not found');
      error.statusCode = 404;
      throw error;
    }
    
    content.status = 'published';
    content.publishedAt = new Date();
    
    await content.save();
    
    return content;
  }

  /**
   * Unpublishes a content item
   * @param {string} contentId - Content ID
   * @returns {Promise<Object>} - Unpublished content object
   */
  static async unpublishContent(contentId) {
    const content = await Content.findById(contentId);
    
    if (!content) {
      const error = new Error('Content not found');
      error.statusCode = 404;
      throw error;
    }
    
    content.status = 'draft';
    
    await content.save();
    
    return content;
  }
}

module.exports = ContentOperations; 