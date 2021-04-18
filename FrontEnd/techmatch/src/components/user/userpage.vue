<template>
  <div class="mypage-wrapper">
    <div class="user-page-declaration">
      基本情報
    </div>
    <basic-info
      :basic-info="techMatchUserResponseModel"
    />
    <div class="user-page-declaration skill">
      スキル情報
    </div>
    <div
      v-show="techMatchUserAllSkillModel.frontEndSkills!==void 0 && techMatchUserAllSkillModel.frontEndSkills.length>0"
      class="skill-type"
    >
      フロントエンド
    </div>
    <user-skills
      :user-skills="techMatchUserAllSkillModel.frontEndSkills"
    />
    <div
      v-show="techMatchUserAllSkillModel.backEndSkills!==void 0 && techMatchUserAllSkillModel.backEndSkills.length>0"
      class="skill-type"
    >
      バックエンド
    </div>
    <user-skills
      :user-skills="techMatchUserAllSkillModel.backEndSkills"
    />
    <div
      v-show="techMatchUserAllSkillModel.nativeApplicationSkills!==void 0 && techMatchUserAllSkillModel.nativeApplicationSkills.length>0"
      class="skill-type"
    >
      ネイティブアプリ
    </div>
    <user-skills
      :user-skills="techMatchUserAllSkillModel.nativeApplicationSkills"
    />
    <div
      v-show="techMatchUserAllSkillModel.infraSkills!==void 0 && techMatchUserAllSkillModel.infraSkills.length>0"
      class="skill-type"
    >
      インフラ
    </div>
    <user-skills
      :user-skills="techMatchUserAllSkillModel.infraSkills"
    />
    <div
      v-show="techMatchUserAllSkillModel.mlSkills!==void 0 && techMatchUserAllSkillModel.mlSkills.length>0"
      class="skill-type"
    >
      機械学習
    </div>
    <user-skills
      :user-skills="techMatchUserAllSkillModel.mlSkills"
    />
  </div>
</template>
<script>
import BasicInfo from './mypage_children/BasicInfo'
import UserSkills from './userpage_children/UserSkills'
// import * as action_type from '@/store/action-type'
export default {
    name: 'MenuBar',
    components:{
        BasicInfo,
        UserSkills
    },
    data:function(){
        return{
            userId:'',
            basicInfo:{},
            contact:{},
            // 受信オブジェクト
            techMatchUserAllSkillModel:{},
            techMatchUserResponseModel:{},
            techMatchUserApplicationsModelList:[],
            techMatchUserRequirentsModelList:[],

        }
    },
    computed:{
    },
    watch:{
    },
    mounted:function(){
        // ルータガードで取得した値を得る
        this.techMatchUserAllSkillModel = this.$route.params.techMatchUserAllInfoModel.techMatchUserAllSkillModel
        this.techMatchUserResponseModel = this.$route.params.techMatchUserAllInfoModel.techMatchUserResponseModel
    },
    methods:{
    }
};
</script>
<style scoped>
.user-page-declaration{
    text-align: center;
    font-size: 2em;
    margin-top: 40px;
    margin-bottom: 20px;
}
.mypage-wrapper{
    width: 60%;
    margin-left: 20%;
    margin-top: 20px;
    margin-bottom: 30px;
}
.skill-type{
  font-size: 1.5em;
  border-bottom: 5px solid #8aa553;
  margin-top: 20px;
}
</style>