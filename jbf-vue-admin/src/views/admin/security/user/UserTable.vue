<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="24" class="toolbar">
        <el-form :inline="true" :model="queryForm">
          <el-form-item v-if="showQueryBtn && $hasPerm('jbf.security.user.view')"  label="用户名：">
            <el-input v-model="queryForm.username" placeholder="用户名" style="width:140px;"></el-input>
          </el-form-item>
          <el-form-item v-if="showQueryBtn && $hasPerm('jbf.security.user.view')"  label="昵称：">
            <el-input v-model="queryForm.nickname" placeholder="昵称" style="width:140px;"></el-input>
          </el-form-item>
          <el-form-item v-if="showQueryBtn && $hasPerm('jbf.security.user.view')"  label="Email：">
            <el-input v-model="queryForm.email" placeholder="Email" style="width:140px;"></el-input>
          </el-form-item>
          <el-form-item v-if="showQueryBtn && $hasPerm('jbf.security.user.view')"  label="性别：">
            <el-select v-model="queryForm.sex" clearable placeholder="请选择" style="width:160px;">
              <el-option label="男" value="0"></el-option>
              <el-option label="女" value="1"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item v-if="showQueryBtn && $hasPerm('jbf.security.user.view')"  label="状态：">
            <el-select v-model="queryForm.status" clearable placeholder="请选择" style="width:160px;">
              <el-option label="正常" value="0"></el-option>
              <el-option label="锁定" value="1"></el-option>
              <el-option label="未激活" value="2"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button-group>
              <el-button v-if="showQueryBtn && $hasPerm('jbf.security.user.view')" type="primary" @click="handleQuery">查询</el-button>
              <el-button v-if="showAddBtn && $hasPerm('jbf.security.user.add')" type="primary" @click="handleAdd">新增</el-button>
              <el-button v-if="showEditBtn && $hasPerm('jbf.security.user.edit')" type="primary" @click="handleEdit">编辑</el-button>
              <el-button v-if="showEditBtn && $hasPerm('jbf.security.user.assignRoles')" type="primary" @click="handleAssign">分配角色</el-button>
              <el-button v-if="showDeleteBtn && $hasPerm('jbf.security.user.delete')" type="primary" @click="handleDelete">删除</el-button>
            </el-button-group>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="24">
        <el-table v-if="showQueryBtn && $hasPerm('jbf.security.user.view')" ref="dataTable" :data="dataTable" :highlight-current-row="true" @current-change="selectChange" v-loading="loading"
                  @select="handleSelect" @select-all="handleSelectAll" @sort-change="handleSortChange" border style="width: 100%; height: 100%">
          <el-table-column v-if="!singleSelect"
                           type="selection"
                           width="55">
          </el-table-column>
          <el-table-column prop="username" label="用户名" header-align="center" align="left" width="120" fixed="left">
          </el-table-column>
          <el-table-column prop="nickname" label="昵称" header-align="center" align="left" width="120">
          </el-table-column>
          <el-table-column prop="email" label="Email" header-align="center" align="left" width="180" >
          </el-table-column>
          <el-table-column prop="mobile" label="手机号码" header-align="center" align="left" width="120">
          </el-table-column>
          <el-table-column prop="sex" label="性别" sortable="custom" header-align="center" align="center" width="80" :formatter="sexFormatter">
          </el-table-column>
          <el-table-column prop="age" label="年龄" sortable="custom" header-align="center" align="center" width="80" >
          </el-table-column>
          <el-table-column prop="birthday" label="生日" header-align="center" align="center" width="100">
          </el-table-column>
          <el-table-column prop="status" label="状态" sortable="custom" header-align="center" align="center" width="80" fixed="right" :formatter="userStatusFormatter">
          </el-table-column>
        </el-table>
        <el-pagination layout="total, sizes, prev, pager, next, jumper" :total="queryForm._query_total" :page-size="queryForm._query_size" :page-sizes="[20, 40, 60, 80, 100]"  style="float:right;"
                       @size-change="handleSizeChange" @current-change="handleCurrentPageChange">
        </el-pagination>
      </el-col>
    </el-row>
    <el-dialog v-el-drag-dialog :close-on-click-modal="false" :title="dialogTitle" :visible.sync="dialogVisible" @close="closeDialog">
      <el-form :model="form" ref="form" :rules="rules">
        <el-form-item label="用户名：" labelWidth="100px" prop="username">
          <el-input v-model="form.username" :readonly="op === 1"></el-input>
        </el-form-item>
        <el-form-item v-if="op === 0" label="密码：" labelWidth="100px" prop="newpassword">
          <el-input type="password" v-model="form.newpassword"></el-input>
        </el-form-item>
        <el-form-item v-if="op === 0" label="确认密码：" labelWidth="100px" prop="newpassword2">
          <el-input type="password" v-model="form.newpassword2"></el-input>
        </el-form-item>
        <el-form-item label="昵称：" labelWidth="100px" prop="nickname">
          <el-input v-model="form.nickname"></el-input>
        </el-form-item>
        <el-form-item label="头像：" labelWidth="100px" prop="icon">
          <el-input v-model="form.icon"></el-input>
        </el-form-item>
        <el-form-item label="Email：" labelWidth="100px" prop="email">
          <el-input v-model="form.email"></el-input>
        </el-form-item>
        <el-form-item label="手机号码：" labelWidth="100px" prop="mobile">
          <el-input v-model="form.mobile"></el-input>
        </el-form-item>
        <el-form-item label="性别：" labelWidth="100px" prop="sex">
          <el-select v-model="form.sex" clearable placeholder="请选择" style="width:100%;">
            <el-option label="男" value="0"></el-option>
            <el-option label="女" value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="年龄：" labelWidth="100px" prop="age">
          <el-input type="number" v-model="form.age"></el-input>
        </el-form-item>
        <el-form-item label="生日：" labelWidth="100px" prop="birthday">
          <el-date-picker v-model="form.birthday" type="date" placeholder="yyyy-MM-dd" format="yyyy-MM-dd" style="width:100%"></el-date-picker>
        </el-form-item>
        <el-form-item label="状态：" labelWidth="100px" prop="status">
          <el-select v-model="form.status" placeholder="请选择" style="width:100%;">
            <el-option label="正常" value="0"></el-option>
            <el-option label="锁定" value="1"></el-option>
            <el-option label="未激活" value="2"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitDialog">确定</el-button>
      </div>
    </el-dialog>
    <el-dialog v-el-drag-dialog :close-on-click-modal="false" title="分配角色" :visible.sync="roleDialogVisible" @close="closeRoleDialog">
      <ui-user-role-checked-tree ref="roleTree" :changeTypeEnable="true"></ui-user-role-checked-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click="roleDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitRoleDialog">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { queryUser, addUser, updateUser, deleteUser, assignRoles } from '../api/user.js'
