import * as ACTION_TYPES from './action-type'
import {GET_MASTER_TABLES,AUTHENTICATION,USER,REQUIREMENT,APPLICATION} from '@/api/index'
import * as MUTATION_TYPES from './mutation-type'
import router from '@/router/index'
export default {
    [ACTION_TYPES.GET_MASTER_TABLES]:({state,commit})=>{
        state.isLoading = true
        GET_MASTER_TABLES
            .getMasterTables()
            .then(result => {
                commit(MUTATION_TYPES.UPDATE_MASTER_TABLE,result.data.result)
                state.isLoading=false;
            })
            .catch(err=>{new Error(err)})
    },
    [ACTION_TYPES.REGISTRATION]:({state},userInfo)=>{
        state.isLoading = true
        return new Promise((resolve,reject)=>{
            AUTHENTICATION
                .registration(userInfo)
                .then(()=>{resolve()})
                .catch(err=>{reject(err)})
                .finally(()=>state.isLoading = false)
        })
    },
    [ACTION_TYPES.MAIN_REGISTRATION]:({state},id)=>{
        state.isLoading = true
        return new Promise((resolve,reject)=>{
            AUTHENTICATION
                .mainRegistration(id)
                .then((result)=>{resolve(result)})
                .catch(err=>{reject(err)})
                .finally(()=>state.isLoading = false)
        })
    },
    [ACTION_TYPES.LOGIN]:({state,commit},userInfo)=>{
        state.isLoading = true
        return new Promise((resolve,reject)=>{
            AUTHENTICATION
                .login(userInfo)
                .then(result=>{
                    commit(MUTATION_TYPES.MUTATE_AUTHENTICATION_INFO,{token:result.data.jwtToken,userId:userInfo.userId})
                    resolve()
                })
                .then(()=>{router.push({name:'home'},()=>{})})
                .catch(err=>{reject(new Error(err))})
                .finally(()=>state.isLoading = false)
        })
    },
    [ACTION_TYPES.GET_USER_ALL_INFO]:({state},userId)=>{
        state.isLoading = true
        return new Promise((resolve,reject)=>{
            USER
                .getUserAllInfo(userId)
                .then(result=>{
                    state.isLoading = false
                    resolve(result)
                })
                .catch(err=>{
                    state.isLoading = false
                    reject(new Error(err))})})
    },
    [ACTION_TYPES.GET_USER_REQUIREMENTS]:({state})=>{
        state.isLoading = true
        return new Promise((resolve,reject)=>{
            USER
                .getUserRequirements()
                .then(result=>{
                    state.isLoading = false
                    resolve(result)
                })
                .catch(err=>{
                    state.isLoading = false
                    reject(new Error(err))})})
    },
    [ACTION_TYPES.GET_USER_APPLICATIONS]:({state})=>{
        state.isLoading = true
        return new Promise((resolve,reject)=>{
            USER
                .getUserApplications()
                .then(result=>{
                    state.isLoading = false
                    resolve(result)
                })
                .catch(err=>{
                    state.isLoading = false
                    reject(new Error(err))})})
    },
    [ACTION_TYPES.UPDATE_USER_INFO]:function({state},{skills,userFrontModel}){
        state.isLoading = true
        return new Promise((resolve,reject)=>{
            USER
                .updateUserInfo({skills:skills,userFrontModel:userFrontModel})
                .then(result=>{
                    resolve(result)
                })
                .catch(err=>{
                    reject(new Error(err))})})
            .finally(()=>state.isLoading = false)
    },
    [ACTION_TYPES.UPDATE_USER_IMAGE]:({state},userImage)=>{
        state.isLoading = true
        return new Promise((resolve,reject)=>{
            USER
                .updateUserImage(userImage)
                .then(result=>{
                    state.isLoading = false
                    resolve(result)
                })
                .catch(err=>{
                    state.isLoading = false
                    reject(new Error(err))})})
    },
    [ACTION_TYPES.REGISTER_REQUIREMENTS]:({state},requirementAndSKills,imageInfo)=>{
        state.isLoading = true
        return new Promise((resolve,reject)=>{
            REQUIREMENT
                .registerRequirement(requirementAndSKills,imageInfo)
                .then(result=>{
                    state.isLoading = false
                    resolve(result)
                })
                .catch(err=>{
                    state.isLoading = false
                    reject(new Error(err))})})
    },
    [ACTION_TYPES.GET_REQUIREMENT_LIST]:({state},condition,pageable)=>{
        state.isLoading = true
        return new Promise((resolve,reject)=>{
            REQUIREMENT
                .getRequirementList(condition,pageable)
                .then(result=>{
                    state.isLoading = false
                    resolve(result)
                })
                .catch(err=>{
                    state.isLoading = false
                    reject(new Error(err))})})
    },
    [ACTION_TYPES.GET_REQUIREMENT]:({state},id)=>{
        state.isLoading = true
        return new Promise((resolve,reject)=>{
            REQUIREMENT
                .getRequirement(id)
                .then(result=>{
                    state.isLoading = false
                    resolve(result)
                })
                .catch(err=>{
                    state.isLoading = false
                    reject(new Error(err))})})
    },
    [ACTION_TYPES.GET_APPLICANTS_INFO]:({state},requirementId)=>{
        state.isLoading = true
        return new Promise((resolve,reject)=>{
            REQUIREMENT
                .getApplicantsInfo(requirementId)
                .then(result=>{
                    state.isLoading = false
                    resolve(result)
                })
                .catch(err=>{
                    state.isLoading = false
                    reject(new Error(err))})})
    },
    [ACTION_TYPES.CONFIRM_IS_MATCHED]:({state},requirementId)=>{
        state.isLoading = true
        return new Promise((resolve,reject)=>{
            APPLICATION
                .confirmIsMatched(requirementId)
                .then(result=>{
                    state.isLoading = false
                    resolve(result)
                })
                .catch(err=>{
                    state.isLoading = false
                    reject(new Error(err))})})
    },
    [ACTION_TYPES.APPLICATION]:({state},requirementId)=>{
        state.isLoading = true
        return new Promise((resolve,reject)=>{
            APPLICATION
                .application(requirementId)
                .then(result=>{
                    resolve(result)
                })
                .catch(err=>{
                    reject(new Error(err))})})
            .finally(()=>{
                state.isLoading = false
            })
    },
    [ACTION_TYPES.DESIDE_APPLICANT]:({state},applicationKey)=>{
        state.isLoading = true
        return new Promise((resolve,reject)=>{
            REQUIREMENT
                .desideApplicant(applicationKey)
                .then(result=>{
                    resolve(result)
                })
                .catch(err=>{
                    reject(new Error(err))})})
            .finally(()=>{
                state.isLoading = false
            })
    },
    [ACTION_TYPES.DELETE_MY_ACCOUNT]:({state,commit})=>{
        state.isLoading = true
        return new Promise((resolve,reject)=>{
            AUTHENTICATION
                .deleteMyAccount()
                .then(()=>{
                    commit(MUTATION_TYPES.MUTATE_LOGOUT)})
                .then(result=>{
                    resolve(result)
                })
                .catch(err=>{
                    reject(new Error(err))})})
            .finally(()=>{
                state.isLoading = false
            })
    },
}