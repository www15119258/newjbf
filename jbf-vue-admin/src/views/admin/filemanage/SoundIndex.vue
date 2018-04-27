<template>
  <div class="fileindex">
    <el-row :gutter="20">
      <el-col :span="24" class="breadcrumb-container">
        <strong class="title">文件列表</strong>
        <el-breadcrumb separator="/" class="breadcrumb-inner">
          <el-breadcrumb-item>
            附件管理
          </el-breadcrumb-item>
          <el-breadcrumb-item>
            文件管理
          </el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>
    <div class="mt20"></div>
    <el-row :gutter="20" style="height: 100%;">
      <el-col :span="24" style="height: 100%;">
        <workspace :fileTypes="[fileType.FOLDER, fileType.SOUND].join(',')" :renameBtnEnable="true" :copyBtnEnable="true" :cutBtnEnable="true" :pasteBtnEnable="true" :delBtnEnable="true"
                   :uploadFromLocalBtnEnable="true" :uploadFromNetBtnEnable="true" :newFolderBtnEnable="true"></workspace>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import Workspace from './Workspace.vue'
import FileType from './api/filetype.js'

export default {
  components: {
    Workspace
  },
  data () {
    return {
      fileType: FileType
    }
  },
  mounted: function () {
  },
  destroyed: function () {
  }
}
</script>

<style scoped>
  .fileindex {
    height: calc(100% - 40px);
  }
</style>
