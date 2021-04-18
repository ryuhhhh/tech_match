<template>
  <div class="basic-info-wrapper">
    <div class="form-wrapper">
      <label class="label">タイトル</label>
      <h1 class="title">
        {{ basicInfo.title }}
      </h1>

      <label class="label description-label">募集者</label>
      <div class="content link">
        <router-link :to="{ name: 'UserPage', query: { userId: basicInfo.userId }}">
          {{ basicInfo.userId }}
        </router-link>
      </div>

      <label class="label description-label">募集期限</label>
      <div class="content">
        {{ basicInfo.expireDate }}
      </div>

      <label class="label description-label">いつまでにやりたいのか</label>
      <div class="content">
        {{ basicInfo.targetDate }}
      </div>

      <label class="label description-label">募集人数</label>
      <div class="content">
        {{ basicInfo.requirementNum }}人
      </div>

      <label class="label description-label">なぜやるのか</label>
      <div class="content">
        {{ basicInfo.whyText }}
      </div>

      <label class="label description-label">なにをやるのか</label>
      <div class="content">
        {{ basicInfo.whatText }}
      </div>

      <label class="label description-label">どうやってやるのか</label>
      <div class="content">
        {{ basicInfo.howText }}
      </div>

      <!-- 画像系 -->
      <div
        class="requirement-image-wrapper-wrapper"
        :class="requirementClass"
      >
        <div class="requirement-image-wrapper">
          <img
            :src="computeRequirementImage0"
            class="requirement-image image0"
          >
        </div>
        <div class="requirement-image-wrapper">
          <img
            :src="computeRequirementImage1"
            class="requirement-image image1"
          >
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
    name:'RequirementBasicInfo',
    props:{
        basicInfo:{
            type:Object,
            default:()=>{
                return {}
            }
        }
    },
    computed:{
        computeRequirementImage0:function(){
            if(this.basicInfo.image_1!==void 0 && this.basicInfo.image_1 !== '' && this.basicInfo.image_1!==null){
                return '/images/requirements/'+this.basicInfo.image_1
            }
            return ''
        },
        computeRequirementImage1:function(){
            if(this.basicInfo.image_2!==void 0 && this.basicInfo.image_2 !== '' && this.basicInfo.image_2!==null ){
                return '/images/requirements/'+this.basicInfo.image_2
            }
            return ''
        },
        requirementClass:function(){
            if((this.basicInfo.image_1===null || this.basicInfo.image_1 === '') && (this.basicInfo.image_2===null || this.basicInfo.image_2 === '')){
                return 'no-image'
            }
            return ''
        }
    }
}
</script>
<style scoped>
/* イメージのCSS */
.requirement-image-wrapper{
    text-align: center;
}
.no-image{
  display:none;
}
.requirement-image{
  margin-top:50px;
  max-width:60%;
}
.requirement-image.image1{
  margin-top:50px;
  margin-bottom:50px;
}
</style>
<style scoped>
a{
  text-decoration: underline;
}
.textarea{
    min-height:9em !important;
}
.form-wrapper{
    background:#f3f2f2;
    border-radius: 5px;
    text-align:left;
    margin-bottom: 20px;
    padding: 10px;
}
.require-num{
    margin-left: 15px;
}
.description-label{
    margin-top:20px;
}
.expire-date{
    width:30%;
}
label{
    /* color: #2d2c2cc9; */
    font-size:1.3em;
    font-weight:normal;
}
.label{
    color:#757763;
    font-size:1.1em;
}
.content{
    color: #363636;
    font-size: 1.3em;
    background: #fdfdfb;
    padding: 10px;
    border-radius: 10px;
    word-wrap: break-word;
}
</style>
