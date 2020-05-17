
function activateLink(htmlLink) {
	var menu = htmlLink.parentElement;
	var links = menu.getElementsByTagName('A');
	for (var i = 0; i < links.length; i++) {
		links[i].classList.remove("active");
	}
	
	htmlLink.classList.add("active");
}

function onPictureNotFound(htmlImg) {
	htmlImg.src = 'assets/images/photo_unknown.jpg';
}