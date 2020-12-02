import request from '@/utils/request'

// 역할

// 페이지 수 정보 받기
export function getRolePage(params) {
  return request({ url: '/sys/role/page', method: 'get', data: params })
}

// 열 테이블 정보 가져 오기
export function getRoleAll() {
  return request({ url: '/sys/role/all', method: 'get' })
}

// ID로 단일 역할 정보 쿼리
export function getRoleById(params) {
  return request({ url: '/sys/role/info/' + params, method: 'get' })
}

// 역할 추가
export function saveRole(params) {
  return request({ url: '/sys/role', method: 'post', data: params })
}

// 수정역할
export function updateRole(params) {
  return request({ url: '/sys/role', method: 'put', data: params })
}

// 삭제역할
export function removeRoleById(params) {
  return request({ url: '/sys/role/' + params, method: 'delete' })
}

// 수정역할권한
export function updateRolePermission(params) {
  return request({ url: '/sys/role/update/permissions', method: 'post', data: params })
}

