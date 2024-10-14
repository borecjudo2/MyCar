const puppeteer = require("puppeteer");
const cheerio = require("cheerio");

async function getAvByPriceStatistic(markId, modelId, generationId, year) {
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
    await page.goto(`https://api.av.by/offer-types/cars/price-statistics?brand=${markId}&generation=${generationId}&model=${modelId}&year=${year}`);

    // Take a screenshot
    const content = await page.content();
    const html = cheerio.load(content);

    // Extract the JSON content from the body
    const jsonContent = html('body').text();

    // Parse the JSON
    const jsonData = JSON.parse(jsonContent);

    // Close the browser
    await browser.close();

    return jsonData;
}

async function getAvByVin(lotId) {
    const browser = await puppeteer.launch({
        headless: 'new',
        // `headless: true` (default) enables old Headless;
        // `headless: 'new'` enables new Headless;
        // `headless: false` enables “headful” mode.
    });
    const page = await browser.newPage();

    // Set a user agent to mimic a real browser
    await page.setUserAgent('Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3');
    await page.setExtraHTTPHeaders({ "X-Api-Key": "sb5c4214bb7def4c06e82ac" })

    // Navigate to the website
    await page.goto(`https://api.av.by/offer-types/cars/offers/${lotId}/vin`);

    // Take a screenshot
    const content = await page.content();
    const html = cheerio.load(content);

    // Extract the JSON content from the body
    const jsonContent = html('body').text();

    // Parse the JSON/
    const jsonData = JSON.parse(jsonContent);

    // Close the browser
    await browser.close();

    return jsonData;
}

module.exports = {
    getAvByPriceStatistic,
    getAvByVin
}
