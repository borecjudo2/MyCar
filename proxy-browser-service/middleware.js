const logger = require("./logger");
const bodyParser = require("body-parser");

function initMiddleware(app) {
    // Health endpoint
    app.get('/health', (req, res) => {
        res.status(200).json({status: 'ok'});
    });

    const asyncHandler = (fn) => (req, res, next) => {
        Promise.resolve(fn(req, res, next)).catch(next);
      };

      // Global error handler middleware (same as before)
      app.use((err, req, res, next) => {
        console.error(err.stack);
        res.status(500).json({ message: err.message || 'Internal Server Error' });
      });

      app.use(   bodyParser.json());

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
}

module.exports = {
    initMiddleware
}
