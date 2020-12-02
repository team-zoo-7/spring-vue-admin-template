const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  nickname: state => state.user.nickname,
  username: state => state.user.username,

  avatar: state => state.user.avatar,
  userinfo: state => state.user.userinfo,

  roles: state => state.user.roles,
  // 파싱되지 않은 백그라운드에서 얻은 사용자 메뉴
  menus: state => state.user.menus,
  // 백엔드에서 얻은 사용자 버튼 권한
  buttons: state => state.user.buttons,

  // permission.js
  // 메뉴 라우팅, 페이지에 표시
  menu_routers: state => state.permission.routers,
  // 사용자의 현재 동적 메뉴 라우팅
  dynamicRouters: state => state.permission.dynamicRouters,

  // 일반 정보
  website: state => state.common.website

}
export default getters
