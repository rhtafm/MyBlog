function welcome() {
    $.ajax({
        url: "/user/current",
        type: "get",
        dataType: "json",
        success: function (result) {
            if (result.code == 1) {
                if (result.data.avatar != null) {
                    $("#user-avatar").attr({src: result.data.avatar})
                }
                const userType = result.data.type
                $("#user-type").html(userType == 1 ? "管理员" : "游客")
            }
        }
    })
}

function articleCount(status) {
    $.ajax({
        url: "/articles/count/" + status,
        type: "get",
        dataType: "json",
        success: function (result) {
            if (result.code == 1) {
                $(".article-count").text(result.data)
            }
        }
    })
}

function categoryCount() {
    $.ajax({
        url: "/categories/count",
        type: "get",
        dataType: "json",
        success: function (result) {
            if (result.code == 1) {
                $(".category-count").text(result.data)
            }
        }
    })
}

function tagCount() {
    $.ajax({
        url: "/tags/count",
        type: "get",
        dataType: "json",
        success: function (result) {
            if (result.code == 1) {
                $(".tag-count").text(result.data)
            }
        }
    })
}

function commentCount() {
    $.ajax({
        url: "/comments/count",
        type: "get",
        dataType: "json",
        success: function (result) {
            if (result.code == 1) {
                $(".comment-count").text(result.data)
            }
        }
    })
}

function htmlCategory(index, category) {
    const it = '<li class ="list-group-item d-flex justify-content-between align-items-start mt-2">' +
        '           <div>' +
        '               <div class="text-truncate">' +
        '                   <span class="ms-2 fw-bold">' + category.name + '</span>' +
        '                   <span class="badge bg-info rounded-pill ms-2">' + category.articleCount + '</span>' +
        '               </div>' +
        '           </div>' +
        '           <div class="ms-sm-5"> ' +
        '               <div class="text-truncate">' +
        '                   <a href="javascript:modifyCategory(' + category.id + ",'" + category.name + "'" + ');" class="btn-sm btn-primary text-decoration-none my-hover-bg"> <i class="fas fa-edit"></i> 修改</a>' +
        '                   <a href="javascript:removeCategory(' + category.id + ');" class="btn-sm btn-danger text-decoration-none ms-2"><i class="fas fa-trash-alt"></i> 删除</a>' +
        '               </div>  ' +
        '           </div>' +
        '       </li>'
    $("#categoryOL").append(it)
}

function listAllCategory() {
    $.ajax({
        url: "/categories",
        type: "get",
        dataType: "json",
        success: function (result) {
            $("#categoryOL").html(null)
            if (result.code == 1) {
                $.each(result.data, function (index, category) {
                    htmlCategory(index, category);
                })
            }
        }
    })
}

function searchCategory(name) {
    $.ajax({
        url: "/categories/" + name,
        type: "get",
        dataType: "json",
        success: function (result) {
            $("#categoryOL").html(null)
            $.each(result.data, function (index, category) {
                htmlCategory(index, category);
            })
        }
    })
}

function addCategory(name) {
    $.ajax({
        url: "/categories",
        type: "post",
        data: {
            categoryName: name,
        },
        dataType: "json",
        success: function (result) {
            listAllCategory()
            categoryCount()
            $("#tooltip-msg").html(result.message)
            $("#tooltip-open").click()
        }
    })
}

function removeCategory(id) {
    if (confirm("您确定要删除吗")) {
        $.ajax({
            url: "/categories/" + id,
            type: "post",
            data: {
                _method: "delete",
            },
            dataType: "json",
            success: function (result) {
                listAllCategory()
                categoryCount()
                $("#tooltip-msg").html(result.message)
                $("#tooltip-open").click()
            }
        })
    }
}

function saveCategoryChange(id) {
    $.ajax({
        url: "/categories/" + id,
        type: "post",
        data: {
            _method: "put",
            categoryName: $("#category-change-input").val().trim(),
        },
        dataType: "json",
        success: function (result) {
            listAllCategory()
            $("#tooltip-msg").html(result.message)
            $("#tooltip-open").click()
        }
    })
}

function modifyCategory(id, name) {
    $("#category-change-input").val(name)
    $("#category-change-btn").click()
    $("#category-change-save").click(function () {
        saveCategoryChange(id)
    })
    $("#category-change-input").keydown(function (event) {
        if (event.keyCode == 13) {
            saveCategoryChange(id)
        }
    })
}

