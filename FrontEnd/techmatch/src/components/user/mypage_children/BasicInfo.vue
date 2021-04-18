<template>
  <div class="wrapper">
    <!-- ユーザの画像を載せる -->
    <div class="user-image-wrapper">
      <img
        :src="computeUserImage"
        class="user-image"
      >
      <div
        v-show="editNow"
        id="user-image-upload-wrapper"
      >
        <label
          for="user-image-upload"
          class="image-label choice-image"
        >選択</label>
        <label
          class="image-label remove-image"
          @click="deleteUserImage"
        >削除</label>
        <input
          id="user-image-upload"
          ref="file"
          type="file"
          @change="setImage"
        >
        <div class="image-upload-error-msg">
          {{ imageUploadErrorMsg }}
        </div>
      </div>
    </div>
    <!-- 名前 -->
    <div class="user-id">
      {{ basicInfo.userId }}
    </div>
    <!-- ツイッター -->
    <div class="sns-account">
      <div class="sns-icon-wrapper">
        <img
          src="./../../../assets/twitter.svg"
          class="sns-icon twitter-icon"
        >
        <input
          v-model="basicInfo.twitterAccount"
          type="text"
          class="sns-account-input"
          :disabled="!editNow"
        >
      </div>
      <div class="sns-icon-wrapper">
        <img
          src="./../../../assets/facebook.svg"
          class="sns-icon facebook-icon"
        >
        <input
          v-model="basicInfo.facebookAccount"
          type="text"
          class="sns-account-input"
          :disabled="!editNow"
        >
      </div>
    </div>
    <!-- コメント -->
    <div class="field">
      <div class="control comment-wrapper">
        <textarea
          ref="comment"
          v-model="basicInfo.introduce"
          class="textarea has-fixed-size comment"
          placeholder="コメント"
          resize="none"
          :disabled="!editNow"
          @keydown="calcHeight"
          @change="calcHeight"
        />
      </div>
      <div
        v-if="commentError.length>0"
        class="comment-error"
      >
        {{ commentError }}
      </div>
    </div>
  </div>
</template>
<script>
import ImageUtil from '@/common/imageUtil'
export default {
    name: 'BasicInfo',
    mixins: [ImageUtil],
    props:{
        editNow:{
            type:Boolean,
            default:function(){
                return false
            }
        },
        basicInfo:{
            type:Object,
            default:function(){
                return {}
            }
        },
        finishEdit:{
            type:Boolean,
            default:function(){
                return false
            }
        },
    },
    data:function(){
        return {
            commentError:'',
            userImagePreview:'',
            userImageFile:'',
            userImageName:'',
            imageUploadErrorMsg:''
        }
    },
    computed:{
        computeUserImage:function(){
            if(this.userImagePreview!==''){
                return this.userImagePreview
            }else{
                if(this.basicInfo.userImage!==undefined && this.basicInfo.userImage!=='' && this.basicInfo.userImage!==null){
                    return '/images/users/'+this.basicInfo.userImage
                } else {
                    return './img/user/default_user_image.svg'
                }

            }
        }
    },
    watch:{
        basicInfo:function(v){
            this.basicInfo = v
        },
        finishEdit:function(newOne){
            if(newOne===true){
                let isOk = true
                // 文字数のバリデーション
                if(this.basicInfo.introduce !== null && this.basicInfo.introduce.length>400){
                    this.commentError = '400文字以内で入力してください'
                    isOk = false
                } else {
                    this.commentError = ''
                }
                // 画像のエラーがないか
                if(this.imageUploadErrorMsg.length>0){
                    isOk = false
                }
                // Twitterアカウントのバリデーション
                // facebookアカウントのバリデーション
                this.$emit('fromBasicInfo',{basicInfo:this.basicInfo,isOk:isOk,userImage:this.userImageFile})
            }
        }
    },
    methods:{
        async calcHeight(){
            const comment = this.$refs.comment
            new Promise
            ((resolve)=> {resolve(comment.style.height = 'auto')})
                .then(()=>{comment.style.height = comment.scrollHeight + 'px'})
        },
        setImage() {
            const files = this.$refs.file
            const image = files.files[0]
            // 拡張子/サイズ/MIMEタイプをチェック
            if (ImageUtil.checkImage(image)){
                //画像圧縮
                ImageUtil
                    .getCompressImageFileAsync(image)
                    .then(e=>{
                        this.userImageFile=e
                        return e})
                    .then(e=>this.createImage(e))
                // イメージの情報を取得
                this.userImageName = image.name
                this.imageUploadErrorMsg = ''
            }else{
                this.imageUploadErrorMsg = '5MB以下 .jpg .jpeg .png でお願いします'
            }
        },
        async createImage(file) {
            ImageUtil.getDataUrlFromFile(file).then(e=>this.userImagePreview=e)
        },
        deleteUserImage(){
            this.userImagePreview = ''
        }
    }
};
</script>
<style scoped>
.user-image-upload-wrapper{
    margin-top:20px;
}
.user-image-wrapper{
    text-align: center;
}
img{
    width: 50%;
}
.image-label{
    border: 3px solid #798072;
    padding: 5px;
    font-size: 1.3em;
    font-weight: bold;
    border-radius: 10px;
    cursor:pointer;
    transition:.3s ease-in-out;
}
.image-label:hover{
    color: #8aa553;
    border-color: #8aa553;
}
.choice-image{
    margin-right:30px;
}
#user-image-upload{
    display:none;
}
.image-upload-error-msg{
    margin-top: 10px;
    color: red;
}
.user-id{
    font-weight: bold;
    text-align: center;
    margin-top: 20px;
    margin-bottom: 20px;
    font-size: 1.6em;
    font-size: 1.8em;
    width: 80%;
    margin-left: 10%;
    color: #363636;
}
.sns-account{
    margin-bottom: 20px;
    text-align: center;
}
.sns-icon-wrapper{
    margin-bottom:10px;
    margin-right: 25%;
    color: #867a65;
}
.sns-account-input{
    position: absolute;
    margin-top: 1px;
    margin-left: 7px;
    font-size: 1.3em;
    border: none;
}
.sns-icon {
    height:30px;
    width:auto;
}
.comment{
    font-size:1.1em;
    max-width: 80%;
    min-width: 80%;
    margin-left:10%;
    overflow:hidden;
}
.comment:hover{
    cursor: default;
}
.comment-error{
    text-align: center;
    color: red;
    font-size: 1.3em;
    margin-top: 10px;
}
</style>