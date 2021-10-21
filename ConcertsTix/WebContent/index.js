function listAllManifestations(manifestation, comments) {

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

	let label8 = $('<label for="image">Image:</label>');
	let labelImage = $('<img id="image" src="' + manifestation.image + '"/><br/><br/>');
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

	for (let comment of comments) {

		let div2 = $('<div class="inputComment"></div>');
		let label9 = $('<h3 class="panel__heading">ALL COMMENTS</h3><br/>');
		let label10 = $('<label for="userComment">User:</label>');
		let labelUser = $('<label name="userComment">' + comment.user.username + '</label><br>');
		let label11 = $('<label for="manifestationComment">Manifestation:</label>');
		let labelManifestation = $('<label name="manifestationComment">' + comment.manifestation.name + '</label><br>');
		let label12 = $('<label for="commentComment">Comment:</label>');
		let labelComment = $('<label name="commentComment">' + comment.comment + '</label><br>');
		let label13 = $('<label for="ratingComment">Rating:</label>');
		let labelRating = $('<label name="ratingComment">' + comment.rating + '</label><br>');

		div1.append(div2);
		div2.append(label9);
		div2.append(label10);
		div2.append(labelUser);
		div2.append(label11);
		div2.append(labelManifestation);
		div2.append(label12);
		div2.append(labelComment);
		div2.append(label13);
		div2.append(labelRating);
	}
}

// _____________________ADDING INFO TO ALL MANIFESTATION PANEL
function listAllManifestationsSearch(manifestation, manifestationId, panelName, buttonValue) {

	let div = $(panelName);

	let forma = $('<form action=# method="post" id="form' + manifestationId + '">');

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

	let label8 = $('<label for="image">Image:</label>');
	let labelImage = $('<img id="image" src="' + manifestation.image + '"/>');
	labelImage.width(100);
	labelImage.height(100);

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
}

function isIterable(obj) {
	// checks for null and undefined
	if (obj == null || Object.keys(obj).length === 0) {
		return false;
	}
	return typeof obj[Symbol.iterator] === 'function';
}

function searchByCriteriaAll(name, type, startDate, endDate, priceMin, priceMax) {

	var url = 'rest/manifestations/search?type=' + type;
	if (name != "") {
		url = url + '&name=' + name;
	}

	if (startDate != "" && endDate != "") {
		url = url + '&startDate=' + startDate + '&endDate=' + endDate;
	}

	if (priceMin != "" && priceMax != "") {
		url = url + '&priceMin=' + priceMin + '&priceMax=' + priceMax;
	}

	$.get({
		url: url,
		success: function (manifestations) {
			let answer = isIterable(manifestations);
			if (answer) {
				$(".panel1").empty();
				$(".panel1").append($('<h1 class="panel__heading">All manifestations</h1><br/>'));
				for (let manifestation of manifestations) {
					listAllManifestationsSearch(manifestation, manifestation.id, ".panel1");
				}
			} else {
				$(".panel1").empty();
				$(".panel1").append($('<h1 class="panel__heading">All manifestations</h1><br/>'));
				$(".panel1").append($('<label for="messageSearch">No manifestations found!</label>'));
			}
		}
	});

}

