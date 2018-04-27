<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="24" class="breadcrumb-container">
        <strong class="title">访问记录列表</strong>
        <el-breadcrumb separator="/" class="breadcrumb-inner">
          <el-breadcrumb-item>
            站点统计
          </el-breadcrumb-item>
          <el-breadcrumb-item>
            访问记录
          </el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>
    <div class="mt20"></div>
    <el-row :gutter="20">
      <el-col :span="24">
        <visit-log-table :showQueryBtn="true" :showAddBtn="true" :showEditBtn="true" :showDeleteBtn="true"></visit-log-table>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import VisitLogTable from './VisitLogTable.vue'

export default {
  components: {
    VisitLogTable
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
