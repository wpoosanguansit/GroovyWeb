window.onload = function()
{
  var canvas = document.getElementsByTagName("canvas");
  var scripts = document.getElementsByTagName("script");
  var code = null;
  for (var j = 0; j < scripts.length; j++) 
  {
	  if (scripts[j].type == "application/processing")
	  {
		  code = scripts[j].textContent;
	  }
  }
  
  for ( var i = 0; i < canvas.length; i++ )
  {
    Processing( canvas[i], code );
  }
};
