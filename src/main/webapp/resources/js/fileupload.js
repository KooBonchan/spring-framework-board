const writeForm = document['form-write'];
const allowedTypes = 'image/jpeg,image/png,image/gif,image/webp,image/svg+xml'.split(',')
let fileVerified = true;
writeForm.files.addEventListener('change',function (e) {
  const files = writeForm.files.files;
  if(files.length > 5){
    alert('You can upload up to 5 files');
    writeForm.files.value = '';
    fileVerified = false;
    return;
  }
  for(const file of files){
    if(file.size > 10 * 1024 * 1024) {
      alert('File "' + file.name + '" is too large. You can upload file with size up to 10MB');
      writeForm.files.value = '';
      fileVerified = false;
      return;
    }
    else if ( ! file.type in allowedTypes){
      alert('File "' + file.name + '" has not allowed format. Only JPG, PNG, GIF, WEBP, SVG are allowed');
      writeForm.files.value = '';
      fileVerified = false;
      return;
    }
  }
  fileVerified = true;
});
