const logger = require("./logger");

function randomSleep() {
    const randomDuration = Math.floor(Math.random() * (5000 - 2000 + 1)) + 2000;
    logger.info(`Sleeping for ${randomDuration / 1000} seconds...`);

    return new Promise((resolve) => setTimeout(resolve, randomDuration));
}

module.exports = {
    randomSleep
}
