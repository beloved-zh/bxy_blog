import request from '@/utils/request'

export function getAboutMe() {
  return request({
    url: `/aboutMe/getAboutMe`,
    method: 'get'
  })
}

