<template>
  <div class="wrapper">
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
      <div class="buttons-wrapper">
        <a
          class="button cancel is-large"
          @click="$router.go(-1)"
        >戻る</a>
        <button
          v-show="!isApplied"
          class="button is-success is-large"
          @click="check"
        >
          応募する
        </button>
        <div
          v-show="isApplied && !isAdoption"
          class="button done is-large"
        >
          応募済み
        </div>
        <div v-show="isApplied && !isAdoption" class="comment">
          募集者の決定または募集期限をお待ちください
        </div>
        <div
          v-show="isApplied && isAdoption"
          class="button done is-large matched"
        >
          マッチ済み
        </div>
        <div v-show="isApplied && isAdoption" class="comment">
          募集者とSNSなどで連絡をお取りください
        </div>
      </div>
    </div>
    <confirm-modal-window
      v-if="ifCheck"
      :title="requirementAndSkillsModel.requirementModel.title"
      @fromConfirmModalWindow="fromConfirmModalWindow"
    />
  </div>
</template>

<script>
import SpecificationBasicInfo from './SpecificationChildren/SpecificationBasicInfo'
import SpecificationSkillsChild from './SpecificationChildren/SpecificationSkillsChild'
import util from '@/common/util'
import * as action_type from '@/store/action-type'
import ConfirmModalWindow from './SpecificationChildren/ConfirmModalWindow'
export default {
    name: 'RequirementList',
    components: {
        SpecificationBasicInfo,
        SpecificationSkillsChild,
        ConfirmModalWindow
    },
    mixins:[util],
    data: () => {
        return {
            requirementStatus:'RS001',
            requirementAndSkillsModel:{},
            applications:[],
            ifCheck:false,
            requirementId:-1,
            isApplied:false,
            isAdoption:false
        }
    },
    computed:{
        isLoggedInComputed:function(){
            return this.$store.state.isLoggedIn
        }
    },
    watch:{
        async isLoggedInComputed(newval){
            if(newval){
                await this.$store.dispatch(action_type.GET_USER_APPLICATIONS)
                    .then(result=>{this.applications=result.data})
                if(this.applications.length>0){
                    let isApplied = this.applications.some(application => {if(application.requirementModel.id === this.requirementId)return true})
                    this.isApplied = isApplied
                }
            }
        }
    },
    mounted:async function(){
        this.requirementId = parseInt(this.$route.query.id)
        window.scrollTo(0,0)
        await this.$set(this,'requirementAndSkillsModel',this.$route.params.requirementAndSkillsModel)
        // ユーザの応募情報を取得する 未ログインなら行わない
        if(this.$store.state.isLoggedIn){
            await this.$store.dispatch(action_type.GET_USER_APPLICATIONS)
                .then(result=>{this.applications=result.data})
            // 応募情報があるとき、応募済みかどうか確認する
            if(this.applications.length>0){
                let isApplied = this.applications.some(application => {if(application.requirementModel.id === this.requirementId)return true})
                this.isApplied = isApplied
                this.isAdoption = this.$route.params.isAdoption
            }
        }
    },
    methods:{
        check:function(){
            // 未ログイン状態ならログインへ
            if(!this.$store.state.isLoggedIn){
                // TODO ログイン画面を出し、ログイン後に同じ画面へ行くようにする
                this.makePopup(['応募する前にログインを行ってください'],5000)
            }else{
                this.ifCheck = true
                // this.$router.push({name:'home'},()=>{})
            }
        },
        fromConfirmModalWindow:async function(bool){
            this.ifCheck = false
            if(bool){
                await this.$store.dispatch(action_type.APPLICATION,this.requirementId)
                this.isApplied = true
                await this.makePopup(['応募しました'],5000)
            }
        },
    }
};
</script>
<style scoped>
.wrapper{
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
.matched{
  border-color: #819e47eb;
}
.comment{
  margin-top: 20px;
  font-size: 1.3em;
}
</style>

<style scoped>
.right-field{
    float: right;
    height: 100%;
    width: 15%;
    position: absolute;
    background-color: #fdfdfb;
    left: 85%;
    top: 100px;
}
</style>