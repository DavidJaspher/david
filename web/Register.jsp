
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="jquery-3.3.1.min.js" type="text/javascript"></script>
      
        <script>
            function loaddata()
            {
                var DataString = 'option=loaddata';
                $.ajax({
                    url: "textbox.do", data: DataString, type: "post",
                    success: function (data) {
                        $("#loaddata").html(data);
                    },
                    error: function (xhr) {
                        alert('Request Status: ' + xhr.status + ' Status Text: ' + xhr.statusText + ' ' + xhr.responseText);
                    }
                });
            }
            function add()
            {
                var DataString = 'addval=' + $("#data_new").val() + '&option=adddata';
                $.ajax({
                    url: "textbox.do", data: DataString, type: "post",
                    success: function (data) {
                        loaddata();
                    },
                    error: function (xhr) {
                        alert('Request Status: ' + xhr.status + ' Status Text: ' + xhr.statusText + ' ' + xhr.responseText);
                    }
                });
            }
            function update(updateid)
            {
                var DataString = 'updateid=' + updateid + '&updateval=' + $("#data_" + updateid).val() + '&option=updatedata';
                $.ajax({
                    url: "textbox.do", data: DataString, type: "post",
                    success: function (data) {
                        loaddata();
                    },
                    error: function (xhr) {
                        alert('Request Status: ' + xhr.status + ' Status Text: ' + xhr.statusText + ' ' + xhr.responseText);
                    }
                });
            }
            loaddata();
        </script>
    </head>
    <body>
        <div id='loaddata' ></div>
    </body>
</html>
