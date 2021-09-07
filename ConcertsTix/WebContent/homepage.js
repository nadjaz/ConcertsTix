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
function listAllManifestations(manifestation, manifestationId, panelName, buttonValue) {
    	
    let div = $(panelName);

	let forma = $('<form action=# method="post" id="form' +  manifestationId + '">');

    let div1 = $('<div class="inputManifestation"></div>');
	let label1 = $('<label for="name">Name:</label>');
    let labelName = $('<label name="name">' + manifestation.name + '</label><br>');
	let label2 = $('<label for="typeManifestation">Manifestation type:</label>');
	let labelTypeManifestation = $('<label name="typeManifestation">' + manifestation.typeManifestation + '</label><br>');
	let label3 = $('<label for="seatingNumber">Seating number:</label>');
	let labelSeatingNumber = $('<input type="text" name="seatingNumber" id="seatingNumber' + manifestationId + '" readonly><br>').val(manifestation.seatingNumber);
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

	let buttonManifestation = $('<input type="button" form="form'+ manifestationId +'" id="' + manifestationId + '" value=' + buttonValue + '>')
	
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
	div1.append(buttonManifestation);
}

// _____________________ADDING INFO TO ALL TICKETS PANEL
function listAllTickets(ticket, panelName) {
    	
    let div = $(panelName);
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
    let labelUsername = $('<input type="text" name="usernameMyProfile" readonly>').val(user.username);
	let label2 = $('<label for="password">Password:</label>');
	let inputPassword = $('<input type="text" name="passwordMyProfile" id="passwordMyProfile">').val(user.password);
	let label3 = $('<label for="name">Name:</label>');
	let inputName = $('<input type="text" name="firstNameMyProfile" id="firstNameMyProfile">').val(user.name);
	let label4 = $('<label for="surname">Surname:</label>');
	let inputSurname = $('<input type="text" name="lastNameMyProfile" id="lastNameMyProfile">').val(user.surname);
	let label5 = $('<label for="gender">Gender:</label>');
	let inputGender = $('<select id="genderMyProfile" name="genderMyProfile"><option value="female">Female</option><option value="male">Male</option></select>').val(user.gender);
	let label6 = $('<label for="dateOfBirth">Date of birth:</label>');
	let inputDateOfBirth = $('<input type="date" name="dateOfBirthMyProfile" id="dateOfBirthMyProfile">').val(user.dateOfBirth);
	// role type cannot be changed
	let label7 = $('<label for="roleMyProfile">Role type:</label>');
	let inputRole = $('<input type="text" name="roleMyProfile" id="roleMyProfile" readonly>').val(user.role);
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
	div1.append(label7);
	div1.append(inputRole);
	form.append(buttonUpdateAccount);
}

//_____________________________FORM FOR CREATING SELLER USER
function createSeller() {
	let div = $(".panel3");
	let form = $('<form action=# method="post" id="createSeller" name="createSeller">');
    let div1 = $('<div class="input"></div>');
	let label1 = $('<label for="username">Username:</label>');
    let labelUsername = $('<input type="text" name="usernameSeller" id="usernameSeller"/>');
	let label2 = $('<label for="password">Password:</label>');
	let inputPassword = $('<input type="password" name="passwordSeller" id="passwordSeller"/>');
	let label3 = $('<label for="name">Name:</label>');
	let inputName = $('<input type="text" name="firstNameSeller" id="firstNameSeller"/>');
	let label4 = $('<label for="surname">Surname:</label>');
	let inputSurname = $('<input type="text" name="lastNameSeller" id="lastNameSeller"/>');
	let label5 = $('<label for="gender">Gender:</label>');
	let inputGender = $('<select id="genderSeller" name="genderSeller"><option value="female">Female</option><option value="male">Male</option></select>');
	let label6 = $('<label for="dateOfBirth">Date of birth:</label>');
	let inputDateOfBirth = $('<input type="date" name="dateOfBirthSeller" id="dateOfBirthSeller" value="2021-09-11"/>');
	let buttonCreateSeller = $('<button type="submit">Create</button>')

	div.append(form);
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
	form.append(buttonCreateSeller);
}

