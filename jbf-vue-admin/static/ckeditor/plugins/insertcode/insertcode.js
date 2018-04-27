CKEDITOR.dialog.add('Insertcode', function(editor){
    var escape = function(value){return value;};
    return{
        title: '插入代码',
        resizable: CKEDITOR.DIALOG_RESIZE_BOTH,
        minWidth: 700,
        minHeight: 550,
        contents: [{
            id: 'cb',
            name: 'cb',
            label: 'cb',
            title: 'cb',
            elements: [{
                type: 'select',
                label: 'Language',
                id: 'lang',
                required: true,
                'default': 'java',
                items: [['Java', 'java'],['ActionScript3', 'as3'], ['JavaScript', 'js'], ['XML', 'xml']]
                }, {
                    type: 'textarea',
                    style: 'width:700px;height:450px',
                    label: 'Code',
                    id: 'code',
                    rows: 31,
                    'default': ''
                }]
            }],
        onOk: function(){
            code = this.getValueOf('cb', 'code');
            lang = this.getValueOf('cb', 'lang');
            html = '' + escape(code) + '';
			//html = html2Escape(html);
            editor.insertHtml("<pre class=\"brush:" + lang + ";\">" + html + "</pre>");  
        },  
        onLoad: function(){}
    };
});

function html2Escape(sHtml) {
 return sHtml.replace(/[<>&"]/g,function(c){return {'<':'&lt;','>':'&gt;','&':'&amp;','"':'&quot;'}[c];});
}