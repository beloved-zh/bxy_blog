import request from '@/utils/request'

export function findAll() {
  return request({
    url: '/role/findAll',
    method: 'get'
  })
}

export function getRoles(keyword, currentPage, pageSize) {
  return request({
    url: `/role/getRoles`,
    method: 'get',
    params: { keyword, currentPage, pageSize }
  })
}

export function addRole(data) {
  return request({
    url: '/role/addRole',
    method: 'post',
    data
  })
}

export function updateRole(data) {
  return request({
    url: `/role/updateRole`,
    method: 'put',
    data
  })
}

export function deleteRole(id) {
  return request({
    url: `/role/deleteRole`,
    method: 'delete',
    data: id
  })
}

export function deleteRoleList(ids) {
  return request({
    url: `/role/deleteRoleList`,
    method: 'delete',
    data: ids
  })
}
