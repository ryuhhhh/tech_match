import store from '@/store/index'
export default {
    methods: {
        makePopup: function(messages,milliseconds) {
            store.state.isPopup=true
            store.state.popupMessage=messages
            setTimeout(()=>{store.state.popupMessage='';store.state.isPopup=false},milliseconds)
        },
        convertObjectsToArray(skillsObject){
            let array = []
            for (let [skillKey, skillValue] of Object.entries(skillsObject)) {
                let skillObj = new Object()
                skillObj.skillCd = skillKey
                skillObj.skillLevelCd = skillValue
                array.push(skillObj)
            }
            return array
        },
        // TODO 最初からスキル名と一緒にサーバーから取得
        convertSkillCd2SkillName(skillCd){
            let allSkills = store.state.masterTables.frontEndSkills
                .concat(store.state.masterTables.backEndSkills)
                .concat(store.state.masterTables.infraSkills)
                .concat(store.state.masterTables.mlSkills)
                .concat(store.state.masterTables.nativeAppSKills)
            for(let skill of allSkills){
                if(skill.skillCd === skillCd){
                    return skill.name
                }
            }
            return '不明'
        }
    }
}