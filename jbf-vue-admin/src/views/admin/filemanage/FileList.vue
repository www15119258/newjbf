<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item v-for="(path, index) in breadcrumbPath" :key="index">
            <span><el-button type="text" @click="selectFolder(path.path)">{{path.name}}</el-button></span>
          </el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <el-table v-if="mode === 0 && $hasPerm('jbf.filemanage.file.view')" class="fileTable" ref="dataTable" :data="dataTable" :highlight-current-row="true" @current-change="selectChange" v-loading="loading"
                  @select="handleSelect" @select-all="handleSelectAll" @sort-change="handleSortChange" border :maxHeight="tableHeight">
          <el-table-column header-align="center" align="center" label="全选" width="40" :render-header="renderCheckboxHeader">
            <template slot-scope="scope">
              <el-checkbox v-if="scope.row.name !== '..'" v-model="scope.row.checked" @click.native.stop @change="checkedChange(scope.row)"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column prop="name" label="文件名" header-align="left" align="left">
            <template slot-scope="scope">
              <i :class="fileIconFormatter(scope.row)"></i>
              <span v-if="scope.row.type === fileType.FOLDER"><el-button type="text" @click="selectFolder(scope.row.path)">{{ scope.row.name }}</el-button></span>
              <span v-else>{{ scope.row.name }}</span>
              <i v-if="scope.row.type !== fileType.FOLDER" class="fa fa-link copylink" v-clipboard:copy="scope.row.absolutePath" v-clipboard:success="onCopySuccess"></i>
            </template>
          </el-table-column>
          <el-table-column prop="size" label="大小" header-align="left" align="left" width="140" :formatter="sizeFormatter">
          </el-table-column>
          <el-table-column prop="lastModifyDate" label="修改时间" header-align="left" align="center" width="140">
          </el-table-column>
        </el-table>
        <el-row v-if="mode === 1 && $hasPerm('jbf.filemanage.file.view')" :gutter="20">
          <el-col :span="24">
            <div class="checked-all">
              <el-checkbox v-model="checkedAll" @change="checkedAllChange">全选</el-checkbox>
            </div>
          </el-col>
          <el-col :span="colspan" v-for="(item, index) in dataTable" :key="index">
            <el-card :body-style="{ padding: '5px' }" style="margin-bottom: 5px;" :title="nameFormatter(item)">
              <el-checkbox v-if="item.name !== '..'" class="card-checkbox" v-model="item.checked" @click.native.stop @change="checkedChange(item)"></el-checkbox>
              <div class="img-div" :class="timesClass" @click="itemClick(item)">
                <i v-if="item.type === fileType.FOLDER" class="image fa fa-folder" style="cursor: pointer;"></i>
                <img v-else-if="item.type === fileType.IMAGE" v-lazy="item.absolutePath" class="image">
                <i v-else-if="item.type === fileType.ZIP || item.type === fileType.RAR" class="image fa fa-file-zip-o"></i>
                <i v-else-if="item.type === fileType.SOUND" class="image fa fa-file-sound-o"></i>
                <i v-else-if="item.type === fileType.MOVIE" class="image fa fa-file-movie-o"></i>
                <i v-else class="image fa fa-file"></i>
              </div>
              <div>
                <span class="title">{{item.name}}</span>
              </div>
              <div class="copylink-div">
                <i v-if="item.type !== fileType.FOLDER" class="fa fa-link copylink" v-clipboard:copy="item.absolutePath" v-clipboard:success="onCopySuccess"></i>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
    <vue-gallery :images="images" :index="imageIndex" @close="imageIndex = null" :options="imageOptions"></vue-gallery>

    <el-dialog v-el-drag-dialog :close-on-click-modal="false" title="新建文件夹" :visible.sync="newFolderDialogVisible" @close="closeNewFolderDialog">
      <el-form :model="folderForm" ref="folderForm" :rules="folderFormRules">
        <el-form-item label="名称：" labelWidth="100px" prop="name">
          <el-input v-model="folderForm.name"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="newFolderDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitNewFolderDialog">确定</el-button>
      </div>
    </el-dialog>

    <el-dialog v-el-drag-dialog :close-on-click-modal="false" title="重命名" :visible.sync="renameFileDialogVisible" @close="closeRenameFileDialog">
      <el-form :model="renameFileForm" ref="renameFileForm" :rules="renameFileFormRules">
        <el-form-item label="名称：" labelWidth="100px" prop="name">
          <el-input v-model="renameFileForm.name"></el-input>
        </el-form-item>
        <el-input type="hidden" v-model="renameFileForm.old" style="display: none"></el-input>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="renameFileDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitRenameFormDialog">确定</el-button>
      </div>
    </el-dialog>

    <el-dialog v-el-drag-dialog :close-on-click-modal="false" title="从网络上传" :visible.sync="uploadFromNetDialogVisible" @close="closeUploadFromNetDialog">
      <el-form :model="uploadFromNetForm" ref="uploadFromNetForm" :rules="uploadFromNetFormRules">
        <el-form-item prop="source">
          <el-input type="textarea" v-model="uploadFromNetForm.source" placeholder="请输入网络地址，多个用换行分隔！"></el-input>
        </el-form-item>
        <div v-for="(item, index) in uploadFromNetForm.items" :key="index" style="width: 100%">
          <el-tag :type="item.upload" :closable="true" @dblclick.native="modifyNetFileName(item)" @close="handleRemoveNetFileName(item)" style="cursor: pointer">{{item.name}}</el-tag>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button-group class="left-btn-groups" style="float: left;">
          <el-button type="primary" @click="parseSource">分析地址</el-button>
          <el-button type="primary" @click="startUploadFromNet">开始上传</el-button>
          <el-button type="primary" @click="removeSuccessUploadFromNet">清除成功上传</el-button>
          <el-button type="primary" @click="removeAllUploadFromNet">清除所有</el-button>
        </el-button-group>
        <el-button @click="uploadFromNetDialogVisible = false">关闭</el-button>
      </div>
    </el-dialog>

    <el-dialog v-el-drag-dialog :close-on-click-modal="false" title="修改名称" :visible.sync="modifyFileNameDialogVisible" @close="closeModifyFileNameDialog">
      <el-input v-model="modifyFileName"></el-input>
      <div slot="footer" class="dialog-footer">
        <el-button @click="modifyFileNameDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitModifyFileNameDialog">确定</el-button>
      </div>
    </el-dialog>

    <el-dialog v-el-drag-dialog :close-on-click-modal="false" title="本地上传" :visible.sync="uploadFromLocalDialogVisible" @close="closeUploadFromLocalDialog">
      <el-upload class="uploadFromLocal" ref="uploadFromLocal"
        :action="uploadFromLocalServer" :multiple="true" :data="{'path': queryForm.path}" :file-list="uploadFromLocalFileList"
               :accept="acceptFileTypes"  :auto-upload="false" :on-success="uploadFromLocalSuccess" :on-error="uploadFromLocalError">
        <el-button id="addLocalFileBtn" slot="trigger" size="small" type="primary" style="display: none">选取文件</el-button>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button-group class="left-btn-groups" style="float: left;">
          <el-button type="primary" @click="addFileFromLocal">添加文件</el-button>
          <el-button type="primary" @click="startUploadFromLocal">开始上传</el-button>
          <!--<el-button type="primary" @click="removeSuccessUploadFromLocal">清除成功上传</el-button>-->
          <el-button type="primary" @click="removeAllUploadFromLocal">清除所有</el-button>
        </el-button-group>
        <el-button @click="uploadFromLocalDialogVisible = false">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listFile, newFolder, renameFile, deleteFile, copyFile, uploadFromNet, uploadFromLocalServer } from './api/file.js'
