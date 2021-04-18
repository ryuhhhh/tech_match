<template>
  <transition name="top">
    <div
      v-if="isPopup"
      class="popup-wrapper"
      @click="close"
    >
      <div id="popup-left" />
      <div id="popup">
        <p
          v-for="message in messages"
          :key="message"
        >
          {{ message }}
        </p>
      </div>
    </div>
  </transition>
</template>
<script>
export default {
    name:'Popup',
    computed:{
        messages:function(){
            return this.$store.state.popupMessage
        },
        isPopup:function(){
            return this.$store.state.isPopup
        }
    },
    methods:{
        // TODO leaveが効かない
        close:function(){
            this.$store.state.isPopup=false
            this.$store.state.popupMessage=[]
        }
    }
}
</script>
<style scoped>

.popup-wrapper{
  display:flex;
  width:100%;
  text-align:right;
  position: fixed;
  z-index: 5;
}
#popup-left{
    width: 70%;
    height: 200px;
    display: inline-block;
    z-index: 100;
    opacity:0;
}
#popup{
    text-align: center;
    padding: 30px;
    font-size: 1.3em;
    color: #484e3d;
    font-weight: bold;

    width: 30%;
    height: 200px;
    background: #f3f2f2;
    z-index: 100;
    margin-top: 20px;
    margin-right: 20px;
    border-radius: 10px;
}
</style>

<style scoped>
.top-enter-active, .top-leave-active {
  transform: translate(0px, 0px);
  transition: transform 225ms cubic-bezier(0, 0, 0.2, 1) 0ms;
}

.top-enter, .top-leave-to {
  transform: translateY(-100vh) translateY(0px);
}
</style>