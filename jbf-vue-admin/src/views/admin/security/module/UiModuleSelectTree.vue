<template>
  <div>
    <el-input v-model="selectVal" :readonly="true" :clearable="clearable" @clear="clearSelect">
      <el-button slot="append" icon="fa fa-group" @click="handleSelect"></el-button>
    </el-input>
    <el-dialog v-el-drag-dialog :closeOnClickModal="false" title="选择" :visible.sync="dialogVisible" appendToBody>
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
import { queryModuleTree, queryModuleById } from '../api/module'

export default {
  model: {
    prop: 'val',
    event: 'setValue'
  },
  components: {
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
    clearable: {
      type: [Boolean],
      default: true
    },
    expandAll: {
      type: [Boolean],
      default: true
    },
    reserved: {
      type: [Array],
      default: function () {
        return []
      }
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
      currentNode: null
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
      queryModuleById(val).then((response) => {
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
          message: '请选择模块！',
          type: 'warning'
        })
        return
      }
      let key = this.currentData.id
      for (let k of this.reserved) {
        if (k === key) {
          this.$message({
            message: '非法的模块！',
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
      queryModuleTree().then((response) => {
        if (response && response.data) {
          this.tree = response.data
        }
      })
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
