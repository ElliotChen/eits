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
<link rel="stylesheet" type="text/css" href="<c:url value="/css/displaytag.css" />" />
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery-1.7.2.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery.form.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery.validate.min.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery.blockUI.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery.datepick.min.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery.dataTables.min.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/hoverIntent.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/superfish.js" />"></script>

</head>
<table>
	<tr>
		<th>Article ID</th>
		<td>${oid}</td>
		<td>Language</td>
		<td>${language.name}</td>
	</tr>
	<tr>
		<th>Type</th>
		<td>${type}</td>
		<th>&nbsp;</th>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<th>Summary</th>
		<td>${summary}</td>
		<th>&nbsp;</th>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<th>Keywords</th>
		<td>${keywords}</td>
		<th>&nbsp;</th>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<th>Entry User</th>
		<td>${entryUser}</td>
		<th>&nbsp;</th>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<th>Entry Date</th>
		<td><fmt:formatDate value="${entryDate}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
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
	<tr>
		<td colspan="4">
			<fieldset>
				<legend>Question:</legend>
				${question}
			</fieldset>
		</td>
	</tr>
	<tr>
		<td colspan="4">
			<fieldset>
				<legend>Answer:</legend>
				${answer}
			</fieldset>
		</td>
	</tr>
	<tr>
		<td>Technology:</td>
		<td><s:textarea name="technology" cols="40" rows="4" readonly="readonly"/></td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>Product:</td>
		<td><s:textarea name="product" cols="40" rows="4" readonly="readonly"/></td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td colspan="4" align="right"><input type="button" value="close" onclick="window.close()"/></td>
	</tr>
</table>

</body>
</html>

