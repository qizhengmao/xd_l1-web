<template >
  <el-container style="height: 100%; border: 1px solid #eee">
    <el-aside :width="aside_width" style="height: 100%;background-color: rgb(238, 241, 246);margin-left: -1px;margin-top: -1px">
      <Aside :isCollapse="isCollapse"></Aside>
    </el-aside>

    <el-container style="height: 100%;margin-top: -1px">
      <el-header style="text-align: right; font-size: 12px;border-bottom: rgb(168,168,168,0.3) 1px solid">
        <Header @doCollapse="doCollapse" :isIcon="isicon"></Header>
      </el-header>

      <el-main style="height: 100%;">
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>
<script>
import Aside from "./Aside";
import Header from "./Header";
export default {
  name: "Index",
  components:{Aside,Header},
  data(){
    return {
      isCollapse:false,
      aside_width:"200px",
      isicon:'el-icon-s-fold'
    }
  },
  methods:{
    doCollapse(){
      this.isCollapse = !this.isCollapse
      if (!this.isCollapse){
        this.aside_width = '200px'
        this.isicon = 'el-icon-s-fold'
      }else {
        this.aside_width = '64px'
        this.isicon = 'el-icon-s-unfold'
      }
    }
  }

}
</script>

<style scoped>
</style>