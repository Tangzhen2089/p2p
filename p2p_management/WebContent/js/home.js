/**
 * 
 */
 $(function(){
 	//页面加载完成后调用
 	findAllProduct(1);
 })
 
 function findAllProduct(pageNum){
 	var pageNo=pageNum;
	var pageSize=5;
	var totalPage = 0;
	var totalCount= 0;
	var url = '/p2p_management/ProductServlet?method=findAll';
	$.post(url,{'pageNo':pageNo,'pageSize':pageSize},function(data){
		var jsonObj = eval(data);
		pageNo=jsonObj.pageNo;//1
		pageSize=jsonObj.pageSize;//5
		totalPage = jsonObj.totalPage;//2
		totalCount= jsonObj.totalCount;//7
		content = jsonObj.content;
		$('#tab').html("<tr><td>序号</td><td>产品序号</td><td>产品名称</td><td>产品周期(月)</td><td>产品利率%</td><td>操作</td></tr>");
		$(content).each(function(i){
			var tr ="<tr><td>"+(i+1)+"</td><td>"+this.proNum+"</td><td>"+this.proName+"</td><td>"+this.proLimit+"</td><td>"+this.annualized+"</td><td><a href='javascript:void(0)' onclick=findById("+this.id+")>编辑</a></td></tr>";
			$('#tab').append($(tr));			
		});
		
		var pageMsg="<nav aria-label='Page navigation'><ul class='pagination pagination-sm'>";
		if(pageNo==1){
			pageMsg +="<li class='disabled'><a href='#' aria-label='Previous'><span aria-hidden='true'>&laquo;</span></a></li>";
		}else{
			pageMsg +="<li ><a href='#' aria-label='Previous' onclick='findAllProduct("+(pageNo-1)+")'><span aria-hidden='true'>&laquo;</span></a></li>";
		}
		for(var i=1;i<=jsonObj.totalPage;i++){
			if(i==pageNo){
				pageMsg +="<li class='active'><a href='#' onclick='findAllProduct("+i+")'>"+i+"</a></li>";
			}else{
				pageMsg +="<li><a href='#' onclick='findAllProduct("+i+")'>"+i+"</a></li>";
			}
		}
		if(pageNo==totalPage){
			pageMsg +="<li class='disabled'><a href='#' aria-label='Next'><span aria-hidden='true'>&raquo;</span></a></li></ul></nav>";
		}else{
			pageMsg +="<li ><a href='#' aria-label='Next' onclick='findAllProduct("+(pageNo+1)+")'><span aria-hidden='true'>&raquo;</span></a></li></ul></nav>";
		}		       
		$("#tab").append($(pageMsg))
		
	},'json');
 }
 
 function logout(){
 	 var flag = window.confirm("是否退出");
 	 if(flag){ 	 
 		window.location.href="/p2p_management/UserServlet?method=logout";
 	 }
 }
 


function addProduct(){

	var proNum=$('#proNum').val();
	var proName=$('#proName').val();
	var proLimit=$('#proLimit').val();
	var annualized=$('#annualized').val();
  var url ='/p2p_management/ProductServlet?method=addProduct';
  $.post(url,{'proNum':proNum,'proName':proName,'proLimit':proLimit,'annualized':annualized},function(){
  	findAllProduct($('.active').text());
  	//关闭窗口
  //	$(".form-control").val("");
  	$('#myModal').modal('hide')
  });
}

function findById(id){
	$('#myModal1').modal('show');
	$.post('/p2p_management/ProductServlet?method=findById',{'id':id},function(data){
		
		var jsonObj = eval(data);
		$('#proNum1').val(jsonObj.proNum);
		$('#proName1').val(jsonObj.proName);
		$('#proLimit1').val(jsonObj.proLimit);
		$('#annualized1').val(jsonObj.annualized);
		
		//将修改按钮解绑，再重新绑定
		$("#update").unbind('click');
		$("#update").bind('click',function(){
			var proNum=$('#proNum1').val();
			var proName=$('#proName1').val();
			var proLimit=$('#proLimit1').val();
			var annualized=$('#annualized1').val();
 			var url ='/p2p_management/ProductServlet?method=updateProduct';
  			$.post(url,{'id':id,'proNum':proNum,'proName':proName,'proLimit':proLimit,'annualized':annualized},function(){
  				findAllProduct($('.active').text());
  				//关闭窗口
  				//	$(".form-control").val("");
  				$('#myModal1').modal('hide')
  			});		
		});	
		$("#delete").click(function(){
			
			var url ='/p2p_management/ProductServlet?method=deleteProduct';
  			$.post(url,{'id':id},function(){
  				findAllProduct($('.active').text());
  				//关闭窗口
  				//	$(".form-control").val("");
  				$('#myModal1').modal('hide')
  			});		
		
		});		
	},'json');
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


 