const express = require('express');
const router = express.Router();
const ContentOperations = require('../operations/ContentOperations');
const { asyncHandler } = require('../utils/errorHandler');
const { protect, authorize } = require('../utils/authMiddleware');

/**
 * @route   GET /api/content
 * @desc    Get all content items
 * @access  Public/Private depending on content status
 */
router.get('/', asyncHandler(async (req, res) => {
  // Parse query parameters for filtering and pagination
  const page = parseInt(req.query.page, 10) || 1;
  const limit = parseInt(req.query.limit, 10) || 10;
  const sort = req.query.sort || '-createdAt';

  // Build filter object
  const filter = {};
  
  // Content type filtering
  if (req.query.type) filter.type = req.query.type;
  
  // Category filtering
  if (req.query.category) filter.category = req.query.category;
  
  // Tag filtering
  if (req.query.tag) filter.tags = req.query.tag;
  
  // Search functionality
  if (req.query.search) {
    filter.$text = { $search: req.query.search };
  }
  
  // If not authenticated or not admin, only show published content
  if (!req.user || req.user.role !== 'admin') {
    filter.status = 'published';
  }

  const result = await ContentOperations.getContentList(filter, { 
    page, 
    limit, 
    sort 
  });

  res.json({
    success: true,
    data: result
  });
}));

/**
 * @route   GET /api/content/:id
 * @desc    Get single content item
 * @access  Public/Private depending on content status
 */
router.get('/:id', asyncHandler(async (req, res) => {
  const content = await ContentOperations.getContentById(req.params.id);
  
  // If content is not published, only author or admin can view it
  if (
    content.status !== 'published' && 
    (!req.user || 
      (req.user.role !== 'admin' && 
      req.user._id.toString() !== content.author._id.toString()))
  ) {
    return res.status(403).json({
      success: false,
      error: 'Not authorized to access this content'
    });
  }

  res.json({
    success: true,
    data: content
  });
}));

/**
 * @route   POST /api/content
 * @desc    Create new content
 * @access  Private
 */
router.post('/', protect, asyncHandler(async (req, res) => {
  // Add current user as author
  const contentData = {
    ...req.body,
    author: req.user._id
  };

  const content = await ContentOperations.createContent(contentData);

  res.status(201).json({
    success: true,
    data: content
  });
}));

/**
 * @route   PUT /api/content/:id
 * @desc    Update content
 * @access  Private (Admin or Author)
 */
router.put('/:id', protect, asyncHandler(async (req, res) => {
  // First check if content exists
  const content = await ContentOperations.getContentById(req.params.id);
  
  // Check ownership or admin role
  if (
    req.user.role !== 'admin' && 
    req.user._id.toString() !== content.author._id.toString()
  ) {
    return res.status(403).json({
      success: false,
      error: 'Not authorized to update this content'
    });
  }

  // Cannot change author
  delete req.body.author;
  
  const updatedContent = await ContentOperations.updateContent(req.params.id, req.body);

  res.json({
    success: true,
    data: updatedContent
  });
}));

/**
 * @route   DELETE /api/content/:id
 * @desc    Delete content
 * @access  Private (Admin or Author)
 */
router.delete('/:id', protect, asyncHandler(async (req, res) => {
  // First check if content exists
  const content = await ContentOperations.getContentById(req.params.id);
  
  // Check ownership or admin role
  if (
    req.user.role !== 'admin' && 
    req.user._id.toString() !== content.author._id.toString()
  ) {
    return res.status(403).json({
      success: false,
      error: 'Not authorized to delete this content'
    });
  }

  await ContentOperations.deleteContent(req.params.id);

  res.json({
    success: true,
    data: { message: 'Content deleted successfully' }
  });
}));

/**
 * @route   PUT /api/content/:id/publish
 * @desc    Publish content
 * @access  Private (Admin or Author)
 */
router.put('/:id/publish', protect, asyncHandler(async (req, res) => {
  // First check if content exists
  const content = await ContentOperations.getContentById(req.params.id);
  
  // Check ownership or admin role
  if (
    req.user.role !== 'admin' && 
    req.user._id.toString() !== content.author._id.toString()
  ) {
    return res.status(403).json({
      success: false,
      error: 'Not authorized to publish this content'
    });
  }

  const publishedContent = await ContentOperations.publishContent(req.params.id);

  res.json({
    success: true,
    data: publishedContent
  });
}));

/**
 * @route   PUT /api/content/:id/unpublish
 * @desc    Unpublish content
 * @access  Private (Admin or Author)
 */
router.put('/:id/unpublish', protect, asyncHandler(async (req, res) => {
  // First check if content exists
  const content = await ContentOperations.getContentById(req.params.id);
  
  // Check ownership or admin role
  if (
    req.user.role !== 'admin' && 
    req.user._id.toString() !== content.author._id.toString()
  ) {
    return res.status(403).json({
      success: false,
      error: 'Not authorized to unpublish this content'
    });
  }

  const unpublishedContent = await ContentOperations.unpublishContent(req.params.id);

  res.json({
    success: true,
    data: unpublishedContent
  });
}));

module.exports = router; 