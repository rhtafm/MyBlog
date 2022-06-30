$(function () {
    getCommentList();
})

function randomColor() {
    const color = ['primary', 'success', 'danger', 'warning', 'info']
    const t = Math.round(4 * Math.random());
    return color[t]
}

function getCommentList() {
    $.ajax({
        url: '/comments',
        type: 'get',
        dataType: 'json',
        success: function (result) {
            var it = $("#commentBody")
            it.html(null)
            $.each(result.data, function (index, comment) {
                var t = '<div class="alert alert-' + randomColor() + '" role="alert">' +
                    '       <h5>' +
                    '           用户名: <span class="text-dark">' + comment.user.username + '</span>&nbsp;&nbsp;&nbsp;' +
                    '           邮箱: <span class="text-dark">' + comment.user.email + '</span>' +
                    '       </h5>' +
                    '       <h4 class="text-center">' + comment.content + '</h4>' +
                    '    </div>'
                it.append(t)
            })
        }
    })
}

$("#comment-btn-open").click(function () {
    if (isLogin) {
        $("#comment-btn").click()
    } else {
        $("#tooltip-msg").html("请先登录")
        $("#tooltip-open").click()
    }
})

$("#comment-save").click(function () {
    const content = $("#comment-input").val().trim()
    if (content != null && content.length > 0) {
        commentSubmit(content)
    }else{
        $("#tooltip-msg").html("请输入评论内容")
        $("#tooltip-open").click()
    }
});

function commentSubmit(content) {
    $.ajax({
        url: '/comments',
        type: 'post',
        dataType: 'json',
        data: {
            content: content
        },
        success: function (result) {
            if (result.code == 1) {
                $("#tooltip-msg").html('提交成功')
            } else {
                $("#tooltip-msg").html('提交失败')
            }
            getCommentList();
            $("#tooltip-open").click()
        }
    })
}