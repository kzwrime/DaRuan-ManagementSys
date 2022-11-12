<template>
  <el-row>
    <el-col :span="24">
      <el-card style="min-width:100%;text-align: center" shadow="hover">
        <h2 style="font-size: 80px">这是一个简单的项目主页</h2>
      </el-card>
    </el-col>
  </el-row>
    <el-row>
      <el-col :xs="24" :sm="8" :md="8" >
        <el-card style="min-width: 100%" shadow="hover">
          <el-card style="width: auto ;border:0px;text-align: center" shadow="never" >
            <el-button  @click="toLabPage" style="position:relative;border: 0">
              <h1 style="font-size: 25px">课题组介绍</h1>
            <el-icon size="28px">
              <home-filled />
            </el-icon>
            </el-button>
            <img src="../assets/pics/car.png" alt="" style="max-width: 100%">
          </el-card>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="8" :md="8" >
        <el-card style="min-width: 100%" shadow="hover">
          <el-card style="width: auto ;border:0px;text-align: center" shadow="never" >
            <el-button @click="toLogin" style="position:relative; ;border: 0" >
              <h1 style="font-size: 25px">实验室资料管理系统</h1>
              <el-icon size="28px">
                <platform />
              </el-icon>
            </el-button>
            <img src="../assets/pics/truck.png" alt="" style="max-width: 100%">
          </el-card>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="8" :md="8" >
        <el-card style="min-width: 100%" shadow="hover">
          <el-card style="width: auto ;border:0px;text-align: center" shadow="never" >
            <el-button @click="toAiqiyi" style="position:relative;border: 0">
              <h1 style="font-size:25px">就是到处看看</h1>
              <el-icon size="28px">
                <IceCream />
              </el-icon>
            </el-button>
            <img src="../assets/pics/print.png" alt="" style="max-width: 100%;">
          </el-card>
        </el-card>
      </el-col>
    </el-row>
</template>
<!--<el-button @click="toLogin">登陆界面</el-button>-->
<!--<el-button @click="toUsr">用户界面</el-button>-->
<!--<el-button @click="toReg">注册界面</el-button>-->
<!--<el-button @click="toVistor">游客界面</el-button>-->
<script>
import {IceCream} from "@element-plus/icons";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "index",
  components: {IceCream},
  methods:{
    toLogin(){
      this.$router.push('/login')
    },
    toReg(){
      this.$router.push('/userRegister')
    },
    toUsr(){
      this.$router.push('/userPage')
    },
    toVistor()
    {
      this.$router.push('/visitor')
    },
    toLabPage(){
      window.open("http://totemdb.whu.edu.cn/")
},
    toAiqiyi(){
      window.open("https://v.iqiyi.com/")
    }

  }
}

</script>

<style scoped>

</style>