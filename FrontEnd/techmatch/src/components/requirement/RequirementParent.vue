<template>
  <div class="requirement-wrapper">
    <div class="requirement-declaration">
      募集基本情報
    </div>
    <requirement-basic-info
      :if-check="ifCheck"
      @fromRequirementBasicInfo="fromRequirementBasicInfo"
    />
    <div class="requirement-declaration skill">
      募集スキル情報
    </div>
    <requirement-skills
      :if-check="ifCheck"
      @fromUserSkills="fromUserSkills"
    />
    <div
      v-show="!ifFinalCheck"
      class="buttons-wrapper"
    >
      <button
        class="button cancel is-large"
        @click="cancel"
      >
        戻る
      </button>
      <button
        class="button is-success is-large"
        @click="check"
      >
        確認
      </button>
    </div>
    <confirm-modal-header
      v-show="ifFinalCheck"
      @fromConfirmModalHeader="fromConfirmModalHeader"
    />
  </div>
</template>

<script>
import RequirementBasicInfo from './RequirementChildren/RequirementBasicInfo'
import RequirementSkills from './RequirementChildren/RequirementSkills'
import ConfirmModalHeader from './RequirementChildren/ConfirmModalHeader'
import * as action_type from '@/store/action-type'
import util from '@/common/util'
export default {
    name: 'RequirementList',
    components: {
        RequirementBasicInfo,
        RequirementSkills,
        ConfirmModalHeader
    },
    mixins:[util],
    data: () => {
        return {
            requirementStatus:'RS001',
            ifCheck:false,
            childrenOkCount:0,
            fromBasicChildIsOk:false,
            ifFinalCheck:false,
            // 送信用作成用
            imageInfo:[],
            requirementModel:{},
            frontEndSkills:[],
            backEndSkills:[],
            infraSkills:[],
            mlSkills:[],
            nativeApplicationSkills:[],
            // 送信用
            requirementAndSKills:{}
        }
    },
    watch:{
        childrenOkCount:function(newVal){
            // スクロール
            let timer = setInterval(function(){
                let yOffset = window.pageYOffset
                window.scrollTo(0,yOffset-50)
                if(yOffset-50<0){
                    clearInterval(timer)
                }
            },10)
            if(newVal>=2){
                // バリデーションを行った基本情報フォームがOKだったら送信
                if(this.fromBasicChildIsOk){
                    this.ifFinalCheck=true
                }
                this.childrenOkCount = 0
                this.ifCheck = false
            }
        }
    },
    methods:{
        cancel:function(){
            this.$router.go(-1)
        },
        check:function(){
            this.ifCheck = true
        },
        fromRequirementBasicInfo:function(result){
            if(result.isOk){
                this.requirementModel = result.form
                this.imageInfo = result.imageInfo.imageFile
                this.fromBasicChildIsOk = true
            }
            this.childrenOkCount++

        },
        fromUserSkills:function(result){
            this.frontEndSkills = result.frontEndSkills
            this.backEndSkills = result.backEndSkills
            this.infrakills = result.infraSkills
            this.mlSkills = result.mlSkills
            this.nativeApplicationSkills = result.nativeApplicationSkills
            this.childrenOkCount++
        },
        fromConfirmModalHeader:async function(bool){
            this.ifFinalCheck=false
            if(bool){
                // this.requirementAndSKills に各フォームの値を入れていく
                this.requirementAndSKills.requirementModel = this.requirementModel
                this.requirementAndSKills.frontEndSkills = this.convertObjectsToArray(this.frontEndSkills)
                this.requirementAndSKills.backEndSkills = this.convertObjectsToArray(this.backEndSkills)
                this.requirementAndSKills.infraSkills = this.convertObjectsToArray(this.infraSkills)
                this.requirementAndSKills.mlSkills = this.convertObjectsToArray(this.mlSkills)
                this.requirementAndSKills.nativeApplicationSkills = this.convertObjectsToArray(this.nativeApplicationSkills)
                await this.$store.
                    dispatch(action_type.REGISTER_REQUIREMENTS,{requirementAndSKills:this.requirementAndSKills,imageInfo:this.imageInfo})
                    .then(result=>{this.$router.push({name:'RequirementSucceeded',params:{requirementModel:result.data.requirementModel}})})
                    .catch(err=>this.makePopup(err.response,3000))
            }
        },

    }
};
</script>
<style scoped>
.requirement-wrapper{
    margin-left: 20%;
    margin-right: 20%;
}
.requirement-declaration{
    text-align: center;
    font-size: 2em;
    margin-top: 20px;
    margin-bottom: 20px;
}
.buttons-wrapper{
    margin-left: 10%;
    width: 80%;
    margin-top: 50px;
    margin-bottom: 50px;
    text-align: center;
}
.button.cancel{
    margin-right:100px;
}
.requirement-declaration.skill{
    padding-top: 20px;
    width: 80%;
    margin-left: 10%;
}
</style>