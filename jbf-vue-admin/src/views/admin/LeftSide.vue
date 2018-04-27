<template>
  <div class="left-side">
    <div class="logo" v-bind:class="{collapse: isCollapse}">
      <span class="title" v-if="!isCollapse">{{$config.system.title}}</span>
    </div>
    <el-menu :default-active="defaultActive" class="el-menu-vertical" @open="handleOpen" @close="handleClose" :collapse="isCollapse"
             :default-openeds="defaultOpeneds" :router="router" >
      <template v-for="(item, index) in menuTree">
        <nav-menu-item :item="item" :key="index"></nav-menu-item>
      </template>
    </el-menu>
  </div>
</template>

<script>
import NavMenuItem from './NavMenuItem.vue'
import event from '@/common/event.js'
import { queryMenuTree } from './security/api/menu.js'

export default {
  components: {
    NavMenuItem
  },
  data () {
    return {
      isCollapse: false,
      opens: new Set([]),
      router: true,
      menuTree: []
    }
  },
  methods: {
    handleOpen: function (key, keyPath) {
      this.opens.add(key)
    },
    handleClose: function (key, keyPath) {
      this.opens.delete(key)
    },
    getNodes: function (tree, nodes) {
      for (let node of tree) {
        if (node.children !== undefined && node.children.length > 0) {
          this.getNodes(node.children, nodes)
        } else {
          nodes.push(node)
        }
      }
    }
  },
  computed: {
    defaultActive: function () {
      let path = this.$route.path
      return path
    },
    defaultOpeneds: function () {
      let tree = this.menuTree
      let nodes = []
      this.getNodes(tree, nodes)
      let path = this.defaultActive
      let select
      for (let node of nodes) {
        if ('/' + node.url === path) {
          select = node.parent
          break
        }
      }
      if (select !== undefined) {
        this.opens.add(String(select))
      }
      return Array.from(this.opens)
    }
  },
  mounted: function () {
    this.$root.eventBus.$on(event.SYSTEM.SIDE_COLLAPSE, (collapse) => {
      this.isCollapse = !collapse
    })
    queryMenuTree('left').then((response) => {
      if (response && response.data) {
        this.menuTree = response.data
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
.left-side {
  height: 100%;
}
.logo {
  height: 60px;
  background-color: #20a0ff;
  color: #fff;
  border-right: 1px solid rgba(238, 241, 146, 0.3);
  position: fixed;
  width: 200px;
  z-index: 999;
}
.logo.collapse {
  width: 65px;
}
.logo .title {
  display: inline-block;
  width: 100%;
  height: 100%;
  line-height: 60px;
  text-align: center;
  font-weight: bold;
  font-size: 18px;
}
.el-menu-vertical {
  height: calc(100% - 60px);
  padding-top: 60px;
}
.el-submenu .el-menu-item {
  min-width: auto;
}
</style>
