import request from '@/utils/request'

// 페이지 수 정보 받기
export function getChinaAreaTree() {
  return request({ url: '/china/area/tree', method: 'get' })
}

// 열 테이블 정보 가져 오기
export function getChinaAreaAll(params) {
  return request({ url: '/china/area/all' + params, method: 'get' })
}

// 아이디로 단일 사용자 정보 조회
export function getChinaAreaById(params) {
  return request({ url: '/china/area/' + params, method: 'get' })
}
