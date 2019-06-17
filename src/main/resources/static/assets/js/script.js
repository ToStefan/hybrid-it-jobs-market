var basePath = 'http://localhost:8080/api';

var isLogged = true;
var isAdminLogged = true;

var preferedLocation = "Berlin";
var preferedDesc = "java";
var preferFullTime = true;


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
        adminManager.usersPagination();
        adminManager.createAdminPage(0, 3);
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

var gitHubManager = {

    getRecommendedJobs: function () {



    }
}


var adminManager = {

    usersPagination: function() {
        $.ajax({
            url: basePath + '/users/count',
            type: 'GET',
            success: function (count) {
                $('#usersPagination').empty();
                $('#usersPagination').append(
                    '<li class="page-item"><button class="page-link" href="#"> <span>«</span></button></li>');
                for (var i = 1; i <= count; i++) {
                    $('#usersPagination').append(
                        '<li class="page-item">' +
                            '<button value="' + i + '" class="page-link linkAsButton" href="#">' + i + '</button>' +
                        '</li>'
                    );
                }
                $('#usersPagination').append(
                    '<li class="page-item"><button class="page-link" href="#"> <span>»</span></button></li>');
                $('.linkAsButton').click(function(e) {
                    e.preventDefault();
                    var page = $(this).val() - 1;
                    adminManager.createAdminPage(page, 3)
                });
            },
            error: function (data, textStatus, xhr) {
                console.log('error!');
            }
        });

    },
    createAdminPage: function(page, elementsCount) {
        domManager.adminPanelPageState();
        pageDto = {
            "page": page,
            "elementsCount": elementsCount
        }
        adminManager.getUsers(JSON.stringify(pageDto));
    },
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
                    adminManager.deleteUser(id);
                });
            },
            error : function(data, textStatus, xhr) {
                console.log('error!');
            }
        });
    },
    deleteUser: function(id) {
        $.ajax({
            url: basePath + '/users/' + id,
            type: 'DELETE',
            success: function () {
                adminManager.usersPagination();
                adminManager.createAdminPage(0, 3);
            },
            error: function (data, textStatus, xhr) {
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
