window.addEventListener("DOMContentLoaded", e => {
    const t = document.body.querySelector("#sidebarToggle");
    t && t.addEventListener("click", e => {
        e.preventDefault(), document.body.classList.toggle("sidenav-toggled"), localStorage.setItem("sidebar-toggle", document.body.classList.contains("sidenav-toggled"))
    })
});