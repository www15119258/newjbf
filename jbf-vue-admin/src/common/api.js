const API = {
  install (Vue, options) {
    Vue.prototype.$copy = function (data) {
      return JSON.parse(JSON.stringify(data))
    }
    Vue.prototype.$setFileManageOption = function (key, value) {
      let option = JSON.parse(localStorage.getItem('fmop') || '{"mode": 0, "times": 1}')
      option[key] = value
      localStorage.setItem('fmop', JSON.stringify(option))
    }
    Vue.prototype.$getFileManageOption = function (key) {
      if (key === undefined) {
        return JSON.parse(localStorage.getItem('fmop') || '{"mode": 0, "times": 1}')
      }
      let option = JSON.parse(localStorage.getItem('fmop') || '{"mode": 0, "times": 1}')
      return option[key]
    }
    Vue.prototype.$handleCommonResponse = function (response, callback) {
      if (response && response.data && response.data.status === 'success') {
        this.$message({
          message: '操作成功',
          type: 'success'
        })
        callback()
      } else if (response && response.data && response.data.status !== 'success') {
        this.$message({
          message: response.data.message,
          type: 'error'
        })
      } else {
        this.$message({
          message: '系统错误',
          type: 'error'
        })
      }
    }
    Vue.prototype.$hasPerm = function (perm) {
      let perms = JSON.parse(localStorage.getItem('perms'))
      if (perms.indexOf(perm) >= 0) {
        return true
      }
      return false
    }
  }
}

export default API
