function listAllUsers(user) {
    	
    let div = $(".panel1");
    let div1 = $('<div class="input"></div><br>');
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
	
	div.append(div1);
	div1.append(label2);
	div1.append(labelPassword);

	div.append(div1);
	div1.append(label3);
	div1.append(labelName);

	div.append(div1);
	div1.append(label4);
	div1.append(labelSurname);

	div.append(div1);
	div1.append(label5);
	div1.append(labelGender);

	div.append(div1);
	div1.append(label6);
	div1.append(labelDateOfBirth);

	div.append(div1);
	div1.append(label7);
	div1.append(labelRole);



	/*.append(labelUsername).append(div1)
	.append(label2).append(labelPassword).append(div1).append(label3).append(labelName)
	.append(div1).append(label4).append(labelSurname).append(div1)
	.append(label5).append(labelGender).append(div1).append(label6).append(labelDateOfBirth)
	.append(div1).append(label7).append(labelRole).append(div1);*/
	
}

function loggedInUserProfile(user) {
	let form = $('form[name="myProfileUpdate"]');
    let div1 = $('<div class="input"></div>');
	let label1 = $('<label for="username">Username:</label>');
    let inputUsername = $('<input type="text" name="username">').val(user.username);
	let label2 = $('<label for="password">Password:</label>');
	let inputPassword = $('<input type="text" name="password">').val(user.password);
	let label3 = $('<label for="name">Name:</label>');
	let inputName = $('<input type="text" name="name">').val(user.name);
	let label4 = $('<label for="surname">Surname:</label>');
	let inputSurname = $('<input type="text" name="surname">').val(user.surname);
	let label5 = $('<label for="gender">Gender:</label>');
	let inputGender = $('<input type="text" name="gender">').val(user.gender);
	let label6 = $('<label for="dateOfBirth">Date of birth:</label>');
	let inputDateOfBirth = $('<input type="text" name="dateOfBirth">').val(user.dateOfBirth);
	let label7 = $('<label for="role">Role:</label>');
	let inputRole = $('<input type="text" name="role">').val(user.role);

	form.append(div1);
	div1.append(label1);
	div1.append(inputUsername);
	
	//form.append(div1);
	div1.append(label2);
	div1.append(inputPassword);

	//form.append(div1);
	div1.append(label3);
	div1.append(inputName);

	//form.append(div1);
	div1.append(label4);
	div1.append(inputSurname);

	//form.append(div1);
	div1.append(label5);
	div1.append(inputGender);

	//form.append(div1);
	div1.append(label6);
	div1.append(inputDateOfBirth);

	//form.append(div1);
	div1.append(label7);
	div1.append(inputRole);


	/*form.append(div1).append(label1).append(inputUsername).append(div1)
	.append(label2).append(inputPassword).append(div1).append(label3).append(inputName)
	.append(div1).append(label4).append(inputSurname).append(div1)
	.append(label5).append(inputGender).append(div1).append(label6).append(inputDateOfBirth)
	.append(div1).append(label7).append(inputRole).append(div1);*/
}

$(document).ready(function() {

	$.get({
		url: 'rest/users/loggedInUser',
		success: function(currentUser) {
			// add to MyProfile panel
			loggedInUserProfile(currentUser);
		}
	});

	$.get({
		url: 'rest/users/loggedInUserRole',
		success: function(currentUserRole) {
			// BUYER ROLE
			if (currentUserRole == "BUYER") {
				// hide panel1 with all users or change it to say all tickets
				// and a new panel for points
				alert("User logged in has a role type BUYER!");
			}
			// SELLER ROLE
			else if (currentUserRole == "SELLER") {
				alert("User logged in has a role type SELLER!");
			}
			// ADMINISTRATOR ROLE
			else {
				$.get({
					url: 'rest/users/list',
					success: function(users) {
						for (let user of users) {
							listAllUsers(user);
						}
					}
				});
			}
		}
});


    


});
