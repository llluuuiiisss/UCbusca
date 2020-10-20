<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" type="text/css" href="userpage.css">
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
<s:form action="admin" method="post">
    <s:submit name="search" value="Funções de Administrador" />
</s:form><br>
<div>
    <div>
        <p>UC Busca</p>
    </div>

    <div>
        <p>Historico</p>
        <s:text name="Hist">${session.hist}</s:text><br>

    </div>

</div>
</body>
</html>
