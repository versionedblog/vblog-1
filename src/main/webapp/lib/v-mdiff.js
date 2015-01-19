$(document).ready(function(){
    //Second call with this:
    // Get object of URL parameters
    var allVars = $.getUrlVars();

    // Getting URL var by its nam
    var userId = $.getUrlVar('userId');
    userId = userId || 1;
    var messageId = $.getUrlVar('messageId');
    messageId = messageId || 1;
    var versionId = $.getUrlVar('versionId');
    versionId = versionId || -1;

    $("userId").val(userId);
    $("messageId").val(messageId);
    $("versionId").val(versionId);
    //var docId = $.url;
    //alert("hmmm!");
    $("#findOlder").click(function(event) {
        // Stop form from submitting normally
        event.preventDefault();

        // Get some values from elements on the page:
        var vuserId = $("#userId").val();
        var vmessageId = $("#messageId").val();
        var vversionId = $("#versionId").val() - 1;
        var vurl = "/rest/json/messagelist/all/message/diff";
        var requestData = { "userId" : vuserId,  "messageId": vmessageId, "versionId" : vversionId};

        $.ajax ({
            "url": vurl,
            "type": "POST",
            "data": JSON.stringify(requestData),
            "dataType": "json",
            "contentType": "application/json; charset=utf-8",
            "success": function(data){
                //alert('post complete' + data);
                //Verify that there was no failure
                if(data.success == false) {
                    alert(data.reason);
                    return;
                }
                //Set up hidden fields in form for next submit
                $("#userId").val(data.userId);
                $("#messageId").val(data.messageId);
                $("#versionId").val(data.versionNumber);
                //Manage Link show/hide
                $('#findOlder').show();
                $('#findNewer').show();
                if(data.versionNumber == data.maxVersionNumber) {
                    $('#findNewer').hide();
                }
                if(data.versionNumber == 1) {
                    $('#findOlder').hide();
                }
                //Handle Diff functionality
                $('#baseText').val(data.oldMessage);
                $('#newText').val(data.newMessage);
                diffUsingJS(0);
            }

        });
    });

    $("#findNewer").click(function(data) {
        // Stop form from submitting normally
        event.preventDefault();

        // Get some values from elements on the page:
        var vuserId = $("#userId").val();
        var vmessageId = $("#messageId").val();
        var vversionId = parseInt($("#versionId").val()) + 1;
        var vurl = "/rest/json/messagelist/all/message/diff";
        var requestData = { "userId" : vuserId,  "messageId": vmessageId, "versionId" : vversionId};

        $.ajax ({
            "url": vurl,
            "type": "POST",
            "data": JSON.stringify(requestData),
            "dataType": "json",
            "contentType": "application/json; charset=utf-8",
            "success": function(data){
                //alert('post complete' + data);
                //Verify that there was no failure
                if(data.success == false) {
                    alert(data.reason);
                    return;
                }
                 //Set up hidden fields in form for next submit
                $("#userId").val(data.userId);
                $("#messageId").val(data.messageId);
                $("#versionId").val(data.versionNumber);
                //Manage Link show/hide
                $('#findOlder').show();
                $('#findNewer').show();
                if(data.versionNumber == data.maxVersionNumber) {
                    $('#findNewer').hide();
                }
                if(data.versionNumber == 1) {
                    $('#findOlder').hide();
                }
                //Handle Diff functionality
                $('#baseText').val(data.oldMessage);
                $('#newText').val(data.newMessage);
                diffUsingJS(0);
            }

        });
    });

    ///all/diff/user/{userId}/message/{messageId}/version/{versionId}
    $.get("/rest/json/messagelist/all/diff/user/" + userId + "/message/" + messageId + "/version/" + versionId,function(data,status){
                if(data.success == false) {
                    alert(data.reason);
                    return;
                }
                //Set up hidden fields in form for next submit
                $("#userId").val(data.userId);
                $("#messageId").val(data.messageId);
                $("#versionId").val(data.versionNumber);
                //Manage Link show/hide
                $('#findOlder').show();
                $('#findNewer').show();
                if(data.versionNumber == data.maxVersionNumber) {
                    $('#findNewer').hide();
                }
                if(data.versionNumber == 1) {
                    $('#findOlder').hide();
                }
                //Handle Diff functionality
                $('#baseText').val(data.oldMessage);
                $('#newText').val(data.newMessage);
                diffUsingJS(0);
    });


});


function diffUsingJS(viewType) {
	"use strict";
	var byId = function (id) { return document.getElementById(id); },
		base = difflib.stringAsLines(byId("baseText").value),
		newtxt = difflib.stringAsLines(byId("newText").value),
		sm = new difflib.SequenceMatcher(base, newtxt),
		opcodes = sm.get_opcodes(),
		diffoutputdiv = byId("diffoutput");

	diffoutputdiv.innerHTML = "";

	diffoutputdiv.appendChild(diffview.buildView({
		baseTextLines: base,
		newTextLines: newtxt,
		opcodes: opcodes,
		baseTextName: "Older",
		newTextName: "Newer",
		contextSize: null,
		viewType: viewType
	}));
}