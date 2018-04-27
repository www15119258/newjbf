// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import config from './config'
import Element from 'element-ui'
import API from './common/api'
import 'font-awesome/css/font-awesome.css'
import 'element-ui/lib/theme-chalk/index.css'
// import '../theme/index.css'
import router from './router'
import VueLazyLoad from 'vue-lazyload'
import VueClipboard from 'vue-clipboard2'
import axios from 'axios'
import directives from '@/directive/directives.js'

Vue.prototype.$config = config
Vue.use(Element, {size: config.system.elementui_default_size || 'mini'})
Vue.use(API)
Vue.use(VueLazyLoad, {
  error: './assets/error.png',
  loading: './assets/loading.gif'
})
Vue.use(VueClipboard)
Vue.use(directives)

Vue.config.productionTip = false

router.beforeEach((to, from, next) => {
  if (to.meta && to.meta.perm) {
    if (vue === undefined) {
      let perms = JSON.parse(localStorage.getItem('perms'))
      if (perms.indexOf(to.meta.perm) < 0) {
        next({ path: '/admin/403' })
        return
      }
    } else if (vue.$hasPerm(to.meta.perm) === false) {
      vue.$alert('您无权访问该页面！', '提示', {
        confirmButtonText: '确定',
        callback () {
        }
      })
      return
    }
  }
  if (to.path === '/admin/login') {
    localStorage.removeItem('user')
    next()
  } else {
    let user = JSON.parse(localStorage.getItem('user'))
    if (!user && to.path !== '/admin/login') {
      next({ path: '/admin/login' })
    } else {
      next()
    }
  }
})

/* eslint-disable no-new */
const vue = new Vue({
  el: '#app',
  router,
  data: {
    eventBus: new Vue()
  },
  components: { App },
  template: '<App/>'
})

axios.interceptors.response.use(function (response) {
  return response
}, function (error) {
  switch (error.response.status) {
    case 401:
      if (!(vue.not_login || vue.not_login === false)) {
        vue.not_login = true
        vue.$alert('未登录或登录超时，请重新登录系统！', '提示', {
          confirmButtonText: '确定',
          callback () {
            let fromPath = vue.$router.history.current.fullPath
            localStorage.setItem('fromPath', fromPath)
            vue.not_login = undefined
            router.push({path: '/admin/login'})
          }
        })
      }
      return
    case 403:
      error.message = '您无权访问该页面，请联系管理员申请权限！'
      break
    default :
      error.message = '系统错误，请联系管理员！'
      break
  }
  vue.$message({
    message: error.message,
    type: 'error'
  })
})
