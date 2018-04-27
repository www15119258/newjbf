<template>
  <div>
    <dict-select v-show="changeTypeEnable" v-model="type" initType="sys_menu_type" style="width: 100%; margin-bottom: 5px" :clearable="false" @change="typeChange" @loaded="dictLoaded"></dict-select>
    <el-tree ref="tree" nodeKey="id" :data="tree" :props="props" :default-expand-all="expandAll" style="max-height: 100%;"
          :showCheckbox="true" :expand-on-click-node="false" highlight-current @current-change="currentChange" @dblclick.native.stop="dialogSubmit">
    </el-tree>
  </div>
</template>

<script>
import { queryMenuTree } from '../api/menu'
import { queryMenus } from '../api/role'
import DictSelect from '@/views/admin/config/dict/DictSelect.vue'

export default {
  components: {
    DictSelect
  },
  props: {
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
    reserved: {
      type: [Array],
      default: function () {
        return []
      }
    }
  },
  data () {
    return {
      roleid: '',
      selectVal: '',
      tree: [],
      props: {
        label: 'name',
        children: 'children'
      },
      dialogVisible: false,
      currentData: null,
      currentNode: null,
      type: 'left'
    }
  },
  watch: {
  },
  methods: {
    setRoleid: function (roleid) {
      this.roleid = roleid
      this.handleQuery()
    },
    currentChange: function (data, node) {
      this.currentData = data
      this.currentNode = node
      this.$emit('current-change', data, node)
    },
    dialogSubmit: function () {
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
      if (this.roleid === undefined || this.roleid === null || this.roleid === '') {
        this.tree = []
        return
      }
      queryMenuTree(this.type).then((response) => {
        if (response && response.data) {
          this.tree = response.data
          queryMenus(this.roleid).then((response) => {
            let datas = response.data
            let checked = []
            for (let d of datas) {
              let node = this.$refs['tree'].getNode(d)
              if (node && (node.childNodes === undefined || node.childNodes === null || node.childNodes.length === 0)) {
                checked.push(d)
              }
              this.$refs['tree'].setCheckedKeys(checked)
            }
          })
        }
      })
    },
    typeChange: function () {
      this.handleQuery()
    },
    dictLoaded: function (val, options) {
      this.handleQuery()
    },
    clearSelected: function () {
      this.$refs['tree'].setCheckedKeys([])
    },
    getType: function () {
      return this.type
    },
    getSelected: function () {
      return this.$refs['tree'].getCheckedKeys()
    }
  },
  mounted: function () {
  },
  destroyed: function () {
  }
}
</script>

<style scoped>
</style>
