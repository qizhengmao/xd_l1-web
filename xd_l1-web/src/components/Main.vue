<template>
  <div>
    <div style="margin-bottom: 5px">
      <el-input v-model="name" placeholder="请输入姓名" suffix-icon="el-icon-search" style="width: 200px"
            @keyup.enter.native="loadPost"></el-input>
      <el-select v-model="sex" filterable placeholder="请选择性别" style="margin-left: 9px">
        <el-option
            v-for="item in sexs"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <el-button type="primary" style="margin-left: 9px" @click="loadPost">查询</el-button>
      <el-button type="success" @click="resetParam">重置</el-button>
      <el-button type="primary" style="margin-left: 9px" @click="add">新增</el-button>
      <!--新增按钮打开的输入框-->
      <el-dialog
          title="提示"
          :visible.sync="centerDialogVisible"
          width="30%"
          center
          :before-close="handleClose"
          >
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="账号" prop="no" style="width: 80%">
            <el-input v-model="ruleForm.no"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password" style="width: 80%">
            <el-input v-model="ruleForm.password"></el-input>
          </el-form-item>
          <el-form-item label="姓名" prop="name" style="width: 80%">
            <el-input v-model="ruleForm.name"></el-input>
          </el-form-item>
          <el-form-item label="年龄" prop="age" style="width: 80%">
            <el-input v-model="ruleForm.age"></el-input>
          </el-form-item>
          <el-form-item label="性别" prop="sex" style="width: 80%">
            <el-radio-group v-model="ruleForm.sex">
              <el-radio border label="1">男</el-radio>
              <el-radio border label="0">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="角色" prop="roleId" style="width: 90%">
            <el-radio-group v-model="ruleForm.roleId">
              <el-radio border label="1">管理员</el-radio>
              <el-radio border label="2">用户</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="电话" prop="phone" style="width: 80%">
            <el-input v-model="ruleForm.phone"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
    <el-button @click="centerDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="save">确 定</el-button>
  </span>
      </el-dialog>
    </div>
