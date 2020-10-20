<%--
  Created by IntelliJ IDEA.
  User: monteiro
  Date: 13-12-2019
  Time: 02:41
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" type="text/css" href="results.css">
    <title>UcBusca | Pesquisas</title>
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
    <div class="botCamp3">
        <s:form action="registerBut" method="post">
            <s:submit name="register" value="Sign Up" />
        </s:form>
    </div>

</div>
<div>
    <div class="logoText">
        <p>UC Busca</p>
    </div>
    <s:form action="searchOFF" method="post">
        <s:textfield name="pesquisa" style="width:900px" />
        <s:submit name="search" value="Search" />
    </s:form><br><br>

    <div>
        <p>Resultados</p>
        <c:forEach items="${session.urls}" var="value">
            <a href="${value.get_link()}">${value.get_titulo()}</a><br>
            <a href="${value.get_link()}">${value.get_link()}</a><br>
            "${value.get_introducao()}"
            <br><br><br>
        </c:forEach>

    </div>

</div>
</body>
</html>
