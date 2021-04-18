import client from './client'

export default{
    getUserAllInfo:function(userId){
        return new Promise((resolve,reject)=>{
            client
                .get('/api/user/getInfo/'+userId)
                .then(result=>{resolve(result)})
                .catch(err=>reject(new Error(err.response)))
        })
    },
    updateUserInfo:function(allInfoToUpdateModel){
        return new Promise((resolve,reject)=>{
            client
                .post('/api/user/myupdate',allInfoToUpdateModel)
                .then(result=>{resolve(result)})
                .catch(err=>{reject(new Error(err.response))})
        })
    },
    updateUserImage:function(userImage){
        let config = {
            headers: {
                'content-type': 'multipart/form-data',
            },
        }
        let fd = new FormData();
        fd.append('userImage',userImage)
        return new Promise((resolve,reject)=>{
            client
                .post('/api/user/my-image-update',fd,config)
                .then(result=>{resolve(result)})
                .catch(err=>{reject(new Error(err.response))})
        })
    },
    getUserRequirements:function(){
        return new Promise((resolve,reject)=>{
            client
                .get('/api/user/requirement-list')
                .then(result=>{resolve(result)})
                .catch(err=>reject(new Error(err.response)))
        })
    },
    getUserApplications:function(){
        return new Promise((resolve,reject)=>{
            client
                .get('/api/applicant/list')
                .then(result=>{resolve(result)})
                .catch(err=>reject(new Error(err.response)))
        })
    }
}