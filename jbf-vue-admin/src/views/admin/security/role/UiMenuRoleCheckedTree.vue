<template>
  <div>
    <dict-select v-show="changeTypeEnable" v-model="type" initType="sys_role_type" style="width: 100%; margin-bottom: 5px" :clearable="false" @change="typeChange" @loaded="dictLoaded"></dict-select>
    <el-tree ref="tree" nodeKey="id" :data="tree" :props="props" :default-expand-all="expandAll" style="max-height: 100%;"
          :showCheckbox="true" :expand-on-click-node="false" highlight-current @current-change="currentChange">
    </el-tree>
  </div>
</template>

<script>
import { queryRoleTree } from '../api/role'
import { queryRoles } from '../api/menu'
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
      menuid: '',
      selectVal: '',
      tree: [],
      props: {
        label: 'nickname',
        children: 'children'
      },
      currentData: null,
      currentNode: null,
      type: '0'
    }
  },
  watch: {
  },
  methods: {
    setMenuid: function (menuid) {
      this.menuid = menuid
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
      if (this.type === undefined || this.type === null || this.type === '') {
        this.tree = []
        return
      }
      if (this.menuid === undefined || this.menuid === null || this.menuid === '') {
        this.tree = []
        return
      }
      queryRoleTree(this.type).then((response) => {
        if (response && response.data) {
          this.tree = response.data
          queryRoles(this.menuid).then((response) => {
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
