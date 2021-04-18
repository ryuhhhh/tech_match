<template>
  <div class="wrapper">
    <!-- スキルの種類毎に作成 -->
    <div
      v-for="skill in skills"
      :key="skill.skillCd"
      class="name-select-wrapper"
      :class="nameSelectWrapperClass"
      :value="skill.skillCd"
    >
      <div class="skill-name">
        {{ skill.name }}
      </div>
      <div
        class="skill-level"
        :class="skillForm[skill.skillCd]"
      >
        {{ adjustLevelExpression(skillForm[skill.skillCd]) }}
      </div>
      <select
        v-model="skillForm[skill.skillCd]"
        class="level-select"
        :disabled="!editNow"
      >
        <option value="" />
        <option
          v-for="(level,index) in skillLevels"
          :key="level.skillLevelCd"
          :value="level.skillLevelCd"
        >
          Lv.{{ index+1 }} {{ level.description }}
        </option>
      </select>
    </div>
  </div>
</template>

<script>
export default {
    name: 'UserSkills',
    components:{
    },
    props:{
        skills:{
            Type:Array,
            default:function(){
                return []
            }
        },
        userSkill:{
            type:Array,
            default:function(){
                return []
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
            skillForm:{}
        }
    },
    computed:{
        skillLevels:function(){
            return this.$store.state.masterTables.skillLevels
        },
        adjustLevelExpression(){
            return function(str){
                if(str!==undefined && str!==''){
                    return str.substring(5)
                }
            }
        },
        nameSelectWrapperClass:function(){
            if(!this.editNow){
                return 'name-select-wrapper-before-editting'
            } else {
                return 'name-select-wrapper-editting'
            }
        }
    },
    watch:{
        finishEdit:function(newOne){
            // 編集が終了の時送信
            if(newOne===true){
                // 空の値を削除する
                for (let [skillKey, skillValue] of Object.entries(this.skillForm)) {
                    if(skillValue === '') {
                        delete this.skillForm[skillKey]
                    }
                }
                this.$emit('fromSkillSelection',this.skillForm)
            }
        }
    },
    created:function(){
        this.$watch(
            () => [this.skills,this.userSkill],
            (value) => {
                if(value[0].length!==0 || value[1].length!==0){
                    // 初期化
                    for(let skill of this.skills){
                        for(let uskill of this.userSkill){
                            if(skill.skillCd === uskill.skillCd){
                                this.$set(this.skillForm,skill.skillCd,uskill.skillLevelCd)
                                break
                            }
                        }
                    }
                }
            }
        );
    }
}
</script>
<style scoped>
.name-select-wrapper{
    display: inline-block;
    position: relative;
    height: 50px;
    margin: 10px;
    background: whitesmoke;
    width: 200px;
    border-radius: 15px;
}
.name-select-wrapper-before-editting{
    border: 2px solid #b9b8b5;
}
.name-select-wrapper-editting{
    border: 2px solid #fa855e82;
}

.skill-name{
    position: absolute;
    z-index: 0;
    font-size: 1.5em;
    height: 50px;
    width: 200px;
    padding-top: 8px;
    padding-left: 10px;
}
.level-select{
    position:absolute;
    z-index:1;
    opacity:0;
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
    height: 50px;
    width: 200px;
    padding: 10px;
    font-size: 1.1em;
}
.skill-level{
    position: absolute;
    z-index: 0;
    font-size: 1.8em;
    height: 50px;
    text-align: right;
    width: 200px;
    padding-top: 5px;
    padding-right: 15px;
    font-weight: bold;
    color: crimson;
}
</style>