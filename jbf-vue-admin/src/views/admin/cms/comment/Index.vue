<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="24" class="breadcrumb-container">
        <strong class="title">评论列表</strong>
        <el-breadcrumb separator="/" class="breadcrumb-inner">
          <el-breadcrumb-item>
            文章内容管理
          </el-breadcrumb-item>
          <el-breadcrumb-item>
            评论管理
          </el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>
    <div class="mt20"></div>
    <el-row :gutter="20">
      <el-col :span="24">
        <comment-table :showQueryBtn="true" :showAddBtn="true" :showEditBtn="true" :showDeleteBtn="true"></comment-table>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import CommentTable from './CommentTable.vue'

export default {
  components: {
    CommentTable
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
