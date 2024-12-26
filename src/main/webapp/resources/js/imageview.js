const bigImage = document.getElementById("image-fullscreen");
const bigImageBackground = document.getElementById("big-image-background");

document.querySelectorAll("img.thumbnail").forEach(element => {
  element.addEventListener("click", (e) => {
    const src = e.target.getAttribute("src")
      .replace(/\/thumbnail\//g, "/");

    bigImage.setAttribute("src", src);
    bigImageBackground.style.display="flex";
  });
})

bigImageBackground.addEventListener("click", () => {
  bigImageBackground.style.display = "none";
});