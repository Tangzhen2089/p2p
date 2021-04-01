/**
 * 
 */

//注册
function registe(){
	
	var flag = checkForm();
	if(!flag){
		return;
	}	
	//获取表单中的数据	
	var formData = $("#regform").serializeJson();
	//发送请求
	$.post("/p2p_home/CustomerServlet?method=registe",formData,function(data){
		var jsonObj = eval(data);
		if(jsonObj.status == 0){
			alert(jsonObj.msg);
		}else if(jsonObj.status == 1){
			window.location.href="/p2p_home/space.html";		
		}	
	},"json");
}

//登录
function login(){	
	//获取表单中的数据	
	var formData = $("#loginform").serializeJson();
	//发送请求
	$.post("/p2p_home/CustomerServlet?method=login",formData,function(data){
		var jsonObj = eval(data);
		if(jsonObj.status == 0){
			$("#msg").html("<font color=red>"+jsonObj.msg+"</font>");
		}else if(jsonObj.status == 1){
			window.location.href="/p2p_home/space.html";		
		}	
	},"json");
}


//密码一致性校验
function checkPwdAndRepwd(){
	var f1=checkNotNull("password");
	var f2=checkNotNull("repassword");
	if(f1&&f2){
		var sPassword=document.getElementById("password").value;
		var sRepassword=document.getElementById("repassword").value;
		var oMsg = document.getElementById("repasswordMsg");
		var oDiv = document.getElementById("repasswordDiv");
		if(sPassword==sRepassword){
			return true;
		}else{
			oDiv.className+=" has-error";
			oMsg.innerHTML="必须与密码一致";
			return false;
		}
	}else{
		return false;
	}
}
			
//通用的非空校验
function checkNotNull(nid){
	var oNodex = document.getElementById(nid);
	//
	var oMsg = document.getElementById(nid+"Msg");
	//
	var oDiv = document.getElementById(nid+"Div");
	//2.对用户名进行非空判断
	var reg = /^\s*$/; //如果有0到多个空白符，则匹配为true
	if (reg.test(oNodex.value)) {
		oDiv.className += " has-error";//oDiv.className ="form-group has-error";
		oMsg.innerHTML = "不能为空";
		return false;
	} else {
		oDiv.className ="form-group";
		oMsg.innerHTML = "";
		return true;
	}
}

//用户名格式校验
function checkUsername(){
	var oNodex = document.getElementById("username");
	//
	var oMsg = document.getElementById("usernameMsg");
	//
	var oDiv = document.getElementById("usernameDiv");

	var reg = /^[a-zA-Z]{1}[a-zA-Z0-9]{3,9}$/; //用户名必须以字母开头且长度在4-10之间，则匹配为true
	if (!reg.test(oNodex.value)) {
		oDiv.className += " has-error";//oDiv.className ="form-group has-error";
		oMsg.innerHTML = "用户名必须以字母开头且长度在4-10之间";
		return false;
	} else {
		oDiv.className ="form-group";
		oMsg.innerHTML = "";
		return true;
	}
}

//邮箱格式校验
function checkEmail(){
	var oNodex = document.getElementById("email");
	//
	var oMsg = document.getElementById("emailMsg");
	//
	var oDiv = document.getElementById("emailDiv");

	var reg =/^[A-Za-z0-9]+([-_.][A-Za-z0-9]+)*@([A-Za-z0-9]+[-.])+[A-Za-z0-9]{2,5}$/ ; //邮箱正则，则匹配为true
	if (!reg.test(oNodex.value)) {
		oDiv.className += " has-error";//oDiv.className ="form-group has-error";
		oMsg.innerHTML = "邮箱格式不正确";
		return false;
	} else {
		oDiv.className ="form-group";
		oMsg.innerHTML = "";
		return true;
	}
}

//同意服务协议
function checkService(){
	var oNodex = document.getElementById("service");
	var oDiv = document.getElementById("serviceDiv");
	if ($("#service").is(':checked')){
		oDiv.className="";
		return true;
	} else {
		oDiv.className=" has-error";
		return false;
	}
}

//表单校验
function checkForm() {	
	var flag1=checkNotNull("username");
	var flag2=checkNotNull("password");
	var flag3=checkNotNull("repassword");
	var flag4=checkPwdAndRepwd(flag2,flag3);
	var flag5=checkNotNull("email");
	var flag6=false;
	if(flag1){
		flag6=checkUsername();
	}
	var flag7=false;
	if(flag5){
		flag7=checkEmail();
	}
	var flag8=false;
	var flag = flag1&&flag2&&flag3&&flag4&&flag5&&flag6;
	if(flag){
		flag8 = checkService();
//		alert(flag8);
		return flag&&flag8;
	}
	return flag;
}

//更换验证码
function changeImg(){		
		var oImg = document.getElementById("img");
		oImg.src="/p2p_home/CheckImgServlet?time="+new Date().getTime();			
}


//自定义一个将表单提交数据封装成json的插件
$.fn.extend({
	serializeJson:function(){
		var json={};
		
		//jquery中的serializeArray方法获得一个不符合要求的数据
		var msg=this.serializeArray();
		
		$(msg).each(function(i){
			if(json[this.name]){
				if(!json[this.name].push){
					json[this.name]=[json[this.name]];
				}
				json[this.name].push(this.value ||"");
			}else{
				json[this.name]=this.value || "";
			}		
		});
		return json;			
	}
});