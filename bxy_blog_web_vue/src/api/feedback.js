import request from '@/utils/request'

export function addFeedback(data) {
  return request({
    url: `/feedback/addFeedback`,
    method: 'post',
    data
  })
}

export function getFeedbackByUser(params) {
  return request({
    url: `/feedback/getFeedbackByUser`,
    method: 'get',
    params
  })
}