//_____________________________FORM FOR CREATING MANIFESTATION
function createManifestation() {
	let div = $(".panel3");
	let form = $('<form action=# method="post" id="createManifestation" name="createManifestation">');
    let div1 = $('<div class="input"></div>');
	let label1 = $('<label for="nameManifestationCreate">Manifestaion name:</label>');
    let labelNameManifestation = $('<input type="text" name="nameManifestationCreate" id="nameManifestationCreate"/>');
	let label2 = $('<label for="typeManifestationCreate">Manifestation type:</label>');
	let inputtypeManifestation = $('<select name="typeManifestationCreate" id="typeManifestationCreate"><option value="CONCERT">Concert</option><option value="FESTIVAL">Festival</option><option value="THEATRE">Theatre</option></select>');
	let label3 = $('<label for="seatingNumberCreate">Seating number:</label>');
	let inputseatingNumber = $('<input type="number" name="seatingNumberCreate" id="seatingNumberCreate"/>');
	let label4 = $('<label for="dateManifestationCreate">Manifestaiton date:</label>');
	let inputDateManifestation = $('<input type="date" name="dateManifestationCreate" id="dateManifestationCreate" value="2021-09-11"/>');
	let label5 = $('<label for="priceRegularCreate">Regular price:</label>');
	let inputPriceRegular = $('<input type="number" id="priceRegularCreate" name="priceRegularCreate"/>');
	let label6 = $('<label for="imageManifestationCreate">Manifestation image:</label>');
	let inputImageManifestation = $('<input type="file" id="imageManifestationCreate" name="imageManifestationCreate">');
	let buttonCreateManifestation = $('<button type="submit">Create</button>')

	div.append(form);
	form.append(div1);
	div1.append(label1);
	div1.append(labelNameManifestation);
	div1.append(label2);
	div1.append(inputtypeManifestation);
	div1.append(label3);
	div1.append(inputseatingNumber);
	div1.append(label4);
	div1.append(inputDateManifestation);
	div1.append(label5);
	div1.append(inputPriceRegular);
	div1.append(label6);
	div1.append(inputImageManifestation);
	form.append(buttonCreateManifestation);
}

// opening popup for entering number of tickets and ticket type you want to reserve
function openForm() {
	document.getElementById("myForm").style.display = "block";
}
  
function closeForm() {
	document.getElementById("myForm").style.display = "none";
}

