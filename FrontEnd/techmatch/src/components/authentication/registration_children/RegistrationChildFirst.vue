<template>
  <div class="wrapper">
    <!-- <ValidationObserver>
      <form class="form" @submit.prevent="onSubmit"> -->
    <ValidationObserver v-slot="{ handleSubmit }">
      <form
        class="form registration-form"
        @submit.prevent="handleSubmit(onSubmit)"
      >
        <div class="registration-title">
          登  録
        </div>
        <div class="use-restriction">
          <label
            class="notice-button"
            @click="()=>{isConfirmNoticesNow=true}"
          >利用規約</label>，
          <label
            class="notice-button"
            @click="()=>{isConfirmPrivacyPolicyNow=true}"
          >プライバシーポリシー</label>を<strong>必ず</strong>ご確認の上ご登録をお願いします。
        </div>
        <validation-provider
          v-slot="{ errors }"
          mode="lazy"
          rules="required|alpha_num|max:50"
        >
          <p class="input-p">
            <label class="label">ユーザ名*</label>
            <input
              v-model="form.userId"
              class="input"
              type="text"
              placeholder="tanaka"
              @change="confirmIsUserIdExist"
            >
          </p>
          <div
            v-if="existUserId!==''"
            class="error-list"
          >
            {{ existUserId }}はすでに存在します
          </div>
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
          rules="required|email"
        >
          <p class="input-p">
            <label class="label">メールアドレス*</label>
            <input
              v-model="form.mailAddress"
              class="input"
              type="text"
              placeholder="test@example.ocm"
            >
          </p>
          <!-- @change="confirmIsMailAddressExist" -->
          <div
            v-if="existMailAddress!==''"
            class="error-list"
          >
            {{ existMailAddress }}はすでに登録されています
          </div>
          <div
            v-for="(error, i) in errors"
            :key="`errors${i}`"
            class="error-list"
          >
            {{ error }}
          </div>
        </validation-provider>
        <ValidationProvider
          v-slot="{ errors }"
          mode="lazy"
          :rules="{
            required: true,
            regex:/^[a-zA-Z0-9!#$%'()+-]+$/,
            min:6,
          }"
          vid="confirmation"
        >
          <p class="input-p">
            <label class="label">パスワード１*</label>
            <input
              v-model="form.password"
              class="input"
              type="password"
            >
          </p>
          <div
            v-for="(error, i) in errors"
            :key="`errors${i}`"
            class="error-list"
          >
            {{ error }}
          </div>
        </ValidationProvider>
        <ValidationProvider
          v-slot="{ errors }"
          mode="lazy"
          rules="required|confirmed:confirmation|min:6"
        >
          <p class="input-p">
            <label class="label">パスワード２*</label>
            <input
              v-model="pass1"
              class="input"
              type="password"
            >
          </p>
          <div
            v-for="(error, i) in errors"
            :key="`errors${i}`"
            class="error-list"
          >
            {{ error }}
          </div>
        </ValidationProvider>
        <div class="buttons-wrapper left">
          <div
            class="button cancel is-medium"
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
            確認
          </button>
        </div>
      </form>
    </ValidationObserver>
    <notices
      v-show="isConfirmNoticesNow"
      class="notices"
      @closeNotices="closeNotices"
    />
    <privacy-policy
      v-show="isConfirmPrivacyPolicyNow"
      class="notices"
      @closeNotices="()=>{isConfirmPrivacyPolicyNow=false}"
    />
  </div>
</template>
<script>import { ValidationObserver,ValidationProvider, extend } from 'vee-validate';
import { required,email,min,max,alpha_num,confirmed,regex} from 'vee-validate/dist/rules';
import client from '@/api/client'
import Notices from './Notices'
import PrivacyPolicy from './PrivacyPolicy'
extend('min',{
    ...min,
    message: '6文字以上で入力してください'}
)
extend('min8',{
    ...min,
    message: '8文字以上で入力してください'}
)
extend('max',{
    ...max,
    message: '50文字以内で入力してください'}
);
extend('email',{
    ...email,
    message: '形式が不正です'}
);
extend('alpha_num', {
    ...alpha_num,
    message: '半角英数字を入力してください'
})
extend('required', {
    ...required,
    message: '必須です'
})
extend('confirmed', {
    ...confirmed,
    message: 'パスワード1とパスワード2を同じにしてください'
})
extend('regex', {
    ...regex,
    message: '半角英数字 !#$%\'()+-を使用してください'
})

export default {
    name:'RegistrationChildFirst',
    components: {
        ValidationObserver,
        ValidationProvider,
        Notices,
        PrivacyPolicy
    },
    data:function(){
        return {
            pass1:'',
            isCancel:false,
            form:{},
            existUserId:'',
            existMailAddress:'',
            isConfirmNoticesNow:false,
            isConfirmPrivacyPolicyNow:false,
        }
    },
    methods:{
        onSubmit:function(){
            if(this.existUserId==='' && this.existMailAddress===''){
                this.$emit('fromRegistrationFirst',this.form)
            }
        },
        cancel:function(){
            this.$emit('cancel')
        },
        confirmIsUserIdExist:function(){
            if(this.form.userId.length<6)return
            client
                .get('/api/user/isUserIdExist/'+this.form.userId)
                .then(result=>{
                    if(result.data===true){
                        this.existUserId=this.form.userId
                    }else{
                        this.existUserId=''
                    }
                })
                .catch(err=>console.log(err.response))
        },
        confirmIsMailAddressExist:function(){
            client
                .get('/api/user/isMailAddressExist/'+this.form.mailAddress)
                .then(result=>{
                    if(result.data===true){
                        this.existMailAddress=this.form.mailAddress
                    }else{
                        this.existMailAddress=''
                    }
                })
                .catch(err=>console.log(err.response))
        },
        closeNotices:function(){
            this.isConfirmNoticesNow = false
        }
    }
}
</script>
<style scoped>
.registration-title{
    text-align: center;
    font-size: 1.8em;
    margin-bottom: 20px;
}
.use-restriction{
  margin-bottom:20px;
}
.registration-form{
    background:#f3f2f2;
    padding: 50px;
    border-radius: 5px;
    text-align:left;
    margin-bottom: 20px;
}
.error-list{
    margin-bottom:20px;
    color:red;
}
.notice-button{
    cursor: pointer;
    text-decoration: underline;
}
label{
    color: #2d2c2cc9;
}
.input-p{
    margin-bottom:10px;
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
