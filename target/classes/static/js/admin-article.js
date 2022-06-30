function listCategory() {
    $.ajax({
        url: "/categories",
        type: "get",
        dataType: "json",
        success: function (result) {
            if (result.code == 1) {
                $("select[name='categoryId']").html("<option disabled selected value>请选择分类</option>")
                $.each(result.data, function (index, category) {
                    const it = '<option value="' + category.id + '">' + category.name + "</option>"
                    $("select[name='categoryId']").append(it)
                })
            }
        }
    })
}

function randomTagColor() {
    const color = ['bg-info', 'bg-warning', 'bg-primary', 'bg-success', 'bg-danger']
    const t = Math.round(4 * Math.random());
    return color[t]
}

function articleStatus(t) {
    return t ? "已发布" : "草稿"
}

function searchArticle(pageNum) {
    $.ajax({
        url: "/articles/s",
        type: "get",
        dataType: "json",
        data: {
            page: pageNum,
            pageSize: 7,
            title: $("input[name='title']").val().trim(),
            categoryId: $("select[name='categoryId'] option:selected").val()
        },
        success: function (result) {
            if (result.code == 1) {
                $("#articleTableBody").html(null)
                $.each(result.data.list, function (index, article) {
                    const it = '<tr>' +
                        '       <td class="fw-bold">' +
                        '           <span class="d-inline-block text-truncate">' + article.title + '</span>' +
                        '       </td>' +
                        '        <td>' +
                        '            <span class="d-inline-block text-truncate">' + article.category.name + '</span>' +
                        '        </td>' +
                        '        <td class="articleTags"></td>' +
                        '        <td>' +
                        '             <span class="d-inline-block text-truncate">' + articleStatus(article.status) + '</span>' +
                        '        </td>' +
                        '        <td>' +
                        '              <span class="d-inline-block text-truncate">' + article.createTime + '</span>' +
                        '        </td>' +
                        '        <td>' +
                        '             <div class="text-truncate">' +
                        '                  <a href="/articles/m/' + article.id + '" class="btn-sm btn-primary text-decoration-none"><i class="fas fa-edit"></i> 修改</a>' +
                        '                  <a href="javascript:removeArticle(' + article.id + ');" class="btn-sm btn-danger text-decoration-none ms-1"><i class="fas fa-trash-alt"></i> 删除</a>' +
                        '             </div>' +
                        '         </td>' +
                        '       </tr>';
                    $("#articleTableBody").append(it)
                    $(".articleTags").eq(index).html(null)
                    if (article.tags != null && article.tags.length > 0) {
                        $.each(article.tags, function (t, tag) {
                            const it = '<label class="mx-1 btn badge rounded-pill ' + randomTagColor() + ' disabled">' + tag.name + "</label>";
                            $(".articleTags").eq(index).append(it)
                        })
                    } else {
                        $(".articleTags").eq(index).html("暂无标签")
                    }
                })
                $("#currentPage").html(result.data.pageNum)
                $("#totalPage").html(result.data.pages)
                pnDisabled()
            } else {
                $("#tooltip-msg").html('暂无内容')
                $("#tooltip-open").click()
            }
        }
    })
}

function removeArticle(id) {
    if (confirm("您确定要删除吗")) {
        $.ajax({
            url: "/articles/" + id,
            type: "post",
            dataType: "json",
            data: {
                _method: "delete",
            },
            success: function (result) {
                $("#tooltip-msg").html(result.message)
                $("#tooltip-open").click()
                searchArticle($("#currentPage").text())
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
    searchArticle(1)
    listCategory()
})
$(".btn-search").click(function () {
    searchArticle(1)
})
$(".prePage").click(function () {
    const current = $("#currentPage").text();
    if (current > 1) {
        searchArticle(Number(current) - 1)
    }
})
$(".nextPage").click(function () {
    const current = $("#currentPage").text();
    if ($("#totalPage").text() > current) {
        searchArticle(Number(current) + 1)
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