<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="24" class="toolbar">
        <el-form :inline="true" :model="queryForm">
          <el-form-item v-if="showQueryBtn && $hasPerm('jbf.cms.comment.view')"  label="用户：">
            <el-input v-model="queryForm.nickname" placeholder="用户" style="width:140px;"></el-input>
          </el-form-item>
          <el-form-item v-if="showQueryBtn && $hasPerm('jbf.cms.comment.view')"  label="IP：">
            <el-input v-model="queryForm.ip" placeholder="IP" style="width:140px;"></el-input>
          </el-form-item>
          <el-form-item v-if="showQueryBtn && $hasPerm('jbf.cms.comment.view')"  label="Email：">
            <el-input v-model="queryForm.email" placeholder="Email" style="width:140px;"></el-input>
          </el-form-item>
          <el-form-item v-if="showQueryBtn && $hasPerm('jbf.cms.comment.view')"  label="类型：">
            <el-select v-model="queryForm.type" clearable placeholder="请选择" style="width:90px;">
              <el-option label="文章" value="0"></el-option>
              <el-option label="页面" value="1"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item v-if="showQueryBtn && $hasPerm('jbf.cms.comment.view')"  label="状态：">
            <el-select v-model="queryForm.status" clearable placeholder="请选择" style="width:90px;">
              <el-option label="暂存" value="0"></el-option>
              <el-option label="通过" value="1"></el-option>
              <el-option label="驳回" value="2"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button-group>
              <el-button v-if="showQueryBtn && $hasPerm('jbf.cms.comment.view')" type="primary" @click="handleQuery">查询</el-button>
            </el-button-group>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="24">
        <el-table v-if="showQueryBtn && $hasPerm('jbf.cms.comment.view')" ref="dataTable" :data="dataTable" :highlight-current-row="true" @current-change="selectChange" v-loading="loading"
                  @select="handleSelect" @select-all="handleSelectAll" @sort-change="handleSortChange" border style="width: 100%; height: 100%">
          <el-table-column v-if="!singleSelect"
                           type="selection"
                           width="55">
          </el-table-column>
          <el-table-column prop="nickname" sortable="custom" label="用户" header-align="center" align="left" width="140">
          </el-table-column>
          <el-table-column prop="content" label="内容" header-align="center" align="left" width="300">
          </el-table-column>
          <el-table-column prop="parentComment.content" label="所回复评论" header-align="center" align="left" width="300" :formatter="parentFormatter">
          </el-table-column>
          <el-table-column prop="email" sortable="custom" label="Email" header-align="center" align="left" width="180">
          </el-table-column>
          <el-table-column prop="ip" sortable="custom" label="IP" header-align="center" align="center" width="120">
          </el-table-column>
          <el-table-column prop="homepage" label="主页" header-align="center" align="left" width="180">
          </el-table-column>
          <el-table-column prop="createDate" sortable="custom" label="时间" header-align="center" align="center" width="140">
          </el-table-column>
          <el-table-column prop="status" sortable="custom" label="状态" header-align="center" align="center" width="80" :formatter="statusFormatter" fixed="right">
          </el-table-column>
          <el-table-column label="操作" fixed="right" header-align="center" align="center" width="180">
            <template slot-scope="scope">
              <el-button type="text" v-if="$hasPerm('jbf.cms.comment.verify')" @click="handleUpdateStatus(scope.$index, scope.row, '1')" :disabled="!(scope.row.status === '0' || scope.row.status === '2')">通过</el-button>
              <el-button type="text" v-if="$hasPerm('jbf.cms.comment.verify')" @click="handleUpdateStatus(scope.$index, scope.row, '2')" :disabled="!(scope.row.status === '0' || scope.row.status === '1')">驳回</el-button>
              <el-button type="text" v-if="$hasPerm('jbf.cms.comment.reply')" @click="handleReply(scope.$index, scope.row)" :disabled="!(scope.row.status === '1')">回复</el-button>
              <el-button type="text" v-if="$hasPerm('jbf.cms.comment.delete')" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination layout="total, sizes, prev, pager, next, jumper" :total="queryForm._query_total" :page-size="queryForm._query_size" :page-sizes="[20, 40, 60, 80, 100]"  style="float:right;"
                       @size-change="handleSizeChange" @current-change="handleCurrentPageChange">
        </el-pagination>
      </el-col>
    </el-row>
    <el-dialog v-el-drag-dialog :close-on-click-modal="false" :title="dialogTitle" :visible.sync="dialogVisible" @close="closeDialog">
      <el-form :model="form" ref="form" :rules="rules">
        <el-form-item label="用户：" labelWidth="100px" prop="nickname">
          <el-input v-model="form.nickname" :readonly="true"></el-input>
        </el-form-item>
        <el-form-item label="评论：" labelWidth="100px" prop="parentContent">
          <el-input type="textarea" :rows="5" v-model="form.parentContent" :readonly="true"></el-input>
        </el-form-item>
        <el-form-item label="回复：" labelWidth="100px" prop="content">
          <el-input type="textarea" :rows="5" v-model="form.content"></el-input>
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
import { queryComment, replyComment, updateCommentStatus, deleteComment } from '../api/comment.js'

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
          parentContent: '',
          nickname: '',
          content: '',
          parent: null,
          belong: null,
          type: null
        }
      }
    }
  },
  data () {
    return {
      loading: false,
      queryForm: {
        type: '',
        ip: '',
        nickname: '',
        email: '',
        status: '',
        sort: '',
        _query_total: 0,
        _query_page: 1,
        _query_size: 20,
        _query_order: this.initQueryOrder
      },
      dataTable: [],
      dialogTitle: '回复',
      dialogVisible: false,
      form: this.$copy(this.initForm),
      rules: {
        content: [
          { required: true, message: '回复内容不能为空', trigger: 'blur' },
          { min: 5, max: 200, message: '回复的长度必须在5到200之间', trigger: 'blur' }
        ]
      },
      currentRow: null
    }
  },
  methods: {
    handleQuery: function () {
      this.currentRow = null
      queryComment(this.queryForm).then((response) => {
        if (response && response.data && response.data.status === 'success') {
          this.dataTable = response.data.data.content
          this.queryForm._query_total = response.data.data.totalElements
        }
      })
    },
    resetForm: function () {
      if (this.$refs['form']) {
        this.$refs['form'].resetFields()
      }
      this.form = this.$copy(this.initForm)
    },
    handleDelete: function (index, row) {
      this.$confirm('您确定要删除该记录吗？其子评论也会连带删除', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteComment(row.id).then((response) => {
          this.$handleCommonResponse(response, () => {
            this.handleQuery()
          })
        })
      }).catch(() => {})
    },
    handleUpdateStatus: function (index, row, status) {
      updateCommentStatus(row.id, status).then((response) => {
        this.$handleCommonResponse(response, () => {
          row.status = status
        })
      })
    },
    handleReply: function (index, row) {
      this.form.parentContent = row.content
      this.form.parent = row.id
      this.form.belong = row.belong
      this.form.type = row.type
      this.form.nickname = row.nickname
      this.dialogVisible = true
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
          replyComment(this.form).then((response) => {
            this.$handleCommonResponse(response, () => {
              this.dialogVisible = false
              this.handleQuery()
            })
          })
        } else {
          return false
        }
      })
    },
    statusFormatter: function (row) {
      if (row.status === undefined || row.status === null || row.status === '') {
        return ''
      } else if (row.status === '0') {
        return '暂存'
      } else if (row.status === '1') {
        return '通过'
      } else if (row.status === '2') {
        return '驳回'
      } else {
        return ''
      }
    },
    parentFormatter: function (row) {
      if (row.parentComment === undefined || row.parentComment === null) {
        return ''
      }
      return row.parentComment.nickname + '：' + row.parentComment.content
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
