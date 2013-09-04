<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Modal Bootstrap</title>
    <script type="text/javascript" src="../_lib/jquery-1.7.2.js"></script> 
	<script type="text/javascript" src="../_lib/jquery.cookie.js"></script>
	<script type="text/javascript" src="../_lib/jquery.hotkeys.js"></script>	
	<script type="text/javascript" src="../jquery.jstree.js"></script>
	<script type="text/javascript" src="../_lib/jquery.ui.core.js"></script>
	<script type="text/javascript" src="../_lib/jquery.ui.widget.js"></script>
	<script type="text/javascript" src="../_lib/jquery.ui.menu.js"></script>
	<script type="text/javascript" src="../_lib/jquery.ui.position.js"></script>
	<script type="text/javascript" src="../_lib/jquery.ui.autocomplete.js"></script>
    <script src="../bootstrap/js/bootstrap.js"></script>
	<link rel="stylesheet" type="text/css" href="../_lib/jquery.ui.autocomplete.css" />
	<link rel="stylesheet" type="text/css" href="../_lib/jquery.ui.theme.css" />
	<link href="../bootstrap/css/bootstrap.css" rel="stylesheet">
</head>
<body>


<a class="btn" data-toggle="modal" href="#myModal">Launch Modal</a>


<div class="modal hide" id="myModal"><!-- note the use of "hide" class -->
  <div class="modal-header">
    <button class="close" data-dismiss="modal">×</button>
    <h3>Modal header</h3>
  </div>
  <div class="modal-body">
    <p>One fine body</p>
  </div>
  <div class="modal-footer">
    <a href="#" class="btn" data-dismiss="modal">Close</a><!-- note the use of "data-dismiss" -->
    <a href="#" class="btn btn-primary">Save changes</a>
  </div>
</div>
    
             
</body>
</html>