<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<script>
	$().ready(function() {
		/*
		$('#exportNewsForm').validate({
				errorClass: "errorField",
		    	rules : {
		    		types : { required:true},
		    		beginDate : { required:true},
		    		endDate : { required:true}
		    		}
		    	});
		*/
		$('#updateProofreadForm').ajaxForm({
            target: '#exportPackageDiv',
            beforeSubmit: function() {
			    $('#updateProofreadForm').validate({ rules : {
			    	epOids : {required:true}
				},
				errorClass: "errorField" });
			    return $('#updateProofreadForm').valid();
			  }
        });		
	});
	
</script>
Waiting for Proofread
<s:form id="updateProofreadForm" namespace="/" action="news!updateProofRead" theme="simple">
<display:table name="${exportPackages.result}" partialList="true" pagesize="${exportPackages.pageSize}" size="${exportPackages.totalCount}" form="exportPackageForm" sort="external" id="ep" requestURI="news!searchExportPackage.action" style="width:100%">
	<display:column title="SELECT" headerClass="tablehead">
		<c:if test="${!ep.closed}">
		<input type="checkbox" name="epOids" value="${ep.oid}"/>
		</c:if>
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
	<display:column property="articleIdList" title="Article ID" headerClass="tablehead"/>
	<display:column title="View" headerClass="tablehead">
      <input type="button" onclick="javascript:viewExportPackage('${ep.oid}');" value="View" />
    </display:column>
</display:table>
	<s:if test="!exportPackages.result.empty">
	<s:submit value="Send Update Notification"/>
	</s:if>
</s:form>