<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>New Author - RamblingWare</title>
<!-- META_BEGIN -->
<%@include file="/WEB-INF/fragment/meta-manage.jspf"%>
<script>
function makeUri() {
	var title = document.getElementById('name').value;
	title = title.replace(/[^0-9a-z]/gi, ' ');
	title = title.replace(/\s+/g, '-').toLowerCase();
	document.getElementById('uriName').value = title;
}
</script>
<!-- META_END -->
</head>
<body class="w3-theme-dark">

	<!-- HEADER_BEGIN -->
	<%@include file="/WEB-INF/fragment/header.jspf"%>
	<!-- HEADER_END -->
	
	<article class="w3-theme-light">
		<div class="page w3-row">
		
			<!-- TABS_BEGIN -->
			<%@include file="/WEB-INF/fragment/tabs.jspf"%>
			<!-- TABS_END -->
		
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<h1>Add New Author</h1>
		
				<!-- REGISTER BEGIN -->
				<div class="w3-container w3-padding-0 w3-border-0">
					<form action="/manage/newuser" method="post">
					<input type="hidden" name="submitForm" value="true" />
					
					<p>
						<label class="w3-validate w3-text-grey-light w3-large" for="name">Full Name:&nbsp;<span class="w3-text-red">*</span></label>
						<input type="text" size="50" maxlength="200" name="name" id="name" value="<s:property value="name" />" onkeyup="makeUri()" required placeholder="John Doe" class="w3-input w3-round-large w3-border" />
					</p>
					<p>
						<label class="w3-validate w3-text-grey-light w3-large" for="uriName">URI:&nbsp;<span class="w3-text-red">*</span>&nbsp;<span class="w3-small w3-text-grey quote">(Note: This must be lowercase and unique!)</span></label>
						<input type="text" size="50" maxlength="300" name="uriName" id="uriName" value="<s:property value="#request.author.uriName" />" required placeholder="john-doe" class="w3-input w3-round-large w3-border" />
						<span class="w3-small w3-text-grey"><%=request.getScheme()+"://"+request.getServerName() %>/author/<s:property value="uriName" /></span>
					</p>
					<p>
						<label class="w3-validate w3-text-grey-light w3-large" for="username">Username:&nbsp;<span class="w3-text-red">*</span></label>
						<input type="text" size="50" maxlength="200" name="username" id="username" value="<s:property value="username" />" required placeholder="jdoe" class="w3-input w3-round-large w3-border" />
						<span class="w3-small w3-text-grey">Your username is used to login. Not your email address.</span>
					</p>
					<p>
						<label class="w3-validate w3-text-grey-light w3-large" for="email">Email:&nbsp;<span class="w3-text-red">*</span></label>
						<input type="text" size="50" maxlength="100" name="email" id="email" value="<s:property value="email" />" required placeholder="jdoe@email.com" class="w3-input w3-round-large w3-border" />
						<span class="w3-small w3-text-grey">Your email address is used to validate your identity.</span>
					</p>
					<p>   
						<label class="w3-validate w3-text-grey-light w3-large" for="password">Password:&nbsp;<span class="w3-text-red">*</span></label>
						<input type="password" size="50" maxlength="100" name="password" id="password" value="" required placeholder="" class="w3-input w3-round-large w3-border" />
						<span class="w3-small w3-text-grey">Must be 8 or more characters.</span>
					</p>
					<p>
						<label class="w3-validate w3-text-grey-light w3-large" for="password2">Password Verify:&nbsp;<span class="w3-text-red">*</span></label>
						<input type="password" size="50" maxlength="100" name="password2" id="password2" value="" required placeholder="Enter password again" class="w3-input w3-round-large w3-border" />
					</p>
					<p>
						<label class="w3-text-grey-light w3-large">Role:&nbsp;<span class="w3-text-red">*</span></label><br/>
						
						<input type="radio" name="role" id="role1" class="w3-check" checked="checked" value="author" />
						<label class="w3-validate w3-text-grey-light w3-large" for="role1"><span class="w3-tag w3-round w3-pale-green"><span class="icon-author w3-large w3-padding-square"></span>Author</span>&nbsp;<span class="w3-small w3-text-grey quote">Can create and edit their own blog posts.</span></label>
						<br/>
						<input type="radio" name="role" id="role2" class="w3-check" value="admin" />
						<label class="w3-validate w3-text-grey-light w3-large" for="role2"><span class="w3-tag w3-round w3-theme-l4"><span class="icon-star w3-large w3-padding-square"></span>Admin</span>&nbsp;<span class="w3-small w3-text-grey quote">Can create and edit any blog posts and edit other Authors pages.</span></label>
					</p>
					
					<hr />
					<button class="w3-btn w3-right w3-round w3-card w3-pale-green" type="submit" title="Submit">
						<span class="icon-checkmark w3-large w3-margin-right"></span>Submit</button>
					<span>&nbsp;&nbsp;</span>
					<button class="w3-btn w3-round w3-card w3-theme-light" type="button" onclick="history.back();" title="Go back">
						<span class="icon-arrow-left w3-large w3-margin-right"></span>Back</button>
					<span>&nbsp;&nbsp;</span>
					<button class="w3-btn w3-round w3-card w3-theme-light" type="reset" title="Reset search fields">Clear</button>
					
					</form>
				</div>
				<!-- REGISTER END -->
				
				<br />
				<br />
			</div>
		</div>
	</article>
	
	<!-- FOOTER_BEGIN -->
	<%@include file="/WEB-INF/fragment/footer.jspf"%>
	<!-- FOOTER_END -->
	
</body>
</html>