<template>
  <div class="loginBody">
    <div class="LoginDiv">
      <div class="login-box">
        <h2>用户登录</h2>
        <el-form :model="loginForm" label-width="100px" :rules="rules" ref="LoginForm">
        <el-form-item prop="no">
          <span slot="label">
            <span style="color: white">
               <strong>密码:</strong>
            </span>
           </span>
        <el-input style="" type="text" v-model="loginForm.no" placeholder="请输入账号" autocomplete="off" size="small">
        </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <span slot="label">
            <span style="color: white">
               <strong>密码:</strong>
            </span>
           </span>
        <el-input style="..." type="password" placeholder="请输入密码" v-model="loginForm.password" show-password autocomplete="off" size="small" @keyup.enter.native="confirm">
        </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="confirm" :disabled="confirm_disabled">确 定</el-button></el-form-item>
          </el-form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      confirm_disabled:false,
      loginForm:{
        no: '',
        password: '',
      },
      rules:{
        no: [{
          required: true, message:'请输入账号', trigger:'blur'
        }],
        password: [{
          required: true, message:'请输入密码', trigger:'blur'
        }]
      }
    }
  },
  methods: {
    confirm(){
      this.confirm_disabled=true;
      this.$refs.LoginForm.validate((valid) =>{
        if (valid) {
          this.$axios.post(this.$httpUrl+"/user/login",this.loginForm).then(res=>res.data).then(res=>{
            console.log(res)
            if (res.code==200){
              sessionStorage.setItem("CurUser",JSON.stringify(res.data.user))
              console.log(res.data.menu)
              this.$store.commit("setMenu",res.data.menu)
              sessionStorage.setItem("isLogin","true")
              this.$router.replace('/Home');
            }else {
              this.confirm_disabled=false;
              alert('校验失败，用户名或密码错误！');
              return false;
            }
          });
        }else {
            this.confirm_disabled=false;
            console.log('校验失败');
          return false;
        }
      })
    }

  }
}
</script>
<style>

</style>
<style scoped>

.loginBody{
  overflow: hidden;
  width: 100vw;
  height: 100vh;
  background: url("../image/Image1.jpg");
  background-size: 100% 100%;
}
body::-webkit-scrollbar{
  display:none
}

.login-box {
  position: absolute;
  top: 50%;
  left: 50%;
  width: 400px;
  padding: 40px;
  transform: translate(-50%, -50%);
  background: rgba(0, 0, 0, .5);
  box-sizing: border-box;
  box-shadow: 0 15px 25px rgba(0, 0, 0, .6);
  border-radius: 10px;
}
.login-box h2 {
  margin: 0 0 30px;
  padding: 0;
  color: #fff;
  text-align: center;
}

.login-box .user-box {
  position: relative;
}

.login-box .user-box input {
  width: 100%;
  padding: 10px 0;
  font-size: 16px;
  color: #fff;
  margin-bottom: 30px;
  border: none;
  border-bottom: 1px solid #fff;
  outline: none;
  background: transparent;
}

.login-box .user-box label {
  position: absolute;
  top: 0;
  left: 0;
  padding: 10px 0;
  font-size: 16px;
  color: #fff;
  pointer-events: none;
  transition: .5s;
}

.login-box .user-box input:focus ~ label,
.login-box .user-box input:valid ~ label {
  top: -20px;
  left: 0;
  color: #03e9f4;
  font-size: 12px;
}

.login-box form a {
  position: relative;
  display: inline-block;
  padding: 10px 20px;
  color: #03e9f4;
  font-size: 16px;
  text-decoration: none;
  text-transform: uppercase;
  overflow: hidden;
  transition: .5s;
  margin-top: 40px;
  letter-spacing: 4px
}

.login-box a:hover {
  background: #03e9f4;
  color: #fff;
  border-radius: 5px;
  box-shadow: 0 0 5px #03e9f4,
  0 0 25px #03e9f4,
  0 0 50px #03e9f4,
  0 0 100px #03e9f4;
}

.login-box a span {
  position: absolute;
  display: block;
}

.login-box a span:nth-child(1) {
  top: 0;
  left: -100%;
  width: 100%;
  height: 2px;
  background: linear-gradient(90deg, transparent, #03e9f4);
  animation: btn-anim1 1s linear infinite;
}

@keyframes btn-anim1 {
  0% {
    left: -100%;
  }
  50%, 100% {
    left: 100%;
  }
}

.login-box a span:nth-child(2) {
  top: -100%;
  right: 0;
  width: 2px;
  height: 100%;
  background: linear-gradient(180deg, transparent, #03e9f4);
  animation: btn-anim2 1s linear infinite;
  animation-delay: .25s
}

@keyframes btn-anim2 {
  0% {
    top: -100%;
  }
  50%, 100% {
    top: 100%;
  }
}

.login-box a span:nth-child(3) {
  bottom: 0;
  right: -100%;
  width: 100%;
  height: 2px;
  background: linear-gradient(270deg, transparent, #03e9f4);
  animation: btn-anim3 1s linear infinite;
  animation-delay: .5s
}

@keyframes btn-anim3 {
  0% {
    right: -100%;
  }
  50%, 100% {
    right: 100%;
  }
}

.login-box a span:nth-child(4) {
  bottom: -100%;
  left: 0;
  width: 2px;
  height: 100%;
  background: linear-gradient(360deg, transparent, #03e9f4);
  animation: btn-anim4 1s linear infinite;
  animation-delay: .75s
}

@keyframes btn-anim4 {
  0% {
    bottom: -100%;
  }
  50%, 100% {
    bottom: 100%;
  }
}
</style>