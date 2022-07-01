const regUsername = /^[a-zA-z0-9_]{5,10}$/;
const regPassword = /^[a-zA-Z0-9_]{5,18}$/;
const formUsername = $("input[name='username']");
const formPassword = $("input[name='password']");
let flagUsername = false;
let flagPassword = false;
formUsername.blur(function () {
    const username = formUsername.val().trim();
    if (username.length > 0) {
        if (regUsername.test(username)) {
            formUsername.removeClass("is-invalid");
            formUsername.addClass("is-valid");
            flagUsername = true
        } else {
            formUsername.removeClass("is-valid");
            formUsername.addClass("is-invalid");
            flagUsername = false
        }
    }
});
formPassword.blur(function () {
    const password = formPassword.val().trim();
    if (password.length > 0) {
        if (regPassword.test(password)) {
            formPassword.removeClass("is-invalid");
            formPassword.addClass("is-valid");
            flagPassword = true
        } else {
            formPassword.removeClass("is-valid");
            formPassword.addClass("is-invalid");
            flagPassword = false
        }
    }
});

function submitForm() {
    if (flagUsername && flagPassword) {
        login(formUsername.val().trim(), formPassword.val().trim())
    } else {
        if (!flagUsername) {
            formUsername.removeClass("is-valid");
            formUsername.addClass("is-invalid")
        }
        if (!flagPassword) {
            formPassword.removeClass("is-valid");
            formPassword.addClass("is-invalid")
        }
    }
}

$(".my-submit").click(function () {
    submitForm()
});
$("input").keydown(function (event) {
    if (event.keyCode === 13) {
        $("#username").blur();
        $("#password").blur();
        submitForm()
    }
});

function login(username, password) {
    $.ajax({
        url: "/user/login",
        type: "post",
        data: {
            username: username,
            password: password
        },
        dataType: "json",
        success: function (c) {
            if (c.code === 1) {
                $("#tooltip-msg").html(c.message);
                $("#tooltip-open").click();
                setTimeout(function () {
                    window.location.href = "/"
                }, 1500)
            } else {
                formUsername.removeClass("is-valid");
                formPassword.removeClass("is-valid");
                $("#login-form")[0].reset();
                $("#tooltip-msg").html(c.message);
                $("#tooltip-open").click()
            }
        }
    })
};
