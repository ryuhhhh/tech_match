import client from './client'

export default{
    getUserApplications:function(){
        return new Promise((resolve,reject)=>{
            client
                .get('/api/applicant/list')
                .then(result=>{resolve(result)})
                .catch(err=>reject(new Error(err.response)))
        })
    },
    application:function(requirementId){
        return new Promise((resolve,reject)=>{
            client
                .post('/api/applicant/application',requirementId)
                .then(result=>{resolve(result)})
                .catch(err=>reject(new Error(err.response)))
        })
    },
    confirmIsMatched:function(requirementId){
        return new Promise((resolve,reject)=>{
            client
                .post('/api/applicant/confirm-is-matched',requirementId)
                .then(result=>{resolve(result)})
                .catch(err=>reject(new Error(err.response)))
        })
    }
}