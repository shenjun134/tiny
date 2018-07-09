function fhQuery(pageIndex) {
  validateForm("searchForm");
  $("#error_msg").empty();
  $('#compact_pagination').empty();

  $('#loading_data').show();
  var form = $("#searchForm");
  var formData = form.serialize();
  $.ajax({
    url: form.attr("action").concat('?pageIndex=').concat(pageIndex === undefined ? 0 : pageIndex),
    data: formData,
    dataType: "json",
    type: form.attr("method"),
    success: function (resp) {
      $('#loading_data').hide();
      $("#result_tbody").empty();
      if (!resp.isSucc) {
        $("#error_msg").append(resp.message);
        return;
      }
      if (resp.data === undefined || resp.data.dataList === undefined || resp.data.dataList.length === 0) {
        $("#error_msg").append("No data found");
        return;
      }
      var page = resp.data.pageInfo;
      $('#compact_pagination').pagination({
        pages: page.totalPageCount,
        currentPage: page.currentPageIndex,
        cssStyle: 'compact-theme',
        displayedPages: 5,
        edges: 5,
        pageFunc: 'fhQuery',
      });
      var data = resp.data.dataList;
      var index = resp.data.pageInfo.beginIndex + 1;
      var tbody = "";
      for (var i = 0; i < data.length; i++) {
        var temp = data[i];
        var row = "<tr>";
        row = row.concat("<td>").concat(i + index).concat("</td>");
        row = row.concat("<td>").concat(temp.frameworkName).concat("</td>");
        row = row.concat("<td>").concat(temp.hostName).concat("</td>");
        row = row.concat("<td>").concat(temp.domain).concat("</td>");
        var tagHtml = '';
        var filterTagCreatedAt = temp.filterTagCreatedAt;
        for(var j = 0; j < filterTagCreatedAt.length; j++){
          var tagCreatedAt =  filterTagCreatedAt[j];
          var tagName = tagCreatedAt.tag;
          var message = tagCreatedAt.message;
          var createdAtLong = tagCreatedAt.createdAt;
          var createdAt = (new Date(createdAtLong)).toLocaleString();
          tagHtml = tagHtml.concat('<a class="tag-row" href="/tiny/hbase/mf?tag=').concat(tagName).concat('&framework=').concat(temp.frameworkName).concat('&host=').concat(temp.hostName).concat('&lastAt=').concat(createdAtLong).concat('">');
          tagHtml = tagHtml.concat('<span class="tag">').concat(message).concat('</span>:');
          tagHtml = tagHtml.concat('<span class="time">').concat(createdAt).concat('</span></a><br/>');
        }
        row = row.concat('<td class="long-msg" style="max-width: 400px;">').concat(tagHtml).concat("</td>");
        tbody = tbody.concat(row).concat("</tr>");
      }
      $("#result_tbody").append(tbody);
    },
    error: function (err) {
      $('#loading_data').hide();
      $("#error_msg").append(err);
    }
  });
}


