export default{
    masterTables:{
        requirementStatues:[], /* 募集状態一覧*/
        skillLevels:[],/*スキルレベル一覧*/
        skillJenres:[],/*スキルジャンル*/
        frontEndSkills:[],/*フロントエンドスキル*/
        backEndSkills:[],/*バックエンドスキル*/
        infraSkills:[],/*インフラスキル*/
        mlSkills:[],/*機械学習スキル*/
        nativeAppSKills:[]/*ネイティブアプリスキル*/
    },
    isAuthenticated:false,
    userId:null,
    isLoading:false,
    isLoggedIn:false,
    token:null,
    isPopup:false,
    popupMessage:[]
}