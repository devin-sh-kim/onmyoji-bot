$(function(){

    // $('.ui.sticky').sticky();

    $("#input-query").on("keypress", function(event){
        if(event.keyCode == 13){
            sendQuery();
            event.preventDefault();
        }
    });

    $("#btn-query").on("click", function() {
        sendQuery();
    });

    $("#result").on("click", ".btn-select-query", function(){
        var $this = $(this);
        var query = $this.val();
        $("#input-query").val(query);
        sendQuery();
    });

});

function sendQuery(){
    var query = $.trim($("#input-query").val());

    if(query == ""){
        var $query = $("#input-query");
        $query.val("");
        return false;
    }

    var data = {
        user_key: "WEB_USER",
        type: "text",
        content: query
    };
    $.ajax({
        url:"kakao/message",
        method: "POST",
        data: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json; charset=utf-8",
            "Cache-Control": "no-cache"
        },
    }).done(function(res){
        console.log(res);
        var $query = $("#input-query");
        $query.val("");


        var $result = $("#result");
        var $message = $("<div class='message ui piled yellow segment'></div>");
        if(res.message.photo){
            $message.append("<image src='"+res.message.photo.url+"'/>");
        }
        $message.append("<pre style='color: black'>"+res.message.text+"</pre>");

        if(res.keyboard){

            var keyboardType = res.keyboard.type;

            switch (keyboardType) {
                case "text":
                    break;
                case "buttons":
                    var buttons = res.keyboard.buttons;
                    var $buttons = $("<div class=''></div>");
                    $.each(buttons, function(i, item){
                        // console.log(i, item);
                        $buttons.append("<button class='ui mini blue button btn-select-query' value='"+item+"'>"+item+"</button>");
                    });
                    $message.append($buttons);
                    break;
            }

        }


        console.log($message);

        $result.prepend($message);

    });
}