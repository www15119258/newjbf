<template>
  <div v-if="showQueryBtn && $hasPerm('jbf.security.module.view')">
    <div class="edit-div" style="margin-bottom: 5px">
      <el-button-group>
        <el-button type="primary" icon="el-icon-refresh" title="刷新" @click="handleQuery"></el-button>
        <el-button type="primary" v-if="showAddBtn && $hasPerm('jbf.security.module.add')" icon="el-icon-plus" title="新增" @click="handleAdd"></el-button>
        <el-button type="primary" v-if="showEditBtn && $hasPerm('jbf.security.module.edit')" icon="el-icon-edit" title="编辑" @click="handleEdit"></el-button>
        <el-button type="primary" v-if="showEditBtn && $hasPerm('jbf.security.module.addPerm')" icon="fa fa-lock" title="分配权限" @click="handleAssignPerm"></el-button>
        <el-button type="primary" v-if="showEditBtn && $hasPerm('jbf.security.module.assignRoles')" icon="fa fa-group" title="分配角色" @click="handleAssign"></el-button>
        <el-button type="primary" v-if="showDeleteBtn && $hasPerm('jbf.security.module.delete')" icon="el-icon-close" title="删除" @click="handleDelete"></el-button>
      </el-button-group>
    </div>
    <el-tree ref="tree" nodeKey="id" :data="tree" :props="props" :defaultExpandAll="expandAll"
    :expandOnClickNode="false" :defaultExpandedKeys="expandedArray" highlightCurrent @current-change="selectedChange">
    </el-tree>
    <el-dialog v-el-drag-dialog :close-on-click-modal="false" :title="dialogTitle" :visible.sync="dialogVisible" @close="closeDialog">
      <el-form :model="form" ref="form" :rules="rules">
        <el-form-item label="名称：" labelWidth="100px" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="描述：" labelWidth="100px" prop="description">
          <el-input type="textarea" v-model="form.description"></el-input>
        </el-form-item>
        <el-form-item label="状态：" labelWidth="100px" prop="status">
          <el-select v-model="form.status" placeholder="请选择" style="width:100%;">
            <el-option label="启用" value="1"></el-option>
            <el-option label="禁用" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="父：" labelWidth="100px" prop="parent">
          <ui-module-select-tree v-model="form.parent" :reserved="[form.id]"></ui-module-select-tree>
        </el-form-item>
        <el-form-item label="排序：" labelWidth="100px" prop="sort">
          <el-input type="number" v-model.number="form.sort"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitDialog">确定</el-button>
      </div>
    </el-dialog>
    <el-dialog v-el-drag-dialog :close-on-click-modal="false" title="分配角色" :visible.sync="roleDialogVisible" @close="closeRoleDialog">
      <ui-module-role-checked-tree ref="roleTree" :changeTypeEnable="true"></ui-module-role-checked-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click="roleDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitRoleDialog">确定</el-button>
      </div>
    </el-dialog>
    <el-dialog v-el-drag-dialog :close-on-click-modal="false" title="分配权限" :visible.sync="permDialogVisible" @close="closePermDialog">
      <ui-module-perm-un-select-table ref="permUnSelectTable"></ui-module-perm-un-select-table>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitPermDialog">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { queryModuleTree, addModule, updateModule, deleteModule, assignRoles } from '../api/module'
import UiModuleSelectTree from './UiModuleSelectTree.vue'
import UiModuleRoleCheckedTree from '../role/UiModuleRoleCheckedTree.vue'
import UiModulePermUnSelectTable from './UiModulePermUnSelectTable.vue'

