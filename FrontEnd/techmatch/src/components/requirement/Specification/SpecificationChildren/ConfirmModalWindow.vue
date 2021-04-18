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
          @click="isRegistrationProcessingOk(false)"
        >
      <!-- <button class="delete" aria-label="close"  @click="isRegistrationProcessingOk(false)"></button> -->
      </header>
      <section class="modal-card-body modal-content-body">
        <!-- TODO アイコンを追加 -->
        <p class="caution">
          応募します。よろしいでしょうか。
        </p>
        <p class="caution">
          応募の取り消しは出来ません。
        </p>
        <p class="caution">
          応募後は募集者の反応をお待ちください。
        </p>
        <table class="table is-striped confirm-table is-fullwidth">
          <thead>
            <tr>
              <td>
                タイトル
              </td>
              <td>
                {{ title }}
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
          応募
        </button>
      </footer>
    </div>
  </div>
</template>

<script>
export default {
    name: 'RegistrationModalWindow',
    props:{
        title:{
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
