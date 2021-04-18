<template>
  <div class="wrapper">
    <requirement-list-search-bar
      @fromRequirementListSearchBar="fromRequirementListSearchBar"
    />
    <div class="requirement-cards">
      <requirement-card
        v-for="requirementSkillEntity in requirementSkillEntityList"
        :key="requirementSkillEntity.id"
        :requirement-skill-entity="requirementSkillEntity"
      />
    </div>
  </div>
</template>

<script>
import * as action_type from '@/store/action-type'
import RequirementListSearchBar from './RequirementListChildren/RequirementListSearchBar'
import RequirementCard from './RequirementListChildren/RequirementCard'
export default {
    name: 'RequirementList',
    components: {
        RequirementListSearchBar,
        RequirementCard
    },
    data: () => {
        return {
            formerScrollLimit:0,
            requirementSkillEntityList:[],
            condition:{
                fullTextSearchWords: [],
                skillMap: {}
            },
            isSearchReachedLast:false, // 最後まで見たかの制御用
            pageable:{
                page:0,
                size:6,
                sort:'updateDate,desc'
            }
        }
    },
    mounted:function(){
        // ユーザの応募情報を取得する
        this.getRequirementList()
        window.addEventListener('scroll',this.handleScroll,false)
    },
    destroyed:function(){
        this.deleteScrollEvent()
        this.deleteFormerScrollEvent()
    },
    methods:{
        addScrollEvent:function(){
            window.addEventListener('scroll',this.handleScroll,{passive:true})
        },
        deleteScrollEvent:function(){
            window.removeEventListener('scroll',this.handleScroll,{passive:true})
        },
        addFormerScrollEvent:function(){
            window.addEventListener('scroll',this.handleFormerScroll,{passive:true})
        },
        deleteFormerScrollEvent:function(){
            window.removeEventListener('scroll',this.handleFormerScroll,{passive:true})
        },
        async handleFormerScroll(){
            if(window.innerHeight+window.scrollY >= this.formerLimit){
                window.scrollY = this.formerLimit
            }
        },
        async handleScroll(){
            // console.log(window.innerHeight,window.scrollY,document.body.clientHeight)
            // 表示高さ + スクロール量 >= 全体の高さ
            if(window.innerHeight+window.scrollY+10 >= document.body.clientHeight){
                this.formerScrollLimit = window.scrollY
                await this.addFormerScrollEvent()
                await this.deleteScrollEvent()
                this.pageable.page += 1
                // TODO これで最後まで取得したら何も行わなくする -> 今は空白の配列を取得している
                await this.getRequirementList()
                this.addScrollEvent()
            }
        },
        getRequirementListForInit:function(){
            this.isSearchReachedLast = false
            this.$store.dispatch(action_type.GET_REQUIREMENT_LIST,{condition:this.condition,pageable:this.pageable})
                .then(result => {this.requirementSkillEntityList=result.data.content})
        },
        getRequirementList:function(){
            if(!this.isSearchReachedLast){
                this.$store.dispatch(action_type.GET_REQUIREMENT_LIST,{condition:this.condition,pageable:this.pageable})
                    .then(result => {
                        if(result.data.content.length<=0){
                            this.isSearchReachedLast = true
                        }
                        this.requirementSkillEntityList=this.requirementSkillEntityList.concat(result.data.content)})
            }
        },
        fromRequirementListSearchBar:function({fullTextSearchWords,skillMap}){
            this.condition.fullTextSearchWords = fullTextSearchWords
            this.condition.skillMap = skillMap
            this.pageInit()
            this.getRequirementListForInit()
        },
        pageInit:function(){
            this.pageable.page = 0
            this.pageable.sort = 'updateDate,desc'
        }
    }
}
</script>
<style scoped>
.wrapper{
    z-index:2;
    min-height: 100vh;
}
</style>