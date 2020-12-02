import request from '@/utils/request'

/**
 * 사용자 로그인
 *
 * @export
 * @param {*} params
 * @returns
 */
export function login(params) {
  return request({ url: '/auth/login', method: 'post', data: params })
}

/**
 * 현재 로그인되어있는 사용자 정보 얻기
 *
 * @export
 * @returns
 */
export function getUserInfo() {
  return request({ url: '/sys/user/info', method: 'get' })
}

/**
 * 사용자 로그 아웃
 *
 * @export
 * @param {*} params
 * @returns
 */
export function logout() {
  return request({ url: '/auth/logout', method: 'get' })
}

// 페이지 수 정보 받기
export function getUserPage(params) {
  return request({ url: '/sys/user/info/page', method: 'get', data: params })
}

// 열 테이블 정보 가져 오기
export function getUserAll() {
  return request({ url: '/user/all', method: 'get' })
}

// 아이디로 단일 사용자 정보 조회
export function getUserByUid(params) {
  return request({ url: '/sys/user/' + params, method: 'get' })
}

// 추가사용자
export function saveUser(params) {
  return request({ url: '/sys/user', method: 'post', data: params })
}

// 수정사용자
export function updateUser(params) {
  return request({ url: '/sys/user', method: 'put', data: params })
}

// 삭제사용자
export function removeUserById(params) {
  return request({ url: '/sys/user/' + params, method: 'delete' })
}

// 수정사용자권한
export function updateUserRoles(params) {
  return request({ url: '/sys/user/update/roles', method: 'post', data: params })
}
