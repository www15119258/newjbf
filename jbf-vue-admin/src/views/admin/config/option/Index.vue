<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="24" class="breadcrumb-container">
        <strong class="title">参数列表</strong>
        <el-breadcrumb separator="/" class="breadcrumb-inner">
          <el-breadcrumb-item>
            系统配置
          </el-breadcrumb-item>
          <el-breadcrumb-item>
            参数设置
          </el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>
    <div class="mt20"></div>
    <option-table :showQueryBtn="true" :showAddBtn="true" :showEditBtn="true" :showDeleteBtn="true"></option-table>
  </div>
</template>

<script>
import OptionTable from './OptionTable.vue'

export default {
  components: {
    OptionTable
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
