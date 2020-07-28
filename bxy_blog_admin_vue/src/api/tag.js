import request from '@/utils/request'

export function getTags(params) {
  return request({
    url: `/tags/getTags`,
    method: 'get',
    params
  })
}

export function findAllTag() {
  return request({
    url: `/tags/findAll`,
    method: 'get'
  })
}

export function addTag(data) {
  return request({
    url: `/tags/addTag`,
    method: 'post',
    data
  })
}

export function updateTag(data) {
  return request({
    url: '/tags/updateTag',
    method: 'put',
    data
  })
}

export function deleteTag(id) {
  return request({
    url: `/tags/deleteTag`,
    method: 'delete',
    data: id
  })
}

export function deleteTagList(ids) {
  return request({
    url: `/tags/deleteTagList`,
    method: 'delete',
    data: ids
  })
}
