import request from '@/utils/request'

export function findAllSort() {
  return request({
    url: `/sort/findAll`,
    method: 'get'
  })
}