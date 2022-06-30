function listUser(pageNum) {
    $.ajax({
        url: "/user",
        type: "get",
        data: {
            page: pageNum,
            pageSize: 7
        },
        dataType: "json",
        success: function (result) {
            if (result.code == 1) {
                $("#userTableBody").html(null)
                $.each(result.data.list, function (index, user) {
                    const it = '<tr> ' +
                        '           <td class="fw-bold">' +
                        '               <div class="text-truncate">' + user.username + '</div>' +
                        '           </td> ' +
                        '           <td> ' +
                        '               <div class="text-truncate">' + user.email + '</div>' +
                        '           </td> ' +
                        '           <td class="my-width-100">' +
                        '               <div class="text-truncate text-center">' + (user.type == 1 ? "管理员" : "游客") + '</div>' +
                        '           </td> ' +
                        '           <td>' +
                        '               <div class="text-truncate">' + user.createTime + '</div>' +
                        '           </td>' +
                        '           <td>' +
                        '                <div class="text-truncate">' +
                        '                    <a href="javascript:updateUser(' + user.id + ',' + user.type + ')" class="btn-sm btn-primary text-decoration-none my-hover-bg"><i class="fas fa-edit"></i> 更新 </a>' +
                        '                    <a href="javascript:removeUser(' + user.id + ')" class="btn-sm btn-danger text-decoration-none ms-1"><i class="fas fa-trash-alt"></i> 移除 </a>' +
                        '                </div>' +
                        '           </td>' +
                        '       </tr>'
                    $("#userTableBody").append(it)
                    $("#currentPage").html(result.data.pageNum)
                    $("#totalPage").html(result.data.pages)
                    pnDisabled()
                })
            }
        }
    })
}

$("#admin").click(function () {
    $("#tourist").removeAttr("checked")
    $("#admin").attr({checked: 'checked'})
})

$("#tourist").click(function () {
    $("#admin").removeAttr("checked")
    $("#tourist").attr({checked: 'checked'})
})

function saveUpdate(id) {
    let type
    if ($("#admin").attr('checked') == undefined) {
        type = 0
    } else {
        type = 1
    }
    $.ajax({
        url: '/user/' + id,
        type: 'post',
        dataType: 'json',
        cache: false,
        data: {
            _method: 'put',
            type: type,
        },
        success: function (result) {
            // listUser($("#currentPage").text())
            $("#tooltip-msg").html(result.message)
            $("#tooltip-open").click()
            setTimeout(function () {
                window.location.href = '/admin/user'
            }, 1500);
        }
    })
}


function updateUser(id, t) {
    if (id != null)
        if (t == 1) {
            $("#tourist").removeAttr("checked")
            $("#admin").attr({checked: 'checked'})
        } else {
            $("#admin").removeAttr("checked")
            $("#tourist").attr({checked: 'checked'})
        }
    $("#user-change-btn").click()
    $("#user-change-save").click(function () {
        saveUpdate(id);
        $("#user-change-close").click()
    })
}


function removeUser(id) {
    if (confirm("您确定要移除该用户吗")) {
        $.ajax({
            url: "/user/" + id,
            type: "post",
            dataType: "json",
            data: {
                _method: "delete",
            },
            success: function (result) {
                $("#tooltip-msg").html(result.message)
                $("#tooltip-open").click()
                listUser($("#currentPage").text())
            }
        })
    }
}

function pnDisabled() {
    const current = $("#currentPage").text()
    if (current > 1) {
        $(".prePage").removeClass("disabled")
    } else {
        $(".prePage").addClass("disabled")
    }
    const total = $("#totalPage").text()
    if (total > current) {
        $(".nextPage").removeClass("disabled")
    } else {
        $(".nextPage").addClass("disabled")
    }
}

$(function () {
    listUser(1)
})
$(".prePage").click(function () {
    const current = $("#currentPage").text();
    if (current > 1) {
        listUser(Number(current) - 1)
    }
})
$(".nextPage").click(function () {
    const current = $("#currentPage").text();
    if ($("#totalPage").text() > current) {
        listUser(Number(current) + 1)
    }
})
$("#logout").click(function () {
    $.ajax({
        url: "/user/logout",
        type: "post",
        dataType: "json",
        success: function (result) {
            $("#tooltip-msg").html(result.message)
            $("#tooltip-open").click()
            setTimeout(function () {
                window.location.href = "/admin/login"
            }, 1500)
        }
    })
});