function mfSqlQuery(pageIndex) {
  validateForm("searchForm1");
  $("#error_msg1").empty();
  $('#compact_pagination1').empty();
  var framework = $("#framework1").val();
  var host = $("#host1").val();
  if (strEmpty(framework) && strEmpty(host)) {
    $("#error_msg1").append("framework and host must enter");
    return;
  }

  var beginTime = $("#beginTime1").val();
  var endTime = $("#endTime1").val();

  if (strEmpty(beginTime) || strEmpty(endTime)) {
    $("#error_msg1").append("begin time and end time must enter");
    return;
  }
  $('#loading_data1').show();
  var form = $("#searchForm1");
  var formData = form.serialize();
  $.ajax({
    url: form.attr("action").concat('?pageIndex=').concat(pageIndex === undefined ? 0 : pageIndex),
    data: formData,
    dataType: "json",
    type: form.attr("method"),
    success: function (resp) {
      $('#loading_data1').hide();
      $("#result_tbody1").empty();
      if (!resp.isSucc) {
        $("#error_msg1").append(resp.message);
        return;
      }
      if (resp.data === undefined || resp.data.dataList === undefined || resp.data.dataList.length === 0) {
        $("#error_msg1").append("No data found");
        return;
      }
      var page = resp.data.pageInfo;
      $('#compact_pagination1').pagination({
        pages: page.totalPageCount,
        currentPage: page.currentPageIndex,
        cssStyle: 'compact-theme',
        displayedPages: 5,
        edges: 5,
        pageFunc: 'mfSqlQuery',
      });
      var data = resp.data.dataList;
      var index = resp.data.pageInfo.beginIndex + 1;
      var tbody = "";
      for (var i = 0; i < data.length; i++) {
        var temp = data[i];
        var measurementId = temp.measurementId;
        var createdLong = temp.createdAt ? temp.createdAt : 0;
        var createdAt = createdLong ? new Date(createdLong).toLocaleString() : '';
        var onclickFunc = 'showMInfo("'.concat(measurementId).concat('", ').concat(createdLong).concat(')');
        console.log('onclickFunc', onclickFunc);
        var row = "<tr>";
        row = row.concat("<td>").concat(i + index).concat("</td>");
        row = row.concat("<td>").concat(temp.duration).concat("<span style='color: #ddd;'>(ms)<span></td>");
        row = row.concat('<td class="long-msg" style="max-width: 400px;">').concat(temp.sql).concat("</td>");
        row = row.concat("<td>").concat(temp.hostName).concat("</td>");
        row = row.concat("<td>").concat(temp.domain).concat("</td>");
        row = row.concat("<td>").concat(temp.frameworkName).concat("</td>");
        row = row.concat('<td>');
        row = row.concat('<div class="show-m-info" tooltip="show measurement" flow="down" data-id="').concat(measurementId).concat('" data-time="').concat(createdLong).concat('" onclick="showMInfo(this)">').concat(measurementId).concat("</div>");
        row = row.concat('<div class="show-m-info" tooltip="show call stack" flow="down" data-id="').concat(measurementId).concat('" data-time="').concat(createdLong).concat('" onclick="showCSInfo(this)">').concat(createdAt).concat("</div></td>");
        tbody = tbody.concat(row).concat("</tr>");
      }
      $("#result_tbody1").append(tbody);
    },
    error: function (err) {
      $('#loading_data1').hide();
      $("#error_msg1").append(err);
    }
  });
}

function mfExceptionQuery(pageIndex) {
  validateForm("searchForm2");
  $("#error_msg2").empty();
  $('#compact_pagination2').empty();
  var framework = $("#framework2").val();
  var host = $("#host2").val();
  if (strEmpty(framework) && strEmpty(host)) {
    $("#error_msg2").append("framework and host must enter");
    return;
  }
  var beginTime = $("#beginTime2").val();
  var endTime = $("#endTime2").val();

  if (strEmpty(beginTime) || strEmpty(endTime)) {
    $("#error_msg2").append("begin time and end time must enter");
    return;
  }
  $('#loading_data2').show();
  var form = $("#searchForm2");
  var formData = form.serialize();
  $.ajax({
    url: form.attr("action").concat('?pageIndex=').concat(pageIndex === undefined ? 0 : pageIndex),
    data: formData,
    dataType: "json",
    type: form.attr("method"),
    success: function (resp) {
      $('#loading_data2').hide();
      $("#result_tbody2").empty();
      if (!resp.isSucc) {
        $("#error_msg2").append(resp.message);
        return;
      }
      if (resp.data === undefined || resp.data.dataList === undefined || resp.data.dataList.length === 0) {
        $("#error_msg2").append("No data found");
        return;
      }
      var page = resp.data.pageInfo;
      $('#compact_pagination2').pagination({
        pages: page.totalPageCount,
        currentPage: page.currentPageIndex,
        cssStyle: 'compact-theme',
        displayedPages: 5,
        edges: 5,
        pageFunc: 'mfExceptionQuery',
      });
      var data = resp.data.dataList;
      var index = resp.data.pageInfo.beginIndex + 1;
      var tbody = "";
      for (var i = 0; i < data.length; i++) {
        var temp = data[i];
        var measurementId = temp.measurementId;
        var createdLong = temp.createdAt ? temp.createdAt : 0;
        var createdAt = createdLong ? new Date(createdLong).toLocaleString() : '';
        var onclickFunc = 'showMInfo("'.concat(measurementId).concat('", ').concat(createdLong).concat(')');
        console.log('onclickFunc', onclickFunc);
        var row = "<tr>";
        row = row.concat("<td>").concat(i + index).concat("</td>");
        row = row.concat("<td>").concat(temp.duration).concat("<span style='color: #ddd;'>(ms)<span></td>");
        row = row.concat('<td class="long-msg" style="max-width: 400px;">').concat(temp.exception).concat("</td>");
        row = row.concat('<td class="long-msg" style="max-width: 400px;">').concat(temp.errorMsg).concat("</td>");
        row = row.concat("<td>").concat(temp.hostName).concat("</td>");
        row = row.concat("<td>").concat(temp.domain).concat("</td>");
        row = row.concat("<td>").concat(temp.frameworkName).concat("</td>");
        row = row.concat('<td>');
        row = row.concat('<div class="show-m-info" tooltip="show measurement" flow="down" data-id="').concat(measurementId).concat('" data-time="').concat(createdLong).concat('" onclick="showMInfo(this)">').concat(measurementId).concat("</div>");
        row = row.concat('<div class="show-m-info" tooltip="show call stack" flow="down" data-id="').concat(measurementId).concat('" data-time="').concat(createdLong).concat('" onclick="showCSInfo(this)">').concat(createdAt).concat("</div></td>");
        tbody = tbody.concat(row).concat("</tr>");
      }
      $("#result_tbody2").append(tbody);
    },
    error: function (err) {
      $('#loading_data2').hide();
      $("#error_msg2").append(err);
    }
  });
}

