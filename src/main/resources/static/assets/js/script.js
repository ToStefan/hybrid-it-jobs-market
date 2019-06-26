//>>> LocalStorage >>>
//Token --> Index = 0;
//Role --> Index = 1; Value: 2 - ROLE_ADMINI, 1 - ROLE_USER;
//Logged username --> Index = 2;

var basePath = 'http://localhost:8080/api';

$(document).ready(function() {

    $('#btnAdminPanel').hide();
    domManager.homePageState();

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
        $('#inputUsernameSign').val("");
        $('#inputPasswordSign').val("");
        $('#recommendedJobs').empty();
        $('#searchedJobs').empty();
        $('#usersPagination').empty();
        $('#tableBody').empty();
        $('#btnAdminPanel').hide();
        storageManager.logout();
        domManager.loggedOutState();
        domManager.homePageState()
    });

    $('#btnSearchJobs').click(function(e) {
        e.preventDefault();
        var type = $('#selectWorkTypeSearchJob').val() == 0 ?
                    null : $('#selectWorkTypeSearchJob option:selected').text();
        jobDto = {
            "type": type,
            "location": $('#autocompleteLocation').val(),
            "description": $('#descSearchJob').val()
        }
        jobsManager.searchForJobs(JSON.stringify(jobDto))
    });

    $('#btnSubmitSignIn').click(function(e) {
        e.preventDefault();
        if($('#inputUsernameSign').val() == "" || $('#inputPasswordSign').val() == ""){
            alert("All fields are required!");
        } else {
            var userDto = {
                "username": $('#inputUsernameSign').val(),
                "password": $('#inputPasswordSign').val()
            }
            authManager.login(JSON.stringify(userDto));
        }
    });
    $('#btnConfirmSignUp').click(function(e) {
        e.preventDefault();
        if($('#inputusername').val() == "" || $('#inputpassword').val() == "" || $('#inputemail').val() == "" ||
            $('#inputfirstname').val() == "" || $('#inputlastname').val() == ""){
            alert("All fields are required!");
        }else {
            var userDto = {
                "username": $('#inputusername').val(),
                "password": $('#inputpassword').val(),
                "email": $('#inputemail').val(),
                "firstname": $('#inputfirstname').val(),
                "lastname": $('#inputlastname').val()
            }
            authManager.register(JSON.stringify(userDto));
        }
    });

    $('#btnConfirmPreference').click(function(e) {
        e.preventDefault();
        userManager.updatePreference(JSON.stringify(userManager.getPreferenceInputs()));
    });
});

var authManager = {

    loadLoggedUser : function() {
        $.ajax({
            url : basePath + '/auth/me',
            dataType : 'json',
            type : 'POST',
            contentType : 'application/json',
            headers : storageManager.createAuthorizationTokenHeader(),
            success : function(loggedUser) {
                    storageManager.setLocalItem(1, loggedUser.authorities.length);
                    storageManager.setLocalItem(2, loggedUser.username);
                    jobsManager.recommendedJobs();
                    userManager.getUserPreference();
                    if (loggedUser.authorities.length == 2) {
                        $('#btnAdminPanel').show();
                    }
            }
        });
    },
    login : function(userDto) {
        $.ajax({
            url : basePath + '/auth/login',
            dataType : 'json',
            type : 'POST',
            contentType : 'application/json',
            data : userDto,
            success : function(data) {
               storageManager.setLocalItem(0, data.token);
               domManager.homePageState();
            },
            error : function (response) {
                alert(response.responseJSON.errorMessage);
            }
        });
    },
    register : function(userDto) {
        $.ajax({
            url : basePath + '/auth/register',
            dataType : 'json',
            type : 'POST',
            contentType : 'application/json',
            data : userDto,
            success : function(user) {
                domManager.signInPageState()
                alert("Successfuly registered! Check out mail for verification link!");
            },
            error : function (response) {
                alert(response.responseJSON.errorMessage);
            }
        });
    }
}

var storageManager = {

    getLocalItem : function(index) {
        return localStorage.getItem(index);
    },
    setLocalItem : function(index, value) {
        localStorage.setItem(index, value);
    },
    removeLocalItem : function(index) {
        localStorage.removeItem(index);
    },
    createAuthorizationTokenHeader : function() {
        var token = storageManager.getLocalItem(0);
        return {
            "Authorization" : "Bearer " + token
        };
    },
    logout: function () {
        storageManager.removeLocalItem(0);
        storageManager.removeLocalItem(1);
        storageManager.removeLocalItem(2);
    }

}

