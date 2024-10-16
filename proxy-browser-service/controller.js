const logger = require("./logger");
const PORT = 3003;

const {sendRequestToUrl, getArchivedBidCarsByVin} = require("./newService")
const {app} = require("./app");
const {initMiddleware, asyncHandler} = require("./middleware");

initMiddleware(app);

app.get("/bidcars/archived/:vin", asyncHandler(async (req, res, next) => {
    const vin = req.params.vin;

    if (!vin) {
        return res.status(404).json({error: 'VIN is required'});
    }

    let car = await getArchivedBidCarsByVin(vin);
    logger.info(car);
    res.json(car);
}));

// Маршрут POST /proxy
app.post('/proxy', asyncHandler(async (req, res, next) => {
    const url = req.body.url;

    if (!url) {
        return res.status(404).json({error: 'URL is required in the request body'});
    }

    const responseData = await sendRequestToUrl(url);

    logger.info(responseData);
    res.json(responseData);
}));

app.listen(PORT, () =>
        logger.info(`Server running on http://localhost:${PORT}`)
);
