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

export function getBlogByTop() {
  return request({
    url: `/blog/getBlogByTop`,
    method: 'get'
  })
}
