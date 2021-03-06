import request from '@/utils/request'

export function getBlogByLevel(params) {
  return request({
    url: `/blog/getBlogByLevel`,
    method: 'get',
    params
  })
}

export function getBlogByLevelAndPage(params) {
  return request({
    url: `/blog/getBlogByLevelAndPage`,
    method: 'get',
    params
  })
}

export function getBlogByMonth(params) {
  return request({
    url: `/blog/getBlogByMonth`,
    method: 'get',
    params
  })
}

export function getBlogByTop() {
  return request({
    url: `/blog/getBlogByTop`,
    method: 'get'
  })
}

export function getFirstBlogGoNowMonth() {
  return request({
    url: `/blog/getFirstBlogGoNowMonth`,
    method: 'get'
  })
}

export function getBlogById(params) {
  return request({
    url: `/blog/getBlogById`,
    method: 'get',
    params
  })
}

export function getBlogBySortId(params) {
  return request({
    url: `/blog/getBlogBySortId`,
    method: 'get',
    params
  })
}

export function getBlogByTagId(params) {
  return request({
    url: `/blog/getBlogByTagId`,
    method: 'get',
    params
  })
}
