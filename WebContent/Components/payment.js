$(document).ready(function()
{ 
if ($("#alertSuccess").text().trim() == "") 
 { 
 $("#alertSuccess").hide(); 
 } 
 $("#alertError").hide(); 
}); 


// SAVE ===========================
$(document).on("click", "#btnSave", function(event) 
{ 
// Clear alerts---------------------
 $("#alertSuccess").text(""); 
 $("#alertSuccess").hide(); 
 $("#alertError").text(""); 
 $("#alertError").hide(); 
// Form validation-------------
var status = validateItemForm(); 

if (status != true) 
 { 
 $("#alertError").text(status); 
 $("#alertError").show(); 
 return; 
 } 
// If valid------------------
var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT"; 
$.ajax( 
{ 
url : "PaymentAPI", 
type : type, 
data : $("#formPay").serialize(), 
dataType : "text", 
complete : function(response, status) 
{ 
onItemSaveComplete(response.responseText, status); 
} 
}); 
}); 
// UPDATE===========================
$(document).on("click", ".btnUpdate", function(event) 
{ 
	$("#hidItemIDSave").val($(this).data("payid")); 
 $("#paydate").val($(this).closest("tr").find('td:eq(0)').text()); 
 $("#description").val($(this).closest("tr").find('td:eq(1)').text()); 
 $("#price").val($(this).closest("tr").find('td:eq(2)').text()); 
 $("#type").val($(this).closest("tr").find('td:eq(3)').text()); 
});


$(document).on("click", ".btnRemove", function(event)
		{ 
		 $.ajax( 
		 { 
		 url : "PaymentAPI", 
		 type : "DELETE", 
		 data : "payid=" + $(this).data("payid"),
		 dataType : "text", 
		 complete : function(response, status) 
		 { 
		 onItemDeleteComplete(response.responseText, status); 
		 } 
		 }); 
		});


//CLIENT-MODEL===========================
function validateItemForm() 
{ 
// DAte
if ($("#paydate").val().trim() == "") 
 { 
 return "Insert Item Code."; 
 } 
// Description
if ($("#description").val().trim() == "") 
 { 
 return "Insert Item Name."; 
 } 
//Price----------------------------
if ($("#price").val().trim() == "") 
 { 
 return "Insert Item Price."; 
 } 
//  is numerical value
var tmpPrice = $("#price").val().trim(); 
if (!$.isNumeric(tmpPrice)) 
 { 
 return "Insert a numerical value for Item Price."; 
 } 
//  convert to decimal price
 $("#price").val(parseFloat(tmpPrice).toFixed(2)); 
// Payment Type--------------------
if ($("#type").val().trim() == "") 
 { 
 return "Insert Item Description."; 
 } 
return true; 
}

function onItemSaveComplete(response, status)
{ 
if (status == "success") 
 { 
 var resultSet = JSON.parse(response); 
 if (resultSet.status.trim() == "success") 
 { 
 $("#alertSuccess").text("Successfully saved."); 
 $("#alertSuccess").show(); 
 $("#divItemsGrid").html(resultSet.data); 
 } else if (resultSet.status.trim() == "error") 
 { 
 $("#alertError").text(resultSet.data); 
 $("#alertError").show(); 
 } 
 } else if (status == "error") 
 { 
 $("#alertError").text("Error while saving."); 
 $("#alertError").show(); 
 } else
 { 
 $("#alertError").text("Unknown error while saving.."); 
 $("#alertError").show(); 
 } 
$("#hidItemIDSave").val(""); 
$("#formItem")[0].reset(); 
}


function onItemDeleteComplete(response, status)
{ 
if (status == "success") 
 { 
 var resultSet = JSON.parse(response); 
 if (resultSet.status.trim() == "success") 
 { 
 $("#alertSuccess").text("Successfully deleted."); 
 $("#alertSuccess").show(); 
 $("#divItemsGrid").html(resultSet.data); 
 } else if (resultSet.status.trim() == "error") 
 { 
 $("#alertError").text(resultSet.data); 
 $("#alertError").show(); 
 } 
 } else if (status == "error") 
 { 
 $("#alertError").text("Error while deleting."); 
 $("#alertError").show(); 
 } else
 { 
 $("#alertError").text("Unknown error while deleting.."); 
 $("#alertError").show(); 
 } 
}