function mfTimeRangQuery(pageIndex) {
  validateForm("searchForm3");
  $("#error_msg3").empty();
  $('#compact_pagination3').empty();
   var framework = $("#framework3").val();
  var host = $("#host3").val();
  if (strEmpty(framework) && strEmpty(host)) {
    $("#error_msg3").append("framework and host must enter");
    return;
  }
  var beginTime = $("#beginTime3").val();
  var endTime = $("#endTime3").val();

  if (strEmpty(beginTime) || strEmpty(endTime)) {
    $("#error_msg3").append("begin time and end time must enter");
    return;
  }
  $('#loading_data3').show();
  var form = $("#searchForm3");
  var formData = form.serialize();
  $.ajax({
    url: form.attr("action").concat('?pageIndex=').concat(pageIndex === undefined ? 0 : pageIndex),
    data: formData,
    dataType: "json",
    type: form.attr("method"),
    success: function (resp) {
      $('#loading_data3').hide();
      $("#result_tbody3").empty();
      if (!resp.isSucc) {
        $("#error_msg3").append(resp.message);
        return;
      }
      if (resp.data === undefined || resp.data.dataList === undefined || resp.data.dataList.length === 0) {
        $("#error_msg3").append("No data found");
        return;
      }
      var page = resp.data.pageInfo;
      $('#compact_pagination3').pagination({
        pages: page.totalPageCount,
        currentPage: page.currentPageIndex,
        cssStyle: 'compact-theme',
        displayedPages: 5,
        edges: 5,
        pageFunc: 'mfTimeRangQuery',
      });
      var data = resp.data.dataList;
      var index = resp.data.pageInfo.beginIndex + 1;
      var tbody = "";
      for (var i = 0; i < data.length; i++) {
        var temp = data[i];
        var row = "<tr>";
        var measurementId = temp.measurementId;
        var createdLong = temp.createdAt ? temp.createdAt : 0;
        var createdAt = createdLong ? new Date(createdLong).toLocaleString() : '';
        var onclickFunc = 'showMInfo("'.concat(measurementId).concat('", ').concat(createdLong).concat(')');
        console.log('onclickFunc', onclickFunc);
        row = row.concat("<td>").concat(i + index).concat("</td>");
        row = row.concat("<td>").concat(temp.duration).concat("<span style='color: #ddd;'>(ms)<span></td>");
        row = row.concat("<td>").concat(temp.thread).concat("</td>");
        row = row.concat("<td>").concat(temp.component).concat("</td>");
        row = row.concat("<td>").concat(temp.hostName).concat("</td>");
        row = row.concat("<td>").concat(temp.domain).concat("</td>");
        row = row.concat("<td>").concat(temp.frameworkName).concat("</td>");
        row = row.concat('<td>');
        row = row.concat('<div class="show-m-info" tooltip="show measurement" flow="down" data-id="').concat(measurementId).concat('" data-time="').concat(createdLong).concat('" onclick="showMInfo(this)">').concat(measurementId).concat("</div>");
        row = row.concat('<div class="show-m-info" tooltip="show call stack" flow="down" data-id="').concat(measurementId).concat('" data-time="').concat(createdLong).concat('" onclick="showCSInfo(this)">').concat(createdAt).concat("</div></td>");
        tbody = tbody.concat(row).concat("</tr>");
      }
      $("#result_tbody3").append(tbody);
    },
    error: function (err) {
      $('#loading_data3').hide();
      $("#error_msg3").append(err);
    }
  });
}


