<footer class="row text-center margin-top-large padding-top-large padding-bottom-large border-top">
	<p class="margin-none">
		<a href="/blog/" class="text-secondary" title="Home">Home</a>&nbsp;&nbsp;|&nbsp;&nbsp;
        <a href="/blog/author/" class="text-secondary" title="Authors">Authors</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		<a href="/blog/search" class="text-secondary" title="Search">Search</a>
		<br/>
		<a href="/blog/year/" class="text-secondary" title="Years">Years</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		<a href="/blog/category/" class="text-secondary" title="Categories">Categories</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		<a href="/blog/tag/" class="text-secondary" title="Tags">Tags</a>
	</p>
	<p class="text-medium text-secondary">
	    <#if context.facebook?has_content>
		    <a target="_blank" title="Facebook" href="${(context.facebook)!''}"><i class="icon-facebook"></i></a>&nbsp;&nbsp;
		</#if>
		<#if context.twitter?has_content>
		    <a target="_blank" title="Twitter" href="${(context.twitter)!''}"><i class="icon-twitter"></i></a>&nbsp;&nbsp;
		</#if>
		<#if context.github?has_content>
		    <a target="_blank" title="GitHub" href="${(context.github)!''}"><i class="icon-github"></i></a>&nbsp;&nbsp;
		</#if>
		<#if context.email?has_content>
		    <a target="_blank" title="Email" href="mailto:${(context.email)!''}"><i class="icon-mail"></i></a>&nbsp;&nbsp;
		</#if>
		<a target="_blank" title="RSS Feed" href="/blog/rss"><i class="icon-rss"></i></a>
	</p>
	<p class="text-tertiary margin-none">
    <a class="text-tertiary" target="_blank" href="https://amdelamar.com/oddox-website/" title="Oddox">Powered by Oddox</a>
	</p>
</footer>
<script type="text/javascript" src="/blog/vendor/osseous/2.5.0/osseous.min.js"></script>