function addUserTr(user) {
	let div = $('<div class="panel-container2"></div>');
	let tdUsername = $('<label name="username">' + user.username + '</label>');
	let tdPassword = $('<label name="password">' + user.password + '</label>');
	let tdName = $('<label name="name">' + user.name + '</label>');
	let tdSurname = $('<label name="surname">' + user.surname + '</label>');
	let tdGender = $('<label name="gender">' + user.gender + '</label>');
	let tdDateOfBirth = $('<label name="dateOfBirth">' + user.dateOfBirth + '</label>');
	let tdRole = $('<label name="role">' + user.role + '</label>');
	div.append(tdUsername).append(tdPassword).append(tdName).append(tdSurname).append(tdGender).append(tdDateOfBirth).append(tdRole);
	div.appendTo('#container');
}


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
	    let gender = $("select[name=gender]").val();
	    let dateOfBirth = $("input[name=dateOfBirth]").val();
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
        	"username" : $("input[name=ime]").val(),
        	"password" : $("input[name=prezime]").val(),
        	"name" : $("input[name=korisnickoIme]").val(),
        	"surname" : $("input[name=lozinka]").val(),
        	"gender" : $("input[name=email]").val(),
			"dateOfBirth" : $("input[name=brojTelefona]").val(),

        };

		

		
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
		

		/*$.get({
			url: 'rest/users/list',
			success: function(users) {
				for (let user of users) {
					addUserTr(user);
				}
			}
		});*/

		
		$.post({
			url: 'rest/users/login',
			data: JSON.stringify({"username": username, "password": password}),
			contentType: 'application/json',
			success: function() {
				$('#success').text('User logged in!');
				$("#success").show().delay(3000).fadeOut();
			},
			error: function(message) {
				$('#error').text("User doesn't exist!");
				$("#error").show().delay(3000).fadeOut();
			}
				
			
		});

		

		
	});


	
});