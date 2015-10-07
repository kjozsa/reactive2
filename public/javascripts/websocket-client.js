var messages = [];

function init() {
    var websocket = new WebSocket("@routes.Application.wsocket.webSocketURL()");

//                websocket.onopen = function(evt) {
//                    alert("connected to websocket");
//                }

    websocket.onmessage = function (evt) {
        var data = JSON.parse(evt.data)

        switch (data.type) {
            case "status":
                messages.push("<p><b>" + data.user + ":</b> " + linkifyStr(data.content) + "</p>");
                if (messages.length > 8) messages.shift();
                $('#replaceDiv').html(messages);
                break;

            case "clients":
                $('#replaceClients').text(data.clients + " clients online");
                break;
        }
    }

    websocket.onclose = function (evt) {
        setTimeout(function () {
            init();
        }, 5000);
    }
}
init();