<template>
  <div>
    <!-- TODO login時にはloginとregiを消し、logoutを付ける -->
    <div class="topbar">
      <div
        class="humburger-image"
        @click="openMenuBar"
      />
      <div class="title1">
        Tech Match
      </div>
      <div class="auth-tools">
        <div
          v-show="!isLoggedIn"
          class="auth-icon"
        >
          <router-link to="login">
            <img
              src="./../../assets/login.svg"
              class="login-icon"
            >
          </router-link>
        </div>
        <div
          v-show="!isLoggedIn"
          class="auth-icon"
        >
          <router-link to="registration">
            <img
              src="./../../assets/register.svg"
              class="register-icon"
            >
          </router-link>
        </div>
        <div
          v-show="isLoggedIn"
          class="auth-icon"
          @click="logout"
        >
          <img
            src="./../../assets/logout.svg"
            class="logout-icon"
          >
        </div>
      </div>
      <transition name="menu">
        <menu-bar
          v-if="show"
          class="menu-bar"
          @Close="closeMenuBar"
        />
      </transition>
      <div
        v-if="show"
        class="cover"
        @click="closeMenuBar"
      />
    </div>
  </div>
</template>

<script>
import * as mutation_type from '@/store/mutation-type'
import MenuBar from '@/components/common/MenuBar'
export default {
    name: 'TopBar',
    components: {
        MenuBar
    },
    data: () => {
        return {
            show:false,
        }
    },
    computed:{
        isLoggedIn:function(){
            return this.$store.state.isLoggedIn
        }
    },
    methods:{
        closeMenuBar:function(){
            this.show=false
        },
        openMenuBar:function(){
            this.show=true
        },
        logout:async function(){
            await this.$store.commit(mutation_type.MUTATE_LOGOUT)
            await this.makePopup(['ログアウトしました'],3000)
            this.$router.push({name:'home'},()=>{})
        }
    }
}
</script>

<style scoped>

.topbar{
  width:100%;
  height:100px;
  background-color:#819e47eb;
}
.humburger{
    height: 100px;
    width: 70px;
}
.icon{
  width:100px;
  height:100px;
}
.login-icon,.logout-icon{
  width: 45px;
}
.title1{
    color: #4b4b4b;
    display: inline-block;
    margin: 0px;
    position: absolute;
    width: 100%;
    top: 2%;
    text-align: center;
    font-size: 3.5em;
}
.auth-tools{
    text-align: right;
    padding-top:10px;
    position: absolute;
    width: 100%;

}
.auth-icon{
  width:75px;
  height: 75px;
  background-color:#f8d9d938;
  display: inline-block;
  border-radius: 40px;
  margin-right: 20px;
  padding: 15px;
}
.auth-icon:hover{
  transition:.3s ease-in-out;
  background-color:#f8d9d9a1;
}

.humburger-image{
    position: absolute;
    display: inline-block;
    background-image: url(../../assets/humburger.svg);
    background-size: 100px;
    background-repeat: no-repeat;
    width:100px;
    height:100px;
    z-index:5;
}
.menu-bar{
  z-index:20;
}
.cover{
    z-index:1;
    width: 100%;
    height: 100%;
    background-color: #6b686869;
    position: fixed;
    top: 0px;
    left: 0px;
}
</style>

<style scoped>
.menu-enter-active, .menu-leave-active {
  transform: translate(0px, 0px);
  transition: transform 225ms cubic-bezier(0, 0, 0.2, 1) 0ms;
}
.menu-enter,.menu-leave-to{
  transform: translateX(-30%) translateX(0px);
}
</style>