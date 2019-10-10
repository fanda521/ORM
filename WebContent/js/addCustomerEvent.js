/**
 * 
 */
window.onload=function(){
//	alert("111");
	bt_addCus=document.getElementById("bt_event");
//	alert(bt_addCus.innerHTML);
	addCustomer();
}
function addCustomer() {
	bt_addCus.onclick=function(){
		window.location.href = 'addCustomerEvent.jsp';
//		alert(22);
	}
}