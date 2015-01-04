$(document).ready(function(){
    //Second call with this:
    // Get object of URL parameters
    //var allVars = $.getUrlVars();

    // Getting URL var by its nam
    var id = $.getUrlVar('id');
    //var docId = $.url;
    //alert("DocId=" + docId);
    var url = null;
    if(id == null) {
        url = "/rest/json/messagelist/all/user/1/message";
        $.get(url,function(data,status){
            //alert("Data: " + data.uuid + "\nStatus: " + status);
            $('#uuid').val(data.uuid);
        });
    } else {
        url = "/rest/json/messagelist/all/user/1/message/" + id + "/latest";
        $.get(url,function(data,status){
            //alert("Data: " + data.uuid + "\nStatus: " + status);
            $('#uuid').val(data.uuidOverride);
            $('#messageId').val(data.messageId);
            $('#versionId').val(data.vmessage.versionNumber);
            $('#author').val(data.vmessage.author);
            $('#title').val(data.vmessage.title);
            $('#text-input').val(data.vmessage.content);
             $("#preview").html(marked("#" + data.vmessage.title + "\n" + "## *By:*" + data.vmessage.author + "\n" + data.vmessage.content));
        });
    }


    //preview.innerHTML = markdown.toHTML(input.value);
    $( "#text-input" ).keyup(function() {
        var title = $( "#title" ).val();
        var author = $( "#author" ).val();
        var content = $( "#text-input" ).val();
        $("#preview").html(marked("#" + title + "\n" + "## *By:*" + author + "\n" + content));
    });

    $( "#addMessageForm" ).submit(function( event ) {

        // alert('TEST IT COMES HERE');
        // Stop form from submitting normally
        event.preventDefault();

        // Get some values from elements on the page:
        var $form = $( this ),
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
                //alert('post complete' + data);
                $('#messageId').val(data.messageId);
                $('#versionId').val(data.versionId);
            }

        });
    });



});
