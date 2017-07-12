<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"
	errorPage="/WEB-INF/error/error.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/WEB-INF/fragment/meta/meta-manage.jspf"%>

<title>New Author - <%=Application.getSetting("name")%></title>
<script>
function makeUri() {
	var title = document.getElementById('name').value;
	title = title.replace(/[^0-9a-z]/gi, ' ');
	title = title.replace(/\s+/g, '-').toLowerCase();
	document.getElementById('uriName').value = title;
}
</script>
</head>
<body class="w3-theme-dark">

	<%@include file="/WEB-INF/fragment/header.jspf"%>
	
	<article class="w3-theme-light">
		<div class="page w3-row">
		
			<%@include file="/WEB-INF/fragment/tabs/tabs.jspf"%>
		
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<h1>Add New Author</h1>
		
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
						<label class="w3-text-grey-light w3-large">Select Role:&nbsp;<span class="w3-text-red">*</span></label><br/>
						<s:iterator value="roles" status="r">
							<input type="radio" name="role" id="role<s:property value="#r.index" />" class="w3-check" value="<s:property value="id" />" />
							<s:if test="id % 4 == 0">
								<label class="w3-validate w3-large" for="role<s:property value="#r.index" />"><span class="w3-tag w3-round w3-pale-red"><span class="icon-cog w3-large"></span>&nbsp;<s:property value="name" /></span>&nbsp;<span class="w3-small w3-text-grey quote"><s:property value="description" /></span></label>
							</s:if>
							<s:elseif test="id % 3 == 0">
								<label class="w3-validate w3-large" for="role<s:property value="#r.index" />"><span class="w3-tag w3-round w3-pale-yellow"><span class="icon-star w3-large"></span>&nbsp;<s:property value="name" /></span>&nbsp;<span class="w3-small w3-text-grey quote"><s:property value="description" /></span></label>
							</s:elseif>
							<s:elseif test="id % 2 == 0">
								<label class="w3-validate w3-large" for="role<s:property value="#r.index" />"><span class="w3-tag w3-round w3-pale-blue"><span class="icon-quill w3-large"></span>&nbsp;<s:property value="name" /></span>&nbsp;<span class="w3-small w3-text-grey quote"><s:property value="description" /></span></label>
							</s:elseif>
							<s:elseif test="id % 1 == 0">
								<label class="w3-validate w3-large" for="role<s:property value="#r.index" />"><span class="w3-tag w3-round w3-pale-green"><span class="icon-author w3-large"></span>&nbsp;<s:property value="name" /></span>&nbsp;<span class="w3-small w3-text-grey quote"><s:property value="description" /></span></label>
							</s:elseif>
							<br/>
						</s:iterator>
					</p>
					
					<br />
					<hr />
					<button class="w3-btn w3-right w3-round w3-green w3-hover-teal" type="submit" title="Submit">
						<span class="icon-checkmark w3-large w3-margin-right"></span>Submit</button>
					<span>&nbsp;&nbsp;</span>
					<button class="w3-btn w3-round w3-card w3-theme-light" type="button" onclick="history.back();" title="Go back">
						<span class="icon-arrow-left w3-large w3-margin-right"></span>Back</button>
					<span>&nbsp;&nbsp;</span>
					<button class="w3-btn w3-round w3-card w3-theme-light" type="reset" title="Reset search fields">Clear</button>
					
					</form>
				</div>
			</div>
		</div>
	</article>
	
	<%@include file="/WEB-INF/fragment/footer.jspf"%>
</body>
</html>