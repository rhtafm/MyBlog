function initMD() {
    editormd("md-content", {
        placeholder: "在此处编辑博客内容",
        width: "100%",
        height: 700,
        syncScrolling: "single",
        previewTheme: "dark",
        editorTheme: "3024-night",
        tex: !0,
        path: "../../static/lib/markdown/lib/"
    })
}

function listCategory() {
    $.ajax({
        url: "/categories",
        type: "get",
        dataType: "json",
        success: function (result) {
            const articleCategoryId = $("input[name='categoryId']").val()
            let it
            if (result.code == 1) {
                $("select[name='categoryId']").html("<option disabled selected value>请选择分类</option>")
                $.each(result.data, function (index, category) {
                    if (category.id == articleCategoryId)
                        it = '<option value="' + category.id + '" selected>' + category.name + "</option>";
                    else
                        it = '<option value="' + category.id + '">' + category.name + "</option>";
                    $("select[name='categoryId']").append(it)
                })
            }
        }
    })
}

function listTag() {
    $.ajax({
        url: "/tags",
        type: "get",
        dataType: "json",
        success: function (result) {
            const tagIds = JSON.parse($("input[name='tagIds']").val())
            let it
            if (result.code == 1) {
                $("select[name='tagIds']").html(null)
                $.each(result.data, function (index, tag) {
                    if (tagIds.indexOf(tag.id) != -1)
                        it = '<option value="' + tag.id + '" selected>' + tag.name + "</option>"
                    else
                        it = '<option value="' + tag.id + '">' + tag.name + "</option>"
                    $("select[name='tagIds']").append(it)
                })
            }
        }
    })
}

let flagTitle;
let flagCategory;

function testForm() {
    const title = $("input[name='title']").val().trim();
    const category = $("select[name='categoryId'] option:selected").val();
    flagTitle = false
    flagCategory = false
    if (title != null && title.length > 0) {
        flagTitle = true
        if (category != null && category.length > 0) {
            flagCategory = true
        } else {
            $("#tooltip-msg").html("请选择文章分类")
            $("#tooltip-open").click()
        }
    } else {
        $("#tooltip-msg").html("请填写文章标题")
        $("#tooltip-open").click()
    }
}

function submitArticle(status) {
    const tagIds = [];
    $("select[name='tagIds'] option:selected").each(function () {
        tagIds.push($(this).val())
    })
    $.ajax({
        url: "/articles",
        type: "post",
        data: {
            id: $("input[name='id']").val().trim(),
            title: $("input[name='title']").val().trim(),
            content: $("#content").val(),
            categoryId: $("select[name='categoryId'] option:selected").val(),
            tagIds: JSON.stringify(tagIds),
            summary: $("#summary").val(),
            coverImg: $("input[name='coverImg']").val(),
            status: status,
            _method: "put",
        },
        dataType: "json",
        success: function (result) {
            const it = $("#tooltip-msg")
            if (result.code == 1) {
                it.html(result.message)
                $("#tooltip-open").click()
                setTimeout(function () {
                    $("#tooltip-close").click()
                    window.location.href = "/admin/article"
                }, 1500)
            } else {
                it.html(result.message)
                $("#tooltip-open").click()
            }
        }
    })
}

$(function () {
    initMD()
    listCategory()
    listTag()
})
$("select[name='categoryId']").select2({
    placeholder: "请选择分类",
    single: "true"
})
$("select[name='tagIds']").select2({
    placeholder: "请选择标签"
})
$(".go-back").click(function () {
    history.go(-1)
})
$("#btn-save").click(function () {
    flagTitle = false
    flagCategory = false
    testForm()
    if (flagTitle && flagCategory) {
        submitArticle(0)
    }
})
$("#btn-release").click(function () {
    flagTitle = false
    flagCategory = false
    testForm()
    if (flagTitle && flagCategory) {
        submitArticle(1)
    }
});