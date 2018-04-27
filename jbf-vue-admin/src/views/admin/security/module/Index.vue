<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="24" class="breadcrumb-container">
        <strong class="title">模块列表</strong>
        <el-breadcrumb separator="/" class="breadcrumb-inner">
          <el-breadcrumb-item>
            用户权限管理
          </el-breadcrumb-item>
          <el-breadcrumb-item>
            模块管理
          </el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>
    <div class="mt20"></div>
    <el-row :gutter="20">
      <el-col :span="10">
        <module-tree :initExpandAll="false" @current-change="moduleChange" @module-select-perm-update="moduleSelectPermUpdate"
                     :showQueryBtn="true" :showAddBtn="true" :showEditBtn="true" :showDeleteBtn="true"></module-tree>
      </el-col>
      <el-col :span="14">
        <ui-module-perm-select-table ref="modulePermTable"  :showRemove="$hasPerm('jbf.security.module.removePerm')"></ui-module-perm-select-table>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import ModuleTree from './ModuleTree.vue'
import UiModulePermSelectTable from './UiModulePermSelectTable.vue'

export default {
  components: {
    ModuleTree,
    UiModulePermSelectTable
  },
  data () {
    return {
    }
  },
  methods: {
    moduleChange: function (data, node) {
      if (data !== undefined && data !== null) {
        this.$refs['modulePermTable'].setModuleid(data.id)
      }
    },
    moduleSelectPermUpdate: function () {
      this.$refs['modulePermTable'].handleQuery()
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
