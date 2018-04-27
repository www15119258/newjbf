<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
html, body, #app{
  margin: 0 auto;
  height: 100%;
  font-family: 微软雅黑, 宋体, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
.el-main .breadcrumb-container .title {
  width: 200px;
  float: left;
  color: #475669;
  font-size: 14px;
  line-height: 1;
}

.el-main .breadcrumb-container .breadcrumb-inner {
  float: right;
}
.mt20 {
  margin-top: 20px;
}
.hidden {
  display: none;
}
</style>
