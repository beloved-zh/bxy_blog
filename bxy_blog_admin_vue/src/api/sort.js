import request from '@/utils/request'

export function getSort(params) {
  return request({
    url: `/sort/getSort`,
    method: 'get',
    params
  })
}

export function findAllSort() {
  return request({
    url: `/sort/findAll`,
    method: 'get'
  })
}

export function addSort(data) {
  return request({
    url: `/sort/addSort`,
    method: 'post',
    data
  })
}

export function updateSort(data) {
  return request({
    url: '/sort/updateSort',
    method: 'put',
    data
  })
}

export function deleteSort(id) {
  return request({
    url: `/sort/deleteSort`,
    method: 'delete',
    data: id
  })
}

export function deleteSortList(ids) {
  return request({
    url: `/sort/deleteSortList`,
    method: 'delete',
    data: ids
  })
}
