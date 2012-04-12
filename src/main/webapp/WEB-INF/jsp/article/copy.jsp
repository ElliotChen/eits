<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<s:form id="copyForm" namespace="/" action="article!copy" theme="simple">
<table>
	<tr><td colspan="4" align="center"><input type="button" value="Copy All" onclick="copyall();"/></td></tr>
	<tr>
		<td>ArticleID:</td>
		<td><s:textfield name="article.id" readonly="true" /></td>
		<td></td>
		<td>${article.id}</td>
	</tr>
	<tr>
		<td>Language:</td>
		<td><s:select list="{'English', 'Chinese'}" value="article.language" /></td>
		<td></td>
		<td><s:select name="copy.language" list="{'English', 'Chinese'}" headerKey="-1" headerValue="--Select--" emptyOption="------" /></td>
	</tr>
	<tr>
		<td>Type:</td>
		<td><s:select list="{'General', 'Chinese'}" value="article.language" /></td>
		<td></td>
		<td><s:select name="copy.language" list="{'English', 'Chinese'}" headerKey="-1" headerValue="--Select--" emptyOption="------" /></td>
	</tr>
</table>
</s:form>