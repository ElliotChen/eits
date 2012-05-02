<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<script>
	$().ready(function() {
		$('#searchForm').ajaxForm({
            target: '#languageDiv'
        });
		$('#displayForm').ajaxForm({
            target: '#languageDiv'
        });
		
		$('#updateForm').ajaxForm({
            target: '#main',
            beforeSubmit: function() {
			    $('#updateForm').validate(
			    	{ rules : {
						name : {
							required:true,
							remote:{
								url:'${ctx}/language!ajaxCheckDuplicate.action',
								data:{
									oid:function() { 
										return $("#moid").val(); 
									},
									name: function() { 
										return $("#mname").val(); 
										}
									}
								}
			    			}
			    		} 
			    	});
			    return $('#updateForm').valid();
			  }
        });
		
		$('#createForm').ajaxForm({
            target: '#main',
            beforeSubmit: function() {
			    $('#createForm').validate(
			    	{ rules : {
			    		oid : {required:true},
			    		name : {
							required:true,
							remote:{
								url:'${ctx}/language!ajaxCheckDuplicate.action',
								data:{
									oid:function() { 
										return $("#cmoid").val(); 
									},
									name: function() { 
										return $("#cmname").val(); 
										}
									}
								}
			    			}
			    		} 
			    	});
			    return $('#createForm').valid();
			  }
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
			$('#moid').val(oid);
			$('#updateForm').attr('action', '${ctx}/language!delete.action');
			$('#updateForm').submit();
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
	<s:form id="updateForm" namespace="/" action="language!update" theme="simple">
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
					<td><input type="text" id="moid" readonly="readonly" name="oid" /></td>
					<td><input type="text" id="mname" name="name" /></td>
					<td><s:submit value="Save" cssClass="save" /><input type="button" value="Cancel" onclick="$('#languageEditDiv').hide();"/></td>
				</tr>
			</tbody>
		</table>
	</s:form>
</div>

<s:form id="searchForm" namespace="/" action="language!search" theme="simple">
	<input type="text" name="example.oid" size="32"/>
	<input type="text" name="example.name" size="26"/>
	<s:submit value="Search" />
</s:form>

<s:form id="displayForm" namespace="/" action="language!search" theme="simple">
	<div id="distagArea" class="distagArea"></div>
</s:form>

<div id="languageDiv" class="datagrid">
	<jsp:include page="language.jsp" />
</div>