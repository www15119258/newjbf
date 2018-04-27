<template>
  <div v-loading.fullscreen.lock="loading">
    <el-row :gutter='20'>
      <el-col :span='24' class='breadcrumb-container'>
        <strong class='title'>新建页面</strong>
        <el-breadcrumb separator='/' class='breadcrumb-inner'>
          <el-breadcrumb-item>
            文章内容管理
          </el-breadcrumb-item>
          <el-breadcrumb-item>
            页面管理
          </el-breadcrumb-item>
          <el-breadcrumb-item>
            新建页面
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
              <el-form-item label='模板：' labelWidth='100px' prop='template'>
                <el-input v-model='form.template'></el-input>
              </el-form-item>
              <el-form-item label='主题图：' labelWidth='100px' prop='pic'>
                <el-input v-model='form.pic'></el-input>
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
import { addSpage } from '../api/spage.js'

export default {
  components: {
    CKEDITOR
  },
  props: {
  },
  data () {
    return {
      loading: false,
      form: {
        title: '新建页面',
        summary: '',
        content: '',
        fixedLink: '',
        pic: '',
        template: '',
        commentEnable: '1',
        commentShow: '1',
        viewCount: 0,
        commentCount: 0,
        supportCount: 0,
        collectionCount: 0,
        status: '0'
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
          { min: 0, max: 200, message: '图片的长度不能超过200', trigger: 'blur' }
        ],
        commentEnable: [
          { required: true, message: '是否允许评论不能为空', trigger: 'blur' }
        ],
        commentShow: [
          { required: true, message: '是否显示评论不能为空', trigger: 'blur' }
        ],
        status: [
          { required: true, message: '状态不能为空', trigger: 'blur' }
        ],
        template: [
          { min: 0, max: 20, message: '模板的长度不能超过20', trigger: 'blur' }
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
          addSpage(this.form).then((response) => {
            this.loading = false
            this.$handleCommonResponse(response, () => {
              this.$alert('操作成功，返回列表页面！', '提示', {
                confirmButtonText: '确定',
                callback: () => {
                  this.$router.back()
                }
              })
            })
          })
        }
      })
    }
  },
  mounted: function () {
    CKEDITOR.replace('content')
    CKEDITOR.replace('summary')
  },
  destroyed: function () {
  }
}
</script>

<style scoped>
</style>
