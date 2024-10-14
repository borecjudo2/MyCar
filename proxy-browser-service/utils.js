const axios = require('axios');
const fs = require('fs');
const path = require('path');
const puppeteer = require("puppeteer");
const logger = require("./logger");


const filePath = "C:/work/copart-parse-telegram-bot/data/ai/cars/generated/data.json";

const downloadImage = async (url, folderPath, fileName) => {
    try {
        const screenshotBuffer = await getScreenshotByUrl(url);
        const imagePath = path.join(folderPath, fileName);

        await saveBufferAsPng(screenshotBuffer, imagePath);
    } catch (error) {
        console.error('Error fetching photo:', error.message);
        throw error;
    }
};

const getScreenshotByUrl = async (url) => {
    try {
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
        await page.goto(url, {timeout: 0,waitUntil: 'domcontentloaded'});

        // Capture a screenshot
        let width = 640;
        let height = 480;

        await page.setViewport({width, height});

        const screenshotBuffer = await page.screenshot();

        // Close the browser
        await browser.close();

        return screenshotBuffer;
    } catch (error) {
        console.error('Error fetching photo:', error.message);
        throw error;
    }
};

async function saveBufferAsPng(buffer, outputPath) {
    try {
        // Write the buffer to a file
        fs.writeFileSync(outputPath, buffer);
        logger.info(`Buffer saved as PNG file at: ${outputPath}`);
    } catch (error) {
        console.error('Error saving buffer as PNG:', error.message);
        throw error;
    }
}

function saveNewDataToDataFile(vin) {
    if (!isVinExistInDataFile(vin)) {
        const data = {
            vin: vin
        };

        addVinsToDataFile(data)
    }
}

function isVinExistInDataFile(vin) {
    const jsonData = readFileFromDataFile();
    return jsonData.data.some(item => item.vin === vin);
}

function addVinsToDataFile(vin) {
    const jsonData = readFileFromDataFile();
    jsonData.data.push(vin)
    writeDataToFile(jsonData);
}

function readFileFromDataFile() {
    const data = fs.readFileSync(filePath);
    return JSON.parse(data);
}

function writeDataToFile(jsonData) {
    // Convert the JSON object back to a string
    const updatedData = JSON.stringify(jsonData, null, 2); // the last argument is for indentation

    // Write the updated data back to the file
    fs.writeFile(filePath, updatedData, 'utf8', (err) => {
        if (err) {
            console.error('Error writing file:', err);
            return;
        }
        logger.info('File updated successfully!');
    });
}

function randomSleep() {
    const randomDuration = Math.floor(Math.random() * (5000 - 2000 + 1)) + 2000;
    logger.info(`Sleeping for ${randomDuration / 1000} seconds...`);

    return new Promise((resolve) => setTimeout(resolve, randomDuration));
}

module.exports = {
    downloadImage,
    getScreenshotByUrl,
    saveNewDataToDataFile,
    isVinExistInDataFile,
    randomSleep
}
