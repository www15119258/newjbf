<template>
  <div class="workspace">
    <el-row class="toolbar">
      <toolbar :refreshBtnEnable="refreshBtnEnable" :selectBtnEnable="selectBtnEnable" :renameBtnEnable="renameBtnEnable" :copyBtnEnable="copyBtnEnable" :cutBtnEnable="cutBtnEnable" :pasteBtnEnable="pasteBtnEnable" :delBtnEnable="delBtnEnable"
      :uploadFromLocalBtnEnable="uploadFromLocalBtnEnable" :uploadFromNetBtnEnable="uploadFromNetBtnEnable" :newFolderBtnEnable="newFolderBtnEnable"></toolbar>
    </el-row>
    <el-row class="content">
      <file-list :fileTypes="fileTypes" :acceptFileTypes="acceptFileTypes"></file-list>
    </el-row>
  </div>
</template>

<script>
import Toolbar from './Toolbar.vue'
import FileList from './FileList.vue'

export default {
  components: {
    Toolbar,
    FileList
  },
  props: {
    refreshBtnEnable: {
      type: [Boolean],
      default: true
    },
    selectBtnEnable: {
      type: [Boolean],
      default: false
    },
    renameBtnEnable: {
      type: [Boolean],
      default: false
    },
    copyBtnEnable: {
      type: [Boolean],
      default: false
    },
    cutBtnEnable: {
      type: [Boolean],
      default: false
    },
    pasteBtnEnable: {
      type: [Boolean],
      default: false
    },
    delBtnEnable: {
      type: [Boolean],
      default: false
    },
    uploadFromLocalBtnEnable: {
      type: [Boolean],
      default: false
    },
    uploadFromNetBtnEnable: {
      type: [Boolean],
      default: false
    },
    newFolderBtnEnable: {
      type: [Boolean],
      default: false
    },
    fileTypes: {
      type: [String],
      default: ''
    },
    acceptFileTypes: {
      type: [String],
      default: ''
    }
  },
  data () {
    return {
    }
  },
  mounted: function () {
  },
  destroyed: function () {
  }
}
</script>

<style scoped>
  .workspace {
    height: 100%;
    padding: 40px 0 0;
    box-sizing: border-box;
    position: relative;
  }
  .toolbar {
    height: 40px;
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
  }
  .content {
    height: 100%;
  }
</style>
