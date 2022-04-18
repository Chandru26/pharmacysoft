!function(){
	var Barchart3D={};	

Barchart3D.draw = function(id, data, wi, hi){
	var margin = {top: 40, right: 20, bottom: 80, left: 50},
		width = wi - margin.left - margin.right,
		height = hi - margin.top - margin.bottom;

	var div = d3.select("#"+id).append("div").attr("class", "tooltip").style("opacity", 0);
	
	var tip = d3.tip()
		  .attr('class', 'd3-tip')
		  .offset([-10, 0])
		  .html(function(d) {
			return "<strong>"+d.label+":</strong> <span style='color:red'>" + d.value + "</span>";
		  })
	
	var svg = d3.select("#"+id).attr("transform", "translate(" + margin.left + "," + margin.top + ")");
	
	svg.call(tip);
	
	var formatPercent = d3.format("");

	var x = d3.scale.ordinal().rangeRoundBands([0, width], .1);

	var y = d3.scale.linear().range([height, 0]);

	var xAxis = d3.svg.axis().scale(x).orient("bottom");

	var yAxis = d3.svg.axis().scale(y).orient("left").tickFormat(formatPercent);

	x.domain(data.map(function(d) { return d.label; }));
	y.domain([0, d3.max(data, function(d) { return d.value; })]);
	
	var barWidth = x.rangeBand();

	svg.append("g")
		.attr("class", "x axis")
		.attr("transform", "translate(0," + height + ")")
		.call(xAxis)
		.selectAll("text")
		.attr("y", 0)
		.attr("x", 9)
		.attr("dy", ".35em")
		.attr("transform", "rotate(30)")
		.style("text-anchor", "start");
	
	svg.append("line")
		.attr("x1", 0)
		.attr("y1", height+1)
		.attr("x2", width)
		.attr("y2", height+1)
		.attr("stroke-width", 1)
		.attr("stroke", "black");

	  
	svg.append("g")
		.attr("class", "y axis")
		.call(yAxis);
	
	svg.append("g")
		.attr("class", "y axis")	
		.append("text")
		.attr("transform", "rotate(-90)")
		.attr("y", 6)
		.attr("dy", "-3em")
		.style("text-anchor", "end")
		.text("Counts");

	  
	  
	for(var i=2; i<=6;i++){	  
		svg.selectAll(".bar"+i)
		.data(data)
		.enter().append("rect")
		.attr("class", "bgbar")
		.attr("x", function(d) { return (x(d.label)+i); })
		.attr("width",barWidth )
		.attr("y", function(d) { return (y(d.value)-i); })
		.attr("height", function(d) { return height - y(d.value); });      
	}
 
	svg.selectAll(".bar1")
		.data(data)
		.enter().append("rect")
		.attr("class", "bar")
		.attr("x", function(d) { return x(d.label)+1; })
		.attr("width",barWidth )
		.attr("y", function(d) { return y(d.value)-1; })
		.attr("height", function(d) { return height - y(d.value); })
		.on('mouseover', tip.show)
		.on('mouseout', tip.hide);
		
	}
	this.Barchart3D = Barchart3D;
}();