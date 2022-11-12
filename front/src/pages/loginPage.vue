<template>
  <div style="background:linear-gradient(45deg,#d3f9d8,#fff0f6)">
    <el-row>
      <el-col :span="24">
        <el-container class="header-content">
          <img class="header_pic" src="../assets/pics/whuheader.png" alt=""
               style="position:absolute;width: 100%;height: 100%"
          >
          <img class="top-logo"
               src="../assets/pics/whulogo.png"
               alt=""
               style="position:relative;">
        </el-container>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24" style="text-align: center">
        <img class="main-logo"
             src="../assets/pics/login.png"
             alt=""
             style="max-height: 200px;position: relative;top: 20%"
        >
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="2">
        <img src="../assets/pics/sleep.png" alt="" style="max-width:20px">
      </el-col>
      <el-col :span="20">
        <el-card shadow="always" class="login-card" body-style="padding:10" style="
      background-color: #f8f9fa;border-radius: 16px;position: relative;top: 12%">
          <el-form
              label-width="50px"
              class="loginForm sign-in-form"
              size="medium"
          >
            <el-form-item label="邮箱:" prop="email" label-width="50px"
                          size="medium">
              <el-input
                  placeholder="Email Address"
                  v-model="user.email"
              ></el-input>
            </el-form-item>
            <el-form-item label="密码:" prop="password" label-width="50px">
              <el-input
                  type="password"
                  placeholder="Password"
                  v-model="user.pwd"
              ></el-input>
            </el-form-item>
          </el-form>
          <el-button-group class="btn-group" size="medium" style="position: relative;left: 30%;">
            <el-button type="primary" round class="login-btn" @click="SubmitInfo" size="medium">登录</el-button>
            <el-button type="success" round class="login-btn" @click="gotoRegister">注册<i class="register-btn"></i
            ></el-button>
            <el-button type="primary" round class="login-btn" @click="gotoVistor" size="medium">游客访问
            </el-button>
          </el-button-group>
        </el-card>
      </el-col>
      <el-col :span="2">

      </el-col>
    </el-row>
    <el-row>
      <el-col>

      </el-col>
      <div style="min-height: 480px;background:conic-gradient(#d3f9d8,#fff0f6)"></div>
    </el-row>
    <el-row>
      <el-col>

      </el-col>
      <div style="min-height: 200px;background:linear-gradient(45deg,#d3f9d8,#fff0f6)"></div>
    </el-row>
    <el-container>

      <el-container>

      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "loginPage",
  components: {
    // eslint-disable-next-line vue/no-unused-components
  },

  data() {
    const user = {
      email: '',
      pwd: ''
    }
    return {
      userInfo: user,
      user
    }
  },
  methods: {
    SubmitInfo() {
      var regEmail = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
      if (this.user.email === '' || !regEmail.test(this.user.email)) {
        this.$alert('邮箱格式错误，请输入正确的邮箱格式', '提示', {
          confirmButtonText: '确定',
        })
        this.user.email = ''
        this.user.pwd = ''
      } else {
        console.log(this.user)
        this.$axios.get('/api/users/login/', {
          params:
              {
                email: this.user.email,
                passwd: this.user.pwd
              }
        })
            .then(res => {
              console.log(res)
              if(res.data===0){
                alert('用户名不存在或密码错误，请重新输入')

              }
              else if(res.data===1){
                alert('登陆成功')
                sessionStorage.setItem("token", this.user.email)
                this.$router.push({
                  path: '/userPage',
                  query: {
                    email: this.user.email
                  }
                })
              }
              else if (res.data===10086){
                alert('欢迎管理员回家~')
                sessionStorage.setItem("token", this.user.email)
                this.$router.push({
                  path: '/book_manage',//push到管理员页面上去
                  query: {
                    email: this.user.email
                  }
                })
              }
              else{
                alert('服务器又挂了，去打管理员吧~')
              }
            })//打回来的数据怎么处理
      }
    },
    gotoRegister() {
      this.$router.replace('/userRegister')
    },
    gotoVistor() {
      this.$router.replace('/visitor')
    }
  }
}
</script>
<style scoped>

/*.login-card {*/
/*  position: relative;*/
/*  left: 38%;*/
/*  width: 23%;*/
/*  height: 78%;*/
/*  top:34%;*/
/*}*/

</style>