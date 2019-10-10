/**
 * 
 */
window.onload=function(){
//	alert("111");
	bt_addCus=document.getElementById("bt_addCus");
//	alert(bt_addCus.innerHTML);
	datas=document.getElementsByClassName("alter");
//	console.log(datas);
	fun1();
	addCustomer();
}
function addCustomer() {
	bt_addCus.onclick=function(){
		window.location.href = "addCustomer.jsp";
//		alert(22);
	}
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