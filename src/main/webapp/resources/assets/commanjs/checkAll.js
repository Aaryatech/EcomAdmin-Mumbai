$(function(){
	   $('.selAllTab').click(function(){
	      if (this.checked) {
	         $(".chkcls").prop("checked", true);
	      } else {
	         $(".chkcls").prop("checked", false);
	      }	
	   });
	 
	   $(".chkcls").click(function(){
	      var numberOfCheckboxes = $(".checkboxes").length;
	      var numberOfCheckboxesChecked = $('.checkboxes:checked').length;
	      if(numberOfCheckboxes == numberOfCheckboxesChecked) {
	         $(".selAllTab").prop("checked", true);
	      } else {
	         $(".selAllTab").prop("checked", false);
	      }
	   });
	});