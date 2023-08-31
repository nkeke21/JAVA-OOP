<html>
<head>
    <title>Welcome</title>
</head>
<body>
<h1>Welcome to Homework 5</h1>

<p>Please log in</p>
<form action="login" method="POST">
    <%--@declare id="username"--%><label for="username">UserName</label>
    User name: <input type="text" name="username"/><br/>
    <%--@declare id="password"--%><label for="password">Password</label>
    Password: <input type="password" name="password"/>
    <button type="submit">Login</button>
</form>
<a href="create.jsp" >Create New Account</a>
</body>
</html>