import FileType from './api/filetype.js'
import event from './api/event.js'
import VueGallery from 'vue-gallery'

export default {
  components: {
    VueGallery
  },
  props: {
    singleSelect: {
      type: [Boolean],
      default: false
    },
    initQueryOrder: {
      type: [String],
      default: 'name asc'
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
      uploadFromLocalServer: uploadFromLocalServer,
      imageOptions: {
        urlProperty: 'absolutePath',
        titleProperty: 'name'
      },
      imageIndex: null,
      mode: 1,
      times: 1,
      queryForm: {
        path: '',
        types: this.fileTypes
      },
      fileType: FileType,
      loading: false,
      dataTable: [],
      tableHeight: screen.height - 300,
      currentRow: null,
      checkedRows: [],
      copyForm: {
        oldPath: '',
        newPath: '',
        files: '',
        type: 0
      },
      checkedAll: false,
      newFolderDialogVisible: false,
      folderForm: {
        name: ''
      },
      folderFormRules: {
        name: [
          { required: true, message: '名称不能为空', trigger: 'blur' },
          { min: 1, max: 200, message: '名称的长度必须在1到200之间', trigger: 'blur' }
        ]
      },
      renameFileDialogVisible: false,
      renameFileForm: {
        old: '',
        name: ''
      },
      renameFileFormRules: {
        name: [
          { required: true, message: '名称不能为空', trigger: 'blur' },
          { min: 1, max: 200, message: '名称的长度必须在1到200之间', trigger: 'blur' }
        ]
      },
      uploadFromNetDialogVisible: false,
      uploadFromNetForm: {
        source: '',
        items: []
      },
      uploadFromNetFormRules: {
      },
      modifyFileNameDialogVisible: false,
      modifyFileName: '',
      submitModifyFileNameFun: function () {
      },
      uploadFromLocalDialogVisible: false,
      uploadFromLocalFileList: []
    }
  },
  computed: {
    breadcrumbPath: function () {
      if (this.queryForm.path === '' || this.queryForm.path === '/') {
        return [{path: '', name: '全部文件'}]
      }
      let array = []
      let paths = this.queryForm.path.split('/')
      let absolutePath = ''
      for (let path of paths) {
        if (path === '' || path === '/') {
          array.push({path: '', name: '全部文件'})
          continue
        }
        absolutePath = absolutePath + '/' + path
        array.push({path: absolutePath, name: path})
      }
      return array
    },
    colspan: function () {
      if (this.times === 1) {
        return 3
      } else if (this.times === 2) {
        return 6
      } else if (this.times === 3) {
        return 8
      }
      return 3
    },
    timesClass: function () {
      if (this.times === 1) {
        return 't1'
      } else if (this.times === 2) {
        return 't2'
      } else if (this.times === 3) {
        return 't3'
      }
      return 't1'
    },
    images: function () {
      let array = []
      for (let item of this.dataTable) {
        if (item.type === this.fileType.IMAGE) {
          array.push(item)
        }
      }
      return array
    }
  },
  methods: {
    handleQuery: function () {
      this.currentRow = null
      this.checkedRows = []
      this.checkedAll = false
      let ele = document.querySelector("#tableCheckedAll input[type='checkbox']")
      if (ele) {
        ele.checked = false
      }
      ele = document.querySelector('#tableCheckedAll .is-checked')
      if (ele) {
        ele.classList.remove('is-checked')
      }
      ele = document.querySelector('#tableCheckedAll')
      if (ele) {
        ele.classList.remove('is-checked')
      }
      this.$root.eventBus.$emit(event.FileManage.CHECKED_CHANGE, this.checkedRows)
      listFile(this.queryForm).then((response) => {
        if (response && response.data) {
          this.dataTable = response.data
        }
      })
    },
    renderCheckboxHeader: function (createElement, { column }) {
      return createElement(
        'el-checkbox',
        {
          attrs: {
            id: 'tableCheckedAll'
          },
          on: {
            change: this.checkedAllChange
          }
        }
      )
    },
    selectChange: function (currentRow, oldCurrentRow) {
      this.currentRow = currentRow
    },
    handleSelect: function (selection, row) {
      this.checkedRows = selection
      this.$root.eventBus.$emit(event.FileManage.CHECKED_CHANGE, this.checkedRows)
    },
    handleSelectAll: function (selection) {
      this.checkedRows = selection
      this.$root.eventBus.$emit(event.FileManage.CHECKED_CHANGE, this.checkedRows)
    },
    handleCurrentPageChange: function (val) {
      this.queryForm._query_page = val
      this.handleQuery()
    },
    handleSortChange: function (column) {
      let sort = column.prop
      if (sort === undefined || sort === null || sort === '') {
        this.queryForm._query_order = this.initQueryOrder
        return
      }
      let order = column.order
      if (order === 'ascending') {
        this.queryForm._query_order = sort + ' asc'
      } else {
        this.queryForm._query_order = sort + ' desc'
      }
      this.handleQuery()
    },
    sizeFormatter: function (row) {
      if (row.type === FileType.FOLDER) {
        return '-'
      }
      return this.formatterSize(row.size)
    },
    nameFormatter: function (row) {
      if (row.type === FileType.FOLDER) {
        return row.name
      }
      return row.name + '\n' + this.formatterSize(row.size)
    },
    formatterSize: function (size) {
      if (size > 1024 * 1024 * 1024 * 1024) {
        return (size / (1024 * 1024 * 1024 * 1024)).toFixed(2) + 'T'
      } else if (size > 1024 * 1024 * 1024) {
        return (size / (1024 * 1024 * 1024)).toFixed(2) + 'G'
      } else if (size > 1024 * 1024) {
        return (size / (1024 * 1024)).toFixed(2) + 'M'
      } else if (size > 1024) {
        return (size / 1024).toFixed(2) + 'K'
      } else if (size) {
        return size + 'B'
      }
      return size
    },
    fileIconFormatter: function (row) {
      if (row.type === FileType.FOLDER) {
        return 'fa fa-folder'
      } else if (row.type === FileType.FILE) {
        return 'fa fa-file'
      } else if (row.type === FileType.ZIP) {
        return 'fa fa-file-zip-o'
      } else if (row.type === FileType.RAR) {
        return 'fa fa-file-zip-o'
      } else if (row.type === FileType.IMAGE) {
        return 'fa fa-file-image-o'
      } else if (row.type === FileType.SOUND) {
        return 'fa fa-file-sound-o'
      } else if (row.type === FileType.MOVIE) {
        return 'fa fa-file-movie-o'
      }
      return 'fa fa-file'
    },
    selectFolder: function (path) {
      this.queryForm.path = path
      this.handleQuery()
    },
    itemClick: function (row) {
      if (row.type === this.fileType.FOLDER) {
        this.selectFolder(row.path)
      } else if (row.type === this.fileType.IMAGE) {
        this.imageIndex = this.images.indexOf(row)
      }
    },
    checkedChange: function (item) {
      if (item.checked) {
        this.checkedRows.push(item)
        if (this.checkedRows.length === this.dataTable.length) {
          this.checkedAll = true
        } else {
          if (this.queryForm.path !== '' && this.queryForm.path !== '/' && this.checkedRows.length === this.dataTable.length - 1) {
            this.checkedAll = true
          }
        }
      } else {
        let index = this.checkedRows.indexOf(item)
        if (index >= 0) {
          this.checkedRows.splice(index, 1)
        }
        this.checkedAll = false
      }
      this.$root.eventBus.$emit(event.FileManage.CHECKED_CHANGE, this.checkedRows)
    },
    checkedAllChange: function (val) {
      this.checkedAll = val
      if (val) {
        this.checkedRows = []
        for (let item of this.dataTable) {
          if (item.name !== '..') {
            item.checked = true
            this.checkedRows.push(item)
          }
        }
      } else {
        this.checkedRows = []
        for (let item of this.dataTable) {
          if (item.name !== '..') {
            item.checked = false
          }
        }
      }
      this.$root.eventBus.$emit(event.FileManage.CHECKED_CHANGE, this.checkedRows)
    },
    closeNewFolderDialog: function () {
      if (this.$refs['folderForm']) {
        this.$refs['folderForm'].resetFields()
      }
      this.folderForm.name = ''
    },
    submitNewFolderDialog: function () {
      this.$refs['folderForm'].validate((valid) => {
        if (valid) {
          newFolder(this.queryForm.path, this.folderForm.name).then((response) => {
            this.$handleCommonResponse(response, () => {
              this.newFolderDialogVisible = false
              this.handleQuery()
            })
          })
        }
      })
    },
    closeRenameFileDialog: function () {
      if (this.$refs['renameFileForm']) {
        this.$refs['renameFileForm'].resetFields()
      }
      this.renameFileForm.name = ''
      this.renameFileForm.old = ''
    },
    submitRenameFormDialog: function () {
      if (this.renameFileForm.old === this.renameFileForm.name) {
        this.$message({
          message: '请输入一个不同的名称！',
          type: 'warning'
        })
        return
      }
      this.$refs['renameFileForm'].validate((valid) => {
        if (valid) {
          renameFile(this.queryForm.path, this.renameFileForm.old, this.renameFileForm.name).then((response) => {
            this.$handleCommonResponse(response, () => {
              this.renameFileDialogVisible = false
              this.handleQuery()
            })
          })
        }
      })
    },
    closeUploadFromNetDialog: function () {
      if (this.$refs['uploadFromNetForm']) {
        this.$refs['uploadFromNetForm'].resetFields()
      }
      this.uploadFromNetForm.source = ''
      this.uploadFromNetForm.items = []
      this.handleQuery()
    },
    parseSource: function () {
      if (this.uploadFromNetForm.source === '') {
        this.$message({
          message: '请输入网络地址，多个用换行分隔！',
          type: 'warning'
        })
        return
      }
      let urlregexp = '(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]'
      let urlArray = this.uploadFromNetForm.source.split('\n')
      let parseArray = []
      for (let url of urlArray) {
        if (url.match(urlregexp)) {
          let i = url.lastIndexOf('/') + 1
          let name = ''
          if (url.lastIndexOf('?') >= 0) {
            name = url.substring(i, url.lastIndexOf('?'))
          } else {
            name = url.substring(i)
          }
          if (name === '') {
            name = new Date().getTime()
          }
          parseArray.push({'url': url, 'name': name, 'path': this.queryForm.path, 'upload': ''})
        }
        this.uploadFromNetForm.items = parseArray
      }
    },
    startUploadFromNet: function () {
      let uploadFiles = this.uploadFromNetForm.items.filter((item) => {
        return item.upload !== 'success'
      })
      if (uploadFiles.length > 0) {
        uploadFromNet(uploadFiles).then((response) => {
          this.$handleCommonResponse(response, () => {
            let uploaded = response.data.data
            for (let upload of uploaded) {
              for (let netFile of this.uploadFromNetForm.items) {
                if (netFile.name === upload.name) {
                  netFile.upload = 'success'
                  break
                }
              }
            }
          })
        })
      }
    },
    removeSuccessUploadFromNet: function () {
      this.uploadFromNetForm.items = this.uploadFromNetForm.items.filter((item) => {
        return item.upload !== 'success'
      })
    },
    removeAllUploadFromNet: function () {
      this.uploadFromNetForm.items = []
    },
    modifyNetFileName: function (item) {
      this.submitModifyFileNameFun = function (name) {
        item.name = name
      }
      this.modifyFileName = item.name
      this.modifyFileNameDialogVisible = true
    },
    closeModifyFileNameDialog: function () {
      this.modifyFileName = ''
    },
    submitModifyFileNameDialog: function () {
      this.submitModifyFileNameFun(this.modifyFileName)
      this.modifyFileNameDialogVisible = false
    },
    handleRemoveNetFileName: function (item) {
      this.uploadFromNetForm.items.splice(this.uploadFromNetForm.items.indexOf(item), 1)
    },
    closeUploadFromLocalDialog: function () {
      this.uploadFromLocalFileList = []
      this.handleQuery()
    },
    addFileFromLocal: function () {
      document.getElementById('addLocalFileBtn').click()
    },
    startUploadFromLocal: function () {
      this.$refs['uploadFromLocal'].submit()
    },
    removeSuccessUploadFromLocal: function () {
      this.$refs['uploadFromLocal'].clearFiles()
    },
    removeAllUploadFromLocal: function () {
      this.uploadFromLocalFileList = []
    },
    uploadFromLocalSuccess: function (response, file, fileList) {
    },
    uploadFromLocalError: function (error, file, fileList) {
      this.$message({
        message: file.name + ':' + error.message,
        type: 'error'
      })
    },
    onCopySuccess: function () {
      this.$message({
        message: '文件地址已成功复制到剪贴板！',
        type: 'success'
      })
    },
    addListeners: function () {
      this.$root.eventBus.$on(event.FileManage.CHANGE_MODE, (mode) => {
        this.mode = mode
        this.$setFileManageOption('mode', mode)
        this.handleQuery()
      })
      this.$root.eventBus.$on(event.FileManage.CHANGE_TIMES, (times) => {
        this.times = times
        this.$setFileManageOption('times', times)
        this.$setFileManageOption('mode', 1)
        if (this.mode !== 1) {
          this.mode = 1
          this.handleQuery()
        }
      })
      this.$root.eventBus.$on(event.FileManage.REFRESH_BTN_CLICK, () => {
        this.handleQuery()
      })
      this.$root.eventBus.$on(event.FileManage.NEW_FOLDER_BTN_CLICK, () => {
        this.newFolderDialogVisible = true
      })
      this.$root.eventBus.$on(event.FileManage.RENAME_FILE_BTN_CLICK, () => {
        if (this.checkedRows && this.checkedRows.length === 1) {
          let checked = this.checkedRows[0]
          this.renameFileForm.old = checked.name
          this.renameFileForm.name = checked.name
          this.renameFileDialogVisible = true
        }
      })
      this.$root.eventBus.$on(event.FileManage.COPY_BTN_CLICK, () => {
        if (this.checkedRows && this.checkedRows.length > 0) {
          let copyedRows = []
          for (let checked of this.checkedRows) {
            copyedRows.push(this.$copy(checked))
          }
          this.copyForm.type = 0
          this.copyForm.oldPath = this.queryForm.path
          this.copyForm.files = copyedRows.map(item => item.name).join('||@||')
          this.$root.eventBus.$emit(event.FileManage.COPYED_CHANGE, copyedRows)
        }
      })
      this.$root.eventBus.$on(event.FileManage.CUT_BTN_CLICK, () => {
        if (this.checkedRows && this.checkedRows.length > 0) {
          let copyedRows = []
          for (let checked of this.checkedRows) {
            copyedRows.push(this.$copy(checked))
          }
          this.copyForm.type = 1
          this.copyForm.oldPath = this.queryForm.path
          this.copyForm.files = copyedRows.map(item => item.name).join('||@||')
          this.$root.eventBus.$emit(event.FileManage.COPYED_CHANGE, copyedRows)
        }
      })
      this.$root.eventBus.$on(event.FileManage.PASTE_BTN_CLICK, () => {
        this.copyForm.newPath = this.queryForm.path
        copyFile(this.copyForm).then((response) => {
          this.$handleCommonResponse(response, () => {
            this.$root.eventBus.$emit(event.FileManage.COPYED_CHANGE, [])
            this.handleQuery()
          })
        })
      })
      this.$root.eventBus.$on(event.FileManage.DELETE_BTN_CLICK, () => {
        if (this.checkedRows && this.checkedRows.length > 0) {
          this.$confirm('您确定要删除选中的文件吗？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            deleteFile(this.queryForm.path, this.checkedRows.map(item => item.name).join('||@||')).then((response) => {
              this.$handleCommonResponse(response, () => {
                this.handleQuery()
              })
            })
          }).catch(() => {})
        }
      })
      this.$root.eventBus.$on(event.FileManage.CONFIRM_SELECT_BTN_CLICK, () => {
        if (this.checkedRows.length === 0) {
          this.$message({
            message: '请选择文件！',
            type: 'warning'
          })
          return
        }
        let multiple = this.$route.query.multiple || false
        if (multiple === false && this.checkedRows.length > 1) {
          this.$message({
            message: '请您一次选择一个文件！',
            type: 'warning'
          })
          return
        }
        let from = this.$route.query.from || ''
        if (from === 'ck') {
          let array = []
          for (let row of this.checkedRows) {
            array.push(row.absolutePath)
          }
          let funcNum = this.$route.query.CKEditorFuncNum || ''
          window.opener.CKEDITOR.tools.callFunction(funcNum, array)
          window.close()
        } else if (from === 'inputBtn') {
          window.opener.inputBtnCallback(this.checkedRows[0].absolutePath)
          window.close()
        }
      })
      this.$root.eventBus.$on(event.FileManage.UPLOAD_FROM_NET_BTN_CLICK, () => {
        this.uploadFromNetDialogVisible = true
      })
      this.$root.eventBus.$on(event.FileManage.UPLOAD_FROM_LOCAL_BTN_CLICK, () => {
        this.uploadFromLocalDialogVisible = true
      })
    },
    removeListeners: function () {
      this.$root.eventBus.$off(event.FileManage.CHANGE_MODE)
      this.$root.eventBus.$off(event.FileManage.CHANGE_TIMES)
      this.$root.eventBus.$off(event.FileManage.REFRESH_BTN_CLICK)
      this.$root.eventBus.$off(event.FileManage.CONFIRM_SELECT_BTN_CLICK)
      this.$root.eventBus.$off(event.FileManage.NEW_FOLDER_BTN_CLICK)
      this.$root.eventBus.$off(event.FileManage.RENAME_FILE_BTN_CLICK)
      this.$root.eventBus.$off(event.FileManage.DELETE_BTN_CLICK)
      this.$root.eventBus.$off(event.FileManage.COPY_BTN_CLICK)
      this.$root.eventBus.$off(event.FileManage.CUT_BTN_CLICK)
      this.$root.eventBus.$off(event.FileManage.PASTE_BTN_CLICK)
      this.$root.eventBus.$off(event.FileManage.UPLOAD_FROM_NET_BTN_CLICK)
      this.$root.eventBus.$off(event.FileManage.UPLOAD_FROM_LOCAL_BTN_CLICK)
    }
  },
  mounted: function () {
    this.queryForm.types = this.$route.query.types || this.fileTypes
    this.handleQuery()
    let fileManageOption = this.$getFileManageOption()
    this.mode = fileManageOption.mode
    this.times = fileManageOption.times
    this.addListeners()
  },
  destroyed: function () {
    this.removeListeners()
  }
}
</script>

