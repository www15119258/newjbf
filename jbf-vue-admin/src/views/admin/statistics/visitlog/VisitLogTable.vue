<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="24" class="toolbar">
        <el-form :inline="true" :model="queryForm">
          <el-form-item v-if="showQueryBtn && $hasPerm('jbf.statistics.visitLog.view')"  label="访问链接：">
            <el-input v-model="queryForm.url" placeholder="访问链接" style="width:140px;"></el-input>
          </el-form-item>
          <el-form-item v-if="showQueryBtn && $hasPerm('jbf.statistics.visitLog.view')"  label="访问页面标题：">
            <el-input v-model="queryForm.pagetitle" placeholder="访问页面标题" style="width:140px;"></el-input>
          </el-form-item>
          <el-form-item v-if="showQueryBtn && $hasPerm('jbf.statistics.visitLog.view')"  label="访问来源地址：">
            <el-input v-model="queryForm.reffer" placeholder="访问来源地址" style="width:140px;"></el-input>
          </el-form-item>
          <el-form-item v-if="showQueryBtn && $hasPerm('jbf.statistics.visitLog.view')"  label="访问者IP：">
            <el-input v-model="queryForm.ip" placeholder="访问者IP" style="width:140px;"></el-input>
          </el-form-item>
          <el-form-item v-if="showQueryBtn && $hasPerm('jbf.statistics.visitLog.view')"  label="浏览器版本：">
            <el-input v-model="queryForm.browser" placeholder="浏览器版本" style="width:140px;"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button-group>
              <el-button v-if="showQueryBtn && $hasPerm('jbf.statistics.visitLog.view')" type="primary" @click="handleQuery">查询</el-button>
              <el-button v-if="showDeleteBtn && $hasPerm('jbf.statistics.visitLog.delete')" type="primary" @click="handleDelete">删除</el-button>
            </el-button-group>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="24">
        <el-table v-if="showQueryBtn && $hasPerm('jbf.statistics.visitLog.view')" ref="dataTable" :data="dataTable" :highlight-current-row="true" @current-change="selectChange" v-loading="loading"
                  @select="handleSelect" @select-all="handleSelectAll" @sort-change="handleSortChange" border style="width: 100%; height: 100%">
          <el-table-column v-if="!singleSelect"
                           type="selection"
                           width="55">
          </el-table-column>
          <el-table-column prop="url" sortable="custom" label="访问链接" header-align="center" align="left" width="200">
          </el-table-column>
          <el-table-column prop="pagetitle" sortable="custom" label="访问标题页面" header-align="center" align="left" width="200">
          </el-table-column>
          <el-table-column prop="createDate" sortable="custom" label="访问时间" header-align="center" align="center" width="140">
          </el-table-column>
          <el-table-column prop="visitor" sortable="custom" label="用户" header-align="center" align="left" width="140">
          </el-table-column>
          <el-table-column prop="reffer" sortable="custom" label="访问来源地址" header-align="center" align="left" width="200">
          </el-table-column>
          <el-table-column prop="ip" sortable="custom" label="访问IP" header-align="center" align="center" width="140">
          </el-table-column>
          <el-table-column prop="browser" sortable="custom" label="浏览器版本" header-align="center" align="left" width="200">
          </el-table-column>
          <el-table-column prop="country" sortable="custom" label="国家" header-align="center" align="left" width="200">
          </el-table-column>
          <el-table-column prop="province" sortable="custom" label="省份" header-align="center" align="left" width="200">
          </el-table-column>
          <el-table-column prop="city" sortable="custom" label="城市" header-align="center" align="left" width="200">
          </el-table-column>
          <el-table-column prop="isp" sortable="custom" label="运营商" header-align="center" align="left" width="200">
          </el-table-column>
          <el-table-column prop="description" label="描述" header-align="center" align="left" width="200">
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
import { queryVisitLog, deleteVisitLog } from '../api/visitlog.js'

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
    }
  },
  data () {
    return {
      loading: false,
      queryForm: {
        url: '',
        pagetitle: '',
        reffer: '',
        ip: '',
        browser: '',
        sort: '',
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
      queryVisitLog(this.queryForm).then((response) => {
        if (response && response.data && response.data.status === 'success') {
          this.dataTable = response.data.data.content
          this.queryForm._query_total = response.data.data.totalElements
        }
      })
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
        deleteVisitLog(this.currentRow.id).then((response) => {
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
