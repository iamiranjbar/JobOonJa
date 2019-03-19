var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
var xhr = new XMLHttpRequest();
xhr.open("GET", "http://localhost:8080/project", true);
xhr.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
       console.log(xhr.responseText);
    }
};
xhr.send(null);