<template>
  <div class="search-wrapper">
    <form
      class="search-form"
      @submit.prevent="handleSearch"
    >
      <div class="skill-search">
        <select
          v-model="selectedSkill"
          class="select skill"
        >
          <option
            value=""
            selected
            class="select-default"
          >
            スキル
          </option>
          <option
            v-for="skill in allSkills"
            :key="skill.skillCd"
            :value="skill.skillCd"
          >
            {{ skill.name }}
          </option>
        </select>
        <select
          v-model="selectedSkillLevel"
          class="select"
          :disabled="selectedSkill===''"
        >
          <option
            value=""
            selected
            class="select-default"
          >
            レベル(未選択可)
          </option>
          <option
            v-for="(skillLevel,index) in masterTables.skillLevels"
            :key="skillLevel.skillLevelCd"
            :value="skillLevel.skillLevelCd"
          >
            Lv.{{ index+1 }} {{ skillLevel.description }}
          </option>
        </select>
      </div>
      <div class="full-text-search">
        <input
          v-model="searchText"
          class="input"
          type="text"
          placeholder="全文検索"
        >
      </div>
      <div class="search-buttons">
        <button
          class="button is-medium"
          type="reset"
          @click="clear"
        >
          クリア
        </button>
        <button
          class="button is-success is-medium"
          type="submit"
        >
          検索
        </button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
    name: 'RequirementListSearchBar',
    data: () => {
        return {
            searchText:'',
            selectedSkill:'',
            selectedSkillLevel:'',
            condition:{
                fullTextSearchWords: [],
                skillMap: {}
            },
            ifCheck:false,
            isSkillSelect:false
        }
    },
    computed:{
        allSkills:function(){
            let allSkills = this.$store.state.masterTables.frontEndSkills
                .concat(this.$store.state.masterTables.backEndSkills)
                .concat(this.$store.state.masterTables.infraSkills)
                .concat(this.$store.state.masterTables.mlSkills)
                .concat(this.$store.state.masterTables.nativeAppSKills)
            return allSkills
        },
        masterTables:function(){
            return this.$store.state.masterTables
        }
    },
    methods:{
        handleSearch:function(){
            this.condition.fullTextSearchWords = this.searchText.split(' ')
            if(this.selectedSkill!==''){
                this.condition.skillMap = {[this.selectedSkill] : this.selectedSkillLevel}
            }else{
                this.selectedSkill = ''
                this.condition.skillMap = {}
            }
            this.$emit('fromRequirementListSearchBar',
                {
                    fullTextSearchWords:this.condition.fullTextSearchWords,
                    skillMap:this.condition.skillMap
                })
        },
        clear:function(){
            this.searchText = ''
            this.fullTextSearchWords = []
            this.selectedSkill = ''
            this.selectedSkillLevel = ''
            this.condition.skillMap = {}
            this.condition.fullTextSearchWords = []
        }
    }
}
</script>
<style scoped>
.search-form{
    margin-top: 20px;
    margin-bottom: 20px;
    text-align: center;
    padding-top: 20px;
    padding-bottom: 20px;
    background-color:#93a07626;
}
select{
    font-size:1.1em;
}
.skill-search{
    display:inline-block;
    border-right: 1px solid black;
    height: 50px;
    padding-right: 20px;
    margin-right: 20px;
    padding-top: 7px;
}
.select.skill{
    margin-right: 20px;
}
.select-default{
    color:#D0D1CD;
}
.full-text-search{
    display:inline-block;
}
.search-buttons{
    margin-top: 10px;
}
.button.is-success{
    margin-left: 40px;
}
</style>