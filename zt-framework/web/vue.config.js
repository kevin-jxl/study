// vue.config.js
const path = require('path')

module.exports = {
  publicPath: './',
  assetsDir: "assets",
  runtimeCompiler: true,

  devServer: {
    disableHostCheck: true,
    overlay: {
      warnings: process.env.NODE_ENV !== 'production',
      errors: process.env.NODE_ENV !== 'production'
    }
  },

  css: {
    loaderOptions: {
      sass: {
        prependData: `@import "@/assets/style/global.scss";`
      }
    }
  },

  pluginOptions: {
    'style-resources-loader': {
      preProcessor: 'scss',
      patterns: []
    }
  }
}
