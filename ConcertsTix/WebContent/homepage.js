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

	let buttonManifestation = $('<input type="button" form="form'+ manifestationId +'" id="' + manifestationId + '" value="' + buttonValue + '" name = "' + buttonValue + '">');
	
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
// inputbuttontoappend for the usage of BUYER when he wants to cancel his ticket reservation
function listAllTickets(ticket, panelName, inputButtonToAppend, ticketId) {
    	
    let div = $(panelName);
    let div1 = $('<div class="input"></div>');
	//let label1 = $('<label for="id">Id:</label>');
    //let labelId = $('<label name="id">' + ticket.id + '</label><br>');
	let label2 = $('<label for="manifestation">Manifestation:</label>');
	let labelManifestation = $('<label name="manifestation">' + ticket.manifestation.name + '</label><br>');
	let label3 = $('<label for="date">Date:</label>');

	let labelDate = $('<input type="date" name="date" id="date' + ticketId + '" readonly><br>').val(ticket.date);

	//let labelDate = $('<label name="date">' + ticket.date + '</label><br>');
	let label4 = $('<label for="price">Price:</label>');
	let labelPrice = $('<label name="price">' + ticket.price + '</label><br>');
	let label5 = $('<label for="buyerNameSurname">Buyers username:</label>');
	let labelBuyersUsername = $('<label name="buyerNameSurname">' + ticket.buyerNameSurname.username + '</label><br>');
	let label6 = $('<label for="statusTicket">Ticket status:</label>');
	let labelTicketStatus = $('<label name="statusTicket">' + ticket.statusTicket + '</label><br>');
	let label7 = $('<label for="typeTicket">Ticket type:</label>');
	let labelTicketType = $('<label name="typeTicket">' + ticket.typeTicket + '</label><br>');
	
	div.append(div1);
	//div1.append(label1);
	//div1.append(labelId);
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
	div1.append(inputButtonToAppend);	
}

