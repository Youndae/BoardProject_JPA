var token = $("meta[name='_csrf']").attr('content');
var header = $("meta[name='_csrf_header']").attr('content');

$(document).ready(function(){
    //board Insert
    $("#insertProc").on('click', function(){
        var form = $("#insertBoardFrm");

        console.log("insert btn click");

        form.submit();
    })

    //board Modify
    $("#modifyProc").on('click', function(){
        var form = $("#insertBoardFrm");

        console.log("modify btn click");

        // form.attr('action', '/board/boardModify');
        form.submit();
    })

    //board ReplyInsert
    $("#replyInsertProc").on('click', function(){
        var form = $("#insertBoardFrm");

        console.log("replyInsertProc btn click");

        form.submit();
    })
})

$(function(){
    $("#reply").click(function(){
        console.log("board Reply");
        var boardNo = $("#boardNo").val();

        console.log("boardNo : " + boardNo);

        location.href = '/board/boardReply/' + boardNo;

    })
})

$(function(){
    $("#deleteBoard").click(function(){
        console.log("delete board");

        var delData = {
            boardNo : $("#boardNo").val()
        };
        console.log("boardNo : " + delData.toString());

        delData = JSON.stringify(delData);

        $.ajax({
            url: '/board/boardDelete',
            method: 'delete',
            data: delData,
            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success: function(data){
                console.log("delete success");
            },
            error: function(request, status, error){
                alert("code : " + request.status + "\n"
                    + "message : " + request.responseText + "\n"
                    + "error : " + error);
            }
        })

    })
})

$(function(){
    $("#modify").click(function(){
        console.log("modify");
        var boardNo = $("#boardNo").val();

        console.log("boardNo : " + boardNo);

        location.href = '/board/boardModify/' + boardNo;
    })
})