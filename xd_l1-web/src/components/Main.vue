<template>
  <el-scrollbar>
    <el-table :data="tableData">
      <el-table-column prop="date" label="日期" width="140" />
      <el-table-column prop="name" label="姓名" width="120" />
      <el-table-column prop="address" label="地址" />
    </el-table>
  </el-scrollbar>
</template>

<script setup>
import {ref} from "vue";
const item = {
  date: '2016-05-02',
  name: '王小虎',
  address: '上海市普陀区金沙江路 1518 弄',
}
const tableData = ref(Array.from({ length: 20 }).fill(item));
</script>

<style scoped>

</style>