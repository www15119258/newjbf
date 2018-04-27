<template>
  <div v-if="showQueryBtn && $hasPerm('jbf.security.role.view')">
    <dict-select v-model="type" initType="sys_role_type" style="width: 100%; margin-bottom: 5px" :clearable="false" @change="roleTypeChange"></dict-select>
    <div class="edit-div" style="margin-bottom: 5px">
      <el-button-group>
        <el-button type="primary" icon="el-icon-refresh" title="刷新" @click="handleQuery"></el-button>
        <el-button type="primary" v-if="showAddBtn && $hasPerm('jbf.security.role.add')" icon="el-icon-plus" title="新增" @click="handleAdd"></el-button>
        <el-button type="primary" v-if="showEditBtn && $hasPerm('jbf.security.role.edit')" icon="el-icon-edit" title="编辑" @click="handleEdit"></el-button>
        <el-button type="primary" v-if="showEditBtn && $hasPerm('jbf.security.role.addUser')" icon="fa fa-user" title="分配用户" @click="handleAssignUser"></el-button>
        <el-button type="primary" v-if="showEditBtn && $hasPerm('jbf.security.role.assignMenus')" icon="fa fa-navicon" title="分配目录" @click="handleAssignMenu"></el-button>
        <el-button type="primary" v-if="showEditBtn && $hasPerm('jbf.security.role.assignModules')" icon="fa fa-desktop" title="分配模块" @click="handleAssignModule"></el-button>
        <el-button type="primary" v-if="showDeleteBtn && $hasPerm('jbf.security.role.delete')" icon="el-icon-close" title="删除" @click="handleDelete"></el-button>
      </el-button-group>
    </div>
    <el-tree ref="tree" nodeKey="id" :data="tree" :props="props" :defaultExpandAll="expandAll"
    :expandOnClickNode="false" :defaultExpandedKeys="expandedArray" highlightCurrent @current-change="selectedChange">
    </el-tree>
    <el-dialog v-el-drag-dialog :close-on-click-modal="false" :title="dialogTitle" :visible.sync="dialogVisible" @close="closeDialog">
      <el-form :model="form" ref="form" :rules="rules">
        <el-form-item label="角色名：" labelWidth="100px" prop="rolename">
          <el-input v-model="form.rolename"></el-input>
        </el-form-item>
        <el-form-item label="角色昵称：" labelWidth="100px" prop="nickname">
          <el-input v-model="form.nickname"></el-input>
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
          <ui-role-select-tree v-model="form.parent" :roleType="form.type" :reserved="[form.id]"></ui-role-select-tree>
        </el-form-item>
        <el-form-item label="排序：" labelWidth="100px" prop="sort">
          <el-input type="number" v-model.number="form.sort"></el-input>
        </el-form-item>
        <!--<el-input v-model="form.parent" type="hidden" class="hidden"></el-input>-->
        <el-input v-model="form.type" type="hidden" class="hidden"></el-input>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitDialog">确定</el-button>
      </div>
    </el-dialog>
    <el-dialog v-el-drag-dialog :close-on-click-modal="false" title="分配目录" :visible.sync="menuDialogVisible" @close="closeMenuDialog">
      <ui-menu-checked-tree ref="menuTree" :changeTypeEnable="true"></ui-menu-checked-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click="menuDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitMenuDialog">确定</el-button>
      </div>
    </el-dialog>
    <el-dialog v-el-drag-dialog :close-on-click-modal="false" title="分配模块" :visible.sync="moduleDialogVisible" @close="closeModuleDialog">
      <ui-module-checked-tree ref="moduleTree" :changeTypeEnable="true"></ui-module-checked-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click="moduleDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitModuleDialog">确定</el-button>
      </div>
    </el-dialog>
    <el-dialog v-el-drag-dialog :close-on-click-modal="false" title="分配用户" :visible.sync="userDialogVisible" @close="closeUserDialog">
      <ui-role-user-un-select-table ref="userUnSelectTable"></ui-role-user-un-select-table>
      <div slot="footer" class="dialog-footer">
        <!--<el-button @click="userDialogVisible = false">取消</el-button>-->
        <el-button type="primary" @click="submitUserDialog">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { queryRoleTree, addRole, updateRole, deleteRole, assignMenus, assignModules } from '../api/role'
import DictSelect from '@/views/admin/config/dict/DictSelect.vue'
import UiRoleSelectTree from './UiRoleSelectTree.vue'
import UiMenuCheckedTree from '../menu/UiMenuCheckedTree.vue'
import UiModuleCheckedTree from '../module/UiModuleCheckedTree.vue'
import UiRoleUserUnSelectTable from './UiRoleUserUnSelectTable.vue'

