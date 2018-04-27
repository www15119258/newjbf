<template>
  <el-container>
    <el-aside :width="width">
      <left-side></left-side>
    </el-aside>
    <el-container>
      <el-header>
        <top-header></top-header>
      </el-header>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import LeftSide from './LeftSide.vue'
import TopHeader from './TopHeader.vue'
import event from '@/common/event.js'

export default {
  components: {
    LeftSide,
    TopHeader
  },
  data () {
    return {
      width: '200px'
    }
  },
  mounted: function () {
    this.$root.eventBus.$on(event.SYSTEM.SIDE_COLLAPSE, (collapse) => {
      if (collapse) {
        this.width = '200px'
      } else {
        this.width = '65px'
      }
    })
  },
  destroyed: function () {
    this.$root.eventBus.$off(event.SYSTEM.SIDE_COLLAPSE)
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.el-container {
  height: 100%;
}
.el-header, .el-footer {
  background-color: #20a0ff;
  color: #333;
  text-align: center;
}
.el-header {
  height: 60px;
  line-height: 60px;
  padding: 0;
}
.el-aside {
  /*background-color: #D3DCE6;*/
  color: #333;
  height: 100%;
}

.el-main {
  background-color: #E9EEF3;
  color: #333;
  height: 100%;
}
</style>
