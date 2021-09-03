// _____________________ADDING INFO TO ALL REGISTERED USERS PANEL
function listAllUsers(user) {
    	
    let div = $(".panel1");
    let div1 = $('<div class="input"></div>');
	let label1 = $('<label for="username">Username:</label>');
    let labelUsername = $('<label name="username">' + user.username + '</label><br>');
	let label2 = $('<label for="password">Password:</label>');
	let labelPassword = $('<label name="password">' + user.password + '</label><br>');
	let label3 = $('<label for="name">Name:</label>');
	let labelName = $('<label name="name">' + user.name + '</label><br>');
	let label4 = $('<label for="surname">Surname:</label>');
	let labelSurname = $('<label name="surname">' + user.surname + '</label><br>');
	let label5 = $('<label for="gender">Gender:</label>');
	let labelGender = $('<label name="gender">' + user.gender + '</label><br>');
	let label6 = $('<label for="dateOfBirth">Date of birth:</label>');
	let labelDateOfBirth = $('<label name="dateOfBirth">' + user.dateOfBirth + '</label><br>');
	let label7 = $('<label for="role">Role:</label>');
	let labelRole = $('<label name="role">' + user.role + '</label><br>');
	
	div.append(div1);
	div1.append(label1);
	div1.append(labelUsername);	
	div1.append(label2);
	div1.append(labelPassword);
	div1.append(label3);
	div1.append(labelName);
	div1.append(label4);
	div1.append(labelSurname);
	div1.append(label5);
	div1.append(labelGender);
	div1.append(label6);
	div1.append(labelDateOfBirth);
	div1.append(label7);
	div1.append(labelRole);	
}

// _____________________ADDING INFO TO ALL MANIFESTATION PANEL
function listAllManifestations(manifestation, manifestationId) {
    	
    let div = $(".panel1");

	let forma = $('<form action=# method="post" id="' +  manifestationId + '">');

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

	let buttonReserveManifestation = $('<input type="button" form="'+ manifestationId +'" id="' + manifestationId + '" value="Reserve">')
	
	div.append(forma);
	forma.append(div1);
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
	div1.append(labelLocation);
	div1.append(label8);
	div1.append(labelImage);
	div1.append(buttonReserveManifestation);
}

// _____________________ADDING INFO TO ALL TICKETS PANEL
function listAllTickets(ticket) {
    	
    let div = $(".panel3");
    let div1 = $('<div class="input"></div>');
	let label1 = $('<label for="id">Id:</label>');
    let labelId = $('<label name="id">' + ticket.id + '</label><br>');
	let label2 = $('<label for="manifestation">Manifestation:</label>');
	let labelManifestation = $('<label name="manifestation">' + ticket.manifestation.name + '</label><br>');
	let label3 = $('<label for="date">Date:</label>');
	let labelDate = $('<label name="date">' + ticket.date + '</label><br>');
	let label4 = $('<label for="price">Price:</label>');
	let labelPrice = $('<label name="price">' + ticket.price + '</label><br>');
	let label5 = $('<label for="buyerNameSurname">Buyers username:</label>');
	let labelBuyersUsername = $('<label name="buyerNameSurname">' + ticket.buyerNameSurname.username + '</label><br>');
	let label6 = $('<label for="statusTicket">Ticket status:</label>');
	let labelTicketStatus = $('<label name="statusTicket">' + ticket.statusTicket + '</label><br>');
	let label7 = $('<label for="typeTicket">Ticket type:</label>');
	let labelTicketType = $('<label name="typeTicket">' + ticket.typeTicket + '</label><br>');
	
	div.append(div1);
	div1.append(label1);
	div1.append(labelId);
	div1.append(label2);
	div1.append(labelManifestation);
	div1.append(label3);
	div1.append(labelDate);
	div1.append(label4);
	div1.append(labelPrice);
	div1.append(label5);
	div1.append(labelBuyersUsername);
	div1.append(label6);
	div1.append(labelTicketStatus);
	div1.append(label7);
	div1.append(labelTicketType);	
}

// _____________________ADDING INFO TO USER PROFILE INFORMATION PANEL
function loggedInUserProfile(user) {
	let form = $('form[name="myProfileUpdate"]');
    let div1 = $('<div class="input"></div>');
	let label1 = $('<label for="username">Username:</label>');
	// username cannot be changed
    let labelUsername = $('<input type="text" name="username" readonly>').val(user.username);
	let label2 = $('<label for="password">Password:</label>');
	let inputPassword = $('<input type="text" name="password">').val(user.password);
	let label3 = $('<label for="name">Name:</label>');
	let inputName = $('<input type="text" name="firstName">').val(user.name);
	let label4 = $('<label for="surname">Surname:</label>');
	let inputSurname = $('<input type="text" name="lastName">').val(user.surname);

	let label5 = $('<label for="gender">Gender:</label>');
	let inputGender = $('<select id="gender" name="gender"><option value="female">Female</option><option value="male">Male</option></select>').val(user.gender);

	let label6 = $('<label for="dateOfBirth">Date of birth:</label>');
	let inputDateOfBirth = $('<input type="date" name="dateOfBirth" id="dateOfBirth">').val(user.dateOfBirth);

	let buttonUpdateAccount = $('<button type="submit">Update</button>')

	form.append(div1);
	div1.append(label1);
	div1.append(labelUsername);
	div1.append(label2);
	div1.append(inputPassword);
	div1.append(label3);
	div1.append(inputName);
	div1.append(label4);
	div1.append(inputSurname);
	div1.append(label5);
	div1.append(inputGender);
	div1.append(label6);
	div1.append(inputDateOfBirth);
	form.append(buttonUpdateAccount);
}

