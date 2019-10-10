window.onload=function(){
	datas=document.getElementsByClassName("alter");
//	console.log(datas);
	fun1();
}
function fun1(){
	for (var i = 0; i < datas.length; i++) {
		datas[i].onclick=function(){
//			console.log(this)
//			console.log(this.parentNode.parentNode.childNodes[3])
			var id=this.parentNode.parentNode.childNodes[3];
//			console.log(id)
			window.location.href="alter.jsp?id="+id.innerHTML;
		}  
	}
}
