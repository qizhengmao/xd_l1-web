<template>
  <div>
    <el-dropdown>
      <el-icon style="margin-right: 8px; margin-top: 1px"
      ><setting
      /></el-icon>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item>查看</el-dropdown-item>
          <el-dropdown-item>新增</el-dropdown-item>
          <el-dropdown-item>删除</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
    <span>王小虎</span>
  </div>
</template>

<script>
export default {
  name: "Header"
}
</script>

<style scoped>

</style>