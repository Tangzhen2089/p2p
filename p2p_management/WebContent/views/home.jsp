<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>p2p后台</title>
	<link href='${pageContext.request.contextPath}/css/bootstrap.min.css' rel='stylesheet'>
	<script src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
	<script src='${pageContext.request.contextPath}/js/bootstrap.min.js'></script>
	<script src="${pageContext.request.contextPath}/js/home.js"></script>
</head>
<body>
<h1>登录成功</h1>
<div class='row'> 
<div class='col-sm-1 col-sm-offset-10'>您好:${user.username}</div>
<div class='col-sm-1'><a href="javascript:void(0)" onclick="logout()">退出</a></div>

</div >
<div class='row'>  
<div class='col-sm-8 col-sm-offset-2'><!-- Button trigger modal -->
<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal"> 新增产品</button>
</div>
</div >
 <div class='row'>  
  <div class='col-sm-8 col-sm-offset-2' style={margin-top:100}>
  <table class='table table-bordered' id ='tab' >
  	<tr>
  		<td>序号</td>
  		<td>产品序号</td>
  		<td>产品名称</td>
  		<td>产品周期</td>
  		<td>产品利率</td>
  		<td>操作</td>
  	</tr>  	
  </table>
  </div>
  </div >
  



<!-- 产品提交 -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">添加产品</h4>
      </div>
      <div class="modal-body">
         <form>
          <div class="form-group">
            <label for="recipient-name" class="control-label">产品序号:</label>
            <input type="text" class="form-control" id="proNum">
          </div>
          <div class="form-group">
            <label for="recipient-name" class="control-label">产品名称:</label>
            <input type="text" class="form-control" id="proName">
          </div>
          <div class="form-group">
            <label for="recipient-name" class="control-label">产品周期:</label>
            <input type="text" class="form-control" id="proLimit">
          </div>
          <div class="form-group">
            <label for="recipient-name" class="control-label">产品利率:</label>
            <input type="text" class="form-control" id="annualized">
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" onclick="addProduct()">提交</button>
      </div>
    </div>
  </div>
</div>

<!-- 产品修改 -->
<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">修改产品</h4>
      </div>
      <div class="modal-body">
         <form>
          <div class="form-group">
            <label for="recipient-name" class="control-label">产品序号:</label>
            <input type="text" class="form-control" id="proNum1">
          </div>
          <div class="form-group">
            <label for="recipient-name" class="control-label">产品名称:</label>
            <input type="text" class="form-control" id="proName1">
          </div>
          <div class="form-group">
            <label for="recipient-name" class="control-label">产品周期:</label>
            <input type="text" class="form-control" id="proLimit1">
          </div>
          <div class="form-group">
            <label for="recipient-name" class="control-label">产品利率:</label>
            <input type="text" class="form-control" id="annualized1">
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary"  id="update">修改</button>
        <button type="button" class="btn btn-primary"  id="delete">删除</button>
      </div>
    </div>
  </div>
</div>
</body>
</html>