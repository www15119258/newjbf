<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="24" class="toolbar">
        <el-form :inline="true" :model="queryForm">
          <el-form-item v-if="showQueryBtn && $hasPerm('jbf.cms.spage.view')"  label="标题：">
            <el-input v-model="queryForm.title" placeholder="标题" style="width:140px;"></el-input>
          </el-form-item>
          <el-form-item v-if="showQueryBtn && $hasPerm('jbf.cms.spage.view')"  label="状态：">
            <el-select v-model="queryForm.status" clearable placeholder="请选择" style="width:90px;">
              <el-option label="暂存" value="0"></el-option>
              <el-option label="发布" value="1"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button-group>
              <el-button v-if="showQueryBtn && $hasPerm('jbf.cms.spage.view')" type="primary" @click="handleQuery">查询</el-button>
              <el-button v-if="showAddBtn && $hasPerm('jbf.cms.spage.add')" type="primary" @click="handleAdd">新增</el-button>
              <el-button v-if="showEditBtn && $hasPerm('jbf.cms.spage.edit')" type="primary" @click="handleEdit">编辑</el-button>
              <el-button v-if="showDeleteBtn && $hasPerm('jbf.cms.spage.delete')" type="primary" @click="handleDelete">删除</el-button>
            </el-button-group>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="24">
        <el-table v-if="showQueryBtn && $hasPerm('jbf.cms.spage.view')" ref="dataTable" :data="dataTable" :highlight-current-row="true" @current-change="selectChange" v-loading="loading" @row-dblclick="rowDblclick"
                  @select="handleSelect" @select-all="handleSelectAll" @sort-change="handleSortChange" border style="width: 100%; height: 100%">
          <el-table-column v-if="!singleSelect"
                           type="selection"
                           width="55">
          </el-table-column>
          <el-table-column prop="title" label="标题" header-align="center" align="left" width="200" fixed="left">
          </el-table-column>
          <el-table-column prop="fixedLink" label="固定链接" header-align="center" align="left" width="120">
          </el-table-column>
          <el-table-column prop="template" label="模板" header-align="center" align="left" width="120">
          </el-table-column>
          <el-table-column prop="createDate" sortable="custom" label="创建时间" header-align="center" align="center" width="140">
          </el-table-column>
          <el-table-column prop="viewCount" sortable="custom" label="浏览数" header-align="center" align="center" width="100">
          </el-table-column>
          <el-table-column prop="status" sortable="custom" label="状态" header-align="center" align="center" width="80" :formatter="statusFormatter" fixed="right">
          </el-table-column>
        </el-table>
        <el-pagination layout="total, sizes, prev, pager, next, jumper" :total="queryForm._query_total" :page-size="queryForm._query_size" :page-sizes="[20, 40, 60, 80, 100]"  style="float:right;"
                       @size-change="handleSizeChange" @current-change="handleCurrentPageChange">
        </el-pagination>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { querySpage, deleteSpage } from '../api/spage.js'

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
    dblclick: {
      type: Function,
      default: null
    }
  },
  data () {
    return {
      loading: false,
      queryForm: {
        title: '',
        sort: '',
        commentEnable: '',
        commentShow: '',
        viewCount: '',
        commentCount: '',
        supportCount: '',
        collectionCount: '',
        status: '',
        _query_total: 0,
        _query_page: 1,
        _query_size: 20,
        _query_order: this.initQueryOrder
      },
      dataTable: [],
      currentRow: null
    }
  },
  methods: {
    handleQuery: function () {
      this.currentRow = null
      querySpage(this.queryForm).then((response) => {
        if (response && response.data && response.data.status === 'success') {
          this.dataTable = response.data.data.content
          this.queryForm._query_total = response.data.data.totalElements
        }
      })
    },
    handleAdd: function () {
      this.$router.push({path: '/admin/cms/spage/add'})
    },
    handleEdit: function () {
      if (this.currentRow === undefined || this.currentRow === null) {
        this.$message({
          message: '请选择要编辑的记录！',
          type: 'warning'
        })
        return
      }
      this.$router.push({path: '/admin/cms/spage/edit/' + this.currentRow.id})
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
        deleteSpage(this.currentRow.id).then((response) => {
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
    statusFormatter: function (row) {
      if (row.status === undefined || row.status === null || row.status === '') {
        return ''
      } else if (row.status === '0') {
        return '暂存'
      } else if (row.status === '1') {
        return '发布'
      } else {
        return ''
      }
    },
    rowDblclick: function (row, event) {
      if (this.dblclick && this.dblclick !== null) {
        this.dblclick(row, event)
      }
    },
    getCurrentRow: function () {
      return this.currentRow
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
  .el-tag + .el-tag {
    margin-left: 10px;
  }
  .el-tag {
    margin-top: 2px;
    margin-bottom: 2px;
  }
</style>
