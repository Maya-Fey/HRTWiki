var contentsShown = true;

function contentsShowHide()
{
	contentsShown ^= true;
	document.getElementById("contents").getElementsByTagName("ul")[0].setAttribute("style", contentsShown ? "" : "display: none;");
	document.getElementById("contentsshowhide").innerHTML = contentsShown ? "Hide" : "Show";
}

function nLI(number, ref, name)
{
	var li = document.createElement("li");
	var a = document.createElement("a")
	a.setAttribute("href", "#" + ref);
	li.appendChild(a);
	var span1 = document.createElement("span");
	span1.setAttribute("class", "contentsno");
	span1.innerHTML = number + " ";
	a.appendChild(span1);
	var span2 = document.createElement("span");
	span2.innerHTML = name;
	a.appendChild(span2);
	return li;
}

function generateContents()
{
	var eles = document.getElementById("contentinner").children;
	var contents = document.getElementById("contents");
	var ul = document.createElement("ul");
	contents.appendChild(ul);
	var h2ctr = 0;
	var h3ctr = 0;
	var curLiUl = null;
	for(var i = 0; i < eles.length; i++) {
		if(eles[i].tagName == "H2") {
			h2ctr++;
			h3ctr = 0;
			
			eles[i].id = "header-" + i;
			
			var li = nLI(h2ctr.toString(), eles[i].id, eles[i].innerHTML);
			ul.appendChild(li);
			curLiUl = document.createElement("ul");
			li.appendChild(curLiUl);			
		} else if(eles[i].tagName == "H3") {
			h3ctr++;
			
			eles[i].id = "header-" + i;
			curLiUl.appendChild(nLI(h2ctr.toString() + "." + h3ctr.toString(), eles[i].id, eles[i].innerHTML));
		}
	}
}

generateContents();
document.getElementById("contentsshowhide").onclick = contentsShowHide;