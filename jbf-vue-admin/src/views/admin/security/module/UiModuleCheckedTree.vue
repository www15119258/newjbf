<template>
  <div>
    <el-tree ref="tree" nodeKey="id" :data="tree" :props="props" :default-expand-all="expandAll" style="max-height: 100%;"
          :showCheckbox="true" :expand-on-click-node="false" highlight-current @current-change="currentChange">
    </el-tree>
  </div>
</template>

<script>
import { queryModuleTree } from '../api/module'
import { queryModules } from '../api/role'

export default {
  components: {
  },
  props: {
    readonly: {
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
      currentData: null,
      currentNode: null
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
    handleSelect: function () {
      if (this.readonly) {
        return
      }
      this.handleQuery()
    },
    handleQuery: function () {
      this.currentData = null
      this.currentNode = null
      if (this.roleid === undefined || this.roleid === null || this.roleid === '') {
        this.tree = []
        return
      }
      queryModuleTree().then((response) => {
        if (response && response.data) {
          this.tree = response.data
          queryModules(this.roleid).then((response) => {
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
    clearSelected: function () {
      this.$refs['tree'].setCheckedKeys([])
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
