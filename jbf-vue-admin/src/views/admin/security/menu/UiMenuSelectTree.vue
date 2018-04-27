<template>
  <div>
    <el-input v-model="selectVal" :readonly="true" :clearable="clearable" @clear="clearSelect">
      <el-button slot="append" icon="fa fa-navicon" @click="handleSelect"></el-button>
    </el-input>
    <el-dialog v-el-drag-dialog :closeOnClickModal="false" title="选择" :visible.sync="dialogVisible" appendToBody>
      <dict-select v-show="changeTypeEnable" v-model="type" initType="sys_menu_type" style="width: 100%; margin-bottom: 5px" :clearable="false" @change="menuTypeChange"></dict-select>
      <el-tree ref="tree" nodeKey="id" :data="tree" :props="props" :default-expand-all="expandAll" style="max-height: 100%;"
             :expand-on-click-node="false" highlight-current @current-change="currentChange" @dblclick.native.stop="dialogSubmit">
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="dialogSubmit">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { queryMenuTree, queryMenuById } from '../api/menu'
import DictSelect from '@/views/admin/config/dict/DictSelect.vue'

export default {
  model: {
    prop: 'val',
    event: 'setValue'
  },
  components: {
    DictSelect
  },
  props: {
    val: {
      type: [Number],
      default: null
    },
    readonly: {
      type: [Boolean],
      default: false
    },
    changeTypeEnable: {
      type: [Boolean],
      default: false
    },
    expandAll: {
      type: [Boolean],
      default: true
    },
    clearable: {
      type: [Boolean],
      default: true
    },
    reserved: {
      type: [Array],
      default: function () {
        return []
      }
    },
    menuType: {
      type: [String],
      default: 'left'
    }
  },
  data () {
    return {
      selectVal: '',
      tree: [],
      props: {
        label: 'name',
        children: 'children'
      },
      dialogVisible: false,
      currentData: null,
      currentNode: null,
      type: this.menuType
    }
  },
  watch: {
    val: function (val, oldVal) {
      this.setVal(val)
    }
  },
  methods: {
    setVal: function (val) {
      this.selectVal = ''
      if (val === undefined || val === null) {
        return
      }
      queryMenuById(val).then((response) => {
        if (response && response.data && response.data.status === 'success') {
          let role = response.data.data
          this.selectVal = role.name
        }
      })
    },
    currentChange: function (data, node) {
      this.currentData = data
      this.currentNode = node
      this.$emit('current-change', data, node)
    },
    dialogSubmit: function () {
      if (this.currentData === undefined || this.currentData === null) {
        this.$message({
          message: '请选择目录！',
          type: 'warning'
        })
        return
      }
      let key = this.currentData.id
      for (let k of this.reserved) {
        if (k === key) {
          this.$message({
            message: '非法的目录！',
            type: 'warning'
          })
          return
        }
      }
      let oldVal = this.val
      this.selectVal = this.currentData.name
      this.$emit('setValue', key)
      this.$emit('change', key, oldVal)
      this.dialogVisible = false
    },
    handleSelect: function () {
      if (this.readonly) {
        return
      }
      this.handleQuery()
      this.dialogVisible = true
    },
    handleQuery: function () {
      this.currentData = null
      this.currentNode = null
      if (this.type === undefined || this.type === null || this.type === '') {
        this.tree = []
        return
      }
      queryMenuTree(this.type).then((response) => {
        if (response && response.data) {
          this.tree = response.data
        }
      })
    },
    menuTypeChange: function () {
      this.handleQuery()
    },
    clearSelect: function () {
      let oldVal = this.val
      this.selectVal = ''
      this.$emit('setValue', null)
      this.$emit('change', null, oldVal)
    }
  },
  mounted: function () {
    this.setVal(this.val)
  },
  destroyed: function () {
  }
}
</script>

<style scoped>
</style>