import MD5 from 'js-md5'
import UiUserRoleCheckedTree from '../role/UiUserRoleCheckedTree.vue'

export default {
  components: {
    UiUserRoleCheckedTree
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
    singleSelect: {
      type: [Boolean],
      default: true
    },
    initQueryOrder: {
      type: [String],
      default: 'createDate desc'
    },
    initForm: {
      type: [Object],
      default: function () {
        return {
          id: null,
          username: '',
          newpassword: '',
          newpassword2: '',
          nickname: '',
          icon: '',
          email: '',
          mobile: '',
          sex: null,
          age: null,
          birthday: null,
          status: '0'
        }
      }
    }
  },
  data () {
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.form.newpassword) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      loading: false,
      queryForm: {
        username: '',
        nickname: '',
        email: '',
        sex: '',
        status: '',
        sort: '',
        _query_total: 0,
        _query_page: 1,
        _query_size: 20,
        _query_order: this.initQueryOrder
      },
      dataTable: [],
      dialogTitle: '',
      dialogVisible: false,
      form: this.$copy(this.initForm),
      rules: {
        username: [
          { required: true, message: '用户名不能为空', trigger: 'blur' },
          { min: 2, max: 20, message: '用户名的长度必须在2到20之间', trigger: 'blur' }
        ],
        newpassword: [
          { required: true, message: '密码不能为空', trigger: 'blur' },
          { min: 6, max: 20, message: '密码的长度必须在6到20之间', trigger: 'blur' }
        ],
        newpassword2: [
          { required: true, message: '密码不能为空', trigger: 'blur' },
          { validator: validatePass2, message: '两次密码不一致', trigger: 'blur' }
        ],
        nickname: [
          { required: true, message: '昵称不能为空', trigger: 'blur' },
          { min: 2, max: 20, message: '昵称的长度必须在2到20之间', trigger: 'blur' }
        ],
        email: [
          { required: true, message: 'Email不能为空', trigger: 'blur' },
          { type: 'email', message: '非法的Email地址', trigger: 'blur' }
        ]
      },
      op: 0,
      currentRow: null,
      roleDialogVisible: false
    }
  },
  methods: {
    handleQuery: function () {
      this.currentRow = null
      queryUser(this.queryForm).then((response) => {
        if (response && response.data && response.data.status === 'success') {
          this.dataTable = response.data.data.content
          this.queryForm._query_total = response.data.data.totalElements
        }
      })
    },
    handleAdd: function () {
      this.op = 0
      this.dialogTitle = '新增用户'
      this.dialogVisible = true
    },
    handleEdit: function () {
      if (this.currentRow === undefined || this.currentRow === null) {
        this.$message({
          message: '请选择要编辑的记录！',
          type: 'warning'
        })
        return
      }
      this.form.id = this.currentRow.id
      this.form.username = this.currentRow.username
      this.form.nickname = this.currentRow.nickname
      this.form.icon = this.currentRow.icon
      this.form.email = this.currentRow.email
      this.form.mobile = this.currentRow.mobile
      this.form.sex = this.currentRow.sex
      this.form.age = this.currentRow.age
      this.form.birthday = this.currentRow.birthday
      this.form.status = this.currentRow.status
      this.op = 1
      this.dialogTitle = '编辑用户'
      this.dialogVisible = true
    },
    resetForm: function () {
      if (this.$refs['form']) {
        this.$refs['form'].resetFields()
      }
      this.form = this.$copy(this.initForm)
    },
    handleDelete: function () {
      if (this.currentRow === undefined || this.currentRow === null) {
        this.$message({
          message: '请选择要删除的记录！',
          type: 'warning'
        })
        return
      }
      this.$confirm('您确定要删除该记录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteUser(this.currentRow.id).then((response) => {
          this.$handleCommonResponse(response, () => {
            this.handleQuery()
          })
        })
      }).catch(() => {})
    },
    selectChange: function (currentRow, oldCurrentRow) {
      this.currentRow = currentRow
    },
    handleSelect: function () {
    },
    handleSelectAll: function () {
    },
    handleSizeChange: function (val) {
      this.queryForm._query_size = val
      this.handleQuery()
    },
    handleCurrentPageChange: function (val) {
      this.queryForm._query_page = val
      this.handleQuery()
    },
    handleSortChange: function (column) {
      let sort = column.prop
      if (sort === undefined || sort === null || sort === '') {
        this.queryForm._query_order = 'createDate desc'
        return
      }
      let order = column.order
      if (order === 'ascending') {
        this.queryForm._query_order = sort + ' asc'
      } else {
        this.queryForm._query_order = sort + ' desc'
      }
      this.handleQuery()
    },
    closeDialog: function () {
      this.resetForm()
    },
    submitDialog: function () {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.op === 0) {
            let user = this.$copy(this.form)
            user.password = MD5(user.newpassword)
            addUser(user).then((response) => {
              this.$handleCommonResponse(response, () => {
                this.dialogVisible = false
                this.handleQuery()
              })
            })
          } else {
            updateUser(this.form).then((response) => {
              this.$handleCommonResponse(response, () => {
                this.dialogVisible = false
                this.handleQuery()
              })
            })
          }
        } else {
          return false
        }
      })
    },
    sexFormatter: function (row) {
      if (row.sex === undefined || row.sex === null || row.sex === '') {
        return ''
      } else if (row.sex === '0') {
        return '男'
      } else if (row.sex === '1') {
        return '女'
      } else {
        return ''
      }
    },
    userStatusFormatter: function (row) {
      if (row.status === undefined || row.status === null || row.status === '') {
        return ''
      } else if (row.status === '0') {
        return '正常'
      } else if (row.status === '1') {
        return '锁定'
      } else if (row.status === '2') {
        return '未激活'
      } else {
        return ''
      }
    },
    handleAssign: function () {
      if (this.currentRow === undefined || this.currentRow === null) {
        this.$message({
          message: '请选择要编辑的记录！',
          type: 'warning'
        })
        return
      }
      this.roleDialogVisible = true
      this.$nextTick(() => {
        this.$refs['roleTree'].setUsername(this.currentRow.username)
      })
    },
    closeRoleDialog: function () {
      this.$refs['roleTree'].clearSelected()
    },
    submitRoleDialog: function () {
      let roles = this.$refs['roleTree'].getSelected()
      let type = this.$refs['roleTree'].getType()
      assignRoles(this.currentRow.id, type, roles.join(',')).then((response) => {
        this.$handleCommonResponse(response, () => {
          this.roleDialogVisible = false
        })
      })
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
