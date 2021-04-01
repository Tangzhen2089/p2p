<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">	
    <title>p2p后台登录</title>	
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
  </head>
  <body>
  
  
    <form class="form-horizontal" action="${pageContext.request.contextPath}/UserServlet?method=login" method="post" >
    	<div class="col-sm-offset-2"><h1>登录页面</h1></div>
    	<div class="col-sm-offset-2"><font color=red>${msg}</font></div>  
      <div class="form-group">
        <label class="col-sm-2 control-label">用户名</label>
        <div class="col-sm-6">
          <input type="text" class="form-control" name="username" placeholder="请输入用户名">
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-2 control-label">密码</label>
        <div class="col-sm-6">
          <input type="password" class="form-control" name="password" id="inputPassword3" placeholder="请输入密码">
        </div>
      </div>
     
      <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
          <button type="submit" class="btn btn-default">登录</button>
        </div>
      </div>
    </form>   
  </body>
</html>