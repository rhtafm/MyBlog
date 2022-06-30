let isLogin = false

$(function () {
    currentUser();
})

function currentUser() {
    $.ajax({
        url: "/user/current",
        type: "get",
        dataType: "json",
        success: function (result) {
            let it
            if (result.code == 1) {
                isLogin = true
                it = ' <button type="button" class="btn btn-danger dropdown-toggle" data-bs-toggle="dropdown"' +
                    '             aria-expanded="false">' +
                    '            <span class="login-username">' + result.data.username + '</span>' +
                    '      </button>' +
                    '      <ul class="dropdown-menu">' +
                    '          <li>' +
                    '              <button class="dropdown-item btn" onclick="logout()">注销</button>' +
                    '          </li>' +
                    '       </ul>'
            } else {
                it = '<a class="btn btn-danger" href="/admin/login">登录</a>'
            }
            $(".userBody").html(it);
        }
    })
}

function logout() {
    $.ajax({
        url: "/user/logout",
        type: "post",
        dataType: "json",
        success: function (result) {
            if (result.code == 1) {
                $("#tooltip-msg").html(result.message)
                $("#tooltip-open").click()
                $(".userBody").html('<a class="btn btn-danger" href="/admin/login">登录</a>')
            }
        }
    })
}