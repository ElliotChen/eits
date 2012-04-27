<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<script type="text/javascript">
<!--
	$().ready(function() {
		
		$('#copyEditForm').ajaxForm({
        	target: '#main',
        	beforeSubmit: function() {
			    $('#copyEditForm').validate({ rules : {
					id : {required:true, number:true},
					summary : {required:true},
					keywords : {required:true},
					question : {required:true},
					answer : {required:true},
					product : {required:true},
					technology : {required:true}
				} });
			    return $('#copyEditForm').valid();
			  }
    	});
		
		delete CKEDITOR.instances['question'];
		delete CKEDITOR.instances['answer'];
		$('#question').ckeditor();
		$('#answer').ckeditor();
		switchType();
		$('#techSelect').multiselect({beforeclose: function(){
			$('#technology').val($('#techSelect').val());
		   },position: {
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
	function previewCopy() {
		cloneForm('#copyEditForm', '#previewForm');
		$("#previewForm").submit();
	}

	function cloneForm(sourceForm, targetForm) {
	    $(':input[name]', sourceForm).each(function() {
	        $('[name=\'' + $(this).attr('name') +'\']', targetForm).val($(this).val());
	    });
	}
	function copyAll() {
		copyField('summary');
		copyField('language.oid');
		copyField('type');
		copyField('question');
		copyField('answer');
		copyField('ticketId');
		copyField('scenario');
		copyField('step');
		copyField('verification');
		copyField('problem');
		copyField('solution');
		copyField('procedure');
		copyField('technology');
		copyField('product');
		copyField('firmware');
		copyField('level');
	}
	function copyField(fname) {
		$('[name =\''+fname+'\']', $("#copyEditForm")).val($('[name =\'sarticle.'+fname+'\']', $("#copyEditForm")).val());
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
<s:form id="copyEditForm" namespace="/" action="edit!create" theme="simple" method="POST" enctype ="multipart/form-data">
	<input type="hidden" name="sourceOid" value="${sarticle.oid}" />
	<input type="hidden" name="articleIdOid" value="${sarticle.articleId.oid}" />
	<input type="hidden" name="source" value="${sarticle.source}" />
	<input type="hidden" name="projectCode" value="${sarticle.projectCode}" />
	<input type="hidden" name="expireType" value="${sarticle.expireType}" />
	<input type="hidden" name="keywords" value="${sarticle.keywords}" />
	<table>
		<tr>
			<td colspan="4" style="text-align: center;"><input type="button" value="Copy All" onclick="copyAll();" /></td>
		</tr>
		<tr>
			<td>ArticleID:</td>
			<td><s:textfield name="sarticle.articleId.oid" readonly="true" size="6"/></td>
			<td></td>
			<td>${sarticle.articleId.oid}</td>
		</tr>
		<tr>
			<td>Language:</td>
			<td><s:select list="languages" listKey="oid" listValue="name" name="sarticle.language.oid" disabled="true"/></td>
			<td></td>
			<td><s:select list="languages" listKey="oid" listValue="name" name="language.oid"/></td>
		</tr>
		<tr>
			<td>Type:</td>
			<td><s:select id="stype" name="sarticle.type" list="@tw.com.dsc.domain.ArticleType@values()" listValue="%{getText('enum.ArticleType.'+toString())}" disabled="true"/></td>
			<td></td>
			<td><s:select id="type" name="type" list="@tw.com.dsc.domain.ArticleType@values()" listValue="%{getText('enum.ArticleType.'+toString())}" onchange="switchType();" value="sarticle.type"/></td>
		</tr>
		<tr>
			<td>Summary:</td>
			<td><s:textfield name="sarticle.summary" readonly="true" /></td>
			<td><input type="button" value="Copy >>" onclick="copyField('summary')"/></td>
			<td><s:textfield name="summary" /></td>
		</tr>
		<tr>
			<td>EntryDate:</td>
			<td><s:date name="sarticle.entryDate" format="yyyy/MM/dd HH:mm:ss" /></td>
			<td></td>
			<td><s:date name="entryDate" format="yyyy/MM/dd HH:mm:ss" /></td>
		</tr>
		<tr class="ArticleType SpecInfo">
			<td>Ticket ID:</td>
			<td><s:textfield name="sarticle.ticketId" readonly="readonly"/></td>
			<td><input type="button" value="Copy >>" onclick="copyField('ticketId')" /></td>
			<td><textfield id="ticketId" name="ticketId"/></td>
		</tr>
		<tr class="ArticleType GeneralInfo SpecInfo">
			<td>Question:</td>
			<td><s:textarea name="sarticle.question" readonly="true" cols="40" rows="4" /></td>
			<td><input type="button" value="Copy >>" onclick="copyField('question')" /></td>
			<td><s:textarea id="question" name="question" cols="40" rows="4" /></td>
		</tr>
		<tr class="ArticleType GeneralInfo SpecInfo">
			<td>Answer:</td>
			<td><s:textarea name="sarticle.answer" readonly="true" cols="40" rows="8" /></td>
			<td><input type="button" value="Copy >>" onclick="copyField('answer')"/></td>
			<td><s:textarea id="answer" name="answer" cols="40" rows="8" /></td>
		</tr>
		<tr class="ArticleType Application TroubleShooting">
			<td>Scenario Description:</td>
			<td><s:textfield name="sarticle.scenario" size="40" maxlength="50" readonly="readonly"/></td>
			<td><input type="button" value="Copy >>" onclick="copyField('scenario')" /></td>
			<td><s:textarea id="scenario" name="scenario" cols="40" rows="4" /></td>
		</tr>
		<tr class="ArticleType Application TroubleShooting">
			<td>Setup/Step By Step Procedure:</td>
			<td><s:textfield name="sarticle.step" size="40" maxlength="50" readonly="readonly"/></td>
			<td><input type="button" value="Copy >>" onclick="copyField('step')" /></td>
			<td><s:textarea id="step" name="step" cols="40" rows="4" /></td>
		</tr>
		<tr class="ArticleType Application TroubleShooting">
			<td>Verification:</td>
			<td><s:textfield name="sarticle.verification" size="40" maxlength="50" readonly="readonly"/></td>
			<td><input type="button" value="Copy >>" onclick="copyField('verification')" /></td>
			<td><s:textarea id="verification" name="verification" cols="40" rows="4" /></td>
		</tr>
		
		<tr class="ArticleType Issue">
			<td>Problem Description:</td>
			<td><s:textfield name="sarticle.problem" size="40" maxlength="50" readonly="readonly"/></td>
			<td><input type="button" value="Copy >>" onclick="copyField('problem')" /></td>
			<td><s:textarea id="problem" name="problem" cols="40" rows="4" /></td>
		</tr>
		<tr class="ArticleType Issue">
			<td>Solution:</td>
			<td><s:textfield name="sarticle.solution" size="40" maxlength="50" readonly="readonly"/></td>
			<td><input type="button" value="Copy >>" onclick="copyField('solution')" /></td>
			<td><s:textarea id="solution" name="solution" cols="40" rows="4" /></td>
		</tr>
		<tr class="ArticleType Issue">
			<td>Procedure:</td>
			<td><s:textfield name="sarticle.procedure" size="40" maxlength="50" readonly="readonly"/></td>
			<td><input type="button" value="Copy >>" onclick="copyField('procedure')" /></td>
			<td><s:textarea id="procedure" name="procedure" cols="40" rows="4" /></td>
		</tr>
		<tr>
			<td>Technology:</td>
			<td><s:textarea name="sarticle.technology" readonly="true" cols="40" rows="4" /></td>
			<td><input type="button" value="Copy >>" onclick="copyField('technology')"/></td>
			<td><s:textarea id="technology" name="technology" cols="40" rows="4" />
				<select id="techSelect" name="techSelect" multiple="multiple">
						<option value="Tech1">Tech1</option>
						<option value="Tech2">Tech2</option>
						<option value="Tech3">Tech3</option>
						<option value="Tech4">Tech4</option>
				</select></td>
		</tr>
		<tr>
			<td>Product:</td>
			<td><s:textarea name="sarticle.product" readonly="true" cols="40" rows="4" /></td>
			<td><input type="button" value="Copy >>" onclick="copyField('product')"/></td>
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
			<td><s:textfield name="sarticle.firmware" readonly="true" /></td>
			<td><input type="button" value="Copy >>" onclick="copyField('firmware')"/></td>
			<td><s:textfield name="firmware" /></td>
		</tr>
		<tr>
			<td>View Level:</td>
			<td><s:select id="slevel" name="sarticle.level" list="user.availableLevels" listValue="%{getText('enum.Level.'+toString())}" value="sarticle.level" disabled="true"/></td>
			<td><input type="button" value="Copy >>" onclick="copyField('level')"/></td>
			<td><s:select id="level" name="level" list="user.availableLevels" listValue="%{getText('enum.Level.'+toString())}" value="sarticle.level"/></td>
		</tr>
		<tr>
			<td>Save As:</td>
			<td></td>
			<td></td>
			<td><s:radio name="statusAction" list="availableStatus" listValue="%{getText('create.statuAction.'+toString())}" value="'WaitForApproving'" /></td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td>
				<input type="button" value="Cancel" onclick="viewArticle('${oid}')"/>
				<input type="button" value="Preview" onclick="previewCopy()" /> 
				<s:submit value="Submit" cssClass="save"/>
			</td>
		</tr>
	</table>
</s:form>

<s:form id="previewForm" namespace="/" action="article!previewCopy" theme="simple" target="_blank">
	<input type="hidden" name="language" />
	<input type="hidden" name="type" />
	<input type="hidden" name="summary" />
	<input type="hidden" name="question" />
	<input type="hidden" name="answer" />
	<input type="hidden" name="technology" />
	<input type="hidden" name="product" />
	<input type="hidden" name="firmware" />
	<input type="hidden" name="level" />
	<input type="hidden" name="state" />
</s:form>