<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ontology Builder for i2b2</title>
<style type="text/css">
label#bio { display: block; width: 50px; }
#myTab {
    width: 625px;
}
.containercb { border:2px solid #ccc; width:530px; height: 100px; overflow-y: scroll; white-space: nowrap; }

</style>
    <!-- <script type="text/javascript" src="jstree/_lib/jquery.js"></script>  
 -->
    <script type="text/javascript" src="jstree/_lib/jquery-1.7.2.js"></script> 

	<script type="text/javascript" src="jstree/_lib/jquery.cookie.js"></script>
	<script type="text/javascript" src="jstree/_lib/jquery.hotkeys.js"></script>	
	<script type="text/javascript" src="jquery.jstree.js"></script>
	<script type="text/javascript" src="jstree/_lib/jquery.ui.core.js"></script>
	<script type="text/javascript" src="jstree/_lib/jquery.ui.widget.js"></script>
	<script type="text/javascript" src="jstree/_lib/jquery.ui.menu.js"></script>
	<script type="text/javascript" src="jstree/_lib/jquery.ui.position.js"></script>
	<script type="text/javascript" src="jstree/_lib/jquery.ui.autocomplete.js"></script>
	
    <script src="jstree/bootstrap/js/bootstrap.js"></script>

	
	
	
	<link rel="stylesheet" type="text/css" href="jstree/_lib/jquery.ui.autocomplete.css" />
	<link rel="stylesheet" type="text/css" href="jstree/_lib/jquery.ui.theme.css" />
	<link href="jstree/bootstrap/css/bootstrap.css" rel="stylesheet">
    
	
	
	<!-- 
	<link type="text/css" rel="stylesheet" href="../_docs/syntax/!style.css"/>
	<link type="text/css" rel="stylesheet" href="../_docs/!style.css"/>
	<script type="text/javascript" src="../_docs/syntax/!script.js"></script>
	 -->
	 
</head>
<body>


    <div class="row">
    <div class="span8">
    
    <ul class="nav nav-tabs" id="myTab">
<li class="active"><a href="#bioportal-codes">Bioportal Codes</a></li>
<li><a href="#custom-codes">Custom Codes</a></li>
<!-- <li><a href="#bulk-codes">Bulk Codes</a></li>  -->
</ul>
 
<div class="tab-content">
<div class="tab-pane active" id="bioportal-codes">

<div class="ui-widget">
<label class="ui-label" for="bio">Bioportal Ontology Lookup: </label>
<input style="width: 550px;" id="bio" /> 
<input type="button" id="clear-bio" value="clear" />
<label class="ui-label-desc" for="bio">Type few letters, eg type "sys" for Systematized Nomenclature of Medicine, International Version<br> eg type "int" for International Classification of Diseases, Version 10 codes </label>
</div>
<br>

<div id="debug-bio" class="ui-widget-debug">
<label>id: </label><input style="width: 40px;" type="text" value="" name="bio-id" id="bio-id"/>
<label>ontologyId: </label><input style="width: 40px;" type="text" value="" name="bio-ontologyId" id="bio-ontologyId"/>
</div>

<br>
<div class="ui-widget">
<label class="ui-label" for="biot">Bioportal Term Lookup: </label>
<input style="width: 550px;" id="biot" />
<input type="button" id="clear-biot" value="clear" />
<label class="ui-label-desc" for="bio">Type few letters, eg heart </label>

<br>


<!-- <input type="checkbox" id="c2" value="c2"><label for="c2">show siblings </label>  -->

</div>

<br>


<!-- <input type="checkbox" id="c1" value="c1"><label for="c1">include tree structure </label> -->

<div class="ui-widget">
    <!-- <label class="checkbox">
    <input type="checkbox" name="keywords" value="__option__">include tree structure
</label>  -->
    <input type="button" id="addtest" value="add node"  /> 
</div>    




<br>

<div id="debug-biot" class="ui-widget-debug">
<label>ontologyVersionId: </label><input style="width: 40px;" type="text" value="" name="biot-ontologyVersionId" id="biot-ontologyVersionId"/>
<label>ontologyId: </label><input style="width: 40px;" type="text" value="" name="biot-ontologyId" id="biot-ontologyId"/><br>
<label>ontologyDisplayLabel: </label><input style="width: 500px;" type="text" value="" name="biot-ontologyDisplayLabel" id="biot-ontologyDisplayLabel"/><br>
<label>recordType: </label><input style="width: 100px;" type="text" value="" name="biot-recordType" id="biot-recordType"/>
<label>objectType: </label><input style="width: 40px;" type="text" value="" name="biot-objectType" id="biot-objectType"/><br>
<label>conceptId: </label><input style="width: 400px;" type="text" value="" name="biot-conceptId" id="biot-conceptId"/><br>
<label>conceptIdShort: </label><input style="width: 400px;" type="text" value="" name="biot-conceptIdShort" id="biot-conceptIdShort"/><br>
<label>preferredName: </label><input style="width: 400px;" type="text" value="" name="biot-preferredName" id="biot-preferredName"/><br>
<label>contents: </label><input style="width: 400px;" type="text" value="" name="biot-contents" id="biot-contents"/><br>
<label>isObsolete: </label><input style="width: 400px;" type="text" value="" name="biot-isObsolete" id="biot-isObsolete"/><br>

</div>

<br>

</div>
<div class="tab-pane" id="custom-codes">
<div class="ui-widget">
	<label class="ui-label" for="name">Name: </label>
	<input style="width: 550px;" id="custom-name" /> 
	<input type="button" id="clear-custom-name" value="clear" />
	</div>
	<br>

    <div class="ui-widget">
	<label class="ui-label" for="code">Code: </label>
	<input style="width: 550px;" id="custom-code" /> 
	<input type="button" id="clear-custom-code" value="clear" />
	</div>
	<br>

    <input type="button" id="addcustom" value="add node" class="ui-widget-button"/>    
    

</div>
<!-- <div class="tab-pane" id="bulk-codes">...</div>  -->
</div>

<!--  <a class="btn" data-toggle="modal" href="#myModal">Launch Modal</a> -->


<div class="modal hide" id="myModal"><!-- note the use of "hide" class -->
  <div class="modal-header">
    <button class="close" data-dismiss="modal">×</button>
    <h3>Contacting Bioportal</h3>
  </div>
  <div class="modal-body">
    <div class="progress progress-striped active" id="progress">
    <div class="bar" style="width: 100%;"></div>
    </div>
    
  
    <label id="containercb-label">Please be patient</label>
    <div class="containercb" id="containercb" style="list-style-type: none; white-space: nowrap;">

    </div>

    <input style="width: 150px; visibility: hidden;" id="show-siblings-parent" />  
    <a href="#" id="show-siblings" style="visibility: hidden;" class="btn">Show Siblings</a>
    
  </div>
  <div class="modal-footer">
    <a href="#" id="modal-close" class="btn" data-dismiss="modal">Close</a> <!-- note the use of "data-dismiss" -->
    <a href="#" id="modal-done" class="btn btn-primary">Done</a>
  </div>
</div>   

<div class="modal hide" id="myModal_i2b2"><!-- note the use of "hide" class -->
  <div class="modal-header">
    <button class="close" data-dismiss="modal">×</button>
    <h3>Transfering to i2b2</h3>
  </div>
  <div class="modal-body">
    <div class="progress progress-striped active" id="progress_i2b2">
    <div class="bar" style="width: 100%;"></div>
    </div>
     
    <label id="containercb-label-i2b2">Please be patient</label>
    <br><br>
    <a href="http://bru2.brisskit.le.ac.uk/i2b2/main/" id="link1" target="_blank">http://bru2.brisskit.le.ac.uk/i2b2/main/</a>
    
  </div>
  <div class="modal-footer">
    <a href="#" id="modal-close" class="btn" data-dismiss="modal">Close</a> <!-- note the use of "data-dismiss" -->   
  </div>
</div>    
    
    </div>
    <div class="span2">

<div class="ui-widget-white">


<input type="button" id="remove_1" value="remove node" />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type="button" id="rename" value="rename node" />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type="button" id="deselect" value="deselect node" />
<br>
<br>

<div id="tree" name="tree"></div>
<br>

<input type="button" id="export" value="export to i2b2" />

<!-- 
<input type="button" id="t1" value="t1" />
<input type="button" id="t2" value="t2" />
<input type="button" id="t3" value="t3" />
 -->

</div>

</div>
    </div>






 



<!-- 
<input type="button" id="add" value="add folder" />
<br>
<input type="button" id="add" value="add node" />
<br>

http://code.google.com/p/jstree/issues/detail?id=668

 -->


































 <script type="text/javascript">
 
 var top_parent;
 var currentlevel = 0;
 
 $('#myTab a').click(function (e) {
	    e.preventDefault();
	    $(this).tab('show');
	    })
  
		function getURLParameter(name) {
			    return decodeURI(
			        (RegExp(name + '=' + '(.+?)(&|$)').exec(location.search)||[,null])[1]
			    );
	    }

		var debug = getURLParameter("debug");
		//alert(debug)
		if (debug == "null")
		{
			$("#debug-bio").hide();
			$("#debug-biot").hide();
		}
		
		
        $(document).ready(function()
        {
        	$("#containercb").hide();
        	
        	var tree = $("#tree");
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
                                                 "children" : [ ] 
                                               }
                                			   , 
                                               { "data" : "child2",
                                                  "attr" : { "id" : "child2.id" },
                                                  "children" : [ ] 
                                               }
                                             ]
                            },
                        ]
                    },
                    "plugins" : [ "themes", "json_data", "crrm", "ui", ,"dnd" ]
                });
           
            
            $("#add").click(function() {               
                $("#tree").jstree("create", $("#child1\\.id"), "inside",  { "data" : "child2" },
                        function() { alert("added"); }, true);
            });
            
            $("#remove_1").click(function () {
            		        $("#tree").jstree("remove");
            });
            
            $("#deselect").click(function () {
		        $("#tree").jstree("deselect_all");
			});
            
            $("#rename").click(function () {
            	$("#tree").jstree("rename"); 
			});
            
            $("#clear-bio").click(function () {
            	$("#bio").val(""); 
            	$("#biot").val("");
			});
            
            $("#clear-biot").click(function () {
            	$("#biot").val(""); 
			});

            $("#addcustom").click(function () {
                //alert('addcustom');
                
                var a = $.jstree._focused().get_selected();
                
                //alert(a.attr("metadata"));
                
                var cl = parseInt(a.attr("metadata"));
                
                if (cl == null || cl == "undefined")
                {
                	cl = 0;
                }
                else
                {
                    cl = cl + 1;	
                }
                
                //alert(cl);
                
            	//$("#tree").jstree("create",null, "inside",$('#custom-name').val() + "(" + $('#custom-code').val() + ")",false,true);
            	//var node = $("#tree").jstree("create",null, "inside", {attr : { id: $('#custom-code').val() }, metadata : { id : cl }, data: $('#custom-name').val() + "(" + $('#custom-code').val() + ")" },false,true);
            	var node = $("#tree").jstree("create",null, "inside", {attr : { id: $('#custom-code').val() }, metadata : { id : cl }, data: $('#custom-name').val() },false,true);
            	
            	
            	node.attr("metadata",cl);              
                var b = $.jstree._focused().get_selected();
                //alert(b.attr("id"));
            });
            	                       
            $("#addtest").click(function () {
            	//alert("addtest");
            	$('#myModal').modal({show: true});
            	
            	$("#progress").show();
            	
            	$("#show-siblings-parent").val("");
            	//$("#tree").jstree("create", $("#somenode"), "inside", { "data":"new_node" });
            	//$("#tree").jstree("create", $("#somenode"), "inside", { "data":"new_node" }, false, true);
            	//$("#tree").jstree("create", $("#somenode"), "inside", { "data":"new_node" }, false, true);
            	
            	var parentNodeID = $.jstree._focused().get_selected().attr('id');
            	
            	var myTreeContainer = $.jstree._reference(tree).get_container();
                var allChildren=myTreeContainer.find("li");
                //alert(allChildren);
                
                var myList="";
                for(var i=0;i<allChildren.length;i++){
                    if(myList!="") myList+= " | ";
                    myList+= $(allChildren[i]).attr("id");
                }
                //$("#output").html(myList);
                //alert(myList);
           
                $("#tree").jstree("close_all", -1);
                
            	$.ajax({
                    url: "http://" + window.location.hostname + ":" + window.location.port + "/OntologyBuilder/rest/service/parents/getParents?ontologyid=" + $("#biot-ontologyId").val() + "&conceptid=" + $("#biot-conceptIdShort").val() + "&ontologyVersionId=" + $("#biot-ontologyVersionId").val(),
                    dataType: "json",
                    data: { },
                    success: function( data ) {                           	
                    	$.each(data, function(index) {
				            //alert("x1 " + data[index].parentid + " " + data[index].id + " " + data[index].fullId + " " + data[index].label);
				            
				            var p_parentid = data[index].parentid;
				            if (p_parentid != null) { p_parentid = p_parentid.replace(".", "p"); }
				            
				            var p_id = data[index].id;
				            if (p_id != null) { p_id = p_id.replace(".", "p"); }
				            
				            if (myList.indexOf(p_id) < 0)
				            	{
				            	//$("#tree").jstree("create", $("#" + p_parentid), "inside", {attr : { id: p_id }, data: data[index].label + "(" + p_id + ")" + " p(" + p_parentid + ")" },false,true);
				            	//$("#tree").jstree("create", $("#" + p_parentid), "inside", {attr : { id: p_id }, metadata : { id : index }, data: data[index].label + "(" + p_id + ")" + " p(" + p_parentid + ")" },false,true);
				            	
				            	//alert(p_id + "" + p_parentid + " " + data[index].label);
				            	
				            	//var node = $("#tree").jstree("create", $("#" + p_parentid), "inside", {attr : { id: p_id }, metadata : { id : index }, data: data[index].label + "(" + p_id + ")" + " p(" + p_parentid + ")" },false,true);
				            	var node = $("#tree").jstree("create", $("#" + p_parentid), "inside", {attr : { id: p_id }, metadata : { id : index }, data: data[index].label },false,true);
				            	
				            	
				            	
				            	currentlevel = index;
				            	
				            	node.attr("metadata",currentlevel);
				            	
				            	//alert(n.inst.get_path().length)
				            	//newNode.description = "1";
				            	}
				            
				           				            
				            if (index == data.length-1)
				            {
				            	top_parent = $("#" + p_parentid);
				            	
				            	//alert("x2 " + p_parentid);
				            	$("#show-siblings-parent").val(p_parentid); 
				            	
				            	if (p_parentid == null)
				            	{
				            		$("#containercb").html("");
				            		$("#containercb-label").text(data[index].label + " also has no associated tree structure or siblings.");
				           	
				            	}
				            	else
				            	{
				            		$("#containercb-label").text(data[index].label + " also has the following siblings, would you like to add them ?");					            		
				            		$("#show-siblings").click();
					            	
				            	}
				            	
				            	
				            	//$("#containercb-label").text(data[index].label + " also has the following siblings, would you like to add them ?");
				            	
				            	
				            	
				            	
				            }
				            	
				            
				            //$("#tree").jstree("create", p_parentid , "inside", {attr : { id: p_id }, data: data[index].label + "(" + p_id + ")" },false,true);


			        	});
                    	
                    	$("#progress").hide();
                        /*
                    	response( $.map( data, function( item ) {
                            return {
                            	
                            }
                        })
                        );
                        */
                        
                    }
                });
            	
            	
            	
            	
            	//$("#demo4").jstree("create","#" + parentNodeID,"inside",folderName,false,true);
            	
            	//alert(parentNodeID);
            	
            	//$("#tree").jstree("create"); 
            	
            	//$("#tree").jstree("create",null, "inside",$('#biot').val() + "(" + $('#biot-conceptIdShort').val() + ")",false,true);

            });
            
        
            
            var availableTags = [
				"ActionScript",
				"AppleScript",
				"Asp",
				"BASIC",
				"C",
				"C++",
				"Clojure",
				"COBOL",
				"ColdFusion",
				"Erlang",
				"Fortran",
				"Groovy",
				"Haskell",
				"Java",
				"JavaScript",
				"Lisp",
				"Perl",
				"PHP",
				"Python",
				"ICD10",
				"ICD9",
				"Snomed"
				];

            /*
                $("#bio").autocomplete({
				source: availableTags,
			    messages: {
			        noResults: '',
			        results: function() {}
			    }
				});
            */
                
            
            /*
            $("#bio").autocomplete({
                source: "http://localhost:8080/OntologyBuilder/rest/service/ontologies/getAll",
                minLength: 2,                
                select: function( event, ui ) {
        			$( "#bio" ).val( ui.name + " / " + ui.name );
        			return false;
        		}
            });
            */
            
            
            $("#bio").autocomplete({
                source: function( request, response ) {
                    $.ajax({
                        url: "http://" + window.location.hostname + ":" + window.location.port + "/OntologyBuilder/rest/service/ontologies/getAll",
                        dataType: "json",
                        data: {
                                q: request.term
                                //c: 10 //items number to retrieve
                                 },
                        success: function( data ) {         
                            response( $.map( data, function( item ) {                                
                            	return {
                            		id2 : item.id, 
                                	id : item.ontologyId,
                                    label: item.name,// + ": " + request.term,
                                    value: item.name// + ": " + request.term                                                            
                                }                                                                                               
                            }));
                        }
                    });
                },
                minLength: 1,
                select: function( event, ui ) {
                	 //alert(ui.item.id);                	 
                	 $("#bio-id").val(ui.item.id2);                              	
                	 $("#bio-ontologyId").val(ui.item.id);  
                	 $("#biot").val("");
                },
                open: function() {
                },
                close: function() {
                },
                messages: {
			        noResults: '',
			        results: function() {}
			    }
            });
            
            
            $("#biot").autocomplete({
                source: function( request, response ) {
                    $.ajax({
                        url: "http://" + window.location.hostname + ":" + window.location.port + "/OntologyBuilder/rest/service/terms/getTerm?ontologyid=" + $("#bio-ontologyId").val(),
                        dataType: "json",
                        data: {
                                q: request.term
                                //c: 10 //items number to retrieve
                                 },
                        success: function( data ) {
         
                            response( $.map( data, function( item ) {
                                return {
                                	id : item.ontologyVersionId,
                                    label: item.preferredName,// + ": " + request.term,
                                    value: item.preferredName,// + ": " + request.term
                                    ontologyVersionId : item.ontologyVersionId,
                                    ontologyId : item.ontologyId,
                                    ontologyDisplayLabel : item.ontologyDisplayLabel,
                                    recordType : item.recordType,
                                    objectType : item.objectType,
                                    conceptId : item.conceptId,
                                    conceptIdShort : item.conceptIdShort,
                                    preferredName : item.preferredName,
                                    contents : item.contents,
                                    isObsolete : item.isObsolete                                                            
                                }
                            }));
                        }
                    });
                },
                minLength: 1,
                select: function( event, ui ) {
                  	 $("#biot-ontologyVersionId").val(ui.item.ontologyVersionId); 
                  	$("#biot-ontologyId").val(ui.item.ontologyId); 
                  	$("#biot-ontologyDisplayLabel").val(ui.item.ontologyDisplayLabel); 
                  	$("#biot-recordType").val(ui.item.recordType); 
                  	$("#biot-objectType").val(ui.item.objectType); 
                  	$("#biot-conceptId").val(ui.item.conceptId); 
                  	$("#biot-conceptIdShort").val(ui.item.conceptIdShort); 
                  	$("#biot-preferredName").val(ui.item.preferredName); 
                  	$("#biot-contents").val(ui.item.contents); 
                  	$("#biot-isObsolete").val(ui.item.isObsolete); 
                  	
                	// alert(ui.item.ontologyVersionId);
                	 //$("#bio-ontologyId").val(ui.item.id);           
                },
                open: function() {
                },
                close: function() {
                },
                messages: {
			        noResults: '',
			        results: function() {}
			    }
            });
            
            			
            
            
            
            /*
            var myArr = [];
            	 
         	 $.ajax({
         	   type: "GET",
         	   url: "http://rest.bioontology.org/bioportal/ontologies?apikey=473e4e23-6ca6-4d54-a7aa-bbd9a24bf339", // change to full path of file on server
         	   dataType: "xml",
         	   success: parseXml,
         	   //complete: setupAC,
         	   failure: function(data) {
         	     alert("XML File could not be found");
         	   }
         	 });
         	 
         	 function parseXml(xml)
         	 {
         	   //find every query value
         	   $(xml).find("ontologyBean").each(function()
         	   {
         		   alert("1");
         	     //myArr.push($(this).attr("label"));
         	   });
         	 }
            
            
            
                $("#bio").autocomplete({
                	source: function( request, response ) {
                    	$.ajax({                   		
                    	url: "http://localhost:8080/OntologyBuilder/rest/service/ontologies/getAll",                    	                        	
                    	data: {
                    	featureClass: "P",
                    	style: "full",
                    	maxRows: 2,
                    	name_startsWith: request.term
                    	},
                    	success: function( data ) {
                    	response( $.map( data.name, function( item ) {
                    	return {
                    	label: item.name,
                    	value: item.name
                    	}
                    	}));
                    	}
                    	});
                    	},
    			    messages: {
    			        noResults: '',
    			        results: function() {}
    			    }
    				});

            */
            
            /*
            
                $( "#city" ).autocomplete({
                	source: function( request, response ) {
                	$.ajax({
                	url: "http://ws.geonames.org/searchJSON",
                	dataType: "jsonp",
                	data: {
                	featureClass: "P",
                	style: "full",
                	maxRows: 12,
                	name_startsWith: request.term
                	},
                	success: function( data ) {
                	response( $.map( data.geonames, function( item ) {
                	return {
                	label: item.name + (item.adminName1 ? ", " + item.adminName1 : "") + ", " + item.countryName,
                	value: item.name
                	}
                	}));
                	}
                	});
                	},
                	minLength: 2,
                	select: function( event, ui ) {
                	log( ui.item ?
                	"Selected: " + ui.item.label :
                	"Nothing selected, input was " + this.value);
                	},
                	open: function() {
                	$( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
                	},
                	close: function() {
                	$( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
                	}
                	});
            
           */ 
            
            
        });
        
        $("#actionButton").click(function() {
            alert($("#storySelector").jstree("is_selected", $("#iteration1")));
        });
        
        $("#export").click(function() {
        	var a = $.jstree._focused().get_selected();
            //alert("export " + a.attr("id"));
            
            $("#containercb-label-i2b2").text("Please be patient");
			           	
            $('#myModal_i2b2').modal({show: true});
            $("#progress_i2b2").show();
                    	
            
            //var xmlString = $("#tree").jstree("get_xml");              
            //alert(xmlString);
            
            data12 = $("#tree").jstree("get_json", -1);
            console.log(JSON.stringify(data12));
            /*
            $.ajax({
            	type: "POST",
            	url: "http://" + window.location.hostname + ":" + window.location.port + "/OntologyBuilder/rest/service/settree",
            	data: JSON.stringify(data12)
            	}).done(function( msg ) {
            	alert( "Data Saved: " + msg );
            });
            */
            $.ajax({
                type: "POST",
                url: "http://" + window.location.hostname + ":" + window.location.port + "/OntologyBuilder/rest/service/settree",
            	data: JSON.stringify(data12),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: function(result){
                
                	//alert('xxx');
                	
                	if(!result) {
                        //alert('A problem has occured, refresh browser and start again');
                        $("#containercb-label-i2b2").text("A problem has occured, refresh browser and start again");
			           	
                    }
                	else
                	{      
                		$("#progress_i2b2").hide();
                		$("#containercb-label-i2b2").text("Success - Login to link below as demo created and select project " + result.d);
			           	
                		
                    	
                		//alert(result.d);                   	
                	}
                	
                	//alert($data);
                	
                },
                failure: function(errMsg) {
                    alert(errMsg);
                }
            });
        });
        
        $("#t1").click(function() {
        	var a = $.jstree._focused().get_selected();
            alert(a.attr("data"));
            
            $("#tree").jstree("create", $("#C0336527") , "inside","dd",false,true);
            
            //alert("to do");
        });
        
        $("#t2").click(function() {
        	var a = $.jstree._focused()._get_parent($.jstree._focused().get_selected());
            alert(a.attr("id"));
            
            //alert("to do");
        });
        
        $("#t3").click(function() {
        	var a = $.jstree._focused().get_selected();
            alert(a.attr("id"));
            
            alert("to do");
        });
        
        $("#c1").click(function() {
            //alert("to do");
            
            
            
            $("#tree").jstree("create", $("#C0232457") , "inside","dd",false,true);
            
            
        });
        
        $("#c2").click(function() {
        	
        	
        	$("#tree").jstree("create", $("#C1140118"), "inside","deed",false,true);
        	
            //alert("to do");
        });
        
 		$("#modal-close").click(function() {
 			$("#containercb").hide();
 			$("#containercb-label").text("");
        });
 		
 		$("#modal-done").click(function() {
 			$('#myModal').modal('hide');
 			$("#containercb").hide();
 			$("#containercb-label").text("");
 			
 			var tval = [];
 			var tname = [];
 			
 	        $(':checkbox:checked').each(function(i){
 	          tval[i] = $(this).val();
 	          tname[i] = $(this).attr('name');
	          
 	         //var node = $("#tree").jstree("create", top_parent, "inside", {attr : { id: tval[i] }, metadata : { id : currentlevel }, data: tname[i] + "(" + tval[i] + ")" },false,true);
 	         var node = $("#tree").jstree("create", top_parent, "inside", {attr : { id: tval[i] }, metadata : { id : currentlevel }, data: tname[i] },false,true);
 	 	      
 	         
 	         // $("#tree").jstree("create", $("#" + data[index].parentid), "inside", {attr : { id: data[index].id }, data: data[index].label + "(" + data[index].id + ")" + " p(" + data[index].parentid + ")" },false,true);
			 node.attr("metadata",currentlevel);
 	         	
 	          //alert(val[i]);
 	        });
 	        
        });
 
		$("#show-siblings").click(function() {    		
			$("#containercb").html("");
			
			var  p_siblings_parent = $("#show-siblings-parent").val();
			
			if ($("#biot-ontologyVersionId").val() == "44103")
	        {
				p_siblings_parent = p_siblings_parent.replace("p", ".");
	        }
			
			$.ajax({
                url: "http://" + window.location.hostname + ":" + window.location.port + "/OntologyBuilder/rest/service/siblings/getSiblings?ontologyversionid=" + $("#biot-ontologyVersionId").val() + "&conceptid=" + p_siblings_parent,
                dataType: "json",
                data: { },
                success: function( data ) {     
                	if(!data) {
                        //alert('empty');
                        return;
                    }
                	else
                	{
                		$("#containercb").show();	
                	}
                	
                	$.each(data, function(index) {
	            		var p_id = data[index].id;
	            		if (p_id != null) { p_id = p_id.replace(".", "p"); }
			            
	            		
	            		
 						var $ctrl = $('<label />').html('&nbsp;' + data[index].label )
                        .prepend($('<input/>').attr({ 
                        	   id:    p_id
					          ,name:  data[index].label
					          ,value: p_id
					          ,type:  'checkbox'
					          }));
 						
 						$("#containercb").append($ctrl);
		        	});     
                	
                	
                }
            });
			
			//$("#containercb").show();
			
			//$("#containercb-label").text(data[index].label + " also has the following siblings, would you like to add them ?");
        	
			
        });
        
        
 </script>
 
<TABLE  border=0> 
<br>      
<br>
<br>
<TD vAlign=top >            
<!--WEBBOT bot="HTMLMarkup" startspan ALT="Site Meter" -->
<script type="text/javascript" language="JavaScript">var site="s20shajid"</script>
<script type="text/javascript" language="JavaScript1.2" src="http://s20.sitemeter.com/js/counter.js?site=s20shajid">
</script>
<noscript>
<a href="http://s20.sitemeter.com/stats.asp?site=s20shajid" target="_top">
<img src="http://s20.sitemeter.com/meter.asp?site=s20shajid" alt="Site Meter" border="0"/></a>
</noscript>
<!-- Copyright (c)2005 Site Meter -->
<!--WEBBOT bot="HTMLMarkup" Endspan i-checksum="40123" -->    
</TD>				
</TR>
</TABLE>
                    
</body>
</html>