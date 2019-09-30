$(document).ready(async () => {
    let logReq = await fetch("/api/getAllLogs");
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
    console.log(logs)
})