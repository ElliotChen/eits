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
</head>
<body>
<display:table name="${packagedArticle}" id="pa">
	<display:caption>Article Status</display:caption>
	<display:column property="articleId" title="Article ID" headerClass="tablehead">
	</display:column>
	<display:column property="leader" title="Leader" headerClass="tablehead"/>
	<display:column property="summary" title="Summary" headerClass="tablehead"/>
	<display:column property="agent" title="Engineer" headerClass="tablehead"/>
	<display:column property="status" title="Status" headerClass="tablehead"/>
</display:table>
</body>
</html>