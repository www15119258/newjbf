<template>
  <div>
    <el-select v-model="selectVal" placeholder="请选择" @change="onChange" :clearable="true" @clear="clearSelect">
      <el-option
        v-for="item in options"
        :key="item.id"
        :label="item.name"
        :value="item.id">
      </el-option>
    </el-select>
  </div>
</template>

<script>
import { queryCategoryList } from '../api/category.js'

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
    }
  },
  data () {
    return {
      selectVal: this.val,
      options: []
    }
  },
  methods: {
    handleQuery: function () {
      queryCategoryList().then((response) => {
        if (response && response.data && response.data.data) {
          this.options = response.data.data
        }
      })
    },
    clearSelect: function () {
      this.selectVal = ''
      this.$emit('setValue', null)
    },
    onChange: function (val) {
      this.selectVal = val
      this.$emit('setValue', val)
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
