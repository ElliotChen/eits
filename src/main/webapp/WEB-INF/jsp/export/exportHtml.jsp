<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
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