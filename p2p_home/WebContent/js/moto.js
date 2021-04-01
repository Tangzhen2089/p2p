/**
 * 
 */
 
 $(function(){
 	//1 向p2p_management发送请求查询所有的产品信息(跨域)
 	$.getJSON("/p2p_management/ProductServlet?method=findAllProduct&callback=?",function(data){
 		var jsonObj = eval(data);
 		var html="";
 		for(var i=0;i<jsonObj.length;i++){
 			var product = jsonObj[i];
 			html += "<div class='col-sm-3 col-sm-offset-2'><div>"+product.proName+"</div><div>利率:"+product.annualized+"</div><div>期限:"+product.proLimit+"</div><div><a href='javascript:void(0)' onclick='setBuyMSg("+product.id+")'>我要购买</a></div></div>";		
 		}
 		
 		$("#product").html(html);
 	});
 });
 
 function setBuyMSg(pid){
 	$("#buyDiv").show();
 	$("#buyBtn").attr("disabled","false");
 	$("#num").val("");
 	$("#msg").html("");
 	$("#preGet").html("0");
 	$("#benjin").html("0");
 	$("#shouyi1").html("0");
 	//向服务器发送请求获得产品信息(跨域)
 	$.getJSON("/p2p_management/ProductServlet?method=findById&callback=?",{"id":pid},function(data){
 		var product = eval(data); 		
 		$("#annualized").html(product.annualized);
 		$("#canping").html("《"+product.proName+"》");
 		$("#qixian").html(product.proLimit);
 		$("#shouyi").html(product.annualized);
 		
 		//失去焦点		
 		$("#num").blur(function(){
 			//预期收益
 			var interest= $("#num").val() * product.proLimit/12 * product.annualized/100;
 			//保留两位小数
 			interest = interest.toFixed(2);
 			time(interest); 			
 		});
 		
 		//点击购买按钮,绑定前先解绑
 		$("#buyBtn").unbind("click");
 		$("#buyBtn").bind("click",function(){
 			var buyMsg={};//购买信息
 			buyMsg.pid=product.id;
 			buyMsg.money = $("#num").val();
 			$.post("/p2p_home/ProductAccountServlet?method=buy",buyMsg,function(data){
 				var jsonObj = eval(data);
 				if(jsonObj.status == 0){
 					alert(jsonObj.msg);
 				}else{
 					alert("投资成功");
 				}
 			},"json");
 		});
 	});
 }
 
 function time(interest){
 	var nu = $("#num").val();
 	if (nu == null || "" == nu){
 		$("#buyBtn").attr("disabled","false");
 		$("#msg").html("");
 		$("#benjin").html("0");
 		$("#preGet").html("0");
 		$("#shouyi1").html("0");
 	}else{
 		if(nu % 100 == 0){
 			$("#buyBtn").removeAttr("disabled");
 			$("#msg").html("");
 			$("#preGet").html(interest);
 			$("#shouyi1").html(interest);
 			$("#benjin").html($("#num").val());
 		}else{
 			$("#buyBtn").attr("disabled","false");
 			$("#msg").html("请输入100的整数倍");
 			$("#benjin").html("0");
 			$("#preGet").html("0");
 			$("#shouyi1").html("0");
			$("#num").val("");			
 		}
 	}
 }
 