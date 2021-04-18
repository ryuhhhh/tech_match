import client from './client'

export default {
    registration:(userInfo)=>{
        return new Promise((resolve,reject)=>{
            client
                .post('api/auth/pre-registration',userInfo)
                .then((result)=>{resolve(result)})
                .catch(err=>{reject(err.response.data)})
        })
    },
    mainRegistration:(id)=>{
        return new Promise((resolve,reject)=>{
            client
                .post('api/auth/registration?id='+id)
                .then((result)=>{resolve(result)})
                .catch(err=>{reject(err.response.data)})
        })
    },
    login:(userInfo)=>{
        return new Promise((resolve,reject)=>{
            client
                .post('api/auth/login',userInfo)
                .then((result)=>{resolve(result)})
                .catch(err=>{reject(new Error(err.response.data[0]))})
        })
    },
    deleteMyAccount:()=>{
        return new Promise((resolve,reject)=>{
            client
                .post('api/user/mydelete')
                .then((result)=>{resolve(result)})
                .catch(err=>{reject(new Error(err.response.data[0]))})
        })
    }
}
