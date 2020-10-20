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
    <link rel="stylesheet" type="text/css" href="index.css">
    <title>UcBusca | Pesquisas</title>
</head>
<body>
<div class="header">
    <div class="headerText">
        <p>UC Busca</p>
    </div>
    <div class="botCamp1">
        <div class="headerText">
            <p>Nome </p><s:text name="username">${session.username}</s:text>
        </div>
    </div>

    <div class="botCamp3">
        <s:form action="register" method="post">
            <s:submit name="register" value="Log Out" />
        </s:form>
    </div>
</div>
<s:form action="hist" method="post">
    <s:submit name="search" value="Historico" />
</s:form><br>
<s:form action="admin" method="post">
    <s:submit name="search" value="Funções de Administrador" />
</s:form><br>
<div>
    <div>
        <p>UC Busca</p>
    </div>
    <s:form action="searchON" method="post">
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