function htmlTag(index, tag) {
    const it = '<li class ="list-group-item d-flex justify-content-between align-items-start mt-2">' +
        '           <div>' +
        '               <div class="text-truncate">' +
        '                   <span class="ms-2 fw-bold">' + tag.name + '</span>' +
        '                   <span class="badge bg-info rounded-pill ms-2">' + tag.articleCount + '</span>' +
        '               </div>' +
        '           </div>' +
        '           <div class="ms-sm-5">' +
        '               <div class="text-truncate"> ' +
        '                   <a href="javascript:modifyTag(' + tag.id + ",'" + tag.name + "'" + ');" class="btn-sm btn-primary text-decoration-none my-hover-bg"><i class="fas fa-edit"></i> 修改</a>' +
        '                   <a href="javascript:removeTag(' + tag.id + ');" class="btn-sm btn-danger text-decoration-none ms-2"><i class="fas fa-trash-alt"></i> 删除</a>' +
        '               </div>' +
        '           </div>' +
        '       </li>'
    $("#tagOL").append(it)
}

function listAllTag() {
    $.ajax({
        url: "/tags",
        type: "get",
        dataType: "json",
        success: function (result) {
            $("#tagOL").html(null)
            $.each(result.data, function (index, tag) {
                htmlTag(index, tag)
            })
        }
    })
}

function searchTag(name) {
    $.ajax({
        url: "/tags/" + name,
        type: "get",
        dataType: "json",
        success: function (result) {
            $("#tagOL").html(null)
            $.each(result.data, function (index, tag) {
                htmlTag(index, tag)
            })
        }
    })
}

function addTag(name) {
    $.ajax({
        url: "/tags",
        type: "post",
        data: {
            tagName: name,
        },
        dataType: "json",
        success: function (result) {
            listAllTag()
            tagCount()
            $("#tooltip-msg").html(result.message)
            $("#tooltip-open").click()
        }
    })
}

function removeTag(id) {
    if (confirm("您确定要删除吗")) {
        $.ajax({
            url: "/tags/" + id,
            type: "post",
            data: {
                _method: "delete",
            },
            dataType: "json",
            success: function (result) {
                listAllTag()
                tagCount()
                $("#tooltip-msg").html(result.message)
                $("#tooltip-open").click()
            }
        })
    }
}

function saveTagChange(id) {
    $.ajax({
        url: "/tags/" + id,
        type: "post",
        data: {
            _method: "put",
            tagName: $("#tag-change-input").val().trim(),
        },
        dataType: "json",
        success: function (result) {
            listAllTag()
            $("#tooltip-msg").html(result.message)
            $("#tooltip-open").click()
        }
    })
}

function modifyTag(id, name) {
    $("#tag-change-input").val(name)
    $("#tag-change-btn").click()
    $("#tag-change-save").click(function () {
        saveTagChange(id)
    })
    $("#tag-change-input").keydown(function (event) {
        if (event.keyCode == 13) {
            saveTagChange(id)
        }
    })
}

$(function () {
    welcome()
    articleCount(1)
    categoryCount()
    tagCount()
    commentCount()
    listAllCategory()
    listAllTag()
})
$("#btn-category").click(function () {
    const categoryOp = $("select[name='categoryOp']").val();
    const inputData = $("#op-category").val().trim();
    if (categoryOp == 0) {
        searchCategory(inputData)
    } else {
        addCategory(inputData)
    }
})
$("#op-category").keydown(function (event) {
    if (event.keyCode == 13) {
        const categoryOp = $("select[name='categoryOp']").val();
        const inputData = $("#op-category").val().trim();
        if (categoryOp == 0) {
            searchCategory(inputData)
        } else {
            addCategory(inputData)
        }
    }
})
$("#btn-tag").click(function () {
    const tagOp = $("select[name='tagOp']").val()
    const inputData = $("#op-tag").val().trim()
    if (tagOp == 0) {
        searchTag(inputData)
    } else {
        addTag(inputData)
    }
})
$("#op-tag").keydown(function (event) {
    if (event.keyCode == 13) {
        const tagOp = $("select[name='tagOp']").val()
        const inputData = $("#op-tag").val().trim()
        if (tagOp == 0) {
            searchTag(inputData)
        } else {
            addTag(inputData)
        }
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