<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"
	errorPage="/WEB-INF/error/error.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/WEB-INF/fragment/meta/meta-manage.jspf"%>

<title>Password - <%=Application.getSetting("name")%></title>
</head>
<body class="w3-theme-dark">

	<%@include file="/WEB-INF/fragment/header.jspf"%>
	
	<article class="w3-theme-light">
		<div class="page w3-row">
		
			<%@include file="/WEB-INF/fragment/tabs/tabs.jspf"%>
		
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<h1>Password</h1>
				
				<div class="w3-row">
				<div class="w3-container w3-padding w3-col s12 m12 l6">
				
					<div class="w3-border w3-round">
						<div class="w3-margin-0 w3-padding-0 w3-theme-light w3-center">
							<h3 class="w3-margin-0 w3-padding uppercase">Password</h3>
						</div>
						<div class="w3-padding w3-small w3-theme-light">
							
							<form action="/manage/settings/password" method="post">
							<input type="hidden" name="password" value="true" />
							<p>
								<label class="w3-validate w3-text-grey-light w3-large" for="passwordOld">Current Password:&nbsp;<span class="w3-text-red">*</span></label>
								<input type="password" size="50" maxlength="300" name="passwordOld" id="passwordOld" value="" required class="w3-input w3-round-large w3-border" />
							</p>
							<p>
								<label class="w3-validate w3-text-grey-light w3-large" for="passwordNew">New Password:&nbsp;<span class="w3-text-red">*</span></label>
								<input type="password" size="50" maxlength="300" name="passwordNew" id="passwordNew" value="" required class="w3-input w3-round-large w3-border" />
								<br/>
								<span class="w3-small w3-text-grey">Must be 8 or more characters. Or just <a href="https://duckduckgo.com/?q=strong+password&t=ffsb&ia=answer" target="_blank">generate one</a>.</span>
							</p>
							<p>
								<label class="w3-validate w3-text-grey-light w3-large" for="passwordVerify">New Password (Verify):&nbsp;<span class="w3-text-red">*</span></label>
								<input type="password" size="50" maxlength="300" name="passwordVerify" id="passwordVerify" value="" required class="w3-input w3-round-large w3-border" />
							</p>
							<hr />
							<p>
								<button class="w3-btn w3-round w3-green w3-hover-teal" type="submit" value="Save" title="Save Changes">Save Changes</button>
							</p>
							</form>
						</div>
					</div>
				
				</div>
				</div>
			</div>
		</div>
	</article>
	
	<%@include file="/WEB-INF/fragment/footer.jspf"%>
</body>
</html>