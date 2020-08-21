import request from '@/utils/request'

export function getAboutMe() {
  return request({
    url: `/aboutMe/getAboutMe`,
    method: 'get'
  })
}

export function addAboutMe(data) {
  return request({
    url: `/aboutMe/addAboutMe`,
    method: 'post',
    data
  })
}
