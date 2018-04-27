<template>
  <div class="errPage-container">
    <el-row>
      <el-col :span="12">
        <h1 class="text-jumbo text-ginormous">Oops!</h1>
        <h2>你没有权限去该页面</h2>
        <h6>如有不满请联系你领导</h6>
        <ul class="list-unstyled">
          <li>或者你可以去:</li>
          <li class="link-type">
            <router-link to="/admin">回首页</router-link>
          </li>
        </ul>
      </el-col>
      <el-col :span="12">
        <img :src="errGif" width="313" height="428" alt="Girl has dropped her ice cream.">
      </el-col>
    </el-row>
  </div>
</template>

<script>
import errGif from '@/assets/403.gif'

export default {
  name: 'page401',
  data () {
    return {
      errGif: errGif + '?' + +new Date()
    }
  },
  methods: {
  }
}
</script>

<style scoped>
  .errPage-container {
    width: 800px;
    margin: 100px auto;
  }
  .errPage-container .pan-back-btn {
    background: #008489;
    color: #fff;
  }
  .errPage-container .pan-gif {
    margin: 0 auto;
    display: block;
  }
  .errPage-container .pan-img {
    display: block;
    margin: 0 auto;
  }
  .errPage-container .text-jumbo {
    font-size: 60px;
    font-weight: 700;
    color: #484848;
  }
  .errPage-container .list-unstyled {
    font-size: 14px;
  }
  .errPage-container .list-unstyled li {
    padding-bottom: 5px;
  }
  .errPage-container .list-unstyled a {
    color: #008489;
    text-decoration: none;
  }
  .errPage-container .list-unstyled a:hover {
    text-decoration: underline;
  }
</style>
