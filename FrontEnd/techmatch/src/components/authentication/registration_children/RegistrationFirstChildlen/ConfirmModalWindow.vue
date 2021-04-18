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
          ユーザ名はこれ以降変更ができません<br>
          登録ボタン押下後、確認メールが送られてきます。<br>
          そちらの案内に従ってください。
        </p>
        <table class="table is-striped confirm-table is-fullwidth">
          <thead>
            <tr
              v-for="row in TableRows"
              :key="row.id"
            >
              <td>
                {{ row.name }}
              </td>
              <td>
                {{ form[row.id] }}
              </td>
            </tr>
            <tr>
              <td>
                パスワード
              </td>
              <td>
                ****
              </td>
            </tr>
          </thead>
        </table>
      </section>
      <footer class="modal-card-foot buttons-wrapper">
        <button
          class="button is-medium"
          @click="isRegistrationProcessingOk(false)"
        >
          戻る
        </button>
        <button
          class="button is-success is-medium ok-button"
          @click="isRegistrationProcessingOk(true)"
        >
          登録
        </button>
        <div
          v-for="(item,key) in errors"
          :key="key"
          class="error-text"
        >
          {{ item }}
        </div>
      </footer>
    </div>
  </div>
</template>

<script>
export default {
    name: 'RegistrationModalWindow',
    props:{
        form:{
            type:Object,
            default:function(){
                return {}
            }
        },
        errors:{
            type:Array,
            default:function(){
                return []
            }
        }
    },
    data:function(){
        return {
            TableRows:[{name:'ユーザ名',id:'userId'},{name:'メールアドレス',id:'mailAddress'}]//,{name:'パスワード',id:'password'}
        }
    },
    methods:{
        isRegistrationProcessingOk:function(bool){
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
.error-text{
  color:red;
  text-align: center;
  margin-top: 10px;
  margin-bottom: 10px;
}
</style>
