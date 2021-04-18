const path = require('path')
const VueLoaderPlugin = require('vue-loader/lib/plugin')
const MiniCssExtractPlugin = require('mini-css-extract-plugin')
module.exports = {
    mode: 'development',
    cache: true,
    // エントリポイントのファイル
    entry: './src/main.js',
    output: {
    // 出力先のディレクトリ
        path: path.resolve(__dirname, 'C:\\Users\\firmy\\techmatch_work\\BackEnd\\base\\src\\main\\resources\\static\\js'),
        filename: 'bundle.js'
    },
    devtool: 'inline-source-map',
    module: {
        rules: [
            {
                test: /\.vue$/,
                loader: 'vue-loader'
            },
            {
                test: /\.js$/,
                use: [
                    {
                        loader: 'babel-loader',
                        options: {
                            'presets': [
                                [
                                    '@babel/preset-env', {
                                        'targets': {
                                            'node': 'current'
                                        }
                                    }
                                ]
                            ]
                        }
                    }
                ]
            },
            {
                test: /\.css$/,
                use: ['vue-style-loader', 'css-loader']
            },
            {
                test: /\.(sass|scss)$/,
                use: [
                    MiniCssExtractPlugin.loader,
                    {
                        loader: 'css-loader'
                    },
                    {
                        loader: 'sass-loader',
                        options: {
                            sourceMap: true,
                        }
                    }
                ]
            },
            {
                test: /\.(png|jpe?g|gif {2}|woff|woff2|ttf|eot|ico)$/i,
                use: [
                    {
                        loader: 'file-loader',
                    },
                ],
            },
            {
                test: /\.svg$/,
                // use: ['babel-loader', 'vue-svg-loader'],
                use: {
                    loader: 'svg-url-loader',
                    options: {}
                }
            },
        ]
    },
    resolve: {
        extensions: ['.js', '.vue', '.json'],
        alias: {
            'vue$': 'vue/dist/vue.esm.js',
            '@': path.resolve('src'),
        }
    },
    plugins: [
        new VueLoaderPlugin(),
        new MiniCssExtractPlugin({
            filename: 'css/mystyles.css'
        }),
    ],
    performance: {
        maxEntrypointSize: 100000,
        maxAssetSize: 100000
    }
}