var files = {};
var previewIndex = 0;
var deletefiles = {};
var step = 0;
var deleteNo = 0;
var token = $("meta[name='_csrf']").attr('content');
var header = $("meta[name='_csrf_header']").attr('content');

$(document).ready(function(){
    var imageNo = $("#modifyImageNo").val();

    console.log("imageNo : " + imageNo);

    if(imageNo != undefined){
        $.getJSON("/imageBoard/imageList", {imageNo: imageNo}, function(arr){
            $(arr).each(function(i, attach){
                $("#preview").append(
                    "<div class=\"preview-box\" value=\"" + attach.imageStep + "\">" +
                    "<img class=\"thumbnail\" id=\"imgName\" src=\"img/" + attach.imageName + "\"\/>" +
                    "<p>" + attach.oldName + "</p>" +
                    "<a href=\"#\" value=\"" + attach.imageStep + "\" onclick=\"deleteOldPreview(this)\">" +
                    "삭제" + "</a>" +
                    "</div>"
                );
                step = attach.imageStep;
            });
        }).fail(function(err){
            alert(err.responseText);
        })
    }

    $("#imageModify").on('click', function(){
        var form = $("#uploadForm")[0];
        var formData = new FormData(form);

        for(var index = 0; index < Object.keys(files).length; index++){
            formData.append('files', files[index]);
        }

        for(var index = 0; index < Object.keys(deletefiles).length; index++){
            formData.append('deletefiles', deletefiles[index]);
        }

        $.ajax({
            type: 'put',
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            url: '/imageBoard/imageModify',
            dataType: 'JSON',
            data: formData,
            beforeSend: function(xhr){
              xhr.setRequestHeader(header, token);
            },
            success: function(result){
                console.log("modify result : " + result);
            },
            error : function(request, status, error){
                alert("code : "+request.status + "\n"
                    + "message : "+request.responseText + "\n"
                    + "error : "+error);
            }
        })
    });

    $("#imageInsert").on('click', function(){
        var form = $("#uploadForm")[0];
        var formData = new FormData(form);

        for(var index = 0; index < Object.keys(files).length; index++){
            formData.append('files', files[index]);
        }

        $.ajax({
            type: 'post',
            enctype: 'multipart/form-data',
            process: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            url: '/imageBoard/imageInsert',
            dataType: 'JSON',
            data: formData,
            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success: function(result){
                console.log("insert result : " + result);
            },
            error : function(request, status, error){
                alert("code : "+request.status + "\n"
                    + "message : "+request.responseText + "\n"
                    + "error : "+error);
            }
        })
    });

    $("#attach input[type=file]").change(function(){
        addPreview($(this));
    });
});

function addPreview(input){
    if(input[0].files.length <= (5 - ($('.preview-box').length))) {
        for(var fileIndex = 0; fileIndex < input[0].files.length; fileIndex++){
            var file = input[0].files[fileIndex];
            if(validation(file.name))
                continue;

            setPreviewForm(file);
        }
    }else {
        alert('5장만 업로드 가능합니다.');
    }
}

function setPreviewForm(file, img){
    var reader = new FileReader();
    reader.onload = function(img){
        var length = $('.preview-box').length;
        var imgNum = ++step;
        var fileNum = 0;

        $("#preview").append(
                "<div class=\"preview-box\" id=\"newImg\" value=\"" + imgNum +"\">" +
                "<img class=\"thumbnail\" id=\"imgName\" src=\"" + img.target.result + "\"\/>" +
                "<p>" + file.name + "</p>" +
                "<a href=\"#\" value=\"" + imgNum + "\" onclick=\"deletePreview(this)\">" +
                "삭제" + "</a>"
                + "</div>"
        );
        files[fileNum] = file;
        fileNum++;
    };
    reader.readAsDataURL(file);
}

function deleteOldPreview(obj){
    var imgNum = obj.attributes['value'].value;
    var imgName = jQuery('#imgName').attr('src');
    var idx = imgName.lastIndexOf('/');
    var deleteImg = imgName.substring(idx + 1);

    deletefiles[deleteNo] = deleteImg;
    deleteNo++;

    $("#preview .preview-box[value=" + imgNum + "]").remove();
}

function deletePreview(obj){
    var imgNum = obj.attributes['value'].value;
    delete files[imgNum];

    $("#preview .preview-box[value=]" + imgNum + "]").remove();
}

function validation(fileName){
    fileName = fileName = "";
    var fileNameExtensionIndex = fileName.lastIndexOf('.') + 1;
    var fileNameExtension = fileName.toLowerCase().substring(
        fileNameExtensionIndex, fileName.length);

    if(!((fileNameExtension === 'jpg') || (fileNameExtension === 'gif') || (fileNameExtension === 'png') || (fileNameExtension === 'jpeg'))){
        alert('jpg, gif, png 확장자만 업로드가 가능합니다.');
        return true;
    }else {
        return false;
    }
}

$(function(){
    $("#modify").click(function(){
        var imageNo = $("#imageNo").val();

        location.href="/imageBoard/imageBoardModify/"+imageNo;
    })
})

$(function(){
    $("#delete").click(function(){
        var delData = {
            imageNo : $("#imageNo").val()
        };
        console.log("imageNo : " + delData.toString());

        delData = JSON.stringify(delData);

        $.ajax({
            url: '/imageBoard/imageDelete',
            method: 'delete',
            data: delData,
            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success: function(data){
                console.log("success");
            },
            error: function(request, status, error){
                alert("code : " + request.status + "\n"
                    + "message : " + request.responseText + "\n"
                    + "error : " + error);
            }
        })
    })
})