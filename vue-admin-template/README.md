# vue-admin-template

> 미니멀 한 vue 관리자 관리 백엔드으로 백엔드을 구축하는 데 필요한 요소 UI 및 축, 아이콘 글꼴 및 권한 제어 및 린트 만 포함되어 있습니다.

[온라인 주소](http://panjiachen.github.io/vue-admin-template)

[국내 방문](https://panjiachen.gitee.io/vue-admin-template)

## Extra

사용자 역할을 기반으로 사이드 바 및 라우터를 동적으로 생성하려면이 브랜치를 사용할 수 있습니다.[permission-control](https://github.com/PanJiaChen/vue-admin-template/tree/permission-control)

이 프로젝트는`webpack4` 개발을 기반으로하며`webpack3` 개발을 사용하려면이 브랜치를 사용하십시오.[webpack3](https://github.com/PanJiaChen/vue-admin-template/tree/webpack3)

vue + typescript 기반의 관리 백엔드를 사용하려면이 프로젝트를 살펴볼 수 있습니다.: [vue-typescript-admin-template](https://github.com/Armour/vue-typescript-admin-template) (감사: [@Armour](https://github.com/Armour))

## 관련 항목

[vue-element-admin](https://github.com/PanJiaChen/vue-element-admin)

[electron-vue-admin](https://github.com/PanJiaChen/electron-vue-admin)

[vue-typescript-admin-template](https://github.com/Armour/vue-typescript-admin-template)

## Build Setup

```bash
# Clone project
git clone https://github.com/PanJiaChen/vue-admin-template.git

# Install dependencies
npm install

# 모든 종류의 이상한 버그를 설치하기 위해 cnpm을 사용하지 않는 것이 좋습니다. 다음을 수행하여 느린 npm 문제를 해결할 수 있습니다.
npm install --registry=https://registry.npm.taobao.org

# Serve with hot reload at localhost:9528
npm run dev

# Build for production with minification
npm run build

# Build for production and view the bundle analyzer report
npm run build --report
```

## Demo

![demo](https://github.com/PanJiaChen/PanJiaChen.github.io/blob/master/images/demo.gif)

### Element-Ui 사용 cdn 튜토리얼

먼저 찾기 `index.html` ([루트 디렉토리](https://github.com/PanJiaChen/vue-admin-template/blob/element-ui-cdn/index.html))

Element의 CSS와 JS를 소개하고 VUE를 소개합니다. Element-Ui는 vue에 의존하기 때문에 vue를 먼저 도입해야합니다.

이후 발견 [webpack.base.conf.js](https://github.com/PanJiaChen/vue-admin-template/blob/element-ui-cdn/build/webpack.base.conf.js) webpack을 패키지 vue 및 요소가 아닌`externals` 추가

```
externals: {
  vue: 'Vue',
  'element-ui':'ELEMENT'
}
```

그 후 글로벌 객체 메소드를 사용하여 vue를 도입하면 수동으로`Vue.use (Vuex)`를 사용할 필요가 없으며 자동으로 마운트된다는 작은 세부 사항이 있습니다. [issue](https://github.com/vuejs/vuex/issues/731)

마지막으로`npm run build --report`를 사용하여 효과를 확인할 수 있습니다.
보여진 바와 같이：
![demo](https://panjiachen.github.io/images/element-cdn.png)

**[특정 코드](https://github.com/PanJiaChen/vue-admin-template/commit/746aff560932704ae821f82f10b8b2a9681d5177)**

**[해당 지점](https://github.com/PanJiaChen/vue-admin-template/tree/element-ui-cdn)**

## Browsers support

Modern browsers and Internet Explorer 10+.

| [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/edge/edge_48x48.png" alt="IE / Edge" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>IE / Edge | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/firefox/firefox_48x48.png" alt="Firefox" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Firefox | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/chrome/chrome_48x48.png" alt="Chrome" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Chrome | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/safari/safari_48x48.png" alt="Safari" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Safari |
| --------- | --------- | --------- | --------- |
| IE10, IE11, Edge| last 2 versions| last 2 versions| last 2 versions

## License

[MIT](https://github.com/PanJiaChen/vue-admin-template/blob/master/LICENSE) license.

Copyright (c) 2017-present PanJiaChen