$(document).ready(function () {

	$("input[type=text]")
		.focus(function () {
			// ukoliko nista nije uneseno u polje
			// polje postaje plavo
			// takodje se pored input polja dodaje poruka upozorenja
			if ($(this).val().length == 0) {
				$(this).css("background-color", "#EAF2F8");
				$(this).parent().append('<div id="message"> Enter some text!</div>');
				$("#message").css("color", "#828282");
				$(".error").remove();
			}
		})
		.focusout(function () {
			// kada je focus van tog input polja
			// polje se zabeli
			// i skloni se poruka upozorenja
			$(this).css("background-color", "white");
			$("#message").remove();
		});

	// ________________________________________REQUEST TO REGISTER A USER
	$("form[name=registerForm]").submit(function () {
		event.preventDefault();

		let success = true;

		let username = $("input[name=username]").val();
		let password = $("input[name=password]").val();
		let firstName = $("input[name=firstName]").val();
		let lastName = $("input[name=lastName]").val();

		// sklanjanje error poruke posle svakog submita
		$(".error").remove();

		if (username.length < 5) {
			$("input[name=username]").after('<span class="error" style="color:#828282"> Username must be atleast 5 letters long!</span>');
			success = false;
		}

		if (password.length < 5) {
			$("input[name=password]").after('<span class="error" style="color:#828282"> Password must be atleast 5 letters long!</span>');
			success = false;
		}

		if (firstName.length < 1) {
			$("input[name=firstName]").after('<span class="error" style="color:#828282"> This field is required!</span>');
			success = false;
		} else {
			let nameReg = /[A-Z][a-z]+/;
			if (!nameReg.test(firstName)) {
				$("input[name=firstName]").after('<span class="error" style="color:#828282"> Enter valid name!</span>');
				success = false;
			}
		}

		if (lastName.length < 1) {
			$("input[name=lastName]").after('<span class="error" style="color:#828282"> This field is required!</span>');
			success = false;
		} else {
			let nameReg = /[A-Z][a-z]+/;
			if (!nameReg.test(lastName)) {
				$("input[name=lastName]").after('<span class="error" style="color:#828282"> Enter valid lastname!</span>');
				success = false;
			}
		}

		let data = {
			"username": $("input[name=username]").val(),
			"password": $("input[name=password]").val(),
			"name": $("input[name=firstName]").val(),
			"surname": $("input[name=lastName]").val(),
			"gender": $("select[name=gender]").val(),
			"dateOfBirth": $("input[name=dateOfBirth]").val()
			//"role" : "BUYER"
		};

		if (success) {
			$.post({
				url: 'rest/users/registerBuyer',
				data: JSON.stringify(data),
				contentType: 'application/json',
				success: function () {
					$('#successRegister').text("User with username " + $("input[name=username]").val() + " succesfully registered!");
					$("#successRegister").show().delay(3000).fadeOut();
					window.location.href = "http://localhost:8080/ConcertsTix/homepage.html";
				},
				error: function (message) {
					$('#errorRegister').text("User with the same username already exists!");
					$("#errorRegister").show().delay(3000).fadeOut();
				}
			});
		}
	});

	// __________________________________REQUEST TO LOGIN IN A USER
	$("form[name=loginForm]").submit(function () {
		event.preventDefault();

		let success = true;

		let username = $("input[name=loginUsername]").val();
		let password = $("input[type=password]").val();

		// sklanjanje error poruke posle svakog submita
		$(".error").remove();

		if (username.length < 1) {
			$("input[name=loginUsername]").after('<span class="error" style="color:#828282"> This field is required!</span>');
			success = false;
		}

		if (password.length < 1) {
			$("input[type=password]").after('<span class="error" style="color:#828282"> This field is required!</span>');
			success = false;
		}

		if (success) {
			$.post({
				url: 'rest/users/login',
				data: JSON.stringify({ "username": username, "password": password }),
				contentType: 'application/json',
				success: function () {
					$('#successLogin').text('User logged in!');
					$("#successLogin").show().delay(3000).fadeOut();
					window.location.href = "http://localhost:8080/ConcertsTix/homepage.html";
				},
				error: function (xhr, error) {
					$('#errorLogin').text(xhr.responseText);
					$("#errorLogin").show().delay(3000).fadeOut();
				}
			});
		}
	});

	// _____________________REQUEST TO LIST ALL MANIFESTATIONS
	$.get({
		url: 'rest/manifestations/list',
		success: function (manifestations) {
			for (let manifestation of manifestations) {

				$.get({
					url: 'rest/comments/find/' + manifestation.id,
					success: function (comments) {

						listAllManifestations(manifestation, comments);
					}
				});
			}
		}
	});

	//__________________________REQUEST TO SEARCH MANIFESTATIONS
	$("#header-search-manifestations").submit(function () {
		event.preventDefault();
		let name = $("input[name=manifestation-name]").val();
		let type = $("select[name=manifestation-type]").val();
		let startDate = $("input[name=manifestation-Startdate]").val();
		let endDate = $("input[name=manifestation-Enddate]").val();
		let priceMin = $("input[name=manifestation-priceMin]").val();
		let priceMax = $("input[name=manifestation-priceMax]").val();

		if ((startDate == "" && endDate != "") || (startDate != "" && endDate == "")) {
			$('#errorSearch').text("Please enter both start and end date!");
			$("#errorSearch").show().delay(3000).fadeOut();
		} else if (moment(endDate).isBefore(startDate)) {
			$('#errorSearch').text("End date is not after the start date!");
			$("#errorSearch").show().delay(3000).fadeOut();
		} else if ((priceMin == "" && priceMax != "") || (priceMin != "" && priceMax == "")) {
			$('#errorSearch').text("Please enter both min and max price!");
			$("#errorSearch").show().delay(3000).fadeOut();
		} else if (priceMin > priceMax) {
			$('#errorSearch').text("Min price must be bigger then max price!");
			$("#errorSearch").show().delay(3000).fadeOut();
		} else {
			searchByCriteriaAll(name, type, startDate, endDate, priceMin, priceMax);
		}

	});

	//___________REQUEST TO SORT MANIFESTATIONS
	$("button[name=sortButton]").click(function () {
		let sortValue = $("select[name=sort-by]").val();
		$.get({
			url: 'rest/manifestations/sort/' + sortValue,
			success: function (manifestations) {
				$(".panel1").empty();
				$(".panel1").append($('<h1 class="panel__heading">All manifestations</h1><br/>'));
				for (let manifestation of manifestations) {
					listAllManifestationsSearch(manifestation, manifestation.id, ".panel1");
				}
			}
		});
	});


});