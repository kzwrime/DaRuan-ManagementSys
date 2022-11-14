<template>
<!-- what bugs? idk -->
<!-- anyone is ok -->
  <div style="background:linear-gradient(60deg,#ecf0f1,#fedc5e)">
    <el-row>
      <el-col>
        <div style="min-height: 150px"></div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :xs="24" :md="6" :xl="4">
        <img class="hi_img" src="../assets/pics/hi.png" alt=""
             style="position: relative; max-width: 200px;left: 20%"
        >
      </el-col>
      <el-col :xs="24" :md="18" :xl="20">
        <h2 class="title_text">欢迎你使用我们的管理系统</h2>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <el-container class="card_container" style="position:relative;left:10%;">
          <el-card class="box-card">
            <el-form
                :model="ruleForm"
                status-icon
                :rules="ruleForm.rules"
                ref="ruleForm"
                class="ruleForm"
            >
              <el-form-item label="姓名:" prop="Name">
                <el-input placeholder="请输入您的姓名"
                          v-model="ruleForm.Name" type="text"
                          autocomplete="off">
                </el-input>
              </el-form-item>
              <el-form-item label="学号:" prop="usrId">
                <el-input placeholder="请输入您的学号"
                          v-model="ruleForm.usrId" type="text"
                          autocomplete="off">
                </el-input>
              </el-form-item>
              <el-form-item label="邮箱:" prop="checkEmail">
                <el-input placeholder="请输入邮箱"
                          v-model="ruleForm.checkEmail"
                          type="email"
                          autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="密码" prop="pass">
                <el-input
                    type="password"
                    v-model="ruleForm.pass"
                    autocomplete="off"
                    placeholder="请输入密码"
                ></el-input>
              </el-form-item>
              <el-form-item label="确认密码" prop="checkPass">
                <el-input
                    type="password"
                    v-model="ruleForm.checkPass"
                    autocomplete="off"
                    placeholder="请输入上一行输入的密码"
                ></el-input>
              </el-form-item>
              <el-form-item>
                <el-card style="position:relative;border-color: white" shadow="never">
                  <el-button type="primary" @click="submitForm('ruleForm')">注册</el-button>
                  <el-button @click="resetForm('ruleForm')">重置</el-button>
                </el-card>
              </el-form-item>
            </el-form>
          </el-card>
        </el-container>
      </el-col>
    </el-row>
    <el-col>

    </el-col>
    <div style="min-height: 480px;background:linear-gradient(60deg,#ecf0f1,#fedc5e)"></div>
  </div>
</template>

<script>
export default {
  name: "regPage",
  data() {
    var validateName = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入姓名'))
      } else if (value.length > 30 || value.length < 2) {
        callback(new Error("你名字长短不太合适吧"))
      } else {
        if (this.ruleForm.Name !== '') {
          this.$refs.ruleForm.validateField('Name')
        }
        callback()
      }
    }
    var validatePass = (rule, value, callback) => {
      var pass_format = /^[a-zA-Z0-9.@*.#*.\w*]*$/
      if (value === '') {
        callback(new Error('请输入密码 7-16位 可包含 数字,字母_,@,#'))
      } else if (value.length > 17 || value.length < 6) {
        callback(new Error("密码长度在7-16位之间"))
      } else if (!pass_format.test(value)) {
        callback(new Error("想SQL注入？包含数字字母下划线,@,# 看清楚啊你"))
      } else {
        if (this.ruleForm.pass !== '') {
          this.$refs.ruleForm.validateField('pass')
        }
        callback()
      }
    }
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.ruleForm.pass) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    var validateEmail = (rule, value, callback) => {
      var email_format = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
      if (value === '') {
        callback(new Error('请输入邮箱'))
      } else if (!email_format.test(this.ruleForm.checkEmail)) {
        callback(new Error('邮箱格式错误'))
      } else {
        callback()
      }
    }
    return {
      ruleForm: {
        checkEmail: '',
        pass: '',
        checkPass: '',
        Name: '',
        usrId: '',
        conTop: {
          backgroundImage: 'url(' + require('../assets/pics/regBack.png') + ')',
          backgroundRepeat: 'no-repeat',
          backgroundSize: "cover"
        },
        rules: {
          checkEmail: [{validator: validateEmail, trigger: 'blur'}],
          pass: [{validator: validatePass, trigger: 'blur'}],
          checkPass: [{validator: validatePass2, trigger: 'blur'}],
          Name: [{validator: validateName, trigger: 'blur'}],
        },
      }
    }
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log('注册成功 三秒后跳转至登陆页面!')
          console.log(this.ruleForm)
          //@Todo 向服务器发送数据
          var toserver=this.$qs.stringify({
            checkEmail: this.ruleForm.checkEmail,
            pass: this.ruleForm.pass,
            Name: this.ruleForm.Name,
            usrId: this.ruleForm.usrId
          })
          this.$axios.post('/api/users/register/',
          toserver
          ).then(res => {
            console.log(res)
                if(res.data===-1){
                  alert('用户已存在！')
                  this.$router.push('/login')
                }
                else if(res.data===0){
                  alert('注册失败，请重新尝试~')
                }
                else if(res.data===1){
                  alert('注册成功！~')
                  this.$router.push('/login')
                }
                else{
                  alert('服务器出现了问题，请联系管理员！~')
                }
              }
          )

        }
        else {
          console.log('error submit!! Check Data Format')
          return false
        }
      })

    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
  },
}
</script>

<style scoped>

.ruleForm {
  position: relative;
  left: 0%;
  height: 90%;
  width: 100%;
  top: 4%;
}

.title_text {
  position: relative;
  left: 0%;
  top: 0%;
  font-size: 50px;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
}

</style>