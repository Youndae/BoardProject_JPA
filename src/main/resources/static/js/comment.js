var token = $("meta[name='_csrf']").attr('content');
var header = $("meta[name='_csrf_header']").attr('content');

//commentReply Insert
$(document).on('click', "#commentReplyInsert", function(){

    console.log("comment reply insert");

    var commentData = {
        commentNo : $("#commentReplyInsert").val(),
        commentGroupNo : $("#commentGroupNo").val(),
        commentIndent : $("#commentIndent").val(),
        commentContent : $("#commentReplyContent").val(),
        commentUpperNo : $("#commentUpperNo").val(),
        boardNo : $("#boardNo").val(),
        imageNo : $("#imageNo").val(),
    };

    if(commentData.CommentContent == ""){
        alert("댓글을 입력해주세요");
        $("#commentReplyContent").focus();
    }else{
        var str = JSON.stringify(commentData);

        console.log("str : " + str);

        $.ajax({
            url: '/comment/commentReply',
            method: 'post',
            data: str,
            contentType: "application/json; charset=UTF-8",
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function(data){
                console.log("return data : " + data);
                location.reload();
            },
            error: function(request, status, error){
                alert("code : " + request.status + "\n"
                    + "message : " + request.responseText + "\n"
                    + "error : " + error);
            }
        })
    }
});

//comment Insert
$(function(){
    $("#commentInsert").click(function(){
        var commentContent = {
            commentContent : $("#commentContent").val(),
            boardNo : $("#boardNo").val(),
            imageNo : $("#imageNo").val(),
        }

        if(commentContent.commentContent == "")
            $("#commentContent").focus();
        else{
            var str = JSON.stringify(commentContent)

            $.ajax({
                url: '/comment/commentInsert',
                method: 'post',
                data: str,
                contentType: "application/json; charset=UTF-8",
                beforeSend: function(xhr){
                    xhr.setRequestHeader(header, token);
                },
                success: function(data){
                    console.log("return data :  " + data);
                    location.reload();
                },
                error: function(request, status, error){
                    alert("code : " +request.status + "\n"
                        + "message : " + request.responseText + "\n"
                        + "error : " + error);
                }
            })
        }
    })
})

//comment Delete
function cDel(obj){
    var commentNo = {
        commentNo : obj.attributes['value'].value,
    };


    console.log("commentNo : " + commentNo.toString());

    commentNo = JSON.stringify(commentNo);

    console.log("json commentNo : " + commentNo);
    $.ajax({
        url: '/comment/commentDelete',
        method: 'delete',
        data: commentNo,
        beforeSend: function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success: function(data){
            console.log("delete! return data : " + data);
        },
        error: function(request, status, error){
            alert("code : " + request.status + "\n"
                + "message : " + request.responseText + "\n"
                + "error : " + error);
        }
    })

}

//commentReply
function cReply(obj){
    $("#replyComment").remove();

    var RInput = $("#CommentReplyContent").val();
    var Num = obj.attributes['value'].value;
    var GNum = $(".comment-box[value="+Num+"] .commentGroupNo").val();
    var INum = $(".comment-box[value="+Num+"] .commentIndent").val();
    var UNum = $(".comment-box[value="+Num+"] .commentUpperNo").val();

    if(RInput == null){
        $(".comment-box[value="+Num+"]").append(
            "<div id=\"replyComment\">" +
            "<input type=\"text\" id=\"commentReplyContent\" name=\"commentReplyContent\">" +
            "<button class=\"btn btn-outline-info btn-sm\" type=\"button\" id=\"commentReplyInsert\" value=\""+ Num +"\">"+"작성"+"</button>" +
            "<input type=\"hidden\" id=\"commentGroupNo\" value=\""+GNum+"\">"+
            "<input type=\"hidden\" id=\"commentIndent\" value=\""+INum+"\">"+
            "<input type=\"hidden\" id=\"commentUpperNo\" value=\""+UNum+"\">"+
            "</div>"
        )
        $("#CommentReplyContent").focus();
    }
}