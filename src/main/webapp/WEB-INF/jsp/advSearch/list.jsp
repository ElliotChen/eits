<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<script>
	$().ready(function() {
		$('#advExportForm').ajaxForm({
            target: '#articleBlock'
        });
	});

</script>

<s:form id="advExportForm" namespace="/" action="advSearchArticle!search" theme="simple">
	<div id="distagArea" class="distagArea"></div>
</s:form>
<div id="articleBlock" class="datagrid">
<h5>KBs</h5>
<display:table name="${articles}" export="true" form="advExportForm" id="article" requestURI="advSearchArticle!search.action">
	<display:column property="articleId.oid" title="ID" headerClass="tablehead"/>
	<display:column property="summary" title="Summary" headerClass="tablehead"/>
	<display:column property="formattedSeries" title="Prod. Series" headerClass="tablehead"/>
	<display:column title="Date" headerClass="tablehead">
		<fmt:formatDate value="${article.entryDate}" pattern="yyyy/MM/dd"/>
	</display:column>
	<display:column property="entryUser" title="Agent" headerClass="tablehead"/>
	<display:column property="i18nStatus" title="Status" headerClass="tablehead"/>
	<display:column property="type" title="Type" headerClass="tablehead"/>
	<display:column property="level" title="View Level" headerClass="tablehead"/>
	<display:column property="hitCount" title="Views" headerClass="tablehead"/>
	<display:column property="ratingInfo" title="Rating" headerClass="tablehead"/>
	<display:column title="Function" headerClass="tablehead">
      <input type="button" onclick="javascript:previewArticle('${article.oid}');" value="Preview" />
      <input type="button" onclick="javascript:editArticle('${article.oid}');" value="Edit"/>
    </display:column>
</display:table>
</div>
