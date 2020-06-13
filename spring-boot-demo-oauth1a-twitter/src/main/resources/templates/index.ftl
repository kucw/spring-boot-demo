<!doctype html>
<html>
<head>
</head>
<body>
<h1>Bind Twitter OAuth 1.0a demo</h1>
<a href="/bind">Click me to start binding</a>

<p>Name: <#if twitterUser??>${twitterUser.screenName!}</#if></p>
<p>Language: <#if twitterUser??>${twitterUser.language!}</#if></p>
<p>accessToken: <#if twitterUser??>${twitterUser.accessToken}</#if></p>
<p>accessTokenSecret: <#if twitterUser??>${twitterUser.accessTokenSecret}</#if></p>

</body>
</html>
