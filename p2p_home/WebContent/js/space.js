/**
 * 
 */
 
 $(function(){
 	//向服务器发送请求来获取账户信息
	$.post("/p2p_home/AccountServlet?method=findByCustomer",function(data){
		var jsonObj = eval(data);
		if(jsonObj.status == 0){
			location.href = "/p2p_home/login.jsp";
		}else{
			$("#total").html(jsonObj.content.total);
			$("#balance").html(jsonObj.content.balance);
			$("#interest").html(jsonObj.content.interest);
			
			//邮箱认证
			if(jsonObj.content.customer.email_status==1){
			$("#emailspan").attr("class","glyphicon glyphicon-ok-circle");
			$("#isIdentified").html("已认证");
			}else{
			$("#emailspan").attr("class","glyphicon glyphicon-info-sign");
			$("#isIdentified").html("未认证");
			}
		}		
	},"json");
	
	$("#identify").bind("click",function(){
 		 $("#emailidentify").slideToggle();
	});
	
	$("#query").bind("click",function(){
		 $("#queryInformation").toggle(); 

 		 $("#tbody").html("<tr class='row' style='font-weight:thick;border-bottom:solid; height: 30px '><td >投资编号</td><td >产品名称</td><td >期限</td><td >年化利率</td><td>预期收益</td><td>客户名称</td><td>投资时间</td><td>投资本金</td><td>是否到期</td></tr>");		
		
		$.post("/p2p_home/ProductAccountServlet?method=findByCustomer",function(data){
			
			var jsonObj = eval(data);
			if(jsonObj.status == 0){
				alert(jsonObj.msg);
			}else{
				var pa = jsonObj.content.productAccount;
				var html = "";
				for(var i = 0 ;i<pa.length;i++){
					html ="<tr class='row' style=' height: 20px '><td>"+pa[i].pa_num+"</td><td >"+pa[i].product.proName+"</td><td >"+pa[i].product.proLimit+"</td><td >"+pa[i].product.annualized+"</td><td>"+pa[i].interest+"</td><td>"+pa[i].customer.c_name+"</td><td>"+(new Date(pa[i].pa_date).toLocaleDateString())+"</td><td>"+pa[i].money+"</td><td>"+(pa[i].status==0?"否":"是")+"</td></tr>";
					$("#tbody").append(html);
				}
				$("#s1").html(jsonObj.content.account.total);
				$("#s2").html(jsonObj.content.account.balance);
				$("#s3").html(jsonObj.content.account.interest);
				
			}			
		},"json");
	});
	
	
	$("#emailspan").html("");
});



function emailId(){
	//向服务器发送请求获得邮箱地址
	$.post("/p2p_home/CustomerServlet?method=findCustomer",function(data){
		var jsonObj = eval(data);

		if(jsonObj.status == 0){
			alert("邮箱已认证");
		}else{
			$("#emailAddr").val(jsonObj.content.email);
			
			$("#myModal").modal('show');
			
			$("#emailSend").click(function(){				
				$.post("/p2p_home/CustomerServlet?method=sendEmail",{"email":jsonObj.content.email},function(data){
					var json = eval(data);
					if(json.status==0){
						alert(json.msg);
					}else{
						alert("邮件发送成功，请查阅邮箱，尽快认证");
					}					
				},"json");							
			});
		}		
	},"json");
}

function emailCheck(){
	var emailAddr = $("#emailAddr").val();
	var emailCheckCode = $("#emailCheckCode").val();
	
	$.post("/p2p_home/CustomerServlet?method=checkEmail",{"emailAddr":emailAddr,"emailCheckCode":emailCheckCode},function(data){
		var jsonObj = eval(data);
		if(jsonObj.status==0){
			alert(jsonObj.msg);
		}else{
			alert(jsonObj.msg);
			$("#emailspan").attr("class","glyphicon glyphicon-ok-circle");
			$("#isIdentified").html("已认证");
			$("#myModal").modal('hide');
		}
	
	},"json");
	
	
	
}


