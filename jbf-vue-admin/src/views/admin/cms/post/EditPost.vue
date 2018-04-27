<template>
  <div v-loading.fullscreen.lock="loading">
    <el-row :gutter='20'>
      <el-col :span='24' class='breadcrumb-container'>
        <strong class='title'>编辑文章</strong>
        <el-breadcrumb separator='/' class='breadcrumb-inner'>
          <el-breadcrumb-item>
            文章内容管理
          </el-breadcrumb-item>
          <el-breadcrumb-item>
            文章管理
          </el-breadcrumb-item>
          <el-breadcrumb-item>
            编辑文章
          </el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>
    <div class='mt20'></div>
    <el-row :gutter='20'>
      <el-col :span='24'>
        <el-form :model='form' ref='form' :rules='rules' label-position="top">
          <el-row :gutter='20'>
            <el-col :span="18">
              <el-form-item label='名称：' labelWidth='100px' prop='title'>
                <el-input v-model='form.title'></el-input>
              </el-form-item>
              <el-form-item label='固定链接：' labelWidth='100px' prop='fixedLink'>
                <el-input v-model='form.fixedLink'></el-input>
              </el-form-item>
              <el-form-item label='内容：' labelWidth='100px' prop='content'>
                <el-input id='content' type='textarea' v-model='form.content'></el-input>
              </el-form-item>
              <el-form-item label='摘要：' labelWidth='100px' prop='summary'>
                <el-input id='summary' type='textarea' v-model='form.summary'></el-input>
              </el-form-item>
              <el-row :gutter="10">
                <el-col :span="12">
                  <el-form-item label='标签：' labelWidth='100px' prop='tagclouds'>
                    <tagcloud v-model='form.tagclouds' :readonly="false"></tagcloud>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label='最近使用标签：' labelWidth='100px'>
                    <tagcloud v-model='recentTagclouds' @click="tagcloudClick"></tagcloud>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter='10'>
                <el-col :span="6">
                  <el-form-item label='浏览：' labelWidth='100px' prop='viewCount'>
                    <el-input type="number" v-model='form.viewCount'></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label='评论：' labelWidth='100px' prop='commentCount'>
                    <el-input type="number" v-model='form.commentCount'></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label='赞：' labelWidth='100px' prop='supportCount'>
                    <el-input type="number" v-model='form.supportCount'></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label='收藏：' labelWidth='100px' prop='collectionCount'>
                    <el-input type="number" v-model='form.collectionCount'></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-col>
            <el-col :span="6">
              <el-form-item label="状态：" labelWidth="100px" prop="status">
                <el-select v-model="form.status" placeholder="请选择" style="width:100%;">
                  <el-option label="暂存" value="0"></el-option>
                  <el-option label="发布" value="1"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label='发布时间：' labelWidth='100px' prop='publishDate'>
                <el-date-picker v-model="form.publishDate" type="datetime" placeholder="yyyy-MM-dd HH:mm:ss" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss" style="width:100%"></el-date-picker>
              </el-form-item>
              <el-form-item label='分类：' labelWidth='100px' prop='categorys'>
                <ui-category-checked-tree v-model="form.categorys"></ui-category-checked-tree>
              </el-form-item>
              <el-form-item label='主题图：' labelWidth='100px' prop='pic'>
                <input-file-select :preview="true" height="200px" v-model='form.pic'></input-file-select>
              </el-form-item>
              <el-form-item label="文章来源：" labelWidth="100px" prop="sourceFrom">
                <el-select v-model="form.sourceFrom" placeholder="请选择" style="width:100%;">
                  <el-option label="原创" value="0"></el-option>
                  <el-option label="转载" value="1"></el-option>
                </el-select>
              </el-form-item>
              <el-row :gutter="10">
                <el-col :span="12">
                  <el-form-item label="是否允许评论：" labelWidth="100px" prop="commentEnable">
                    <el-switch v-model="form.commentEnable" activeValue="1" inactiveValue="0">
                    </el-switch>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="是否显示评论：" labelWidth="100px" prop="commentShow">
                    <el-switch v-model="form.commentShow" activeValue="1" inactiveValue="0">
                    </el-switch>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="10">
                <el-col :span="12">
                  <el-form-item label="是否置顶：" labelWidth="100px" prop="isTop">
                    <el-switch v-model="form.isTop" >
                    </el-switch>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="置顶级别：" labelWidth="100px" prop="topLevel">
                    <el-input type="number" v-model='form.topLevel' :disabled="!form.isTop"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-col>
          </el-row>
          <el-form-item label=''>
            <el-button type='primary' @click='goBack'>取消</el-button>
            <el-button type='primary' @click='goSubmit'>确定</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import CKEDITOR from 'CKEDITOR'
