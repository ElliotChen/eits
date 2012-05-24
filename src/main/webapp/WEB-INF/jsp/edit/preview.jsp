<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>EITS</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/smoothness/jquery-ui-1.8.18.custom.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/superfish.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/superfish-navbar.css" />" />
<!-- <link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.dataTables.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/displaytag.css" />" />-->
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.datepick.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/login.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.multiselect.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.multiselect.filter.css" />" />
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
<div id="main" class="contents viewmycart" >
<div class="condition">
<table class="conditionborder">
	<tr>
		<th>Article ID</th>
		<td>${article.articleId.oid}</td>
		<th>Language</th>
		<td>${article.language.name}</td>
	</tr>
	<tr>
		<th>Source</th>
		<td>${article.source}</td>
		<th>&nbsp;</th>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<th>ZyTech News</th>
		<td>${article.news}</td>
		<th>&nbsp;</th>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<th>Type</th>
		<td><s:property value="getText('enum.ArticleType.' + article.type)" /></td>
		<th>&nbsp;</th>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<th>Summary</th>
		<td>${article.summary}</td>
		<th>&nbsp;</th>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<th>Keywords</th>
		<td>${article.keywords}</td>
		<th>&nbsp;</th>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<th>Entry User</th>
		<td>${article.entryUser}</td>
		<th>&nbsp;</th>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<th>Entry Date</th>
		<td><fmt:formatDate value="${article.entryDate}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
		<th>&nbsp;</th>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<th>Last Update</th>
		<td><fmt:formatDate value="${updateDate}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
		<th>&nbsp;</th>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<th>Publish Date</th>
		<td><fmt:formatDate value="${publishDate}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
		<th>&nbsp;</th>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<th>View Level</th>
		<td>${level}</td>
		<th>&nbsp;</th>
		<td>&nbsp;</td>
	</tr>
</table>
	<fieldset class="conditionborder">
				<legend>Question:</legend>
				${question}
			</fieldset>
	<fieldset class="conditionborder">
				<legend>Answer:</legend>
				${answer}
			</fieldset>
<table class="conditionborder">
	<tr>
		<th>Technology:</th>
		<td><s:textarea name="technology" cols="40" rows="4" readonly="readonly"/></td>
		<th>&nbsp;</th>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<th>Product:</th>
		<td><s:textarea name="product" cols="40" rows="4" readonly="readonly"/></td>
		<th>&nbsp;</th>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<th>View</th>
		<td>${article.hitCount}</td>
		<th>&nbsp;</th>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<th>Rating</th>
		<td>${article.ratingInfo}</td>
		<th>&nbsp;</th>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td colspan="4" align="right"><input type="button" value="close" onclick="window.close()"/></td>
	</tr>
</table>
</div>
</div>
</body>
</html>


