import { login, logout, getUserInfo } from '@/api/sysUser'
import { getToken, getHeader, setToken, removeToken } from '@/utils/auth'

const user = {
  state: {
    token: getToken(),
    header: getHeader(),
    nickname: '',
    username: '',
    avatar: '',
    userinfo: {},
    roles: [],
    menus: [], // 메뉴 권한
    buttons: [] // 권한 설치
  },

  mutations: {
    SET_TOKEN: (state, header, token) => {
      state.header = header
      state.token = token
    },
    SET_INFO: (state, user) => {
      state.avatar = user.avatar
      state.username = user.username
      state.nickname = user.nickname
      state.userinfo = user
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_MENUS: (state, menus) => {
      state.menus = menus
    },
    SET_BUTTONS: (state, buttons) => {
      state.buttons = buttons
    }
  },

  actions: {
    // 로그인
    Login({ commit }, userInfo) {
      return new Promise((resolve, reject) => {
        login(userInfo).then(response => {
          const tokenValue = response.prefix + response.value
          setToken(response.header, tokenValue)
          commit('SET_TOKEN', response.header, tokenValue)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 사용자 정보 얻기
    GetInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        getUserInfo().then(response => {
          if (response.roles && response.roles.length > 0) { // 반환 된 역할이 비어 있지 않은 배열인지 확인
            commit('SET_ROLES', response.roles)
          } else {
            reject('getInfo: 현재 사용자에게는 역할이 없습니다. !')
          }
          commit('SET_INFO', response)
          commit('SET_MENUS', response.menus)
          commit('SET_BUTTONS', response.buttons)
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 로그 아웃
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout().then(() => {
          commit('SET_INFO', '')
          commit('SET_TOKEN', '', '')
          commit('SET_ROLES', [])
          removeToken()
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 로그 아웃
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_INFO', '')
        commit('SET_TOKEN', '', '')
        commit('SET_ROLES', [])
        removeToken()
        resolve()
      })
    }
  }
}

export default user
