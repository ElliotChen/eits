<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>

<script type="text/javascript">
<!--
	$().ready(function() {
		$('#editForm').ajaxForm({
			target: '#main',
			beforeSubmit: function() {
			    $('#editForm').validate({ rules : {
			    	'articleId.oid' : {required:true, number:true},
					summary : {required:true},
					keywords : {required:true},
					question : {required:true},
					answer : {required:true},
					product : {required:true},
					technology : {required:true}
				} });
			    return $('#editForm').valid();
			  }
			});
		delete CKEDITOR.instances['question'];
		delete CKEDITOR.instances['answer'];
		$('#question').ckeditor();
		$('#answer').ckeditor();
		switchType();
		$('#techSelect').multiselect({beforeclose: function(){
			$('#technology').val($('#techSelect').val());
		   }, position: {
			      my: 'left bottom',
			      at: 'left top'
			   }}).multiselectfilter();
		$('#productSelect').multiselect({beforeclose: function(){
			$('#product').val($('#productSelect').val());
		   },position: {
			      my: 'left bottom',
			      at: 'left top'
			   }}).multiselectfilter();
	});
	function previewSave() {
		cloneForm('#editForm', '#editPreviewForm');
		$("#editPreviewForm").submit();
	}

	function cloneForm(sourceForm, targetForm) {
	    $(':input[name]', sourceForm).each(function() {
	        $('[name=\'' + $(this).attr('name') +'\']', targetForm).val($(this).val());
	    });
	}
	
	function cancelSave() {
		$('#editForm').val('action', '${ctx}/edit!list.action');
		$('#editForm').submit();
	}
	
	function switchType() {
		$('.ArticleType').hide();
		$('.'+$('#type').val()).show();
	}
	
	function switchSource() {
		if ('OBM' == $('input:[name="source"]:checked').val()) {
			$('#projectCode').attr('disabled', 'disabled');
			$('#level').children('option').each(function() {
				if('L3CSO' != $(this).val()) {
					$(this).removeAttr('disabled');
				}
			});
		} else {
			$('#projectCode').removeAttr('disabled');
			$('#level').children('option').each(function() {
				if('L3CSO' != $(this).val()) {
					$(this).attr('disabled', 'disabled');
				} else {
					$(this).attr('selected', 'selected');
				}
			});
		}
	}
