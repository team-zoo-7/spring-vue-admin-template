import axios from 'axios'
import Qs from 'qs'
import { Message, MessageBox } from 'element-ui'
import store from '../store'
import { getToken, getHeader } from '@/utils/auth'
// axios 인스턴스 만들기
const service = axios.create({
  baseURL: process.env.BASE_API, // API의 base_url
  timeout: 50000 // 요청 시간 초과
})

// 인터셉터 요청
service.interceptors.request.use(
  config => {
    if (getToken() !== '') {
      config.headers[getHeader()] = getToken()
    }
    const method = config.method.toLocaleLowerCase()
    if (method === 'get') {
      // GET 요청 직렬화 매개 변수
      if (config.data !== undefined && config.data !== null) {
        config.url += ('?' + Qs.stringify(config.data))
      }
    }
    return config
  },
  error => {
    // Do something with request error
    console.log(error) // for debug
    Promise.reject(error)
  }
)

// response 인터셉터
service.interceptors.response.use(
  response => {
    /**
     * status 200이 아니라면 실수를 던진다는 뜻이며, 비즈니스와 결합 할 수 있습니다.
     */
    const result = response.data
    if (result.status !== 200) {
      // 50008:잘못된 토큰, 50012:로그인 한 다른 클라이언트, 50014:토큰 만료;
      if (result.status === 401) {
        MessageBox.confirm(
          '로그 아웃되었습니다. 취소하여이 페이지에 머물거나 다시 로그인 할 수 있습니다.',
          '로그 아웃 하시겠습니까?',
          {
            confirmButtonText: '재 등록',
            cancelButtonText: '취소',
            type: 'warning'
          }
        ).then(() => {
          store.dispatch('FedLogOut').then(() => {
            location.reload() // 버그를 피하기 위해 vue-router 객체를 다시 인스턴스화하기 위해
          })
        })
      }

      // 시스템 내부 오류
      Message({
        message: result.message,
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject('error')
    } else {
      return result.data
    }
  },
  error => {
    console.log('err' + error) // for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
