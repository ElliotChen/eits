<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<script>
	$().ready(function() {
			
	});
	
</script>
ZyTech News
<s:form id="gennewForm" namespace="/" action="news!gennews" target="_blank" theme="simple">
<display:table name="${closedPackages.result}" partialList="true" pagesize="${closedPackages.pageSize}" size="${closedPackages.totalCount}" form="closedPackageForm" sort="external" id="ep" requestURI="news!searchClosedPackage.action" style="width:100%">
	<display:column title="SELECT" headerClass="tablehead">
		<input type="checkbox" name="epOids" value="${ep.oid}"/>
	</display:column>
	<display:column property="oid" title="ID" headerClass="tablehead"/>
	<display:column title="ZyTech News" headerClass="tablehead">
		<c:if test="${ep.news}">Yes</c:if>
		<c:if test="${!ep.news}">No</c:if>
	</display:column>
	<display:column title="Export Date" headerClass="tablehead">
		<fmt:formatDate value="${ep.exportDate}" pattern="yyyy/MM/dd"/>
	</display:column>
	<display:column title="Duration" headerClass="tablehead">
		<fmt:formatDate value="${ep.beginDate}" pattern="yyyy/MM/dd"/>~<fmt:formatDate value="${ep.endDate}" pattern="yyyy/MM/dd"/>
	</display:column>
	<display:column property="articleIdList" style="width:100px" title="Article ID" headerClass="tablehead"/>
	<display:column property="newsIdList" title="Firmware No" headerClass="tablehead"/>
	<display:column title="View" headerClass="tablehead">
      <input type="button" onclick="viewPackageStatus('${ep.oid}');" value="View Article" />
      <c:if test="${not empty ep.newsIdList}">
      <input type="button" onclick="viewFirmwareStatus('${ep.oid}');" value="View Firmware" />
      </c:if>
    </display:column>
</display:table>
	<s:if test="!closedPackages.result.empty">
	<s:submit value="Gen ZyTech News"/>
	</s:if>
</s:form>
