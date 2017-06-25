window.onload = function() {
	var canvas = document.getElementById("canvas"), 
		context = canvas.getContext("2d"), 
		width = canvas.width = window.innerWidth, 
		height = canvas.height = window.innerHeight,
		xscale = width / ( 2 * Math.PI), 
		yscale = height / 8;

	context.translate(0, height / 2);
	context.scale(1, -1);
	for(var phi = 0; phi < 2 * Math.PI; phi += 0.01) {		
		var sin = Math.sin(phi);
		var x = xscale * phi;
		var y = yscale * sin; 
		context.fillRect(x, y, 1, 1);
	}
};