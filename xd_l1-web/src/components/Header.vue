<template>
  <div style="display: flex;line-height: 60px">
    <div style="margin-top:3px;cursor:pointer">
      <i :class="isIcon" style="font-size: 20px" @click="collapse"></i>
    </div>
    <div style="flex: 1;text-align: center;font-size: 34px">
      <span>欢迎来到仓库管理系统</span>
    </div>
    <el-dropdown>
      <span>{{user.name}}</span>
      <i class="el-icon-caret-bottom" style="margin-left:5px "></i>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item @click.native="toUser">个人中心</el-dropdown-item>
        <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "Header",
  data(){
    return {
      confirm_disabled:false,
      user : JSON.parse(sessionStorage.getItem('CurUser')),
      isLogin : sessionStorage.getItem('isLogin ')
    }
  },
  props: {
    isIcon: String
  },
  methods: {
    toUser() {
      this.$router.push("/Home")
    },
    logout() {
      this.$confirm('您确定要退出登录吗？','提示',{
        confirmButtonText:'确定',
        type:'warning',
        center: true,
      })
          .then(() => {
            this.$message({
              type:'success',
              message:'退出登录成功'
            })
            sessionStorage.clear()
            this.$router.push("/")
          })
          .catch(() => {
            this.$message({
              type:'info',
              message:'您已取消退出'
            })
          })

    },
    collapse() {
      this.$emit("doCollapse")
    },
  }
}
</script>

<style scoped>

</style>