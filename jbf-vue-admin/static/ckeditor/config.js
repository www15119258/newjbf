/**
 * @license Copyright (c) 2003-2014, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

// eslint-disable-next-line no-undef
CKEDITOR.editorConfig = function (config) {
  config.extraPlugins = 'codesnippet,html5audio,html5video'
  config.codeSnippet_theme = 'default'
  config.format_tags = 'p;h1;h2;h3;h4;h5;h6'
  config.toolbarGroups = [
    {name: 'document', groups: ['mode', 'doctools', 'document']},
    { name: 'clipboard', groups: [ 'clipboard', 'undo' ] },
    { name: 'editing', groups: ['find', 'selection'] },
    { name: 'links' },
    { name: 'insert' },
    { name: 'others' },
    '/',
    { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
    { name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ] },
    { name: 'styles' },
    { name: 'colors' },
    { name: 'tools' },
    { name: 'about' }
  ]
  config.removeButtons = 'Save,NewPage,Print'
  // let projectName = 'http://localhost:8090'
  config.filebrowserImageUploadUrl = '/api/sys/filemanage/upload/uploadImgForCK.do'
  config.filebrowserBrowseUrl = '/admin/filemanage/FileSelect?from=ck&types=0,4'
}
