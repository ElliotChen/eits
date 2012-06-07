<%@page import="java.io.File"%>
<%@page contentType="application/vnd.ms-excel" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display-el"%>
<script language="javascript">
	function openWord() {
		Layer1.style.border = 0
		word = new ActiveXObject('Word.Application');
		word.Application.Visible = true;
		var mydoc = word.Documents.Add('', 0, 1);
		myRange = mydoc.Range(0, 1)
		var sel = Layer1.document.body.createTextRange()
		sel.select()
		Layer1.document.execCommand('Copy')
		sel.moveEnd('character')
		myRange.Paste();
		location.reload()
		word.ActiveWindow.ActivePane.View.Type = 9
	}
	
	$().ready(function() {
		openWord();
	});
</script>
<%
response.setContentType("application/vnd.ms-excel");
response.setHeader("Content-Disposition", "attachment;filename=KB.doc");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ZyXEL KB</title>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery-1.7.2.js" />"></script>
</head>
<body>
Word
<div id="exportDiv" class="condition">
	<s:iterator value="infos" var="info">
		<table class="conditionborder">
			<tr>
				<td>${info.account}</td>
			</tr>
		</table>
		<s:iterator value="#info.articles" var="article">
			<table class="conditionborder">
				<tr>
					<th>KB:<s:property value="articleId.oid" />  <s:property value="account" /></th>
				</tr>
				<tr>
					<th><s:property value="summary" /></th>
				</tr>
				<c:if test="${article.type == 'SpecInfo'}">
				<tr>
					<td>Ticket Id : <s:property value="ticketId" /></td>
				</tr>
				</c:if>
				<c:if test="${(article.type == 'GeneralInfo') || (article.type == 'SpecInfo')}">
				<tr>
					<td>Question : <s:property value="question" escapeHtml="false" />
					</td>
				</tr>
				<tr>
					<td>Answer : <s:property value="answer" escapeHtml="false" />
					</td>
				</tr>
				</c:if>
				<c:if test="${(article.type == 'Application') || (article.type == 'TroubleShooting')}">
				<tr>
					<td>Scenario : <s:property value="scenario" escapeHtml="false" />
					</td>
				</tr>
				<tr>
					<td>Step : <s:property value="step" escapeHtml="false" />
					</td>
				</tr>
				<tr>
					<td>Verification : <s:property value="verification" escapeHtml="false" />
					</td>
				</tr>
				</c:if>
				<c:if test="${article.type == 'Issue'}">
				<tr>
					<td>Problem : <s:property value="problem" escapeHtml="false" />
					</td>
				</tr>
				<tr>
					<td>Solution : <s:property value="solution" escapeHtml="false" />
					</td>
				</tr>
				<tr>
					<td>Procedure : <s:property value="procedure" escapeHtml="false" />
					</td>
				</tr>
				</c:if>
				<tr>
					<td></td>
				</tr>
			</table>
		</s:iterator>
		<table class="conditionborder" style="border-right: 2">
			<tr>
				<td colspan="2"><h3>${info.account} Total Amount = 5</h3></td>
			</tr>
			<tr><td width="50%"><h3>Expression(mature and smooth)</h3></td><td width="50%">&nbsp;</td></tr>
			<tr><td><h3>Style (fitting the nature of document)</h3></td><td></td></tr>
			<tr><td><h3>Spelling</h3></td><td></td></tr>
			<tr><td><h3>Correct use of punctuation marks</h3></td><td></td></tr>
			<tr><td><h3>Comment</h3></td><td></td></tr>
			<tr><td><h3>Avg Rate</h3></td><td></td></tr>
		</table>
		<br />
	</s:iterator>
</div>
</body>
</html>