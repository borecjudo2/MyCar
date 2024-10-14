const axios = require("axios");
const {headers} = require("./data");
const puppeteer = require("puppeteer");
const cheerio = require("cheerio");
const {randomSleep} = require("./utils");
const {getDetailsForArchivedBidcarsByHtml, getAllDetailsForArchivedBidcarsByHtml} = require("./bidcars_service");
const logger = require("./logger");

async function getCarsCopart(i, filter) {
    const response = await axios.post(
        "https://www.copart.com/public/lots/vehicle-finder-search-results",
        {
            query: ["*"],
            filter: filter,
            sort: [
                "salelight_priority asc",
                "member_damage_group_priority asc",
                "auction_date_type asc",
                "auction_date_utc desc",
            ],
            page: i,
            size: 100,
            start: 0,
            watchListOnly: false,
            freeFormSearch: false,
            hideImages: false,
            defaultSort: false,
            specificRowProvided: false,
            displayName: "",
            searchName: "",
            backUrl: "",
            includeTagByField: {
                VEHT: "{!tag=VEHT}",
            },
            rawParams: {},
        },
        {
            headers: headers,
        }
    );

    return response.data.data.results.content;
}

async function getBidCarsArchived(i, year, maxYear, minOdometer, maxOdometer, mark, model) {
    return (async () => {
        const browser = await puppeteer.launch({
            headless: 'new',
            // `headless: true` (default) enables old Headless;
            // `headless: 'new'` enables new Headless;
            // `headless: false` enables “headful” mode.
        });
        const page = await browser.newPage();

        // Set a user agent to mimic a real browser
        await page.setUserAgent('Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3');

        // Navigate to the website
        await page.goto(`https://bid.cars/app/search/archived/request?search-type=filters&type=Automobile&year-from=${year}&year-to=${maxYear}&make=${mark}&model=${model}&odometer-from=${minOdometer}&odometer-to=${maxOdometer}&auction-type=All&page=${i}`);

        // Take a screenshot
        const content = await page.content();
        const html = cheerio.load(content);

        // Extract the JSON content from the body
        const jsonContent = html('body').text();

        // Parse the JSON
        const jsonData = JSON.parse(jsonContent).data;

        // Close the browser
        await browser.close();

        return jsonData;
    })();
}

async function getBidCars(i, minYear, maxYear, minOdometer, maxOdometer, mark, model) {
    return (async () => {
        const browser = await puppeteer.launch({
            headless: 'new'
        });

        try {
            const page = await browser.newPage();

            // Set a user agent to mimic a real browser
            await page.setUserAgent('Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3');

            // Navigate to the website
            await page.goto(`https://bid.cars/app/search/request?search-type=filters&type=Automobile&year-from=${minYear}&year-to=${maxYear}&make=${mark}&model=${model}&odometer-from=${minOdometer}&odometer-to=${maxOdometer}&auction-type=All&start-code=All&drive-type=All&transmission=All&order-by=date&hide-finished=true&page=${i}`);

            // Take a screenshot
            const content = await page.content();
            const html = cheerio.load(content);

            // Extract the JSON content from the body
            const jsonContent = html('body').text();

            // Parse the JSON
            if (jsonContent.trim() === "") {
                // Close the browser
                await browser.close();
                return [];
            }

            return JSON.parse(jsonContent).data;
        } catch (e) {
            logger.error(e);
            return [];
        } finally {
            // Close the browser
            await browser.close();
        }
    })();
}

async function getArchivedBidCarsByVinAndStatus(vin, status) {
    return (async () => {
        const response = await getRequest(`https://bid.cars/app/search/en/vin-lot/${vin}/false`)
        await randomSleep();

        if (response && response.url) {
            if (response.url.includes("search/archived")) {
                return {
                    "error": "This vin has more than 1 lot"
                }
            }

            const map = {};

            for (let i = 0; i < 2; i++) {
                const $ = await getRequestForHtmlPage(response.url);
                map[i] = getDetailsForArchivedBidcarsByHtml($, status);

                const prevValue = map[i - 1];

                if (prevValue && prevValue.price !== map[i].price) {
                    // Added to have ability to call this api again to get correct price data
                    prevValue["price"] = "";
                    return {
                        "data": prevValue,
                        "listData": [map[0], map[1]]
                    }
                }

            }

            return {
                "data": map[0],
                "listData": [map[0], map[1]]
            }
        }

        return {
            "error": "Can not access url"
        }
    })();

}

async function getArchivedBidCarsByVin(vin) {
    return (async () => {
        const response = await getRequest(`https://bid.cars/app/search/en/vin-lot/${vin}/false`)
        await randomSleep();

        if (response && response.url) {
            if (response.url.includes("search/archived")) {
                return {
                    "error": "This vin has more than 1 lot"
                }
            }

            const $ = await getRequestForHtmlPage(response.url);
            const data = getAllDetailsForArchivedBidcarsByHtml($);

            return {
                "data": data
            }
        }

        return {
            "error": "Can not access url"
        }
    })();

}

async function getRequest(url) {
    return (async () => {
        const browser = await puppeteer.launch({
            headless: 'new',
            // `headless: true` (default) enables old Headless;
            // `headless: 'new'` enables new Headless;
            // `headless: false` enables “headful” mode.
        });
        const page = await browser.newPage();

        await page.setJavaScriptEnabled(true)
        await page.setUserAgent('Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3');

        // Navigate to the URL to execute JavaScript and set cookies
        await page.goto(url,
            {
                waitUntil: 'domcontentloaded',
                timeout: 0
            }
        );

        const content = await page.content();
        const html = cheerio.load(content);

        // Extract the JSON content from the body
        const jsonContent = html('body').text();

        // Parse the JSON
        const jsonData = JSON.parse(jsonContent);

        // Close the browser
        await browser.close();

        return jsonData;
    })();

}

async function getRequestForHtmlPage(url) {
    return (async () => {
        const browser = await puppeteer.launch({
            headless: 'new',
            // `headless: true` (default) enables old Headless;
            // `headless: 'new'` enables new Headless;
            // `headless: false` enables “headful” mode.
        });
        const page = await browser.newPage();

        await page.setJavaScriptEnabled(true)
        await page.setUserAgent('Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3');

        // Navigate to the URL to execute JavaScript and set cookies
        await page.goto(url,
            {
                waitUntil: 'domcontentloaded',
                timeout: 0
            }
        );

        const content = await page.content();
        const cheerioAPI = cheerio.load(content);

        // Close the browser
        await browser.close();

        return cheerioAPI;
    })();

}

module.exports = {
    getCarsCopart,
    getBidCars,
    getBidCarsArchived,
    getArchivedBidCarsByVin,
    getArchivedBidCarsByVinAndStatus
};
