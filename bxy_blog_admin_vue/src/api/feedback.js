import request from '@/utils/request'

export function getFeedback(params) {
  return request({
    url: `/feedback/getFeedback`,
    method: 'get',
    params
  })
}

export function deleteFeedback(id) {
  return request({
    url: `/feedback/deleteFeedback`,
    method: 'delete',
    data: id
  })
}

export function updateFeedback(data) {
  return request({
    url: `/feedback/updateFeedback`,
    method: 'put',
    data
  })
}
