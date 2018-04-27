<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="24" class="toolbar">
        <el-form :inline="true" :model="queryForm">
          <el-form-item v-if="showQueryBtn && $hasPerm('jbf.security.perm.view')"  label="名称：">
            <el-input v-model="queryForm.name" placeholder="名称" style="width:140px;"></el-input>
          </el-form-item>
          <el-form-item v-if="showQueryBtn && $hasPerm('jbf.security.perm.view')"  label="权限：">
            <el-input v-model="queryForm.perm" placeholder="权限" style="width:140px;"></el-input>
          </el-form-item>
          <el-form-item v-if="showQueryBtn && $hasPerm('jbf.security.perm.view')"  label="状态：">
            <el-select v-model="queryForm.status" clearable placeholder="请选择" style="width:160px;">
              <el-option label="启用" value="1"></el-option>
              <el-option label="禁用" value="0"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button-group>
              <el-button v-if="showQueryBtn && $hasPerm('jbf.security.perm.view')" type="primary" @click="handleQuery">查询</el-button>
              <el-button v-if="showAddBtn && $hasPerm('jbf.security.perm.add')" type="primary" @click="handleAdd">新增</el-button>
              <el-button v-if="showEditBtn && $hasPerm('jbf.security.perm.edit')" type="primary" @click="handleEdit">编辑</el-button>
              <el-button v-if="showDeleteBtn && $hasPerm('jbf.security.perm.delete')" type="primary" @click="handleDelete">删除</el-button>
            </el-button-group>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="24">
        <el-table v-if="showQueryBtn && $hasPerm('jbf.security.perm.view')" ref="dataTable" :data="dataTable" :highlight-current-row="true" @current-change="selectChange" v-loading="loading"
                  @select="handleSelect" @select-all="handleSelectAll" @sort-change="handleSortChange" border style="width: 100%; height: 100%">
          <el-table-column v-if="!singleSelect"
                           type="selection"
                           width="55">
          </el-table-column>
          <el-table-column prop="name" sortable="custom" label="名称" header-align="center" align="left" width="140" fixed="left">
          </el-table-column>
          <el-table-column prop="perm" sortable="custom" label="权限" header-align="center" align="left" width="200">
          </el-table-column>
          <el-table-column prop="description" label="描述" header-align="center" align="left">
          </el-table-column>
          <el-table-column prop="status" sortable="custom" label="状态" header-align="center" align="center" width="80" :formatter="statusFormatter">
          </el-table-column>
        </el-table>
        <el-pagination layout="total, sizes, prev, pager, next, jumper" :total="queryForm._query_total" :page-size="queryForm._query_size" :page-sizes="[20, 40, 60, 80, 100]"  style="float:right;"
                       @size-change="handleSizeChange" @current-change="handleCurrentPageChange">
        </el-pagination>
      </el-col>
    </el-row>
    <el-dialog v-el-drag-dialog :close-on-click-modal="false" :title="dialogTitle" :visible.sync="dialogVisible" @close="closeDialog">
      <el-form :model="form" ref="form" :rules="rules">
        <el-form-item label="名称：" labelWidth="100px" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="权限：" labelWidth="100px" prop="perm">
          <el-input v-model="form.perm"></el-input>
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
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitDialog">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { queryPerm, addPerm, updatePerm, deletePerm } from '../api/perm.js'

export default {
  components: {
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
          name: '',
          perm: '',
          description: '',
          status: '1'
        }
      }
    }
  },
  data () {
    return {
      loading: false,
      queryForm: {
        name: '',
        perm: '',
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
        name: [
          { required: true, message: '名称不能为空', trigger: 'blur' },
          { min: 1, max: 20, message: '名称的长度必须在1到20之间', trigger: 'blur' }
        ],
        perm: [
          { required: true, message: '权限不能为空', trigger: 'blur' },
          { min: 1, max: 200, message: '权限的长度必须在1到200之间', trigger: 'blur' }
        ],
        description: [
          { min: 0, max: 200, message: '描述的长度必须在0到200之间', trigger: 'blur' }
        ],
        status: [
          { required: true, message: '状态不能为空', trigger: 'blur' }
        ]
      },
      op: 0,
      currentRow: null
    }
  },
  methods: {
    handleQuery: function () {
      this.currentRow = null
      queryPerm(this.queryForm).then((response) => {
        if (response && response.data && response.data.status === 'success') {
          this.dataTable = response.data.data.content
          this.queryForm._query_total = response.data.data.totalElements
        }
      })
    },
    handleAdd: function () {
      this.op = 0
      this.dialogTitle = '新增权限'
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
      this.form.name = this.currentRow.name
      this.form.perm = this.currentRow.perm
      this.form.description = this.currentRow.description
      this.form.status = this.currentRow.status
      this.op = 1
      this.dialogTitle = '编辑权限'
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
        deletePerm(this.currentRow.id).then((response) => {
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
        this.queryForm._query_order = this.initQueryOrder
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
            addPerm(this.form).then((response) => {
              this.$handleCommonResponse(response, () => {
                this.dialogVisible = false
                this.handleQuery()
              })
            })
          } else {
            updatePerm(this.form).then((response) => {
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
    statusFormatter: function (row) {
      if (row.status === undefined || row.status === null || row.status === '') {
        return ''
      } else if (row.status === '0') {
        return '禁用'
      } else if (row.status === '1') {
        return '启用'
      } else {
        return ''
      }
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
