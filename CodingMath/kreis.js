window.onload = function() {
	var canvas = document.getElementById("canvas"), 
		context = canvas.getContext("2d"), 
		width = canvas.width = window.innerWidth, 
		height = canvas.height = window.innerHeight;
	
	var	centerX = width / 2;
		centerY = height / 2, 
		radius = 100,
		phi = 0; 

	function update() {
		var x, y;
		
		x = centerX + radius * Math.cos(phi),
		y = centerY + radius * Math.sin(phi);

		if (phi < 2 * Math.PI) {
			context.clearRect(0, 0, width, height);
			context.fillRect(x, y, 40, 40);
			phi += 0.1;
		} else {
			radius -= 2;
			phi = 0;
		}
		if (radius > 20) {
			requestAnimationFrame(update);
		}
	}
	update();
};