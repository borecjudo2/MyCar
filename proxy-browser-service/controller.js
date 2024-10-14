const logger = require("./logger");
const PORT = 3003;

const {getBidCars,getArchivedBidCarsByVin} = require("./client");
const {randomSleep} = require("./utils");
const {app}  = require("./app");
const {initMiddleware} = require("./middleware");

initMiddleware(app);

app.get("/bidcars/archived/:vin", async (req, res) => {
    try {
        const vin = req.params.vin;

        let car = await getArchivedBidCarsByVin(vin);
        logger.info(car)
        res.json(car);
    } catch (e) {
        logger.error(e);
        res.status(500).send('Something broke!');
    }
});

app.get("/bidcars/:minYear/:maxYear/:minOdometer/:maxOdometer/:mark/:model", async (req, res) => {
    try {

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

app.listen(PORT, () =>
    logger.info(`Server running on http://localhost:${PORT}`)
);
