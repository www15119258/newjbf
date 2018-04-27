<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="24" class="toolbar">
        <el-form :inline="true" :model="queryForm">
          <el-form-item v-if="showQueryBtn && $hasPerm('jbf.cms.post.view')"  label="标题：">
            <el-input v-model="queryForm.title" placeholder="标题" style="width:140px;"></el-input>
          </el-form-item>
          <el-form-item v-if="showQueryBtn && $hasPerm('jbf.cms.post.view')"  label="分类：">
            <ui-category-select v-model="queryForm.category" style="width:140px;"></ui-category-select>
          </el-form-item>
          <el-form-item v-if="showQueryBtn && $hasPerm('jbf.cms.post.view')"  label="文章来源：">
            <el-select v-model="queryForm.sourceFrom" clearable placeholder="请选择" style="width:90px;">
              <el-option label="原创" value="0"></el-option>
              <el-option label="转载" value="1"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item v-if="showQueryBtn && $hasPerm('jbf.cms.post.view')"  label="置顶：">
            <el-select v-model="queryForm.isTop" clearable placeholder="请选择" style="width:90px;">
              <el-option label="是" value="true"></el-option>
              <el-option label="否" value="false"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item v-if="showQueryBtn && $hasPerm('jbf.cms.post.view')"  label="状态：">
            <el-select v-model="queryForm.status" clearable placeholder="请选择" style="width:90px;">
              <el-option label="暂存" value="0"></el-option>
              <el-option label="发布" value="1"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button-group>
              <el-button v-if="showQueryBtn && $hasPerm('jbf.cms.post.view')" type="primary" @click="handleQuery">查询</el-button>
              <el-button v-if="showAddBtn && $hasPerm('jbf.cms.post.add')" type="primary" @click="handleAdd">新增</el-button>
              <el-button v-if="showEditBtn && $hasPerm('jbf.cms.post.edit')" type="primary" @click="handleEdit">编辑</el-button>
              <el-button v-if="showDeleteBtn && $hasPerm('jbf.cms.post.delete')" type="primary" @click="handleDelete">删除</el-button>
            </el-button-group>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="24">
        <el-table v-if="showQueryBtn && $hasPerm('jbf.cms.post.view')" ref="dataTable" :data="dataTable" :highlight-current-row="true" @current-change="selectChange" v-loading="loading" @row-dblclick="rowDblclick"
                  @select="handleSelect" @select-all="handleSelectAll" @sort-change="handleSortChange" border style="width: 100%; height: 100%">
          <el-table-column v-if="!singleSelect"
                           type="selection"
                           width="55">
          </el-table-column>
          <el-table-column prop="title" label="标题" header-align="center" align="left" width="200" fixed="left">
          </el-table-column>
          <el-table-column prop="fixedLink" label="固定链接" header-align="center" align="left" width="120">
          </el-table-column>
          <el-table-column prop="categoryList" label="分类" header-align="center" align="left" width="200">
            <template slot-scope="scope">
              <el-tag size="medium" v-for="category in scope.row.categoryList" :key="category.id">{{ category.name }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="tagcloudList" label="标签" header-align="center" align="left" width="200">
            <template slot-scope="scope">
              <el-tag size="medium" v-for="tagcloud in scope.row.tagcloudList" :key="tagcloud.id">{{ tagcloud.name }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="sourceFrom" sortable="custom" label="文章来源" header-align="center" align="center" width="100" :formatter="sourceFromFormatter">
          </el-table-column>
          <el-table-column prop="isTop" sortable="custom" label="置顶" header-align="center" align="center" width="80" :formatter="isTopFormatter">
          </el-table-column>
          <el-table-column prop="topLevel" sortable="custom" label="置顶级别" header-align="center" align="center" width="100">
          </el-table-column>
          <el-table-column prop="createDate" sortable="custom" label="创建时间" header-align="center" align="center" width="140">
          </el-table-column>
          <el-table-column prop="publishDate" sortable="custom" label="发布时间" header-align="center" align="center" width="140">
          </el-table-column>
          <el-table-column prop="creator" label="创建人" header-align="center" align="left" width="140">
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
import { queryPost, deletePost } from '../api/post.js'
import UiCategorySelect from '../category/UiCategorySelect.vue'

export default {
  components: {
    UiCategorySelect
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
        isTop: '',
        sourceFrom: '',
        commentEnable: '',
        commentShow: '',
        viewCount: '',
        commentCount: '',
        supportCount: '',
        collectionCount: '',
        category: null,
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
      queryPost(this.queryForm).then((response) => {
        if (response && response.data && response.data.status === 'success') {
          this.dataTable = response.data.data.content
          this.queryForm._query_total = response.data.data.totalElements
        }
      })
    },
    handleAdd: function () {
      this.$router.push({path: '/admin/cms/post/add'})
    },
    handleEdit: function () {
      if (this.currentRow === undefined || this.currentRow === null) {
        this.$message({
          message: '请选择要编辑的记录！',
          type: 'warning'
        })
        return
      }
      this.$router.push({path: '/admin/cms/post/edit/' + this.currentRow.id})
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
        deletePost(this.currentRow.id).then((response) => {
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
    sourceFromFormatter: function (row) {
      if (row.sourceFrom === undefined || row.sourceFrom === null || row.sourceFrom === '') {
        return ''
      } else if (row.sourceFrom === '0') {
        return '原创'
      } else if (row.sourceFrom === '1') {
        return '转载'
      } else {
        return ''
      }
    },
    isTopFormatter: function (row) {
      if (row.isTop === undefined || row.isTop === null) {
        return ''
      } else if (row.isTop) {
        return '是'
      } else {
        return '否'
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
