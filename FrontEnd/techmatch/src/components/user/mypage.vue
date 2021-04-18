<template>
  <div class="mypage-wrapper">
    <basic-info
      :basic-info="basicInfo"
      :edit-now="editNow"
      :finish-edit="finishEdit"
      @fromBasicInfo="fromBasicInfo"
    />
    <user-skills
      :tech-match-user-all-skill-model="techMatchUserAllSkillModel"
      :edit-now="editNow"
      :finish-edit="finishEdit"
      @fromUserSkills="fromUserSkills"
    />
    <requirement-list
      :tech-match-user-requirents-model-list="techMatchUserRequirentsModelList"
    />
    <applications-list
      :tech-match-user-applications-model-list="techMatchUserApplicationsModelList"
    />
    <div
      v-show="!editNow"
      class="edit-icon-wrapper"
      :class="editIconClass"
      @click="startEdit"
    >
      <img
        src="./../../assets/edit.svg"
        class="edit-icon"
      >
    </div>
    <div
      v-show="editNow"
      class="edit-icon-wrapper"
      :class="editIconClass"
      @click="endEdit"
    >
      <img
        src="./../../assets/check.svg"
        class="edit-icon"
      >
    </div>
  </div>
</template>
<script>
import BasicInfo from './mypage_children/BasicInfo'
import UserSkills from './mypage_children/UserSkills'
import * as action_type from '@/store/action-type'
import RequirementList from './mypage_children/RequirementsList'
import ApplicationsList from './mypage_children/ApplicationsList'
export default {
    name: 'MenuBar',
    components:{
        BasicInfo,
        UserSkills,
        RequirementList,
        ApplicationsList
    },
    data:function(){
        return{
            userId:'',
            basicInfo:{},
            contact:{},
            applications:{},
            requrireMents:{},
            editNow:false,
            finishEdit:false,
            // 受信オブジェクト
            techMatchUserAllSkillModel:{},
            techMatchUserResponseModel:{},
            techMatchUserApplicationsModelList:[],
            techMatchUserRequirentsModelList:[],
            // 送信用
            userFrontModel:{},
            skills:[],
            userImage:null,
            // フォーム制御用
            fromChildrenFormCount:0,
            isInputOfFormOk:false,

        }
    },
    computed:{
        editIconClass:function(){
            if(!this.editNow){
                return 'before-editting'
            } else {
                return 'editting'
            }
        }
    },
    watch:{
        fromChildrenFormCount:function(value){
            if(value>=2){
                this.fromChildrenFormCount = 0
                this.finishEdit = false
                this.$store.state.isLoading = false
                if(this.isInputOfFormOk){
                    this.$store.dispatch(action_type.UPDATE_USER_INFO,{skills:this.skills,userFrontModel:this.userFrontModel})
                    this.skills = []
                    if(this.userImage!==null&&this.userImage!==''){
                        this.$store.dispatch(action_type.UPDATE_USER_IMAGE,this.userImage)
                    }
                }
            }
        }
    },
    // マウント時にマイページに載せる情報を取得
    mounted:function(){
        /* TODO どうやって一回で済ませる?*/
        // 初回はmutationを待つ
        this.$store.watch(
            state => state.userId,
            userId => {
                if(userId!=='' &&  userId!==null){
                    this.$store.dispatch(action_type.GET_USER_ALL_INFO,userId)
                        .then(result=>{
                            //   console.log(result)
                            this.techMatchUserResponseModel = result.data.techMatchUserResponseModel
                            this.techMatchUserAllSkillModel = result.data.techMatchUserAllSkillModel

                            this.userId = result.data.techMatchUserAllSkillModel.userId
                            this.$set(this.basicInfo,'introduce',result.data.techMatchUserResponseModel.introduce)
                            this.$set(this.basicInfo,'userId',result.data.techMatchUserResponseModel.userId)
                            this.$set(this.basicInfo,'facebookAccount',result.data.techMatchUserResponseModel.facebookAccount)
                            this.$set(this.basicInfo,'twitterAccount',result.data.techMatchUserResponseModel.twitterAccount)
                            this.$set(this.basicInfo,'userImage',result.data.techMatchUserResponseModel.userImage)
                        }
                        )
                    this.$store.dispatch(action_type.GET_USER_REQUIREMENTS)
                        .then(result=>{this.techMatchUserRequirentsModelList = result.data})
                    this.$store.dispatch(action_type.GET_USER_APPLICATIONS)
                        .then(result=>{this.techMatchUserApplicationsModelList = result.data})
                }
            }
        )
        // 2回目以降はこっち
        if(this.$store.state.userId!=='' &&  this.$store.state.userId!==null){
            let userId = this.$store.state.userId
            this.$store.dispatch(action_type.GET_USER_ALL_INFO,userId)
                .then(result=>{
                    //   console.log(result)
                    this.techMatchUserResponseModel = result.data.techMatchUserResponseModel
                    this.techMatchUserAllSkillModel = result.data.techMatchUserAllSkillModel
                    this.userId = result.data.techMatchUserAllSkillModel.userId
                    this.$set(this.basicInfo,'introduce',result.data.techMatchUserResponseModel.introduce)
                    this.$set(this.basicInfo,'userId',result.data.techMatchUserResponseModel.userId)
                    this.$set(this.basicInfo,'facebookAccount',result.data.techMatchUserResponseModel.facebookAccount)
                    this.$set(this.basicInfo,'twitterAccount',result.data.techMatchUserResponseModel.twitterAccount)
                    this.$set(this.basicInfo,'userImage',result.data.techMatchUserResponseModel.userImage)
                }
                )
            this.$store.dispatch(action_type.GET_USER_REQUIREMENTS)
                .then(result=>{this.techMatchUserRequirentsModelList = result.data})
            this.$store.dispatch(action_type.GET_USER_APPLICATIONS)
                .then(result=>{this.techMatchUserApplicationsModelList = result.data})
        }
    },
    methods:{
        fromBasicInfo:function(basicInfoForm){
            // 基本情報
            this.userFrontModel.facebookAccount = basicInfoForm.basicInfo.facebookAccount
            this.userFrontModel.twitterAccount = basicInfoForm.basicInfo.twitterAccount
            this.userFrontModel.introduce = basicInfoForm.basicInfo.introduce
            this.userFrontModel.userId = basicInfoForm.basicInfo.userId

            // ユーザイメージ
            this.userImage = basicInfoForm.userImage
            this.isInputOfFormOk = basicInfoForm.isOk
            this.fromChildrenFormCount ++
        },
        startEdit:function(){
            this.editNow = true
        },
        endEdit:function(){
            this.editNow = false
            this.finishEdit = true
            this.$store.state.isLoading = true
        },
        fromUserSkills:function(userSkillsForm){
            // TODO 配列に直す...
            for (let [, skills] of Object.entries(userSkillsForm)) {
                for (let [skillKey, skillValue] of Object.entries(skills)) {
                    let skillObj = new Object()
                    skillObj.skillCd = skillKey
                    skillObj.skillLevelCd = skillValue
                    this.skills.push(skillObj)
                }
            }
            this.fromChildrenFormCount ++
        }
    }
};
</script>
<style scoped>
.mypage-wrapper{
    width: 60%;
    margin-left: 20%;
    margin-top: 20px;
    margin-bottom: 30px;
}
.edit-icon-wrapper{
    position: fixed;
    font-size: 1.6em;
    top: 130px;
    left: 89%;
    text-align: center;
    width: 100px;
    color: #363636;
    height: 100px;
    font-weight: bold;
    line-height: 100px;
    border-radius: 75px;
    padding: 10px;
    cursor:pointer;
    opacity: 0.7;
    transition:0.3s ease-in-out;
    z-index: 5;
}
.edit-icon-wrapper:hover{
    opacity:1;
}
.before-editting{
    background: #8aa553;
}
.editting{
    background:#ff5722;
}

</style>