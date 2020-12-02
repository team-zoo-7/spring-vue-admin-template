import router from './router'
import store from './store'
import NProgress from 'nprogress' // Progress 진행 표시 줄
import 'nprogress/nprogress.css'// Progress 진행률 표시 줄 스타일
import { Message } from 'element-ui'
import { getToken } from '@/utils/auth' // 확인

const whiteList = ['/login'] // 화이트리스트 리디렉션 안함
router.beforeEach((to, from, next) => {
  NProgress.start()
  if (getToken()) {
    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done() // if current page is dashboard will not trigger	afterEach hook, so manually handle it
    } else {
      if (store.getters.menus.length === 0) {
        // 사용자 정보 가져 오기(확인하십시오 GetInfo 이 방법에서 메뉴 열 테이블을 얻었습니다.)
        store.dispatch('GetInfo').then(res => {
          // 동적으로 설정된 라우팅（이전 단계에서 얻은 사용자를 GenerateRoutes 메서드에 전달합니다.）
          store.dispatch('GenerateRoutes', store.getters.menus).then(r => {
            // 구문 분석 된 라우팅 열 테이블 가져 오기, 라우터에 대한 동적 추가
            router.addRoutes(store.getters.dynamicRouters)
            // addRoutes가 완료되었는지 확인하는 해킹 방법
            next({ ...to, replace: true })
          })
        }).catch((err) => {
          store.dispatch('FedLogOut').then(() => {
            Message.error(err || 'Verification failed, please login again')
            next({ path: '/' })
          })
        })
      } else {
        next()
      }
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      next(`/login?redirect=${to.path}`) // 그렇지 않으면 모두 로그인 페이지로 리디렉션됩니다.
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done() // 종료Progress
})
