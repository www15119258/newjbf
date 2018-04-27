<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="24" class="toolbar">
        <el-form :inline="true" :model="queryForm">
          <el-form-item v-if="showQueryBtn && $hasPerm('jbf.cms.aggregation.view')"  label="名称：">
            <el-input v-model="queryForm.name" placeholder="名称" style="width:140px;"></el-input>
          </el-form-item>
          <el-form-item v-if="showQueryBtn && $hasPerm('jbf.cms.aggregation.view')"  label="类型：">
            <dict-select v-model="queryForm.type" initType="cms_aggregation_type"></dict-select>
          </el-form-item>
          <el-form-item v-if="showQueryBtn && $hasPerm('jbf.cms.aggregation.view')"  label="状态：">
            <el-select v-model="queryForm.status" clearable placeholder="请选择" style="width:160px;">
              <el-option label="启用" value="1"></el-option>
              <el-option label="禁用" value="0"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button-group>
              <el-button v-if="showQueryBtn && $hasPerm('jbf.cms.aggregation.view')" type="primary" @click="handleQuery">查询</el-button>
              <el-button v-if="showAddBtn && $hasPerm('jbf.cms.aggregation.add')" type="primary" @click="handleAdd">新增</el-button>
              <el-button v-if="showEditBtn && $hasPerm('jbf.cms.aggregation.edit')" type="primary" @click="handleEdit">编辑</el-button>
              <el-button v-if="showDeleteBtn && $hasPerm('jbf.cms.aggregation.delete')" type="primary" @click="handleDelete">删除</el-button>
            </el-button-group>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="24">
        <el-table v-if="showQueryBtn && $hasPerm('jbf.cms.aggregation.view')" ref="dataTable" :data="dataTable" :highlight-current-row="true" @current-change="selectChange" v-loading="loading"
                  @select="handleSelect" @select-all="handleSelectAll" @sort-change="handleSortChange" border style="width: 100%; height: 100%">
          <el-table-column v-if="!singleSelect"
                           type="selection"
                           width="55">
          </el-table-column>
          <el-table-column prop="name" sortable="custom" label="名称" header-align="center" align="left" width="140" fixed="left">
          </el-table-column>
          <el-table-column prop="type" sortable="custom" label="类型" header-align="center" align="left" width="120">
          </el-table-column>
          <el-table-column prop="aggregationType" sortable="custom" label="文章集类型" header-align="center" align="center" width="120" :formatter="aggregationTypeFormatter">
          </el-table-column>
          <el-table-column prop="refer" label="关联内容" header-align="center" align="left" width="200" :formatter="referFormatter">
          </el-table-column>
          <el-table-column prop="logo" label="Logo" header-align="center" align="center">
            <template slot-scope="scope">
              <center-image v-if="scope.row.logo" :src="scope.row.logo" height="80px"></center-image>
            </template>
          </el-table-column>
          <el-table-column prop="target" sortable="custom" label="链接打开方式" header-align="center" align="center" width="120">
          </el-table-column>
          <el-table-column prop="sort" label="排序" header-align="center" align="center" width="80">
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
        <el-form-item label="名称：" labelWidth="120px" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="描述：" labelWidth="120px" prop="description">
          <el-input type="textarea" v-model="form.description"></el-input>
        </el-form-item>
        <el-form-item label="类型：" labelWidth="120px" prop="type">
          <dict-select v-model="form.type" initType="cms_aggregation_type" style="width: 100%;" :clearable="true"></dict-select>
        </el-form-item>
        <el-form-item label="文章集类型：" labelWidth="120px" prop="aggregationType">
          <el-radio-group v-model="form.aggregationType">
            <el-radio-button label="0">文章</el-radio-button>
            <el-radio-button label="1">页面</el-radio-button>
            <el-radio-button label="2">分类</el-radio-button>
            <el-radio-button label="3">自定义链接</el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item v-if="form.aggregationType === '0'" label="文章：" labelWidth="120px" prop="postid">
          <ui-post-select v-model="form.postid"></ui-post-select>
        </el-form-item>
        <el-form-item v-if="form.aggregationType === '1'" label="页面：" labelWidth="120px" prop="spageid">
          <ui-spage-select v-model="form.spageid"></ui-spage-select>
        </el-form-item>
        <el-form-item v-if="form.aggregationType === '2'" label="分类：" labelWidth="120px" prop="categoryid">
          <ui-category-select-tree v-model="form.categoryid"></ui-category-select-tree>
        </el-form-item>
        <el-form-item v-if="form.aggregationType === '3'" label="链接地址：" labelWidth="120px" prop="link">
          <el-input v-model="form.link"></el-input>
        </el-form-item>
        <el-form-item label="LOGO：" labelWidth="120px" prop="logo">
          <input-file-select :preview="true" height="80px" v-model='form.logo'></input-file-select>
        </el-form-item>
        <el-form-item label="链接打开方式：" labelWidth="120px" prop="target">
          <el-select v-model="form.target" placeholder="请选择" style="width:100%;">
            <el-option label="_blank" value="_blank"></el-option>
            <el-option label="_self" value="_self"></el-option>
            <el-option label="_parent" value="_parent"></el-option>
            <el-option label="_top" value="_top"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态：" labelWidth="120px" prop="status">
          <el-select v-model="form.status" placeholder="请选择" style="width:100%;">
            <el-option label="启用" value="1"></el-option>
            <el-option label="禁用" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="排序：" labelWidth="120px" prop="sort">
          <el-input type="number" v-model.number="form.sort"></el-input>
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
import { queryAggregation, addAggregation, updateAggregation, deleteAggregation } from '../api/aggregation.js'
import CenterImage from '@/components/image/CenterImage.vue'
import InputFileSelect from '@/views/admin/filemanage/InputFileSelect.vue'
import DictSelect from '@/views/admin/config/dict/DictSelect.vue'
import UiPostSelect from '../post/UiPostSelect.vue'
import UiSpageSelect from '../spage/UiSpageSelect.vue'
import UiCategorySelectTree from '../category/UiCategorySelectTree.vue'

