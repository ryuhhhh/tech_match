import client from './client'
export default{
    registerRequirement:(args)=>{
        return new Promise((resolve,reject)=>{
            let fd = new FormData()
            let config = {
                headers: {
                    'content-type': 'multipart/form-data',
                },
            }
            fd.append('images',args.imageInfo[0])
            fd.append('images',args.imageInfo[1])

            client
                .post('/api/requirement/require',args.requirementAndSKills)
                .then((result)=>{
                    fd.append('requirementId',result.data.requirementModel.id)
                    client
                        .post('/api/requirement/require-images',fd,config)
                        .then(()=>resolve(result))
                        .catch(err=>reject(new Error(err.response)))
                })
                .catch(err=>{reject(new Error(err.response))})
        })
    },
    getRequirementList:function(args){
        return new Promise((resolve,reject)=>{
            client
                .post('/api/requirement/getList',args.condition,{
                    params: {
                        page:args.pageable.page,
                        size:args.pageable.size,
                        sort:args.pageable.sort
                    }
                }
                )
                // listSearchModel
                .then(result=>resolve(result))
                .catch(err=>reject(new Error(err.response)))
        })
    },
    getRequirement:function(id){
        return new Promise((resolve,reject)=>{
            client
                .post('/api/requirement/get/'+id)
                .then(result=>resolve(result))
                .catch(err=>reject(new Error(err.response)))
        })
    },
    getApplicantsInfo:function(requirementId){
        return new Promise((resolve,reject)=>{
            client
                .post('/api/requirement/applicant',requirementId)
                .then(result=>resolve(result))
                .catch(err=>reject(new Error(err.response)))
        })
    },
    desideApplicant:function(applicationKey){
        return new Promise((resolve,reject)=>{
            client
                .post('/api/requirement/decide-applicant',applicationKey)
                .then(result=>resolve(result))
                .catch(err=>reject(new Error(err.response)))
        })
    }
}