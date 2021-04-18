<template>
  <div class="wrapper">
    <ValidationObserver v-slot="{ handleSubmit }">
      <form
        class="form"
        @submit.prevent="handleSubmit(onSubmit)"
      >
        <validation-provider
          v-slot="{ errors }"
          mode="lazy"
          rules="max:100"
        >
          <p class="input-p">
            <label class="label">twitter</label>
            <input
              v-model="form.twitterAccount"
              class="input"
              type="text"
              placeholder="@example"
            >
          </p>
          <div
            v-for="(error, i) in errors"
            :key="`errors${i}`"
            class="error-list"
          >
            {{ error }}
          </div>
        </validation-provider>
        <validation-provider
          v-slot="{ errors }"
          mode="lazy"
          name="email"
          rules="max:100"
        >
          <p class="input-p">
            <label class="label">facebook</label>
            <input
              v-model="form.facebookAccount"
              class="input"
              type="text"
              placeholder="@example"
            >
          </p>
          <div
            v-for="(error, i) in errors"
            :key="`errors${i}`"
            class="error-list"
          >
            {{ error }}
          </div>
        </validation-provider>
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
            次へ
          </button>
        </div>
      </form>
    </ValidationObserver>
  </div>
</template>
<script>import { ValidationObserver,ValidationProvider, extend } from 'vee-validate';
import { max} from 'vee-validate/dist/rules';
extend('max',{
    ...max,
    message: '100文字以内で入力してください'}
)
export default {
    name:'RegistrationChildFirst',
    components: {
        ValidationObserver,
        ValidationProvider
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
                this.$emit('fromRegistrationSecondBack',this.form)
            }else{
                this.$emit('fromRegistrationSecond',this.form)
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
