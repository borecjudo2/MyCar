const logger = require("./logger");
const PORT = 3000;
const express = require("express");
const {
    getCarsCopart,
    getBidCars,
    getBidCarsArchived,
    getArchivedBidCarsByVin,
    getArchivedBidCarsByVinAndStatus
} = require("./api");
const {mini, gti} = require("./data");
const {
    downloadImage,
    saveNewDataToDataFile,
    isVinExistInDataFile,
    getScreenshotByUrl,
    randomSleep
} = require("./utils");
const app = express();
const bodyParser = require("body-parser");
const fs = require("fs");
const path = require("path");
const {getAvByPriceStatistic, getAvByVin} = require("./avby-api");
const {data_bidcars} = require("./data_bidcars");
const {bidcars_details_test_data} = require("./test_data");

const isTestData = false;

app.use((req, res, next) => {
    logger.info(`Request ${req.method} ${req.url}`);
    next(); // Pass control to the next middleware or route handler
});

// Error handling middleware
app.use((err, req, res, next) => {
    logger.error(err.stack);
    res.status(500).send('Something broke!');
});

app.use(bodyParser.json());

// Middleware to handle OPTIONS requests
const handleOptions = (req, res, next) => {
    res.header('Access-Control-Allow-Origin', '*'); // Adjust the origin as needed
    res.header('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, OPTIONS');
    res.header('Access-Control-Allow-Headers', 'Content-Type, Authorization');

    // Respond to OPTIONS requests with a 200 status code
    if (req.method === 'OPTIONS') {
        res.status(200).send();
    } else {
        next();
    }

    // Store reference to original send method
    const originalSend = res.send;

    // Override send method
    res.send = function (body) {
        // Log the status code
        logger.info(`Response[${res.statusCode}] ${req.method} ${req.url}`);
        // Call original send method
        originalSend.call(this, body);
    };
};

// Use the middleware for all routes
app.use(handleOptions);

// Health endpoint
app.get('/', (req, res) => {
    res.status(200).json({status: 'ok'});
});

// Health endpoint
app.get('/health', (req, res) => {
    res.status(200).json({status: 'ok'});
});

app.get("/cars/:carMark", async (req, res) => {
    const carMark = req.params.carMark;

    if (carMark === "test") {
        res.json(gti.concat(mini));
        return;
    }

    const filter = {
        "VEHT": ["vehicle_type_code:VEHTYPE_V"],
        "ODM": ["odometer_reading_received:[0 TO 36000]"],
        "YEAR": ["lot_year:[2019 TO 2021]"],
        "MAKE": [`#MakeDesc:${carMark}`]
    };

    let responseData = [];

    for (let i = 0; i < 101; i++) {
        const data = await getCarsCopart(i, filter);
        logger.info(i);
        logger.info(data.length);

        responseData = responseData.concat(data);

        logger.info(responseData.length);

        if (data.length === 0) {
            break;
        }
        await randomSleep();
    }

    logger.info("responseData = " + responseData.length);
    res.json(responseData);
});

app.get("/cars/:carMark/:carModel", async (req, res) => {
    const carMark = req.params.carMark;
    const carModel = req.params.carModel;

    if (carMark === "test") {
        res.json(gti.concat(mini));
        return;
    }

    const filter = {
        "VEHT": ["vehicle_type_code:VEHTYPE_V"],
        "ODM": ["odometer_reading_received:[0 TO 36000]"],
        "YEAR": ["lot_year:[2019 TO 2021]"],
        "MAKE": [`#MakeDesc:${carMark}`],
        "MODG": [`lot_model_group:"${carModel}"`]
    };

    let responseData = [];

    for (let i = 0; i < 101; i++) {
        const data = await getCarsCopart(i, filter);
        logger.info(i);
        logger.info(data.length);

        responseData = responseData.concat(data);

        logger.info(responseData.length);

        if (data.length === 0) {
            break;
        }
        await randomSleep();
    }

    logger.info("responseData = " + responseData.length);
    res.json(responseData);
});

app.get("/bidcars/archived/:vin/:status", async (req, res) => {
    try {
        if (isTestData) {
            res.json(bidcars_details_test_data);
            return;
        }

        const vin = req.params.vin;
        const status = req.params.status;

        let car = await getArchivedBidCarsByVinAndStatus(vin, status);

        logger.info(car)
        res.json(car);
    } catch (e) {
        logger.error(e);
        res.status(500).send('Something broke!');
    }
});

