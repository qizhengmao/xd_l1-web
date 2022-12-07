<template>
      <el-menu
          style="height: 100vh"
          active-text-color="#ffd04b"
          background-color="#545c64"
          class="el-menu-vertical-demo"
          default-active="/Home"
          text-color="#fff"
          :collapse="collapse"
          :collapse-transition="false"
      >
        <button @click="isCollapse">aaa</button>
        <el-menu-item index="/Home">
          <el-icon ><HomeFilled /></el-icon>
          <span>首页</span>
        </el-menu-item>
        <el-menu-item index="/One">
          <el-icon><Flag /></el-icon>
          <span>待定一</span>
        </el-menu-item>
        <el-menu-item index="/Tow">
          <el-icon><Opportunity /></el-icon>
          <span>待定二</span>
        </el-menu-item>
      </el-menu>
</template>
<script setup>
import { HomeFilled,Opportunity,Flag } from '@element-plus/icons-vue'
import {ref} from "vue";
let collapse = ref(false);
let a = ref(1);
const isCollapse = () => {
  collapse.value = !collapse.value;
  console.log(collapse.value)
  return{
    collapse,
    isCollapse
  }
}
</script>

<style scoped>

</style>