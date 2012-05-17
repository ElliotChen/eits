<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<s:if test="hasActionMessages()">
   <div>
      <s:actionmessage/>
   </div>
</s:if>

<s:form id="faqForm" namespace="/" action="searchArticle!faq" theme="simple">
	<div id="distagArea" class="distagArea"></div>
</s:form>
<div id="faqArticleBlock" class="datagrid">
	<jsp:include page="faq.jsp"></jsp:include>
</div>

<s:form id="latestForm" namespace="/" action="searchArticle!latest" theme="simple">
	<div id="distagArea" class="distagArea"></div>
</s:form>
<div id="latestArticleBlock" class="datagrid">
	<jsp:include page="latest.jsp"></jsp:include>
</div>