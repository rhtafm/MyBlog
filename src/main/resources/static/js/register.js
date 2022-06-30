const regUsername = /^[a-zA-z0-9_]{5,10}$/;
const regEmail = /^[a-zA-Z0-9_]+@[a-zA-Z0-9_]+(\.[a-zA-Z0-9]+)+$/;
const regPassword = /^[a-zA-Z0-9_]{5,18}$/;
const formUsername = $("input[name='username']");
const formEmail = $("input[name='email']");
const formCheckCode = $("input[name='checkCode']");
const formPassword = $("input[name='password']");
const formPasswords = $("#passwords");
let flagUsername = false;
let flagEmail = false;
let flagPassword = false;
let flagPasswords = false;
formUsername.blur(function () {
    const username = formUsername.val().trim();
    if (username.length > 0) {
        if (regUsername.test(username)) {
            justUsername(username)
        } else {
            $("#usernameFeedback").html("请输入长度为 5 - 10 位的用户名(字母、数组或下划线)");
            formUsername.removeClass("is-valid");
            formUsername.addClass("is-invalid");
            flagUsername = false
        }
    }
});
formEmail.blur(function () {
    const email = formEmail.val().trim();
    if (email.length > 0) {
        if (regEmail.test(email)) {
            justEmail(email)
        } else {
            $("#emailFeedback").html("请输入合法的邮箱");
            formEmail.removeClass("is-valid");
            formEmail.addClass("is-invalid");
            flagEmail = false
        }
    }
});

formCheckCode.blur(function () {
    const checkCode = formCheckCode.val().trim();
    if (checkCode.length > 0) {
        if (checkCode.length == 6) {
            formCheckCode.removeClass("is-invalid");
        } else {
            formCheckCode.addClass("is-invalid");
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
formPasswords.blur(function () {
    const passwords = formPasswords.val().trim();
    if (passwords.length > 0 && flagPassword) {
        if (formPassword.val() === passwords) {
            formPasswords.removeClass("is-invalid");
            formPasswords.addClass("is-valid");
            flagPasswords = true
        } else {
            formPasswords.removeClass("is-valid");
            formPasswords.addClass("is-invalid");
            flagPasswords = false
        }
    }
});
$(".my-submit").click(function () {
    submitForm()
});
$("input").keydown(function (a) {
    if (a.keyCode === 13) {
        formUsername.blur();
        formEmail.blur();
        formPassword.blur();
        formPasswords.blur();
        submitForm()
    }
});

function submitForm() {
    const checkCode = $("#checkCode").val().trim();
    if (checkCode != null && checkCode.length > 0) {
        if (flagUsername && flagEmail && flagPasswords) {
            register(formUsername.val().trim(), formEmail.val().trim(), formPassword.val().trim())
        } else {
            if (!flagUsername) {
                formUsername.removeClass("is-valid");
                formUsername.addClass("is-invalid")
            }
            if (!flagEmail) {
                formEmail.removeClass("is-valid");
                formEmail.addClass("is-invalid")
            }
            if (!flagPassword) {
                formPassword.removeClass("is-valid");
                formPassword.addClass("is-invalid")
            }
            if (!flagPasswords && flagPassword) {
                formPasswords.removeClass("is-valid");
                formPasswords.addClass("is-invalid")
            }
        }
    } else {
        $("#checkCode").removeClass("is-valid");
        $("#checkCode").addClass("is-invalid")
    }
}

function justUsername(username) {
    $.ajax({
        url: "/user/username/" + username,
        type: "get",
        dataType: "json",
        success: function (result) {
            if (result.code == 0) {
                $("#usernameFeedback").html(result.message);
                formUsername.removeClass("is-valid");
                formUsername.addClass("is-invalid");
                flagUsername = false
            } else {
                formUsername.removeClass("is-invalid");
                formUsername.addClass("is-valid");
                flagUsername = true
            }
        }
    })
}

function justEmail(email) {
    $.ajax({
        url: "/user/email/" + email,
        type: "get",
        dataType: "json",
        success: function (result) {
            if (result.code == 0) {
                $("#emailFeedback").html(result.message);
                formEmail.removeClass("is-valid");
                formEmail.addClass("is-invalid");
                flagEmail = false
            } else {
                formEmail.removeClass("is-invalid");
                formEmail.addClass("is-valid");
                flagEmail = true
            }
        }
    })
}

var countdown = 60

function setTime(val) {
    if (countdown == 0) {
        val.removeClass('disabled');
        val.html('发送验证码');
        countdown = 60;
        return
    } else {
        val.addClass('disabled');
        val.html('重新发送(' + countdown + ')')
        countdown--;
    }
    setTimeout(function () {
        setTime(val);
    }, 1000);
}

function sendCode() {
    $.ajax({
        url: '/user/checkcode/' + formEmail.val().trim(),
        type: 'post',
        data: {
            _method: 'put'
        },
        success: function (result) {
            if (result.code == 1) {
                setTime($("#sendCheckCode"));
            }
        }
    })
}

$("#sendCheckCode").click(function () {
    if (flagUsername && flagEmail) {
        $("#sendCheckCode").addClass('disabled').html('<span class="spinner-border spinner-border-sm"></span>')
        sendCode();
    } else {
        $("#tooltip-msg").html("请填写合法的用户名或邮箱")
        $("#tooltip-open").click();
    }
})

function register(username, email, password) {
    $.ajax({
        url: "/user/register",
        type: "post",
        data: {
            username: username,
            email: email,
            password: password,
            checkCode: $("#checkCode").val().trim()
        },
        dataType: "json",
        success: function (result) {
            if (result.code == 1) {
                $("#tooltip-msg").html(result.message);
                $("#tooltip-open").click();
                setTimeout(function () {
                    window.location.href = "/admin/login"
                }, 2000)
            } else {
                formUsername.removeClass("is-valid");
                formEmail.removeClass("is-valid");
                formCheckCode.removeClass("is-valid");
                formPassword.removeClass("is-valid");
                formPasswords.removeClass("is-valid");
                $("#register-form")[0].reset();
                $("#tooltip-msg").html(result.message);
                $("#tooltip-open").click()
            }
        }
    })
}
