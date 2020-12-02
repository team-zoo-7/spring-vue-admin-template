import request from '@/utils/request'

// 권한api

// 페이지 수 정보 받기
export function getPermissionTree(params) {
  return request({ url: '/sys/permission/tree', method: 'get', data: params })
}

// 페이지 수 정보 받기
export function getPermissionPage(params) {
  return request({ url: '/sys/permission/page', method: 'get', data: params })
}

// 열 테이블 정보 가져 오기
export function getPermissionAll() {
  return request({ url: '/sys/permission/all', method: 'get' })
}

// ID 기반 단일 권한 정보 조회
export function getPermissionById(params) {
  return request({ url: '/sys/permission/info/' + params, method: 'get' })
}

// 추가권한
export function savePermission(params) {
  return request({ url: '/sys/permission', method: 'post', data: params })
}

// 수정권한
export function updatePermission(params) {
  return request({ url: '/sys/permission', method: 'put', data: params })
}

// 삭제권한
export function removePermissionById(params) {
  return request({ url: '/sys/permission/' + params, method: 'delete' })
}

