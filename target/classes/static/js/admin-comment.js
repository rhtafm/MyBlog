function listComment(pageNum) {
    $.ajax({
        url: "/comments/pg",
        type: "get",
        data: {
            page: pageNum,
            pageSize: 7
        },
        dataType: "json",
        success: function (result) {
            if (result.code == 1) {
                $("#commentTableBody").html(null)
                $.each(result.data.list, function (index, comment) {
                    const it = '<tr> ' +
                        '           <td class="fw-bold">' +
                        '               <div class="text-truncate">' + comment.user.username + '</div>' +
                        '           </td> ' +
                        '           <td> ' +
                        '               <div class="text-truncate">' + comment.user.email + '</div>' +
                        '           </td> ' +
                        '           <td class="my-width-100">' +
                        '               <div class="text-truncate text-center">' + comment.content + '</div>' +
                        '           </td> ' +
                        '           <td>' +
                        '               <div class="text-truncate">' + comment.createTime + '</div>' +
                        '           </td>' +
                        '           <td>' +
                        '                <div class="text-truncate">' +
                        '                    <a href="javascript:showComment(' + comment.id + ')" class="btn-sm btn-primary text-decoration-none my-hover-bg"><i class="fas fa-eye"></i> 查看 </a>' +
                        '                    <a href="javascript:removeComment(' + comment.id + ')" class="btn-sm btn-danger text-decoration-none ms-1"><i class="fas fa-trash-alt"></i> 删除 </a>' +
                        '                </div>' +
                        '           </td>' +
                        '       </tr>'
                    $("#commentTableBody").append(it)
                    $("#currentPage").html(result.data.pageNum)
                    $("#totalPage").html(result.data.pages)
                    pnDisabled()
                })
            }
        }
    })
}

function showComment(id) {
    $.ajax({
        url: "/comments/" + id,
        type: "get",
        dataType: "json",
        success: function (result) {
            if (result.code == 1) {
                $(".modal-body").html(result.data.content)
                $("#btn-comment-show").click()
            }
        }
    })
}

function removeComment(id) {
    if (confirm("您确定要删除该评论吗")) {
        $.ajax({
            url: "/comments/" + id,
            type: "post",
            dataType: "json",
            data: {
                _method: "delete",
            },
            success: function (result) {
                $("#tooltip-msg").html(result.message)
                $("#tooltip-open").click()
                listComment($("#currentPage").text())
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
    listComment(1)
})
$(".prePage").click(function () {
    const current = $("#currentPage").text();
    if (current > 1) {
        listComment(Number(current) - 1)
    }
})
$(".nextPage").click(function () {
    const current = $("#currentPage").text();
    if ($("#totalPage").text() > current) {
        listComment(Number(current) + 1)
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