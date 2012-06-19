<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>EITS</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css" />" />
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery-1.7.2.js" />"></script>
</head>
<script>
	$().ready(function() {
		$("#zytechForm").submit();
	});
	
</script>
<body>
<form id="zytechForm" action="http://172.23.23.229/system/zytech_result3.php" method="GET">
	<s:iterator value="sels" var="sel">
		<input type="hidden" name="sel[]" value="${sel},"/>
	</s:iterator>
	<input type="submit" name="Submit" />
</form>
</body>
</html>