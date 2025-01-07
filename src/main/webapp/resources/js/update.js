document.querySelectorAll(".image-wrapper").forEach((element) => {
  element.addEventListener("click",(e) => {
    element.classList.toggle("deleted");
  })
})

function verifyUpdateFileCounts(){
  const form = document['form-write'];
  const originalCount = document.querySelectorAll(".image-wrapper").length;
  const deletedCount = document.querySelectorAll(".image-wrapper.deleted").length;
  const totalFileCount = form.files.files.length + originalCount - deletedCount;
  if(totalFileCount > 5){
    alert('Too many images. You can have up to 5 images per document.')
    return false;
  }
  return true;
}
