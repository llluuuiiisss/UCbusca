<%--
  Created by IntelliJ IDEA.
  User: Freitas
  Date: 11/12/2019
  Time: 22:50
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" type="text/css" href="userpage.css">
    <title class="header">UcBusca</title>
</head>
<body>
<body>
<div class="header">
    <div class="headerText">
        <p>UC Busca</p>
    </div>
    <div class="botCamp1">
        <div class="headerText">
            <p>Nome User</p>
        </div>
    </div>

    <div class="botCamp3">
        <s:form action="register" method="post">
            <s:submit name="register" value="Log Out" />
        </s:form>
    </div>
</div>
<s:form action="searchON" method="post">
    <s:submit name="search" value="Dados do Sistema" />
</s:form><br>
<s:form action="sairAdmin" method="post">
    <s:submit name="search" value="Sair Admin" />
</s:form><br>

<div class="logoPos">
    <div class="logoText">
        <p>UC Busca</p>
    </div>
    <s:form action="indexaUrl" method="post">
        <s:textfield name="link" style="width:900px" />
        <s:submit name="search" value="Indexar Url" />
    </s:form><br>
    <s:form action="makeAdmin" method="post">
        <s:textfield name="user" style="width:900px"/>
        <s:submit name="search" value="Tornar Utilizador Admin"/>
    </s:form><br>
</div>

</body>
</html>
