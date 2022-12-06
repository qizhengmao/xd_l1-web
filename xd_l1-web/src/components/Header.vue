<template>
  <div>
    <span>王小虎</span>
    <el-dropdown>
      <el-icon style="margin-left: 5px; margin-right: 8px; margin-top: 1px">
        <ArrowDown/>
      </el-icon>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item @click="toUser">个人中心</el-dropdown-item>
          <el-dropdown-item @click="logOut">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>
</template>

<script setup>
import { ArrowDown } from '@element-plus/icons-vue';
const toUser = () => {
  console.log("toUser")
}
const logOut = () => {
  console.log("logOut")
}

</script>

<style scoped>

</style>