// _____________________ADDING INFO TO ALL COMMENTS PANEL
function listAllComments(comment, panelName, buttonComment1, buttonComment2) {
    	
    let div = $(panelName);
    let div1 = $('<div class="input"></div>');
	let label1 = $('<label for="userComment">User:</label>');
    let labelUser = $('<label name="userComment">' + comment.user.username + '</label><br>');
	let label2 = $('<label for="manifestationComment">Manifestation:</label>');
	let labelManifestation = $('<label name="manifestationComment">' + comment.manifestation.name + '</label><br>');
	let label3 = $('<label for="commentComment">Comment:</label>');
	let labelComment = $('<label name="commentComment">' + comment.comment + '</label><br>');
	let label4 = $('<label for="ratingComment">Rating:</label>');
	let labelRating = $('<label name="ratingComment">' + comment.rating + '</label><br>');
	let label5 = $('<label for="statusComment">Comment status:</label>');
	let labelStatus = $('<label name="statusComment">' + comment.status + '</label><br>');
	
	div.append(div1);
	div1.append(label1);
	div1.append(labelUser);
	div1.append(label2);
	div1.append(labelManifestation);
	div1.append(label3);
	div1.append(labelComment);
	div1.append(label4);
	div1.append(labelRating);
	div1.append(label5);
	div1.append(labelStatus);
	div1.append(buttonComment1);
	div1.append(buttonComment2);		
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
    let labelNameManifestation = $('<input type="text" name="nameManifestationCreate" id="nameManifestationCreate" required/>');
	let label2 = $('<label for="typeManifestationCreate">Manifestation type:</label>');
	let inputtypeManifestation = $('<select name="typeManifestationCreate" id="typeManifestationCreate"><option value="CONCERT">Concert</option><option value="FESTIVAL">Festival</option><option value="THEATRE">Theatre</option></select>');
	let label3 = $('<label for="seatingNumberCreate">Seating number:</label>');
	let inputseatingNumber = $('<input type="number" name="seatingNumberCreate" id="seatingNumberCreate" required/>');
	let label4 = $('<label for="dateManifestationCreate">Manifestaiton date:</label>');
	let inputDateManifestation = $('<input type="date" name="dateManifestationCreate" id="dateManifestationCreate" value="2021-09-11"/>');
	let label5 = $('<label for="priceRegularCreate">Regular price:</label>');
	let inputPriceRegular = $('<input type="number" id="priceRegularCreate" name="priceRegularCreate" required/>');
	let label6 = $('<label for="imageManifestationCreate">Manifestation image:</label>');
	let inputImageManifestation = $('<input type="file" id="imageManifestationCreate" name="imageManifestationCreate" required>');
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

// form for updating manifestations by seller
function updateManifestation(manifestation, manifestationId, panelName, buttonValue) {
	let div = $(panelName);

	let forma = $('<form action=# method="post" id="form' +  manifestationId + '">');

    let div1 = $('<div class="inputManifestation"></div>');
	let label1 = $('<label for="nameManifestationUpdate">Name:</label>');
    let labelName = $('<input type="text" name="nameManifestationUpdate" id="nameManifestationUpdate" required>').val(manifestation.name);
	let label2 = $('<label for="typeManifestationUpdate">Manifestation type:</label>');
	let labelTypeManifestation = $('<select name="typeManifestationUpdate" id="typeManifestationUpdate"><option value="CONCERT">Concert</option><option value="FESTIVAL">Festival</option><option value="THEATRE">Theatre</option></select>').val(manifestation.typeManifestation);
	let label3 = $('<label for="seatingNumberUpdate">Seating number:</label>');
	let labelSeatingNumber = $('<input type="number" name="seatingNumberUpdate" id="seatingNumberUpdate" required>').val(manifestation.seatingNumber);
	let label4 = $('<label for="dateUpdate">Date:</label>');
	let labelDate =  $('<label name="dateUpdate">' + manifestation.date + '</label><br>');
	let label5 = $('<label for="priceRegularUpdate">Regular price:</label>');
	let labelPriceRegular = $('<input type="number" name="priceRegularUpdate" id="priceRegularUpdate" required>').val(manifestation.priceRegular);
	let label6 = $('<label for="statusUpdate">Status:</label>');
	let labelStatus = $('<label name="statusUpdate">' + manifestation.status + '</label><br>');

	let label7 = $('<label for="location">Location:</label>');
	let labelLocation = $('<label name="location">' + manifestation.location.city + '</label><br>');

	let label8 =  $('<label for="image">Image:</label>');
	let labelImage =$('<img id="image" src="' + manifestation.image + '"/>'); 
	labelImage.width(100);
	labelImage.height(100);

	let buttonManifestation = $('<input type="button" form="form'+ manifestationId +'" id="' + manifestationId + '" value="' + buttonValue + '" name = "' + buttonValue + '">')
	
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

// _____________________ADDING INFO TO ALL MANIFESTATION PANEL FOR SEARCH
function listAllManifestationsSearch(manifestation, manifestationId, panelName, buttonValue) {
    	
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

	//let buttonManifestation = $('<input type="button" form="form'+ manifestationId +'" id="' + manifestationId + '" value="' + buttonValue + '" name = "' + buttonValue + '">');
	
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
	//div1.append(buttonManifestation);
}

function isIterable(obj) {
	// checks for null and undefined
	if (obj == null || Object.keys(obj).length === 0) {
	  return false;
	}
	return typeof obj[Symbol.iterator] === 'function';
}

function searchByCriteriaAll(name, type, startDate, endDate, priceMin, priceMax) {
    $.get({
        url: 'rest/manifestations/findOne/' + name + '/' + type + '/' + startDate + '/' + endDate + '/' + priceMin + '/' + priceMax,
        success: function(manifestations) {
			let answer = isIterable(manifestations);
			if (answer) {
				$(".panel1").empty();
				$(".panel1").append($('<h1 class="panel__heading">All manifestations</h1><br/>'));
				for (let manifestation of manifestations) {
					listAllManifestationsSearch(manifestation, manifestation.id , ".panel1");
				}
			} else {
				$(".panel1").empty();
				$(".panel1").append($('<h1 class="panel__heading">All manifestations</h1><br/>'));
				$(".panel1").append($('<label for="messageSearch">No manifestations found!</label>'));
				//listAllManifestationsSearch(manifestations, manifestations.id , ".panel1");
			}
        }
    });
}

function searchByCriteriaByNameAndDate(name, type, startDate, endDate) {
    $.get({
        url: 'rest/manifestations/findDate/' + name + '/' + type + '/' + startDate + '/' + endDate,
        success: function(manifestations) {
			let answer = isIterable(manifestations);
			if (answer) {
				$(".panel1").empty();
				$(".panel1").append($('<h1 class="panel__heading">All manifestations</h1><br/>'));
				for (let manifestation of manifestations) {
					listAllManifestationsSearch(manifestation, manifestation.id , ".panel1");
				}
			} else {
				$(".panel1").empty();
				$(".panel1").append($('<h1 class="panel__heading">All manifestations</h1><br/>'));
				$(".panel1").append($('<label for="messageSearch">No manifestations found!</label>'));
				//listAllManifestationsSearch(manifestations, manifestations.id , ".panel1");
			}
        }
    });
}

function searchByCriteriaAllButDate(name, type, priceMin, priceMax) {
    $.get({
        url: 'rest/manifestations/findPrice/' + name + '/' + type + '/' + priceMin + '/' + priceMax,
        success: function(manifestations) {
			let answer = isIterable(manifestations);
			if (answer) {
				$(".panel1").empty();
				$(".panel1").append($('<h1 class="panel__heading">All manifestations</h1><br/>'));
				for (let manifestation of manifestations) {
					listAllManifestationsSearch(manifestation, manifestation.id , ".panel1");
				}
			} else {
				$(".panel1").empty();
				$(".panel1").append($('<h1 class="panel__heading">All manifestations</h1><br/>'));
				$(".panel1").append($('<label for="messageSearch">No manifestations found!</label>'));
				//listAllManifestationsSearch(manifestations, manifestations.id , ".panel1");
			}
        }
    });
}

function searchByCriteriaJustName(name, type) {
    $.get({
        url: 'rest/manifestations/findOne/' + name + '/' + type,
        success: function(manifestations) {
			let answer = isIterable(manifestations);
			if (answer) {
				$(".panel1").empty();
				$(".panel1").append($('<h1 class="panel__heading">All manifestations</h1><br/>'));
				for (let manifestation of manifestations) {
					listAllManifestationsSearch(manifestation, manifestation.id , ".panel1");
				}
			} else {
				$(".panel1").empty();
				$(".panel1").append($('<h1 class="panel__heading">All manifestations</h1><br/>'));
				$(".panel1").append($('<label for="messageSearch">No manifestations found!</label>'));
				//listAllManifestationsSearch(manifestations, manifestations.id , ".panel1");
			}
        }
    });
}

function searchByCriteriaWithoutName(type, startDate, endDate, priceMin, priceMax) {
    $.get({
        url: 'rest/manifestations/findOne/' + type + '/' + startDate + '/' + endDate + '/' + priceMin + '/' + priceMax,
        success: function(manifestations) {
			let answer = isIterable(manifestations);
			if (answer) {
				$(".panel1").empty();
				$(".panel1").append($('<h1 class="panel__heading">All manifestations</h1><br/>'));
				for (let manifestation of manifestations) {
					listAllManifestationsSearch(manifestation, manifestation.id , ".panel1");
				}
			} else {
				$(".panel1").empty();
				$(".panel1").append($('<h1 class="panel__heading">All manifestations</h1><br/>'));
				$(".panel1").append($('<label for="messageSearch">No manifestations found!</label>'));
				//listAllManifestationsSearch(manifestations, manifestations.id , ".panel1");
			}
        }
    });
}

function searchByCriteriaWithoutNameAndPrice(type, startDate, endDate) {
    $.get({
        url: 'rest/manifestations/findJustDate/' + type + '/' + startDate + '/' + endDate,
        success: function(manifestations) {
			let answer = isIterable(manifestations);
			if (answer) {
				$(".panel1").empty();
				$(".panel1").append($('<h1 class="panel__heading">All manifestations</h1><br/>'));
				for (let manifestation of manifestations) {
					listAllManifestationsSearch(manifestation, manifestation.id , ".panel1");
				}
			} else {
				$(".panel1").empty();
				$(".panel1").append($('<h1 class="panel__heading">All manifestations</h1><br/>'));
				$(".panel1").append($('<label for="messageSearch">No manifestations found!</label>'));
				//listAllManifestationsSearch(manifestations, manifestations.id , ".panel1");
			}
        }
    });
}

function searchByCriteriaJustPrice(type, priceMin, priceMax) {
    $.get({
        url: 'rest/manifestations/findJustPrice/' + type + '/' + priceMin + '/' + priceMax,
        success: function(manifestations) {
			let answer = isIterable(manifestations);
			if (answer) {
				$(".panel1").empty();
				$(".panel1").append($('<h1 class="panel__heading">All manifestations</h1><br/>'));
				for (let manifestation of manifestations) {
					listAllManifestationsSearch(manifestation, manifestation.id , ".panel1");
				}
			} else {
				$(".panel1").empty();
				$(".panel1").append($('<h1 class="panel__heading">All manifestations</h1><br/>'));
				$(".panel1").append($('<label for="messageSearch">No manifestations found!</label>'));
				//listAllManifestationsSearch(manifestations, manifestations.id , ".panel1");
			}
        }
    });
}

function searchByCriteriaJustType(type) {
    $.get({
        url: 'rest/manifestations/findType/' + type,
        success: function(manifestations) {
			let answer = isIterable(manifestations);
			if (answer) {
				$(".panel1").empty();
				$(".panel1").append($('<h1 class="panel__heading">All manifestations</h1><br/>'));
				for (let manifestation of manifestations) {
					listAllManifestationsSearch(manifestation, manifestation.id , ".panel1");
				}
			} else {
				$(".panel1").empty();
				$(".panel1").append($('<h1 class="panel__heading">All manifestations</h1><br/>'));
				$(".panel1").append($('<label for="messageSearch">No manifestations found!</label>'));
				//listAllManifestationsSearch(manifestations, manifestations.id , ".panel1");
			}
        }
    });
}

// opening popup for entering number of tickets and ticket type you want to reserve
function openForm(formName) {
	document.getElementById(formName).style.display = "block";
}
  
// closing popup
function closeForm(formName) {
	document.getElementById(formName).style.display = "none";
}

var loggedUser;

$(document).ready(function() {

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
			$.ajax({
				url: 'rest/users/updateUser',
				type: 'PUT',
				data: JSON.stringify(data),
				contentType: 'application/json',
				success: function() {
					$('#successUpdate').text("User with username "  +  $("input[name=usernameMyProfile]").val() + " succesfully updated!");
					$("#successUpdate").show().delay(3000).fadeOut();
					window.location.href="http://localhost:8080/ConcertsTix/homepage.html";
				}
			});	
		}
	});


	// __________________SHOWING PROFILE INFORMATION DETAILS
	// show my profile details
	$.get({
		url: 'rest/users/loggedInUser',
		success: function(currentUser) {
			loggedUser = currentUser;
			// add my profile information to MyProfile panel
			loggedInUserProfile(currentUser);

			// _______________REQUEST FOR HOMEPAGE ELEMENTS SHOWING FOR EACH ROLE OF USER
			// show specific homepage view for each user
			$.get({
				url: 'rest/users/loggedInUserRole',
				success: function(currentUserRole) {
					// ________________________________________________________BUYER ROLE_________________________________________________________
					if (currentUserRole == "BUYER") {
						// _____________________________________REQUEST TO LIST ALL TICKETS
						// prvi nacin preko korisinika registrovanog na samoj karti
						$.get({
							url: 'rest/tickets/list/' + loggedUser.username,
							success: function(tickets) {
								$(".panel4").append($('<h1 class="panel__heading">My tickets</h1><br/>'));
								for (let ticket of tickets) {
									let inputToAppend = $('<input type="button" value="Cancel" id="' + ticket.id + '" name="Cancel">');
									listAllTickets(ticket, ".panel4", inputToAppend, ticket.id);
								}

								$('input[name=Cancel]').click(function() {

									let ticketId =this.id;
									let manifestationDate = $('#date' + ticketId).val();

									// nalazimo danasnji datum
									// da vidimo da li je uopste moguc odustanak od date karte
									// odustanak je moguc najkasnije sedam dana pre manifestacija
								
									var today = new Date();
									var todaysDate = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();

									var start = moment(manifestationDate);
									var end = moment(todaysDate);
									let diffDays = start.diff(end, "days")

									if (diffDays >= 7) {
										$.ajax({
											url: 'rest/tickets/cancel/' + ticketId,
											type: 'PUT',
											success: function() {
												$('#successCancel').text("Successfully canceled your ticket!");
												$("#successCancel").show().delay(3000).fadeOut();
												window.location.href="http://localhost:8080/ConcertsTix/homepage.html";
											}
										});
									} else {
										$('#errorCancel').text("Cannot cancel your ticket because the manifestation is less than 7 days away from today or in the past!");
										$("#errorCancel").show().delay(3000).fadeOut();
									}
								});
							}
						});
						// _____________________________________REQUEST TO LIST ALL TICKETS
						// drugi nacin preko liste karata koju ima sam korisnik
						/*$.get({
							url: 'rest/users/listTickets',
							success: function(tickets) {
								$(".panel4").append($('<h1 class="panel__heading">My tickets</h1><br/>'));
								for (let ticket of tickets) {
									listAllTickets(ticket, ".panel4");
								}
							}
						});*/

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
									name=Comment
									//input[type="button"]
									$('input[name=Reserve]').click(function(){
										let manifestationId = this.id;

										// setting the max value of tickets you can reserve for that event
										manifestationSeatingNumber = $("#seatingNumber" + manifestationId).val();
										$("#ticketNumber").attr({"max" : manifestationSeatingNumber, "min" : 1});

										let numberOfTickets = null;
										let ticketType = null;
										let fullPrice = null;
										// opening form that requests number of ticket i want to reserve and ticket type
										openForm("myForm");
										// on form submit
										
										// unosi broj karata koji zeli da kupi i tip karte koju zeli da kupi
										// kako bi smanjili broj slobodnih mesta za manifestaciju
										// u trenutku kad bude rezervisao kartu	
										$("form[name=formPopUpTicket]").submit(function() {
											event.preventDefault();
											numberOfTickets = $("#ticketNumber").val();
											ticketType = $("#ticketType").val();
											closeForm("myForm");

											// za kliknutu manifestaciju trazimo objekat manifestacija
											$.get({
												url: 'rest/manifestations/findOne/' + manifestationId,
												success: function(manifestation) {

													// vrednost ukupne cene karte
													if (ticketType == "FAN_PIT") {
														fullPrice = numberOfTickets * manifestation.priceRegular * 2;													
													} else if (ticketType == "VIP") {
														fullPrice = numberOfTickets * manifestation.priceRegular * 4;
													} else {
														fullPrice = numberOfTickets * manifestation.priceRegular;
													}

													$('input[name=ticketFullPrice]').val(fullPrice);

													// novi popup koji prikazuje konacnu cenu od koje korisnik moze da odustane ili potvrdi
													openForm("myForm2");

													// kada korisnik odobri full cenu karte
													$("form[name=formPopUpTicket2]").submit(function() {
														event.preventDefault();
														
														// smanjujemo broj slobodnih mesta za odabranu manifestaciju
														$.ajax({
															url: 'rest/manifestations/reserveOne/' + manifestationId + '/' + numberOfTickets,
															type: 'PUT',
															success: function() {

																// request za rezervaciju karte za tu odredjenu manifestaciju
																// za odredjeni tip karte
																$.post({
																	url: 'rest/tickets/reserve/' + ticketType + '/' + numberOfTickets,
																	data: JSON.stringify(manifestation),
																	contentType: 'application/json',
																	success: function(ticketId) {
																		
																		$('#successReserve').text("Successfully reserved a ticket!");
																		$("#successReserve").show().delay(3000).fadeOut();

																		// __________________REQUEST TO GET THE TICKET WITH TICKETID JUST CREATED
																		$.get({
																			url: 'rest/tickets/findOne/' + ticketId,
																			success: function(ticket) {
																				
																				// dodajemo rezervisani ticket u listu svih ticketa useru koji je rezervisao
																				$.post({
																					url: 'rest/users/addTicket',
																					data: JSON.stringify(ticket),
																					contentType: 'application/json',
																					success: function() {
																						// dodajemo rezervisani ticket u listu svih ticketa useru koji je rezervisao
																						$('#successReserve').text("Successfully added ticket to users ticket list!");
																						$("#successReserve").show().delay(3000).fadeOut();
																						//closeForm2("myForm2");
																						window.location.href="http://localhost:8080/ConcertsTix/homepage.html";
																					}	
																				});

																				
																			}
																		});
																	},
																	error: function() {
																		$('#errorReserve').text("Ticket can not be reserved!");
																		$("#errorReserve").show().delay(3000).fadeOut();
																	}
																});

															}
														});
															
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

						//__________ REQUEST TO GET ALL MANIFESTATIONS BUYER CAN COMMENT ON
						$.get({
							url: 'rest/tickets/myFinishedManifestations',
							success: function(manifestations) {
								$(".panel5").append($('<h1 class="panel__heading">My finished manifestations</h1><br/>'));
								for (let manifestation of manifestations) {
									listAllManifestations(manifestation, manifestation.id, ".panel5", "Comment");
								}


								$('input[name=Comment]').click(function() {
									let manifestationId = this.id;
									openForm("myForm3");

									$("form[name=formPopUpComment]").submit(function() {
										event.preventDefault();
										
										closeForm("myForm3");

										let data = {
											"comment" : $("#commentManifestation").val(),
											"rating" : $("#ratingManifestation").val()
										};

										$.post({
											url: 'rest/comments/create/' + manifestationId,
											data: JSON.stringify(data),
											contentType: 'application/json',
											success: function() {
												$('#successActivate').text("Comment on manifestations successfully created!");
												$("#successActivate").show().delay(10000).fadeOut();
											}
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

						// _____________________________________REQUEST TO LIST ALL MANIFESTATIONS BY SELLER that he can update/change
						$.get({
							url: 'rest/users/listManifestations',
							success: function(manifestations) {
								$(".panel1").append($('<h1 class="panel__heading">My manifestations</h1><br/>'));
								for (let manifestation of manifestations) {
									updateManifestation(manifestation, manifestation.id, ".panel1", "Change");
								}

								// UPDATE MANIFESTATION BY SELLER
								// input[type="button"]
								$('input[name=Change]').click(function(){
									let manifestationId = this.id;
				
									let data = {
										"name" : $("input[name=nameManifestationUpdate]").val(),
										"typeManifestation" : $("select[name=typeManifestationUpdate]").val(),
										"seatingNumber" : $("input[name=seatingNumberUpdate]").val(),
										"priceRegular" : $("input[name=priceRegularUpdate]").val()
									};

									$.ajax({
										url: 'rest/manifestations/update/' + manifestationId,
										type: 'PUT',
										data: JSON.stringify(data),
										contentType: 'application/json',
										success: function() {
											$('#successReserve').text("Manifestation succesfully updated!");
											$("#successReserve").show().delay(3000).fadeOut();
											window.location.href="http://localhost:8080/ConcertsTix/homepage.html";
										}
									});	
								});
							}
						});

						// _____________________________________REQUEST TO LIST ALL RESERVED TICKETS
						$.get({
							url: 'rest/tickets/listReserved',
							success: function(tickets) {
								$(".panel4").append($('<h1 class="panel__heading">All reserved tickets</h1><br/>'));
								for (let ticket of tickets) {
									listAllTickets(ticket, ".panel4");
								}
							}
						});

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
								success: function(manifestationId) {
									$('#successCreate').text("Manifestation with name "  +  $("input[name=nameManifestationCreate]").val() + " succesfully created!");
									$("#successCreate").show().delay(3000).fadeOut();

									// dodajemo napravljenu manifestaciju (preko manifestationId) u listu manifestacija SELLERa
									$.post({
										url: 'rest/users/addManifestation/' + manifestationId,
										success: function() {
											// dodajemo rezervisani ticket u listu svih ticketa useru koji je rezervisao
											$('#successCreate').text("Successfully added manifestation to users manifestation list!");
											$("#successCreate").show().delay(3000).fadeOut();
											window.location.href="http://localhost:8080/ConcertsTix/homepage.html";
										}	
									});
									
								},
								error: function() {
									$('#errorCreate').text("Date already occupied, you need to entry a different manifestation date!");
									$("#errorCreate").show().delay(3000).fadeOut();
								}
							});	
						});

						// _____________________________________REQUEST TO LIST ALL STANDBY COMMENTS
						// so that SELLER can approve or deny them
						$.get({
							url: 'rest/comments/listStandby',
							success: function(comments) {
								$(".panel5").append($('<h1 class="panel__heading">All standby comments</h1><br/>'));
								for (let comment of comments) {
									let buttonComment1 = $('<input type="button" + value="Approve" id="' +  comment.id + '" name = "Approve"><br/>');
									let buttonComment2 = $('<input type="button" + value="Deny" id="' +  comment.id + '" name = "Deny"><br/>');
									listAllComments(comment, ".panel5", buttonComment1, buttonComment2);
								}

								$('input[name=Approve]').click(function(){
									let commentId = this.id;

									$.ajax({
										url: 'rest/comments/approve/' + commentId,
										type: 'PUT',
										success: function() {
											$('#successActivate').text("Successfully approved comment!");
											$("#successActivate").show().delay(8000).fadeOut();
											window.location.href="http://localhost:8080/ConcertsTix/homepage.html";
										},
										error: function() {
											$('#errorActivate').text("Error approving comment!");
											$("#errorActivate").show().delay(8000).fadeOut();
										}
									});

								
								});

								$('input[name=Deny]').click(function(){
									let commentId = this.id;
									$('#successActivate').text("Successfully denied comment!");
									$("#successActivate").show().delay(8000).fadeOut();
								});


							}
						});

						//___________________________________REQUEST TO LIST ALL COMMENTS WITH ALL STATUSES
						$.get({
							url: 'rest/comments/list',
							success: function(comments) {
								$(".panel6").append($('<h1 class="panel__heading">All comments</h1><br/>'));
								for (let comment of comments) {
									listAllComments(comment, ".panel6");
								}
							}
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
								
								//'input[type="button"]'
								$('input[name=Activate]').click(function(){
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
						
						// _____________________________________REQUEST TO LIST ALL TICKETS WITH ALL STATUSES (CANCELED AND RESERVED)
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

						// _________________________________________________CREATING SELLER SUBMIT FORM
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

						//___________________________________REQUEST TO LIST ALL COMMENTS WITH ALL STATUSES
						$.get({
							url: 'rest/comments/list',
							success: function(comments) {
								$(".panel6").append($('<h1 class="panel__heading">All comments</h1><br/>'));
								for (let comment of comments) {
									listAllComments(comment, ".panel6");
								}
							}
						});

						

					}
				}
			});







		}
	});

	// _____________________LOGOUT REQUEST
	$("button[name=logOut").click(function() {
		$.post({
			url: 'rest/users/logout',
			success: function(data) {
				//alert("User with username " + data + " successfully logged out");
				window.location.href="http://localhost:8080/ConcertsTix/index.html";
			}
		});
	});

	//__________________________REQUEST TO SEARCH MANIFESTATIONS
	$("#header-search-manifestations").submit(function() {
		event.preventDefault();
		let name = $("input[name=manifestation-name]").val();
	    let type = $("select[name=manifestation-type]").val();
		let startDate = $("input[name=manifestation-Startdate]").val();
	    let endDate = $("input[name=manifestation-Enddate]").val();
		let priceMin = $("input[name=manifestation-priceMin]").val();
		let priceMax = $("input[name=manifestation-priceMax]").val();


		if(name != "") {

			if (startDate != "" && endDate != "") {
				if (moment(endDate).isAfter(startDate)) {
					if (priceMin != "" && priceMax != "") {
						if (priceMin < priceMax) {
							$('#successSearch').text('All data entered!');
							$("#successSearch").show().delay(3000).fadeOut();
							searchByCriteriaAll(name, type, startDate, endDate, priceMin, priceMax);
						}  else {
							$('#errorSearch').text("Min price bigger then max price!");
							$("#errorSearch").show().delay(3000).fadeOut();
						}
					} else {
						searchByCriteriaByNameAndDate(name, type, startDate, endDate);
					}
				}  else {
					$('#errorSearch').text("End date is not after start date!");
					$("#errorSearch").show().delay(3000).fadeOut();
				}

			} else {
				if (priceMin != "" && priceMax != "") {
					if (priceMin < priceMax) {
						searchByCriteriaAllButDate(name, type, priceMin, priceMax);
					}  else {
						$('#errorSearch').text("Min price bigger then max price!");
						$("#errorSearch").show().delay(3000).fadeOut();
					}
				} else {
					searchByCriteriaJustName(name, type);
				}
			}

		} else if (startDate != "" && endDate != "") {
			if (moment(endDate).isAfter(startDate)) {

				if (priceMin != "" && priceMax != "") {
					if (priceMin < priceMax) {
						searchByCriteriaWithoutName(type, startDate, endDate, priceMin, priceMax);
					}  else {
						$('#errorSearch').text("Min price bigger then max price!");
						$("#errorSearch").show().delay(3000).fadeOut();
					}
				}
				else {
					searchByCriteriaWithoutNameAndPrice(type, startDate, endDate);
				}

			} else {
				$('#errorSearch').text("End date is not after start date!");
				$("#errorSearch").show().delay(3000).fadeOut();
			}

		} else if (priceMax != "" && priceMin != "") {
			if (priceMin < priceMax) {
				searchByCriteriaJustPrice(type, priceMin, priceMax);
			}  else {
				$('#errorSearch').text("Min price bigger then max price!");
				$("#errorSearch").show().delay(3000).fadeOut();
			}
		} else {
			searchByCriteriaJustType(type);
		}
	});
	


    


});
