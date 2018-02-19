<meta charset="utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="viewport" content="width=device-width, initial-scale=1"/>
<meta name="generator" content="Oddox"/>
<meta name="application-name" content="${(name)!'Oddox'}"/>
<meta name="author" content="${(author)!'Oddox'}"/>
<meta name="keywords" content="${(keywords)!'oddox'}"/>
<meta name="description" content="${(description)!'description'}"/>
<meta name="robots" content="all"/>
<meta http-equiv="cache-control" content="public"/>
<meta http-equiv="expires" content="${(date)!''}"/>

<#include "/templates/fragment/meta/meta-webapp.ftl">

<!-- Facebook Open Graph -->
<meta property="fb:app_id" content="${(facebookAppId)!''}"/>
<meta property="og:url" content="${(url)!''}"/>
<meta property="og:type" content="article"/>
<meta property="og:locale" content="en_US"/>
<meta property="og:title" content="${(name)!'Oddox'}"/>
<meta property="og:image" content="/img/icon-512-round.png"/>
<meta property="og:image:type" content="image/png"/>
<meta property="og:image:width" content="512"/>
<meta property="og:image:height" content="512"/>
<meta property="og:description" content="${(description)!'description'}"/>
<meta property="og:site_name" content="${(name)!'Oddox'}" />

<!-- Twitter Card -->
<meta name="twitter:card" content="summary"/>
<meta name="twitter:site" content="${(twitterHandle)!''}"/>
<meta name="twitter:creator" content="${(twitterAuthorHandle)!''}"/>
<meta name="twitter:title" content="${(name)!'Oddox'}"/>
<meta name="twitter:description" content="${(description)!'description'}"/>
<meta name="twitter:image" content="/img/icon-512-round.png"/>
<meta name="twitter:domain" content="${(domain)!''}"/>

<!-- Google+ Schema.org -->
<meta itemprop="name" content="${(name)!'Oddox'}"/>
<meta itemprop="description" content="${(description)!'description'}"/>
<meta itemprop="image" content="/img/icon-512-round.png"/> 

<link rel="author" href="humans.txt"/>
<link rel="shortcut icon" href="/favicon.ico"/>
<link rel="stylesheet" href="/css/ramblingware.css"/>
<link rel="stylesheet" href="/vendor/icomoon/icomoon.css"/>
<link rel="alternate" href="/rss" type="application/rss+xml" title="RSS"/>