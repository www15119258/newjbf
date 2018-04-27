<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="24" class="breadcrumb-container">
        <strong class="title">标签列表</strong>
        <el-breadcrumb separator="/" class="breadcrumb-inner">
          <el-breadcrumb-item>
            文章内容管理
          </el-breadcrumb-item>
          <el-breadcrumb-item>
            标签管理
          </el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>
    <div class="mt20"></div>
    <el-row :gutter="20">
      <el-col :span="24">
        <tagcloud-table :showQueryBtn="true" :showAddBtn="true" :showEditBtn="true" :showDeleteBtn="true"></tagcloud-table>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import TagcloudTable from './TagcloudTable.vue'

export default {
  components: {
    TagcloudTable
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
