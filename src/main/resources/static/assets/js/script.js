var basePath = 'http://localhost:8080/api';

var isLogged = true;
var isAdminLogged = true;
var loggedUserId = 1;

$(document).ready(function() {

    $('#btnAdminPanel').hide();
    domManager.homePageState();

    $('.dropdown-toggle').dropdown()

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

var jobsManager = {

    recommendedJobs: function() {
        $.ajax({
            url: basePath + '/jobs/user/' + loggedUserId,
            type: 'GET',
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
            }
        });
    },
    jobDetails: function (jobId) {
        domManager.jobDetailsPageState();
        $.ajax({
            url: basePath + '/jobs/' + jobId,
            type: 'GET',
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

        if(isLogged){
            domManager.loggedState();
            jobsManager.recommendedJobs();
        } else{
            domManager.loggedOutState();
            $('#btnAdminPanel').hide();
        }
        if(isAdminLogged){
            $('#btnAdminPanel').show();
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
