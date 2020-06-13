<!doctype html>
<html>
<head>
</head>
<body>
<h1>Bind Github OAuth 2.0 demo</h1>
<a href="/redirectToGithub">Click me to start binding</a>

<p>Name: <#if githubUser??>${githubUser.name}</#if></p>
<p>Image: <#if githubUser??></p><img src="${githubUser.avatarUrl}"></#if>

</body>
</html>
