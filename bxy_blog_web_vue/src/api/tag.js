import request from '@/utils/request'

export function findAllTag() {
  return request({
    url: `/tags/findAll`,
    method: 'get'
  })
}

