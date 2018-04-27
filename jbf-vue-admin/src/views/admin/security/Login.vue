<template>
  <el-form :model="user" :rules="rules" ref="user" label-position="left" label-width="0px" class="login-container" v-loading="loading">
    <h3 class="title">{{$config.system.title}}</h3>
    <el-form-item prop="username">
      <el-input type="text" name="username" v-model="user.username" auto-complete="off" placeholder="用户名" @keyup.enter.native="loginSubmit"></el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input type="password" name="password" v-model="user.password" auto-complete="off" placeholder="密码" @keyup.enter.native="loginSubmit"></el-input>
    </el-form-item>
    <el-form-item prop="reset_pwd" align="right">
      <el-button type="text" >
        忘记密码
      </el-button>
    </el-form-item>
    <el-form-item style="width:100%;">
      <el-button type="primary" style="width:100%;" @click="loginSubmit" >登录</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import {login} from './api/login'
import MD5 from 'js-md5'

export default {
  name: 'HelloWorld',
  data () {
    return {
      loading: false,
      user: {
        username: '',
        password: ''
      },
      rules: {
        username: [
          { required: true, message: '用户名不能为空', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '密码不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    loginSubmit: function () {
      this.loading = true
      let userForm = this.$copy(this.user)
      userForm.password = MD5(userForm.password)
      login(userForm).then((response) => {
        this.loading = false
        if (response && response.data && response.data.status === 'success') {
          localStorage.setItem('user', JSON.stringify(response.data.data.user))
          localStorage.setItem('perms', JSON.stringify(response.data.data.perms))
          if (localStorage.getItem('fromPath')) {
            this.$router.push(localStorage.getItem('fromPath'))
            localStorage.removeItem('fromPath')
          } else {
            this.$router.push({path: '/admin'})
          }
        } else if (response && response.data && response.data.status !== 'success') {
          this.$message({
            message: response.data.message,
            type: 'error'
          })
        }
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .login-container {
    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 350px;
    padding: 35px 35px 15px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }

  .title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
  }
</style>
