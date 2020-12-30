$(function(){
	   $('.selAllTab').click(function(){
	      if (this.checked) {
	         $(".chkcls").each(function() {
	             this.checked = true;                        
	         });
	      } else {
	         $(".chkcls").each(function() {
	             this.checked = false;                       
	         });
	      }	
	   });
	   
	   $(".selAllTab").click(function(){
		   $(".chkcls")
			.prop('checked', this.checked);
	   });
	   
	   
	   
	   
	 
	 /*  $(".chkcls").click(function(){
	      var numberOfCheckboxes = $(".chkcls").length;
	      var numberOfCheckboxesChecked = $('.chkcls:checked').length;
	      if(numberOfCheckboxes > numberOfCheckboxesChecked) {
	         $(".chkcls").prop("checked", true);
	      } else {
	         $(".chkcls").prop("checked", false);
	      }
	   });*/
	});