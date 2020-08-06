import request from '@/utils/request'

export function getConfig() {
  return request({
    url: `/web/getConfig`,
    method: 'get'
  })
}
