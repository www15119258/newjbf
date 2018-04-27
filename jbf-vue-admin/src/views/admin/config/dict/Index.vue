<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="24" class="breadcrumb-container">
        <strong class="title">字典列表</strong>
        <el-breadcrumb separator="/" class="breadcrumb-inner">
          <el-breadcrumb-item>
            系统配置
          </el-breadcrumb-item>
          <el-breadcrumb-item>
            字典设置
          </el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>
    <div class="mt20"></div>
    <dict-table :showQueryBtn="true" :showAddBtn="true" :showEditBtn="true" :showDeleteBtn="true"></dict-table>
  </div>
</template>

<script>
import DictTable from './DictTable.vue'

export default {
  components: {
    DictTable
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