$(document).ready(function() {

	// __________________SHOWING PROFILE INFORMATION DETAILS
	// show my profile details
	$.get({
		url: 'rest/users/loggedInUser',
		success: function(currentUser) {
			// add my profile information to MyProfile panel
			loggedInUserProfile(currentUser);
		}
	});

	// ___________________UPDATING USER PROFILE INFORMATION REQUEST
	// update profile information form
	$("form[name=myProfileUpdate]").submit(function() {
		event.preventDefault();
		
		let success = true;
		
	    let firstName = $("input[name=firstName]").val();
	    let lastName = $("input[name=lastName]").val();
		
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
			"dateOfBirth" : $("input[name=dateOfBirth]").val()
        };

		// ovde moramo cekati odgovor, da bi mogli da prikazujemo druge stvari na stranici
		$.post({
			url: 'rest/users/updateBuyer',
			data: JSON.stringify(data),
			contentType: 'application/json',
			//async: false,
			success: function() {
				$('#successRegister').text("User with username "  +  $("input[name=username]").val() + " succesfully updated!");
				$("#successRegister").show().delay(3000).fadeOut();
				//window.location.href="http://localhost:8080/ConcertsTix/homepage.html";
			}
		});	
	});

	// _______________REQUEST FOR HOMEPAGE ELEMENTS SHOWING FOR EACH ROLE OF USER
	// show specific homepage view for each user
	$.get({
		url: 'rest/users/loggedInUserRole',
		success: function(currentUserRole) {
			// ________________________________________________________BUYER ROLE_________________________________________________________
			if (currentUserRole == "BUYER") {
				// _____________________________________REQUEST TO LIST ALL TICKETS
				$.get({
					url: 'rest/tickets/list',
					success: function(tickets) {
						$(".panel3").append($('<h1 class="panel__heading">My tickets</h1><br/>'));
						for (let ticket of tickets) {
							listAllTickets(ticket);
						}
					}
				});

				// _____________________REQUEST TO LIST ALL MANIFESTATIONS
				$.get({
					url: 'rest/manifestations/list',
					//async: false,
					success: function(manifestations) {
						$(".panel1").append($('<h1 class="panel__heading">All manifestations</h1><br/>'));
						for (let manifestation of manifestations) {
							listAllManifestations(manifestation, manifestation.id);
						}



						// ________________ WHEN A BUYER WANTS TO RESERVE A TICKET FOR A SPECIFIC MANIFESTATION
						$(function(){
							$('input[type="button"]').click(function(){
								let manifestationId = this.id;
								// mesto za upisivanje rezervacije
								$.get({
									url: 'rest/manifestations/findOne/' + manifestationId,
									success: function(manifestation) {
										// vracena manifestacija za odredjeni id
										// request za rezervaciju karte za tu odredjenu manifestaciju
										$.post({
											url: 'rest/tickets/reserve',
											data: JSON.stringify(manifestation),
											contentType: 'application/json',
											success: function() {
												$('#successReserve').text("Successfully reserved a ticket!");
												$("#successReserve").show().delay(3000).fadeOut();
											},
											error: function() {
												$('#errorReserve').text("Ticket can not be reserved!");
												$("#errorReserve").show().delay(3000).fadeOut();
											}
										});


									},
									error: function() {
										$('#errorReserve').text("Manifestation you want to reserve is deleted, not found!");
										$("#errorReserve").show().delay(3000).fadeOut();
									}
								});
							});
						});
					}
				});
				//alert("User logged in has a role type BUYER!");
			}
			// _______________________________________________________________________SELLER ROLE_________________________________________________________
			else if (currentUserRole == "SELLER") {
				alert("User logged in has a role type SELLER!");
			}
			// ____________________________________________________________ADMINISTRATOR ROLE_____________________________________________________________
			else {
				$.get({
					url: 'rest/users/list',
					success: function(users) {
						$(".panel1").append($('<h1 class="panel__heading">All users</h1><br/>'));
						for (let user of users) {
							listAllUsers(user);
						}
					}
				});
			}
		}
	});

	// _____________________LOGOUT REQUEST
	// needs to be fixed!!!
	$("button[name=logOut").click(function() {
		$.post({
			url: 'rest/users/logout',
			success: function(data) {
				//alert("User with username " + data + " successfully logged out");
				window.location.href="http://localhost:8080/ConcertsTix/index.html";
			}
		});
	});

	


    


});
