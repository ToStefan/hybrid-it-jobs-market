var basePath = 'http://localhost:8080/api';

var isLogged = true;
var isAdminLogged = true;

$(document).ready(function() {

    domManager.homePageState();
    $('#btnAdminPanel').hide();
    if(isLogged){
        domManager.loggedState();
    } else{
        domManager.loggedOutState();
    }
    if(isAdminLogged){
        $('#btnAdminPanel').show();
    }

    $('#btnHome').click(function(e) {
        e.preventDefault();
        domManager.homePageState();
    });
    $('#btnPreference').click(function(e) {
        e.preventDefault();
        domManager.preferencePageState();
    });
    $('#btnAdminPanel').click(function(e) {
        e.preventDefault();
        domManager.adminPanelPageState();
        pageDto = {
            "page": 0,
            "elementsCount": 3
        }
        adminManager.getUsers(JSON.stringify(pageDto));
    });
    $('#btnSignIn').click(function(e) {
        e.preventDefault();
        domManager.signInPageState();
    });
    $('#btnSignUp').click(function(e) {
        e.preventDefault();
        domManager.signUpPageState();
    });
    $('#btnLogout').click(function(e) {
        e.preventDefault();
        domManager.loggedOutState();
    });

});

var adminManager = {

    getUsers: function (pageDto) {
        $.ajax({
            url : basePath + '/users/search',
            dataType : 'json',
            type : 'POST',
            contentType : 'application/json',
            data : pageDto,
            success : function(users, textStatus, xhr) {
                $('#tableBody').empty();
                $.each(users, function(index, user) {
                    $('#tableBody').append(
                        '<tr>' +
                            '<th>' + user.id + '</th>' +
                            '<td>' + user.username + '</td>' +
                            '<td>' + user.password + '</td>' +
                            '<td>' + user.firstname + '</td>' +
                            '<td>' + user.lastname + '</td>' +
                            '<td class="text-center">' +
                                '<button value="' + user.id + '" class="btn btn-danger btnDeleteUser">Delete</button>' +
                            '</td>' +
                        '</tr>'
                    )
                });
                $('.btnDeleteUser').click(function(e) {
                    e.preventDefault();
                    var id = $(this).val();
                    console.log(id);
                });
            },
            error : function(data, textStatus, xhr) {
                console.log('error!');
            }
        });
    }
}

var domManager = {

    loggedState: function(){
        $('#btnSignUp').hide();
        $('#btnLogout').show();
        $('#btnSignIn').hide();
        $('#btnPreference').show();
    },
    loggedOutState: function() {
        $('#btnSignUp').show();
        $('#btnLogout').hide();
        $('#btnSignIn').show();
        $('#btnPreference').hide();
    },
    homePageState : function() {
        $('#homePage').show();
        $('#preferencePage').hide();
        $('#signInPage').hide();
        $('#signUpPage').hide();
        $('#adminPanelPage').hide();
        $('#jobDetailsPage').hide();
    },
    preferencePageState : function() {
        $('#homePage').hide();
        $('#preferencePage').show();
        $('#signInPage').hide();
        $('#signUpPage').hide();
        $('#adminPanelPage').hide();
        $('#jobDetailsPage').hide();
    },
    signInPageState : function() {
        $('#homePage').hide();
        $('#preferencePage').hide();
        $('#signInPage').show();
        $('#signUpPage').hide();
        $('#adminPanelPage').hide();
        $('#jobDetailsPage').hide();
    },
    signUpPageState : function() {
        $('#homePage').hide();
        $('#preferencePage').hide();
        $('#signInPage').hide();
        $('#signUpPage').show();
        $('#adminPanelPage').hide();
        $('#jobDetailsPage').hide();
    },
    adminPanelPageState : function() {
        $('#homePage').hide();
        $('#preferencePage').hide();
        $('#signInPage').hide();
        $('#signUpPage').hide();
        $('#adminPanelPage').show();
        $('#jobDetailsPage').hide();
    },
    jobDetailsPageState : function() {
        $('#homePage').hide();
        $('#preferencePage').hide();
        $('#signInPage').hide();
        $('#signUpPage').hide();
        $('#adminPanelPage').hide();
        $('#jobDetailsPage').show();
    }
}
