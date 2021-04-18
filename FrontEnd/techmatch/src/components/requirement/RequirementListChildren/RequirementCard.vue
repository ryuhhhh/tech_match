<template>
  <div
    class="search-card-wrapper card"
    @click="ConfirmRequirementSpecification"
  >
    <header class="card-header">
      {{ requirementSkillEntity.title }}
    </header>
    <div class="card-content">
      <div>
        <table class="table is-striped confirm-table is-fullwidth">
          <tr
            v-for="skillEntity in requirementSkillEntity.requirementSkillEntityList"
            :key="skillEntity.requirementSkillId"
          >
            <td>
              {{ convertSkillCd2SkillName(skillEntity.skillCd) }}
            </td>
            <td>
              Lv.{{ skillEntity.skillLevelCd.substring(5) }}
            </td>
          </tr>
        </table>
      </div>
    </div>
    <footer class="card-footer">
      ユーザ：{{ requirementSkillEntity.userId }}
    </footer>
  </div>
</template>

<script>
import util from '@/common/util'
export default {
    name: 'RequirementCard',
    components: {
    },
    mixins:[util],
    props:{
        requirementSkillEntity:{
            type:Object,
            default:()=>{
                return {}
            }
        }
    },
    data: () => {
        return {
        }
    },
    computed:{
    },
    mounted:function(){
    },
    methods:{
        ConfirmRequirementSpecification:function(){
            this.$router.push({name:'RequirementSpecification',query:{id:this.requirementSkillEntity.id}})
        }
    }
}
</script>
<style scoped>
.search-card-wrapper{
    width: 35%;
    margin-left: 10%;
    height: 300px;
    margin-bottom:30px;
    display: inline-block;
    cursor:pointer;
    transition:.3s ease-in-out;
}
.card-header{
    font-size: 1.3em;
}
.card-footer{
    font-size:1.1em;
}
.card-header,.card-footer{
    padding-left:10px;
    padding-right:10px;
}
.search-card-wrapper:hover{
    transform:scale(1.1,1.1);
}
.card-content{
    height: 80%;
    overflow:auto;
}
</style>