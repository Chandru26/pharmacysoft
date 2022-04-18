<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />  
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Pharmacy Software</title>

<link rel="stylesheet" href="/pharmacysoft/static/css/bootstrap/bootstrap.min.css">
<link rel="stylesheet" href="/pharmacysoft/static/css/bootstrap/bootstrap-theme.min.css">
<link rel="stylesheet" href="/pharmacysoft/static/css/pharmacysoft/app.css"></link>
<link rel="stylesheet" href="/pharmacysoft/static/css/bootstrap/font-awesome.css" />


</head>
<body>
<div class="container-fluid">
<div class="login-container">

<div class="row">
	<div class="col-sm-12">
		<div class="row panel-heading">
		<div class="col-sm-1"><img src="/pharmacysoft/static/images/bgs.png" class="img-responsive center-block" alt="bgs photo"> </div>
		<div class="col-sm-10 text-center"><p><span  class="trusttext">||Jai Sri Gurudev||</span><br><span  class="trusttext">Sri Adichunchanagiri Shikshana Trust (R)</span><br><span class="collegenamecol"><b>SRI ADICHUNCHANAGIRI AYURVEDA MEDICAL COLLGE, HOSPITAL AND RESEARCH CENTER </b></span><br>
		<span class="collegeadd">Nagarur, Bengaluru - 562 123<br>Ph.No : +91 9481861519, Email : principal@amc.org.in</br>  www.amc.org.in<br><br></span></p></div>
		<div class="col-sm-1"><img src="/pharmacysoft/static/images/nns.png" class="img-responsive center-block" alt="nns photo"></div>
	</div>
	</div>
</div>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<div class="row"><div class="col-sm-4"></div>
<div class="col-sm-4">
<div class="login-card">
<div class="login-form">
<c:url var="loginUrl" value="/login" />
<form action="${loginUrl}" method="post" class="form-horizontal">
	<c:if test="${param.error != null}">
		<div class="alert alert-danger">
			<p>Invalid username and password.</p>
		</div>
	</c:if>
	<c:if test="${param.logout != null}">
		<div class="alert alert-success">
			<p>You have been logged out successfully.</p>
		</div>
	</c:if>
	<div class="input-group input-sm">
		<label class="input-group-addon" for="username"><i class="fa fa-user"></i></label>
		<input type="text" class="form-control" id="username" name="ssoId" placeholder="Enter Username" required>
	</div>
	<div class="input-group input-sm">
		<label class="input-group-addon" for="password"><i class="fa fa-lock"></i></label> 
		<input type="password" class="form-control" id="password" name="password" placeholder="Enter Password" required>
	</div>
	<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
		
	<div class="form-actions">
		<input type="submit"
			class="btn btn-block btn-primary btn-default" value="Log in">
	</div>
</form>
</div>
</div>

</div>
</div>

</div>
</div>
</body>
</html>