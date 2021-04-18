<template>
  <div class="wrapper">
    <div class="select">
      <div
        v-for="skill in frontEndSkills"
        :key="skill.skillCd"
        class="skill-select"
      >
        <label :for="skill.skillCd">
          {{ skill.name }}
        </label>
        <select
          :id="skill.skillCd"
          class="level-select"
        >
          <option
            v-for="(option,i) in skillLevels"
            :key="option.skillLevelCd"
            :value="option"
          >
            {{ i+1+" "+option['description'] }}
          </option>
        </select>
      </div>
    </div>
  </div>
</template>
<script>
export default {
    components: {
    },
    data:function(){
        return {
            jenre:'',
        }
    },
    computed:{
        skillJenres:function(){
            return this.$store.state.masterTables.skillJenres
        },
        frontEndSkills:function(){
            return this.$store.state.masterTables.frontEndSkills
        },
        skillLevels:function(){
            return this.$store.state.masterTables.skillLevels
        }
        // skillJenres:[],/*スキルジャンル*/
        // backEndSkills:[],/*バックエンドスキル*/
        // infraSkills:[],/*インフラスキル*/
        // mlSkills:[],/*機械学習スキル*/
        // nativeAppSKills:[]/*ネイティブアプリスキル*/
    },
    methods:{
    },
}
</script>
<style scoped>
.skill-select{
    text-align: center;
    display:inline-block;
    border: 1px solid black;
    margin: 20px;
    width: 30%;
        position: relative;
}
label{
position: absolute;
    left: 26px;
}
.level-select{

    opacity: 0;
    width:100%;
    height:100%;
}
.select:not(.is-multiple):not(.is-loading)::after{
    display:none;
}
</style>
