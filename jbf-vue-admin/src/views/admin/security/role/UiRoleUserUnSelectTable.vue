<template>
  <div>
    <el-form :inline="true" :model="queryForm">
      <el-form-item label="用户名：">
        <el-input v-model="queryForm.username" placeholder="用户名" style="width:140px;"></el-input>
      </el-form-item>
      <el-form-item label="昵称：">
        <el-input v-model="queryForm.nickname" placeholder="昵称" style="width:140px;"></el-input>
      </el-form-item>
      <el-form-item label="Email：">
        <el-input v-model="queryForm.email" placeholder="Email" style="width:140px;"></el-input>
      </el-form-item>
      <el-form-item label="状态：">
        <el-select v-model="queryForm.status" clearable placeholder="请选择" style="width:160px;">
          <el-option label="正常" value="0"></el-option>
          <el-option label="锁定" value="1"></el-option>
          <el-option label="未激活" value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-button type="primary" @click="handleQuery">查询</el-button>
    </el-form>
    <el-table ref="dataTable" :data="dataTable" :highlight-current-row="true" @current-change="selectChange" v-loading="loading"
              @select="handleSelect" @select-all="handleSelectAll" @sort-change="handleSortChange" border style="width: 100%; height: 100%">
      <el-table-column v-if="!singleSelect"
                       type="selection"
                       width="55">
      </el-table-column>
      <el-table-column prop="username" sortable="custom" label="用户名" header-align="center" align="left" width="120" fixed="left">
      </el-table-column>
      <el-table-column prop="nickname" sortable="custom" label="昵称" header-align="center" align="left" width="120">
      </el-table-column>
      <el-table-column prop="email" label="Email" header-align="center" align="left" width="180" >
      </el-table-column>
      <el-table-column prop="status" sortable="custom" label="状态" header-align="center" align="center" width="80" :formatter="userStatusFormatter">
      </el-table-column>
      <el-table-column label="操作" fixed="right" header-align="center" align="center" width="60">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="handleAdd(scope.$index, scope.row)">添加</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination layout="total, sizes, prev, pager, next, jumper" :total="queryForm._query_total" :page-size="queryForm._query_size" :page-sizes="[20, 40, 60, 80, 100]"  style="float:right;"
                   @size-change="handleSizeChange" @current-change="handleCurrentPageChange">
    </el-pagination>
  </div>
</template>

<script>
import { queryUnSelectUsers, addUser } from '../api/role'

export default {
  components: {
  },
  props: {
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
        role: '',
        username: '',
        nickname: '',
        email: '',
        status: '',
        _query_total: 0,
        _query_page: 1,
        _query_size: 20,
        _query_order: this.initQueryOrder
      },
      roleid: '',
      dataTable: [],
      rules: {
      },
      op: 0,
      currentRow: null
    }
  },
  methods: {
    setRoleid: function (roleid) {
      this.queryForm.role = roleid
      this.handleQuery()
    },
    handleQuery: function () {
      this.currentRow = null
      if (this.queryForm.role === undefined || this.queryForm.role === null || this.queryForm.role === '') {
        this.dataTable = []
        this.queryForm._query_total = 0
        return
      }
      queryUnSelectUsers(this.queryForm).then((response) => {
        if (response && response.data && response.data.status === 'success') {
          this.dataTable = response.data.data.content
          this.queryForm._query_total = response.data.data.totalElements
        }
      })
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
    handleAdd: function (index, row) {
      this.$confirm('您确定要添加该记录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        addUser(this.queryForm.role, this.currentRow.id).then((response) => {
          this.$handleCommonResponse(response, () => {
            this.handleQuery()
          })
        })
      }).catch(() => {})
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
