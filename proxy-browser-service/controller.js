const logger = require("./logger");
const PORT = 3003;

const {sendRequestToUrl, getArchivedBidCarsByVin} = require("./newService")
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

app.post('/proxy', async (req, res) => {
    const url = req.body.url;

    if (!url) {
        return res.status(404).json({error: 'URL is required in the request body'});
    }

    const responseData = await sendRequestToUrl(url);

    logger.info(responseData)
    res.json(responseData);
});

app.listen(PORT, () =>
    logger.info(`Server running on http://localhost:${PORT}`)
);
