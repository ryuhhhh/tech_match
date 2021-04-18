import Vue from 'vue'
import App from './App.vue'
import router from './router/index'
import store from './store/index'
import util from '@/common/util'
import client from '@/api/client'
import './mystyles.scss'
Vue.mixin(util)
Vue.config.productionTip = false

new Vue({
    router,
    store,

    // 起動時にローカルストレージにトークンがあるか確認
    async created(){
        await store.dispatch('GET_MASTER_TABLES')
        // 認証情報を取得する
        let token = localStorage.getItem('token')
        let userId = localStorage.getItem('userId')
        // 認証情報を保存する
        if(token!==null && token!=='' && userId !==null && userId !==''){
            // サーバーに確認する
            let isExpired = true
            await client.post('/api/auth/tokenTest',token).then(result=>isExpired=result.data)
                .catch(()=>{isExpired===true})
            // ログイン処理を行う
            if(!isExpired){
                await store.commit('MUTATE_COME_BACK',{token:token,userId:userId})
            }
            // ログアウト処理を行う
            else{
                await store.commit('MUTATE_LOGOUT')
            }
        }
    },
    render: h => h(App)
}).$mount('#app')