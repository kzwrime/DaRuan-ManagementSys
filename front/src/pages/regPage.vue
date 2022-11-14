<template>
<!-- again bye-->
<!-- yo -->
  <div class="reg-page" align="center">
    <el-card class="reg-card">
      <h2>欢迎使用我们的系统</h2>
      <table>
        <tr>
          <td>
            <img
              src="../assets/icons8-blond-curly-hair-business-lady.png"
              boader="2"
            />
          </td>
          <td>
            <img src="../assets/icons8-man-in-white-shirt.png" boader="2" />
          </td>
          <td>
            <img
              src="../assets/icons8-blond-long-hair-business-lady.png"
              boader="2"
            />
          </td>
        </tr>
        <tr>
          <td>
            <img
              src="../assets/icons8-brown-curly-hair-lady-in-light-green-shirt.png"
              boader="2"
            />
          </td>
          <td>
            <img
              src="../assets/icons8-man-in-yellow-striped-sweater.png"
              boader="2"
            />
          </td>
          <td>
            <img src="../assets/icons8-man-with-beard-in-suit.png" boader="2" />
          </td>
        </tr>
      </table>
      <el-form
        :model="ruleForm"
        status-icon
        :rules="ruleForm.rules"
        ref="ruleForm"
        class="ruleForm"
        size="large"
      >
        <el-form-item label="姓名:" prop="Name">
          <el-input
            placeholder="请输入您的姓名"
            v-model="ruleForm.Name"
            type="text"
            autocomplete="off"
          >
          </el-input>
        </el-form-item>
        <el-form-item label="学号:" prop="usrId">
          <el-input
            placeholder="请输入您的学号"
            v-model="ruleForm.usrId"
            type="text"
            autocomplete="off"
          >
          </el-input>
        </el-form-item>
        <el-form-item label="邮箱:" prop="checkEmail">
          <el-input
            placeholder="请输入邮箱"
            v-model="ruleForm.checkEmail"
            type="email"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="pass">
          <el-input
            type="password"
            v-model="ruleForm.pass"
            autocomplete="off"
            placeholder="请输入密码"
          ></el-input>
        </el-form-item>
        <el-form-item label="确认" prop="checkPass">
          <el-input
            type="password"
            v-model="ruleForm.checkPass"
            autocomplete="off"
            placeholder="请输入上一行输入的密码"
          ></el-input>
        </el-form-item>
        <el-form-item style="position:relative;left:25%">
            <el-button type="primary" @click="submitForm('ruleForm')"
              >注册</el-button
            >
            <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "regPage",
  data() {
    var validateName = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入姓名"));
      } else if (value.length > 30 || value.length < 2) {
        callback(new Error("你名字长短不太合适吧"));
      } else {
        if (this.ruleForm.Name !== "") {
          this.$refs.ruleForm.validateField("Name");
        }
        callback();
      }
    };
    var validatePass = (rule, value, callback) => {
      var pass_format = /^[a-zA-Z0-9.@*.#*.\w*]*$/;
      if (value === "") {
        callback(new Error("请输入密码 7-16位 可包含 数字,字母_,@,#"));
      } else if (value.length > 17 || value.length < 6) {
        callback(new Error("密码长度在7-16位之间"));
      } else if (!pass_format.test(value)) {
        callback(new Error("想SQL注入？包含数字字母下划线,@,# 看清楚啊你"));
      } else {
        if (this.ruleForm.pass !== "") {
          this.$refs.ruleForm.validateField("pass");
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.ruleForm.pass) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    var validateEmail = (rule, value, callback) => {
      var email_format = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
      if (value === "") {
        callback(new Error("请输入邮箱"));
      } else if (!email_format.test(this.ruleForm.checkEmail)) {
        callback(new Error("邮箱格式错误"));
      } else {
        callback();
      }
    };
    return {
      ruleForm: {
        checkEmail: "",
        pass: "",
        checkPass: "",
        Name: "",
        usrId: "",
        conTop: {
          backgroundImage: "url(" + require("../assets/pics/regBack.png") + ")",
          backgroundRepeat: "no-repeat",
          backgroundSize: "cover",
        },
        rules: {
          checkEmail: [{ validator: validateEmail, trigger: "blur" }],
          pass: [{ validator: validatePass, trigger: "blur" }],
          checkPass: [{ validator: validatePass2, trigger: "blur" }],
          Name: [{ validator: validateName, trigger: "blur" }],
        },
      },
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log("注册成功 三秒后跳转至登陆页面!");
          console.log(this.ruleForm);
          //@Todo 向服务器发送数据
          var toserver = this.$qs.stringify({
            checkEmail: this.ruleForm.checkEmail,
            pass: this.ruleForm.pass,
            Name: this.ruleForm.Name,
            usrId: this.ruleForm.usrId,
          });
          this.$axios.post("/api/users/register/", toserver).then((res) => {
            console.log(res);
            if (res.data === -1) {
              alert("用户已存在！");
              this.$router.push("/login");
            } else if (res.data === 0) {
              alert("注册失败，请重新尝试~");
            } else if (res.data === 1) {
              alert("注册成功！~");
              this.$router.push("/login");
            } else {
              alert("服务器出现了问题，请联系管理员！~");
            }
          });
        } else {
          console.log("error submit!! Check Data Format");
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
};
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
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
    "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
}

.reg-page {
  width: 100%;
  height: 100%;
  background: url(../assets/pencil.jpg);
  background-size: 100% 100%;
  background-repeat: no-repeat;
  background-attachment: fixed;
}
.reg-card {
  position: relative;
  width: 450px;
  background: rgba(224, 220, 220, 0.349);
  border-radius: 10px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
  margin: auto;
  padding-left: 0px;
  top: 10%;
}
</style>
