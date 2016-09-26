<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>New Post - RamblingWare</title>
<!-- META_BEGIN -->
<%@include file="/WEB-INF/fragment/meta-manage.jspf"%>pf"%>
<script src="/ckeditor/ckeditor.js"></script>
<script>
function changeForm() {
	var b = document.getElementById('hasBanner').checked;
	if(b)
	{
		document.getElementById('banner').disabled = false;
		document.getElementById('bannerCaption').disabled = false;
		document.getElementById('bannerDiv1').style.display = 'block';
		document.getElementById('bannerDiv2').style.display = 'block';
	}
	else
	{
		document.getElementById('banner').disabled = true;
		document.getElementById('bannerCaption').disabled = true;
		document.getElementById('bannerDiv1').style.display = 'none';
		document.getElementById('bannerDiv2').style.display = 'none';
	}
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
		
			<div id="page-content" class="w3-col m8 w3-container w3-border w3-padding w3-card-2">
			
				<!-- ADMIN TABS BEGIN -->
				<%@include file="/WEB-INF/fragment/admin-tabs.jspf"%>
				<!-- ADMIN TABS END -->
				
				<h1>Create New Post</h1>
				<p>Use this page to make a new blog post to the webapp.</p>
				
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
		
				<!-- NEW POST BEGIN -->
				<div class="w3-container w3-padding-0 w3-border-0">
					<form action="/manage/newpost" method="post">
					<input type="hidden" name="submitForm" value="true" />
					<p>
						<label class="w3-validate w3-text-grey-light w3-large" for="title">Post Title:&nbsp;<span class="w3-text-red">*</span></label>
						<input type="text" size="50" maxlength="300" name="title" id="title" value="<s:property value="title" />" required placeholder="How to make a blog post!" class="w3-input w3-round-large  w3-border" />
					</p>
					<p>
						<label class="w3-validate w3-text-grey-light w3-large" for="uriName">URI:&nbsp;<span class="w3-text-red">*</span>&nbsp;<span class="footnote quote">(Note: This must be lowercase and unique!)</span></label>
						<input type="text" size="50" maxlength="300" name="uriName" id="uriName" value="<s:property value="uriName" />" required placeholder="how-to-make-a-blog-post" class="w3-input w3-round-large  w3-border" />
					</p>
					<p>   
						<label class="w3-validate w3-text-grey-light w3-large" for="thumbnail">Thumbnail Image URL:&nbsp;<span class="w3-text-red">*</span></label>
						<input type="text" size="50" maxlength="200" name="thumbnail" id="thumbnail" value="<s:property value="thumbnail" />" required placeholder="http://imgur.com/image.png" class="w3-input w3-round-large  w3-border" />
					</p>
					<p>
						<input type="checkbox" name="hasBanner" id="hasBanner" class="w3-check" onchange="changeForm()" />
						<label class="w3-validate w3-text-grey-light w3-large" for="hasBanner">Add a banner image?</label>
					</p>
					<p id="bannerDiv1" style="display:none" class="w3-animate-right">   
						<label class="w3-validate w3-text-grey-light w3-large" for="banner">Banner Image URL:&nbsp;<span class="w3-text-red">*</span></label>
						<input type="text" disabled="disabled" size="50" maxlength="200" name="banner" id="banner" value="<s:property value="banner" />" placeholder="http://imgur.com/image.png" class="w3-input w3-round-large  w3-border" />
					</p>
					<p id="bannerDiv2" style="display:none" class="w3-animate-right">
						<label class="w3-validate w3-text-grey-light w3-large" for="bannerCaption">Banner Caption/Credit:&nbsp;<span class="w3-text-red">*</span></label>
						<input type="text" disabled="disabled" size="50" maxlength="200" name="bannerCaption" id="bannerCaption" value="<s:property value="bannerCaption" />" placeholder="Image Source: imgur" class="w3-input w3-round-large  w3-border" />
					</p>
					<p>
						<label class="w3-validate w3-text-grey-light w3-large" for="htmlContent">Post Content:&nbsp;<span class="w3-text-red">*</span>&nbsp;<span class="footnote quote">(Note: Max 12288 chars.)</span></label>
						<textarea name="htmlContent" id="htmlContent" rows="10" cols="100" style="width:100%"><s:property value="htmlContent" /></textarea>
			            <script>
			                // Replace the <textarea id="htmlContent"> with a CKEditor instance, using default configuration.
			                CKEDITOR.replace('htmlContent');
			            </script>
					</p>
					<p>   
						<label class="w3-validate w3-text-grey-light w3-large" for="description">Description:&nbsp;<span class="w3-text-red">*</span></label>
						<input type="text" size="50" maxlength="300" name="description" id="description" value="<s:property value="description" />" required placeholder="A quick description for RSS and social media..." class="w3-input w3-round-large  w3-border" />
					</p>
					<p>   
						<label class="w3-validate w3-text-grey-light w3-large" for="tags">Tags:&nbsp;<span class="w3-text-red">*</span>&nbsp;<span class="footnote quote">(Note: Separated by commas.)</span></label>
						<input type="text" size="50" maxlength="200" name="tags" id="tags" value="<s:property value="tags" />" required placeholder="[java, interview, funny]" class="w3-input w3-round-large  w3-border" />
					</p>
					<p>
						<input type="checkbox" name="isVisible" id="isVisible" class="w3-check" />
						<label class="w3-validate w3-text-grey-light w3-large" for="isVisible">Make this post publicly visible?</label>
					</p>
					<p>
						<input type="checkbox" name="isFeatured" id="isFeatured" class="w3-check" />
						<label class="w3-validate w3-text-grey-light w3-large" for="isFeatured">Make this a "Featured" post?</label>
					</p>
					
					
					<hr />
					<button class="icon-redo w3-btn w3-right w3-round w3-card w3-green w3-hover-text-white" type="submit" name="submit" value="Submit" title="Submit">Submit</button>
					<span>&nbsp;&nbsp;</span>
					<button class="icon-undo w3-btn w3-round w3-card w3-theme-light" type="button" onclick="history.back();" value="Back" title="Go back">Back</button>
					<span>&nbsp;&nbsp;</span>
					<button class="icon-delete w3-btn w3-round w3-card w3-theme-light" type="reset" onclick="return confirm('Are you sure you want to reset?')" value="Reset" title="Reset search fields">Clear</button>
					
					</form>
				</div>
				<!-- NEW POST END -->
				
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