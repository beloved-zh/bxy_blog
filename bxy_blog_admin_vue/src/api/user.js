import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/auth/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/auth/info',
    method: 'get',
    params: { token }
  })
}

export function getMenus(token) {
  return request({
    url: '/auth/getMenus',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/auth/logout',
    method: 'post'
  })
}

export function getUser(params) {
  return request({
    url: '/user/getUser',
    method: 'get',
    params
  })
}

export function addUser(data) {
  return request({
    url: `/user/addUser`,
    method: 'post',
    data
  })
}

export function updateUser(data) {
  return request({
    url: `/user/updateUser`,
    method: 'put',
    data
  })
}

export function deleteUser(data) {
  return request({
    url: `/user/deleteUser`,
    method: 'delete',
    data
  })
}
