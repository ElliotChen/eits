<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>

<script type="text/javascript">
<!--
	$().ready(function() {
		$('#psForm').ajaxForm({
            target: '#productSelectDiv',
            success : $.unblockUI
        });
		$('#editForm').ajaxForm({
			target: '#main',
			beforeSubmit: function() {
				for (var i in CKEDITOR.instances)
		        {
		            CKEDITOR.instances[i].updateElement();
		        };
		        
			    $('#editForm').validate({ rules : {
			    	languageOid : {required:true},
					summary : {required:true},
					product : {required:true},
					technology : {required:true},
					firmware : {required:true},
					projectCode : {required:function(element) {
				        return 'Project' == $('input:[name="source"]:checked').val();
				    }},
					ticketId : {required:function(element) {
				        return $("#type").val() == 'SpecInfo';
				    }},
					question : {required:function(element) {
						return ($("#type").val() == 'GeneralInfo' || $("#type").val() == 'SpecInfo');
				    }},
					answer : {required:function(element) {
				        return ($("#type").val() == 'GeneralInfo' || $("#type").val() == 'SpecInfo');
				    }},
				    scenario : {required:function(element) {
					    return $("#type").val() == 'Application' || $("#type").val() == 'TroubleShooting';
					}},
					step : {required:function(element) {
						return $("#type").val() == 'Application' || $("#type").val() == 'TroubleShooting';
					}},
					verification : {required:function(element) {
						return $("#type").val() == 'Application' || $("#type").val() == 'TroubleShooting';
					}},
					problem : {required:function(element) {
						return $("#type").val() == 'Issue';
					}},
					solution : {required:function(element) {
						return $("#type").val() == 'Issue';
					}},
					procedure : {required:function(element) {
						return $("#type").val() == 'Issue';
					}}
				},
				ignore : [],
				errorClass: "errorField"
				});
			    return $('#editForm').valid();
			  }
			});
		
		delete CKEDITOR.instances['question'];
		delete CKEDITOR.instances['answer'];
		delete CKEDITOR.instances['scenario'];
		delete CKEDITOR.instances['step'];
		delete CKEDITOR.instances['verification'];
		delete CKEDITOR.instances['problem'];
		delete CKEDITOR.instances['solution'];
		delete CKEDITOR.instances['procedure'];
		
		$('#question').ckeditor();
		$('#answer').ckeditor();
		$('#scenario').ckeditor();
		$('#step').ckeditor();
		$('#verification').ckeditor();
		$('#problem').ckeditor();
		$('#solution').ckeditor();
		$('#procedure').ckeditor();
		switchType();
		$(".numeric").numeric({ decimal: false, negative: false });
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
			showAllModel();
		} else {
			$('#projectCode').removeAttr('disabled');
			$('#level').children('option').each(function() {
				if('L3CSO' != $(this).val()) {
					$(this).attr('disabled', 'disabled');
				} else {
					$(this).attr('selected', 'selected');
				}
			});
			switchProjectCode();
		}
	}
	function switchProjectCode() {
		$('#pCode').val($('#projectCode').val());
		$('#psForm').submit();
	}
	function showAllModel() {
		$('#pCode').val('');
		$('#psForm').submit();
	}
//-->
</script>
<s:form id="psForm" namespace="/" action="edit!listModels" theme="simple">
	<input id="pCode" type="hidden" name="example.projectCode" />
