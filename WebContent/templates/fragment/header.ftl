<nav id="sidebarMenu" class="sidebar shadow-right">
    <div class="nav-item-close">
        <a class="right" title="Close Menu" href="javascript:closeSidebar()" aria-label="Close">
            <svg width="16" height="16" viewBox="0 0 16 16" xmlns="http://www.w3.org/2000/svg"><path d="M2.757 14.657L8 9.414l5.243 5.243 1.414-1.414L9.414 8l5.243-5.243-1.414-1.414L8 6.586 2.757 1.343 1.343 2.757 6.586 8l-5.243 5.243z" fill-rule="evenodd"></path></svg>
        </a>
    </div>
    <div class="nav-list text-center" onclick="closeSidebar()">
        <a class="nav-item" href="/">Blog</a>
        <a class="nav-item" href="/about">About</a>
        <br/>
        <a class="nav-item" href="/author/">Authors</a>
		<a class="nav-item" href="/search">Search</a>
		<br/>
		<a class="nav-item" href="/year/">Years</a>
		<a class="nav-item" href="/category/">Categories</a>
		<a class="nav-item" href="/tag/">Tags</a>
		<hr/>
		<a class="nav-item text-tertiary" target="_blank" href="https://oddox.org" title="Oddox.org">Powered by Oddox</a>
    </div>
</nav>
<nav class="navbar border-bottom">
    <div class="navbar-body container">
        <div class="nav-item-logo">
            <a href="/"><span class="text-medium text-bold text-uppercase text-wide">${(name)!'Oddox'}</span></a>
        </div>
        <div class="nav-small-menu">
            <a class="nav-item" title="Open Menu" href="javascript:openSidebar()">
                <svg width="16" height="16" viewBox="0 0 16 16" xmlns="http://www.w3.org/2000/svg"><path d="M0 7h16v2H0V7zm0-6h16v2H0V1zm0 12h16v2H0v-2z" fill-rule="evenodd"></path></svg>
            </a>
        </div>
        <div class="nav-group nav-large-menu text-bold">
            <a class="nav-item" href="/">Blog</a>
            <a class="nav-item" href="/about">About</a>
            <form action="/search" method="post" class="nav-item">
				<i class="icon-search padding"></i>
				<input id="searchHead" name="q" maxlength="50" placeholder="Search..." style="max-height:28px;" type="text">
			</form>
        </div>
    </div>
</nav>
<#if actionErrors??>
	<#list actionErrors as actionError>
	<div class="border border-danger text-bold text-danger" style="float:left; position:absolute; left:50%; top:1%;">
	${(actionError)!''}
	</div>
	</#list>
</#if>
<#if actionMessages??>
	<#list actionMessages as actionMessage>
	<div class="border border-success text-bold text-success" style="float:left; position:absolute; left:50%; top:1%;">
	${(actionMessage)!''}
	</div>
	</#list>
</#if>
