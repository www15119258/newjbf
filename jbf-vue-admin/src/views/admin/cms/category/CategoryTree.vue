<template>
  <div>
    <div class="edit-div" style="margin-bottom: 5px">
      <el-button-group v-if="showQueryBtn && $hasPerm('jbf.cms.category.view')">
        <el-button type="primary" icon="el-icon-refresh" title="刷新" @click="handleQuery"></el-button>
        <el-button v-if="showAddBtn && $hasPerm('jbf.cms.category.add')" type="primary" icon="el-icon-plus" title="新增" @click="handleAdd"></el-button>
        <el-button v-if="showEditBtn && $hasPerm('jbf.cms.category.edit')" type="primary" icon="el-icon-edit" title="编辑" @click="handleEdit"></el-button>
        <el-button v-if="showDeleteBtn && $hasPerm('jbf.cms.category.delete')" type="primary" icon="el-icon-close" title="删除" @click="handleDelete"></el-button>
      </el-button-group>
    </div>
    <el-tree v-if="showQueryBtn && $hasPerm('jbf.cms.category.view')" ref="tree" nodeKey="id" :data="tree" :props="props" :defaultExpandAll="expandAll"
    :expandOnClickNode="false" :defaultExpandedKeys="expandedArray" highlightCurrent @current-change="selectedChange">
    </el-tree>
    <el-dialog v-el-drag-dialog :close-on-click-modal="false" :title="dialogTitle" :visible.sync="dialogVisible" @close="closeDialog">
      <el-form :model="form" ref="form" :rules="rules">
        <el-form-item label="名称：" labelWidth="100px" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="URL：" labelWidth="100px" prop="url">
          <el-input v-model="form.url"></el-input>
        </el-form-item>
        <el-form-item label="描述：" labelWidth="100px" prop="description">
          <el-input type="textarea" v-model="form.description"></el-input>
        </el-form-item>
        <el-form-item label="父：" labelWidth="100px" prop="parent">
          <ui-category-select-tree v-model="form.parent" :reserved="[form.id]"></ui-category-select-tree>
        </el-form-item>
        <el-form-item label="排序：" labelWidth="100px" prop="sort">
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
import { queryCategoryTree, addCategory, updateCategory, deleteCategory } from '../api/category.js'
import UiCategorySelectTree from './UiCategorySelectTree.vue'

export default {
  components: {
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
    initForm: {
      type: [Object],
      default: function () {
        return {
          id: null,
          name: '',
          url: '',
          description: '',
          parent: null,
          sort: 100
        }
      }
    }
  },
  data () {
    return {
      tree: [],
      props: {
        label: 'name',
        children: 'children'
      },
      expandAll: this.initExpandAll,
      expandedArray: this.initExpandedArray,
      currentData: null,
      currentNode: null,
      dialogTitle: '',
      dialogVisible: false,
      op: 0,
      form: this.$copy(this.initForm),
      rules: {
        name: [
          { required: true, message: '名称不能为空', trigger: 'blur' },
          { min: 1, max: 20, message: '名称的长度必须在1到20之间', trigger: 'blur' }
        ],
        url: [
          { required: true, message: 'URL不能为空', trigger: 'blur' },
          { min: 1, max: 20, message: 'URL的长度必须在1到20之间', trigger: 'blur' }
        ],
        description: [
          { min: 0, max: 200, message: '描述的长度必须在0到200之间', trigger: 'blur' }
        ],
        sort: [
          { required: true, message: '排序不能为空', trigger: 'blur' },
          { type: 'number', message: '排序必须为数字', trigger: 'blur' }
        ]
      }
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
      queryCategoryTree().then((response) => {
        if (response && response.data) {
          this.tree = response.data
        }
      })
    },
    handleAdd: function () {
      this.op = 0
      this.dialogTitle = '新建分类'
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
      this.dialogTitle = '编辑分类'
      this.form.id = this.currentData.id
      this.form.url = this.currentData.url
      this.form.name = this.currentData.name
      this.form.description = this.currentData.description
      this.form.parent = this.currentData.parent
      this.form.sort = this.currentData.sort
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
        deleteCategory(this.currentData.id).then((response) => {
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
            addCategory(this.form).then((response) => {
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
            updateCategory(this.form).then((response) => {
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
