<template>
  <div class="wrapper">
    <form
      class="form"
      @submit.prevent="onSubmit"
    >
      <select-block />
      <div class="buttons-wrapper left">
        <button
          class="button is-light is-medium back"
          type="submit"
          @click="back"
        >
          戻る
        </button>
      </div>
      <div class="buttons-wrapper right">
        <button
          class="button is-success is-medium next"
          type="submit"
        >
          確認
        </button>
      </div>
    </form>
  </div>
</template>
<script>
import SelectBlock from './RegistrationThirdChild/SelectBlock.vue'
export default {
    name:'RegistrationChildFirst',
    components: {
        SelectBlock
    },
    data:function(){
        return {
            form:{},
            isBack:false
        }
    },
    methods:{
        onSubmit:function(){
            if(this.isBack){
                this.isBack=false
                this.$emit('fromRegistrationThirdBack',this.form)
            }else{
                this.$emit('fromRegistrationThird',this.form)
            }
        },
        back:function(){
            this.isBack=true
        }
    }
}
</script>
<style scoped>
.error-list{
    margin-bottom:20px;
    color:red;
}
label{
    color: #2d2c2cc9;
}
.input-p{
    margin-bottom:10px;
}
.form{
    margin-left: 30%;
    margin-right: 30%;
    margin-bottom: 20px;
    /* margin-top: 20px; */
}
.buttons-wrapper{
    padding-top: 20px;
}
.buttons-wrapper.left{
    text-align: left;
    float: left;
}
.buttons-wrapper.right{
    text-align: right;
}
</style>
