<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Title</title>
    <script type="text/javascript" src="../_lib/jquery.js"></script>
    
    <script type="text/javascript" src="../jquery.jstree.js"></script>
	<script type="text/javascript" src="../_lib/css.js"></script>
	<script type="text/javascript" src="../_lib/tree_component.js"></script>
	<link rel="stylesheet" type="text/css" href="../_lib/tree_component.css" />
	
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

<h2>Creating nodes</h2>
<p>You can create nodes programatically with the <span class="var">create</span> function. You can place the new node inside another node (even at a specific position among its children) or <span class="val">"below"</span> or <span class="val">"after"</span> the node (if you omit the reference node, the currently selected is used). This is controlled by the <span class="var">rules.createat</span> config option, and the third argument of the create function.</p>
<input type="button" value="create" onclick="$.tree_reference('demo0').create();" />
<input type="button" value="create with title" onclick="$.tree_reference('demo0').create({ data: 'Created node' },$('#pjson0_3'));" />
<input type="button" value="create with title and icon" onclick="$.tree_reference('demo0').create({ attributes : { 'class' : 'cc' }, data: { title : 'ID and ICON', icon : '../media/images/ok.png' } },$('#pjson0_3'));" />
<div id="demo0" class="demo"></div>
<div id="sources0" class="sources">
<pre>
// Button codes
$.tree_reference('demo0').create();
$.tree_reference('demo0').create({ data: 'Created node' },$('#pjson0_3'));
$.tree_reference('demo0').create({ attributes : { 'class' : 'cc' }, data: { title : 'ID and ICON', icon : '../media/images/ok.png' } },$('#pjson0_3'));
</pre>
</div>
<br>

<label>Bioportal Ontology Lookup</label> <input type="text" name="bio">
<label>Bioportal Term Lookup</label> <input type="text" name="biot">

<br>

<input type="button" id="add" value="add folder" />
<br>
<input type="button" id="add" value="add node" />
<br>
<input type="button" id="remove_1" value="remove_1" />
<br>
<input type="button" id="addtest" value="add node test" />
<br>

 <script type="text/javascript">
 $(document).ready(function()
	  {
	    $("#demo0").tree({
	      data  : {
	        type  : "json",
	        json  : [ 
	          { attributes: { id : "pjson0_1" }, data: "Root node 1", children : [
	            { attributes: { id : "pjson0_2" }, data: { title : "Custom icon", icon : "../media/images/ok.png" } },
	            { attributes: { id : "pjson0_3" }, data: "Child node 2" },
	            { attributes: { id : "pjson0_4" }, data: "Some other child node" }
	          ]}, 
	          { attributes: { id : "pjson0_5" }, data: "Root node 2" } 
	        ]
	      }
	    });
	});
        
 </script>
</body>
</html>