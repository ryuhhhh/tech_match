<template>
  <div class="wrapper-applicant-list">
    <div class="requirement-title" />
    <section class="modal-card-body modal-content-body">
      <!-- TODO アイコンを追加 -->
      <table
        v-if="applicantsList.length>0"
        class="table is-striped confirm-table is-fullwidth"
      >
        <thead>
          <tr>
            <td>
              ユーザ名
            </td>
            <td>
              詳細
            </td>
            <td>
              決定
            </td>
          </tr>
          <tr
            v-for="applicant in applicantsList"
            :key="applicant.userId"
          >
            <td class="applicant-user-id">
              {{ applicant.userId }}
            </td>
            <td>
              <button class="button is-light is-medium">
                <router-link :to="{ name: 'UserPage', query: { userId: applicant.userId }}">
                  詳細
                </router-link>
              </button>
            </td>
            <td v-if="!applicant.adoption">
              <button
                class="button is-success is-medium"
                @click="confirmDeside(applicant.userId)"
              >
                決定
              </button>
            </td>
            <td v-else>
              <div
                class="button done is-medium"
              >
                決定済み
              </div>
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
        applicantsList:{
            type:Array,
            default:function(){
                return []
            }
        }
    },
    data:function(){
        return{
        }
    },
    mounted:function(){
    },
    methods:{
        confirmApplicantSpecification:function(id){
            this.$router.push({name:'UserPage',query:{userId:id}})
        },
        confirmDeside:function(userId){
            this.$emit('fromApplicantsList',userId)
        }
    }
};
</script>
<style scoped>
.wrapper-applicant-list{
  margin-bottom:30px;
}
.requirement-title{
    font-size: 1.5em;
    border-bottom: 5px solid #fa855e87;
}
.applicant-user-id{
  font-size:1.6em;
}
.button.done{
  border-color: #D0D1CD;
  color: #363636;
  background-color:#f3f2f2;
  cursor:auto;
}
</style>