function mQueryFormValidate() {
  var beginTime = $("#beginTime").val();
  var endTime = $("#endTime").val();
  var id = $("#id").val();
  var createdAt = $("#createdAt").val();
  if (strEmpty(beginTime) || strEmpty(endTime)) {
    if (strEmpty(id) && strEmpty(createdAt)) {
      $("#error_msg").append("Measurement Id and CreatedAt(milliseconds) or begin time and end time must enter");
      return false;
    } else {
      return true;
    }
    $("#error_msg").append("begin time and end time must enter");
    return false;
  }
  return true;
}

function mmQuery() {
  validateForm("searchForm");
  $("#error_msg").empty();

  var beginTime = $("#beginTime").val();
  var endTime = $("#endTime").val();
  var id = $("#id").val();
  var createdAt = $("#createdAt").val();

  if (!mQueryFormValidate()) {
    return;
  }
  $('#loading_data').show();
  var form = $("#searchForm");
  var formData = form.serialize();
  $.ajax({
    url: form.attr("action"),
    data: formData,
    dataType: "json",
    type: form.attr("method"),
    success: function (resp) {
      $('#loading_data').hide();
      $("#result_tbody").empty();
      if (!resp.isSucc) {
        $("#error_msg").append(resp.message);
        return;
      }
      if (resp.data === undefined || resp.data.length === 0) {
        $("#error_msg").append("No data found");
        return;
      }
      var data = resp.data;
      var tbody = "";
      // for (var i = 0; i < data.length; i++) {
      //   var record = data[i];
      //   console.log('record', record);
      //   var createAt = new Date(record.createdAt);
      //   var row = "<tr>";
      //   row = row.concat("<td>").concat(i+1).concat("</td>");
      //   row = row.concat("<td>").concat('--- ').concat('-').concat(' ---').concat("</td>");
      //   row = row.concat('<td class="long-msg" style="max-width: 600px;" colspan="2">').concat('<strong>Created at</strong>: ').concat(createAt.toLocaleString()).concat('<br/><code>');
      //   row = row.concat(JSON.stringify(record.recordInfo)).concat("</code</td>");
      //   //row = row.concat('<td class="long-msg" style="max-width: 300px;"><code>').concat('---').concat("</code</td>");
      //   tbody = tbody.concat(row).concat("</tr>");
      //   for (var j = 0; j < record.measurementList.length; j++) {
      //     var temp = record.measurementList[j];
      //     console.log('temp', temp);
      //     var row = "<tr>";
      //     row = row.concat("<td>").concat(i + 1).concat('.').concat(j).concat("</td>");
      //     row = row.concat("<td>").concat(temp.parentKey).concat('-').concat(temp.selfKey).concat("</td>");
      //     row = row.concat('<td class="long-msg" style="max-width: 600px;"><code>').concat(JSON.stringify(temp.mInfo)).concat("</code></td>");
      //     row = row.concat('<td class="long-msg" style="max-width: 300px;"><code>').concat(JSON.stringify(temp.argument)).concat("</code</td>");
      //     tbody = tbody.concat(row).concat("</tr>");
      //   }
      // }
      for (var i = 0; i < data.length; i++) {
        var record = data[i];
        var row = "<tr>";
        row = row.concat("<td>").concat(i+1).concat("</td>");
        row = row.concat("<td>").concat(record.callStack).concat("</td>");
        tbody = tbody.concat(row).concat("</tr>");
      }
      $("#result_tbody").append(tbody);
    },
    error: function (err) {
      $('#loading_data').hide();
      $("#error_msg").append(err);
    }
  });
}

