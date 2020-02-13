var ws;

function connect() {
    var CreditApplicationId = document.getElementById("username").value;
    
    var host = document.location.host;
    var pathname = document.location.pathname;
    
    ws = new WebSocket("ws://" +host  + pathname + "/StatusUpdate/" + CreditApplicationId);

    ws.onmessage = function(event) {
      window.location.reload(false);
    };
}
