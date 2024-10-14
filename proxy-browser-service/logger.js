const { format, createLogger, transports } = require("winston");

const { combine, timestamp, label, printf } = format;
const CATEGORY = "LOG";

//Using the printf format.
const customFormat = printf(({ level, message, label, timestamp }) => {
    return `${timestamp} [${label}] ${level}: ${JSON.stringify(message)}`;
});

const logger = createLogger({
    level: "info",
    format: combine(label({ label: CATEGORY }), timestamp(), customFormat),
    transports: [new transports.Console()],
});

module.exports = logger;
