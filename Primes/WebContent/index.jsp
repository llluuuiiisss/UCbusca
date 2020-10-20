<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="index.css">
	<title class="header">UcBusca</title>
</head>
<body>
<div class="header">
	<div class="headerText">
		<p>UC Busca</p>
	</div>

	<s:form action="login" method="post">
	<div class="botCamp">
		<text>Username: </text>
		<s:textfield name="username" /><br>
	</div>
	<div class="botCamp1">
		<text>Password: </text>
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

<div class="logoPos">
	<div class="logoText">
		<p>UC Busca</p>
	</div>
	<s:form action="searchOFF" method="post">
		<s:textfield name="pesquisa" style="width:900px" />
		<s:submit name="search" value="Search" />
	</s:form><br>
</div>






</body>
</html>