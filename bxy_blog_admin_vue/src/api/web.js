import request from '@/utils/request'

export function getConfig() {
  return request({
    url: `/web/getConfig`,
    method: 'get'
  })
}

export function updateConfig(data) {
  return request({
    url: `/web/updateConfig`,
    method: 'put',
    data
  })
}
