import Vue from 'vue'

/**
 * 시각 형식
 * @param {*} date
 * @param {*} fmt
 */
function dateTimeFormat(date, fmt) {
  if (/(y+)/.test(fmt)) {
    fmt = fmt.replace(
      RegExp.$1,
      (date.getFullYear() + '').substr(4 - RegExp.$1.length)
    )
  }
  const o = {
    'M+': date.getMonth() + 1,
    'd+': date.getDate(),
    'h+': date.getHours(),
    'm+': date.getMinutes(),
    's+': date.getSeconds()
  }
  for (const k in o) {
    if (new RegExp(`(${k})`).test(fmt)) {
      const str = o[k] + ''
      fmt = fmt.replace(
        RegExp.$1,
        RegExp.$1.length === 1 ? str : ('00' + str).substr(str.length)
      )
    }
  }
  return fmt
}

// 시각 러빙 필터
Vue.filter('formatDate', function(time) {
  // 처리 된 값을 반환
  var date = new Date(time)
  return dateTimeFormat(date, 'yyyy-MM-dd')
})

// 시각 러빙 필터
Vue.filter('formatDateTime', function(time) {
  // 처리 된 값을 반환
  var date = new Date(time)
  return dateTimeFormat(date, 'yyyy-MM-dd hh:mm:ss')
})

// 상태 표시 필터
Vue.filter('showStatus', function(state) {
  // 상태 유형 확인
  let result = ''
  if (state === 0) {
    result = '삭제하세요'
  } else if (state === 1) {
    result = '정상'
  } else {
    result = '숨겨진'
  }
  return result
})

// 상태 표시 필터
Vue.filter('showGender', function(gender) {
  // 상태 유형 확인
  let result = ''
  if (gender === 0) {
    result = '년'
  } else if (gender === 1) {
    result = '남'
  } else {
    result = '미상'
  }
  return result
})
