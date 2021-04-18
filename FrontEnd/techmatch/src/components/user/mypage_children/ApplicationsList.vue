<template>
  <div class="wrapper">
    <div class="application-title">
      応募一覧
    </div>
    <section class="modal-card-body modal-content-body">
      <!-- TODO アイコンを追加 -->
      <table
        v-if="techMatchUserApplicationsModelList.length>0"
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
            <td>
              状態
            </td>
          </tr>
          <tr
            v-for="application in techMatchUserApplicationsModelList"
            :key="application.requirementModel.id"
            class="application-tr"
            @click="confirmSpecification(application.requirementModel.id)"
          >
            <td>
              {{ application.requirementModel.title }}
            </td>
            <td>
              {{ application.requirementModel.expireDate }}
            </td>
            <td v-show="application.adoption">
              マッチ済み
            </td>
            <td v-show="!application.adoption">
              応募中
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
    name: 'ApplicationList',
    props:{
        techMatchUserApplicationsModelList:{
            type:Array,
            default:function(){
                return []
            }
        }
    },
    methods:{
        confirmSpecification:function(id){
            this.$router.push({name:'RequirementSpecification',query:{id:id}})
        }
    }
};
</script>
<style scoped>
.application-title{
    margin-bottom: 10px;
    margin-top: 20px;
    font-size: 1.5em;
    border-bottom: 5px solid #fa855e87;
}
.application-tr{
    cursor:pointer;
    transition:.3s ease-in-out;
}
.application-tr:hover{
    background-color:#eff0eb;
}
</style>