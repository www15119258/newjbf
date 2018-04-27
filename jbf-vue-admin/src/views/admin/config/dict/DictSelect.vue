<template>
  <el-select v-model="selectedVal" filterable :clearable="clearable" :placeholder="placeholder" @change="changeVal">
    <el-option
      v-for="item in options"
      :key="item.value"
      :label="item.label"
      :value="item.value">
    </el-option>
  </el-select>
</template>

<script>
import { queryListByType } from '../api/dict.js'

export default {
  components: {
  },
  model: {
    prop: 'val',
    event: 'change'
  },
  props: {
    initType: {
      type: String,
      default: ''
    },
    val: {
      type: String,
      default: ''
    },
    clearable: {
      type: Boolean,
      default: true
    },
    placeholder: {
      type: String,
      default: '请选择'
    }
  },
  data () {
    return {
      options: [],
      selectedVal: this.val,
      type: this.initType
    }
  },
  watch: {
    type: function (val, oldVal) {
      this.query()
    }
  },
  methods: {
    query: function () {
      if (this.type === undefined || this.type === null || this.type === '') {
        return
      }
      queryListByType(this.type).then((response) => {
        if (response && response.data) {
          this.options = response.data
          this.$emit('loaded', this.val, this.options)
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
