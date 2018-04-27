import Vue from 'vue'
import Router from 'vue-router'
import _import from './_import'

Vue.use(Router)

const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/admin/login',
      name: 'Login',
      component: _import('admin/security/Login')
    },
    {
      path: '/admin',
      name: 'AdminIndex',
      component: _import('admin/Index'),
      redirect: '/admin/dashboard',
      children: [
        {
          path: '/admin/dashboard',
          name: 'Dashboard',
          component: _import('admin/Dashboard')
        },
        {
          path: '/admin/403',
          name: 'admin403',
          component: _import('admin/error/403')
        },
        {
          path: '/admin/config/dict',
          name: 'DictManage',
          meta: {perm: 'jbf.config.dict.view'},
          component: _import('admin/config/dict/Index')
        },
        {
          path: '/admin/config/option',
          name: 'OptionManage',
          meta: {perm: 'jbf.config.option.view'},
          component: _import('admin/config/option/Index')
        },
        {
          path: '/admin/security/user',
          name: 'UserManage',
          component: _import('admin/security/user/Index')
        },
        {
          path: '/admin/security/role',
          name: 'RoleManage',
          component: _import('admin/security/role/Index')
        },
        {
          path: '/admin/security/menu',
          name: 'MenuManage',
          component: _import('admin/security/menu/Index')
        },
        {
          path: '/admin/security/module',
          name: 'ModuleManage',
          component: _import('admin/security/module/Index')
        },
        {
          path: '/admin/security/perm',
          name: 'PermManage',
          component: _import('admin/security/perm/Index')
        },
        {
          path: '/admin/cms/category',
          name: 'CategoryManage',
          meta: {perm: 'jbf.cms.category.view'},
          component: _import('admin/cms/category/Index')
        },
        {
          path: '/admin/cms/post',
          name: 'PostManage',
          meta: {perm: 'jbf.cms.post.view'},
          component: _import('admin/cms/post/Index')
        },
        {
          path: '/admin/cms/post/add',
          name: 'AddPost',
          meta: {perm: 'jbf.cms.post.add'},
          component: _import('admin/cms/post/AddPost')
        },
        {
          path: '/admin/cms/post/edit/:id',
          name: 'EditPost',
          meta: {perm: 'jbf.cms.post.edit'},
          component: _import('admin/cms/post/EditPost')
        },
        {
          path: '/admin/cms/tagcloud',
          name: 'TagcloudManage',
          meta: {perm: 'jbf.cms.tagcloud.view'},
          component: _import('admin/cms/tagcloud/Index')
        },
        {
          path: '/admin/cms/spage',
          name: 'SpageManage',
          meta: {perm: 'jbf.cms.spage.view'},
          component: _import('admin/cms/spage/Index')
        },
        {
          path: '/admin/cms/spage/add',
          name: 'AddSpage',
          meta: {perm: 'jbf.cms.spage.add'},
          component: _import('admin/cms/spage/AddSpage')
        },
        {
          path: '/admin/cms/spage/edit/:id',
          name: 'EditSpage',
          meta: {perm: 'jbf.cms.spage.edit'},
          component: _import('admin/cms/spage/EditSpage')
        },
        {
          path: '/admin/cms/comment',
          name: 'CommentManage',
          meta: {perm: 'jbf.cms.comment.view'},
          component: _import('admin/cms/comment/Index')
        },
        {
          path: '/admin/cms/friendLink',
          name: 'FriendLinkManage',
          meta: {perm: 'jbf.cms.friendLink.view'},
          component: _import('admin/cms/friendLink/Index')
        },
        {
          path: '/admin/cms/advertisement',
          name: 'AdvertisementManage',
          meta: {perm: 'jbf.cms.advertisement.view'},
          component: _import('admin/cms/advertisement/Index')
        },
        {
          path: '/admin/cms/aggregation',
          name: 'AggregationManage',
          meta: {perm: 'jbf.cms.aggregation.view'},
          component: _import('admin/cms/aggregation/Index')
        },
        {
          path: '/admin/filemanage/file',
          name: 'FileManage',
          component: _import('admin/filemanage/Index')
        },
        {
          path: '/admin/filemanage/imagefile',
          name: 'ImageFileManage',
          component: _import('admin/filemanage/ImageIndex')
        },
        {
          path: '/admin/filemanage/moviefile',
          name: 'MovieFileManage',
          component: _import('admin/filemanage/MovieIndex')
        },
        {
          path: '/admin/filemanage/soundfile',
          name: 'SoundFileManage',
          component: _import('admin/filemanage/SoundIndex')
        },
        {
          path: '/admin/filemanage/zipfile',
          name: 'ZipFileManage',
          component: _import('admin/filemanage/ZipIndex')
        },
        {
          path: '/admin/statistics/visitlog',
          name: 'VisitLogManage',
          component: _import('admin/statistics/visitlog/Index')
        }
      ]
    },
    {
      path: '/admin/filemanage/fileselect',
      name: 'FileSelect',
      component: _import('admin/filemanage/FileSelect')
    }
  ]
})

export default router
