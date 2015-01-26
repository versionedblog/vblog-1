<!--
<html>
  <head>
    <title>Sample Application JSP Page</title>
  </head>

  <body bgcolor=white>

  <table border="0" cellpadding="10">
    <tr>
      <td align=center>
        <img src="images/springsource.png">
      </td>
      <td>
         <h1>Sample Application JSP Page</h1>
      </td>
    </tr>
  </table>

  <br />
  <p>This  xxxxx is the output of a JSP page that is part of the HelloWorld application.</p>



  </body>
</html>
-->

<html>
  <head>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
      google.load("visualization", "1", {packages:["corechart"]});
      google.setOnLoadCallback(drawChart);
      function drawChart() {

        var data = google.visualization.arrayToDataTable([
          ['Task', 'Hours per Day'],
          ['Work',     11],
          ['Eat',      2],
          ['Commute',  2],
          ['Watch TV', 2],
          ['Sleep',    7]
        ]);

        var options = {
          title: 'My Daily Activities'
        };

        var chart = new google.visualization.PieChart(document.getElementById('piechart'));

        chart.draw(data, options);
      }
    </script>
  </head>
  <body>
    <div id="piechart" style="width: 900px; height: 500px;"></div>


     <%= new String("Hello!") %>
  </body>
</html>