<template>
  <div>
    <el-button-group class="left-btn-groups">
      <el-button type="primary" @click="confirmSelect" v-if="selectBtnEnable">确认选择</el-button>
      <el-button type="primary" @click="handleRefresh" v-if="refreshBtnEnable">刷新</el-button>
      <el-button type="primary" @click="upoladFromLocal" v-if="uploadFromLocalBtnEnable && $hasPerm('jbf.filemanage.file.uploadFromLocal')">本地上传</el-button>
      <el-button type="primary" @click="upoladFromNet" v-if="uploadFromNetBtnEnable && $hasPerm('jbf.filemanage.file.uploadFromNet')">网络上传</el-button>
      <el-button type="primary" @click="newFolder" v-if="newFolderBtnEnable && $hasPerm('jbf.filemanage.file.uploadFromNet')">新建文件夹</el-button>
      <el-button type="primary" @click="renameFile" v-if="renameBtnEnable && renameBtn && $hasPerm('jbf.filemanage.file.rename')">重命名</el-button>
      <el-button type="primary" @click="copyFile" v-if="copyBtnEnable && copyBtn && $hasPerm('jbf.filemanage.file.copy')">复制</el-button>
      <el-button type="primary" @click="cutFile" v-if="cutBtnEnable && cutBtn && $hasPerm('jbf.filemanage.file.copy')">剪切</el-button>
      <el-button type="primary" @click="pasteFile" v-if="pasteBtnEnable && pasteBtn && $hasPerm('jbf.filemanage.file.copy')">粘帖</el-button>
      <el-button type="primary" @click="deleteFile" v-if="delBtnEnable && delBtn && $hasPerm('jbf.filemanage.file.delete')">删除</el-button>
    </el-button-group>
    <el-button-group class="right-btn-groups">
      <el-button v-if="$hasPerm('jbf.filemanage.file.view')" type="primary" title="列表" icon="fa fa-th-list" @click="changeMode(0)"></el-button>
      <el-button v-if="$hasPerm('jbf.filemanage.file.view')" type="primary" title="网格" icon="fa fa-th" @click="changeMode(1)">
      </el-button>
      <el-dropdown v-if="$hasPerm('jbf.filemanage.file.view')" split-button type="primary" class="dropdownbtn" @click="changeMode(1)">
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="changeTimes(1)">1X</el-dropdown-item>
          <el-dropdown-item @click.native="changeTimes(2)">2X</el-dropdown-item>
          <el-dropdown-item @click.native="changeTimes(3)">3X</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </el-button-group>
  </div>
</template>

<script>
import event from './api/event.js'

export default {
  components: {
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
    }
  },
  data () {
    return {
      checkedRows: [],
      copyedRows: []
    }
  },
  computed: {
    renameBtn: function () {
      if (this.checkedRows && this.checkedRows.length === 1) {
        return true
      }
      return false
    },
    copyBtn: function () {
      if (this.checkedRows && this.checkedRows.length > 0) {
        return true
      }
      return false
    },
    cutBtn: function () {
      if (this.checkedRows && this.checkedRows.length > 0) {
        return true
      }
      return false
    },
    pasteBtn: function () {
      if (this.copyedRows && this.copyedRows.length > 0) {
        return true
      }
      return false
    },
    delBtn: function () {
      if (this.checkedRows && this.checkedRows.length > 0) {
        return true
      }
      return false
    }
  },
  methods: {
    changeMode: function (mode) {
      this.$root.eventBus.$emit(event.FileManage.CHANGE_MODE, mode)
    },
    changeTimes: function (times) {
      this.$root.eventBus.$emit(event.FileManage.CHANGE_TIMES, times)
    },
    handleRefresh: function () {
      this.$root.eventBus.$emit(event.FileManage.REFRESH_BTN_CLICK)
    },
    confirmSelect: function () {
      this.$root.eventBus.$emit(event.FileManage.CONFIRM_SELECT_BTN_CLICK)
    },
    newFolder: function () {
      this.$root.eventBus.$emit(event.FileManage.NEW_FOLDER_BTN_CLICK)
    },
    renameFile: function () {
      this.$root.eventBus.$emit(event.FileManage.RENAME_FILE_BTN_CLICK)
    },
    deleteFile: function () {
      this.$root.eventBus.$emit(event.FileManage.DELETE_BTN_CLICK)
    },
    copyFile: function () {
      this.$root.eventBus.$emit(event.FileManage.COPY_BTN_CLICK)
    },
    cutFile: function () {
      this.$root.eventBus.$emit(event.FileManage.CUT_BTN_CLICK)
    },
    pasteFile: function () {
      this.$root.eventBus.$emit(event.FileManage.PASTE_BTN_CLICK)
    },
    upoladFromNet: function () {
      this.$root.eventBus.$emit(event.FileManage.UPLOAD_FROM_NET_BTN_CLICK)
    },
    upoladFromLocal: function () {
      this.$root.eventBus.$emit(event.FileManage.UPLOAD_FROM_LOCAL_BTN_CLICK)
    }
  },
  mounted: function () {
    this.$root.eventBus.$on(event.FileManage.CHECKED_CHANGE, (checkedRows) => {
      this.checkedRows = checkedRows
    })
    this.$root.eventBus.$on(event.FileManage.COPYED_CHANGE, (copyedRows) => {
      this.copyedRows = copyedRows
    })
  },
  destroyed: function () {
    this.$root.eventBus.$off(event.FileManage.CHECKED_CHANGE)
    this.$root.eventBus.$off(event.FileManage.COPYED_CHANGE)
  }
}
</script>

<style scoped>
  .left-btn-groups {
    float: left;
  }
  .right-btn-groups {
    float: right;
  }
</style>
<style>
  .dropdownbtn .el-button-group .el-button:first-child {
    display: none;
  }
</style>
