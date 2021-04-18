<template>
  <div class="wrapper">
    <div class="requirement-title">
      募集一覧
    </div>
    <section class="modal-card-body modal-content-body">
      <!-- TODO アイコンを追加 -->
      <table
        v-if="techMatchUserRequirentsModelList.length>0"
        class="table is-striped confirm-table is-fullwidth"
      >
        <thead>
          <tr>
            <td>
              タイトル
            </td>
            <td>
              期日
            </td>
          </tr>
          <tr
            v-for="requirement in techMatchUserRequirentsModelList"
            :key="requirement.requirementModel.id"
            class="requirement-tr"
            @click="confirmSpecification(requirement.requirementModel.id)"
          >
            <td>
              {{ requirement.requirementModel.title }}
            </td>
            <td>
              {{ requirement.requirementModel.expireDate }}
            </td>
          </tr>
        </thead>
      </table>
    </section>
  </div>
</template>

<style scoped>
</style>

<script>
export default {
    name: 'RequirementList',
    props:{
        techMatchUserRequirentsModelList:{
            type:Array,
            default:function(){
                return []
            }
        }
    },
    methods:{
        confirmSpecification:function(id){
            this.$router.push({name:'MyRequirementSpecification',query:{id:id}})
        }
    }
};
</script>
<style scoped>
.requirement-title{
    margin-bottom: 10px;
    margin-top: 20px;
    font-size: 1.5em;
    border-bottom: 5px solid #fa855e87;
}
.requirement-tr{
    cursor:pointer;
    transition:.3s ease-in-out;
}
.requirement-tr:hover{
    background-color:#eff0eb;
}
</style>