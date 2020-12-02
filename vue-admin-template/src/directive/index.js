import Vue from 'vue'
import store from '@/store'

/** 글로벌 디렉티브 등록**/

/**
 * 판사 권한 지침
 * 용법: <el-button v-has="'perm:new'" class="btns">추가</el-button>
 */
Vue.directive('has', {
  bind: function(el, binding) {
    if (!Vue.prototype.$_has(binding.value)) {
      el.parentNode.removeChild(el)
    }
  }
})

// 권한 검사 방법
Vue.prototype.$_has = function(value) {
  // 사용자 버튼 권한 받기
  let isExist = false
  // 현재 사용자의 모든 버튼 열 테이블
  const dynamicButtons = store.getters.buttons
  if (dynamicButtons === undefined || dynamicButtons === null || dynamicButtons.length < 1) {
    return isExist
  }
  dynamicButtons.forEach(button => {
    if (button.resources === value) {
      isExist = true
      return isExist
    }
  })
  return isExist
}

/**
 * 수정 제목 명령
 * 용법: <div v-title>기사 관리</div>
 */
Vue.directive('title', {
  inserted: function(el, binding) {
    document.title = el.innerText
    el.remove()
  }
})
