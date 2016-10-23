<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Edit Author - RamblingWare</title>
<!-- META_BEGIN -->
<%@include file="/WEB-INF/fragment/meta-manage.jspf"%>
<script src="/ckeditor/ckeditor.js"></script>
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
		
			<div id="page-content" class="w3-col m8 w3-container w3-padding">
			
				<!-- ADMIN TABS BEGIN -->
				<%@include file="/WEB-INF/fragment/admin-tabs.jspf"%>
				<!-- ADMIN TABS END -->
				
				<h1>Edit Author</h1>
				<p>Use this page to make changes to an existing author.</p>
		
				<s:if test="hasActionErrors()">
				   <s:iterator value="actionErrors">
						<p class="error"><s:property/></p>
					</s:iterator>
				</s:if>
				<s:if test="hasActionMessages()">
				   <s:iterator value="actionMessages">
						<p class="info"><s:property/></p>
					</s:iterator>
				</s:if>
				
				<!-- EDIT AUTHOR BEGIN -->
				<div class="w3-container w3-padding-0 w3-border-0">
					<form action="/manage/edituser/<s:property value="#request.author.uriName" />" method="post">
					<input type="hidden" name="id" value="<s:property value="#request.author.id" />" />
					<p>
						<label class="w3-validate w3-text-grey-light w3-large" for="title">Author Name:&nbsp;<span class="w3-text-red">*</span></label>
						<input type="text" size="50" maxlength="300" name="title" id="title" value="<s:property value="#request.author.name" />" required placeholder="Rambling Man" class="w3-input w3-round-large w3-hover-light-grey w3-border" />
					</p>
					<p>
						<label class="w3-validate w3-text-grey-light w3-large" for="uriName">URI:&nbsp;<span class="w3-text-red">*</span>&nbsp;<span class="footnote quote">(Note: This must be lowercase and unique!)</span></label>
						<input type="text" size="50" maxlength="300" name="uriName" id="uriName" value="<s:property value="#request.author.uriName" />" required placeholder="rambling-man" class="w3-input w3-round-large w3-hover-light-grey w3-border" />
					</p>
					<p>   
						<label class="w3-validate w3-text-grey-light w3-large" for="thumbnail">Thumbnail Image URL:&nbsp;<span class="w3-text-red">*</span></label>
						<input type="text" size="50" maxlength="200" name="thumbnail" id="thumbnail" value="<s:property value="#request.author.thumbnail" />" required placeholder="http://imgur.com/image.png" class="w3-input w3-round-large  w3-border" />
					</p>
					<p>
						<label class="w3-validate w3-text-grey-light w3-large" for="htmlContent">Post Content:&nbsp;<span class="w3-text-red">*</span>&nbsp;<span class="footnote quote">(Note: Max 12288 chars.)</span></label>
						<textarea name="htmlContent" id="htmlContent" rows="10" cols="100" style="width:100%">
						<s:property value="#request.author.htmlContent" />
						</textarea>
			            <script>
			                // Replace the <textarea id="htmlContent"> with a CKEditor instance, using default configuration.
			                CKEDITOR.replace('htmlContent');
			            </script>
					</p>
					<p>   
						<label class="w3-validate w3-text-grey-light w3-large" for="description">Description:&nbsp;<span class="w3-text-red">*</span></label>
						<input type="text" size="50" maxlength="300" name="description" id="description" value="<s:property value="#request.author.description" />" required placeholder="A quick description for RSS and social media..." class="w3-input w3-round-large w3-hover-light-grey w3-border" />
					</p>
					
					
					<hr />
					<button class="icon-redo w3-btn w3-right w3-round w3-card w3-green w3-hover-text-white" type="submit" name="submit" value="Submit" title="Submit">Save</button>
					<span>&nbsp;&nbsp;</span>
					<button class="icon-undo w3-btn w3-round w3-card w3-theme-light" type="button" onclick="history.back();" value="Back" title="Go back">Back</button>
					<span>&nbsp;&nbsp;</span>
					<button class="icon-delete w3-btn w3-round w3-card w3-red w3-hover-text-white" type="submit" onclick="return confirm('Are you sure you want to delete?\n\nAny blog posts by this author will still exist.')" name="delete" value="Delete" title="Delete this post">Delete Author</button>
					
					</form>
				</div>
				<!-- EDIT POST END -->
			
								
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