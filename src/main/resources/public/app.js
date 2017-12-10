$(function(){
    $("#btn-query").on("click", function() {
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
            var $buttons = $("#buttons");
            $buttons.empty();

            var keyboardType = res.keyboard.type;

            switch (keyboardType) {
                case "text":
                    break;
                case "buttons":
                    var buttons = res.keyboard.buttons;
                    $.each(buttons, function(i, item){
                        // console.log(i, item);
                        $buttons.append("<button class='btn-select-query' value='"+item+"'>"+item+"</button>");
                    });
                    break;
            }

            var $result = $("#result");
            var $message = $("<div class='message'></div>");
            if(res.message.photo){
                $message.append("<image src='"+res.message.photo.url+"'/>");
            }
            $message.append("<pre>"+res.message.text+"</pre>");

            console.log($message);

            $result.prepend($message);

        });
    });
});