var contentsShown = true;

function contentsShowHide()
{
	contentsShown ^= true;
	document.getElementById("contents").getElementsByTagName("ul")[0].setAttribute("style", contentsShown ? "" : "display: none;");
	document.getElementById("contentsshowhide").innerHTML = contentsShown ? "Hide" : "Show";
}

document.getElementById("contentsshowhide").onclick = contentsShowHide;