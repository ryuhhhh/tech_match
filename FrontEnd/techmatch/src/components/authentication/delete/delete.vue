<template>
  <div class="delete-wrapper">
    <div class="title">
      退会
    </div>
    <div v-if="isLoggedIn">
      <p><img src="./../../../assets/caution.svg">以下の情報が削除されます。退会してもよろしいでしょうか。</p>
      <div class="message-body">
        <ul>
          <li class="list-item">
            ・ユーザ基本情報
          </li>
          <li class="list-item">
            ・ユーザスキル情報
          </li>
          <li class="list-item">
            ・募集情報
          </li>
          <li class="list-item">
            ・応募情報
          </li>
        </ul>
      </div>
      <p>退会される場合は，自身のユーザ名を入力し，退会ボタンを押下してください。</p>
      <div class="input-wrapper">
        <input
          v-model="userId"
          type="text"
          placeholder="ユーザ名"
        >
        <button
          class="button is-light is-midium"
          @click="checkUserId"
        >
          退会
        </button>
      </div>
      <div
        v-if="isError"
        style="color:red;font-size:1.1em;"
      >
        ユーザ名を正しく入力してください
      </div>
    </div>
    <div
      v-else
      class="else-comment"
    >
      ご登録・ログインをお待ちしております。
    </div>
  </div>
</template>
<script>
export default {
    name: 'Delete',
    data:function(){
        return {
            userId:'',
            error:'ユーザ名を正しく入力してください',
            isError:false
        }
    },
    computed:{
        isLoggedIn(){
            return this.$store.state.isLoggedIn
        }
    },
    methods:{
        checkUserId:async function(){
            if(this.userId === this.$store.state.userId){
                this.isError = false
                await this.$store.dispatch('DELETE_MY_ACCOUNT')
                await this.makePopup(['ありがとうざいました','またのご利用お待ちしております'],6000)
            } else {
                this.isError = true
            }
        }
    }
};
</script>
<style scoped>
.delete-wrapper{
    padding:50px;
    min-height:100vh;
}
p{
    color:#4b4b4b;
    font-size:1.5em;
    font-weight:bold;
}
img{
    width: 50px;
    position: relative;
    top: 13px;
    margin-right: 10px;
}
.message-body{
    padding: 10px;
    line-height: 1.5em;
    font-size: 1.3em;
    /* font-weight: bold; */
    color:#4b4b4b;
    margin-top:20px;
    margin-bottom:20px;
}
.list-item{
    margin-top:10px;
}
.input-wrapper{
    margin-top: 8px;
}
input[type="text"]{

    margin-right: 30px;
}
.else-comment{
    font-size:1.8em;
}
</style>