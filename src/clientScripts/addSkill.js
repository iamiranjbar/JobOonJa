var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
var readline = require('readline-sync');
var xhr = new XMLHttpRequest();
var skillName = readline.question("Which skill to add? ");
xhr.open("PUT", "http://localhost:8080/skill/2/" + skillName, true);
xhr.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
       console.log(xhr.responseText);
    }
};
xhr.send(null);