export default {
  components: {
    UiModuleSelectTree,
    UiModuleRoleCheckedTree,
    UiModulePermUnSelectTable
  },
  props: {
    showQueryBtn: {
      type: [Boolean],
      default: false
    },
    showAddBtn: {
      type: [Boolean],
      default: false
    },
    showEditBtn: {
      type: [Boolean],
      default: false
    },
    showDeleteBtn: {
      type: [Boolean],
      default: false
    },
    initExpandAll: {
      type: [Boolean],
      default: true
    },
    initExpandedArray: {
      type: [Array],
      default: function () {
        return []
      }
    },
    initForm: {
      type: [Object],
      default: function () {
        return {
          id: null,
          name: '',
          description: '',
          status: '1',
          parent: null,
          sort: 100
        }
      }
    }
  },
  data () {
    return {
      tree: [],
      props: {
        label: 'name',
        children: 'children'
      },
      expandAll: this.initExpandAll,
      expandedArray: this.initExpandedArray,
      currentData: null,
      currentNode: null,
      dialogTitle: '',
      dialogVisible: false,
      op: 0,
      form: this.$copy(this.initForm),
      rules: {
        name: [
          { required: true, message: '名称不能为空', trigger: 'blur' },
          { min: 1, max: 20, message: '名称的长度必须在1到20之间', trigger: 'blur' }
        ],
        description: [
          { min: 0, max: 200, message: '描述的长度必须在0到200之间', trigger: 'blur' }
        ],
        status: [
          { required: true, message: '状态不能为空', trigger: 'blur' }
        ],
        sort: [
          { required: true, message: '排序不能为空', trigger: 'blur' },
          { type: 'number', message: '排序必须为数字', trigger: 'blur' }
        ]
      },
      roleDialogVisible: false,
      permDialogVisible: false
    }
  },
  methods: {
    selectedChange: function (data, node) {
      this.currentData = data
      this.currentNode = node
      this.$emit('current-change', data, node)
    },
    handleQuery: function () {
      this.currentData = null
      this.currentNode = null
      queryModuleTree().then((response) => {
        if (response && response.data) {
          this.tree = response.data
        }
      })
    },
    handleAdd: function () {
      this.op = 0
      this.dialogTitle = '新建模块'
      this.form.parent = this.getParent()
      this.dialogVisible = true
    },
    getParent: function () {
      if (this.currentData !== undefined && this.currentData !== null) {
        return this.currentData.id
      } else {
        return null
      }
    },
    handleEdit: function () {
      if (this.currentData === undefined || this.currentData === null) {
        this.$message({
          message: '请选择要编辑的记录！',
          type: 'warning'
        })
        return
      }
      this.op = 1
      this.dialogTitle = '编辑角色'
      this.form.id = this.currentData.id
      this.form.name = this.currentData.name
      this.form.description = this.currentData.description
      this.form.status = this.currentData.status
      this.form.parent = this.currentData.parent
      this.form.sort = this.currentData.sort
      this.dialogVisible = true
    },
    handleDelete: function () {
      if (this.currentData === undefined || this.currentData === null) {
        this.$message({
          message: '请选择要删除的记录！',
          type: 'warning'
        })
        return
      }
      this.$confirm('您确定要删除该记录吗？如果该记录包含子，该操作将会连同子节点一起删除！', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteModule(this.currentData.id).then((response) => {
          this.$handleCommonResponse(response, () => {
            const parent = this.currentNode.parent
            const children = parent.data.children || parent.data
            const index = children.findIndex(d => d.id === this.currentData.id)
            children.splice(index, 1)
          })
        })
      }).catch(() => {})
    },
    closeDialog: function () {
      this.resetForm()
    },
    resetForm: function () {
      if (this.$refs['form']) {
        this.$refs['form'].resetFields()
      }
      this.form = this.$copy(this.initForm)
    },
    submitDialog: function () {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.op === 0) {
            addModule(this.form).then((response) => {
              this.$handleCommonResponse(response, () => {
                this.dialogVisible = false
                let parentid = response.data.data.parent
                if (parentid === undefined || parentid === null) {
                  this.tree.push(response.data.data)
                  return
                }
                let parent = this.$refs['tree'].getNode(parentid)
                if (parent === undefined || parent === null) {
                  return
                }
                if (parent.data.children === undefined || parent.data.children === null) {
                  this.$set(parent.data, 'children', [])
                }
                parent.data.children.push(response.data.data)
              })
            })
          } else {
            updateModule(this.form).then((response) => {
              this.$handleCommonResponse(response, () => {
                this.dialogVisible = false
                if (this.currentData.parent !== response.data.data.parent) {
                  this.handleQuery()
                  return
                }
                const parent = this.currentNode.parent
                const children = parent.data.children || parent.data
                const index = children.findIndex(d => d.id === this.currentData.id)
                const oldChildren = this.currentData.children
                this.currentData = response.data.data
                this.currentData.children = oldChildren
                this.currentNode.data = this.currentData
                children.splice(index, 1, this.currentData)
                this.$nextTick(() => {
                  this.$refs['tree'].setCurrentKey(this.currentData.id)
                  this.currentNode = this.$refs['tree'].getNode(this.currentData.id)
                })
              })
            })
          }
        }
      })
    },
    handleAssign: function () {
      if (this.currentData === undefined || this.currentData === null) {
        this.$message({
          message: '请选择要编辑的记录！',
          type: 'warning'
        })
        return
      }
      this.roleDialogVisible = true
      this.$nextTick(() => {
        this.$refs['roleTree'].setModuleid(this.currentData.id)
      })
    },
    closeRoleDialog: function () {
      this.$refs['roleTree'].clearSelected()
    },
    submitRoleDialog: function () {
      let roles = this.$refs['roleTree'].getSelected()
      let type = this.$refs['roleTree'].getType()
      assignRoles(this.currentData.id, type, roles.join(',')).then((response) => {
        this.$handleCommonResponse(response, () => {
          this.roleDialogVisible = false
        })
      })
    },
    handleAssignPerm: function () {
      if (this.currentData === undefined || this.currentData === null) {
        this.$message({
          message: '请选择要编辑的记录！',
          type: 'warning'
        })
        return
      }
      this.permDialogVisible = true
      this.$nextTick(() => {
        this.$refs['permUnSelectTable'].setModuleid(this.currentData.id)
      })
    },
    closePermDialog: function () {
      this.$emit('module-select-perm-update')
    },
    submitPermDialog: function () {
      this.permDialogVisible = false
    }
  },
  mounted: function () {
    this.handleQuery()
  },
  destroyed: function () {
  }
}
</script>

<style scoped>
</style>
