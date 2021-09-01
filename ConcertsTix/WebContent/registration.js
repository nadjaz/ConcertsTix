


$(document).ready(function() {

	
	$("input[type=text]")
    	.focus(function() {
    		// ukoliko nista nije uneseno u polje
    		// polje postaje plavo
    		// takodje se pored input polja dodaje poruka upozorenja
    		if($(this).val().length == 0) {
    	 		$(this).css("background-color", "#EAF2F8");
    	 		$(this).parent().append('<div id="message"> Enter some text!</div>');
				$("#message").css("color","#828282");
				$(".error").remove();
    		}
    	})	
    	.focusout(function() {
    		// kada je focus van tog input polja
    		// polje se zabeli
    		// i skloni se poruka upozorenja
    		$(this).css("background-color", "white");
    		$("#message").remove();
    	});	
	
	$("form[name=registerForm]").submit(function() {
		event.preventDefault();
		
		let success = true;
		
	    let firstName = $("input[name=firstName]").val();
	    let lastName = $("input[name=lastName]").val();
	    
		let role = $("select[name=role]").val();
		
		// sklanjanje error poruke posle svakog submita
		$(".error").remove();

		if(firstName.length < 1) {
    		$("input[name=firstName]").after('<span class="error" style="color:#828282"> This field is required!</span>');
    		success = false;
    	} else {
    		let nameReg = new RegExp('[A-Z][a-z]+');
    		if (!nameReg.test(firstName)) {
				$("input[name=firstName]").after('<span class="error" style="color:#828282"> Enter valid name!</span>');
				success = false;
			}
		}
		
		if(lastName.length < 1) {
    		$("input[name=lastName]").after('<span class="error" style="color:#828282"> This field is required!</span>');
    		success = false;
    	} else {
    		let nameReg = new RegExp('[A-Z][a-z]+');
    		if (!nameReg.test(lastName)) {
				$("input[name=lastName]").after('<span class="error" style="color:#828282"> Enter valid lastname!</span>');
				success = false;
			}
		}

		let data = {
        	"username" : $("input[name=username]").val(),
        	"password" : $("input[name=password]").val(),
        	"name" : $("input[name=firstName]").val(),
        	"surname" : $("input[name=lastName]").val(),
        	"gender" : $("select[name=gender]").val(),
			"dateOfBirth" : $("input[name=dateOfBirth]").val(),
			"role" : "BUYER"
        };

		$.post({
			url: 'rest/users/register',
			data: JSON.stringify(data),
			contentType: 'application/json',
			success: function() {
				$('#successRegister').text("User with username "  +  $("input[name=username]").val() + " succesfully registered!");
				$("#successRegister").show().delay(3000).fadeOut();
			},
			error: function(message) {
				$('#errorRegister').text("User with the same username already exists!");
				$("#errorRegister").show().delay(3000).fadeOut();
			}
		});

		
	});

	$("form[name=loginForm]").submit(function() {
		event.preventDefault();
		
		let success = true;
		
		let username = $("input[name=loginUsername]").val();
	    let password = $("input[type=password]").val();
	    
		// sklanjanje error poruke posle svakog submita
		$(".error").remove();
		
		if(username.length < 1) {
    		$("input[name=loginUsername]").after('<span class="error" style="color:#828282"> This field is required!</span>');
    		success = false;
    	}

		if(password.length < 1) {
    		$("input[type=password]").after('<span class="error" style="color:#828282"> This field is required!</span>');
    		success = false;
		}
		
		$.post({
			url: 'rest/users/login',
			data: JSON.stringify({"username": username, "password": password}),
			contentType: 'application/json',
			success: function() {
				$('#successLogin').text('User logged in!');
				$("#successLogin").show().delay(3000).fadeOut();
			},
			error: function(message) {
				$('#errorLogin').text("User doesn't exist!");
				$("#errorLogin").show().delay(3000).fadeOut();
			}
		});

		

		
	});


	
});