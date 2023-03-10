<template>
  <div class="index" style="height: 100%">
    <div style="margin-bottom: 5px">
      <el-input v-model="name" placeholder="请输入物品分类名称" suffix-icon="el-icon-search" style="width: 200px"
                @keyup.enter.native="loadPost"></el-input>
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
          <el-form-item label="分类名" prop="name" style="width: 80%">
            <el-input v-model="ruleForm.name"></el-input>
          </el-form-item>
          <el-form-item label="备注"  prop="remark" style="width: 80%">
            <el-input type="textarea" v-model="ruleForm.remark"></el-input>
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
              height="675"
              style="width: 100%;height: 100%"
              :header-cell-style="{ background:'#e3f8f6',color: '#000000'}"
              border>
      <el-table-column prop="id" label="ID" width="60">
      </el-table-column>
      <el-table-column prop="name" label="分类名" width="180">
      </el-table-column>
      <el-table-column prop="remark" label="备注">
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
  name: "AdminManage",
  data() {
    return {
      loading: true,
      tableData: [],
      pageNum:1,
      pageSize:5,
      total:0,
      name:'',
      centerDialogVisible: false,
      ruleForm: {
        id:'',
        name: '',
        remark:'',
      },
      //新增输入框规则
      rules: {
        name: [
          { required: true, message: '请输入物品', trigger: 'blur' },
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
      this.$axios.get(this.$httpUrl+'/goodstype/list').then(res=>res.data).then(res=>{
        console.log(res)
      })
    },
    resetParam(){
      this.name=''
      this.loadPost();
    },
    mod(row){
      console.log(row)
      this.centerDialogVisible = true
      this.$nextTick(()=>{
        //赋值到表单
        this.ruleForm.id = row.id
        this.ruleForm.name = row.name
        this.ruleForm.remark = row.remark
      })
    },
    del(id){
      console.log(id)
      this.$axios.post(this.$httpUrl+'/goodstype/del?id='+id,this.ruleForm).then(res=>res.data).then(res=>{
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
      this.$axios.post(this.$httpUrl+'/goodstype/save',this.ruleForm).then(res=>res.data).then(res=>{
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
      this.$axios.post(this.$httpUrl+'/goodstype/update',this.ruleForm).then(res=>res.data).then(res=>{
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
            console.log(this.ruleForm.id)
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
      this.$axios.post(this.$httpUrl+'/goodstype/listPage',{
        pageSize:this.pageSize,
        pageNum:this.pageNum,
        param:{
          name:this.name,
          remark:this.remark,
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
    this.loadPost();
  }

}
</script>

<style scoped>

</style>