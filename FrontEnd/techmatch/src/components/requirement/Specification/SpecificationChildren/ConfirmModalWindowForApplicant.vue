<template>
  <div class="modal is-active">
    <div class="modal-background" />
    <div class="modal-card">
      <header class="modal-card-head">
        <p class="modal-card-title">
          登録内容確認
        </p>
        <img
          src="./../../../../assets/delete.svg"
          class="close-icon"
          @click="isRequirementProcessingOk(false)"
        >
      </header>
      <section class="modal-card-body modal-content-body">
        <p class="caution">
          応募者を1人決定します
        </p>
        <p class="caution">
          決定の取り消しは出来ません。
        </p>
        <p class="caution">
          決定後はSNS等を通じて応募者へコンタクトを取ってください。
        </p>
        <table class="table is-striped confirm-table is-fullwidth">
          <thead>
            <tr>
              <td>
                ユーザID
              </td>
              <td>
                {{ userId }}
              </td>
            </tr>
          </thead>
        </table>
      </section>
      <footer class="modal-card-foot buttons-wrapper">
        <button
          class="button is-medium"
          @click="isRequirementProcessingOk(false)"
        >
          戻る
        </button>
        <button
          class="button is-success is-medium ok-button"
          @click="isRequirementProcessingOk(true)"
        >
          決定
        </button>
      </footer>
    </div>
  </div>
</template>

<script>
export default {
    name: 'RegistrationModalWindow',
    props:{
        userId:{
            type:String,
            default:''
        }
    },
    data:function(){
        return {
            // TableRows:[{name:'タイトル',id:'title'}]
        }
    },
    methods:{
        isRequirementProcessingOk:function(bool){
            this.$emit('fromConfirmModalWindow',bool)
        }
    }
};
</script>

<style scoped>
.confirm-table{
    margin-top: 30px;
    margin-bottom: 30px;
    font-size: 1.6em;
}
.modal-content-body{
    padding:45px;
}
.caution{
    font-size: 1.3em;
    color: #3e3e3e;
    font-weight: bold;
}
.buttons-wrapper{
    display:block;
    text-align:right;
}
.ok-button{
    margin-left: 25px;
}
.close-icon{
    cursor:pointer;
}
</style>