var jobsManager = {

    recommendedJobs: function() {
        $.ajax({
            url: basePath + '/jobs/user/' + storageManager.getLocalItem(2),
            type: 'GET',
            headers : storageManager.createAuthorizationTokenHeader(),
            success: function (jobs) {
                $('#recommendedJobs').empty();
                $.each(jobs, function(index, job) {
                    $('#recommendedJobs').append(
                        '<div class="col-md-6">' +
                            '<div class="card text-center m-2">' +
                                '<div class="card-header">' +
                                    '<a href="#" value="' + job.id + '" class="jobDetailsLink">' + job.title + '</a>' +
                                '</div>' +
                                '<div class="card-body">' +
                                    '<h5 class="card-title">' + job.location + '</h5>' +
                                    '<h6 class="card-title">' + job.type + '</h6>' +
                                    '<p class="card-text">' + job.description.slice(0, 80) + '</p>' +
                                '</div>' +
                                '<div class="card-footer text-muted">' + job.created_at + '</div>' +
                            '</div>' +
                        '</div>'
                    );
                });
                $('.jobDetailsLink').click(function(e) {
                    e.preventDefault();
                    var jobId = $(this).attr("value");
                    jobsManager.jobDetails(jobId);
                });
            },
            error: function (response) {
                console.log(response);
            }
        });
    },
    jobDetails: function (jobId) {
        domManager.jobDetailsPageState();
        $.ajax({
            url: basePath + '/jobs/' + jobId,
            type: 'GET',
            headers : storageManager.createAuthorizationTokenHeader(),
            success: function (job) {
                $('#jobDtlsTitle').text(job.title);
                $('#jobDtlsType').text(job.type);
                $('#jobDtlsLoc').text(job.location);
                $('#jobDtlsDesc').text(job.description);
                $('#jobDtlsCreatedAt').text(job.created_at);
                $('#jobDtlsHowToApply').text(job.how_to_apply);
                $('#jobDtlsCompany').text(job.company);
                $('#jobDtlsCompanyUrl').text(job.company_url);
                $('#jobDtlsCompanyLogo').attr("src", job.company_logo);
            }
        });
    },
    searchForJobs: function (jobDto) {
        $.ajax({
            url: basePath + '/jobs/search',
            type: 'POST',
            data: jobDto,
            dataType : 'json',
            contentType : 'application/json',
            success: function (jobs) {
                $('#searchedJobs').empty();
                $('#searchedJobs').append('<div class="col-md-12"><h4 class="text-center">Search result:</h4></div>');
                $.each(jobs, function(index, job) {
                    $('#searchedJobs').append(
                    '<div class="col-md-6">' +
                        '<div class="card text-center m-2">' +
                            '<div class="card-header">' +
                                '<a href="#" value="' + job.id + '" class="jobDetailsLink">' + job.title + '</a>' +
                            '</div>' +
                            '<div class="card-body">' +
                                '<h5 class="card-title">' + job.location + '</h5>' +
                                '<h6 class="card-title">' + job.type + '</h6>' +
                                '<p class="card-text">' + job.description.slice(0, 80) + '</p>' +
                            '</div>' +
                            '<div class="card-footer text-muted">' + job.created_at + '</div>' +
                        '</div>' +
                    '</div>');
                });
            }
        });
    }
}

