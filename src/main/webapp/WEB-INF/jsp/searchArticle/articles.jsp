<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
Frequently Viewed KBs
<table id="faqArticles">
	<thead>
		<tr>
			<td>ID</td>
			<td>Summary</td>
			<td>Publish Date</td>
			<td>Hitcount</td>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>19031</td>
			<td>Summary 1</td>
			<td>2010/12/11</td>
			<td>291</td>
		</tr>
		<tr>
			<td>17451</td>
			<td>Summary 2</td>
			<td>2011/12/11</td>
			<td>171</td>
		</tr>
	</tbody>
</table>
<br/>
<br/>
<br/>

Latest Articles:
<table id="latestArticles">
	<thead>
		<tr>
			<td>ID</td>
			<td>Summary</td>
			<td>Publish Date</td>
			<td>Hitcount</td>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>19031</td>
			<td>Summary 1</td>
			<td>2010/12/11</td>
			<td>291</td>
		</tr>
		<tr>
			<td>17451</td>
			<td>Summary 2</td>
			<td>2011/12/11</td>
			<td>171</td>
		</tr>
		<tr>
			<td>16451</td>
			<td>Summary 3</td>
			<td>2011/12/11</td>
			<td>191</td>
		</tr>
		<tr>
			<td>17751</td>
			<td>Summary 4</td>
			<td>2011/12/11</td>
			<td>173</td>
		</tr>
		<tr>
			<td>17451</td>
			<td>Summary 5</td>
			<td>2011/12/11</td>
			<td>161</td>
		</tr>
		<tr>
			<td>18451</td>
			<td>Summary 6</td>
			<td>2011/12/11</td>
			<td>182</td>
		</tr>
		<tr>
			<td>19451</td>
			<td>Summary 7</td>
			<td>2011/12/11</td>
			<td>191</td>
		</tr>
		<tr>
			<td>27451</td>
			<td>Summary 8</td>
			<td>2011/12/11</td>
			<td>143</td>
		</tr>
		<tr>
			<td>17481</td>
			<td>Summary 9</td>
			<td>2011/12/11</td>
			<td>151</td>
		</tr>
		<tr>
			<td>17441</td>
			<td>Summary 10</td>
			<td>2011/12/11</td>
			<td>162</td>
		</tr>
		<tr>
			<td>17459</td>
			<td>Summary 11</td>
			<td>2011/12/11</td>
			<td>132</td>
		</tr>
		<tr>
			<td>17751</td>
			<td>Summary 12</td>
			<td>2011/12/11</td>
			<td>161</td>
		</tr>
		<tr>
			<td>17851</td>
			<td>Summary 13</td>
			<td>2011/12/11</td>
			<td>145</td>
		</tr>
		
	</tbody>
</table>
<script type="text/javascript">
	var disTable;
	$().ready(function() {
		disTable = $("#faqArticles").dataTable({"bFilter" : false, "bPaginate" : false});
		disTable = $("#latestArticles").dataTable({
			"sPaginationType" : "full_numbers",
			"bFilter" : false});
	});
</script>