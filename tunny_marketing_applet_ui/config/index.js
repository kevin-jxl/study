
// see http://vuejs-templates.github.io/webpack for documentation.
var path = require('path')
// var baseUrl = 'http://192.168.11.43:4001';
// var baseUrl = 'http://10.133.85.71:4001';
// var baseUrl = 'http://10.138.22.15:4001';
var baseUrl = 'http://127.0.0.1:4001';
var apiUrl = 'http://10.153.71.207:10003';
module.exports = {
    build: {
        env: require('./prod.env'),
        index: path.resolve(__dirname, '../dist/index.html'),
        assetsRoot: path.resolve(__dirname, '../dist'),
        assetsSubDirectory: 'static',
        assetsPublicPath: '/',
        productionSourceMap: false,
        // Gzip off by default as many popular static hosts such as
        // Surge or Netlify already gzip all static assets for you.
        // Before setting to `true`, make sure to:
        // npm install --save-dev compression-webpack-plugin
        productionGzip: false,
        productionGzipExtensions: ['js', 'css'],
        // Run the build command with an extra argument to
        // View the bundle analyzer report after build finishes:
        // `npm run build --report`
        // Set to `true` or `false` to always turn it on or off
        bundleAnalyzerReport: process.env.npm_config_report
    },
    dev: {
        env: require('./dev.env'),
        port: 8000,
        autoOpenBrowser: true,
        assetsSubDirectory: 'static',
        host: 'localhost',
        assetsPublicPath: '/',
        proxyTable: {
            '/auth': {
                target: baseUrl,
                changeOrigin: true,
                pathRewrite: {
                    '^/auth': '/auth'
                }
            },
            '/admin': {
                target: baseUrl,
                changeOrigin: true,
                pathRewrite: {
                    '^/admin': '/admin'
                }
            },
          '/api': {
                target: baseUrl,
                changeOrigin: true,
                pathRewrite: {
                    '^/api': '/api'
                }
            },
            '/code': {
                target: baseUrl,
                changeOrigin: true,
                pathRewrite: {
                    '^/code': '/code'
                }
            },
            '/gen': {
                target: baseUrl,
                changeOrigin: true,
                pathRewrite: {
                    '^/gen': '/gen'
                }
            },
            '/daemon': {
                target: baseUrl,
                changeOrigin: true,
                pathRewrite: {
                    '^/daemon': '/daemon'
                }
            },
            '/marketingpc': {
                target: baseUrl,
                changeOrigin: true,
                pathRewrite: {
                    '^/marketingpc': '/marketingpc'
                }
            }

        },
        cssSourceMap: false
    }
}
