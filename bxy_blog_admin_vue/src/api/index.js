import request from '@/utils/request'

export function init() {
  return request({
    url: `/index/init`,
    method: 'get'
  })
}
