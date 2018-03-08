<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/script/jquery.js"></script>
<title>Insert title here</title>
</head>
<body>
	HELLO MY FRIEND!
	<script type="text/javascript">
	$.ajax({
		type:"POST",
		url:"${pageContext.request.contextPath}/toAjax",
		data:{
			"id": "001",
			"name": "超级哈哈镜"
			},
		dataType:"json",
		success:function(data){
			console.log(data);
		},
		error:function(data) {
			console.log(data);
		}
	});
	</script>
</body>
</html>