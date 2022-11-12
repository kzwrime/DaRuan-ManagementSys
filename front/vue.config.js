const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
      transpileDependencies: true,
      devServer: {
        port:8080,//前端项目端口号
        proxy: {
          '/api': {
            // target: 'http://192.168.64.1:8080',//服务器url
            target: 'http://127.0.0.1:8888',//服务器url
            ws:true,
            pathRewrite:{
              '^/api':''
            },
            //后端不需要写跨域了
            changeOrigin: true,
          }
        }
      }
    }
)