function tdmQuery(pageIndex) {
  validateForm("searchForm");
  $("#error_msg").empty();
  $('#compact_pagination').empty();
  var taskId = $("#taskId").val();
  if (strEmpty(taskId)) {
    $("#error_msg").append("task id is empty");
    return;
  }
  $('#loading_data').show();
  var form = $("#searchForm");
  var formData = form.serialize();
  $.ajax({
    url: form.attr("action").concat('?pageIndex=').concat(pageIndex === undefined ? 0 : pageIndex),
    data: formData,
    dataType: "json",
    type: form.attr("method"),
    success: function (resp) {
      $('#loading_data').hide();
      $("#result_tbody").empty();
      if (!resp.isSucc) {
        $("#error_msg").append(resp.message);
        return;
      }
      if (resp.data === undefined || resp.data.dataList === undefined || resp.data.dataList.length === 0) {
        $("#error_msg3").append("No data found");
        return;
      }
      var page = resp.data.pageInfo;
      $('#compact_pagination').pagination({
        pages: page.totalPageCount,
        currentPage: page.currentPageIndex,
        cssStyle: 'compact-theme',
        displayedPages: 5,
        edges: 5,
        pageFunc: 'tdmQuery',
      });
      var data = resp.data.dataList;
      var index = resp.data.pageInfo.beginIndex + 1;
      var tbody = "";
      for (var i = 0; i < data.length; i++) {
        var temp = data[i];
        var row = "<tr>";
        var createdAtLong = temp.createdAt;
        var createdAt = (new Date(createdAtLong)).toLocaleString();
        row = row.concat("<td>").concat(i + index).concat("</td>");
        row = row.concat("<td><a class='jump-link' href='/tiny/hbase/tdv?metricsId=").concat(temp.metricsId).concat("&lastAt=").concat(createdAtLong).concat("'>").concat(temp.metricsId).concat("</a></td>");
        row = row.concat("<td>").concat(temp.taskId).concat("</td>");
        row = row
          .concat('<td class="long-msg" style="max-width: 300px;"><code>')
          .concat(temp.tagList)
          .concat("</code></td>");
        row = row.concat("<td>").concat(temp.metricsName).concat("</td>");
        row = row.concat("<td>").concat(createdAt).concat("</td>");
        tbody = tbody.concat(row).concat("</tr>");
      }
      $("#result_tbody").append(tbody);
    },
    error: function (err) {
      $('#loading_data').hide();
      $("#error_msg").append(err);
    }
  });
}


function tdvQuery(pageIndex) {
  validateForm("searchForm");
  $("#error_msg").empty();
  $('#compact_pagination').empty();
  var metricsId = $("#metricsId").val();
  if (strEmpty(metricsId)) {
    $("#error_msg").append("metrics id is empty");
    return;
  }
  var beginTime = $("#beginTime").val();
  var endTime = $("#endTime").val();

  if (strEmpty(beginTime) && strEmpty(endTime)) {
    $("#error_msg").append("begin time and end time at least enter one");
    return;
  }
  $('#loading_data').show();
  var form = $("#searchForm");
  var formData = form.serialize();
  $.ajax({
    url: form.attr("action").concat('?pageIndex=').concat(pageIndex === undefined ? 0 : pageIndex),
    data: formData,
    dataType: "json",
    type: form.attr("method"),
    success: function (resp) {
      $('#loading_data').hide();
      $("#result_tbody").empty();
      if (!resp.isSucc) {
        $("#error_msg").append(resp.message);
        return;
      }
      if (resp.data === undefined || resp.data.dataList === undefined || resp.data.dataList.length === 0) {
        $("#error_msg3").append("No data found");
        return;
      }
      var page = resp.data.pageInfo;
      $('#compact_pagination').pagination({
        pages: page.totalPageCount,
        currentPage: page.currentPageIndex,
        cssStyle: 'compact-theme',
        displayedPages: 5,
        edges: 5,
        pageFunc: 'tdvQuery',
      });
      var data = resp.data.dataList;
      var index = resp.data.pageInfo.beginIndex + 1;
      var tbody = "";
      for (var i = 0; i < data.length; i++) {
        var temp = data[i];
        var row = "<tr>";
        row = row.concat("<td>").concat(i + index).concat("</td>");
        row = row.concat("<td>").concat(temp.time).concat("</td>");
        row = row.concat("<td>").concat(temp.value).concat("</td>");
        tbody = tbody.concat(row).concat("</tr>");
      }
      $("#result_tbody").append(tbody);
    },
    error: function (err) {
      $('#loading_data').hide();
      $("#error_msg").append(err);
    }
  });
}



