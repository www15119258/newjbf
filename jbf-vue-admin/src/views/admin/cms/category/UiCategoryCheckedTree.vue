<template>
  <div>
    <el-tree ref="tree" nodeKey="id" :data="tree" :props="props" :default-expand-all="expandAll" style="max-height: 100%;"
          :showCheckbox="true" :expand-on-click-node="false" highlight-current @current-change="currentChange" @check-change="checkChange">
    </el-tree>
  </div>
</template>

<script>
import { queryCategoryTree } from '../api/category'
import { queryCategorys } from '../api/post'

export default {
  components: {
  },
  model: {
    prop: 'checked',
    event: 'setValue'
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
    checked: {
      type: [Array],
      default: function () {
        return []
      }
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
      postid: '',
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
    checked: function (val, oldVal) {
      this.$refs['tree'].setCheckedKeys(val)
    }
  },
  methods: {
    setPostid: function (postid) {
      this.postid = postid
      this.handleQuery()
    },
    currentChange: function (data, node) {
      this.currentData = data
      this.currentNode = node
      this.$emit('current-change', data, node)
    },
    checkChange: function () {
      this.$emit('setValue', this.$refs['tree'].getCheckedKeys())
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
      queryCategoryTree().then((response) => {
        if (response && response.data) {
          this.tree = response.data
          if (this.postid === undefined || this.postid === null || this.postid === '') {
            return
          }
          queryCategorys(this.postid).then((response) => {
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
    clearSelected: function () {
      this.$refs['tree'].setCheckedKeys([])
    },
    getSelected: function () {
      return this.$refs['tree'].getCheckedKeys()
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