$(document).ready(function() {

	var loggedUser = null;

	// __________________SHOWING PROFILE INFORMATION DETAILS
	// show my profile details
	$.get({
		url: 'rest/users/loggedInUser',
		success: function(currentUser) {
			loggedUser = currentUser;
			// add my profile information to MyProfile panel
			loggedInUserProfile(currentUser);
		}
	});

	// ___________________UPDATING USER PROFILE INFORMATION REQUEST
	// update profile information form
	$("form[name=myProfileUpdate]").submit(function() {
		event.preventDefault();
		
		let success = true;
		
		let password = $("input[name=passwordMyProfile]").val();
	    let firstName = $("input[name=firstNameMyProfile]").val();
	    let lastName = $("input[name=lastNameMyProfile]").val();
		
		// sklanjanje error poruke posle svakog submita
		$(".error").remove();

		if(password.length < 5) {
    		$("input[name=passwordMyProfile]").after('<span class="error" style="color:#828282">  Password must be atleast 5 letters long!</span>');
    		success = false;
    	}

		if(firstName.length < 1) {
    		$("input[name=firstNameMyProfile]").after('<span class="error" style="color:#828282"> This field is required!</span>');
    		success = false;
    	} else {
    		let nameReg = new RegExp('[A-Z][a-z]+');
    		if (!nameReg.test(firstName)) {
				$("input[name=firstNameMyProfile]").after('<span class="error" style="color:#828282"> Enter valid name!</span>');
				success = false;
			}
		}
		
		if(lastName.length < 1) {
    		$("input[name=lastNameMyProfile]").after('<span class="error" style="color:#828282"> This field is required!</span>');
    		success = false;
    	} else {
    		let nameReg = new RegExp('[A-Z][a-z]+');
    		if (!nameReg.test(lastName)) {
				$("input[name=lastNameMyProfile]").after('<span class="error" style="color:#828282"> Enter valid lastname!</span>');
				success = false;
			}
		}

		let data = {
        	"username" : $("input[name=usernameMyProfile]").val(),
        	"password" : $("#passwordMyProfile").val(),
        	"name" : $("#firstNameMyProfile").val(),
        	"surname" : $("#lastNameMyProfile").val(),
        	"gender" : $("#genderMyProfile").val(),
			"dateOfBirth" : $("#dateOfBirthMyProfile").val()
        };

		if(success) {
			// ovde moramo cekati odgovor, da bi mogli da prikazujemo druge stvari na stranici
			$.post({
				url: 'rest/users/updateBuyer',
				data: JSON.stringify(data),
				contentType: 'application/json',
				//async: false,
				success: function() {
					$('#successUpdate').text("User with username "  +  $("input[name=usernameMyProfile]").val() + " succesfully updated!");
					$("#successUpdate").show().delay(3000).fadeOut();
					window.location.href="http://localhost:8080/ConcertsTix/homepage.html";
				}
			});	
		}
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
					url: 'rest/tickets/list/' + loggedUser.username,
					success: function(tickets) {
						$(".panel4").append($('<h1 class="panel__heading">My tickets</h1><br/>'));
						for (let ticket of tickets) {
							listAllTickets(ticket, ".panel4");
						}
					}
				});

				// _____________________REQUEST TO LIST ALL ACTIVE MANIFESTATIONS
				$.get({
					url: 'rest/manifestations/listActive',
					//async: false,
					success: function(manifestations) {
						$(".panel1").append($('<h1 class="panel__heading">All active manifestations</h1><br/>'));
						for (let manifestation of manifestations) {
							listAllManifestations(manifestation, manifestation.id, ".panel1", "Reserve");
						}
						


						// ________________ WHEN A BUYER WANTS TO RESERVE A TICKET FOR A SPECIFIC MANIFESTATION
						$(function(){
							$('input[type="button"]').click(function(){
								let manifestationId = this.id;

								// setting the max value of tickets you can reserve for that event
								manifestationSeatingNumber = $("#seatingNumber" + manifestationId).val();
								$("#ticketNumber").attr({"max" : manifestationSeatingNumber, "min" : 1});

								let numberOfTickets = null;
								let ticketType = null;
								// opening form that requests number of ticket i want to reserve and ticket type
								openForm();
								// on form submit
								
								// unosi broj karata koji zeli da kupi
								// kako bi smanjili broj slobodnih mesta za manifestaciju
								// u trenutku kad bude rezervisao kartu	
								// i tip karte koji zeli da uzme
								$("form[name=formPopUpTicket]").submit(function() {
									event.preventDefault();
									numberOfTickets = $("#ticketNumber").val();
	    							ticketType = $("#ticketType").val();
									closeForm();
									// za kliknutu manifestaciju trazimo objekat manifestacija
									$.get({
										url: 'rest/manifestations/findOne/' + manifestationId + '/' + numberOfTickets,
										success: function(manifestation) {
											// vracena manifestacija za odredjeni id
											// request za rezervaciju karte za tu odredjenu manifestaciju
											$.post({
												url: 'rest/tickets/reserve/' + ticketType,
												data: JSON.stringify(manifestation),
												contentType: 'application/json',
												success: function() {
													$('#successReserve').text("Successfully reserved a ticket!");
													$("#successReserve").show().delay(3000).fadeOut();
													window.location.href="http://localhost:8080/ConcertsTix/homepage.html";
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
						});
					}
				});

				// get buyers points score
				// _____________________________________REQUEST TO GET USER POINTS SCORE
				$.get({
					url: 'rest/users/userPoints',
					success: function(points) {
						$(".panel3").append($('<h1 class="panel__heading">My points</h1><br/>'));
						let div = $(".panel3");
						let div1 = $('<div class="input"></div>');
						let label1 = $('<label for="userPoints">User points:</label>');
						let labelPoints = $('<label name="userPoints" id="userPoints">' + points + '</label><br>');
						div.append(div1);
						div1.append(label1);
						div1.append(labelPoints);	
					}
				});
				//alert("User logged in has a role type BUYER!");
			}
			// _______________________________________________________________________SELLER ROLE_________________________________________________________
			else if (currentUserRole == "SELLER") {
				$(".panel3").append($('<h1 class="panel__heading">Create manifestation</h1><br/>'));
				createManifestation();

				// CREATING MANIFESTATION SUBMIT FORM
				$("form[name=createManifestation]").submit(function() {
					event.preventDefault();
						
					let data = {
						"name" : $("input[name=nameManifestationCreate]").val(),
						"typeManifestation" : $("select[name=typeManifestationCreate]").val(),
						"seatingNumber" : $("input[name=seatingNumberCreate]").val(),
						"date" : $("input[name=dateManifestationCreate]").val(),
						"priceRegular" : $("input[name=priceRegularCreate]").val(),
						"image" : $("input[name=imageManifestationCreate]").val()
					};
			
					$.post({
						url: 'rest/manifestations/create',
						data: JSON.stringify(data),
						contentType: 'application/json',
						success: function() {
							$('#successCreate').text("Manifestation with name "  +  $("input[name=nameManifestation]").val() + " succesfully created!");
							$("#successCreate").show().delay(3000).fadeOut();
							window.location.href="http://localhost:8080/ConcertsTix/homepage.html";
						},
						error: function(message) {
							$('#errorCreate').text("Date already occupied, you need to entry a different manifestation date!");
							$("#errorCreate").show().delay(3000).fadeOut();
						}
					});
			
					
				});

				//alert("User logged in has a role type SELLER!");
			}
			// ____________________________________________________________ADMINISTRATOR ROLE_____________________________________________________________
			else {
				//___________________________________REQUEST TO LIST ALL USERS
				$.get({
					url: 'rest/users/list',
					success: function(users) {
						$(".panel1").append($('<h1 class="panel__heading">All users</h1><br/>'));
						for (let user of users) {
							listAllUsers(user);
						}
					}
				});

				//___________________________REQUEST TO LIST ALL INACTIVE MANIFESTATIONS
				$.get({
					url: 'rest/manifestations/listInactive',
					success: function(manifestations) {
						$(".panel5").append($('<h1 class="panel__heading">All inactive manifestations</h1><br/>'));

						for (let manifestation of manifestations) {
							listAllManifestations(manifestation, manifestation.id, ".panel5", "Activate");
						}

						$('input[type="button"]').click(function(){
							let manifestationId = this.id;
							// za kliknutu manifestaciju trazimo objekat manifestacija
							$.get({
								url: 'rest/manifestations/activateOne/' + manifestationId,
								success: function() {
									$('#successActivate').text("Successfully activated the manifestation!");
									$("#successActivate").show().delay(3000).fadeOut();
									window.location.href="http://localhost:8080/ConcertsTix/homepage.html";
								},
								error: function() {
									$('#errorActivate').text("Cannot active manifestation!");
									$("#errorActivate").show().delay(3000).fadeOut();
								}
							});

						});
					}
				});
				
				// _____________________________________REQUEST TO LIST ALL TICKETS
				$.get({
					url: 'rest/tickets/list',
					success: function(tickets) {
						$(".panel4").append($('<h1 class="panel__heading">All tickets</h1><br/>'));
						for (let ticket of tickets) {
							listAllTickets(ticket, ".panel4");
						}
					}
				});


				$(".panel3").append($('<h1 class="panel__heading">Create seller</h1><br/>'));
				createSeller();

				// CREATING SELLER SUBMIT FORM
				$("form[name=createSeller]").submit(function() {
					event.preventDefault();
					
					let success = true;
					

					let username = $("input[name=usernameSeller]").val();
					let password = $("input[name=passwordSeller]").val();
					let firstName = $("input[name=firstNameSeller]").val();
					let lastName = $("input[name=lastNameSeller]").val();
					
					// sklanjanje error poruke posle svakog submita
					$(".error").remove();

					if(username.length < 5) {
						$("input[name=usernameSeller]").after('<span class="error" style="color:#828282"> Username must be atleast 5 letters long!</span>');
						success = false;
					}

					if(password.length < 5) {
						$("input[name=passwordSeller]").after('<span class="error" style="color:#828282"> Password must be atleast 5 letters long!</span>');
						success = false;
					}
			
					if(firstName.length < 1) {
						$("input[name=firstNameSeller]").after('<span class="error" style="color:#828282"> This field is required!</span>');
						success = false;
					} else {
						let nameReg = new RegExp('[A-Z][a-z]+');
						if (!nameReg.test(firstName)) {
							$("input[name=firstNameSeller]").after('<span class="error" style="color:#828282"> Enter valid name!</span>');
							success = false;
						}
					}
					
					if(lastName.length < 1) {
						$("input[name=lastNameSeller]").after('<span class="error" style="color:#828282"> This field is required!</span>');
						success = false;
					} else {
						let nameReg = new RegExp('[A-Z][a-z]+');
						if (!nameReg.test(lastName)) {
							$("input[name=lastNameSeller]").after('<span class="error" style="color:#828282"> Enter valid lastname!</span>');
							success = false;
						}
					}
			
					let data = {
						"username" : $("input[name=usernameSeller]").val(),
						"password" : $("input[name=passwordSeller]").val(),
						"name" : $("input[name=firstNameSeller]").val(),
						"surname" : $("input[name=lastNameSeller]").val(),
						"gender" : $("select[name=genderSeller]").val(),
						"dateOfBirth" : $("input[name=dateOfBirthSeller]").val()
						//"role" : "SELLER"
					};
			
					if (success) {
						$.post({
							url: 'rest/users/registerSeller',
							data: JSON.stringify(data),
							contentType: 'application/json',
							success: function() {
								$('#successRegister').text("User with username "  +  $("input[name=usernameSeller]").val() + " succesfully registered!");
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
