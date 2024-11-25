<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Weather Report</title>
<style>
input[type=submit]{
background-color:green;
color:white;
padding:15px 30px;
margin:8px 0px;
border:none;
border-radius=5px;
}
input[type=text]{
padding:15px 30px;
border:1px solid #ccc;
box-sizing:border-box;
}
div{
border-radius:5px;
background-color:lightgray;
padding:20px;
}
form{
margin:250px;
}
</style>
</head>
<body>

<h1 style="text-align:center;">Weather Navigator</h1>
<div>
<form action="MyServlet" method="get" style="text-align:center;">
<input type="text" name="word" placeholder="Enter a location...">
<input type="submit" value="submit">
</form>

</div>
</body>
</html>