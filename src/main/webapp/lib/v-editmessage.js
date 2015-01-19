$(document).ready(function(){

    $('#saveLabel').hide();
    //Second call with this:
    // Get object of URL parameters
    //var allVars = $.getUrlVars();

    // Getting URL var by its nam
    var userId = $.getUrlVar('userId');
    var messageId = $.getUrlVar('messageId');
    //var docId = $.url;
    //alert("DocId=" + docId);
    var url = null;
    if(userId == null || messageId == null) {
        url = "/rest/json/messagelist/all/user/1/message";
        $.get(url,function(data,status){
            //alert("Data: " + data.uuid + "\nStatus: " + status);
            $('#uuid').val(data.uuid);
            $('#messageIdIndicator').html("Create Message");
            $('#versionIdIdIndicator').html("&nbsp;");

        });
    } else {
        url = "/rest/json/messagelist/all/user/" + userId + "/message/" + messageId + "/latest";
        $.get(url,function(data,status){
            //alert("Data: " + data.uuid + "\nStatus: " + status);
            $('#uuid').val(data.uuidOverride);
            $('#messageIdIndicator').html("Message #" + data.messageId);
            $('#versionIdIdIndicator').html("Message #" + data.vmessage.versionNumber);
            $('#messageId').val(data.messageId);
            $('#versionId').val(data.vmessage.versionNumber);
            $('#author').val(data.vmessage.author);
            $('#title').val(data.vmessage.title);
            $('#text-input').val(data.vmessage.content);
             $("#preview").html(marked("#" + data.vmessage.title + "\n" + "## *By:*" + data.vmessage.author + "\n" + data.vmessage.content));
        });
    }

//onclick="javascript:location.href='http://www.uol.com.br/'"


    function f1(event) {
                   // alert('TEST IT COMES HERE');
                    // Stop form from submitting normally
                    event.preventDefault();

                    // Get some values from elements on the page:
                    var $form = $( '#addMessageForm' ),
                        vmessageId = $form.find( "input[name='messageId']" ).val(),
                        vlastVersionId = $form.find( "input[name='lastVersionId']" ).val(),
                        vuserId = $form.find( "input[name='userId']" ).val(),
                        vauthor = $form.find( "input[name='author']" ).val(),
                        vtitle = $form.find( "input[name='title']" ).val(),
                        vcontent = $form.find( "textarea[name='content']" ).val(),
                        vuuid = $form.find( "input[name='uuid']" ).val(),
                        vurl = $form.attr( "action" );

                    var requestData = { "uuid" : vuuid,  "messageId": vmessageId, "lastVersionId" : vlastVersionId, "userId" : vuserId, "author" : vauthor, "title" : vtitle, "content" : vcontent};

                    $.ajax ({
                        "url": vurl,
                        "type": "POST",
                        "data": JSON.stringify(requestData),
                        "dataType": "json",
                        "contentType": "application/json; charset=utf-8",
                        "success": function(data){
                            $('#messageIdIndicator').html("Message #" + data.messageId);
                            $('#versionIdIndicator').html("Version #" + data.versionId);
                            $('#messageId').val(data.messageId);
                            $('#versionId').val(data.versionId);
                            $('#saveLink').hide();
                            $('#saveLabel').html("Saved");
                            $('#saveLabel').show();
                        }

                    });
    }

    function render() {
            $('#saveLink').show();
            $('#saveLabel').hide();
            var title = $( "#title" ).val();
            var author = $( "#author" ).val();
            var content = $( "#text-input" ).val();
            //$("#preview").html(marked("#" + title + "\n" + "## *By:*" + author + "\n" + content));

            $("#titlePreview").html(marked(title));
            $("#datePreview").html(marked(new Date().format("mmm d, yyyy HH:MM:ss")));
            $("#authorPreview").html(marked(author));
            $("#messagePreview").html(marked(content));
    }
    var saveLink = $("<a></a>").text("Save");
    $('#saveLink').html(saveLink);
    $('#saveLink').click(f1);
    /**
     $( "#saveLinkURL" ).click(function() {

     });
     **/

    $( "#author" ).keyup(function() {
        render();
    });
    $( "#title" ).keyup(function() {
        render();
    });
    $( "#text-input" ).keyup(function() {
        render();
    });

    $( "#addMessageForm" ).submit(function( event ) {
        //save(event);
    });



});
