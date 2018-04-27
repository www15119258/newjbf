<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="24" class="breadcrumb-container">
        <strong class="title">分类列表</strong>
        <el-breadcrumb separator="/" class="breadcrumb-inner">
          <el-breadcrumb-item>
            文章内容管理
          </el-breadcrumb-item>
          <el-breadcrumb-item>
            分类管理
          </el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>
    <div class="mt20"></div>
    <el-row :gutter="20">
      <el-col :span="10">
        <category-tree :showQueryBtn="true" :showAddBtn="true" :showEditBtn="true" :showDeleteBtn="true"></category-tree>
      </el-col>
      <el-col :span="14">
      </el-col>
    </el-row>
  </div>
</template>

<script>
import CategoryTree from './CategoryTree.vue'

export default {
  components: {
    CategoryTree
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