</s:form>
<jsp:include page="/WEB-INF/jsp/commons/message.jsp"></jsp:include>
<div class="condition">
<s:form id="editForm" namespace="/" action="edit!create" theme="simple" method="POST" enctype ="multipart/form-data">
	<s:hidden name="entryUser" />
	<s:hidden name="articleId.oid" />
	<s:hidden name="keywords" />
	<input type="hidden" name="comefrom" value="create" />
	<table class="conditionborder">
		<!-- 
		<tr>
			<td>ARTICLE ID:</td>
			<td><s:textfield name="articleId.oid" maxlength="6" size="6" readonly="true"/>(Draft)</td>
		</tr>
		 -->
		<tr>
			<td>LANGUAGE:</td>
			<td><s:select list="languages" listKey="oid" listValue="name" name="languageOid" value="language.oid"/></td>
		</tr>
		<s:if test="user.l3">
		<tr>
			<td>SOURCE:</td>
			<td><s:radio name="source" list="@tw.com.dsc.domain.Source@values()" onchange="switchSource()" />
				<s:select id="projectCode" name="projectCode" list="projects" disabled="true" listKey="oid" listValue="projectCode" headerKey="" headerValue="----" onchange="switchProjectCode()"></s:select>
			</td>
		</tr>
		</s:if>
		<s:else>
			<input type="hidden" name="source" value="OBM"/>
			<input type="hidden" name="projectCode" value=""/>
		</s:else>
		
		
		<s:if test="user.l3">
		<tr>
			<td>ZyTech News:</td>
			<td><s:radio name="news" list="#{'true':'Yes','false':'No'}" />
			</td>
		</tr>
		</s:if>
		<s:else>
			<input type="hidden" name="news" value="false"/>
		</s:else>
		
		<tr>
			<td>TYPE:</td>
			<td><s:select id="type" name="type" list="@tw.com.dsc.domain.ArticleType@values()" listValue="%{getText('enum.ArticleType.'+toString())}" onchange="switchType();" /></td>
		</tr>
		<tr>
			<td>SUMMARY:</td>
			<td><s:textfield name="summary" size="40" maxlength="50"/></td>
		</tr>
		<tr>
			<td>Expire after:</td>
			<td><s:select list="@tw.com.dsc.domain.ExpireType@values()" listValue="%{getText('enum.ExpireType.'+toString())}" name="expireType" /></td>
		</tr>
		<tr>
			<td colspan="2">ENTRY DATE		<s:date name="entryDate" format="yyyy/MM/dd HH:mm:ss" />　　　　LAST UPDATE		<s:date name="updateDate" format="yyyy/MM/dd HH:mm:ss" />　　　　PUBLISH DATE		<s:date name="publishDate" format="yyyy/MM/dd HH:mm:ss" /></td>
		</tr>
		<%-- 
		<tr>
			<td>KEYWORDS</td>
			<td><s:textfield name="keywords" size="40" maxlength="50"/></td>
		</tr>
		--%>
		<tr class="ArticleType SpecInfo">
			<td>eITS TICKET ID:</td>
			<td><s:textfield id="ticketId" name="ticketId" size="40" maxlength="50" cssClass="numeric"/></td>
		</tr>
		<tr class="ArticleType GeneralInfo SpecInfo">
			<td>QUESTION:</td>
			<td><s:textarea id="question" name="question" cols="40" rows="4" /></td>
		</tr>
		<tr class="ArticleType GeneralInfo SpecInfo">
			<td>ANSWER:</td>
			<td><s:textarea id="answer" name="answer" cols="40" rows="8" /></td>
		</tr>
		<tr class="ArticleType Application TroubleShooting">
			<td>SCENARIO DESCRIPTION:</td>
			<td><s:textarea id="scenario" name="scenario" cols="40" rows="4" /></td>
		</tr>
		<tr class="ArticleType Application TroubleShooting">
			<td>SETUP/STEP BY STEP PROCEDURE:</td>
			<td><s:textarea id="step" name="step" cols="40" rows="4" /></td>
		</tr>
		<tr class="ArticleType Application TroubleShooting">
			<td>VERIFICATION:</td>
			<td><s:textarea id="verification" name="verification" cols="40" rows="4" /></td>
		</tr>
		
		<tr class="ArticleType Issue">
			<td>PROBLEM DESCRIPTION:</td>
			<td><s:textarea id="problem" name="problem" cols="40" rows="4" /></td>
		</tr>
		<tr class="ArticleType Issue">
			<td>SOLUTION:</td>
			<td><s:textarea id="solution" name="solution" cols="40" rows="4" /></td>
		</tr>
		<tr class="ArticleType Issue">
			<td>CONDITION/REPRODUCE PROCEDURE:</td>
			<td><s:textarea id="procedure" name="procedure" cols="40" rows="4" /></td>
		</tr>
		<tr>
			<td>VIEW LEVEL:</td>
			<td><s:select id="level" name="level" list="user.availableLevels" listValue="%{getText('enum.Level.'+toString())}" /></td>
		</tr>
		<tr>
			<td>TECHNOLOGY:</td>
			<td><s:textarea id="technology" name="technology" cols="40" rows="4" readonly="true"/>
				<div>
				<select id="techSelect" name="techSelect" multiple="true">
					<s:iterator value="technologies" var="tech">
						<optgroup label="<s:property value="technology" />">
							<s:iterator value="#tech.items" var="item">
								<option value="<s:property value="#tech.technology" />--<s:property value="name" />"><s:property value="name" /></option>
							</s:iterator>
						</optgroup>
					</s:iterator>
				</select>
				</div>
			</td>
		</tr>
		<tr>
			<td>PRODUCT:</td>
			<td><s:textarea id="product" name="product" cols="40" rows="4" readonly="true"/>
				<div id="productSelectDiv">
				<select id="productSelect" name="productSelect" multiple="true">
					<s:iterator value="products" var="product">
						<optgroup label="<s:property value="name" />">
							<s:iterator value="#product.models" var="model">
								<option value="<s:property value="#product.name" />--<s:property value="name" />"><s:property value="name" /></option>
							</s:iterator>
						</optgroup>
					</s:iterator>
				</select>
				</div>
			</td>
		</tr>
		<tr>
			<td>FIRMWARE:</td>
			<td><s:textfield name="firmware" size="40" maxlength="50"/></td>
		</tr>

		<tr>
			<td>SAVE AS:</td>
			<td>
				<s:radio name="statusAction" list="availableStatus" listValue="%{getText('create.statusAction.'+toString())}" value="'WaitForApproving'" />
			</td>
		</tr>
		<tr>
			<td colspan="4" align="right"><input type="button" value="Cancle" onclick="switchMenu('m3', 'edit!list.action');" />
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
	<input type="hidden" name="projectCode" />
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
	<input type="hidden" name="firmware" />
</s:form>
</div>