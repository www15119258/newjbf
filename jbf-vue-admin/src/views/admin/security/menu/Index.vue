<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="24" class="breadcrumb-container">
        <strong class="title">目录列表</strong>
        <el-breadcrumb separator="/" class="breadcrumb-inner">
          <el-breadcrumb-item>
            用户权限管理
          </el-breadcrumb-item>
          <el-breadcrumb-item>
            目录管理
          </el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>
    <div class="mt20"></div>
    <el-row :gutter="20">
      <el-col :span="10">
        <menu-tree :initExpandAll="false" :showQueryBtn="true" :showAddBtn="true" :showEditBtn="true" :showDeleteBtn="true"></menu-tree>
      </el-col>
      <el-col :span="14">
      </el-col>
    </el-row>
  </div>
</template>

<script>
import MenuTree from './MenuTree.vue'

export default {
  components: {
    MenuTree
  },
  data () {
    return {
    }
  },
  mounted: function () {
  },
  destroyed: function () {
  }
}
</script>

<style scoped>
</style>
