<template>
  <div class="wrapper-parent">
    本登録を実施<br>{{ id }}
  </div>
</template>
<script>
import * as action_type from '@/store/action-type'
export default {
    name:'RegistartionParent',
    components:{
    },
    data:function(){
        return {
            id:'',
            result:{}
        }
    },
    mounted:async function(){
        this.id = this.$route.query.id
        // 本登録を実施
        await this.$store.dispatch(action_type.MAIN_REGISTRATION,this.id)
            .then(()=>{
                this.makePopup(['登録が完了しました。','右上のアイコンからログインができます'],6000)
                this.$router.push({name:'home'},()=>{})
            })
            .catch((err)=>{
                this.makePopup(['登録に失敗しました。',err[0]],6000)
                this.$router.push({name:'home'},()=>{})
            })
    },
    methods:{
    }
}
</script>

<style scoped>
.wrapper-parent{
    min-height:100vh;
    padding:10px;
}
</style>
