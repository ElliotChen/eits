<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<s:if test="hasActionMessages()">
   <div>
      <s:actionmessage/>
   </div>
</s:if>

<div id="faqArticleBlock" class="datagrid">
	<jsp:include page="faq.jsp"></jsp:include>
</div>

<div id="latestArticleBlock" class="datagrid">
	<jsp:include page="latest.jsp"></jsp:include>
</div>