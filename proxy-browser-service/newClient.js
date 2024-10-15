const puppeteer = require("puppeteer");
const cheerio = require("cheerio");

const userAgent = 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3';

async function getHtmlData(url) {
    return (async () => {
        const browser = await puppeteer.launch({
            headless: 'new'
        });

        try {
            const page = await browser.newPage();

            await page.setJavaScriptEnabled(true)
            await page.setUserAgent(userAgent);

            // Navigate to the URL to execute JavaScript and set cookies
            await page.goto(url,
                    {
                        waitUntil: 'domcontentloaded',
                        timeout: 0
                    }
            );

            const content = await page.content();
            if (!content) {
                throw new Error("Page content is null!")
            }

            const html = cheerio.load(content);
            if (!html) {
                throw new Error("HTML data content is null!")
            }

            return html;
        } finally {
            await browser.close();
        }
    })();

}

async function sendRequest(url) {
    return (async () => {
        const html = await getHtmlData(url);
        if (!html) {
            throw new Error("HTML data content is null!")
        }

        // Extract the JSON content from the body
        const htmlBody = html('body');
        if (!htmlBody) {
            throw new Error("HTML body content is null!")
        }

        const htmlBodyText = htmlBody.text();
        if (!htmlBodyText) {
            throw new Error("HTML body text content is null!")
        }

        const json = JSON.parse(htmlBodyText);
        if (!json) {
            throw new Error("JSON content is null!")
        }

        return json;
    })();
}

module.exports = {
    sendRequest,
    getHtmlData
};
