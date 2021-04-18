<template>
  <div>
    <div class="wrapper">
      <div class="confirm-message">
        <!-- <img src="./../../../assets/caution.svg" /> -->
        下記の内容で募集をかけます。これ以降、編集・更新・取止めができません。よろしいでしょうか。
      </div>
      <div class="buttons-wrapper">
        <button
          class="button cancel"
          @click="submit(false)"
        >
          キャンセル
        </button>
        <button
          class="button is-success "
          @click="submit(true)"
        >
          OK
        </button>
      </div>
    </div>
    <div class="modal-cover" />
  </div>
</template>

<script>
export default {
    name: 'ConfirmModalHeader',
    methods:{
        submit:function(bool){
            this.$emit('fromConfirmModalHeader',bool)
        }
    }
};
</script>
<style scoped>
.wrapper{
    position: fixed;
    top: 0px;
    color: #898571;
    left: 0px;
    width: 100%;
    font-size: 1.5em;
    height: 115px;
    /* background: #fa855e; */
    background: #7e867a;
    text-align: center;
    z-index:99;
    padding:10px;
}
.confirm-message{
    color: #f3f2f2;
    font-size:1.1em;
    margin-bottom:5px;
}
img{
    width:50px;
}
.button.cancel{
    margin-right: 50px;
}
.modal-cover{
    width: 100%;
    height: 100%;
    position: fixed;
    background: transparent;
    z-index: 10;
    left: 0px;
    top: 100px;
}
</style>