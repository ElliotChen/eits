<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<script>
	$().ready(function() {
		$('#publishedForm').ajaxForm({
            target: '#publishedArticleBlock'
        });
	});

</script>
<s:if test="hasActionMessages()">
   <div>
      <s:actionmessage/>
   </div>
</s:if>
<s:form id="publishedForm" namespace="/" action="searchArticle!latestL3Published" theme="simple">
	<div id="distagArea" class="distagArea"></div>
</s:form>
<h5>L3 Latest Published Articles</h5>
<div id="publishedArticleBlock" class="datagrid">
	<jsp:include page="published.jsp"></jsp:include>
</div>