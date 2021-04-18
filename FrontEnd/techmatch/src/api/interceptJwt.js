import client from './client'
import state from '@/sotre/state'
export default{
    intercept:function(){
        if(state.token!=='' && state.token!==undefined && state.token!==null){
            client.interceptors.request.use((config) => {
                config.headers.Authorization = `Bearer ${config.token}`
                return config
            }, function (error) {
                return Promise.reject(error)
            })
        }
    }
}