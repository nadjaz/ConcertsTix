function listAllManifestations(manifestation) {
    	
    let div = $(".panel1");
    let div1 = $('<div class="inputManifestation"></div>');
	let label1 = $('<label for="name">Name:</label>');
    let labelName = $('<label name="name">' + manifestation.name + '</label><br>');
	let label2 = $('<label for="typeManifestation">Manifestation type:</label>');
	let labelTypeManifestation = $('<label name="typeManifestation">' + manifestation.typeManifestation + '</label><br>');
	let label3 = $('<label for="seatingNumber">Seating number:</label>');
	let labelSeatingNumber = $('<label name="seatingNumber">' + manifestation.seatingNumber + '</label><br>');
	let label4 = $('<label for="date">Date:</label>');
	let labelDate = $('<label name="date">' + manifestation.date + '</label><br>');
	let label5 = $('<label for="priceRegular">Regular price:</label>');
	let labelPriceRegular = $('<label name="priceRegular">' + manifestation.priceRegular + '</label><br>');
	let label6 = $('<label for="status">Status:</label>');
	let labelStatus = $('<label name="status">' + manifestation.status + '</label><br>');

	let label7 = $('<label for="location">Location:</label>');
	let labelLocation = $('<label name="location">' + manifestation.location.city + '</label><br>');

	let label8 =  $('<label for="image">Image:</label>');
	let labelImage =$('<img id="image" src="' + manifestation.image + '"/>'); 
	labelImage.width(100);
	labelImage.height(100);
	
	div.append(div1);
	div1.append(label1);
	div1.append(labelName);
	div1.append(label2);
	div1.append(labelTypeManifestation);
	div1.append(label3);
	div1.append(labelSeatingNumber);
	div1.append(label4);
	div1.append(labelDate);
	div1.append(label5);
	div1.append(labelPriceRegular);
	div1.append(label6);
	div1.append(labelStatus);
	div1.append(label7);
	div1.append(labelLocation)
	div1.append(label8);
	div1.append(labelImage);	
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
	
	// ________________________________________REQUEST TO REGISTER A USER
	$("form[name=registerForm]").submit(function() {
		event.preventDefault();
		
		let success = true;
		
		let username = $("input[name=username]").val();
		let password = $("input[name=password]").val();
	    let firstName = $("input[name=firstName]").val();
	    let lastName = $("input[name=lastName]").val();
	    
		//let role = $("select[name=role]").val();
		
		// sklanjanje error poruke posle svakog submita
		$(".error").remove();

		if(username.length < 5) {
			$("input[name=username]").after('<span class="error" style="color:#828282"> Username must be atleast 5 letters long!</span>');
			success = false;
		}

		if(password.length < 5) {
			$("input[name=password]").after('<span class="error" style="color:#828282"> Password must be atleast 5 letters long!</span>');
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
        	"username" : $("input[name=username]").val(),
        	"password" : $("input[name=password]").val(),
        	"name" : $("input[name=firstName]").val(),
        	"surname" : $("input[name=lastName]").val(),
        	"gender" : $("select[name=gender]").val(),
			"dateOfBirth" : $("input[name=dateOfBirth]").val()
			//"role" : "BUYER"
        };

		if(success) {
			$.post({
				url: 'rest/users/registerBuyer',
				data: JSON.stringify(data),
				contentType: 'application/json',
				success: function() {
					$('#successRegister').text("User with username "  +  $("input[name=username]").val() + " succesfully registered!");
					$("#successRegister").show().delay(3000).fadeOut();
					window.location.href="http://localhost:8080/ConcertsTix/homepage.html";
				},
				error: function(message) {
					$('#errorRegister').text("User with the same username already exists!");
					$("#errorRegister").show().delay(3000).fadeOut();
				}
			});
		}

		
	});

	// __________________________________REQUEST TO LOGIN IN A USER
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
				window.location.href="http://localhost:8080/ConcertsTix/homepage.html";
			},
			error: function(message) {
				$('#errorLogin').text("User doesn't exist!");
				$("#errorLogin").show().delay(3000).fadeOut();
			}
		});	
	});

	// _____________________REQUEST TO LIST ALL MANIFESTATIONS
	$.get({
		url: 'rest/manifestations/list',
		success: function(manifestations) {
			for (let manifestation of manifestations) {
				listAllManifestations(manifestation);
			}
		}
	});
	
});