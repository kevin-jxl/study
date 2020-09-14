var htmlLL=document.getElementsByName("html")[0]
var whdef = 100 / 1366;
var wH = window.innerHeight;
var wW = window.innerWidth;
var rem = wW * whdef;
document.getElementsByTagName('html')[0].style.fontSize=rem + "px"
window.onload=function(){
	var whdef = 100 / 1366;
	var wH = window.innerHeight;
	var wW = window.innerWidth;
	var rem = wW * whdef;
	document.getElementsByTagName('html')[0].style.fontSize=rem + "px"
}
window.onresize=function(){
	var whdef = 100 / 1366;
	var wH = window.innerHeight;
	var wW = window.innerWidth;
	var rem = wW * whdef;
	document.getElementsByTagName('html')[0].style.fontSize=rem + "px"
}