<style scoped>
  .el-card {
    text-align: center;
    width: 100%;
  }
  .el-card__body {
    width: 100%;
  }
  .image {
    position: absolute;
    left: 50%;
    top: 50%;
    text-align: center;
    transform: translate(-50%,-50%);
  }
  .img-div.t1 .image {
    max-width: 80px;
    max-height: 80px;
    font-size: 80px;
  }
  .img-div.t2 .image {
    max-width: 120px;
    max-height: 120px;
    font-size: 120px;
  }
  .img-div.t3 .image {
    max-width: 200px;
    max-height: 200px;
    font-size: 200px;
  }
  .img-div {
    width: 100%;
    margin: 0 auto;
    position: relative;
  }
  .img-div.t1 {
    height: 80px;
  }
  .img-div.t2 {
    height: 120px;
  }
  .img-div.t3 {
    height: 200px;
  }
  .title {
    white-space: nowrap;
    width: 100%;
    display: inline-block;
    font-size: 12px;
  }
  .el-card {
    position: relative;
  }
  .el-card:hover {
    border: 1px solid #2489C5
  }
  .card-checkbox {
    position: absolute;
    left: 0;
    top: -1px;
  }
  .copylink-div {
    position: relative;
    height: 20px;
  }
  .copylink {
    display: none;
    cursor: pointer;
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    right: 10px;
  }
</style>
<style>
  .checked-all .el-checkbox__label{
    font-size: 12px;
    padding-left: 4px;
  }
  .uploadFromLocal .el-upload {
    display: none;
  }
  .el-table--enable-row-hover .el-table__body tr:hover .copylink {
    display: inline;
  }
  .el-card:hover .copylink {
    display: inline;
  }
</style>
