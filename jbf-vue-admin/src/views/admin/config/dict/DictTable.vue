<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="24" class="toolbar">
        <el-form :inline="true" :model="queryForm">
          <el-form-item v-if="showQueryBtn && $hasPerm('jbf.config.dict.view')"  label="类型：">
            <dict-type-select v-model="queryForm.type" placeholder="类型" style="width:140px;"></dict-type-select>
          </el-form-item>
          <el-form-item v-if="showQueryBtn && $hasPerm('jbf.config.dict.view')"  label="值：">
            <el-input v-model="queryForm.value" placeholder="值" style="width:140px;"></el-input>
          </el-form-item>
          <el-form-item v-if="showQueryBtn && $hasPerm('jbf.config.dict.view')"  label="标签：">
            <el-input v-model="queryForm.label" placeholder="标签" style="width:140px;"></el-input>
          </el-form-item>
          <el-form-item v-if="showQueryBtn && $hasPerm('jbf.config.dict.view')"  label="是否可见：">
            <el-select v-model="queryForm.visible" placeholder="请选择" style="width:160px;">
              <el-option label="所有" value=""></el-option>
              <el-option label="是" value="1"></el-option>
              <el-option label="否" value="0"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button-group>
              <el-button v-if="showQueryBtn && $hasPerm('jbf.config.dict.view')" type="primary" @click="handleQuery">查询</el-button>
              <el-button v-if="showAddBtn && $hasPerm('jbf.config.dict.add')" type="primary" @click="handleAdd">新增</el-button>
              <el-button v-if="showEditBtn && $hasPerm('jbf.config.dict.edit')" type="primary" @click="handleEdit">编辑</el-button>
              <el-button v-if="showDeleteBtn && $hasPerm('jbf.config.dict.delete')" type="primary" @click="handleDelete">删除</el-button>
            </el-button-group>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="24">
        <el-table v-if="showQueryBtn && $hasPerm('jbf.config.dict.view')" tref="dataTable" :data="dataTable" :highlight-current-row="true" @current-change="selectChange" v-loading="loading"
                  @select="handleSelect" @select-all="handleSelectAll" @sort-change="handleSortChange" border style="width: 100%; height: 100%">
          <el-table-column v-if="!singleSelect"
                           type="selection"
                           width="55">
          </el-table-column>
          <el-table-column prop="type" label="类型" sortable="custom" header-align="center" align="left" width="180" fixed="left">
          </el-table-column>
          <el-table-column prop="value" label="值" sortable="custom" header-align="center" align="left" width="180">
          </el-table-column>
          <el-table-column prop="label" label="标签" sortable="custom" header-align="center" align="left" width="180" >
          </el-table-column>
          <el-table-column prop="description" label="描述" header-align="center" align="left">
          </el-table-column>
          <el-table-column prop="visible" label="是否可见" sortable="custom" header-align="center" align="center" width="100">
          </el-table-column>
          <el-table-column prop="sort" label="排序" sortable="custom" header-align="center" align="center" width="80" >
          </el-table-column>
          <el-table-column prop="parentName" label="父" header-align="center" align="left" width="180">
          </el-table-column>
        </el-table>
        <el-pagination layout="total, sizes, prev, pager, next, jumper" :total="queryForm._query_total" :page-size="queryForm._query_size" :page-sizes="[20, 40, 60, 80, 100]"  style="float:right;"
                       @size-change="handleSizeChange" @current-change="handleCurrentPageChange">
        </el-pagination>
      </el-col>
    </el-row>
    <el-dialog v-el-drag-dialog :close-on-click-modal="false" :title="dialogTitle" :visible.sync="dialogVisible" @close="closeDialog">
      <el-form :model="form" ref="form" :rules="rules">
        <el-form-item label="类型：" labelWidth="100px" prop="type">
          <el-input v-model="form.type"></el-input>
        </el-form-item>
        <el-form-item label="值：" labelWidth="100px" prop="value">
          <el-input v-model="form.value"></el-input>
        </el-form-item>
        <el-form-item label="标签：" labelWidth="100px" prop="label">
          <el-input v-model="form.label"></el-input>
        </el-form-item>
        <el-form-item label="描述：" labelWidth="100px" prop="description">
          <el-input type="textarea" v-model="form.description"></el-input>
        </el-form-item>
        <el-form-item label="是否可见：" labelWidth="100px" prop="visible">
          <el-select v-model="form.visible" placeholder="请选择" style="width: 100%">
            <el-option label="是" value="1"></el-option>
            <el-option label="否" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="父：" labelWidth="100px" prop="parent">
          <el-input v-model="form.parent"></el-input>
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
import { queryDict, addDict, updateDict, deleteDict } from '../api/dict.js'
import DictTypeSelect from './DictTypeSelect.vue'

export default {
  components: {
    DictTypeSelect
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
    initForm: {
      type: [Object],
      default: function () {
        return {
          id: null,
          type: '',
          value: '',
          label: '',
          description: '',
          visible: '1',
          parent: null
        }
      }
    }
  },
  data () {
    return {
      loading: false,
      queryForm: {
        type: '',
        value: '',
        label: '',
        visible: '',
        sort: null,
        _query_total: 0,
        _query_page: 1,
        _query_size: 20,
        _query_order: 'type asc,sort asc'
      },
      dataTable: [],
      dialogTitle: '',
      dialogVisible: false,
      form: this.$copy(this.initForm),
      rules: {
        type: [
          { required: true, message: '类型不能为空', trigger: 'blur' },
          { min: 2, max: 50, message: '类型的长度必须在3到50个字符之间', trigger: 'blur' }
        ],
        value: [
          { required: true, message: '值不能为空', trigger: 'blur' }
        ],
        label: [
          { required: true, message: '标签不能为空', trigger: 'blur' }
        ]
      },
      op: 0,
      currentRow: null
    }
  },
  methods: {
    handleQuery: function () {
      this.currentRow = null
      queryDict(this.queryForm).then((response) => {
        if (response && response.data && response.data.status === 'success') {
          this.dataTable = response.data.data.content
          this.queryForm._query_total = response.data.data.totalElements
        }
      })
    },
    handleAdd: function () {
      this.op = 0
      this.dialogTitle = '新增字典'
      this.dialogVisible = true
    },
    handleEdit: function () {
      if (this.currentRow === undefined || this.currentRow === null) {
        this.$message({
          message: '请选择要编辑的记录',
          type: 'warning'
        })
        return
      }
      this.form.id = this.currentRow.id
      this.form.type = this.currentRow.type
      this.form.value = this.currentRow.value
      this.form.label = this.currentRow.label
      this.form.description = this.currentRow.description
      this.form.visible = this.currentRow.visible
      this.form.parent = this.currentRow.parent
      this.op = 1
      this.dialogTitle = '编辑字典'
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
          message: '请选择要删除的记录',
          type: 'warning'
        })
        return
      }
      this.$confirm('您确定要删除该记录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteDict(this.currentRow.id).then((response) => {
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
        this.queryForm._query_order = 'type asc,sort asc'
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
            addDict(this.form).then((response) => {
              this.$handleCommonResponse(response, () => {
                this.dialogVisible = false
                this.handleQuery()
              })
            })
          } else {
            updateDict(this.form).then((response) => {
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
