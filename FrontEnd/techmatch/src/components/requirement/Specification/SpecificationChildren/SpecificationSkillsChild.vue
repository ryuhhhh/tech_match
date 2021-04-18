<template>
  <div class="skill-select-wrapper">
    <!-- スキルの種類毎に作成 -->
    <div
      v-for="skill in requireSkills"
      :key="skill.skillCd"
      class="name-select-wrapper"
      :value="skill.skillCd"
    >
      <div class="skill-name">
        {{ skill.skillName }}
      </div>
      <div class="skill-level">
        {{ skill.skillLevelCd.substring(5) }}
      </div>
    </div>
  </div>
</template>

<script>
export default {
    name: 'UserSkills',
    props:{
        requireSkills:{
            Type:Array,
            default:function(){
                return []
            }
        }
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
    border: 2px solid #b9b8b5;
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