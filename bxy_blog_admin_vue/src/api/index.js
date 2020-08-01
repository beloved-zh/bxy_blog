import request from '@/utils/request'

export function init() {
  return request({
    url: `/index/init`,
    method: 'get'
  })
}

export function getBlogCountByBlogSort() {
  return request({
    url: `/index/getBlogCountByBlogSort`,
    method: 'get'
  })
}

export function getBlogCountByTag() {
  return request({
    url: `/index/getBlogCountByTag`,
    method: 'get'
  })
}

export function getBlogCountByLevel() {
  return request({
    url: `/index/getBlogCountByLevel`,
    method: 'get'
  })
}

export function getBlogContributeCount() {
  return request({
    url: `/index/getBlogContributeCount`,
    method: 'get'
  })
}
