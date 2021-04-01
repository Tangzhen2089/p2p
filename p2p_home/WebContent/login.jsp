<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">	
    <title>登录页面</title>	
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/customer.js"></script>
  </head>
  <body>    
    <form class="form-horizontal" id="loginform" >
    	<div class="col-sm-offset-2"><h1>用户登录</h1></div>
    	<div class="col-sm-offset-2" id="msg"></div>  
      <div class="form-group">
        <label class="col-sm-2 control-label">用户名</label>
        <div class="col-sm-6">
          <input type="text" class="form-control" name="c_name" placeholder="请输入用户名或者邮箱" value=${cookie.remember.value }>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-2 control-label">密码</label>
        <div class="col-sm-6">
          <input type="password" class="form-control" name="password" placeholder="请输入密码">
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-2 control-label">验证码</label>
        <div class="col-sm-3">
          <input type="text" class="form-control" name="checkcode" placeholder="请输入验证码">          
        </div>
        <label class="  control-label" style="text-align: left;"><img src="/p2p_home/CheckImgServlet" id="img" onclick="changeImg()"/><a href=# onclick="changeImg()">看不清，换一张</a></label>
      </div>
      <div  id="serviceDiv" >
	      <div class="col-sm-offset-2 col-sm-2 checkbox">
	          <label>
	            	<input type="checkbox" name="remeber">记住用户名
	          </label>
	      </div>
	      <label class=" col-sm-offset-3 control-label">还没账号?前去<a href="/p2p_home/register.html">注册</a></label>
		</div>	    
      <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
          <input type="button" class="btn btn-danger" onclick="login()" value="立即登录"/>
        </div>
      </div>
    </form>   
  </body>
</html>
