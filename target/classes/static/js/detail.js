$(function () {
    editormd.markdownToHTML("content-blog", {
        htmlDecode: "style,script,iframe",
        emoji: false,
        taskList: false,
        tex: true,
        flowChart: true
    })
});