//-->
</script>
<s:form id="editForm" namespace="/" action="edit!create" theme="simple" method="POST" enctype ="multipart/form-data">
	<s:hidden name="entryUser" />
	<table>
		<tr>
			<td>ArticleID:</td>
			<td><s:textfield name="articleId.oid" maxlength="6" size="6"/>(Draft)</td>
		</tr>
		<tr>
			<td>Language:</td>
			<td><s:select list="languages" listKey="oid" listValue="name" name="languageOid" value="language.oid"/></td>
		</tr>
		<s:if test="user.l3">
		<tr>
			<td>Source:</td>
			<td><s:radio name="source" list="@tw.com.dsc.domain.Source@values()"
					onchange="switchSource()" />
					<select id="projectCode" name="projectCode" disabled="disabled">
						<option value="proA">ProjectA</option>
						<option value="proB">ProjectB</option>
					</select>
			</td>
		</tr>
		</s:if>
		<s:else>
			<input type="hidden" name="source" value="OBM"/>
		</s:else>
		
		
		<s:if test="user.l3">
		<tr>
			<td>News:</td>
			<td><s:radio name="news" list="#{'true':'Yes','false':'No'}" />
			</td>
		</tr>
		</s:if>
		<s:else>
			<input type="hidden" name="news" value="false"/>
		</s:else>
		
		<tr>
			<td>Type:</td>
			<td><s:select id="type" name="type" list="@tw.com.dsc.domain.ArticleType@values()" listValue="%{getText('enum.ArticleType.'+toString())}" onchange="switchType();" /></td>
		</tr>
		<tr>
			<td>Summary:</td>
			<td><s:textfield name="summary" size="40" maxlength="50"/></td>
		</tr>
		<tr>
			<td>Expire after:</td>
			<td><s:select list="@tw.com.dsc.domain.ExpireType@values()" listValue="%{getText('enum.ExpireType.'+toString())}" name="expireType" /></td>
		</tr>
		<tr>
			<td>EntryDate:</td>
			<td><s:date name="entryDate" format="yyyy/MM/dd HH:mm:ss" /></td>
		</tr>
		<tr>
			<td>Keywords:</td>
			<td><s:textfield name="keywords" size="40" maxlength="50"/></td>
		</tr>
		<tr class="ArticleType SpecInfo">
			<td>Ticket ID:</td>
			<td><s:textfield name="ticketId"/></td>
		</tr>
		<tr class="ArticleType GeneralInfo SpecInfo">
			<td>Question:</td>
			<td><s:textarea id="question" name="question" cols="40" rows="4" /></td>
		</tr>
		<tr class="ArticleType GeneralInfo SpecInfo">
			<td>Answer:</td>
			<td><s:textarea id="answer" name="answer" cols="40" rows="8" /></td>
		</tr>
		<tr class="ArticleType Application TroubleShooting">
			<td>Scenario Description:</td>
			<td><s:textfield name="scenario" size="40" maxlength="50"/></td>
		</tr>
		<tr class="ArticleType Application TroubleShooting">
			<td>Setup/Step By Step Procedure:</td>
			<td><s:textfield name="step" size="40" maxlength="50"/></td>
		</tr>
		<tr class="ArticleType Application TroubleShooting">
			<td>Verification:</td>
			<td><s:textfield name="verification" size="40" maxlength="50"/></td>
		</tr>
		
		<tr class="ArticleType Issue">
			<td>Problem Description:</td>
			<td><s:textfield name="problem" size="40" maxlength="50"/></td>
		</tr>
		<tr class="ArticleType Issue">
			<td>Solution:</td>
			<td><s:textfield name="solution" size="40" maxlength="50"/></td>
		</tr>
		<tr class="ArticleType Issue">
			<td>Procedure:</td>
			<td><s:textfield name="procedure" size="40" maxlength="50"/></td>
		</tr>
		<tr>
			<td>View Level:</td>
			<td><s:select id="level" name="level" list="user.availableLevels" listValue="%{getText('enum.Level.'+toString())}" /></td>
		</tr>
		<tr>
			<td>Technology:</td>
			<td><s:textarea id="technology" name="technology" cols="40" rows="4" />
				<s:select id="techSelect" name="techSelect" list="technologies" listKey="technology" listValue="technology" multiple="true"/>
				
				
			</td>
		</tr>
		<tr>
			<td>Product:</td>
			<td><s:textarea id="product" name="product" cols="40" rows="4" />
				<select id="productSelect" name="productSelect" multiple="multiple">
					<optgroup label="ProductA">
						<option value="Model1">Model1</option>
						<option value="Model2">Model2</option>
					</optgroup>
					<optgroup label="ProductB">
						<option value="Model3">Model3</option>
						<option value="Model4">Model4</option>
					</optgroup>
				</select>
			</td>
		</tr>
		<tr>
			<td>Firmware:</td>
			<td><s:file name="upload" /> </td>
		</tr>

		<tr>
			<td>Save As:</td>
			<td>
				<s:radio name="statusAction" list="availableStatus" listValue="%{getText('create.statusAction.'+toString())}" value="'WaitForApproving'" />
			</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td><input type="button" value="Cancle" onclick="switchMenu('m3', 'edit!list.action');" />
			<input type="button" value="Preview"
				onclick="previewSave()" /> <s:submit value="Submit" cssClass="save" />
			</td>
		</tr>
	</table>
</s:form>

<s:form id="editPreviewForm" namespace="/" action="edit!previewSave" theme="simple" target="_blank">
	<input type="hidden" name="articleId.oid" />
	<input type="hidden" name="languageOid" />
	<input type="hidden" name="source" />
	<input type="hidden" name="news" />
	<input type="hidden" name="type" />
	<input type="hidden" name="summary" />
	<input type="hidden" name="expireType" />
	<input type="hidden" name="entryUser" />
	<input type="hidden" name="entryDate" />
	<input type="hidden" name="keywords" />
	<input type="hidden" name="ticketId" />
	<input type="hidden" name="question" />
	<input type="hidden" name="answer" />
	<input type="hidden" name="scenario" />
	<input type="hidden" name="step" />
	<input type="hidden" name="verification" />
	<input type="hidden" name="problem" />
	<input type="hidden" name="solution" />
	<input type="hidden" name="procedure" />
	<input type="hidden" name="level" />
	<input type="hidden" name="technology" />
	<input type="hidden" name="product" />
</s:form>