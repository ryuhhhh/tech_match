<template>
  <div class="wrapper">
    <!-- ユーザの画像を載せる -->
    <div class="user-image-wrapper">
      <img
        :src="computeUserImage"
        class="user-image"
      >
    </div>
    <!-- 名前 -->
    <div class="user-id">
      {{ basicInfo.userId }}
    </div>
    <!-- つ言った -->
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
        basicInfo:{
            type:Object,
            default:function(){
                return {}
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
                //   return './../../../assets/user_image.svg'
                return '/public/user_image.svg'
            }
        }
    },
    watch:{
    },
    methods:{
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
img{
    height: 140px;
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