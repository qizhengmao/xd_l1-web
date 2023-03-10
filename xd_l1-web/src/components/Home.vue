<template>
  <div class="index">
    <div>
      <h3>欢迎您，尊敬的{{ user.name }},祝您新的一天工作愉快</h3>
      <br>
      <el-descriptions :column="3" border
      size="medium">
        <el-descriptions-item label="用户名" label-class-name="my-label" content-class-name="my-content">{{ user.name }}</el-descriptions-item>
        <el-descriptions-item label="账号">{{user.no}}</el-descriptions-item>
        <el-descriptions-item label="电话">{{ user.phone }}</el-descriptions-item>
        <el-descriptions-item label="年龄">{{ user.age }}</el-descriptions-item>
        <el-descriptions-item label="性别">
          <el-tag
              :type="user.sex === 1 ? 'primary' : 'success'"
              disable-transitions>{{user.sex === 1 ? '男' : '女'}}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="角色">
          <el-tag
              :type="user.roleId === 0 ? 'danger' : (user.roleId === 1 ? 'primary' : 'success')"
              disable-transitions>{{user.roleId === 0 ? '超级管理员' : (user.roleId === 1 ? '管理员' : '用户')}}</el-tag>
        </el-descriptions-item>
      </el-descriptions>
    </div>
    <el-divider></el-divider>
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="12" :xl="12" style="margin-bottom: 20px;">
        <Pays />
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="12" :xl="12" style="margin-bottom: 20px;">
        <Clients />
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" style="margin-bottom: 20px;">
        <Maps />
      </el-col>
    </el-row>
  </div>
</template>

<script>
import Pays from '../components/analyze/pay'
import Clients from '../components/analyze/client'
import Maps from '../components/analyze/maps'
export default {
  data(){
    return {
      user : JSON.parse(sessionStorage.getItem('CurUser')),
    }
  },
  components:{
    Pays,
    Clients,
    Maps,
  },
}
</script>

<style scoped>
.index{
  background: #F5F7F9;
  padding: 15px;
  box-sizing: border-box;
  width: 100%;
  min-height: 100%;
}
</style>