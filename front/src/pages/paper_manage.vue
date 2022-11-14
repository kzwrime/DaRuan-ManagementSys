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
    <el-menu-item >论文管理</el-menu-item>
    <el-menu-item @click="goto_people()" >用户管理</el-menu-item>
    <el-menu-item @click="goto_borrow()">借阅管理</el-menu-item>
  </el-menu>

  <h1></h1>


  <!--把搜索和书籍入库放到一行-->
  <el-row>
    <!--搜索-->
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item label="请输入查询关键词：">
        <el-input v-model="formInline.user" placeholder="查询关键词"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit(formInline.user)">查询</el-button>
      </el-form-item>
    </el-form>



    <!--书籍入库弹窗后的具体信息输入-->
    <el-button type="success"  @click="dialogFormVisible = true">论文入库
    </el-button>
    <el-dialog title="论文入库" v-model="dialogFormVisible">
      <el-form
          :model="ruleForm"
          :rules="rules"
          ref="ruleForm"
          label-width="100px"
          class="demo-ruleForm"
      >
        <el-form-item label="论文名称" prop="paper_name">
          <el-input v-model="ruleForm.paper_name"></el-input>
        </el-form-item>
        <el-form-item label="论文地址" prop="paper_url">
          <el-input v-model="ruleForm.paper_url"></el-input>
        </el-form-item>
        <el-form-item label="论文作者" prop="paper_author">
          <el-input v-model="ruleForm.paper_author"></el-input>
        </el-form-item>
        <el-form-item label="论文简介" prop="paper_intro">
          <el-input v-model="ruleForm.paper_intro"></el-input>
        </el-form-item>
        <el-form-item label="出版时间" prop="paper_year">
          <el-input v-model="ruleForm.paper_year"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button plain type="primary" @click="submitForm('ruleForm')"
          >确定</el-button
          >
          <el-button  plain @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </el-row>



  <!--表格-->
  <el-table
      :data="tableData"
      height="700"
      border
      style="width: 100%">
    <el-table-column
        prop="paper_id"
        label="paper-id"
        width="180">
    </el-table-column>
    <el-table-column
        prop="paper_url"
        label="paper-address"
        width="180">
    </el-table-column>
    <el-table-column
        prop="paper_name"
        label="paper-name"
        width="180">
    </el-table-column>
    <el-table-column
        prop="paper_author"
        label="paper-author"
        width="180">
    </el-table-column>
    <el-table-column
        prop="paper_year"
        label="borrow-publish"
        width="180">
    </el-table-column>
    <el-table-column
        prop="paper_click_times"
        label="click-times"
        width="180">
    </el-table-column>
    <el-table-column
        prop="paper_intro"
        label="paper-intro"
    >
    </el-table-column>
    <el-table-column
        fixed="right"
        label="操作"
        width="200">
      <template v-slot="scope">

        <!--  <el-button
              size="mini"
              type="info"
              @click="handleEdit(scope.$index, scope.row)"
              plain
          >编辑</el-button>
          -->
        <el-button type="info" plain @click="handleEdit(scope.$index, scope.row,scope.$index.dialogFormVisible1)">编辑
        </el-button>

        <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)"
            plain
        >删除</el-button>

      </template>
    </el-table-column>
  </el-table>

