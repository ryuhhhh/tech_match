import Login from '@/components/authentication/login/login'
import RequirementList from '@/components/requirement/RequirementList'
import RequirementSpecification from '@/components/requirement/Specification/RequirementSpecification'
import MyRequirementSpecification from '@/components/requirement/Specification/MyRequirementSpecification'
import RequirementParent from '@/components/requirement/RequirementParent'
import RequirementSucceeded from '@/components/requirement/RequirementSucceeded'
import RegistrationParent from '@/components/authentication/RegistrationParent'
import MainRegistration from '@/components/authentication/MainRegistration'
import DeleteAnAccount from '@/components/authentication/delete/delete'
import Mypage from '@/components/user/mypage'
import UserPage from '@/components/user/userpage'
import about from '@/components/about/about'
import inquiry from '@/components/inquiry/inquiry'

import PrivacyPolicy from '@/components/restrictionAndPolicy/PrivacyPolicy'
import notices from '@/components/restrictionAndPolicy/Notices'
import store from '@/store/index'
import util from '@/common/util'
import client from '@/api/client'
export default[
    {
        path: '/',
        name: 'home',
        component: RequirementList
    },
    {
        path: '/registration',
        name: 'registration',
        component: RegistrationParent
    },
    {
        path: '/main-registration',
        name: 'MainRegistration',
        component: MainRegistration,
        beforeEnter:async (to,from,next)=>{
            let id = to.query.id
            if(id !== null && id !== void 0){
                next()
            }else{
                next(false)
            }
        }
    },
    {
        path: '/login',
        name: 'login',
        component: Login
    },
    {
        path: '/RequirementList',
        name: 'RequirementList',
        component: RequirementList
    },
    {
        path: '/RequirementSpecification',
        name: 'RequirementSpecification',
        component: RequirementSpecification,
        beforeEnter:async (to,from,next)=>{
            let id = to.query.id
            if(id !== null && id !== void 0){
                let requirementAndSkillsModel = null
                let isAdoption = false
                // 募集情報を取得する
                await store
                    .dispatch('GET_REQUIREMENT',id)
                    .then(result=>requirementAndSkillsModel = result.data)
                // マッチ状態を取得する
                await store
                    .dispatch('CONFIRM_IS_MATCHED',id)
                    .then(result=>isAdoption = result.data)
                if(requirementAndSkillsModel !== null){
                    to.params.requirementAndSkillsModel = requirementAndSkillsModel
                    to.params.isAdoption = isAdoption
                    next()
                }else{
                    next(false)
                }

            }else{
                next(false)
            }
        }
    },
    {
        path: '/MyRequirementSpecification',
        name: 'MyRequirementSpecification',
        component: MyRequirementSpecification,
        beforeEnter:async (to,from,next)=>{
            let id = to.query.id
            if(id !== null && id !== void 0){
                let requirementAndSkillsModel = null
                await store
                    .dispatch('GET_REQUIREMENT',id)
                    .then(result=>requirementAndSkillsModel = result.data)
                if(requirementAndSkillsModel !== null){
                    let userId = null
                    await client.post('/api/auth/confirmUserId',localStorage.getItem('token'))
                        .then(result=>{userId=result.data[0]})
                        .catch(()=>{userId===null})
                    if(userId !== null && requirementAndSkillsModel.requirementModel.userId === userId){
                        to.params.requirementAndSkillsModel = requirementAndSkillsModel
                        next()
                    }else{
                        util.methods.makePopup(['あなたは、募集者ではありません。','または未ログイン状態です。'],3000)
                        next(false)
                    }
                }else{
                    next(false)
                }

            }else{
                next(false)
            }
        }
    },
    {
        path: '/requirement-parent',
        name: 'RequirementParent',
        component: RequirementParent,
        // TODO ガード時にポップアップを表示
        beforeEnter: (to, from, next) => {
            if (localStorage.getItem('userId')!=='' && localStorage.getItem('userId')!==null) {
                next()
            } else {
                next(false)
            }
        }
    },
    {
        path: '/requirement-succeeded',
        name: 'RequirementSucceeded',
        component: RequirementSucceeded,
        // TODO ガード時にポップアップを表示
        beforeEnter: (to, from, next) => {
            if (to.params.requirementModel!== void 0 && localStorage.getItem('userId')!=='' && localStorage.getItem('userId')!==null) {
                next()
            } else {
                next(false)
            }
        }
    },
    {
        path: '/mypage',
        name: 'Mypage',
        component: Mypage,
        // TODO ガード時にポップアップを表示
        beforeEnter: (to, from, next) => {
            if (localStorage.getItem('userId')!=='' && localStorage.getItem('userId')!==null) {
                next()
            } else {
                next(false)
            }
        }
    },
    {
        path: '/userpage',
        name: 'UserPage',
        component: UserPage,
        // TODO ガード時にポップアップを表示
        beforeEnter: async (to, from, next) => {
            // クエリパラメータからユーザIDを取得
            let userId = to.query.userId
            if(userId === '' || userId === undefined || userId === null){
                next(false)
            } else {
                // ユーザ情報を取得
                store
                    .dispatch('GET_USER_ALL_INFO',userId)
                    .then(result=>{
                        to.params.techMatchUserAllInfoModel = result.data
                        next()
                    })
                    .catch(()=>{next(false)})
            }
        }
    },
    {
        path: '/about',
        name: 'About',
        component: about
    },
    {
        path: '/inquiry',
        name: 'Inquiry',
        component: inquiry
    },
    {
        path: '/notices',
        name: 'Notices',
        component: notices
    },
    {
        path: '/notices',
        name: 'Notices',
        component: notices
    },
    {
        path: '/privacy-policy',
        name: 'PrivacyPolicy',
        component: PrivacyPolicy
    },
    {
        path: '/delete-ur-account',
        name: 'DeleteUrAccount',
        component: DeleteAnAccount
    }
]