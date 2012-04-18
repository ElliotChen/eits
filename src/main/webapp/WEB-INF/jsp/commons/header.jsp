<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<div id="loginDiv" style="margin-left: 500px;">
	<s:if test="@tw.com.dsc.util.ThreadLocalHolder@getUser().guest">
			Hi! <s:property value="@tw.com.dsc.util.ThreadLocalHolder@getUser().name" />
		<a href="#login-box" class="login-window">Login</a>
		<div id="login-box" class="login-popup">
			<a href="#" class="close"><img src="images/close_pop.png" class="btn_close" title="Close Window" alt="Close" /></a>
			<form method="post" class="signin" action="${ctx}/system!login.action">
				<fieldset class="textbox">
					<label class="username"> <span>Username or email</span> <input id="username" name="user.account" value=""
						type="text" autocomplete="on" placeholder="Username">
					</label> <label class="password"> <span>Password</span> <input id="password" name="user.password" value=""
						type="password" placeholder="Password">
					</label> <input class="submit button" type="submit" value="Login" />
				</fieldset>
			</form>
		</div>
	</s:if>
	<s:elseif test="!@tw.com.dsc.util.ThreadLocalHolder@getUser().guest">
		<a href="#" onclick="switchMenu('m3', 'edit!list.action');">Home</a>
		<a href="${ctx}/system!logout.action">Logout</a>
		<br />
			Welcome! <s:property value="@tw.com.dsc.util.ThreadLocalHolder@getUser().account" />
		<br />
			Article#:<input type="text" id="quickOid" name="quickOid" size="6" maxlength="6" />
		<input type="button" onclick="quickViewArticle();" value="Go" />
		<br />
			Role:<select name="role">
			<option value="L3Admin">L3Admin</option>
			<option value="L2Admin">L2Admin</option>
		</select>
	</s:elseif>
</div>