import request from '@/utils/request'

export function getBlog(params) {
  return request({
    url: `/blog/getBlog`,
    method: 'get',
    params
  })
}

export function addBlog(data) {
  return request({
    url: `/blog/addBlog`,
    method: 'post',
    data
  })
}

export function updateBlog(data) {
  return request({
    url: '/blog/updateBlog',
    method: 'put',
    data
  })
}

export function deleteBlog(id) {
  return request({
    url: '/blog/deleteBlog',
    method: 'delete',
    data: id
  })
}
