<template>
  <!--导航栏-->
  <el-menu
      :default-active="activeIndex2"
      class="el-menu-demo"
      mode="horizontal"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
      @select="handleSelect"
  >

    <el-menu-item @click="goto_book()">图书管理</el-menu-item>
    <el-menu-item @click="goto_paper()">论文管理</el-menu-item>
    <el-menu-item  >用户管理</el-menu-item>
    <el-menu-item @click="goto_borrow()">借阅管理</el-menu-item>
  </el-menu>
  <h1></h1>

  <!--搜索-->
  <el-row>
  <!--搜索选择栏-->
  <el-select v-model="value" placeholder="搜索对象"  class="sel" >
    <el-option
        v-for="item in options"
        :key="item.value"
        :label="item.label"
        :value="item.value"
    >
    </el-option>
  </el-select>
  <!--搜索框-->
  <el-form :inline="true" :model="formInline" class="demo-form-inline">
    <el-form-item >
      <el-input v-model="formInline.user" placeholder="查询关键词"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="select_user(formInline.user,value)">查询</el-button>
    </el-form-item>
  </el-form>
  </el-row>

  <!--用户信息-->
  <el-table :data="this.tableData" style="width: 100%" max-height="750">
    <el-table-column fixed prop="user_email" label="邮箱" width="200">
    </el-table-column>
    <el-table-column prop="user_name" label="姓名" width="200"> </el-table-column>
    <el-table-column prop="usrid" label="学号" width="300">
    </el-table-column>
    <el-table-column prop="passwd" label="密码" width="300"> </el-table-column>
    <el-table-column prop="admin" label="身份" width="250"> </el-table-column>
    <el-table-column fixed="right" label="操作" >
      <template #default="scope">
        <el-button
            size="mini"
            type="info"
            @click="update_user(scope.$index, scope.row)"
            plain
            class="remove"
        >修改信息</el-button
        >
        <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)"
            class="delete"
        >删除该用户</el-button
        >
      </template>
    </el-table-column>
  </el-table>

  <!-- 表格里面的编辑弹窗-->
  <el-dialog title="编辑用户信息" v-model="dialogFormVisible1">
    <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
        class="demo-ruleForm1"
    >
      <el-form-item label="用户姓名" prop="user_name">
        <el-input v-model="ruleForm.user_name"></el-input>
      </el-form-item>
      <el-form-item label="用户学号" prop="usrid">
        <el-input v-model="ruleForm.usrid"></el-input>
      </el-form-item>
      <el-form-item label="用户密码" prop="passwd">
        <el-input v-model="ruleForm.passwd"></el-input>
      </el-form-item>
      <el-form-item label="用户身份" prop="admin">
        <el-input v-model="ruleForm.admin"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm1('ruleForm')"
        >确认
        </el-button
        >
        <el-button @click="resetForm1('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
    <template #footer>
    <span class="dialog-footer">
      <el-button @click="dialogFormVisible1 = false">取 消</el-button>
      <el-button type="primary" @click="dialogFormVisible1 = false"
      >确 定</el-button
      >
    </span>
    </template>
  </el-dialog>

  <!--翻页框-->
  <div class="block">
    <span class="demonstration"></span>
    <el-pagination

        @current-change="handleCurrentChange"
        v-model:currentPage="currentPage"
        :page-size="20"
        layout="total, prev, pager, next"
        :total=this.total
    >
    </el-pagination>
  </div>

</template>

<script>
export default {
  name: "people_manage",
  data() {
    return {

      //翻页的参数
      currentPage: 1,

      //页面大小
      pageSize:20,
      //总信息条数
      total:200,


      formInline: {
        user: '',//查询关键词
      },

      //查询选择栏
      options: [
        {
          value: 'user_email',
          label: '用户邮箱',
        },
        {
          value: 'user_name',
          label: '用户名称',
        },
      ],
      value: '',

      ruleForm: {
        user_name: '',
        passwd: '',
        usrid: '',
        admin:'',
      },
      rules: {
        user_name: [
          {required: true, message: '请输入用户名称', trigger: 'blur'},

        ],
        passwd: [
          {required: true, message: '请输入用户密码', trigger: 'blur'},

        ],
        usrid: [
          {required: true, message: '请输入用户学号', trigger: 'blur'},

        ],
        admin: [
          {required: true, message: '请输入用户身份', trigger: 'blur'},

        ],
      },
      dialogFormVisible1: false,
      formLabelWidth: '120px',

      emaill:'',


      tableData: [
      ],
    }
  },

  mounted:function(){
    this.handleCurrentChange(1)
  },


  methods :{
    //路由
    goto_borrow() {
      this.$router.replace('/borrow_manage')
    },
    goto_book() {
      this.$router.replace('/book_manage')
    },
    goto_paper() {
      this.$router.replace('/paper_manage')
    },

    //查询按钮
    select_user(input,object) {
      console.log('输入',input)
      console.log('duix',object)
      if(object === 'user_email'){//邮箱搜索
        console.log('这里是邮箱')
        this.$axios.get('/api/users/search/email', {
          params: {
            email: input,
          }
        })
            .then(
                res => {
                  var tmp = []
                  tmp.push(res.data)
                  console.log(tmp)
                  this.tableData = tmp
                  this.total = 1

                }
            )
      }
      else{
        console.log('这里是用户名')
        this.$axios.get('/api/users/search/name', {
          params: {
            name: input,
          }
        })
            .then(
                res => {
                  console.log(res)
                  console.log(res.data)
                  this.tableData = res.data
                  this.total = res.data.length
                }
            )
      }

    },

    //更新信息
    update_user(index, row) {
      this.dialogFormVisible1 = true
      console.log(row.user_email)
      this.emaill = row.user_email
    },


//修改书籍
    submitForm1(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          alert('提交成功')
          var toserver = this.$qs.stringify({
            admin:this.ruleForm.admin,
            name:this.ruleForm.user_name,
            passwd:this.ruleForm.passwd,
            usrid:this.ruleForm.usrid,
            email:this.emaill
          })
          //console.log(formName.book_name, formName.book_author, formName.isbn, formName.intro)
          //console.log('bookid+', this.book_idd)
          this.$axios.put('/api/users/update', toserver)
              .then(
                  res => {
                    console.log(res)
                    this.emaill = ''
                  }
              )
        } else {
          console.log('error submit!!')
          return false
        }

      })
    },//立即创建
    resetForm1(formName) {
      this.$refs[formName].resetFields()
    }, //重置




    //删除用户
    handleDelete(index, row){
      console.log(index, row)
      console.log(row.user_email)
      this.$axios.delete('/api/users/admin/delete', {
        params: {
          email: row.user_email,
        }
      })
          .then(
              res => {
                this.tableData = res.data.content
                this.total = res.data.totalElements

              }
          )
      this.handleCurrentChange(1)
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
      this.$axios.get('/api/users/all', {
        params: {
          pagesize: this.pageSize,
          pagenum: val
        }
      })
          .then(
              res => {
                console.log(res)
                this.tableData = res.data.content
                this.total = res.data.totalElements
              }
          )
    },


  }
}
</script>

<style scoped>
.sel{
  height: 10px;
  width: 100px;
}
.set{
  width: 80px;
}
.remove{
  width: 80px;
}
.delete{
  width: 80px;
}
</style>