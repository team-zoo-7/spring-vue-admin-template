import request from '@/utils/request'

// 부서

// 열 테이블 정보 가져 오기
export function getDeptTree() {
  return request({ url: '/sys/department/tree', method: 'get' })
}

// 열 테이블 정보 가져 오기
export function getDeptPage(params) {
  return request({ url: '/sys/department/page', method: 'get', data: params })
}

// 열 테이블 정보 가져 오기
export function getDeptAll() {
  return request({ url: '/sys/department/all', method: 'get' })
}

// 단일부서 정보를 아이디로 조회
export function getDeptById(params) {
  return request({ url: '/sys/department/' + params, method: 'get' })
}

// 추가부서
export function saveDept(params) {
  return request({ url: '/sys/department', method: 'post', data: params })
}

// 수정부서
export function updateDept(params) {
  return request({ url: '/sys/department', method: 'put', data: params })
}

// 삭제부서
export function removeDeptById(params) {
  return request({ url: '/sys/department/' + params, method: 'delete' })
}
