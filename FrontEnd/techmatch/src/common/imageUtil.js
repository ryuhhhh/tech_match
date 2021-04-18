import imageCompression from 'browser-image-compression';

export default {
    // アップロードされた画像ファイルを取得
    async getCompressImageFileAsync(file) {
        const options = {
            maxSizeMB: 1, // 最大ファイルサイズ
            maxWidthOrHeight: 2000 // 最大画像幅もしくは高さ
        }
        try {
            // 圧縮画像の生成
            return await imageCompression(file, options)
        } catch (error) {
            console.error('getCompressImageFileAsync is error', error)
            throw error;
        }
    },
    // プレビュー表示用のdataurlを取得
    async getDataUrlFromFile(file) {
        try {
            return await imageCompression.getDataUrlFromFile(file)
        } catch (error) {
            console.error('getDataUrlFromFile is error', error)
            throw error
        }
    },
    checkImage(image){
    // 画像があるか
        if(!image) return false
        let extention = this.getExtention(image.name)
        // 拡張子があるか
        if(extention.length<=0){
            return false
        }
        let allowedUserImageExtention = ['.png','.jpeg','.jpg']
        let allowedUserImageSize = 5000000
        // MIMEタイプ/拡張子/サイズがOKか
        if (image.type.startsWith('image/')
        &&(allowedUserImageExtention.indexOf(extention)>=0)
        &&(image.size<allowedUserImageSize)
        ){
            return true
        }
        return false
    },
    getExtention(imageName){
        let dotPosition = imageName.lastIndexOf('.')
        if (dotPosition === -1) return ''
        return imageName.slice(dotPosition)
    },
};