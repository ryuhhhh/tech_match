<template>
  <div class="wrapper">
    <!-- スキルの種類毎に作成 -->
    <div class="skill-type">
      フロントエンド
    </div>
    <SkillSelection
      :user-skill="techMatchUserAllSkillModel.frontEndSkills"
      :skills="masterTables.frontEndSkills"
      :edit-now="editNow"
      :finish-edit="finishEdit"
      @fromSkillSelection="fromFrontSkillSelection"
    />
    <div class="skill-type">
      バックエンド
    </div>
    <SkillSelection
      :user-skill="techMatchUserAllSkillModel.backEndSkills"
      :skills="masterTables.backEndSkills"
      :edit-now="editNow"
      :finish-edit="finishEdit"
      @fromSkillSelection="fromBackSkillSelection"
    />
    <div class="skill-type">
      ネイティブアプリ
    </div>
    <SkillSelection
      :user-skill="techMatchUserAllSkillModel.nativeApplicationSkills"
      :skills="masterTables.nativeAppSKills"
      :edit-now="editNow"
      :finish-edit="finishEdit"
      @fromSkillSelection="fromNativeSkillSelection"
    />
    <div class="skill-type">
      インフラ
    </div>
    <SkillSelection
      :user-skill="techMatchUserAllSkillModel.infraSkills"
      :skills="masterTables.infraSkills"
      :edit-now="editNow"
      :finish-edit="finishEdit"
      @fromSkillSelection="fromInfraSkillSelection"
    />
    <div class="skill-type">
      機械学習
    </div>
    <SkillSelection
      :user-skill="techMatchUserAllSkillModel.mlSkills"
      :skills="masterTables.mlSkills"
      :edit-now="editNow"
      :finish-edit="finishEdit"
      @fromSkillSelection="fromMlSkillSelection"
    />
  </div>
</template>

<script>
import SkillSelection from './UserSkillsChild/SkillSelection'
export default {
    name: 'UserSkills',
    components:{
        SkillSelection
    },
    props:{
        techMatchUserAllSkillModel:{
            type:Object,
            default:function(){
                return {}
            }
        },
        editNow:{
            type:Boolean,
            default:function(){
                return false
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
            form:{},
            skillEmitCount:0
        }
    },
    computed:{
        skillJenres:function(){
            return this.$store.state.masterTables.skillJenres
        },
        masterTables:function(){
            return this.$store.state.masterTables
        },
    },
    watch:{
        skillEmitCount:function(value){
            if(value>=5){
                this.skillEmitCount = 0
                this.$emit('fromUserSkills',this.form)
            }
        }
    },
    methods:{
        fromFrontSkillSelection:function(form){
            this.form.frontEndSkills = form
            this.skillEmitCount++
        },
        fromBackSkillSelection:function(form){
            this.form.backEndSkills = form
            this.skillEmitCount++
        },
        fromNativeSkillSelection:function(form){
            this.form.nativeApplicationSkills = form
            this.skillEmitCount++
        },
        fromInfraSkillSelection:function(form){
            this.form.infraSkills = form
            this.skillEmitCount++
        },
        fromMlSkillSelection:function(form){
            this.form.mlSkills = form
            this.skillEmitCount++
        }
    }
};
</script>
<style scoped>
.skill-type{
  margin-bottom: 10px;
  margin-top: 20px;
  font-size: 1.5em;
  border-bottom: 5px solid #8aa553;
}
</style>