<template>
  <div>
    <el-input v-model="selectVal" :clearable="clearable" @clear="clearSelect">
      <el-button slot="append" icon="fa fa-file-text" @click="handleSelect"></el-button>
    </el-input>

    <el-dialog v-el-drag-dialog :close-on-click-modal="false" title="请选择" :visible.sync="dialogVisible" append-to-body>
      <spage-table ref="spageTable" :dblclick="dblclick" :showQueryBtn="true" ></spage-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitChoose">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import SpageTable from './SpageTable.vue'
import { querySpageById } from '../api/spage.js'

export default {
  model: {
    prop: 'id',
    event: 'setValue'
  },
  components: {
    SpageTable
  },
  props: {
    id: {
      type: [Number],
      default: null
    },
    clearable: {
      type: [Boolean],
      default: true
    }
  },
  data () {
    return {
      dialogVisible: false,
      selectVal: ''
    }
  },
  watch: {
    id: function (val, oldVal) {
      this.setId(val)
    }
  },
  methods: {
    dblclick: function (row, event) {
      this.selectVal = row.title
      this.$emit('setValue', row.id)
      this.dialogVisible = false
    },
    handleSelect: function () {
      this.dialogVisible = true
    },
    submitChoose: function () {
      let row = this.$refs['spageTable'].getCurrentRow()
      if (row === undefined || row === null) {
        this.$message({
          message: '请选择记录！',
          type: 'warning'
        })
        return
      }
      this.selectVal = row.title
      this.$emit('setValue', row.id)
      this.dialogVisible = false
    },
    setId: function (val) {
      if (val === undefined || val === null || val === '') {
        this.selectVal = ''
      } else {
        querySpageById(val).then((response) => {
          if (response && response.data && response.data.status === 'success') {
            this.selectVal = response.data.data.title
          } else {
            this.selectVal = ''
          }
        })
      }
    },
    clearSelect: function () {
      this.selectVal = ''
      this.$emit('setValue', null)
    }
  },
  mounted: function () {
    this.setId(this.id)
  },
  destroyed: function () {
  }
}
</script>

<style scoped>
</style>
