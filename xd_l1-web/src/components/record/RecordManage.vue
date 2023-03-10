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
    </div>
    <!--    数据显示-->
    <el-table :data="tableData"
              height="675"
              style="width: 100%;height: 100%"
              :header-cell-style="{ background:'#e3f8f6',color: '#000000'}"
              border>
      <el-table-column prop="id" label="ID" width="60">
      </el-table-column>
      <el-table-column prop="goodsname" label="货物名" width="180">
      </el-table-column>
      <el-table-column prop="storagename" label="仓库名">
      </el-table-column>
      <el-table-column prop="goodstypename" label="分类">
      </el-table-column>
      <el-table-column prop="count" label="数量">
      </el-table-column>
      <el-table-column prop="adminname" label="操作人">
      </el-table-column>
      <el-table-column prop="username" label="申请人">
      </el-table-column>
      <el-table-column prop="createtime" label="操作时间">
      </el-table-column>
      <el-table-column prop="remark" label="备注">
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
  name: "RecordManage",
  data() {
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
      ruleForm: {
        id:'',
        name: '',
        storage:'',
        goodstype:'',
        count:'',
        remark:'',
      },
    }
  },
  methods:{
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
      this.$axios.get(this.$httpUrl+'/record/list').then(res=>res.data).then(res=>{
        console.log(res)
      })
    },
    resetParam(){
      this.name=''
      this.storage=''
      this.goodstype=''
      this.loadPost();
    },
    resetForm() {
      this.$refs.ruleForm.resetFields();
    },
    doMod(){
      this.$axios.post(this.$httpUrl+'/record/update',this.ruleForm).then(res=>res.data).then(res=>{
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
    //显示数据
    loadPost(){
      this.$axios.post(this.$httpUrl+'/record/listPage',{
        pageSize:this.pageSize,
        pageNum:this.pageNum,
        param:{
          name:this.name,
          storage: this.storage+'',
          goodstype: this.goodstype+'',
          roleId: this.user.roleId+'',
          userId: this.user.id+''
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