/* RamblingWare JavaScript */
function openSidebar() {
    document.getElementById('sidebar').style.display = 'block';
}
function closeSidebar() {
    document.getElementById('sidebar').style.display = 'none';
}
window.onclick = function(event) {
  var modal = document.getElementById('share-popup');
    if (event.target === modal) {
      modal.style.display = 'none';
    }
};
function openPopup(name) {
  document.getElementById(name).style.display = 'block';
}
function closePopup(name) {
  document.getElementById(name).style.display = 'none';
}