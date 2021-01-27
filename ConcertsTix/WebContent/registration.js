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
		
		let username = $("input[name=username]").val();
	    let password = $("input[type=password]").val();
	    let firstName = $("input[name=firstName]").val();
	    let lastName = $("input[name=lastName]").val();
	    let gender = $("select[name=gender]").val();
	    let dateOfBirth = $("input[name=dateOfBirth]").val();
		let role = $("select[name=role]").val();
		
		// sklanjanje error poruke posle svakog submita
		$(".error").remove();
		
		if(username.length < 1) {
    		$("input[name=username]").after('<span class="error" style="color:#828282"> This field is required!</span>');
    		success = false;
    	}

		if(password.length < 1) {
    		$("input[name=password]").after('<span class="error" style="color:#828282"> This field is required!</span>');
    		success = false;
    	}

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
        	"username" : $("input[name=ime]").val(),
        	"password" : $("input[name=prezime]").val(),
        	"name" : $("input[name=korisnickoIme]").val(),
        	"surname" : $("input[name=lozinka]").val(),
        	"gender" : $("input[name=email]").val(),
			"dateOfBirth" : $("input[name=brojTelefona]").val(),

        };

		

		
	});
	
});