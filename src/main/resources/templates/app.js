var stompClient = null;

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/exampleTopic', function (helloMessage) {
            showMessage(JSON.parse(helloMessage.body).content);
        });
    });
}

function showMessage(message) {
    $("#messages").append("<p>" + message + "</p>");
}

$(function () {
    connect();
});