<template>
  <el-container class="layout-container-demo" style="height: 100vh">
      <el-aside width="200px" style="height: 100%" >
        <el-scrollbar>
          <Aside></Aside>
        </el-scrollbar>
      </el-aside>
    <el-container style="height: 100%;">
      <el-header style="text-align: right; font-size: 12px; padding-left: 0px;border-bottom: rgba(169,169,169,0.3) 1px solid">
        <div style="display: flex; margin-top:20px">
          <div style="font-size: 20px">
            <component  class="xxx" :is="Fold" ></component>
          </div>
<!--          <el-icon style="font-size: 20px" >-->
<!--            <Fold />-->
<!--          </el-icon>-->
          <div style="flex: 1; text-align: center; font-size: 34px; margin-top: -15px">
            <span>欢迎来到仓库管理系统</span>
          </div>
          <div class="toolbar">
            <Header></Header>
          </div>
        </div>
      </el-header>
      <el-main>
        <Main></Main>
      </el-main>
    </el-container>
  </el-container>
  <menu type="Message"></menu>
</template>

<script setup>
import { Fold } from '@element-plus/icons-vue';
import Aside from "@/components/Aside";
import Header from "@/components/Header";
import Main from "@/components/Main";


</script>

<style scoped>

.layout-container-demo .el-menu {
  border-right: none;
}
.layout-container-demo .el-main {
  padding: 0;
}
.layout-container-demo .toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;
}
</style>