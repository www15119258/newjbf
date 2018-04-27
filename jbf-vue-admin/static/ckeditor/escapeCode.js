function escapeSHCode(str) {
	return str.replace(/[<>&"]/g,function(c){return {'<':'&lt;','>':'&gt;','&':'&amp;','"':'&quot;'}[c];});
}

function escapeSHCodeChar(str) {
	var reg = new RegExp("<[pP][rR][eE]\\sclass(.*?)=(.*?)['\"][bB][rR][uU][sS][hH](.*?)['\"].*?>([\\s\\S]*?)</[pP][rR][eE]>","g");  
	var t = null;
	while(t = reg.exec(str)){  
		str = str.replace(t[4], escapeSHCode(t[4]));
	}
	return str;
}