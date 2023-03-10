<template>
  <div class="index" style="height: 100%">
    <div style="margin-bottom: 5px">
      <el-input v-model="name" placeholder="请输入货物名称" suffix-icon="el-icon-search" style="width: 200px"
                @keyup.enter.native="loadPost"></el-input>
      <el-select v-model="storage" placeholder="请选择仓库" style="margin-left: 5px">
        <el-option
            v-for="item in storageData"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>
      <el-select v-model="goodstype" placeholder="请选择分类" style="margin-left: 5px">
        <el-option
            v-for="item in goodstypeData"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>
      <el-button type="primary" style="margin-left: 9px" @click="loadPost">查询</el-button>
      <el-button type="success" @click="resetParam">重置</el-button>
      <el-button type="primary" style="margin-left: 9px" @click="add" v-if="user.roleId!=2">新增</el-button>
      <el-button type="primary" style="margin-left: 9px" @click="inGoods" v-if="user.roleId!=2">入库</el-button>
      <el-button type="primary" style="margin-left: 9px" @click="outGoods" v-if="user.roleId!=2">出库</el-button>
      <!--新增按钮打开的输入框-->
      <el-dialog
          title="提示"
          :visible.sync="centerDialogVisible"
          width="30%"
          center
          :before-close="handleClose"
      >
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="货物名" prop="name" style="width: 80%">
            <el-input v-model="ruleForm.name"></el-input>
          </el-form-item>
          <el-form-item label="仓库名"  prop="storage" style="width: 80%">
            <el-select v-model="ruleForm.storage" placeholder="请选择仓库" style="margin-left: 5px">
              <el-option
                  v-for="item in storageData"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="分类"  prop="goodstype" style="width: 80%">
            <el-select v-model="ruleForm.goodstype" placeholder="请选择分类" style="margin-left: 5px">
              <el-option
                  v-for="item in goodstypeData"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="数量"  prop="count" style="width: 80%">
            <el-input v-model="ruleForm.count"></el-input>
          </el-form-item>
          <el-form-item label="备注"  prop="remark" style="width: 80%">
            <el-input type="textarea" v-model="ruleForm.remark"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer" >
    <el-button @click="centerDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="save">确 定</el-button>
  </span>
      </el-dialog>
      <!-- 出入库 -->
      <el-dialog
          title="出入库"
          :visible.sync="inDialogVisible"
          width="30%"
          center
          :before-close="handleClose"
      >
        <el-dialog
            width="70%"
            title="用户选择"
            :visible.sync="innerVisible"
            append-to-body>
          <SelectUser @doSelectUser="doSelectUser"></SelectUser>
          <span slot="footer" class="dialog-footer">
    <el-button @click=" innerVisible= false">取 消</el-button>
    <el-button type="primary" @click="confirmUser">确 定</el-button>
  </span>
        </el-dialog>
        <el-form :model="ruleForm1" :rules="rules1" ref="ruleForm1" label-width="100px" class="demo-ruleForm">
          <el-form-item label="货物名" style="width: 80%">
            <el-input v-model="ruleForm1.goodsname" readonly></el-input>
          </el-form-item>
          <el-form-item label="申请人" style="width: 80%">
            <el-input v-model="ruleForm1.username" readonly @click.native="selectUser"></el-input>
          </el-form-item>
          <el-form-item label="数量"  prop="count" style="width: 80%">
            <el-input v-model="ruleForm1.count"></el-input>
          </el-form-item>
          <el-form-item label="备注"  prop="remark" style="width: 80%">
            <el-input type="textarea" v-model="ruleForm1.remark"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
    <el-button @click=" inDialogVisible= false">取 消</el-button>
    <el-button type="primary" @click="doInGoods">确 定</el-button>
  </span>
      </el-dialog>
    </div>
    <!--    数据显示-->
    <el-table :data="tableData"
              height="675"
              style="width: 100%;height: 100%"
              :header-cell-style="{ background:'#e3f8f6',color: '#000000'}"
              border
              highlight-current-row
              @current-change="selectCurrentChange">
      <el-table-column prop="id" label="ID" width="60">
      </el-table-column>
      <el-table-column prop="name" label="货物名" width="180">
      </el-table-column>
      <el-table-column prop="storage" label="仓库名" :formatter="formatStoage">
      </el-table-column>
      <el-table-column prop="goodstype" label="分类" :formatter="formatGoodsType">
      </el-table-column>
      <el-table-column prop="count" label="数量">
      </el-table-column>
      <el-table-column prop="remark" label="备注">
      </el-table-column>
      <el-table-column prop="operate" label="操作"  v-if="user.roleId!=2">
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
import SelectUser from "../user/SelectUser";
export default {
  name: "GoodsManage",
  components:{SelectUser},
  data() {
    let checkCount=(rule,value,callback)=>{
      if (value>9999999){
        callback(new  Error('数量过大'))
      }else {
        callback()
      }
    }
    return {
      user : JSON.parse(sessionStorage.getItem('CurUser')),
      goodstypeData: [],
      storageData: [],
      loading: true,
      tableData: [],
      pageNum:1,
      pageSize:5,
      total:0,
      name:'',
      storage: '',
      goodstype: '',
      centerDialogVisible: false,
      inDialogVisible: false,
      innerVisible:false,
      currentRow:{},
      tempUser:{},
      ruleForm: {
        id:'',
        name: '',
        storage:'',
        goodstype:'',
        count:'',
        remark:'',
      },
      //新增输入框规则
      rules: {
        name: [
          { required: true, message: '请输入物品', trigger: 'blur' },
        ],
        storage: [
          { required: true, message: '请选择仓库', trigger: 'blur' },
        ],
        goodstype: [
          { required: true, message: '请选择分类', trigger: 'blur' },
        ],
        count:[
          { required: true, message: '请输入数量', trigger: 'blur' },
          {pattern:/^([1-9][0-9]*){1,3}$/,message: '数量必须为正整数数字',trigger:'blur'},
          {validator:checkCount,trigger: 'blur'}
        ]
      },
      ruleForm1: {
        goods:'',
        goodsname:'',
        count:'',
        username:'',
        userid:'',
        adminId:'',
        remark:'',
        action:'1',
      },
      rules1: {

      },
    }
  },
  methods:{
    doSelectUser(val) {
      console.log(val);
      this.tempUser = val
    },
    confirmUser() {
      this.ruleForm1.username = this.tempUser.name
      this.ruleForm1.userid = this.tempUser.id
      this.innerVisible =false
    },
    selectCurrentChange(val) {
      this.currentRow = val;
    },
    formatStoage(row){
      let temp = this.storageData.find(item=>{
        return item.id == row.storage
      })

      return temp && temp.name
    },
    formatGoodsType(row){
      let temp = this.goodstypeData.find(item=>{
        return item.id == row.goodstype
      })

      return temp && temp.name
    },
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
      this.$axios.get(this.$httpUrl+'/goods/list').then(res=>res.data).then(res=>{
        console.log(res)
      })
    },
    resetParam(){
      this.name=''
      this.storage=''
      this.goodstype=''
      this.loadPost();
    },
    mod(row){
      console.log(row)
      this.centerDialogVisible = true
      this.$nextTick(()=>{
        //赋值到表单
        this.ruleForm.id = row.id
        this.ruleForm.name = row.name
        this.ruleForm.storage = row.storage
        this.ruleForm.goodstype = row.goodstype
        this.ruleForm.count = row.count
        this.ruleForm.remark = row.remark
      })
    },
    del(id){
      console.log(id)
      this.$axios.post(this.$httpUrl+'/goods/del?id='+id,this.ruleForm).then(res=>res.data).then(res=>{
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
    inGoods(){
      if (!this.currentRow.id){
          alert('请选择记录');
          return;
      }
      this.inDialogVisible = true
      //重置输入框
      this.$nextTick(()=>{
        this.resetInForm();
      })
      this.ruleForm1.goodsname = this.currentRow.name
      this.ruleForm1.goods = this.currentRow.id
      this.ruleForm1.adminId = this.user.id
      this.ruleForm1.action = '1'
    },
    outGoods(){
      if (!this.currentRow.id){
        alert('请选择记录');
        return;
      }
      this.inDialogVisible = true
      //重置输入框
      this.$nextTick(()=>{
        this.resetInForm();
      })
      this.ruleForm1.goodsname = this.currentRow.name
      this.ruleForm1.goods = this.currentRow.id
      this.ruleForm1.adminId = this.user.id
      this.ruleForm1.action = '2'
    },
    selectUser() {
      this.innerVisible = true;
    },
    resetForm() {
      this.$refs.ruleForm.resetFields();
    },
    resetInForm() {
      this.$refs.ruleForm1.resetFields();
    },
    doSave(){
      this.$axios.post(this.$httpUrl+'/goods/save',this.ruleForm).then(res=>res.data).then(res=>{
        console.log(res)
        if (res.code==200) {
          this.$message({
            message: '添加成功',
            type: 'success'
          });
          this.centerDialogVisible = false
          this.loadPost()
          this.resetForm()
        }else{
          this.$message.error({
            message: '添加失败',
            type: 'warning'
          });
        }
      })
    },
    doInGoods(){
      this.$axios.post(this.$httpUrl+'/record/save',this.ruleForm1).then(res=>res.data).then(res=>{
        console.log(res)
        if (res.code==200) {
          this.$message({
            message: '添加成功',
            type: 'success'
          });
          this.inDialogVisible = false
          this.loadPost()
          this.resetInForm()
        }else{
          this.$message.error({
            message: '添加失败',
            type: 'warning'
          });
        }
      })
    },
    doMod(){
      this.$axios.post(this.$httpUrl+'/goods/update',this.ruleForm).then(res=>res.data).then(res=>{
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
      this.$axios.post(this.$httpUrl+'/goods/listPage',{
        pageSize:this.pageSize,
        pageNum:this.pageNum,
        param:{
          name:this.name,
          storage: this.storage+'',
          goodstype: this.goodstype+'',
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
    },
    loadStorage(){
      this.$axios.get(this.$httpUrl+'/stoage/list').then(res=>res.data).then(res=>{
        console.log(res)
        if (res.code==200){
          this.storageData=res.data
        }else{
          alert('获取失败')
        }
      })
    },
    loadGoodsType(){
      this.$axios.get(this.$httpUrl+'/goodstype/list').then(res=>res.data).then(res=>{
        console.log(res)
        if (res.code==200){
          this.goodstypeData=res.data
        }else{
          alert('获取失败')
        }
      })
    }
  },
  beforeMount(){
    this.loadStorage();
    this.loadGoodsType();
    this.loadPost();
  }

}
</script>

<style scoped>

</style>