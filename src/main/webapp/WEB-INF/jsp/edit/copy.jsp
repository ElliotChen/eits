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
					'language.oid' : {required:true},
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
		copyFirmware();
	}
	function copyField(fname) {
		$('[name =\''+fname+'\']', $("#copyEditForm")).val($('[name =\'sarticle.'+fname+'\']', $("#copyEditForm")).val());
	}
	function copyFirmware() {
		$('#tfn').val($('#sfn').val());
		$('#tfn').show();
		$('#copySourceFirmware').val('true');
		
		var file = $("#upload");
		file.after(file.clone().val(""));  
		file.remove();  
	}
	function discopyFirmware() {
		$('#tfn').val('');
		$('#tfn').hide();
		$('#copySourceFirmware').val('false');
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
<div class="condition">
<s:form id="copyEditForm" namespace="/" action="edit!create" theme="simple" method="POST" enctype ="multipart/form-data">
	<input type="hidden" name="sourceOid" value="${sarticle.oid}" />
	<input type="hidden" name="articleId.oid" value="${sarticle.articleId.oid}" />
	<input type="hidden" name="source" value="${sarticle.source}" />
	<input type="hidden" name="projectCode" value="${sarticle.projectCode}" />
	<input type="hidden" name="expireType" value="${sarticle.expireType}" />
	<input type="hidden" name="keywords" value="${sarticle.keywords}" />
	<input type="hidden" name="level" value="Public" />
	<table class="conditionborder">
		<tr>
			<td colspan="4" style="text-align: center;"><input type="button" value="Copy All" onclick="copyAll();" /></td>
		</tr>
		<tr>
			<td>ARTICLE ID</td>
			<td><s:textfield name="sarticle.articleId.oid" readonly="true" size="6"/></td>
			<td></td>
			<td>${sarticle.articleId.oid}</td>
		</tr>
		<tr>
			<td>LANGUAGE</td>
			<td><s:select list="languages" listKey="oid" listValue="name" name="sarticle.language.oid" disabled="true"/></td>
			<td></td>
			<td><s:select list="copyLanguages" listKey="oid" listValue="name" name="language.oid"/></td>
		</tr>
		<tr>
			<td>TYPE</td>
			<td><s:select id="stype" name="sarticle.type" list="@tw.com.dsc.domain.ArticleType@values()" listValue="%{getText('enum.ArticleType.'+toString())}" disabled="true"/></td>
			<td></td>
			<td><s:select id="type" name="type" list="@tw.com.dsc.domain.ArticleType@values()" listValue="%{getText('enum.ArticleType.'+toString())}" onchange="switchType();" value="sarticle.type"/></td>
		</tr>
		<tr>
			<td>SUMMARY</td>
			<td><s:textfield name="sarticle.summary" readonly="true" /></td>
			<td><input type="button" value="Copy >>" onclick="copyField('summary')"/></td>
			<td><s:textfield name="summary" /></td>
		</tr>
		<tr>
			<td>ENTRY DATE</td>
			<td><s:date name="sarticle.entryDate" format="yyyy/MM/dd HH:mm:ss" /></td>
			<td></td>
			<td><s:date name="entryDate" format="yyyy/MM/dd HH:mm:ss" /></td>
		</tr>
		<tr class="ArticleType SpecInfo">
			<td>Ticket ID</td>
			<td><s:textfield name="sarticle.ticketId" readonly="readonly"/></td>
			<td><input type="button" value="Copy >>" onclick="copyField('ticketId')" /></td>
			<td><s:textfield id="ticketId" name="ticketId"/></td>
		</tr>
		<tr class="ArticleType GeneralInfo SpecInfo">
			<td>QUESTION</td>
			<td><s:textarea name="sarticle.question" readonly="true" cols="40" rows="4" /></td>
			<td><input type="button" value="Copy >>" onclick="copyField('question')" /></td>
			<td><s:textarea id="question" name="question" cols="40" rows="4" /></td>
		</tr>
		<tr class="ArticleType GeneralInfo SpecInfo">
			<td>ANSWER</td>
			<td><s:textarea name="sarticle.answer" readonly="true" cols="40" rows="8" /></td>
			<td><input type="button" value="Copy >>" onclick="copyField('answer')"/></td>
			<td><s:textarea id="answer" name="answer" cols="40" rows="8" /></td>
		</tr>
		<tr class="ArticleType Application TroubleShooting">
			<td>SCENARIO DESCRIPTION</td>
			<td><s:textfield name="sarticle.scenario" size="40" maxlength="50" readonly="readonly"/></td>
			<td><input type="button" value="Copy >>" onclick="copyField('scenario')" /></td>
			<td><s:textarea id="scenario" name="scenario" cols="40" rows="4" /></td>
		</tr>
		<tr class="ArticleType Application TroubleShooting">
			<td>SETUP/STEP BY STEP PROCEDURE</td>
			<td><s:textfield name="sarticle.step" size="40" maxlength="50" readonly="readonly"/></td>
			<td><input type="button" value="Copy >>" onclick="copyField('step')" /></td>
			<td><s:textarea id="step" name="step" cols="40" rows="4" /></td>
		</tr>
		<tr class="ArticleType Application TroubleShooting">
			<td>VERIFICATION</td>
			<td><s:textfield name="sarticle.verification" size="40" maxlength="50" readonly="readonly"/></td>
			<td><input type="button" value="Copy >>" onclick="copyField('verification')" /></td>
			<td><s:textarea id="verification" name="verification" cols="40" rows="4" /></td>
		</tr>
		
		<tr class="ArticleType Issue">
			<td>PROBLEM DESCRIPTION</td>
			<td><s:textfield name="sarticle.problem" size="40" maxlength="50" readonly="readonly"/></td>
			<td><input type="button" value="Copy >>" onclick="copyField('problem')" /></td>
			<td><s:textarea id="problem" name="problem" cols="40" rows="4" /></td>
		</tr>
		<tr class="ArticleType Issue">
			<td>SOLUTION</td>
			<td><s:textfield name="sarticle.solution" size="40" maxlength="50" readonly="readonly"/></td>
			<td><input type="button" value="Copy >>" onclick="copyField('solution')" /></td>
			<td><s:textarea id="solution" name="solution" cols="40" rows="4" /></td>
		</tr>
		<tr class="ArticleType Issue">
			<td>CONDITION/REPRODUCE PROCEDURE</td>
			<td><s:textfield name="sarticle.procedure" size="40" maxlength="50" readonly="readonly"/></td>
			<td><input type="button" value="Copy >>" onclick="copyField('procedure')" /></td>
			<td><s:textarea id="procedure" name="procedure" cols="40" rows="4" /></td>
		</tr>
		<tr>
			<td>TECHNOLOGY</td>
			<td><s:textarea name="sarticle.technology" readonly="true" cols="40" rows="4" /></td>
			<td><input type="button" value="Copy >>" onclick="copyField('technology')"/></td>
			<td><s:textarea id="technology" name="technology" cols="40" rows="4" />
				<select id="techSelect" name="techSelect" multiple="true">
					<s:iterator value="technologies" var="tech">
						<optgroup label="<s:property value="technology" />">
							<s:iterator value="#tech.items" var="item">
								<option value="<s:property value="#tech.technology" />-<s:property value="name" />"><s:property value="name" /></option>
							</s:iterator>
						</optgroup>
					</s:iterator>
				</select>
			</td>
		</tr>
		<tr>
			<td>PRODUCT</td>
			<td><s:textarea name="sarticle.product" readonly="true" cols="40" rows="4" /></td>
			<td><input type="button" value="Copy >>" onclick="copyField('product')"/></td>
			<td><s:textarea id="product" name="product" cols="40" rows="4" />
				<select id="productSelect" name="productSelect" multiple="true">
					<s:iterator value="products" var="product">
						<optgroup label="<s:property value="name" />">
							<s:iterator value="#product.models" var="model">
								<option value="<s:property value="#product.name" />-<s:property value="name" />"><s:property value="name" /></option>
							</s:iterator>
						</optgroup>
					</s:iterator>
				</select>
			</td>
		</tr>
		<tr>
			<td>FIRMWARE</td>
			<td>
				<s:textfield id="sfn" name="sarticle.firmware.name" readonly="true" />
				
			</td>
			<td><input type="button" value="Copy >>" onclick="copyFirmware()"/></td>
			<td><input type="text" id="tfn" name="targetFirmware" readonly="readonly" hidden="true"/><s:file id="upload" name="upload" onchange="discopyFirmware()"/><input type="hidden" id="copySourceFirmware" name="copySourceFirmware" value="false"/></td>
		</tr>
		<!-- 
		<tr>
			<td>View Level:</td>
			<td><s:select id="slevel" name="sarticle.level" list="user.availableLevels" listValue="%{getText('enum.Level.'+toString())}" value="sarticle.level" disabled="true"/></td>
			<td><input type="button" value="Copy >>" onclick="copyField('level')"/></td>
			<td><s:select id="level" name="level" list="user.availableLevels" listValue="%{getText('enum.Level.'+toString())}" value="sarticle.level"/></td>
		</tr>
		 -->
		<tr>
			<td>SAVE AS</td>
			<td></td>
			<td></td>
			<td><s:radio name="statusAction" list="availableStatus" listValue="%{getText('create.statusAction.'+toString())}" value="'WaitForApproving'" /></td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td>
				<!-- <input type="button" value="Cancel" onclick="viewArticle('${oid}')"/> -->
				<input type="button" value="Preview" onclick="previewCopy()" /> 
				<s:submit value="Submit" cssClass="save"/>
			</td>
		</tr>
	</table>
</s:form>

<s:form id="previewForm" namespace="/" action="edit!previewSave" theme="simple" target="_blank">
	<input type="hidden" name="sourceOid" value="${sarticle.oid}" />
	<input type="hidden" name="articleId.oid" />
	<input type="hidden" name="language.oid" />
	<input type="hidden" name="source" />
	<input type="hidden" name="news" />
	<input type="hidden" name="type" />
	<input type="hidden" name="summary" />
	<input type="hidden" name="expireType" />
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
	<input type="hidden" name="copySourceFirmware"/>
	<input type="hidden" name="targetFirmware"/>
</s:form>
</div>