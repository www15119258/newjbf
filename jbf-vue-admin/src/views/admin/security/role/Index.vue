<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="24" class="breadcrumb-container">
        <strong class="title">角色列表</strong>
        <el-breadcrumb separator="/" class="breadcrumb-inner">
          <el-breadcrumb-item>
            用户权限管理
          </el-breadcrumb-item>
          <el-breadcrumb-item>
            角色管理
          </el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>
    <div class="mt20"></div>
    <el-row :gutter="20">
      <el-col :span="10">
        <role-tree @current-change="roleChange" @type-change="typeChange" @role-select-user-update="roleSelectUserUpdate"
                   :showQueryBtn="true" :showAddBtn="true" :showEditBtn="true" :showDeleteBtn="true"></role-tree>
      </el-col>
      <el-col :span="14">
        <ui-role-user-select-table ref="roleUserTable" :showRemove="$hasPerm('jbf.security.role.removeUser')"></ui-role-user-select-table>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import RoleTree from './RoleTree.vue'
import UiRoleUserSelectTable from './UiRoleUserSelectTable.vue'

export default {
  components: {
    RoleTree,
    UiRoleUserSelectTable
  },
  data () {
    return {
    }
  },
  methods: {
    roleChange: function (data, node) {
      if (data !== undefined && data !== null) {
        this.$refs['roleUserTable'].setRoleid(data.id)
      }
    },
    typeChange: function () {
      this.$refs['roleUserTable'].setRoleid(null)
    },
    roleSelectUserUpdate: function () {
      this.$refs['roleUserTable'].handleQuery()
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
