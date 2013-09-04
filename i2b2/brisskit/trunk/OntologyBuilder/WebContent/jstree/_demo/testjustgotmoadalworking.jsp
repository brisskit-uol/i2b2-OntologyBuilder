<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ontology Builder for i2b2</title>
<style type="text/css">
label#bio { display: block; width: 50px; }
</style>
    <!-- <script type="text/javascript" src="../_lib/jquery.js"></script>  
 -->
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
    
	
	
	<!-- 
	<link type="text/css" rel="stylesheet" href="../_docs/syntax/!style.css"/>
	<link type="text/css" rel="stylesheet" href="../_docs/!style.css"/>
	<script type="text/javascript" src="../_docs/syntax/!script.js"></script>
	 -->
	 
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


 
 <TABLE border="1">
<TR><TH rowspan="3" align="left">
Bioportal Codes
<!-- 0 -->

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

<input type="checkbox" id="c1" value="c1"><label for="c1">include tree structure </label>
<input type="checkbox" id="c2" value="c2"><label for="c2">show siblings </label>

</div>

<br>

<input type="button" id="addtest" value="add node"  class="ui-widget-button" />

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

</TH>
    <TH colspan="2"><!-- 1 --></TH>
    <TH rowspan="3" align="left" valign="top">
    
    
    <!-- 3 -->
    
    Custom Codes
    
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
    
    
    </TH>
<TR><TH><!-- 2 --> &nbsp;&nbsp;OR&nbsp;&nbsp;
<TR><TH><!-- 4 -->
</TABLE>


<!-- 
<input type="button" id="add" value="add folder" />
<br>
<input type="button" id="add" value="add node" />
<br>

http://code.google.com/p/jstree/issues/detail?id=668

 -->

<div class="ui-widget">


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

</div>
































 <script type="text/javascript">
  
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
 
            	$("#tree").jstree("create",null, "inside",$('#custom-name').val() + "(" + $('#custom-code').val() + ")",false,true);

            });
            	
            $("#addtest").click(function () {
            	//alert("addtest");
            	$('#myModal').modal({show: true});
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
                    url: "http://" + window.location.hostname + ":" + window.location.port + "/TestWS/rest/service/parents/getParents?ontologyid=" + $("#biot-ontologyId").val() + "&conceptid=" + $("#biot-conceptIdShort").val(),
                    dataType: "json",
                    data: { },
                    success: function( data ) {                           	
                    	$.each(data, function(index) {
				            //alert("x1 " + data[index].parentid + " " + data[index].id + " " + data[index].fullId + " " + data[index].label);
				            
				            if (myList.indexOf(data[index].id) < 0)
				            	{
				            	$("#tree").jstree("create", $("#" + data[index].parentid), "inside", {attr : { id: data[index].id }, data: data[index].label + "(" + data[index].id + ")" },false,true);
								}
				            	
				            
				            //$("#tree").jstree("create", data[index].parentid , "inside", {attr : { id: data[index].id }, data: data[index].label + "(" + data[index].id + ")" },false,true);


			        	});
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
                source: "http://localhost:8080/TestWS/rest/service/ontologies/getAll",
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
                        url: "http://" + window.location.hostname + ":" + window.location.port + "/TestWS/rest/service/ontologies/getAll",
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
                        url: "http://" + window.location.hostname + ":" + window.location.port + "/TestWS/rest/service/terms/getTerm?ontologyid=" + $("#bio-ontologyId").val(),
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
                    	url: "http://localhost:8080/TestWS/rest/service/ontologies/getAll",                    	                        	
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