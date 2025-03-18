const mongoose = require('mongoose');

const ContentSchema = new mongoose.Schema({
  title: {
    type: String,
    required: [true, 'Please add a title'],
    trim: true,
    maxlength: [100, 'Title cannot be more than 100 characters']
  },
  description: {
    type: String,
    required: [true, 'Please add a description'],
    maxlength: [500, 'Description cannot be more than 500 characters']
  },
  body: {
    type: String,
    required: [true, 'Please add content body']
  },
  type: {
    type: String,
    enum: ['article', 'page', 'post', 'document'],
    default: 'post'
  },
  author: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User',
    required: true
  },
  status: {
    type: String,
    enum: ['draft', 'published', 'archived'],
    default: 'draft'
  },
  tags: [String],
  publishedAt: {
    type: Date,
    default: null
  },
  featuredImage: {
    type: String,
    default: null
  },
  category: {
    type: String,
    default: 'uncategorized'
  },
  metadata: {
    type: mongoose.Schema.Types.Mixed,
    default: {}
  }
}, {
  timestamps: true
});

// Create index for search
ContentSchema.index({ title: 'text', description: 'text', body: 'text' });

module.exports = mongoose.model('Content', ContentSchema); 