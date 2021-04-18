<template>
  <div class="wrapper">
    <div class="right-field">
      <div class="adoptedNum">
        決定状況<br>
        {{ computeAdoptedNum }}/{{ requirementNum }}
      </div>
    </div>
    <div class="main-field">
      <div class="requirement-declaration">
        募集基本情報
      </div>
      <specification-basic-info :basic-info="requirementAndSkillsModel.requirementModel" />
      <div class="requirement-declaration skill">
        募集スキル情報
      </div>
      <div
        v-show="requirementAndSkillsModel.frontEndSkills!==void 0 && requirementAndSkillsModel.frontEndSkills.length>0"
        class="skill-type"
      >
        フロントエンド
      </div>
      <specification-skills-child
        :require-skills="requirementAndSkillsModel.frontEndSkills"
      />
      <div
        v-show="requirementAndSkillsModel.backEndSkills!==void 0 && requirementAndSkillsModel.backEndSkills.length>0"
        class="skill-type"
      >
        バックエンド
      </div>
      <specification-skills-child
        :require-skills="requirementAndSkillsModel.backEndSkills"
      />
      <div
        v-show="requirementAndSkillsModel.nativeApplicationSkills!==void 0 && requirementAndSkillsModel.nativeApplicationSkills.length>0"
        class="skill-type"
      >
        ネイティブアプリ
      </div>
      <specification-skills-child
        :require-skills="requirementAndSkillsModel.nativeApplicationSkills"
      />
      <div
        v-show="requirementAndSkillsModel.infraSkills!==void 0 && requirementAndSkillsModel.infraSkills.length>0"
        class="skill-type"
      >
        インフラ
      </div>
      <specification-skills-child
        :require-skills="requirementAndSkillsModel.infraSkills"
      />
      <div
        v-show="requirementAndSkillsModel.mlSkills!==void 0 && requirementAndSkillsModel.mlSkills.length>0"
        class="skill-type"
      >
        機械学習
      </div>
      <specification-skills-child
        :require-skills="requirementAndSkillsModel.mlSkills"
      />
      <div class="requirement-declaration skill">
        応募ユーザ
      </div>
      <applicants-list
        :applicants-list="applicantsList"
        @fromApplicantsList="fromApplicantsList"
      />
      <confirm-modal-window
        v-if="ifCheck"
        :user-id="applicantId"
        @fromConfirmModalWindow="fromConfirmModalWindow"
      />
      <div class="buttons-wrapper">
        <a
          class="button cancel is-large"
          @click="$router.go(-1)"
        >戻る</a>
      </div>
    </div>
  </div>
</template>

<script>
import SpecificationBasicInfo from './SpecificationChildren/SpecificationBasicInfo'
import SpecificationSkillsChild from './SpecificationChildren/SpecificationSkillsChild'
import util from '@/common/util'
import * as action_type from '@/store/action-type'
import ConfirmModalWindow from './SpecificationChildren/ConfirmModalWindowForApplicant'
import ApplicantsList from './SpecificationChildren/ApplicantsList'
export default {
    name: 'RequirementList',
    components: {
        SpecificationBasicInfo,
        SpecificationSkillsChild,
        ConfirmModalWindow,
        ApplicantsList
    },
    mixins:[util],
    data: () => {
        return {
            requirementStatus:'RS001',
            requirementAndSkillsModel:{},
            applications:[],
            ifCheck:false,
            requirementId:-1,
            applicantId:'',
            applicantsList:[],
            requirementNum:0
        }
    },
    computed:{
        computeAdoptedNum:function(){
            let num = 0
            for(let applicant of this.applicantsList){
                if(applicant.adoption){
                    num++
                }
            }
            return num
        }
    },
    mounted:async function(){
        this.requirementId = parseInt(this.$route.query.id)
        window.scrollTo(0,0)
        await this.$set(this,'requirementAndSkillsModel',this.$route.params.requirementAndSkillsModel)
        await this.$store.dispatch(action_type.GET_APPLICANTS_INFO,this.$route.query.id)
            .then(result=>{this.applicantsList=result.data})
        this.requirementNum = this.requirementAndSkillsModel.requirementModel.requirementNum
    },
    methods:{
        cancel:function(){
            this.$router.push({name:'home'},()=>{})
        },
        check:function(){
            // 未ログイン状態ならログインへ
            if(!this.$store.state.isLoggedIn){
                // TODO ログイン画面を出し、ログイン後に同じ画面へ行くようにする
                this.makePopup(['応募する前にログインを行ってください'],5000)
            }else{
                this.ifCheck = true
            }
        },
        fromConfirmModalWindow:async function(bool){
            this.ifCheck = false
            if(bool){
                await this.$store.dispatch(action_type.DESIDE_APPLICANT,{id:this.requirementId,userId:this.applicantId})
                this.applicantId = ''
                await this.makePopup(['決定しました'],5000)
            }
        },
        fromApplicantsList:async function(id){
            this.applicantId = id
            this.ifCheck = true
        }
    }
};
</script>
<style scoped>
.wrapper{
    margin-left: 20%;
    margin-right: 20%;
    /* display: -webkit-flex; */
    /* display: flex; */
}
.right-field{
    float: right;
    position: absolute;
    height: 100%;
    background-color: #f3f2f2;
    width: 15%;
    left: 85%;
    margin-top: 4%;
}
.adoptedNum{
    font-size: 1.8em;
    padding: 20px;
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
}
.requirement-declaration.skill{
    padding-top: 20px;
    width: 80%;
    margin-left: 10%;
}
.skill-type{
  font-size: 1.5em;
  border-bottom: 5px solid #8aa553;
  margin-top: 20px;
}
.button.done{
  border-color: #D0D1CD;
  color: #363636;
  background-color:#f3f2f2;
  cursor:auto;
}
.buttons-wrapper{
  text-align:center;
}
</style>