export default {
  components: {
    DictSelect,
    UiRoleSelectTree,
    UiMenuCheckedTree,
    UiModuleCheckedTree,
    UiRoleUserUnSelectTable
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
    initType: {
      type: [String],
      default: '0'
    },
    initForm: {
      type: [Object],
      default: function () {
        return {
          id: null,
          rolename: '',
          nickname: '',
          description: '',
          status: '1',
          parent: null,
          sort: 100,
          type: this.type
        }
      }
    }
  },
  data () {
    return {
      tree: [],
      props: {
        label: 'nickname',
        children: 'children'
      },
      expandAll: this.initExpandAll,
      expandedArray: this.initExpandedArray,
      currentData: null,
      currentNode: null,
      type: this.initType,
      dialogTitle: '',
      dialogVisible: false,
      op: 0,
      form: this.$copy(this.initForm),
      rules: {
        rolename: [
          { required: true, message: '角色名不能为空', trigger: 'blur' },
          { min: 2, max: 20, message: '角色名的长度必须在2到20之间', trigger: 'blur' }
        ],
        nickname: [
          { required: true, message: '角色昵称不能为空', trigger: 'blur' },
          { min: 2, max: 20, message: '角色昵称的长度必须在2到20之间', trigger: 'blur' }
        ],
        description: [
          { min: 0, max: 200, message: '描述的长度必须在0到200之间', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '类型不能为空', trigger: 'blur' }
        ],
        status: [
          { required: true, message: '状态不能为空', trigger: 'blur' }
        ],
        sort: [
          { required: true, message: '排序不能为空', trigger: 'blur' },
          { type: 'number', message: '排序必须为数字', trigger: 'blur' }
        ]
      },
      menuDialogVisible: false,
      moduleDialogVisible: false,
      userDialogVisible: false
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
      if (this.type === undefined || this.type === null || this.type === '') {
        this.tree = []
        return
      }
      queryRoleTree(this.type).then((response) => {
        if (response && response.data) {
          this.tree = response.data
        }
      })
    },
    roleTypeChange: function (val) {
      this.type = val
      this.handleQuery()
      this.$emit('type-change')
    },
    handleAdd: function () {
      this.op = 0
      this.dialogTitle = '新建角色'
      this.form.type = this.type
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
      this.form.rolename = this.currentData.rolename
      this.form.nickname = this.currentData.nickname
      this.form.description = this.currentData.description
      this.form.status = this.currentData.status
      this.form.parent = this.currentData.parent
      this.form.sort = this.currentData.sort
      this.form.type = this.currentData.type
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
        deleteRole(this.currentData.id).then((response) => {
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
            addRole(this.form).then((response) => {
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
            updateRole(this.form).then((response) => {
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
    handleAssignMenu: function () {
      if (this.currentData === undefined || this.currentData === null) {
        this.$message({
          message: '请选择要编辑的记录！',
          type: 'warning'
        })
        return
      }
      this.menuDialogVisible = true
      this.$nextTick(() => {
        this.$refs['menuTree'].setRoleid(this.currentData.id)
      })
    },
    closeMenuDialog: function () {
      this.$refs['menuTree'].clearSelected()
    },
    submitMenuDialog: function () {
      let menus = this.$refs['menuTree'].getSelected()
      let type = this.$refs['menuTree'].getType()
      assignMenus(this.currentData.id, type, menus.join(',')).then((response) => {
        this.$handleCommonResponse(response, () => {
          this.menuDialogVisible = false
        })
      })
    },
    handleAssignModule: function () {
      if (this.currentData === undefined || this.currentData === null) {
        this.$message({
          message: '请选择要编辑的记录！',
          type: 'warning'
        })
        return
      }
      this.moduleDialogVisible = true
      this.$nextTick(() => {
        this.$refs['moduleTree'].setRoleid(this.currentData.id)
      })
    },
    closeModuleDialog: function () {
      this.$refs['moduleTree'].clearSelected()
    },
    submitModuleDialog: function () {
      let modules = this.$refs['moduleTree'].getSelected()
      assignModules(this.currentData.id, modules.join(',')).then((response) => {
        this.$handleCommonResponse(response, () => {
          this.moduleDialogVisible = false
        })
      })
    },
    handleAssignUser: function () {
      if (this.currentData === undefined || this.currentData === null) {
        this.$message({
          message: '请选择要编辑的记录！',
          type: 'warning'
        })
        return
      }
      this.userDialogVisible = true
      this.$nextTick(() => {
        this.$refs['userUnSelectTable'].setRoleid(this.currentData.id)
      })
    },
    closeUserDialog: function () {
      this.$emit('role-select-user-update')
    },
    submitUserDialog: function () {
      this.userDialogVisible = false
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
