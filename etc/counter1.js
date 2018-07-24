var http = require('http');
var userCount = 0;
var server = http.createServer(function (req, res) {
    userCount++;
    console.log(userCount);
    res.writeHead(200, { 'Content-Type': 'text/plain' });
    res.write('1: ' + userCount + '\n');
    res.end();
});
server.listen(9080);
console.log('server running...');
