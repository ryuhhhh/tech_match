import Vue from 'vue'
import Vuex from 'vuex'
import state from './state'
import getters from './getters'
import actions from './actions'
import mutations from './mutations'
Vue.use(Vuex)

// devtoolを使用
Vue.config.devtools = true
export default new Vuex.Store({
    state,
    mutations,
    actions,
    getters
})
