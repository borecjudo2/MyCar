const {sendRequest, getHtmlData} = require("./newClient")

async function sendRequestToUrl(url) {
    return (async () => {
        return await sendRequest(url);
    })();
}

async function getArchivedBidCarsByVin(vin) {
    return (async () => {
        const response = await sendRequestToUrl(`https://bid.cars/app/search/en/vin-lot/${vin}/false`)

        if (!response || !response.url) {
            throw new Error("Can not access url");
        }

        if (response.url.includes("search/archived")) {
            throw new Error("This vin has more than 1 lot");
        }

        const html = await getHtmlData(response.url);
        const data = getAllDetailsForArchivedBidcarsByHtml(html);

        return {
            "data": data
        }
    })();
}

function getAllDetailsForArchivedBidcarsByHtml($) {
    const result = getDetails($);
    const fullPrice = getFullPrice($);
    result["price"] = fullPrice["Sold"];
    result["priceNotSold"] = fullPrice["Not sold"];
    result["priceNoInfo"] = fullPrice["No information"];
    return result;
}

function getFullPrice($) {
    const salesHistoryItems = $('tr[style="cursor: pointer"]');
    let price = {};

    salesHistoryItems.each(function () {
        const salesHistoryItem = $(this);
        const statusSoldSpan = salesHistoryItem.find(`span.status`);

        if (statusSoldSpan.length > 0) {
            const priceSpan = salesHistoryItem.find('span.status.price');

            const filteredSpans = statusSoldSpan.filter(function () {
                return !$(this).hasClass('price');
            });
            const trim = filteredSpans.text().trim();
            price[trim] = priceSpan.text().trim(); // Assuming the price is text inside the span
        }
    });

    return price;
}

function getDetails($) {
    const result = {};

    $('.options-list .option').each((index, element) => {
        const optionText = $(element).contents().first().text().trim().toLocaleLowerCase().replaceAll(" ", "_");

        if (optionText === "airbag_checked") {
            const airbagInfo = {};
            $(element).find('.right-info span').each((index, spanElement) => {
                const spanText = $(spanElement).text().trim().toLocaleLowerCase().replace(" ", "_");
                const faIcon = $(spanElement).find('i.fa');
                const faIconClass = faIcon.attr('class');

                airbagInfo[spanText] = faIconClass.trim()
                                .replace("fa", "")
                                .replace("fa-", "")
                                .replace(" ", "")
                        || '';
            });

            result[optionText] = airbagInfo;
        }

        if (optionText
                && optionText
                !== "photos"
                && optionText
                !== "vin"
                && optionText
                !== "airbag_checked"
                && optionText
                !== "odometer") {
            const value = $(element).find('.right-info').text().trim();
            if (value) {
                result[optionText] = value;
            }
        }
    });

    return result;
}

module.exports = {
    getArchivedBidCarsByVin,
    sendRequestToUrl
};
