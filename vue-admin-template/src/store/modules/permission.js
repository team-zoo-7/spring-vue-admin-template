// store/permission.js
import { asyncRouterMap, constantRouterMap } from '@/router'

/**
 *
 * @param  {Array} userRouter 무대 뒤에서 사용자권한json
 * @param  {Array} allRouter  프런트 엔드에 구성된 모든 동적 경로 모음
 * @return {Array} realRoutes 필터링 된 경로
 */

export function recursionRouter(userRouter = [], allRouter = []) {
  var realRoutes = []
  allRouter.forEach((v, i) => {
    userRouter.forEach((item, index) => {
      if (item.resources === v.meta.resources) {
        if (item.children && item.children.length > 0) {
          v.children = recursionRouter(item.children, v.children)
        }
        v.meta.title = item.title
        v.meta.icon = item.icon
        realRoutes.push(v)
      }
    })
  })
  return realRoutes
}

/**
*
* @param {Array} routes 사용자필터링 된 경로
*
* 하위 경로가있는 모든 경로에 대해 재귀 적으로 첫 번째 설정一个children.path 기본 경로입니다.
*/
export function setDefaultRoute(routes) {
  routes.forEach((v, i) => {
    if (v.children && v.children.length > 0) {
      v.redirect = { name: v.children[0].name }
      setDefaultRoute(v.children)
    }
  })
}

const permission = {
  state: {
    routers: constantRouterMap, // 이것은 404 500 및 기타 경로와 같은 기본 권한 열 테이블입니다.
    dynamicRouters: [] // 이것은 백엔드 테이블을 통해 얻은 오른쪽 열입니다.
  },
  mutations: {
    SET_ROUTERS: (state, routers) => {
      state.dynamicRouters = routers
      state.routers = constantRouterMap.concat(routers)
    }
  },
  actions: {
    GenerateRoutes({ commit }, data) {
      return new Promise(resolve => {
        commit('SET_ROUTERS', recursionRouter(data, asyncRouterMap))
        resolve()
      })
    }
  }
}

export default permission