function showMInfo(target) {
  var measurementId = $(target).attr('data-id');
  var createdAt = $(target).attr('data-time');
  console.log('measurementId', measurementId);
  console.log('createdAt', createdAt);
  var loadingHtml = '<div class="loading" id="loading_data" colspan="6" style="width: 100%; height: 100%; margin-top: 20%;">';
  loadingHtml = loadingHtml.concat('<div class="k-line k-line6-1" ></div>');
  loadingHtml = loadingHtml.concat('<div class="k-line k-line6-2" ></div>');
  loadingHtml = loadingHtml.concat('<div class="k-line k-line6-3" ></div>');
  loadingHtml = loadingHtml.concat('<div class="k-line k-line6-4" ></div>');
  loadingHtml = loadingHtml.concat('<div class="k-line k-line6-5" ></div>');
  loadingHtml = loadingHtml.concat('</div>');

  var concent = '<div id="measurement_info" class="measurement-info">';
  concent = concent.concat(loadingHtml).concat('</div>');

  zeroModal.show({
    title: 'Measurement',
    content: concent,
    esc: true,
    width: '80%',
    height: '80%',
    opacity: 0.8,
  });

  //TODO

  var formData = { measurementId: measurementId ? measurementId.trim() : '', createdAt: createdAt ? createdAt.trim() : '' };
  $.ajax({
    url: '/tiny/hbase/mm/query.do',
    data: formData,
    dataType: "json",
    type: 'post',
    success: function (resp) {
      $("#measurement_info").empty();
      if (!resp.isSucc) {
        $("#measurement_info").append(resp.message);
        return;
      }
      if (resp.data === undefined || resp.data.length === 0) {
        $("#measurement_info").append("No data found");
        return;
      }
      var data = resp.data;
      var tbody = "";
      var seq = 1;
      for (var i = 0; i < data.length; i++) {
        var record = data[i];
        console.log('record', record);
        var row = "<tr>";
        var createdLong = record.createdAt ? record.createdAt : 0;
        var createdAt = createdLong ? new Date(createdLong).toLocaleString() : '';
        row = row.concat("<td>").concat(seq).concat("</td>");
        row = row.concat("<td>").concat('--- ').concat('-').concat(' ---').concat("</td>");
        row = row.concat('<td class="long-msg" style="max-width: 600px;">').concat('<strong>Created at</strong>: ').concat(createdAt).concat('<br/><code>');
        row = row.concat(JSON.stringify(record.recordInfo)).concat("</code</td>");
        row = row.concat('<td class="long-msg" style="max-width: 300px;"><code>').concat('---').concat("</code</td>");
        tbody = tbody.concat(row).concat("</tr>");
        seq = seq + 1;

        for (var j = 0; j < record.measurementList.length; j++) {
          var temp = record.measurementList[j];
          console.log('temp', temp);
          var row = "<tr>";
          row = row.concat("<td>").concat(seq).concat("</td>");
          row = row.concat("<td>").concat(temp.parentKey).concat('-').concat(temp.selfKey).concat("</td>");
          row = row.concat('<td class="long-msg" style="max-width: 600px;"><code>').concat(JSON.stringify(temp.mInfo)).concat("</code></td>");
          row = row.concat('<td class="long-msg" style="max-width: 300px;"><code>').concat(JSON.stringify(temp.argument)).concat("</code</td>");
          tbody = tbody.concat(row).concat("</tr>");
          seq = seq + 1;
        }
      }
      var tableHtml = '<table class="table table-hover">';
      tableHtml = tableHtml.concat('<thead>');
      tableHtml = tableHtml.concat('<tr>');
      tableHtml = tableHtml.concat('<th>#</th>');
      tableHtml = tableHtml.concat('<th>Parent-Current</th>');
      tableHtml = tableHtml.concat('<th>Information</th>');
      tableHtml = tableHtml.concat('<th>Argument</th>');
      tableHtml = tableHtml.concat('</tr>');
      tableHtml = tableHtml.concat('</thead>');
      tableHtml = tableHtml.concat('<tbody>');
      tableHtml = tableHtml.concat(tbody);
      tableHtml = tableHtml.concat('</tbody>');
      tableHtml = tableHtml.concat('</thead>');
      $("#measurement_info").append(tableHtml);
    },
    error: function (err) {
      $('#measurement_info').hide();
    }
  });

}

