<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Title</title>
<script type="text/javascript" src="../_lib/jquery.js"></script>
	<script type="text/javascript" src="../_lib/jquery.cookie.js"></script>
	<script type="text/javascript" src="../_lib/jquery.hotkeys.js"></script>
	<script type="text/javascript" src="../jquery.jstree.js"></script>
	<!-- 
	<link type="text/css" rel="stylesheet" href="../_docs/syntax/!style.css"/>
	<link type="text/css" rel="stylesheet" href="../_docs/!style.css"/>
	<script type="text/javascript" src="../_docs/syntax/!script.js"></script>
	 -->
	 
</head>
<body>

<div id="selector">
  <ul>
    <li><a>Team A's Projects</a>
      <ul>
    <li><a>Iteration 1</a>
          <ul>
            <li><a>Story A</a></li>
            <li><a>Story B</a></li>
            <li><a>Story C</a></li>
          </ul>
        </li>
    <li><a>Iteration 2</a>
          <ul>
        <li><a>Story D</a></li>
          </ul>
        </li>
      </ul>
    </li>
  </ul>
</div>

<button id="actionButton">Do it!</button>

<div id="tree" name="tree"></div>
<br>

<label>Bioportal Ontology Lookup</label> <input type="text" name="bio">
<label>Bioportal Term Lookup</label> <input type="text" name="biot">

<br>

<input type="button" id="add" value="add folder" />
<br>
<input type="button" id="add" value="add node" />
<br>

 <script type="text/javascript">
        $(document).ready(function()
        {
        	
        	var tree = $("#selector");
            tree.bind("loaded.jstree", function (event, data) {
                tree.jstree("open_all");
            });
            
            tree.bind("refresh.jstree", function (event, data) {
                tree.jstree("open_all");
            });
        
        	$("#selector").jstree();
        
        	
            $("#tree").jstree({ 
                    "json_data" : {
                        "data" : [
                            { 
                                "data" : "parent", 
                                "attr" : { "id" : "root.id" }, 
                                "children" : [ { "data" : "child1",
                                                 "attr" : { "id" : "child1.id" },
                                                 "children" : [ ] }
                                             ]
                            },
                        ]
                    },
                    "plugins" : [ "themes", "json_data", "crrm" ]
                });
           
            
            $("#add").click(function() {               
                $("#tree").jstree("create", $("#child1\\.id"), "inside",  { "data" : "child2" },
                        function() { alert("added"); }, true);
            });
        
        });
        
        $("#actionButton").click(function() {
            alert($("#storySelector").jstree("is_selected", $("#iteration1")));
        });
        
 </script>
</body>
</html>