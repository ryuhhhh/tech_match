<template>
  <div class="basic-infor-wrapper">
    <ValidationObserver ref="validationObserver">
      <div class="form-wrapper">
        <validation-provider
          v-slot="{ errors }"
          rules="required"
        >
          <p class="input-p">
            <label class="label">募集期限*</label>
            <input
              v-model="form.expireDate"
              class="input expire-date"
              type="date"
              @change="dateIsFutureCheck(new Date(form.expireDate),0)"
            >
          </p>
          <div
            v-for="(error, i) in errors"
            :key="`errors${i}`"
            class="error-list"
          >
            {{ error }}
          </div>
          <div class="error-list">
            {{ dateErrorMsg[0] }}
          </div>
        </validation-provider>
        <validation-provider
          v-slot="{ errors }"
          rules="required"
        >
          <p class="input-p">
            <label class="label description-label">いつまでにやりたいのか*</label>
            <input
              v-model="form.targetDate"
              class="input expire-date"
              type="date"
              @change="dateIsFutureCheck(new Date(form.targetDate),1)"
            >
          </p>
          <div
            v-for="(error, i) in errors"
            :key="`errors${i}`"
            class="error-list"
          >
            {{ error }}
          </div>
          <div class="error-list">
            {{ dateErrorMsg[1] }}
          </div>
        </validation-provider>
        <validation-provider
          v-slot="{ errors }"
          rules="required|max_value100:100"
        >
          <p class="input-p">
            <label class="label">募集人数*</label>
            <input
              v-model="form.requiredNum"
              class="input expire-date"
              type="text"
              placeholder="3"
            >
            <label class="require-num">人</label>
          </p>
          <div
            v-for="(error, i) in errors"
            :key="`errors${i}`"
            class="error-list"
          >
            {{ error }}
          </div>
        </validation-provider>
        <validation-provider
          v-slot="{ errors }"
          rules="required|max:50"
        >
          <p class="input-p">
            <label class="label">タイトル*</label>
            <input
              v-model="form.title"
              class="input"
              type="text"
              placeholder="エンジニアを個人間でつなげるサービスの開発"
            >
          </p>
          <div
            v-for="(error, i) in errors"
            :key="`errors${i}`"
            class="error-list"
          >
            {{ error }}
          </div>
        </validation-provider>
        <validation-provider
          v-slot="{ errors }"
          name="why"
          rules="required|max400:400"
        >
          <label class="label description-label">なぜやるのか*</label>
          <div class="field">
            <div class="control comment-wrapper">
              <textarea
                ref="comment"
                v-model="form.whyText"
                class="textarea has-fixed-size comment"
                :placeholder="whyPlaceHolder"
                resize="none"
                @keydown="calcHeight"
                @change="calcHeight"
              />
            </div>
          </div>
          <div
            v-for="(error, i) in errors"
            :key="`errors${i}`"
            class="error-list"
          >
            {{ error }}
          </div>
        </validation-provider>
        <validation-provider
          v-slot="{ errors }"
          name="what"
          rules="required|max400:400"
        >
          <label class="label description-label">なにをやるのか*</label>
          <div class="field">
            <div class="control comment-wrapper">
              <textarea
                ref="comment"
                v-model="form.whatText"
                class="textarea has-fixed-size comment"
                :placeholder="whatPlaceHolder"
                resize="none"
                @keydown="calcHeight"
                @change="calcHeight"
              />
            </div>
          </div>
          <div
            v-for="(error, i) in errors"
            :key="`errors${i}`"
            class="error-list"
          >
            {{ error }}
          </div>
        </validation-provider>
        <validation-provider
          v-slot="{ errors }"
          name="how"
          rules="required|max400:400"
        >
          <label class="label description-label">どうやってやるのか*</label>
          <div class="field">
            <div class="control comment-wrapper">
              <textarea
                ref="comment"
                v-model="form.howText"
                class="textarea has-fixed-size comment"
                :placeholder="howPlaceHolder"
                resize="none"
                @keydown="calcHeight"
                @change="calcHeight"
              />
            </div>
          </div>
          <div
            v-for="(error, i) in errors"
            :key="`errors${i}`"
            class="error-list"
          >
            {{ error }}
          </div>
        </validation-provider>

        <!-- 画像系 -->
        <div class="requirement-image-wrapper-wrapper">
          <div class="requirement-image-wrapper">
            <img
              :src="computeRequirementImage0"
              :class="requirementImage0Class"
            >
            <div id="requirement-image-upload-wrapper">
              <label
                for="requirement-image-upload0"
                class="image-label choice-image"
              >選択</label>
              <label
                class="image-label remove-image"
                @click="deleteRequirementImage(0)"
              >削除</label>
              <input
                id="requirement-image-upload0"
                ref="file0"
                type="file"
                @change="setImage(0)"
              >
              <div class="image-upload-error-msg">
                {{ imageInfo.imageUploadErrorMsg[0] }}
              </div>
            </div>
          </div>
          <div class="requirement-image-wrapper">
            <img
              :src="computeRequirementImage1"
              :class="requirementImage1Class"
            >
            <div id="requirement-image-upload-wrapper">
              <label
                for="requirement-image-upload1"
                class="image-label choice-image"
              >選択</label>
              <label
                class="image-label remove-image"
                @click="deleteRequirementImage(1)"
              >削除</label>
              <input
                id="requirement-image-upload1"
                ref="file1"
                type="file"
                @change="setImage(1)"
              >
              <div class="image-upload-error-msg">
                {{ imageInfo.imageUploadErrorMsg[1] }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </ValidationObserver>
  </div>
</template>
<script>
import ImageUtil from '@/common/imageUtil'
import { ValidationObserver,ValidationProvider, extend } from 'vee-validate'
import { required,max,max_value} from 'vee-validate/dist/rules'
extend('max400',{
    ...max,
    message: '400文字以内で入力してください'}
)
extend('required', {
    ...required,
    message: '必須です'
})
extend('max_value100', {
    ...max_value,
    message: '100人以下にしてください'
})

export default {
    name:'RequirementBasicInfo',
    components: {
        ValidationObserver,
        ValidationProvider
    },
    props:{
        ifCheck:{
            type:Boolean,
            default:false
        }
    },
    data:function(){
        return {
            // ファイル情報
            imageInfo:{
                imageFile:['',''],
                requirementImagePreview:['./img/icon/picture.svg','./img/icon/picture.svg'],
                imageUploadErrorMsg:['',''],
            },
            form:{},
            whyPlaceHolder:'昨今、エンジニアの力を手身近につけるサービスが増えてきました。\n'+
                           'そして、企業対個人をつなげるサービスや汎用的に個人対個人をつなげるサービスはあります。\n'+
                           'だが、エンジニア対エンジニアをつなげる目立った専門サービスはまだないため.....',
            whatPlaceHolder:'エンジニアが個人間で気軽につながるようなサービスを作ります\n'+
                           '題してTechMatchです。\n'+
                           '機能的には...',
            howPlaceHolder:'2カ月間かけて１人でSpringBoot+Vue.js+AWSを使い開発します\n'+
                           'プロジェクト管理は〇〇を使い...工数の予定としては...',
            dateErrorMsg:['','']
        }
    },
    computed:{
        computeRequirementImage0:function(){
            return this.imageInfo.requirementImagePreview[0]
        },
        computeRequirementImage1:function(){
            return this.imageInfo.requirementImagePreview[1]
        },
        requirementImage0Class:function(){
            if(this.imageInfo.imageFile[0]!==void 0 && this.imageInfo.imageFile[0]!==''){
                return 'requirement-image'
            }
            return 'no-requirement-image'
        },
        requirementImage1Class:function(){
            if(this.imageInfo.imageFile[1]!==void 0 && this.imageInfo.imageFile[1]!==''){
                return 'requirement-image'
            }
            return 'no-requirement-image'
        }
    },
    watch:{
        ifCheck:function(newVal){
            if(newVal){
                this.check()
            }
        }
    },
    methods:{
        async calcHeight(){
            const comment = this.$refs.comment
            new Promise
            ((resolve)=> {resolve(comment.style.height = 'auto')})
            // テキストエリアの高さをスクロールの高さにする
                .then(()=>{comment.style.height = comment.scrollHeight + 'px'})
        },
        setImage(num) {
            const image = num===0?this.$refs.file0.files[0]:this.$refs.file1.files[0]
            // 拡張子/サイズ/MIMEタイプをチェック
            if (ImageUtil.checkImage(image)){
                //画像圧縮
                ImageUtil
                    .getCompressImageFileAsync(image)
                    .then(e=>{
                        this.imageInfo.imageFile.splice(num,1,e)
                        return e})
                    .then(e=>this.createImage(e,num))
                // イメージの情報を取得
                this.imageInfo.imageUploadErrorMsg.splice(num,1,'')
            }else{
                this.imageInfo.imageUploadErrorMsg.splice(num,1,'5MB以下 .jpg .jpeg .png でお願いします')
            }
        },
        async createImage(file,num) {
            ImageUtil.getDataUrlFromFile(file).then(e=>this.imageInfo.requirementImagePreview.splice(num,1,e))
        },
        deleteRequirementImage(num){
            document.getElementById('requirement-image-upload'+num).value = ''
            this.$set(this.imageInfo.imageFile, num, '')
            this.$set(this.imageInfo.requirementImagePreview, num, './img/icon/picture.svg')
            this.$set(this.imageInfo.imageUploadErrorMsg, num, '')
        },
        check:function(){
            let isFutrureExpireDate = this.dateIsFutureCheck(this.form.expireDate,0)
            let isFutrureTargetDate = this.dateIsFutureCheck(this.form.targetDate,1)
            this.$refs.validationObserver.validate()
                .then(result=>{
                    if(result && isFutrureExpireDate && isFutrureTargetDate){
                        this.$emit('fromRequirementBasicInfo',{isOk:true,form:this.form,imageInfo:this.imageInfo})
                    }else{
                        this.$emit('fromRequirementBasicInfo',{isOk:false})
                    }
                })
        },
        dateIsFutureCheck(date,num){
            if(date!==null){
                let nowDate = new Date(Date.now())
                if(new Date(date)<nowDate){
                    this.dateErrorMsg[num] = '日付は未来を選択してください'
                    return false
                }else{
                    this.dateErrorMsg[num] = ''
                    return true
                }
            }
        }
    }
}
</script>
<style scoped>
/* イメージのCSS */
.requirement-image-wrapper{
    text-align: center;
}
.requirement-image{
    margin-top: 50px;
}

.no-requirement-image{
    margin-top: 50px;
    width: 100px;
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
#requirement-image-upload0,#requirement-image-upload1{
    display:none;
}
.image-upload-error-msg{
    margin-top: 10px;
    color: red;
}
</style>
<style scoped>
.textarea{
    min-height:9em !important;
}
.form-wrapper{
    background:#f3f2f2;
    /* padding: 50px; */
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
.error-list{
    margin-bottom:20px;
    color:red;
}
label{
    /* color: #2d2c2cc9; */
    font-size:1.5em;
    font-weight:normal;
}
.input-p{
    margin-bottom:10px;
}
</style>
