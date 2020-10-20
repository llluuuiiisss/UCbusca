<%--
  Created by IntelliJ IDEA.
  User: Freitas
  Date: 11/12/2019
  Time: 22:56
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title class="header">UcBusca|SignUp</title>
    <link rel="stylesheet" type="text/css" href="register.css">
</head>
<body>
<div class="header">
    <div class="headerText">
        <p>UC Busca</p>
    </div>

    <s:form action="login" method="post">
    <div class="botCamp">
        <s:text name="Username:" />
        <s:textfield name="username" /><br>
    </div>
    <div class="botCamp1">
        <s:text name="Password:"/>
        <s:textfield name="password" /><br>
    </div>
    <div class="botCamp2">
        <s:submit value="Login" />
        </s:form>
    </div>
</div>

<div class="logoPos">
    <s:form action="signup" method="post">
        <s:text name="Nome:" />&nbsp
        <s:textfield name="username" /><br>
        <s:text name="Password:" />&nbsp
        <s:textfield name="password" /><br>
        <s:text name="Email:" />&nbsp
        <s:textfield name="email" /><br>
        <s:submit value="Signup" />
    </s:form><br>
</div>

</body>
</html>
