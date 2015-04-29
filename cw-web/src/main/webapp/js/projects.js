var projectsIsOpen;

$(document).ready(function () {
    registerClickProjectsButton();
});

function registerClickProjectsButton() {
    $("#projectsBtn").click(function () {
        if (!projectsIsOpen) {
            openProjects();
        }else{
            closeProjects();
        }
    });
}

function openProjects() {
    $('#exitBtn').animate({'margin-right': "8%"});
    $("#myProjects").slideDown('fast');
    projectsIsOpen = true;
}

function closeProjects() {
    $('#exitBtn').animate({'margin-right': "0%"});
    $("#myProjects").slideUp('fast');
    projectsIsOpen = false;
}
