<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.rw.model.ApplicationStore" %>
<!DOCTYPE HTML>
<html>
<head>
<title>New Post - RamblingWare</title>
<!-- META_BEGIN -->
<%@include file="/WEB-INF/fragment/meta-manage.jspf"%>
<script src="/ckeditor/ckeditor.js"></script>
<script>
function changeForm() {
	if(document.getElementById('hasBanner').checked)
	{
		document.getElementById('bannerDiv1').style.display = 'block';
		document.getElementById('bannerDiv2').style.display = 'block';
		document.getElementById('banner').disabled = false;
		document.getElementById('banner').required = true;
		document.getElementById('bannerCaption').disabled = false;
	}
	else
	{
		document.getElementById('banner').disabled = true;
		document.getElementById('banner').required = false;
		document.getElementById('bannerCaption').disabled = true;
		document.getElementById('bannerDiv1').style.display = 'none';
		document.getElementById('bannerDiv2').style.display = 'none';
	}
}
function validate() {
	var pattern = /^((https):\/\/)/;
	if(!pattern.test(document.getElementById('thumbnail').value)) {
		if(!confirm('Thumbnail is not secure (HTTPS). Do you want to continue?')) {
			document.getElementById('thumbnail').focus();
			return false;
		}
	}
	if(document.getElementById('hasBanner').checked) {
		if(document.getElementById('banner').value.length <= 0) {
			alert('Banner is empty! Please provide a URL to an image.');
			return false;
		}
		if(!pattern.test(document.getElementById('banner').value)) {
			if(!confirm('Banner is not secure (HTTPS). Do you want to continue?')) {
				document.getElementById('banner').focus();
				return false;
			}
		}
	}
	if(document.getElementById('htmlContent').value.length > 12288) {
		alert('Sorry! The post is too long.\nMax length = 12288 chars\nPost length = '+document.getElementById('htmlContent').value.length+'\n\nPlease shorten your post.');
		return false;
	}
	return true;
}
function makeUri() {
	var title = document.getElementById('title').value;
	title = title.replace(/[^0-9a-z]/gi, ' ');
	title = title.replace(/\s+/g, '-').toLowerCase();
	document.getElementById('uriName').value = title;
}
function preview() {
	var title = document.getElementById('title').value;
	if(title.length<=1)
		title = "Blog Post Title";
	document.getElementById('previewTitle').innerHTML = title;
	document.getElementById('previewTitle2').innerHTML = title;	
	
	var desc = document.getElementById('description').value;
	if(desc.length<=1)
		desc = "Blog Post Description.";
	document.getElementById('previewDesc').innerHTML = desc;
	
	var tags = document.getElementById('tags').value;
	// chop off [ ] if they were added
	if(tags.startsWith('['))
		tags = tags.substring(1);
	if(tags.endsWith(']'))
		tags = tags.substring(0,tags.length-1);
	var tagArray = tags.split(',');
	var tagHtml = "";
	for (var i = 0; i < tagArray.length; i++) {
		if(tagArray[i].length>0)
		tagHtml += "&nbsp;<a class=\"tag w3-round w3-theme w3-hover-shadow\" href=\"#\">"+tagArray[i]+"</a>"; 
	}
	if(tagHtml<=1)
		tagHtml = "&nbsp;<a class=\"tag w3-round w3-theme w3-hover-shadow\" href=\"#\">Tag</a>";
	document.getElementById('previewTags').innerHTML = tagHtml;
	
	
	var src = document.getElementById('thumbnail').value;
	if(src.length<=1)
		src = "https://i.imgur.com/pHKz09F.png";
	document.getElementById('previewImg').src = src;
	changeForm();
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
				
				<h1>Create New Post</h1>
		
				<!-- NEW POST BEGIN -->
				<div class="w3-container w3-padding-0 w3-border-0">
					<form action="/manage/newpost" method="post">
					<input type="hidden" name="submitForm" value="true" />
					
					<p>
						<label class="w3-validate w3-text-grey-light w3-large" for="title">Post Title:&nbsp;<span class="w3-text-red">*</span></label>
						<input type="text" size="50" maxlength="300" name="title" id="title" value="<s:property value="title" />" onkeyup="makeUri()" onkeypress="preview()" onchange="preview()" required placeholder="How to make a blog post!" class="w3-input w3-round-large  w3-border" />
					</p>
					<p>
						<label class="w3-validate w3-text-grey-light w3-large" for="uriName">URI:&nbsp;<span class="w3-text-red">*</span>&nbsp;<span class="footnote quote">(Note: This must be lowercase and unique!)</span></label>
						<input type="text" size="50" maxlength="300" name="uriName" id="uriName" value="<s:property value="uriName" />" required placeholder="how-to-make-a-blog-post" class="w3-input w3-round-large  w3-border" />
					</p>
					<p>   
						<label class="w3-validate w3-text-grey-light w3-large" for="description">Description:&nbsp;<span class="w3-text-red">*</span></label>
						<input type="text" size="50" maxlength="300" name="description" id="description" value="<s:property value="description" />" onkeyup="preview()" onchange="preview()" required placeholder="A quick description for RSS and social media..." class="w3-input w3-round-large  w3-border" />
					</p>
					<p>   
						<label class="w3-validate w3-text-grey-light w3-large" for="tags">Tags:&nbsp;<span class="w3-text-red">*</span>&nbsp;<span class="footnote quote">(Note: Separated by commas.)</span></label>
						<input type="text" size="50" maxlength="200" name="tags" id="tags" value="<s:property value="tags" />" onkeyup="preview()" onchange="preview()" required placeholder="java, interview, funny" class="w3-input w3-round-large  w3-border" />
					</p>
					<p>   
						<label class="w3-validate w3-text-grey-light w3-large" for="thumbnail">Thumbnail Image URL:&nbsp;<span class="w3-text-red">*</span></label>
						<input type="text" size="50" maxlength="200" name="thumbnail" id="thumbnail" value="<s:property value="thumbnail" />" onchange="preview()" required placeholder="https://imgur.com/image.png" class="w3-input w3-round-large  w3-border" />
					</p>							
					
					<h3>Social Card Preview</h3>
					<div class="w3-container w3-round w3-border w3-card w3-hover-shadow w3-padding-0">
						
						<div class="w3-container w3-round w3-col s12 m3 l4 w3-padding-0 w3-center w3-theme-light" style="overflow: hidden;">
							<a href="#">
							<img id="previewImg" style="max-height:200px;" src="<s:property value="thumbnail" />" alt="Photo for your Post" title="Blog post photo." />
							</a>
						</div>
						
						<div class="w3-container w3-round w3-col s12 m9 l8 w3-padding-16">
						<h3 class="w3-padding-0 w3-margin-0"><a id="previewTitle" href="#"><s:property value="title" /></a></h3>
						<p id="previewDesc" class="footnote"><s:property value="description" /></p><br/>
						
						<p class="footnote">Tags:
						<span id="previewTags">
							&nbsp;<a class="tag w3-tag w3-round w3-theme w3-hover-shadow" href="#">Tags</a>
						</span>
						
						</p>
						<p class="footnote"><s:property value="#session.USER.getName()" />&nbsp;|&nbsp;<%=ApplicationStore.formatReadableDate(new java.util.Date(System.currentTimeMillis())) %>
						&nbsp;|&nbsp;<span><a class="footnote" href="#" /><span>0 comments</span></a></span></p>
						</div>
					</div>
					
					<br />
					<hr />
					
					<p>
						<input type="checkbox" name="hasBanner" id="hasBanner" class="w3-check" onchange="changeForm()" value="true" />
						<label class="w3-validate w3-text-grey-light w3-large" for="hasBanner">Add a banner image?&nbsp;<span class="footnote quote">(A large image above the title of the post.)</span></label>
					</p>
					<p id="bannerDiv1" style="display:none" class="w3-animate-right">   
						<label class="w3-validate w3-text-grey-light w3-large" for="banner">Banner Image URL:&nbsp;<span class="w3-text-red">*</span></label>
						<input type="text" disabled="disabled" size="50" maxlength="200" name="banner" id="banner" value="<s:property value="banner" />" placeholder="https://imgur.com/image.png" class="w3-input w3-round-large  w3-border" />
					</p>
					<p id="bannerDiv2" style="display:none" class="w3-animate-right">
						<label class="w3-validate w3-text-grey-light w3-large" for="bannerCaption">Banner Caption/Credit:&nbsp;</label>
						<input type="text" disabled="disabled" size="50" maxlength="200" name="bannerCaption" id="bannerCaption" value="<s:property value="bannerCaption" />" placeholder="Image Source: imgur" class="w3-input w3-round-large  w3-border" />
					</p>
					<h2 id="previewTitle2">Post Title</h2>
					<p>
						<label class="w3-validate w3-text-grey-light w3-large" for="htmlContent">Post Content:&nbsp;<span class="w3-text-red">*</span>&nbsp;<span class="footnote quote">(Note: Max 12288 chars.)</span></label>
						<textarea name="htmlContent" id="htmlContent" rows="10" cols="100" maxlength="12288" style="width:100%"><s:property value="htmlContent" /></textarea>
			            <script>
			                // Replace the <textarea id="htmlContent"> with a CKEditor instance, using default configuration.
			                CKEDITOR.replace('htmlContent');
			            </script>
					</p>
					
					<hr />
					<p>
						<label class="w3-validate w3-text-grey-light w3-large" for="publishDate">Publish Date:&nbsp;<span class="w3-text-red">*</span>&nbsp;<span class="footnote quote">(MM-DD-YYYY or Month DD, YY)</span></label>
						<input type="text" size="50" maxlength="20" name="publishDate" id="publishDate" value="<s:property value="publishDateReadable" />" required placeholder="MM-DD-YYYY" class="w3-input w3-round-large w3-border" />
					</p>
					<p>
						<input type="checkbox" name="visible" id="visible" class="w3-check" value="true" />
						<label class="w3-validate w3-text-grey-light w3-large" for="visible">Make this post publicly visible?&nbsp;<span class="icon-eye w3-large w3-text-black w3-padding-square"></span><span class="footnote quote">(You can make it public later if you want.)</span></label>
					</p>
					<p>
						<input type="checkbox" name="featured" id="featured" class="w3-check" value="true" />
						<label class="w3-validate w3-text-grey-light w3-large" for="featured">Make this a "Featured" post?&nbsp;<span class="icon-star-full w3-large w3-text-yellow w3-padding-square"></span><span class="footnote quote">(Gets put on the Featured sidebar of every page.)</span></label>
					</p>
					
					<hr />
					<button class="w3-btn w3-right w3-round w3-card w3-pale-green" type="submit" title="Submit" onclick="return validate()">
						<span class="icon-checkmark w3-large w3-margin-right"></span>Submit</button>
					<span>&nbsp;&nbsp;</span>
					<button class="w3-btn w3-round w3-card w3-theme-light" type="button" onclick="history.back();" title="Go back">
						<span class="icon-arrow-left w3-large w3-margin-right"></span>Back</button>
					<span>&nbsp;&nbsp;</span>
					<button class="w3-btn w3-round w3-card w3-theme-light" type="reset" onclick="return confirm('Are you sure you want to reset?')" title="Reset search fields">
						<span class="icon-bin w3-large w3-margin-right"></span>Clear</button>
					
					</form>
				</div>
				<script>
					preview();
				</script>
				<!-- NEW POST END -->
				
			</div>
		</div>
	</article>
	
	<!-- FOOTER_BEGIN -->
	<%@include file="/WEB-INF/fragment/footer.jspf"%>
	<!-- FOOTER_END -->
	
</body>
</html>