<!-- 表格里面的编辑弹窗-->
  <el-dialog title="论文修改" v-model="dialogFormVisible1">
    <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
        class="demo-ruleForm"
    >
      <el-form-item label="论文名称" prop="paper_name">
        <el-input v-model="ruleForm.paper_name"></el-input>
      </el-form-item>
      <el-form-item label="论文地址" prop="paper_url">
        <el-input v-model="ruleForm.paper_url"></el-input>
      </el-form-item>
      <el-form-item label="论文作者" prop="paper_author">
        <el-input v-model="ruleForm.paper_author"></el-input>
      </el-form-item>
      <el-form-item label="论文简介" prop="paper_intro">
        <el-input v-model="ruleForm.paper_intro"></el-input>
      </el-form-item>
      <el-form-item label="出版时间" prop="paper_year">
        <el-input v-model="ruleForm.paper_year"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm1('ruleForm')"
        >确定</el-button
        >
        <el-button @click="resetForm1('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
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
  name: "paper_manage",
  data() {
    return {
      //导航栏数据
      activeIndex2:'1',  //默认1为主页面

      block_tag:true,//模糊查询


      //翻页的参数
      currentPage: 1,
      //页面大小
      pageSize:20,
      //总信息条数
      total:200,

      //增加论文栏
      ruleForm: {
        paper_name: '',
        paper_url: '',
        paper_author:'',
        paper_intro:'',
        paper_year:'',
        paper_click_times:'',
        paper_id:'',


      },
      rules: {
        paper_name: [
          {required: true, message: '请输入论文名称', trigger: 'blur'},

        ],
        paper_url: [
          {required: true, message: '请输入论文地址', trigger: 'blur'},

        ],
        paper_author: [
          {required: true, message: '请输入论文作者名称', trigger: 'blur'},

        ],
        paper_intro: [
          {required: true, message: '请输入论文介绍', trigger: 'blur'},

        ],
        paper_year: [
          {required: true, message: '请输入出版时间', trigger: 'blur'},

        ],
      },
      dialogFormVisible: false,//弹窗添加书籍
      formLabelWidth: '120px',


      //编辑论文信息
      dialogFormVisible1: false,
      ruleForm1: {
        paper_name1: '',
        paper_address1: '',
        paper_author1:'',
        paper_intro1:'',
        paper_publish1:'',

      },
      rules1: {
        paper_name1: [
          {required: true, message: '请输入论文名称', trigger: 'blur'},

        ],
        paper_address1: [
          {required: true, message: '请输入论文地址', trigger: 'blur'},

        ],
        paper_author1: [
          {required: true, message: '请输入作者名称', trigger: 'blur'},

        ],
        paper_intro1: [
          {required: true, message: '请输入论文介绍', trigger: 'blur'},

        ],
        paper_publish1: [
          {required: true, message: '请输入出版时间', trigger: 'blur'},

        ],
      },
      formLabelWidth1: '120px',


      //查询栏
      formInline: {
        user: '',//查询关键词
      },


      idd:'',
      paper_click:'',


      //表格数据
      tableData: [
      ],
    }
  },

  mounted:function(){
    this.handleCurrentChange(1)
  },

  methods :{


    handleSelect(key, keyPath) {
      console.log(key, keyPath)
    },//导航栏切换


    //查询按钮
    onSubmit(input) {
      console.log(input)
      var toServer=this.$qs.stringify({
        keywords:input,
        pagesize:this.pagesize,
        pagenum:this.currentPage
      })
      this.$axios.post('/api/papers/search/exact', toServer)
          .then(
              res => {
                console.log(res)
                this.tableData = res.data
              }
          )
    },


    //新建书籍
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          alert('提交成功')
          var toserver = this.$qs.stringify({
            paper_name: this.ruleForm.paper_name,
            paper_author: this.ruleForm.paper_author,
            publish_year: this.ruleForm.paper_year,
            paper_intro: this.ruleForm.paper_intro,
            paper_add:this.ruleForm.paper_url
          })
          this.$axios.post('/api/papers/insert', toserver)
              .then(
                  res => {
                    console.log(res)
                  }
              )
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },//立即创建

    resetForm(formName) {
      this.$refs[formName].resetFields()
    }, //重置



    handleEdit(index, row) {
      this.dialogFormVisible1 = true
      console.log(index, row)

      this.idd = row.paper_id
      this.paper_click = row.paper_click_times
    },


    //修改书籍
    submitForm1(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          alert('提交成功')
          var toserver = this.$qs.stringify({
            paper_name: this.ruleForm.paper_name,
            paper_author: this.ruleForm.paper_author,
            publish_year: this.ruleForm.publish_year,
            paper_intro: this.ruleForm.paper_intro,
            paper_id: this.idd,
            paper_add: this.ruleForm.paper_url,
            paper_click_times:this.paper_click
          })
          //console.log(formName.book_name, formName.book_author, formName.isbn, formName.intro)
          console.log('paperid+', this.idd)
          this.$axios.put('/api/papers/update', toserver)
              .then(
                  res => {
                    console.log(res)
                    this.paper_click = ''
                    this.idd = ''
                  }
              )
        } else {
          console.log('error submit!!')
          return false
        }

      })
    },//立即创建

    resetForm1(formName1) {
      this.$refs[formName1].resetFields()
    }, //重置




    //删除按钮
    handleDelete(index, row) {
      console.log(index, row)
      console.log(row.paper_id)
      console.log(row.paper_name)
      this.$axios.delete('/api/papers/delete', {
        params: {
          paper_id:row.paper_id
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





    //翻页处理函数
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
      this.$axios.get('/api/papers/all', {
        params: {
          //category:this.category,
          pagesize: this.pageSize,
          pagenum: val
        }
      })
          .then(
              res => {
                this.tableData = res.data.content
                this.total = res.data.totalElements
              }
          )
    },


    //路由
    goto_borrow() {
      this.$router.replace('/borrow_manage')
    },
    goto_book() {
      this.$router.replace('/book_manage')
    },
    goto_people() {
      this.$router.replace('/people_manage')
    },
  }
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