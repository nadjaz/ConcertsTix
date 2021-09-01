function addUserTr(user) {
    	
    let div = $( ".panel" );
    let div1 = $('<div class="input"></div>')
    let tdUsername = $('<label name="username">' + user.username + '</label>');
	let tdPassword = $('<label name="password">' + user.password + '</label>');
	let tdName = $('<label name="name">' + user.name + '</label>');
	let tdSurname = $('<label name="surname">' + user.surname + '</label>');
	let tdGender = $('<label name="gender">' + user.gender + '</label>');
	let tdDateOfBirth = $('<label name="dateOfBirth">' + user.dateOfBirth + '</label>');
	let tdRole = $('<label name="role">' + user.role + '</label>');
	div.append(div1).append(tdUsername).append(div1).append(tdPassword).append(div1).append(tdName).append(div1).append(tdSurname).append(div1).append(tdGender).append(div1).append(tdDateOfBirth).append(div1).append(tdRole).append(div1);
	
}

$(document).ready(function() {

    $.get({
			url: 'rest/users/list',
			success: function(users) {
				for (let user of users) {
					addUserTr(user);
				}
			}
	});


});
