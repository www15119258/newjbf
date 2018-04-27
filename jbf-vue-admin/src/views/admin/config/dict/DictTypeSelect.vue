<template>
  <el-select v-model="selectedVal" filterable clearable :placeholder="placeholder" @change="changeVal">
    <el-option
      v-for="item in options"
      :key="item"
      :label="item"
      :value="item">
    </el-option>
  </el-select>
</template>

<script>
import { queryDictTypes } from '../api/dict.js'

export default {
  components: {
  },
  model: {
    prop: 'val',
    event: 'change'
  },
  props: {
    val: {
      type: String,
      default: ''
    },
    placeholder: {
      type: String,
      default: '请选择'
    }
  },
  data () {
    return {
      options: [],
      selectedVal: this.val
    }
  },
  methods: {
    query: function () {
      queryDictTypes().then((response) => {
        if (response && response.data) {
          this.options = response.data
        }
      })
    },
    changeVal: function (val) {
      this.$emit('change', val)
    }
  },
  mounted: function () {
    this.query()
  },
  destroyed: function () {
  }
}
</script>

<style scoped>
</style>
