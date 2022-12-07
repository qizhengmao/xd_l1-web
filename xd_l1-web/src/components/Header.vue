<template>
  <div>
    <span>王小虎</span>
    <el-dropdown>
      <el-icon style="margin-left: 5px; margin-right: 8px; margin-top: 1px" @click="aa">
        <ArrowDown />
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
import {  ArrowDown } from '@element-plus/icons-vue';
import emitter from "@/utils/eventbus.ts"
import {ref} from "vue"; //引用刚刚新建的utils文件

const menuL = ref<Boolean>(false)	// 建立一个menuL
const cpLeft = () => {	// head里面的图标点击按钮
  menuL.value = !menuL.value	//首先把menuL取反
  menuL.value ? head_icon.value = 'Expand' : head_icon.value = 'Fold'	//
  emitter.emit("sayInfo",menuL.value)	//通过mitt的emit把menuL传递出去，sayInfo传参名称
}

const toUser = () => {
  console.log("toUser")
}
const logOut = () => {
  console.log("logOut")
}

</script>

<style scoped>

</style>