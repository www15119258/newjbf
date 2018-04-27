<template>
  <div>
    <hamburger class="hamburger-container" :toggleClick="toggleSideBar" :isActive="opened"></hamburger>
    <div class="config">
      <el-dropdown trigger="click">
        <i class="el-icon-setting" size="40px"></i>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>a</el-dropdown-item>
          <el-dropdown-item>b</el-dropdown-item>
          <el-dropdown-item divided @click.native="logout">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <div class="userinfo">
      <span class="el-dropdown-link userinfo-inner"><img :src="userIcon" /> 苍之涛</span>
    </div>
  </div>
</template>

<script>
import userdefault from '@/assets/userdefault.png'
import hamburger from '@/components/hamburger'
import event from '@/common/event.js'
import { logout } from '@/views/admin/security/api/login.js'

export default {
  components: {
    hamburger
  },
  data () {
    return {
      opened: true,
      userIcon: userdefault
    }
  },
  methods: {
    toggleSideBar: function () {
      this.opened = !this.opened
      this.$root.eventBus.$emit(event.SYSTEM.SIDE_COLLAPSE, this.opened)
    },
    logout: function () {
      logout().then((response) => {
        this.$handleCommonResponse(response, () => {
          localStorage.removeItem('user')
          this.$router.push({path: '/admin/login'})
        })
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.user-icon {
  width: 40px;
  height: 40px;
  float: right;
}
.user-icon img {
  width: 40px;
  height: 40px;
  border-radius: 20px;
  margin: 10px 0px 10px 10px;
}

.config {
  width: 60px;
  height: 60px;
  font-size: 30px;
  margin-top: 3px;
  color: white;
  cursor: pointer;
  float: right;
}

.config i {
  font-size: 30px;
  color: #FFFFFF;
}

.userinfo {
  text-align: right;
  float: right;
}

.userinfo-inner {
  cursor: pointer;
  color: #fff;
}

.userinfo .userinfo-inner img {
  width: 40px;
  height: 40px;
  border-radius: 20px;
  margin: 10px 0px 10px 10px;
  float: right;
}

.hamburger-container {
  fill: #FFFFFF;
  margin-top: 4px;
  width: 60px;
  height: 60px;
  display: inline-block;
  float: left;
}

</style>
