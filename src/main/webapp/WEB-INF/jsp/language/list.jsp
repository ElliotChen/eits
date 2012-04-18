<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<script>
	$().ready(function() {
		$('#searchLanguageForm').ajaxForm({
            target: '#languageDiv'
        });
		$('#languageDisForm').ajaxForm({
            target: '#languageDiv'
        });
		
		$('#languageForm').ajaxForm({
            target: '#main'
        });
		
		$('#languageEditDiv').hide();
	});
	
	function editLanguage(oid, name) {
		$('#moid').val(oid);
		$('#mname').val(name);
		$('#languageEditDiv').show();
	};
	
	function deleteLanguage(oid) {
		if (confirm('Delete ?')) {
			$('#oid').val(oid);
			$('#languageForm').attr('action', '${ctx}/language!delete.action');
			$('#languageForm').submit();
		} else {
			//return false;
		}
	};
</script>
<s:if test="hasActionMessages()">
   <div>
      <s:actionmessage/>
   </div>
</s:if>
<div id="languageEditDiv">
	<s:form id="languageForm" namespace="/" action="language!save" theme="simple">
		<table>
			<thead>
				<tr>
					<th>Language ID</th>
					<th>Language</th>
					<th>Function</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><input type="text" id="moid" name="model.oid" /></td>
					<td><input type="text" id="mname" name="model.name" /></td>
					<td><s:submit value="Save" cssClass="save" /> </td>
				</tr>
			</tbody>
		</table>
	</s:form>
</div>

<s:form id="searchLanguageForm" namespace="/" action="language!search" theme="simple">
	<input type="text" name="example.oid" size="32"/>
	<input type="text" name="example.name" size="26"/>
	<s:submit value="Search" />
</s:form>

<s:form id="languageDisForm" namespace="/" action="language!search" theme="simple">
	<div id="distagArea" class="distagArea"></div>
</s:form>

<div id="languageDiv">
	<jsp:include page="language.jsp" />
</div>