const profile = document.querySelector(".header__body__right_side");

const profileArrow = document.querySelectorAll(".profile_arrow");
const toolbar = document.querySelector(".header_toolbar");

profile.onclick = function () {
    profileArrow[0].classList.toggle("active");
    profileArrow[1].classList.toggle("active");
    toolbar.classList.toggle("active");
};