app.get("/bidcars/archived/:vin", async (req, res) => {
    try {
        if (isTestData) {
            res.json(bidcars_details_test_data);
            return;
        }

        const vin = req.params.vin;

        let car = await getArchivedBidCarsByVin(vin);
        logger.info(car)
        res.json(car);
    } catch (e) {
        logger.error(e);
        res.status(500).send('Something broke!');
    }
});

app.get("/bidcars/archived/:year/:minOdometer/:maxOdometer/:mark/:model", async (req, res) => {
    try {
        if (isTestData) {
            res.json(data_bidcars);
            return;
        }

        const year = req.params.year;
        const minOdometer = req.params.minOdometer;
        const maxOdometer = req.params.maxOdometer;
        const mark = req.params.mark;
        let model = req.params.model;

        let responseData = [];

        for (let i = 1; i < 16; i++) {
            const data = await getBidCarsArchived(i, year, year, minOdometer, maxOdometer, mark, model);
            logger.info(i);
            logger.info(data.length);

            responseData = responseData.concat(data);

            logger.info(responseData.length);

            if (data.length === 0) {
                break;
            }
            await randomSleep();
        }

        logger.info("responseData = " + responseData.length);
        logger.info(responseData)
        res.json(responseData);
    } catch (e) {
        logger.error(e);
        res.status(500).send('Something broke!');
    }
});

app.get("/bidcars/archived/:year/:maxYear/:minOdometer/:maxOdometer/:mark/:model", async (req, res) => {
    try {
        if (isTestData) {
            res.json(data_bidcars);
            return;
        }

        const year = req.params.year;
        const maxYear = req.params.maxYear;
        const minOdometer = req.params.minOdometer;
        const maxOdometer = req.params.maxOdometer;
        const mark = req.params.mark;
        let model = req.params.model;

        let responseData = [];

        for (let i = 1; i < 11; i++) {
            const data = await getBidCarsArchived(i, year, maxYear, minOdometer, maxOdometer, mark, model);
            const filteredData = data.filter(item => !isVinExistInDataFile(item.vin));

            logger.info(i);
            logger.info(filteredData.length);

            responseData = responseData.concat(filteredData);

            if (data.length === 0) {
                break;
            } else {
                await randomSleep();
            }
        }

        logger.info("responseData = " + responseData.length);
        logger.info(responseData)
        res.json(responseData);
    } catch (e) {
        logger.error(e);
        res.status(500).send('Something broke!');
    }
});

app.get("/bidcars/:minYear/:maxYear/:minOdometer/:maxOdometer/:mark/:model", async (req, res) => {
    try {
        if (isTestData) {
            res.json(data_bidcars);
            return;
        }

        const minYear = req.params.minYear;
        const maxYear = req.params.maxYear;
        const minOdometer = req.params.minOdometer;
        const maxOdometer = req.params.maxOdometer;
        const mark = req.params.mark;
        const model = req.params.model;

        let responseData = [];

        for (let i = 1; i < 16; i++) {
            const data = await getBidCars(i, minYear, maxYear, minOdometer, maxOdometer, mark, model);
            logger.info(i);
            logger.info(data.length);

            responseData = responseData.concat(data);

            logger.info(responseData.length);

            if (data.length === 0) {
                break;
            }
            await randomSleep();
        }

        logger.info("responseData = " + responseData.length);
        logger.info(responseData)
        res.json(responseData);
    } catch (e) {
        logger.error(e);
        res.status(500).send('Something broke!');
    }
});

