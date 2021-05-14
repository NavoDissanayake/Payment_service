<%@page import="com.PaymentService"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/payment.js"></script>
</head>
<body>
<div class="container"><div class="row"><div class="col-6"> 
<h1>Payment Management</h1><!-- Header -->
<form id="formPay" name="formPay"><!-- Form of the payment -->
 Payment Date: 
 <input id="paydate" name="paydate" type="text" 
 class="form-control form-control-sm">
 <br> Payment Description: 
 <input id="description" name="description" type="text" 
 class="form-control form-control-sm">
 <br> Price: 
 <input id="price" name="price" type="text" 
 class="form-control form-control-sm">
 <br> Payment Type: 
 <input id="type" name="type" type="text" 
 class="form-control form-control-sm">
 <br>
 <input id="btnSave" name="btnSave" type="button" value="Save" 
 class="btn btn-primary">
 <input type="hidden" id="hidItemIDSave" 
 name="hidItemIDSave" value="">
</form>
<div id="alertSuccess" class="alert alert-success"></div>
<div id="alertError" class="alert alert-danger"></div>
<br>
<div id="divItemsGrid">
	<%
		PaymentService itemObj = new PaymentService(); 
	 		out.print(itemObj.readPayment());
	%>
</div>
</div> </div> </div> 

</body>
</html>