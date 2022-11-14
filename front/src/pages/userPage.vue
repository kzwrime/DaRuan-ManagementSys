<template>
<!-- glad to see u again -->
  <div style="background:linear-gradient(to bottom left,#ebeee8,#3271ae)">
    <el-row class="top">
      <el-col :xs="4" :md="5" :lg="6">
        <!--      <img class="headlogo" src="../assets/pics/whulogo.png" alt=""-->
        <!--      style="position:absolute; max-width: inherit;left: 5%">-->
        <img src="../assets/pics/sleep.png" alt="" style="max-width:20px">
      </el-col>
      <el-col :xs=20 :md="19" :lg="18">
        <img class="headlab" src="../assets/pics/userHead.png" alt=""
             style="max-width: inherit;"
        >
        <el-dropdown
            split-button
            type="primary"
            style="position:absolute;right: 5%;bottom: 10%"
        >
          <el-icon size="20px">
            <avatar/>
          </el-icon>

          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="openDrawer">个人信息</el-dropdown-item>
              <el-dropdown-item @click="toLogin">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>

        <el-drawer title="个人中心"
                   v-model="table"
                   direction="rtl"
                   size="75%">
          <el-card>
            <h1>邮箱: {{ email }}</h1>
            <br>
            <el-button @click="innerDrawer = true">修改个人信息</el-button>
          </el-card>
          <el-drawer
              title="用户信息修改"
              :append-to-body="true"
              v-model="innerDrawer"
              destroy-on-close
              size="65%"
          >
              <el-form
                  :model="ruleForm"
                  status-icon
                  :rules="ruleForm.rules"
                  ref="ruleForm"
                  class="ruleForm"
                  style="position:relative;"
              >
                <el-form-item label="用户名:" prop="Name">
                  <el-input placeholder="请输入您的用户名"
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

                <el-form-item label="新密码" prop="pass">
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
                    <el-button type="primary" @click="submitForm('ruleForm')">修改</el-button>
                    <el-button @click="resetForm('ruleForm')">重置</el-button>
                  </el-card>
                </el-form-item>
              </el-form>

          </el-drawer>
          <br>
          <el-card>
            <p>已借阅图书:</p>
            <br>
            <el-table :data=grid_data stripe border>
              <el-table-column property="book_id" label="BookID" width="180"></el-table-column>
              <el-table-column property="book_name" label="Book" width="150"></el-table-column>
              <el-table-column property="op" label="操作">
                <template v-slot="scope">
                  <el-button plain type="success" @click="book_back(scope.row.book_id)">还书
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </el-drawer>
      </el-col>
    </el-row>
    <div class="main" style="height: 100%">
      <el-row>
        <el-col :xs="24" :md="4" :xl="6">
          <el-menu
              default-active="1"
              class="menu-vertical"
              background-color=#6e9bc5
              text-color=#fff0cf
              height=2860px
              style="border: #aed0ee"
              active-text-color=yellow
          >
            <el-sub-menu index="1">
              <template #title>
                <i class="el-icon-location"></i>
                <span style="font-size: 20px; font-weight: normal;">资料类型</span>
              </template>
              <el-menu-item-group>
                <el-menu-item index="1-1" @click="getRemoteData('book')" style="font-size: 16px">图书</el-menu-item>
                <el-menu-item index="1-2" @click="getRemoteData('paper')" style="font-size: 16px">论文</el-menu-item>
              </el-menu-item-group>
            </el-sub-menu>
          </el-menu>
        </el-col>
        <el-col :xs="24" :md="20" :xl="18">
          <el-card body-style="position: relative; padding: 0px;margin: 0">
            <el-container class="search_container"
                          style="position: relative;
                    width:100%;;top:2px