<!--    数据显示-->
    <el-table :data="tableData"
              v-loading="loading"
              height="675"
              style="width: 100%"
    :header-cell-style="{ background:'#e3f8f6',color: '#000000'}"
    border>
      <el-table-column prop="id" label="ID" width="60">
      </el-table-column>
      <el-table-column prop="no" label="账号" width="180">
      </el-table-column>
      <el-table-column prop="name" label="姓名" width="180">
      </el-table-column>
      <el-table-column prop="age" label="年龄" width="80">
      </el-table-column>
      <el-table-column prop="sex" label="性别" width="80">
        <template slot-scope="scope">
        <el-tag
            :type="scope.row.sex === 1 ? 'primary' : 'success'"
            disable-transitions>{{scope.row.sex === 1 ? '男' : '女'}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="roleId" label="角色" width="120">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.roleId === 0 ? 'danger' : (scope.row.roleId === 1 ? 'primary' : 'success')"
              disable-transitions>{{scope.row.roleId === 0 ? '超级管理员' : (scope.row.roleId === 1 ? '管理员' : '用户')}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="电话" width="180">
      </el-table-column>
      <el-table-column prop="operate" label="操作">
        <template slot-scope="scope">
          <el-button size="small" type="success" @click="mod(scope.row)">编辑</el-button>
          <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.id)" style="margin-left: 5px">
            <el-button slot="reference" size="small" type="danger">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
<!--    分页-->
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
  </div>
</template>

<script>
export default {
  name: "Main",
  data() {
    //判断年龄输入框年龄是否过大
    let checkAge = (rule, value, callback) => {
      if(value>150){
        callback(new  Error('年龄过大'));
      }else {
        callback();
      }
    };
    //判断账号输入框账号数据是否重复
    let checkDuplicate = (rule,value,callback) => {
      if(this.ruleForm.id){
        return callback();
      }
      this.$axios.get(this.$httpUrl+"/user/findByNo?no="+this.ruleForm.no).then(res=>res.data).then(res=>{
        if (res.code!=200){

          callback()
        }else {
          callback(new Error('账号已存在'));
        }
      })
    };
    return {
      loading: true,
      tableData: [],
      pageNum:1,
      pageSize:5,
      total:0,
      name:'',
      sex:'',
      sexs:[{
        value: '1',
        label: '男'
      }, {
        value: '0',
        label: '女'
      }],
      centerDialogVisible: false,
      ruleForm: {
        id:'',
        name: '',
        password:'',
        no:'',
        age:'',
        sex:'1',
        phone:'',
        roleId:'2',
      },
      //新增输入框规则
      rules: {
        no: [
          { required: true, message: '请输入账号', trigger: 'blur' },
          { min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur' },
          {validator:checkDuplicate,trigger: 'blur'}
        ],
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 3, max: 8, message: '长度在 2 到 8 个字符', trigger: 'blur' }
        ],
        age: [
          { required: true, message: '请输入年龄', trigger: 'blur' },
          { min: 1, max: 3, message: '长度在 1 到 3 个字符', trigger: 'blur' },
          {pattern:/^([1-9][0-9]*){1,3}$/,message: '年龄必须为正整数数字',trigger:'blur'},
          {validator:checkAge,trigger: 'blur'}
        ],
        phone: [
          { required: true, message: '请输入手机号码', trigger: 'blur' },
          {pattern:/^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/
            ,message: '请输入正确的手机号码',trigger:'blur'},
        ],
      }
    }
  },
  methods:{
    handleClose(done) {
      this.$confirm('确认关闭？')
          //忽略下一行校验
          // eslint-disable-next-line
          .then(_ => {
            done();
          })
          //忽略下一行校验
          // eslint-disable-next-line
          .catch(_ => {});
    },
    //分页
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageNum=1
      this.pageSize=val
      this.loadPost()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum=val
      this.loadPost()
    },
    loadGet(){
      this.$axios.get(this.$httpUrl+'/user/list').then(res=>res.data).then(res=>{
        console.log(res)
      })
    },
    resetParam(){
      this.name=''
      this.sex=''
    },
    mod(row){
      console.log(row)

      this.centerDialogVisible = true
      this.$nextTick(()=>{
        //赋值到表单
        this.ruleForm.id = row.id
        this.ruleForm.no = row.no
        this.ruleForm.name = row.name
        this.ruleForm.password = row.password
        this.ruleForm.age = row.age+''
        this.ruleForm.sex = row.sex+''
        this.ruleForm.phone = row.phone
        this.ruleForm.roleId = row.roleId+''
      })
    },
    del(id){
      console.log(id)
      this.$axios.post(this.$httpUrl+'/user/del?id='+id,this.ruleForm).then(res=>res.data).then(res=>{
        console.log(res)
        if (res.code==200) {
          this.$message({
            message: '删除成功',
            type: 'success'
          });
          this.loadPost()
        }else{
          this.$message.error({
            message: '删除失败',
            type: 'warning'
          });
        }
      })
    },
    add(){
      this.centerDialogVisible = true
      //重置输入框
      this.$nextTick(()=>{
        this.resetForm();
      })
    },
    resetForm() {
      this.$refs.ruleForm.resetFields();
    },
    doSave(){
      this.$axios.post(this.$httpUrl+'/user/save',this.ruleForm).then(res=>res.data).then(res=>{
        console.log(res)
        if (res.code==200) {
          this.$message({
            message: '添加成功',
            type: 'success'
          });
          this.centerDialogVisible = false
          this.loadPost()
        }else{
          this.$message.error({
            message: '添加失败',
            type: 'warning'
          });
        }
      })
    },
    doMod(){
      this.$axios.post(this.$httpUrl+'/user/update',this.ruleForm).then(res=>res.data).then(res=>{
        console.log(res)
        if (res.code==200) {
          this.$message({
            message: '添加成功',
            type: 'success'
          });
          this.centerDialogVisible = false
          this.loadPost()
        }else{
          this.$message.error({
            message: '添加失败',
            type: 'warning'
          });
        }
      })
    },
    //添加数据
    save(){
        this.$refs.ruleForm.validate((valid) => {
          if (valid) {
            if(this.ruleForm.id){
              this.doMod();
            }else{
              this.doSave();
            }
          } else {
            console.log('error submit!!');
            return false;
          }
        });

    },
    //显示数据
    loadPost(){
      this.$axios.post(this.$httpUrl+'/user/listPageC1',{
        pageSize:this.pageSize,
        pageNum:this.pageNum,
        param:{
          name:this.name,
          sex:this.sex
        }
      }).then(res=>res.data).then(res=>{
        console.log(res)
        if (res.code==200){
          this.tableData=res.data
          this.total=res.total
        }else{
          alert('获取失败')
        }
      })
    }
  },
  beforeMount(){
    this.loadGet();
    this.loadPost();
  }

}
</script>

<style scoped>

</style>