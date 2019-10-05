$(document).ready(async () => {
    let logReq = await fetch("/api/getAllLogs.json");
    console.log(JSON.stringify(logReq, null, "\t"))
    if(!logReq.ok) {
        let errorStr = "An error occured. Response code <code>"
            + logReq.status + "</code>, status <code>" + logReq.statusText + "</code>.<br />";
        bootbox.alert({
            title: "Uh oh...",
            message: errorStr
        });
        return;
    }
    let logs = await logReq.json();
    $("#loading-screen").hide();
    $("#app").show();
    logs.logs.forEach((it, i) => $("#log-list").append("<a class=\"nav-link\" data-toggle=\"pill\" role=\"tab\" aria-selected=\"false\" data-log=\"" + it + "\">" + it + "</a>"))
});