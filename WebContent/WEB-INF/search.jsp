<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ page errorPage="/WEB-INF/error/error.jsp" %>
<!-- META_BEGIN -->
<%@include file="/WEB-INF/fragment/meta.jspf"%>
<!-- META_END -->
<title>Search - RamblingWare</title>
</head>
<body class="w3-theme-dark">

	<!-- HEADER_BEGIN -->
	<%@include file="/WEB-INF/fragment/header.jspf"%>
	<!-- HEADER_END -->

	<!-- CONTENT BEGIN -->
	<article class="w3-theme-light">
		<div class="page w3-row">
			
			<!-- TABS_BEGIN -->
			<%@include file="/WEB-INF/fragment/tabs.jspf"%>
			<!-- TABS_END -->
			
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
			
				<h1>Search</h1>				
				
				<% String month = (String) request.getAttribute("month"); %>
				<% String year = (String) request.getAttribute("year"); %>
				<% String author = (String) request.getAttribute("author"); %>
				<% if(month==null) month = ""; %>
				<% if(year==null) year = ""; %>
				<% if(author==null) author = ""; %>
				<form action="/blog/search" method="get">
					
					<div class="w3-row">
						<div class="w3-container w3-col s11 m6 l5">
						
							<p><select id="year" name="year" class="w3-select w3-round-large w3-hover-text-blue w3-border w3-padding">
								<option value="">[Select a Year]</option>
								<option <%if(year.equals("2014")) out.print("selected"); %> value="2014">2014</option>
								<option <%if(year.equals("2015")) out.print("selected"); %> value="2015">2015</option>
								<option <%if(year.equals("2016")) out.print("selected"); %> value="2016">2016</option>
							</select></p>
						
						</div>
						<div class="w3-container w3-col s11 m6 l5">
						
							<p><select id="month" name="month" class="w3-select w3-round-large w3-hover-text-blue w3-border w3-padding">
								<option value="">[Select a Month]</option>
								<option <%if(month.equals("Jan")) out.print("selected"); %> value="Jan">January</option>
								<option <%if(month.equals("Feb")) out.print("selected"); %> value="Feb">February</option>
								<option <%if(month.equals("Mar")) out.print("selected"); %> value="Mar">March</option>
								<option <%if(month.equals("Apr")) out.print("selected"); %> value="Apr">April</option>
								<option <%if(month.equals("May")) out.print("selected"); %> value="May">May</option>
								<option <%if(month.equals("Jun")) out.print("selected"); %> value="Jun">June</option>
								<option <%if(month.equals("Jul")) out.print("selected"); %> value="Jul">July</option>
								<option <%if(month.equals("Aug")) out.print("selected"); %> value="Aug">August</option>
								<option <%if(month.equals("Sep")) out.print("selected"); %> value="Sep">September</option>
								<option <%if(month.equals("Oct")) out.print("selected"); %> value="Oct">October</option>
								<option <%if(month.equals("Nov")) out.print("selected"); %> value="Nov">November</option>
								<option <%if(month.equals("Dec")) out.print("selected"); %> value="Dec">December</option>
							</select></p>
						
						</div>
						<div class="w3-container w3-col s11 m6 l5">
						
							<p><select id="tag" name="tag" class="w3-select w3-round-large w3-hover-text-blue w3-border w3-padding">
								<option value="">[Select a Tag]</option>
								<s:iterator value="tagOptions" status="to" var="tagOp">
								<s:if test="%{#tagOp.equals(tag)}">
								<option selected value="<s:property />"><s:property /></option>
								</s:if><s:else>
								<option value="<s:property />"><s:property /></option>
								</s:else>
								</s:iterator>
						
							</select></p>
						
						</div>
						<div class="w3-container w3-col s11 m6 l5">
							
							<p><select id="author" name="author" class="w3-select w3-round-large w3-hover-text-blue w3-border w3-padding">
								<option value="">[Select an Author]</option>
								<s:iterator value="authorOptions" status="ao" var="authorOp">
								<s:if test="%{#authorOp.uri_name.equals(author)}">
								<option selected value="<s:property value="uri_name" />"><s:property value="name" /></option>
								</s:if><s:else>
								<option value="<s:property value="uri_name" />"><s:property value="name" /></option>
								</s:else>
								</s:iterator>
							</select></p>
						
						</div>
						<div class="w3-container w3-col s11 m6 l5">
							<p>
							<input type="text" size="50" name="title" id="title" value="<s:property value="title" />" style="width:90%"
								placeholder="Article Title" class="w3-input w3-round-large w3-hover-text-blue w3-border w3-padding" />
							</p>
						</div>
						<div class="w3-container w3-col s11 m6 l5">
							<p>
							<button class="w3-btn w3-round w3-card w3-theme-light" type="submit" value="Search" title="Search for posts"><span class="icon-search w3-large w3-margin-right"></span>Search</button>
							<span>&nbsp;&nbsp;</span>
							<button class="w3-btn w3-round w3-card w3-theme-light" type="reset" value="Reset" title="Reset search fields">Reset</button>
							</p>
						</div>
					</div>
				</form>
				<hr />
				
				<s:if test="hasActionErrors()">
				   <s:iterator value="actionErrors">
					<p class="w3-padding w3-border w3-card-2 w3-round w3-pale-red w3-text-red w3-border-red" onclick="this.style.display='none'" data-close="">
						<span class="icon-cross w3-large w3-margin-right"></span><s:property/></p>
					</s:iterator>
				</s:if>
				<s:if test="hasActionMessages()">
				   <s:iterator value="actionMessages">
					<p class="w3-padding w3-border w3-card-2 w3-round w3-pale-green w3-text-green w3-border-green" onclick="this.style.display='none'" data-close="">
						<span class="icon-checkmark w3-large w3-margin-right"></span><s:property/></p>
					</s:iterator>
				</s:if>
				
				<!-- RESULTS START -->
				<s:if test="results != null">
				<s:if test="results.isEmpty()">
					<p class="w3-padding w3-border w3-card-2 w3-round w3-pale-red w3-text-red w3-border-red" onclick="this.style.display='none'" data-close="">
						<span class="icon-cross w3-large w3-margin-right"></span>No results found!</p>
				</s:if>
				<s:else>
					<s:iterator value="results" status="r">
					
						<div class="w3-container w3-round w3-border w3-card w3-hover-shadow w3-padding-0">
						
						<div class="w3-container w3-round w3-col s12 m3 l4 w3-padding-0 w3-center w3-theme-light" style="overflow: hidden;">
							<a href="/blog/post/<s:property value="uriName" />">
							<img style="max-height:200px;" src="<s:property value="thumbnail" />" alt="Photo for <s:property value="title" />" title="Blog post photo." />
							</a>
						</div>
						
						<div class="w3-container w3-round w3-col s12 m9 l8 w3-padding-16">
						<h3 class="w3-padding-0 w3-margin-0"><a href="/blog/post/<s:property value="uriName" />"><s:property value="title" /></a></h3>
						<p class="footnote"><s:property value="description" /></p>
						<p class="footnote"><br /><s:property value="author" />&nbsp;|&nbsp;<s:property value="createDateReadable" /></p>
						<p class="footnote"><b>Tags:</b>&nbsp;
						<s:if test="tags != null && !tags.isEmpty()">
							<s:iterator value="tags">
								&nbsp;<a class="tag w3-tag w3-round w3-theme w3-hover-light-grey w3-hover-shadow" title="<s:property />" href="/blog/search?tag=<s:property />"><s:property /></a>
							</s:iterator>
						</s:if>
						<span class="w3-right">&nbsp;&nbsp;<a class="footnote" href="/blog/post/<s:property value="uriName" />#comments"><span class="disqus-comment-count" data-disqus-identifier="<s:property value="uriName" />"></span></a></span>
						</p>
						</div>
						</div><br />
					</s:iterator>
					
					<p class="footnote"><s:property value="results.size()" /> result(s) for: <span>
					<s:if test="tag != null && !tag.isEmpty()">
						Tag (<s:property value="tag" />) 
					</s:if>
					<s:if test="title != null && !title.isEmpty()">
						Title like "<s:property value="title" />"
					</s:if>
					<s:if test="month != null && !month.isEmpty()">
						Month (<s:property value="month" />) 
					</s:if>
					<s:if test="year != null && !year.isEmpty()">
						Year (<s:property value="year" />) 
					</s:if>
					</span></p>
				</s:else>
				</s:if>
				<s:else>
					<p class="footnote">Use the form above to search for a blog post.</p>
				</s:else>				
				<!-- RESULTS END -->
				
				<br /><br />
			</div>
			
			<!-- ARCHIVE BEGIN -->
			<%@include file="/WEB-INF/fragment/archive.jspf" %>
			<!-- ARCHIVE END -->
			
			<!-- RECENTLY VIEWED BEGIN -->
			<%@include file="/WEB-INF/fragment/recentlyviewed.jspf" %>
			<!-- RECENTLY VIEWED END -->
			
		</div>
	</article>
	<!-- CONTENT END -->

	<!-- FOOTER_BEGIN -->
	<%@include file="/WEB-INF/fragment/footer.jspf"%>
	<!-- FOOTER_END -->
	
</body>
</html>