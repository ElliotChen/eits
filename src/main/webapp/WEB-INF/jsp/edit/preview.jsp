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
<script type="text/javascript">
<!--
	$().ready(function() {
		switchType();

	});
	
	function switchType() {
		$('.ArticleType').hide();
		$('.${type}').show();
	}
	
//-->
</script>
</head>
<body>
<div id="main" class="contents viewmycart" >
<div class="condition">
<table class="conditionborder">
	<tr>
		<th>ARTICLE ID:</th>
		<td>${article.articleId.oid}</td>
		<th>Available Language:</select></th>
		<td><select><option value="${article.language.oid}">${article.language.name}</option></td>
	</tr>
	<tr>
		<th>SOURCE:</th>
		<td colspan="3">${article.source}</td>
	</tr>
	<tr>
		<th>ZyTech News:</th>
		<td colspan="3">${article.news}</td>
	</tr>
	<tr>
		<th>TYPE:</th>
		<td colspan="3"><s:property value="getText('enum.ArticleType.' + article.type)" /></td>
	</tr>
	<tr>
		<th>SUMMARY:</th>
		<td colspan="3">${article.summary}</td>
	</tr>
	<tr>
		<th>KEYWORDS:</th>
		<td colspan="3">${article.keywords}</td>
	</tr>
	<tr>
		<th>ENTRY User:</th>
		<td colspan="3">${article.entryUser}</td>
	</tr>
	<tr>
		<th>ENTRY DATE:</th>
		<td colspan="3"><fmt:formatDate value="${article.entryDate}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
	</tr>
	<tr>
		<th>LAST UPDATE:</th>
		<td colspan="3"><fmt:formatDate value="${updateDate}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
	</tr>
	<tr>
		<th>PUBLISH DATE:</th>
		<td colspan="3"><fmt:formatDate value="${publishDate}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
	</tr>
	<tr>
		<th>VIEW LEVEL</th>
		<td colspan="3">${level}</td>
	</tr>

	<tr class="ArticleType SpecInfo">
			<th>eITS TICKET ID:</th>
			<td colspan="3">${ticketId}</td>
		</tr>
		<tr class="ArticleType GeneralInfo SpecInfo">
			<th>QUESTION:</th>
			<td colspan="3">${question}</td>
		</tr>
		<tr class="ArticleType GeneralInfo SpecInfo">
			<th>ANSWER:</th>
			<td colspan="3">${answer}</td>
		</tr>
		<tr class="ArticleType Application TroubleShooting">
			<th>SCENARIO DESCRIPTION:</th>
			<td colspan="3">${scenario}</td>
		</tr>
		<tr class="ArticleType Application TroubleShooting">
			<th>SETUP/STEP BY STEP PROCEDURE:</th>
			<td colspan="3">${step}</td>
		</tr>
		<tr class="ArticleType Application TroubleShooting">
			<th>VERIFICATION:</th>
			<td colspan="3">${verification}</td>
		</tr>
		
		<tr class="ArticleType Issue">
			<th>PROBLEM DESCRIPTION:</th>
			<td colspan="3">${problem}</td>
		</tr>
		<tr class="ArticleType Issue">
			<th>SOLUTION:</th>
			<td colspan="3">${solution}</td>
		</tr>
		<tr class="ArticleType Issue">
			<th>CONDITION/REPRODUCE PROCEDURE:</th>
			<td colspan="3">${procedure}</td>
		</tr>

	<tr>
		<th>TECHNOLOGY:</th>
		<td colspan="3"><s:textarea name="technology" cols="60" rows="8" readonly="true"/></td>
	</tr>
	<tr>
		<th>PRODUCT:</th>
		<td colspan="3"><s:textarea name="product" cols="60" rows="8" readonly="true"/></td>
	</tr>
	<tr>
		<th>FIRMWARE:</th>
		<td colspan="3">
			<input type="text" name="targetFirmware" value="${targetFirmware}" readonly="readonly" />
		</td>
	</tr>
	<tr>
		<th>VIEWS:</th>
		<td colspan="3">${article.hitCount}</td>
	</tr>
	<tr>
		<th>RATING:</th>
		<td colspan="3">${article.ratingInfo}</td>
	</tr>
	<tr>
		<td colspan="4" align="right"><input type="button" value="close" onclick="window.close()"/></td>
	</tr>
</table>
</div>
</div>
</body>
</html>


