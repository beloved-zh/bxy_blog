import request from '@/utils/request'

export function getMenus(params) {
  return request({
    url: `/menu/getMenus`,
    method: 'get',
    params
  })
}

export function findAllMenu() {
  return request({
    url: `/menu/findAll`,
    method: 'get'
  })
}

export function addMenu(data) {
  return request({
    url: `/menu/addMenu`,
    method: 'post',
    data
  })
}

export function deleteMenu(id) {
  return request({
    url: '/menu/deleteMenu',
    method: 'delete',
    data: id
  })
}

export function updateMenu(data) {
  return request({
    url: '/menu/updateMenu',
    method: 'put',
    data
  })
}
