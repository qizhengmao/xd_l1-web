<template>
  <div id="app">
    <router-view/>
  </div>
</template>
<script>
export default {
  name: 'App',
  components: {},
  data(){
    return {
      user: JSON.parse(sessionStorage.getItem('CurUser')),
    }
  },
  watch:{
    '$store.state.menu': {
      handler(val,old) {
        console.log("111")
        if (!old && this.user && this.user) {
          console.log("222")
          this.$store.commit("setMenu",val)
        }
      },
      immediate: true
    },
  }
}
</script>
<style>
#app{
  overflow: scroll;
  height: 100vh;
}
</style>