function showCSInfo(target) {
  var measurementId = $(target).attr('data-id');
  var createdAt = $(target).attr('data-time');
  console.log('measurementId', measurementId);
  console.log('createdAt', createdAt);
  var loadingHtml = '<div class="loading" id="loading_data" colspan="6" style="width: 100%; height: 100%; margin-top: 20%;">';
  loadingHtml = loadingHtml.concat('<div class="k-line k-line6-1" ></div>');
  loadingHtml = loadingHtml.concat('<div class="k-line k-line6-2" ></div>');
  loadingHtml = loadingHtml.concat('<div class="k-line k-line6-3" ></div>');
  loadingHtml = loadingHtml.concat('<div class="k-line k-line6-4" ></div>');
  loadingHtml = loadingHtml.concat('<div class="k-line k-line6-5" ></div>');
  loadingHtml = loadingHtml.concat('</div>');

  var concent = '<div id="measurement_info" class="measurement-info">';
  concent = concent.concat(loadingHtml).concat('</div>');

  zeroModal.show({
    title: 'Measurement',
    content: concent,
    esc: true,
    width: '80%',
    height: '80%',
    opacity: 0.8,
  });

  //TODO

  var formData = { measurementId: measurementId ? measurementId.trim() : '', createdAt: createdAt ? createdAt.trim() : '' };
  $.ajax({
    url: '/tiny/hbase/mm/query.do',
    data: formData,
    dataType: "json",
    type: 'post',
    success: function (resp) {
      $("#measurement_info").empty();
      if (!resp.isSucc) {
        $("#measurement_info").append(resp.message);
        return;
      }
      if (resp.data === undefined || resp.data.length === 0) {
        $("#measurement_info").append("No data found");
        return;
      }
      var data = resp.data;
      var callStack = data ? (data.length > 0 ? data[0].callStack : 'no data found...' ) : 'no data found...';
      $("#measurement_info").append(callStack);
    },
    error: function (err) {
      $('#measurement_info').hide();
    }
  });

}



function showLoading(){
  $('#g-load-ct').attr('class', 'g-loading g-loading-show');
}


function hideLoading(){
  $('#g-load-ct').attr('class', 'g-loading g-loading-hide');
}

function showMessage(type, message){
  var clz = 'unknow';
  if(type === 'warning'){
    clz = 'warning';
  }else if(type === 'error'){
    clz = 'error';
  }else if(type === 'info'){
    clz = 'info';
  }else if(type === 'success'){
    clz = 'success';
  }
  $('#g-mesg-ct').html('');
  $('#g-mesg-ct').attr('class', 'g-message g-message-on');
  $('#g-mesg-ct').append('<span class="'+clz+'">' + message + '</span>');

  setTimeout(function(){
    hideMessage();
  }, 15000);
}

function hideMessage(){
  $('#g-mesg-ct').attr('class', 'g-message g-message-off');
}
