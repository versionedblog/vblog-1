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

                if(data.versionNumber == 1) {
                    $('#pver').html("None");
                } else {
                    $('#pver').html("Version#" + (data.versionNumber-1));
                }
                $('#cver').html("Version#" + data.versionNumber);


                //Manage Link show/hide
                /**
                $("#findOlder").attr("href", "mcompare.html?userId=" + data.userId + "&messageId=" + data.messageId + "&versionId=" + (data.versionNumber-1))
                $("#findNewer").attr("href", "mcompare.html?userId=" + data.userId + "&messageId=" + data.messageId + "&versionId=" + (data.versionNumber+1))
                $('#findOlder').show();
                $('#findNewer').show();
                if(data.versionNumber == data.maxVersionNumber) {
                    $('#findNewer').hide();
                }
                if(data.versionNumber == 1) {
                    $('#findOlder').hide();
                }
                **/
                //Handle Diff functionality
                $('#baseText').html(marked(data.oldMessage));
                $('#newText').html(marked(data.newMessage));


                //Link Management
                $('#versionNumber').html(data.versionNumber);
                if(data.versionNumber == 1) {
                    $('#prevVersionLink').html("Older Version");
                } else {
                    var prevVersionLink = $("<a></a>").attr("href", "mcompare.html?userId="+data.userId+"&messageId="+data.messageId + "&versionId=" + (data.versionNumber-1)).text("Older Version");
                    $('#prevVersionLink').html(prevVersionLink);
                }
                if(data.versionNumber == data.maxVersionNumber) {
                    $('#nextVersionLink').html("Newer Version");
                } else {
                    var nextVersionLink = $("<a></a>").attr("href", "mcompare.html?userId="+data.userId+"&messageId="+data.messageId + "&versionId=" + (data.versionNumber+1)).text("Newer Version");
                    $('#nextVersionLink').html(nextVersionLink);
                }

                var firstVersionLink = $("<a></a>").attr("href", "mcompare.html?userId="+data.userId+"&messageId="+data.messageId + "&versionId=" + 1).text("First Version");
                $('#firstVersionLink').html(firstVersionLink);

                var lastVersionLink = $("<a></a>").attr("href", "mcompare.html?userId="+data.userId+"&messageId="+data.messageId + "&versionId=" + data.maxVersionNumber).text("Latest Version");
                $('#lastVersionLink').html(lastVersionLink);
    });


});


