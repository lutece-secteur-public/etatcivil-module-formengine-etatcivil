$(document).ready(function() {
	$("#btn-top").click( function(event) {
		vUrl = location.href.replace( location.hash,"") + '#top'; 
		$(this).attr('href', vUrl ); 
	});
	
	 // Tooltip
	 if( $('.tooltips').length > 0 ){
		$(".tooltips").tooltip({
		  selector: "a[data-toggle='tooltip']"
		})
	};
});