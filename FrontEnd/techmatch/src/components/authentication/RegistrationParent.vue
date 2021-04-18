<template>
  <div class="wrapper-parent">
    <!-- <div class="registartion-wrapper" > -->
    <transition
      name="registration"
      appear
    >
      <keep-alive>
        <registration-child-first
          v-model="form"
          class="child-form"
          @cancel="cancel"
          @fromRegistrationFirst="registrationProcessingFirst"
        />
      </keep-alive>
    </transition>
    <confirm-modal-window
      v-show="isRegistrationProcessing"
      :form="form"
      :errors="errors"
      @fromConfirmModalWindow="confirmUserDecision"
    />
    <div
      class="cover"
      @click="cancel"
    />
    <!-- </div> -->
  </div>
</template>
<script>
import RegistrationChildFirst from './registration_children/RegistrationChildFirst'
import ConfirmModalWindow from './registration_children/RegistrationFirstChildlen/ConfirmModalWindow'
import * as action_type from '@/store/action-type'
export default {
    name:'RegistartionParent',
    components:{
        RegistrationChildFirst,
        ConfirmModalWindow,
    },
    data:function(){
        return {
            form:{},
            isRegistrationProcessing:false,
            fromModalConfirm:false,
            isConfirmNoticesNow:false,
            errors:[]
        }
    },
    methods:{
        registrationProcessingFirst:function(form){
            this.form=form
            this.isRegistrationProcessing=true
        },
        cancel:function(){
            this.$router.go(-1)
        },
        async confirmUserDecision(isRegistrationOk){
            if(isRegistrationOk){
                // ここをメールアドレス認証の処理に切り替える
                // TODO エラーが起きたらUI上に表示
                // errに配列が入っている
                await this.$store.dispatch(action_type.REGISTRATION,this.form)
                    .then(()=>{this.makePopup(['メールを送信しました',this.form.userId+'さん'],8000)})
                    .then(()=>this.$router.push({name:'home'},()=>{}))
                    .catch(err=>{this.$set(this,'errors',err)})
            }else{
                this.isRegistrationProcessing=false
            }
        }
    }
}
</script>
<style scoped>
.registration-enter-active {
  animation: bounce-in .5s;
}
.registration-leave-active {
  animation: bounce-in .5s reverse;
}
@keyframes bounce-in {
  0% {
    transform: scale(0);
  }
  50% {
    transform: scale(1.2);
  }
  100% {
    transform: scale(1);
  }
}
</style>

<style scoped>
.wrapper-parent{
}
.cover{
    position: absolute;
    width: 100%;
    height: 130%;
    overflow:auto;
    top: 0px;
    left: 0px;
    background: #575353f5;
    z-index: 10;
}
.notices{
    z-index: 11;
    position: absolute;
    background-color: white;
    padding: 5px;
    border-radius: 5px;
    width: 80%;
    margin-left: 10%;
    height: max-content;
}
.registartion-wrapper{
  width:100%;
  height:max-content;
}
.registration-title{
    text-align: center;
    font-size: 2.5em;
    margin-top: 20px;
    text-align: center;
    color:#2d2c2cc9;
}
.child-form{
    position: absolute;
    display: inline-block;
    width: 40%;
    height: max-content;
    z-index: 11;
    margin-left: 30%;
    top: 0%;
    margin-top: 3%;
}
</style>
