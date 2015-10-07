function startWebSocket(endpoint) {
    var messages = [];
    var websocket = new WebSocket(endpoint);

    //websocket.onopen = function (evt) {
    //    alert("connected to websocket");
    //}

    websocket.onmessage = function (evt) {
        var data = JSON.parse(evt.data)

        switch (data.type) {
            case "status":
                messages.push("<p><b>&lt;" + data.user + "&gt;</b> " + linkifyStr(data.content) + "</p>");
                if (messages.length > 10) messages.shift();
                $('#replaceDiv').html(messages);
                break;

            case "clients":
                var msg = data.clients == 1 ? " client online" : " clients online";
                $('#replaceClients').text(data.clients + msg);
                break;
        }
    }

    websocket.onclose = function (evt) {
        setTimeout(function () {
            startWebSocket(endpoint);
        }, 5000);
    }
}