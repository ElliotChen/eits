<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ZyXEL KB</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/smoothness/jquery-ui-1.8.18.custom.css" />" />
<!-- <link rel="stylesheet" type="text/css" href="<c:url value="/css/superfish.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/superfish-navbar.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.dataTables.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/displaytag.css" />" />-->
<link rel="stylesheet" type="text/css" href="<c:url value="/css/newMenu.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.datepick.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/login.css" />" />
<!-- 
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.multiselect.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.multiselect.filter.css" />" />
 -->
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery-1.7.2.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery.form.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery.validate.min.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery.blockUI.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery.datepick.min.js" />"></script>
<!-- <script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery.dataTables.min.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/hoverIntent.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/superfish.js" />"></script> -->
<script>
	$().ready(function() {
		$('#exportNewsForm').ajaxForm({
            target: '#exportNewsFormDiv'
        });
	});
	
</script>
</head>
<body>
<div id="main" class="contents viewmycart" >
<div class="condition">
<div id="exportNewsFormDiv">
<s:if test="infos.empty">
Sorry, there is no article to export.
</s:if>
<s:elseif test="exportable">
<s:form id="exportNewsForm" namespace="/" action="news!export" theme="simple" target="_blank">
	<s:hidden name="news" />
	<s:hidden name="beginDate" />
	<s:hidden name="endDate" />
	<s:iterator value="types" var="type">
		<input type="hidden" name="types" value="${type}" ?>
	</s:iterator>
	
	<s:submit value="Export for Proofread" />
</s:form>
</s:elseif>
</div>
<div id="exportResDiv">
</div>
<div id="exportDiv" class="condition" style="width: 600px">
	${label}
	<s:iterator value="infos" var="info">
		<table class="conditionborder" border="2">
			<tr>
				<td align="center" colspan="2">${info.name} (${info.account})</td>
			</tr>
		<s:iterator value="#info.articles" var="article">
				<tr>
					<td colspan="2"><b>KB:<s:property value="articleId.oid" />,&nbsp;&nbsp;${info.name}</b></td>
				</tr>
				<tr>
					<td colspan="2"><b><s:property value="summary" /></b></td>
				</tr>
				<c:if test="${article.type == 'SpecInfo'}">
				<tr>
					<td colspan="2">Ticket Id : <s:property value="ticketId" /></td>
				</tr>
				</c:if>
				<c:if test="${(article.type == 'GeneralInfo') || (article.type == 'SpecInfo')}">
				<tr>
					<td colspan="2">Question : <s:property value="question" escapeHtml="false" />
					</td>
				</tr>
				<tr>
					<td colspan="2">Answer : <s:property value="answer" escapeHtml="false" />
					</td>
				</tr>
				</c:if>
				<c:if test="${(article.type == 'Application') || (article.type == 'TroubleShooting')}">
				<tr>
					<td colspan="2">Scenario : <s:property value="scenario" escapeHtml="false" />
					</td>
				</tr>
				<tr>
					<td colspan="2">Step : <s:property value="step" escapeHtml="false" />
					</td>
				</tr>
				<tr>
					<td colspan="2">Verification : <s:property value="verification" escapeHtml="false" />
					</td>
				</tr>
				</c:if>
				<c:if test="${article.type == 'Issue'}">
				<tr>
					<td colspan="2">Problem : <s:property value="problem" escapeHtml="false" />
					</td>
				</tr>
				<tr>
					<td colspan="2">Solution : <s:property value="solution" escapeHtml="false" />
					</td>
				</tr>
				<tr>
					<td colspan="2">Procedure : <s:property value="procedure" escapeHtml="false" />
					</td>
				</tr>
				</c:if>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
		</s:iterator>
			<tr>
				<td colspan="2"><h4>${info.name} Total Amount = ${info.size}</h4></td>
			</tr>
			<tr><td width="50%"><h4>Expression(mature and smooth)</h4></td><td width="50%">&nbsp;</td></tr>
			<tr><td><h4>Style (fitting the nature of document)</h4></td><td></td></tr>
			<tr><td><h4>Spelling</h4></td><td></td></tr>
			<tr><td><h4>Correct use of punctuation marks</h4></td><td></td></tr>
			<tr><td><h4>Comment</h4></td><td></td></tr>
			<tr><td><h4>Avg Rate</h4></td><td></td></tr>
		</table>
		<br />
	</s:iterator>
</div>
</div>
</div>
</body>
</html>


