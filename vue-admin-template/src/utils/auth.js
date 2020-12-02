import Cookies from 'js-cookie'

// 쿠키 키 이름의 토큰 값
const TokenKey = 'Token-Value'

// 쿠키 키 이름의 요청 헤더
const HeaderKey = 'Token-Header'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function getHeader() {
  return Cookies.get(HeaderKey)
}

export function setToken(header, token) {
  Cookies.set(HeaderKey, header)
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  Cookies.remove(TokenKey)
  return Cookies.remove(TokenKey)
}
