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
		$('#deleteForm').ajaxForm({
            target: '#main'
        });
		
		$('#updateForm').validate(
		    	{
		    	submitHandler: function() {
		    		$('#updateForm').ajaxSubmit({
		                target: '#main'});
		    	},
		    	rules : {
					name : {
						required:true,
						remote:{
							url:'${ctx}/language!ajaxCheckDuplicateName.action',
							data:{
								'example.oid':function() { 
									return $("#moid").val(); 
								},
								'example.name': function() { 
									return $("#mname").val(); 
									}
								}
							}
		    			}
		    		},
		    		messages : {
		    			name : {
		    				remote : 'Language name exists.'
		    			}
		    		}
		    	});
		$('#createForm').validate(
		    	{
		    	submitHandler: function() {
		    		$('#createForm').ajaxSubmit({
		                target: '#main'});
		    	},
		    	rules : {
		    		oid : {
						required:true,
						remote:{
							url:'${ctx}/language!ajaxCheckDuplicateOid.action',
							data:{
								'example.oid':function() { 
									return $("#cmoid").val(); 
								}
							}
		    			}
		    		},
					name : {
						required:true,
						remote:{
							url:'${ctx}/language!ajaxCheckDuplicateName.action',
							data:{
								'example.oid':function() { 
									return $("#cmoid").val(); 
								},
								'example.name': function() { 
									return $("#cmname").val(); 
									}
								}
							}
		    			}
		    		},
		    		messages : {
		    			oid : {
		    				remote : 'Language ID exists.'
		    			},
		    			name : {
		    				remote : 'Language name exists.'
		    			}
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
			$('#doid').val(oid);
			$('#deleteForm').submit();
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

<div id="searchFormDiv" class="datagrid">
	<s:form id="searchForm" namespace="/" action="language!search" theme="simple">
		<table class="displayTagGrid">
			<tr>
				<td>&nbsp;</td>
				<td><input type="text" name="example.oid" size="32" /></td>
				<td><input type="text" name="example.name" size="25" /></td>
				<td><s:submit value="Search" /></td>
			</tr>
		</table>
		
	</s:form>

	<s:form id="displayForm" namespace="/" action="language!search" theme="simple">
		<div id="distagArea" class="distagArea"></div>
	</s:form>
</div>


<div id="languageDiv" class="datagrid">
	<jsp:include page="language.jsp" />
</div>
<div id="languageEditDiv" class="datagrid">
	<s:form id="updateForm" namespace="/" action="language!update" theme="simple">
		<table class="displayTagGrid">
			<thead>
				<tr>
				<th colspan="3">Edit Language</td>
				</tr>
				<tr class="tablehead">
					<th>Language ID</th>
					<th>Language</th>
					<th>Function</th>
				</tr>
			</thead>
			<tbody>
				<tr class="odd">
					<td><input type="text" id="moid" readonly="readonly" name="oid" /></td>
					<td><input type="text" id="mname" name="name" maxlength="25"/></td>
					<td><s:submit value="Save" cssClass="save"/><input type="button" value="Cancel" onclick="$('#languageEditDiv').hide();"/></td>
				</tr>
			</tbody>
		</table>
	</s:form>
	<s:form id="deleteForm" namespace="/" action="language!delete" theme="simple">
		<input type="hidden" id="doid" name="oid" />
	</s:form>
</div>

<div class="datagrid">
	<s:form id="createForm" namespace="/" action="language!create" theme="simple">
		<input type="hidden" name="system" value="false" />
		<table class="displayTagGrid">
			<tr>
				<th colspan="3">Create Language</td>
			</tr>
			<tr class="tablehead">
				<th>Language ID</td>
				<th>Language</th>
				<th>Function</th>
			</tr>
			<tr class="odd">
				<td><input type="text" id="cmoid" name="oid" maxlength="32"/></td>
				<td><input type="text" id="cmname" name="name" maxlength="25"/></td>
				<td><input type="submit" value="Create" class="save"/></td>
			</tr>
		</table>
	</s:form>
</div>