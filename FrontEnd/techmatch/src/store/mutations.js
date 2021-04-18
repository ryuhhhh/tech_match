import * as MUTATION_TYPES from './mutation-type'
import client from '@/api/client'
export default{
    [MUTATION_TYPES.MUTATE_LOGOUT](state){
        client.defaults.headers.common['Authorization'] = null
        state.token = null
        state.isLoggedIn = false
        state.userId = null
        // localStorageの認証情報を削除する
        localStorage.removeItem('token')
        localStorage.removeItem('userId')
    },
    [MUTATION_TYPES.MUTATE_AUTHENTICATION_INFO](state,{token,userId}){
        if(token!==null && token!==undefined){
            client.defaults.headers.common['Authorization'] = 'Bearer ' + token
            state.token = token
            state.isLoggedIn = true
            state.userId = userId
            // localStorageに認証情報を置く
            localStorage.setItem('token',token)
            localStorage.setItem('userId',userId)
        }
    },
    [MUTATION_TYPES.MUTATE_COME_BACK](state,{token,userId}){
        if(token!==null && token!==undefined){
            client.defaults.headers.common['Authorization'] = 'Bearer ' + token
            state.token = token
            state.isLoggedIn = true
            state.userId = userId
        }
    },
    [MUTATION_TYPES.UPDATE_MASTER_TABLE](state,tables){
        // 各テーブル情報に入れる
        state.masterTables.requirementStatues = tables.requirementStatusEntityList
        state.masterTables.skillLevels = tables.skillLevelEntityList
        state.masterTables.skillJenres = tables.skillJenreCodeEntityList
        state.masterTables.frontEndSkills = tables.fronEndSkillCodeEntity
        state.masterTables.backEndSkills = tables.backEndSkillCodeEntity
        state.masterTables.infraSkills = tables.infraSkillCodeEntity
        state.masterTables.mlSkills = tables.mlSkillCodeEntity
        state.masterTables.nativeAppSKills = tables.nativeApplicationSkillCodeEntity
    },
}