var adminManager = {

    usersPagination: function() {
        $.ajax({
            url: basePath + '/users/count',
            type: 'GET',
            headers : storageManager.createAuthorizationTokenHeader(),
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
            headers : storageManager.createAuthorizationTokenHeader(),
            success : function(users) {
                $('#tableBody').empty();
                $.each(users, function(index, user) {
                    preferedWorkType = user.fullTime == true ? "Full time" : "Part time";
                    jobDesc = user.jobDesc == null ? "None" : user.jobDesc;
                    jobLoc = user.jobLocation == null ? "None" : user.jobLocation;
                    notifTime = user.notificationTime == null ? "None" : user.notificationTime;
                    notifOnOff = user.notificationOn == true ? "ON" : "OFF";
                    $('#tableBody').append(
                        '<tr>' +
                            '<th>' + user.id + '</th>' +
                            '<td>' + user.username + '</td>' +
                            '<td>' + user.email + '</td>' +
                            '<td>' + jobDesc + '</td>' +
                            '<td>' + jobLoc + '</td>' +
                            '<td>' + preferedWorkType + '</td>' +
                            '<td>' + notifOnOff + "/" + user.notificationType + '</td>' +
                            '<td>' + notifTime + '</td>' +
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
            }
        });
    },
    deleteUser: function(id) {
        $.ajax({
            url: basePath + '/users/' + id,
            type: 'DELETE',
            headers : storageManager.createAuthorizationTokenHeader(),
            success: function () {
                adminManager.usersPagination();
                adminManager.createAdminPage(0, 3);
            },
            error : function (response) {
                alert(response.responseJSON.errorMessage);
            }
        });
    }
}

var userManager = {
    updatePreference: function (userDto) {
        $.ajax({
            url: basePath + '/users',
            dataType : 'json',
            type : 'PUT',
            contentType : 'application/json',
            data : userDto,
            headers : storageManager.createAuthorizationTokenHeader(),
            success: function (user) {
                userManager.setUserPreference(user);
                domManager.homePageState();
            },
            error: function (response) {
                console.log(response);
            }
        });
    },
    getUserPreference: function () {
        $.ajax({
            url: basePath + '/users/preference/' + storageManager.getLocalItem(2),
            dataType : 'json',
            type : 'GET',
            headers : storageManager.createAuthorizationTokenHeader(),
            success: function (user) {
                userManager.setUserPreference(user);
            }
        });
    },
    setUserPreference: function (user) {
        $('#notificationButtons').empty();
        if(user.notificationOn == true) {
            $('#notificationButtons').append(
                "<button class=\"btn btn-danger btn-lg\" id=\"btnTurnOffNotification\">Turn off</button>");
        } else {
            $('#notificationButtons').append(
                "<button class=\"btn btn-primary btn-lg\" id=\"btnTurnOnNotification\">Turn on</button>")
        }
        $('#btnTurnOnNotification').click(function(e) {
            e.preventDefault();
            notificationDTO = schedulerManager.getNotificationDTO();
            if(notificationDTO == null){
                alert("Notification time, description and location cannot be empty & type cannot be NEVER!");
            } else {
                schedulerManager.addTask(JSON.stringify(notificationDTO));
                userManager.updatePreference(JSON.stringify(userManager.getPreferenceInputs()));
            }
        });
        $('#btnTurnOffNotification').click(function(e) {
            e.preventDefault();
            schedulerManager.removeTask();
        });

        $('#inputNotfTime').val(user.notificationTime);
        $('#inputjobdesc').val(user.jobDesc);
        $('#inputpreferedjobloc').val(user.jobLocation);
        user.fullTime == true ?
            $("#selectWorkTypePreference").val('1') : $("#selectWorkTypePreference").val('2');
        if(user.notificationType == "NEVER"){
            $("#selectNotifTypePreference").val('1');
        } else if(user.notificationType == "DAILY"){
            $("#selectNotifTypePreference").val('2');
        } else if(user.notificationType == "WEEKLY"){
            $("#selectNotifTypePreference").val('3');
        } else if(user.notificationType == "MONTHLY"){
            $("#selectNotifTypePreference").val('4');
        } else {
            $("#selectNotifTypePreference").val('0');
        }
    },
    getPreferenceInputs: function () {
        var fullTime = $('#selectWorkTypeSearchJob').val() == 1 ? true : false;
        var notificationType = $('#selectNotifTypePreference').val() == 0 ?
            "NEVER" : $('#selectNotifTypePreference option:selected').text();

        userDto = {
            "username": storageManager.getLocalItem(2),
            "notificationTime": $('#inputNotfTime').val(),
            "notificationType": notificationType,
            "jobDesc": $('#inputjobdesc').val(),
            "jobLocation": $('#inputpreferedjobloc').val(),
            "fullTime": fullTime,
        }
        return userDto;
    }
}

var schedulerManager = {
    
    removeTask: function () {
        $.ajax({
            url: basePath + '/scheduler/' + storageManager.getLocalItem(2),
            type: 'DELETE',
            headers : storageManager.createAuthorizationTokenHeader(),
            success: function () {

            },
            error : function (response) {
                console.log(response);
            }
        });
    
    },
    addTask: function (notificationDTO) {
        $.ajax({
            url : basePath + '/scheduler',
            dataType : 'json',
            type : 'POST',
            contentType : 'application/json',
            data : notificationDTO,
            //headers : storageManager.createAuthorizationTokenHeader(),
            success : function() {
            },
            error: function (response) {
            }
        });
    },
    getNotificationDTO: function () {
        notfDTO = userManager.getPreferenceInputs();
        if(notfDTO.notificationTime == "" || notfDTO.jobDesc == "" || notfDTO.jobLocation == ""
                || notfDTO.notificationType == "NEVER"){
            notfDTO = null;
        }
        return notfDTO;
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

        if (storageManager.getLocalItem(0) != null) {
            domManager.loggedState();
            authManager.loadLoggedUser();
        } else {
            domManager.loggedOutState();
            $('#btnAdminPanel').hide();
        }
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
