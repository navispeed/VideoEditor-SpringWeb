let tasks = new Map();

/**
 *
 * @param string {string}
 * @return {string}
 */
function jsUcfirst(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}

window.onload = (() => {
    let id = $("#info").data("id");
    const model = $(".model");

    model.find(".card-header-icon").click((e) => {
        let parents = $(e.currentTarget).parents(".clone");
        let icon = parents.find(".icon").find(".fas");
        parents.find(".card-content").toggle();
        icon.toggleClass("fa-angle-down");
        icon.toggleClass("fa-angle-left");
    });

    let currentInterval;
    let currentIntervalE;
    function setUpdateInterval(interval) {
        if (interval === currentInterval) {
            return;
        }
        clearInterval(currentIntervalE);
        currentInterval = interval;
        currentIntervalE = setInterval(() => {
            $.get('/api/' + id + "/tasks", (d) => {
                d.forEach(updateTask)
                if (d.filter(task => task.state !== "DONE") > 0) {
                    setUpdateInterval(300);
                } else {
                    setUpdateInterval(10000);
                }
            }, "json");
        }, interval);
    }


    /**
     *
     * @param data {{id: string, progress: number|null, type: string, state: string, output: {expired: boolean}}}
     */
    function updateTask(data) {
        let task;
        if (!tasks.has(data.id)) {
            task = model.clone(true, true);
            task.data("id", data.id);
            task.removeClass('model');
            task.addClass('clone');
        } else {
            task = $(`.clone[data-id=${data.id}]`);
        }
        let title = task.find(".card-header-title");
        if (JSON.stringify(tasks.get(data.id)) === JSON.stringify(data)) {
            return;
        }
        tasks.set(data.id, data);

        let progressBar = task.find(".progress");
        let streamButton = task.find(".stream-button");
        streamButton.attr("href",`/task/${data.id}/stream`)
        progressBar.text(data.progress);
        progressBar.val(data.progress);
        if (data.progress === 100 || data.state === "DONE") {
            progressBar.removeClass("is-info");
            progressBar.addClass("is-success");
        }
        title.text(jsUcfirst(data.type.toLowerCase()) + " task");
        task.removeAttr('hidden');
        task.appendTo(model.parent("ul"));
        if (data.output.expired) {
            let playButton = task.find(".stream-button");
            playButton.attr("href", "#");
            playButton.removeClass("is-info");
            playButton.addClass("is-grey");
            title.text(jsUcfirst(data.type.toLowerCase()) + " task (EXPIRED)");
        }
    }

    setUpdateInterval(300);
});
