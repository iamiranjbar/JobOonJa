<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Projects</title>
</head>
<body>
    <script type="text/javascript">
        function defaultPart() {
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    var obj = JSON.parse(this.responseText);
                    for (car in obj)
                        document.getElementById("all").innerHTML = "all";
                }
            };
            xhttp.open("GET", "project", true);
            xhttp.send();
        }
    </script>
    <div id="all"></div>
    <button type="button" onClick="defaultPart()">Click Me!</button>
</body>
</html>