">

                <el-select v-model="switchValue" placeholder="查询方式"
                           style="position:relative;width: 20%">
                <el-option
                      v-for="item in searchOptions"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                  >
                  </el-option>
                </el-select>
               <component :is="searchClass" :category="this.category" :pagesize="this.pageSize"
                          :pagenum="this.currentPage" @returnData="handleDataReturn"/>
            </el-container>
            <div style="min-height: 685px">
              <component :is="page_class" :data="tableData" :filter="year_filter" :user_email="email"/>
            </div>
            <div class="pagination">
              <el-pagination
                  @current-change="handleCurrentChange"
                  :current-page=this.currentPage
                  :page-size=this.pageSize
                  layout="total, prev, pager, next"
                  :total=this.total
                  style="position: relative; left: 5%; border-color: white"
              >
              </el-pagination>
            </div>
          </el-card>
        </el-col>
      </el-row>

    </div>
  </div>
</template>
<script>
import {useRoute} from "vue-router";
import paper_table from "@/components/paper_table";
import book_table from "@/components/book_table";
import accSearchBox from "@/components/AccSearchBox";
import vagueSearchBox from "@/components/VagueSearchBox";
export default {
  name: "userPage",
  props: ['userInfo'],
  data() {
    const pageSize = 15;
    const route = useRoute()

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
        if (this.ruleForm.Name !== '') {
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


    return {
      switchValue: 'exact',
      grid_data: [],
      DataRaw:[],
      tableData: [],

      accKey: '',
      currentPage: 1,
      pageSize,
      total:0,
      total_raw:0,
      category: '',
      email: route.query.email,
      timer: null,
      table: false,
      innerDrawer: false,
      direction: 'rtl',
      vagueInput: '',
      ruleForm: {
        checkEmail: '',
        pass: '',
        checkPass: '',
        Name: '',
        usrId: '',
        rules: {

          pass: [{validator: validatePass, trigger: 'blur'}],
          checkPass: [{validator: validatePass2, trigger: 'blur'}],
          Name: [{validator: validateName, trigger: 'blur'}],
        },
      },
      searchOptions: [
        {
          value: 'exact',
          label: '精确查询',
        },
        {
          value: 'vague',
          label: '模糊查询',
        }

      ]
    }
  },
  components: {
    paper_table,
    book_table,
    accSearchBox,
    vagueSearchBox
  },
  computed: {
    page_class() {
      console.log(this.category)
      switch (this.category) {
        case "paper":
          return "paper_table";
        case "book" :
          return "book_table";
        default:
          return ""
      }
    },
    searchClass(){
      console.log(this.switchValue)
      switch (this.switchValue){
        case 'exact':
          return 'accSearchBox'
        case 'vague':
            return 'vagueSearchBox'
        default:
          return ''
      }
    }
  },
  mounted:function(){
    this.getRemoteData('book')
  },

  methods: {
    convertState(raw_data){
        console.log(raw_data)
      var display_data=[]
      for(var i=0;i<raw_data.length;i++){
        display_data.push(raw_data[i])
        if(raw_data[i].state===0){
          display_data[i].state='已借出'
        }
        else{
          display_data[i].state='在架上'
        }

      }
      return display_data
    },
    handleDataReturn(payload){
      if (payload===-1){
        this.getRemoteData(this.category)
      }
      console.log('payload_data:',payload)
      this.tableData=this.convertState(payload)
      this.total=payload.length
    },
    submitForm(formName) {
      this.
      this.$refs[formName].validate((valid) => {
        if (valid) {
          alert('修改信息成功 三秒后跳转至登陆页面!')
          console.log(this.email)
          console.log({
            old_email: this.email,

            newName: this.ruleForm.Name,
            newId: this.ruleForm.usrId,
            newEmail: this.ruleForm.checkEmail,
            newPwd: this.ruleForm.pass,
          })
          //@Todo 向服务器发送数据
          var toServerData=this.$qs.stringify({
            email: this.email,

            name: this.ruleForm.Name,
            usrid: this.ruleForm.usrId,
            passwd: this.ruleForm.pass,
            admin:0
          })
          this.$axios.put('/api/users/update', toServerData).
          then(res=>{
            if(res.data===1){
              sessionStorage.clear()
              this.table = false,
              this.innerDrawer = false,
              setTimeout(() => {
                this.$router.push('/login');
              }, 2000)
            }
            else{
              alert('修改失败，请你反思一下是不是自己给我这sql注入呢~')
            }
          }
        )
        } else {
          console.log('error submit!! Check Data Format')
          return false
        }
      })

    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
    getRemoteData(item_class) {
      console.log(this.userInfo)
      console.log(item_class)
      this.category=item_class
      if(this.category==='book'){
        console.log('Request for Book Infos')
      this.$axios.get('/api/books/all', {
        params: {
          //category: item_class,
          pagesize: this.pageSize,
          pagenum: this.currentPage
        }
      }).then(
          res => {
            console.log('res_data=',res)
            console.log('totalElements:'+res.data.totalElements+'totalPages:'+res.data.totalPages)
            var convertData=this.convertState(res.data.content)
            this.tableData =convertData
            this.DataRaw = convertData
            this.total=res.data.totalElements
            this.total_raw=res.data.totalElements
          }
      )}
      else if(this.category==='paper'){
        console.log('Request for Paper Infos')

        this.$axios.get('/api/papers/all', {
          params: {
            //category: item_class,
            pagesize: this.pageSize,
            pagenum: this.currentPage
          }
        }).then(
            res => {
              console.log('res_data=',res)
              console.log('totalElements:'+res.data.totalElements+'totalPages:'+res.data.totalPages)
              this.tableData = res.data.content
              this.DataRaw = res.data.content

              this.total=res.data.totalElements
            }
        )
      }
      else{
        alert('category not initialized value:'+this.category)
      }
      console.log(
          {
            category: item_class,
            pageSize: this.pageSize,
            pageNum: this.currentPage
          })
      this.category = item_class
      this.currentPage = 1
    },

    handleCurrentChange(val) {
      //主要是处理页面显示 外带拿数据的功能
      this.currentPage = val;//当前页面显示
      console.log(`当前页: ${val}`)
      console.log('Data:' + this.currentPage)
      console.log({
        //category: this.category,
        pageSize: this.pageSize,
        pageNum: val
      })
      if(this.category==='book') {
        this.$axios.get('/api/books/all', {
          params: {
            pagesize: this.pageSize,
            pagenum: val
          }
        })
            .then(
                res => {
                  console.log(res)
                  var convertData=this.convertState(res.data.content)
                  this.tableData = convertData
                  this.DataRaw = convertData
                }
            )
      }
      else if(this.category==='paper'){
        this.$axios.get('/api/papers/all', {
          params: {
            pagesize: this.pageSize,
            pagenum: val
          }
        })
            .then(
                res => {
                  this.tableData = res.data.content
                  this.DataRaw = res.data.content
                }
            )
      }
      else{
        alert('请稍后再试~')
      }
    },
    openDrawer() {
      this.table = true
      this.$axios.get('/api/books/rentbook', {
        params:{
        user_email: this.email,
          // book_id:
        }
      }).then(
          res => {
            console.log(res)
            if(res.data[0].book_id===-2){
              this.grid_data=[]
            }
            else{
            this.grid_data = res.data
          }
          }
      )
    },
    toLogin() {
      this.$alert('您已退出登录')
      this.$router.push('/login')
      sessionStorage.clear()
    },
    book_back( book_id)
    {
      console.log({
        user_email: this.email,
        book_id: book_id,
      })
      this.$axios.get('/api/books/returnbook', {
        params:{
        user_email: this.email,
        book_id: book_id,
        }
      }).then(res=>{
        console.log(res)
        if(res.data[0].intro==='sb' || res.data[0].book_id===-1){
          alert('还书失败？又搁着给我请求接口？')
        }
        else if (res.data[0].book_id===-2){
          this.grid_data=[]
          alert('无已借阅书本')
        }
        else{
          alert('还书成功！请放回书架吧')
          this.grid_data=res.data
        }

      })
    },
  },

}
</script>

<style scoped>
.oridinary {
  position: relative;
  left: 38%;
  width: 23%;
  height: 78%;
  top: 34%;
}
</style>