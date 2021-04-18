<template>
  <div class="requirement-skills-wrapper">
    <!-- スキルの種類毎に作成 -->
    <div class="skill-type">
      フロントエンド
    </div>
    <SkillSelection
      :skills="masterTables.frontEndSkills"
      :if-check="ifCheck"
      @fromSkillSelection="fromFrontSkillSelection"
    />
    <div class="skill-type">
      バックエンド
    </div>
    <SkillSelection
      :skills="masterTables.backEndSkills"
      :if-check="ifCheck"
      @fromSkillSelection="fromBackSkillSelection"
    />
    <div class="skill-type">
      ネイティブアプリ
    </div>
    <SkillSelection
      :skills="masterTables.nativeAppSKills"
      :if-check="ifCheck"
      @fromSkillSelection="fromNativeSkillSelection"
    />
    <div class="skill-type">
      インフラ
    </div>
    <SkillSelection
      :skills="masterTables.infraSkills"
      :if-check="ifCheck"
      @fromSkillSelection="fromInfraSkillSelection"
    />
    <div class="skill-type">
      機械学習
    </div>
    <SkillSelection
      :skills="masterTables.mlSkills"
      :if-check="ifCheck"
      @fromSkillSelection="fromMlSkillSelection"
    />
  </div>
</template>

<script>
import SkillSelection from './RequirementSkillsChild/RequirementSkillSelection'
export default {
    name: 'UserSkills',
    components:{
        SkillSelection
    },
    props:{
        ifCheck:{
            type:Boolean,
            default:false
        }
    },
    data:function(){
        return {
            form:{},
            skillEmitCount:0,
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
.requirement-skills-wrapper{
}
.skill-type{
  font-size: 1.5em;
  border-bottom: 5px solid #8aa553;
}
</style>