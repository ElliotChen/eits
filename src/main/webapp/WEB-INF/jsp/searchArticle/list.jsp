<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<s:if test="hasActionMessages()">
   <div>
      <s:actionmessage/>
   </div>
</s:if>

<div id="faqArticleBlock">
	<jsp:include page="faq.jsp"></jsp:include>
</div>

<div id="latestArticleBlock">
	<jsp:include page="latest.jsp"></jsp:include>
</div>