<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>EITS</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/superfish.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/superfish-navbar.css" />" />
<!-- <link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.dataTables.css" />" />-->
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.datepick.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css" />" />
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery-1.7.2.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery.form.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery.validate.min.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery.blockUI.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery.datepick.min.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery.dataTables.min.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/hoverIntent.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/superfish.js" />"></script>

</head>
<body>
<display:table name="${articleLogs}" id="log">
	<display:caption>Article Logs</display:caption>
	<display:column title="Create Date" headerClass="tablehead">
		<fmt:formatDate value="${log.createdDate}" pattern="yyyy/MM/dd"/>
	</display:column>
	<display:column property="action" title="Action" headerClass="tablehead"/>
	<display:column property="account" title="User" headerClass="tablehead"/>
	<display:column property="message" title="Message" headerClass="tablehead"/>
	<display:column property="ip" title="IP" headerClass="tablehead"/>
</display:table>
</body>
</html>


