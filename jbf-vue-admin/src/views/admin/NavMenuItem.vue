<template>
  <el-menu-item v-if="isLeaf" :index="index" >
    <i v-if="item.icon !== ''" :class="(item.icon)?'fa fa-'+ item.icon:''"></i><span slot="title">{{item.name}}</span>
  </el-menu-item>
  <el-submenu v-else :index="index">
    <template slot="title">
      <i :class="(item.icon)?'fa fa-'+ item.icon:''"></i><span slot="title">{{item.name}}</span>
    </template>
    <nav-menu-item v-for="(child, index) in item.children" :item="child" :key="index"  v-if="!isLeaf" >
    </nav-menu-item>
  </el-submenu>
</template>

<script>
export default {
  name: 'NavMenuItem',
  props: ['item'],
  data () {
    return {
    }
  },
  computed: {
    isLeaf: function () {
      return this.item.children === undefined || this.item.children === null || this.item.children.length === 0
    },
    index: function () {
      if (this.item.url) {
        return '/' + this.item.url
      }
      return String(this.item.id)
    }
  },
  methods: {
  },
  mounted: function () {
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .fa {
    margin-right: 5px;
  }
</style>