app.get("/bidcars/:minYear/:maxYear/:minOdometer/:maxOdometer/:mark/:model/:page", async (req, res) => {
    try {
        if (isTestData) {
            res.json(data_bidcars);
            return;
        }

        const minYear = req.params.minYear;
        const maxYear = req.params.maxYear;
        const minOdometer = req.params.minOdometer;
        const maxOdometer = req.params.maxOdometer;
        const mark = req.params.mark;
        const model = req.params.model;
        let page = Number(req.params.page);

        let data = await getBidCars(page, minYear, maxYear, minOdometer, maxOdometer, mark, model);
        let filteredData = data.filter(item => !isVinExistInDataFile(item.vin));

        logger.info(page);
        logger.info("data = " + data.length);
        logger.info("filteredData = " + filteredData.length);

        while (filteredData.length === 0) {
            await randomSleep();

            page = page + 1;
            console.log("page " + page);
            data = await getBidCars(page, minYear, maxYear, minOdometer, maxOdometer, mark, model);
            filteredData = data.filter(item => !isVinExistInDataFile(item.vin));

            logger.info(page);
            logger.info("data = " + data.length);
            logger.info("filteredData = " + filteredData.length);
        }

        logger.info(page);
        logger.info("responseData = " + filteredData.length);

        const newData = {
            page: page,
            data: filteredData
        }
        res.json(newData);
    } catch (e) {
        logger.error(e);
        res.status(500).send('Something broke!');
    }
});

app.post("/ai/generator/save", async (req, res) => {
    try {
        const baseFolderPathDamage = "C:/work/copart-parse-telegram-bot/data/ai/cars/generated/damage/";
        const baseFolderPathPart = "C:/work/copart-parse-telegram-bot/data/ai/cars/generated/part/";

        const url = req.body.url;
        const vin = req.body.vin;
        const img = req.body.img;
        const damageLevel = req.body.damageLevel;
        const part = req.body.part;

        saveNewDataToDataFile(vin);

        if (url !== "none") {
            await downloadImage(url, baseFolderPathDamage + part + "_" + damageLevel, img + "_" + vin + '.jpg');
        }

        res.json()
    } catch (e) {
        logger.error('Something broke!');
        logger.error(e);

        res.json();
    }
});

app.get('/ai/generator/count', (req, res) => {
    try {
        const directoryPath = "C:/work/copart-parse-telegram-bot/data/ai/cars/generated/damage/";

        function getFolderStats() {
            try {
                let foldersData = [];
                // Чтение содержимого директории
                const files = fs.readdirSync(directoryPath);

                // Фильтрация только папок
                const folders = files.filter(file => fs.statSync(path.join(directoryPath, file)).isDirectory());

                // Асинхронный цикл для подсчета файлов в каждой папке
                for (const folder of folders) {
                    const folderPath = path.join(directoryPath, folder);
                    const folderFiles = fs.readdirSync(folderPath);

                    const data = {
                        folder: folder,
                        count: folderFiles.length
                    };
                    foldersData = foldersData.concat(data);
                }

                return foldersData;
            } catch (err) {
                logger.error('Ошибка при чтении директории:', err);
                return [];
            }
        }

        const data = getFolderStats();
        res.json(data);
    } catch (e) {
        logger.error(e);
        res.status(500).send('Something broke!');
    }
});

app.get('/avby/statistic/:markId/:modelId/:generationId/:year', async (req, res) => {
    try {
        const markId = req.params.markId;
        const modelId = req.params.modelId;
        const generationId = req.params.generationId;
        const year = req.params.year;

        const data = await getAvByPriceStatistic(markId, modelId, generationId, year);

        res.json(data);
    } catch (e) {
        logger.error(e);
        res.status(500).send('Something broke!');
    }
});

app.get('/avby/vin/:lotId', async (req, res) => {
    try {
        const lotId = req.params.lotId;

        const data = await getAvByVin(lotId);

        res.json(data);
    } catch (e) {
        logger.error(e);
        res.status(500).send('Something broke!');
    }
});

app.get('/sleep', async (req, res) => {
    try {
        await sleep(100);
        res.json({});
    } catch (e) {
        logger.error(e);
        res.status(500).send('Something broke!');
    }
});

function sleep(value) {
    return new Promise((resolve) => setTimeout(resolve, value * 1000));
}

app.post('/photo', async (req, res) => {
    try {
        const photoUrl = req.body.photoUrl;

        if (!photoUrl) {
            return res.status(400).json({error: 'URL is required in the request body'});
        }

        try {
            const screenshot = await getScreenshotByUrl(photoUrl);

            res.writeHead(200, {
                'Content-Type': 'image/png',
                'Content-Length': screenshot.length
            });

            res.end(screenshot);

        } catch (error) {
            logger.error('Error:', error);
            res.status(500).json({error: 'Internal Server Error'});
        }
    } catch (e) {
        logger.error(e);
        res.status(500).send('Something broke!');
    }
});

app.listen(PORT, () =>
    logger.info(`Server running on http://localhost:${PORT}`)
);
