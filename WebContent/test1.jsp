<html>
<head>
<style>
p { color:red; margin:5px; cursor:pointer; }
p:hover { background:yellow; }
</style>
<script src="jquery-1.6.1.min.js"></script>
</head>
<body>
<button id="mybutton" type="button">Click Me!</button> 
</body>

<script>
var cohort = '5,';

//alert("d");

$("#mybutton").click(function() {
	//alert("Handler for .click() called.");
	mvf1();
});


function callbacki2b2(json)
{
	alert( json.status );
}

function mvf1()
{      
	/*
        $.ajax({
        	  dataType: 'jsonp',
        	  jsonp: 'jsonp_callback',
        	  url: 'http://hack5.brisskit.le.ac.uk:8080/i2b2WS/rest/service/i2b2callback3/5',
        	  success: function (json) {    
        	  }
        	});
	
	i2b2.ExportXLS.model.prsRecord.sdxInfo.sdxDisplayName
	sdxData.sdxInfo.sdxDisplayName
	
	*/
        
        $j.ajax({
      	  dataType: 'jsonp',
      	  jsonp: 'jsonp_callback',
      	  url: 'http://' + window.location.hostname  + ':8080/i2b2WS/rest/service/i2b2callback1/'+cohort.substr(0,cohort.length-1),
      	  success: function (json) {     		
      	  }
      	});       
}
</script>
</html>