export default {
  components: {
    CenterImage,
    InputFileSelect,
    DictSelect,
    UiPostSelect,
    UiSpageSelect,
    UiCategorySelectTree
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
          description: '',
          type: 'default',
          logo: '',
          link: '',
          target: '_blank',
          status: '1',
          refer: null,
          postid: null,
          spageid: null,
          categoryid: null,
          aggregationType: '3',
          sort: 100
        }
      }
    }
  },
  data () {
    return {
      loading: false,
      queryForm: {
        name: '',
        status: '',
        sort: '',
        target: '',
        type: '',
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
          { min: 0, max: 200, message: '名称的长度必须在1到200之间', trigger: 'blur' }
        ],
        description: [
          { min: 0, max: 200, message: '描述的长度必须在0到200之间', trigger: 'blur' }
        ],
        type: [
          { min: 0, max: 200, message: '类型的长度不能超过200', trigger: 'blur' }
        ],
        logo: [
          { min: 0, max: 2000, message: 'Logo图片的长度不能超过2000', trigger: 'blur' }
        ],
        link: [
          { min: 0, max: 2000, message: '链接地址的长度不能超过2000', trigger: 'blur' }
        ],
        target: [
          { required: true, message: '链接打开方式不能为空', trigger: 'blur' }
        ],
        status: [
          { required: true, message: '状态不能为空', trigger: 'blur' }
        ],
        sort: [
          { required: true, message: '排序不能为空', trigger: 'blur' },
          { type: 'number', message: '排序必须为数字', trigger: 'blur' }
        ]
      },
      op: 0,
      currentRow: null
    }
  },
  methods: {
    handleQuery: function () {
      this.currentRow = null
      queryAggregation(this.queryForm).then((response) => {
        if (response && response.data && response.data.status === 'success') {
          this.dataTable = response.data.data.content
          this.queryForm._query_total = response.data.data.totalElements
        }
      })
    },
    handleAdd: function () {
      this.op = 0
      this.dialogTitle = '新增文章集'
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
      this.form = this.$copy(this.currentRow)
      this.op = 1
      if (this.form.aggregationType === '0') {
        this.form.postid = this.form.refer
      } else if (this.form.aggregationType === '1') {
        this.form.spageid = this.form.refer
      } else if (this.form.aggregationType === '2') {
        this.form.categoryid = this.form.refer
      } else {
      }
      this.dialogTitle = '编辑文章集'
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
        deleteAggregation(this.currentRow.id).then((response) => {
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
          if (this.form.aggregationType === '0') {
            if (this.form.postid === undefined || this.form.postid === null || this.form.postid === '') {
              this.$message({
                message: '请选择文章！',
                type: 'error'
              })
              return
            }
            this.form.refer = this.form.postid
            this.form.link = ''
          } else if (this.form.aggregationType === '1') {
            if (this.form.spageid === undefined || this.form.spageid === null || this.form.spageid === '') {
              this.$message({
                message: '请选择页面！',
                type: 'error'
              })
              return
            }
            this.form.refer = this.form.spageid
            this.form.link = ''
          } else if (this.form.aggregationType === '2') {
            if (this.form.categoryid === undefined || this.form.categoryid === null || this.form.categoryid === '') {
              this.$message({
                message: '请选择分类！',
                type: 'error'
              })
              return
            }
            this.form.refer = this.form.categoryid
            this.form.link = ''
          } else {
            if (this.form.link === undefined || this.form.link === null || this.form.link === '') {
              this.$message({
                message: '请填写链接地址！',
                type: 'error'
              })
              return
            }
            this.form.refer = null
          }
          delete this.form.post
          delete this.form.spage
          delete this.form.category
          if (this.op === 0) {
            addAggregation(this.form).then((response) => {
              this.$handleCommonResponse(response, () => {
                this.dialogVisible = false
                this.handleQuery()
              })
            })
          } else {
            updateAggregation(this.form).then((response) => {
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
    },
    aggregationTypeFormatter: function (row) {
      if (row.aggregationType === undefined || row.aggregationType === null || row.aggregationType === '') {
        return ''
      } else if (row.aggregationType === '0') {
        return '文章'
      } else if (row.aggregationType === '1') {
        return '页面'
      } else if (row.aggregationType === '2') {
        return '分类'
      } else if (row.aggregationType === '3') {
        return '自定义链接'
      } else {
        return ''
      }
    },
    referFormatter: function (row) {
      if (row.aggregationType === undefined || row.aggregationType === null || row.aggregationType === '') {
        return ''
      } else if (row.aggregationType === '0') {
        if (row.post) {
          return row.post.title
        }
      } else if (row.aggregationType === '1') {
        if (row.spage) {
          return row.spage.title
        }
      } else if (row.aggregationType === '2') {
        if (row.category) {
          return row.category.name
        }
      } else if (row.aggregationType === '3') {
        return row.link
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
