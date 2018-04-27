<template>
  <div>
    <div v-if="preview" class="preview-div" :style="'height: ' + height">
      <img :src="selectVal" class="preview" @click="previewImg"/>
    </div>
    <el-input v-model="selectVal" :readonly="readonly" :clearable="clearable" @clear="clearSelect" @blur="inputBlur">
      <el-button slot="append" icon="fa fa-folder" @click="handleSelect"></el-button>
    </el-input>
    <vue-gallery :images="images" :index="imageIndex" @close="imageIndex = null"></vue-gallery>
  </div>
</template>

<script>
import VueGallery from 'vue-gallery'

export default {
  model: {
    prop: 'val',
    event: 'setValue'
  },
  components: {
    VueGallery
  },
  props: {
    val: {
      type: [String],
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
    preview: {
      type: [Boolean],
      default: false
    },
    height: {
      type: [String],
      default: '0'
    }
  },
  data () {
    return {
      selectVal: this.val,
      images: [],
      imageIndex: null
    }
  },
  watch: {
    val: function (val, oldVal) {
      this.setVal(val)
    }
  },
  methods: {
    setVal: function (val) {
      this.selectVal = val
    },
    clearSelect: function () {
      this.$emit('setValue', '')
    },
    handleSelect: function () {
      window.open('/admin/filemanage/FileSelect?from=inputBtn&types=0,4')
      window.inputBtnCallback = (url) => {
        this.selectVal = url
        this.$emit('setValue', this.selectVal)
      }
    },
    inputBlur: function () {
      this.$emit('setValue', this.selectVal)
    },
    previewImg: function () {
      this.images = [this.selectVal]
      this.imageIndex = 0
    }
  },
  mounted: function () {
  },
  destroyed: function () {
  }
}
</script>

<style scoped>
  .preview-div {
    width: 100%;
    position: relative;
    text-align: center;
  }
  .preview {
    max-width: 100%;
    max-height: 100%;
    position: absolute;
    left: 50%;
    top: 50%;
    text-align: center;
    transform: translate(-50%,-50%);
  }
</style>
