$(function(){
	// sideNav activation
	$(".button-collapse").sideNav();

	$('.dropdown-button').dropdown({
      inDuration: 300,
      outDuration: 225,
      constrainWidth: false, // Does not change width of dropdown to that of the activator
      hover: true, // Activate on hover
      gutter: 0, // Spacing from edge
      belowOrigin: false, // Displays dropdown below the button
      alignment: 'left', // Displays dropdown with edge aligned to the left of button
      stopPropagation: false // Stops event propagation
    }
  );
    // slide activation
	$('.slider').slider();

	$('.scrollspy').scrollSpy();

    $(window).scroll(function(event) {

        if($(this).scrollTop() >1) {
        	// $('#primarypart1').removeClass('after');
        	// $('#primarypart2').removeClass('after');
        	// $('nav #home').css('padding-top','0px');
        	// $('nav #home').css('padding-left','10px');
        	// $('nav #home').css('font-size','2.1rem');
            
        	// $('nav .brand-logo2').css('display','none');

            $('.textheader').css('opacity','0');
            $('#home').css('opacity','1');
            $('#home').removeClass('home');
        } else {
        	// $('#primarypart1').addClass('after');
        	// $('#primarypart2').addClass('after');
        	// $('nav #home').css('padding-left','50px');
        	// $('nav #home').css('padding-top','100px');
        	// $('nav #home').css('font-size','4rem');
        	// $('nav .brand-logo2').css('padding-left','150px');
        	// $('nav .brand-logo2').css('padding-top','100px');
        	// $('nav .brand-logo2').css('font-size','4rem');
        	// $('nav .brand-logo2').css('display','block');
            $('.textheader').css('opacity','1');
        	$('#home').css('opacity','0');
        	
        }
    })

    $(window).scroll(function(event) {
        if($(this).scrollTop() > 164) {
            $('.fixnav').css('box-shadow',' 0 2px 2px 0 rgba(0,0,0,0.14), 0 1px 5px 0 rgba(0,0,0,0.12), 0 3px 1px -2px rgba(0,0,0,0.2)');
            $('.fixwrapper').addClass('z-depth-5');
        } else {
            $('.fixnav').css('box-shadow',' 0px 0px 0px 0px #9c27b0');
            $('.fixwrapper').removeClass('z-depth-5');
            
            
        }
    })


    $('#content-book').mouseover(function(event) {
    	$('#content-book').addClass('z-depth-5');
    })

    $('#content-book').mouseout(function(event) {
    	$('#content-book').removeClass('z-depth-5');
    })

    $('#test2 .card').each(function(){
    	$(this).mouseover(function(event){
    		$(this).addClass('z-depth-5');
    	})
    })
    	

    $('#test2 .card').each(function(){
    	$(this).mouseout(function(event){
    		$(this).removeClass('z-depth-5');
    	})
    })


    $('.carousel.carousel-slider').carousel({fullWidth: true});

    // tim kiem

    $('#close').mouseup(function(event) {
    	$('#searchp #search' ).val('');
    })

    $('.scrollspy').scrollSpy();

    $(window).scroll(function(event) {
        if($(this).scrollTop() > 65) {
            $('#scrollspy').css('position','fixed');  
            $('#scrollspy').css('margin-top','-60px');

            $('#scrollspy').addClass('pined');
        } else {
            // $('.scrollspy').addClass('pin-top'); 
            $('#scrollspy').css('position','relative');
            $('#scrollspy').removeClass('pined');
             $('#scrollspy').css('margin-top','15px');
        }
    })
    
   $('.parallax').parallax();

   $('.modal').modal();

   $('select').material_select();

   
})
