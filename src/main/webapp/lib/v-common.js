/**
Must be included after jquery to work correctly.
**/
$.extend({
  getUrlVars: function(){
    var vars = [], hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for(var i = 0; i < hashes.length; i++)
    {
      hash = hashes[i].split('=');
      vars.push(hash[0]);
      vars[hash[0]] = hash[1];
    }
    return vars;
  },
  getUrlVar: function(name){
    return $.getUrlVars()[name];
  }
});



function dateformat1(datems) {

    var m_names = new Array("January", "February", "March",
    "April", "May", "June", "July", "August", "September",
    "October", "November", "December");

    var m_formatted = new Array("01", "02", "03",
        "04", "05", "06", "07", "08", "09",
        "10", "11", "12");

    var d = new Date(datems);
    var curr_date = d.getDate();
    var curr_month = d.getMonth();
    var curr_year = d.getFullYear();
    var curr_hour = d.getHours();
    var curr_min = d.getMinutes();
    var curr_sec = d.getSeconds();
    var str = m_formatted[curr_month] + "-" + curr_date + "-" + curr_year + " " + curr_hour + ":" + curr_min + ":" + curr_sec;

    return str;
}

