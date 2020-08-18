import request from '@/utils/request'

export function getLinks() {
  return request({
    url: `/links/getLinks`,
    method: 'get'
  })
}

export function getLinkByUserAndStatus(params) {
  return request({
    url: `/links/getLinkByUserAndStatus`,
    method: 'get',
    params
  })
}

export function addLink(data) {
  return request({
    url: '/links/addLink',
    method: 'post',
    data
  })
}

