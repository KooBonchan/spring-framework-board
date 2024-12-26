document.querySelectorAll(".image-container").forEach((element) => {
  element.addEventListener("click",(e) => {
    element.classList.toggle("deleted");
  })
})

function verifyUpdateFileCounts(){
  const form = document['form-write'];
  const originalCount = document.querySelectorAll(".image-container").length;
  const deletedCount = document.querySelectorAll(".image-container.deleted").length;
  const totalFileCount = form.files.files.length + originalCount - deletedCount;
  console.log(originalCount, deletedCount. totalFileCount);
  if(totalFileCount > 5){
    alert('Too many images. You can have up to 5 images per document.')
    return false;
  }
  return true;
}

function appendImageUpdates() {
  const form = document['form-write'];
  form.querySelectorAll('input[name="deletedFiles"]')
    .forEach(input => input.remove());
  document.querySelectorAll(".image-container").forEach((element) => {
    if(element.classList.contains("deleted")){
      const input = document.createElement('input');
      input.type = 'hidden';
      input.name = 'deletedFiles';
      input.value = element.dataset.imageIdx || '';
      form.appendChild(input);
    }
  })
}