import UiCategoryCheckedTree from '../category/UiCategoryCheckedTree.vue'
import Tagcloud from '../tagcloud/Tagcloud.vue'
import { updatePost, queryPostById } from '../api/post.js'
import { queryTagcloud } from '../api/tagcloud.js'
import InputFileSelect from '@/views/admin/filemanage/InputFileSelect.vue'

export default {
  components: {
    CKEDITOR,
    UiCategoryCheckedTree,
    Tagcloud,
    InputFileSelect
  },
  props: {
    randomTitle: {
      type: [String],
      default: '' + new Date().getTime()
    }
  },
  data () {
    return {
      loading: false,
      recentTagclouds: [],
      form: {
        id: '',
        title: '',
        summary: '',
        content: '',
        fixedLink: '',
        tagclouds: [],
        categorys: [],
        pic: '',
        sourceFrom: '0',
        commentEnable: '1',
        commentShow: '1',
        isTop: false,
        topLevel: null,
        viewCount: 0,
        commentCount: 0,
        supportCount: 0,
        collectionCount: 0,
        status: '0',
        publishDate: ''
      },
      rules: {
        title: [
          { required: true, message: '标题不能为空', trigger: 'blur' },
          { min: 1, max: 200, message: '标题的长度必须在1到200之间', trigger: 'blur' }
        ],
        fixedLink: [
          { min: 0, max: 200, message: '固定链接的长度不能超过200', trigger: 'blur' }
        ],
        pic: [
          { min: 0, max: 2000, message: '图片的长度不能超过2000', trigger: 'blur' }
        ],
        sourceFrom: [
          { required: true, message: '文章来源不能为空', trigger: 'blur' }
        ],
        commentEnable: [
          { required: true, message: '是否允许评论不能为空', trigger: 'blur' }
        ],
        commentShow: [
          { required: true, message: '是否显示评论不能为空', trigger: 'blur' }
        ],
        isTop: [
          { required: true, message: '是否置顶不能为空', trigger: 'blur' }
        ],
        status: [
          { required: true, message: '状态不能为空', trigger: 'blur' }
        ],
        categorys: [
          { required: true, message: '分类不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    goBack: function () {
      this.$router.back()
    },
    goSubmit: function () {
      this.form.content = CKEDITOR.instances['content'].getData()
      this.form.summary = CKEDITOR.instances['summary'].getData()
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.loading = true
          updatePost(this.form).then((response) => {
            this.loading = false
            if (response && response.data && response.data.status === 'success') {
              this.$alert('操作成功，返回列表页面！', '提示', {
                confirmButtonText: '确定',
                callback: () => {
                  this.$router.back()
                }
              })
            } else if (response && response.data && response.data.status !== 'success') {
              this.$message({
                message: response.data.message,
                type: 'error'
              })
            } else {
              this.$message({
                message: '系统错误',
                type: 'error'
              })
            }
          })
        }
      })
    },
    tagcloudClick: function (tag) {
      if (this.form.tagclouds.indexOf(tag) < 0) {
        this.form.tagclouds.push(tag)
      }
    }
  },
  mounted: function () {
    let tagcloudForm = {
      sort: '',
      _query_total: 0,
      _query_page: 1,
      _query_size: 20,
      _query_order: 'lastUse desc'
    }
    queryTagcloud(tagcloudForm).then((response) => {
      if (response && response.data && response.data.status === 'success') {
        this.recentTagclouds = response.data.data.content.map(item => item.name)
      }
    })
    let postid = this.$route.params.id
    queryPostById(postid).then((response) => {
      if (response && response.data && response.data.status === 'success') {
        this.form.id = response.data.data.id
        this.form.title = response.data.data.title
        this.form.summary = response.data.data.summary
        this.form.content = response.data.data.content
        this.form.fixedLink = response.data.data.fixedLink
        this.form.categorys = response.data.data.categoryList.map(item => item.id)
        this.form.tagclouds = response.data.data.tagcloudList.map(item => item.name)
        this.form.pic = response.data.data.pic
        this.form.sourceFrom = response.data.data.sourceFrom
        this.form.commentEnable = response.data.data.commentEnable
        this.form.commentShow = response.data.data.commentShow
        this.form.isTop = response.data.data.isTop
        this.form.topLevel = response.data.data.topLevel
        this.form.viewCount = response.data.data.viewCount
        this.form.commentCount = response.data.data.commentCount
        this.form.supportCount = response.data.data.supportCount
        this.form.collectionCount = response.data.data.collectionCount
        this.form.status = response.data.data.status
        this.form.publishDate = response.data.data.publishDate
        CKEDITOR.replace('content')
        CKEDITOR.replace('summary')
      }
    })
  },
  destroyed: function () {
  }
}
</script>

<style scoped>
</style>
