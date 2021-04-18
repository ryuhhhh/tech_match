<template>
  <div class="login-wrapper">
    <transition
      name="login"
      appear
      disappear
    >
      <form
        class="login-form"
        @submit.prevent="login"
      >
        <div class="login-title">
          ログイン
        </div>
        <p class="input-p">
          <label class="label">ユーザ名</label>
          <input
            v-model="form.userId"
            class="input"
            type="text"
          >
        </p>
        <p class="input-p">
          <label class="label">パスワード</label>
          <input
            v-model="form.password"
            class="input"
            type="password"
          >
        </p>
        <div class="buttons-wrapper left">
          <div
            class="button is-medium"
            @click="cancel"
          >
            キャンセル
          </div>
        </div>
        <div class="buttons-wrapper right">
          <button
            class="button is-success is-medium"
            type="submit"
          >
            ログイン
          </button>
        </div>
        <div class="error-msg">
          {{ errorMsg }}
        </div>
      </form>
    </transition>
    <div
      class="cover"
      @click="cancel"
    />
  </div>
</template>
<script>
import * as action_type from '@/store/action-type'
export default {
    name: 'Login',
    components: {
    },
    data: () => {
        return{
            form:{},
            errorMsg:''
        }
    },
    computed:{
        isLoging:function(){
            return this.$store.state.isLoging
        }
    },
    methods:{
        cancel:function(){
            this.$router.go(-1)
        },
        cancelOfWrapper:function(){
        },
        login:function(){
            this.$store.dispatch(action_type.LOGIN,this.form)
                .then(()=>{
                    this.makePopup(['おかえりなさい',this.form.userId+'さん'],3000)
                })
                .catch(err=>{
                    this.errorMsg=err.toString().substring(13)
                })
        }
    },
};
</script>
<style scoped>
.login-enter-active {
  animation: bounce-in .5s;
}
.login-leave-active {
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
.wrapper{
}
.cover{
    position:absolute;
    top:0px;
    left:0px;
    width:100%;
    height:100%;
    background-color:#575353f5;
    z-index: 20;
}
.login-form{
    position: absolute;
    margin-bottom: 20px;
    width: max-content;
    background: #f3f2f2;
    padding: 20px;
    text-align: left;
    padding: 50px;
    width:40%;
    padding-right:100px;
    padding-left:100px;
    margin-left:30%;
    border-radius: 5px;
    z-index:21;
}
.login-title{
    text-align: center;
    font-size: 1.8em;
    margin-bottom: 20px;
}

.buttons-wrapper{
    padding-top:20px;
}
.buttons-wrapper.left{
    text-align: left;
    float: left;
}
.input-p{
    margin-bottom:10px;
}
.buttons-wrapper.right{
    text-align: right;
}
.error-msg{
    text-align: center;
    color: red;
    font-size: 1.2em;
    margin-top: 30px;
}
</style>