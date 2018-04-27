<template>
  <div>
    <el-tag size="medium"
      :key="tag"
      v-for="tag in dynamicTags"
      :closable="!readonly"
      :disable-transitions="false"
            @click.native="tagClick(tag)"
      @close="handleClose(tag)">
      {{tag}}
    </el-tag>
    <el-input
      class="input-new-tag"
      v-if="inputVisible && !readonly"
      v-model="inputValue"
      ref="saveTagInput"
      size="small"
      @keyup.enter.native="handleInputConfirm"
      @blur="handleInputConfirm"
    >
    </el-input>
    <el-button v-else-if="!inputVisible && !readonly" class="button-new-tag" @click="showInput">+ New Tag</el-button>
  </div>
</template>

<script>

export default {
  components: {
  },
  model: {
    prop: 'tags',
    event: 'setValue'
  },
  props: {
    repeat: {
      type: [Boolean],
      default: false
    },
    tags: {
      type: [Array],
      default: function () {
        return []
      }
    },
    readonly: {
      type: [Boolean],
      default: true
    }
  },
  watch: {
    tags: function (val, oldVal) {
      this.dynamicTags = val
    }
  },
  data () {
    return {
      dynamicTags: this.tags,
      inputVisible: false,
      inputValue: ''
    }
  },
  methods: {
    handleClose: function (tag) {
      this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1)
      this.$emit('setValue', this.dynamicTags)
    },
    showInput: function () {
      this.inputVisible = true
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus()
      })
    },
    handleInputConfirm: function () {
      let inputValue = this.inputValue
      if (inputValue) {
        if (!this.repeat) {
          if (this.dynamicTags.indexOf(inputValue) >= 0) {
            this.inputVisible = false
            this.inputValue = ''
            return
          }
        }
        this.dynamicTags.push(inputValue)
      }
      this.inputVisible = false
      this.inputValue = ''
      this.$emit('setValue', this.dynamicTags)
    },
    tagClick: function (tag) {
      this.$emit('click', tag)
    }
  },
  mounted: function () {
  },
  destroyed: function () {
  }
}
</script>

<style scoped>
.el-tag + .el-tag {
  margin-left: 10px;
}
.el-tag {
  margin-top: 2px;
  margin-bottom: 2px;
}
.button-new-tag {
  margin-left: 10px;
  /*height: 32px;*/
  /*line-height: 30px;*/
  /*padding-top: 0;*/
  /*padding-bottom: 0;*/
}
.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
.el-tag {
  cursor: pointer;
}
</style>
