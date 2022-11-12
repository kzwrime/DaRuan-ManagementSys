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

    <el-menu-item index="1">图书管理</el-menu-item>
    <el-menu-item @click="goto_paper()">论文管理</el-menu-item>
    <el-menu-item @click="goto_people()">用户管理</el-menu-item>
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
    <el-button type="success" @click="handleNum">书籍入库
    </el-button>
    <el-dialog destroy-on-close title="书籍入库" v-model="dialogFormVisible" >
      <el-form
          :model="ruleForm"
          :rules="rules"
          ref="ruleForm"
          label-width="100px"
          class="demo-ruleForm"
      >
        <el-form-item label="书籍名称" prop="book_name">
          <el-input v-model="ruleForm.book_name"></el-input>
        </el-form-item>
        <el-form-item label="ISBN编号" prop="isbn">
          <el-input v-model="ruleForm.isbn"></el-input>
        </el-form-item>
        <el-form-item label="书籍作者" prop="book_author">
          <el-input v-model="ruleForm.book_author"></el-input>
        </el-form-item>
        <el-form-item label="书籍简介" prop="intro">
          <el-input v-model="ruleForm.intro"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')"
          >确定
          </el-button
          >
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>


      </el-form>
      <template #footer>
    <span class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取 消</el-button>
    </span>
      </template>
    </el-dialog>


  </el-row>


  <!--表格-->
  <el-table
      :data="tableData"
      height="700"
      border
      style="width: 100%">
    <el-table-column
        prop="book_id"
        label="book-id"
        width="180">
    </el-table-column>
    <el-table-column
        prop="isbn"
        label="book-ISBN"
        width="180">
    </el-table-column>
    <el-table-column
        prop="book_name"
        label="book-name"
        width="180">
    </el-table-column>
    <el-table-column
        prop="book_author"
        label="book-author"
        width="180">
    </el-table-column>
    <el-table-column
        prop="state"
        label="book-state"
        width="180">
    </el-table-column>
    <el-table-column
        prop="borrow_count"
        label="borrow-times"
        width="180">
    </el-table-column>
    <el-table-column
        prop="intro"
        label="book-intro"
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
              @click=""
              plain
          >编辑</el-button>
          -->
        <el-button type="info" plain @click="handleEdit(scope.$index, scope.row)">编辑</el-button>


        <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)"
            plain
        >删除
        </el-button>

      </template>
    </el-table-column>
  </el-table>


  <!-- 表格里面的编辑弹窗-->
  <el-dialog title="编辑书籍" v-model="dialogFormVisible1" destroy-on-close>
    <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
        class="demo-ruleForm1"
    >
      <el-form-item label="书籍名称" prop="book_name">
        <el-input v-model="ruleForm.book_name"></el-input>
      </el-form-item>
      <el-form-item label="ISBN编号" prop="isbn">
        <el-input v-model="ruleForm.isbn"></el-input>
      </el-form-item>
      <el-form-item label="书籍作者" prop="book_author">
        <el-input v-model="ruleForm.book_author"></el-input>
      </el-form-item>
      <el-form-item label="书籍简介" prop="intro">
        <el-input v-model="ruleForm.intro"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" plain @click="submitForm1('ruleForm')"
        >修改书籍信息
        </el-button
        >
        <el-button  plain @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
    <template #footer>
    <span class="dialog-footer">
      <el-button @click="dialogFormVisible1 = false">取 消</el-button>

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
  name: "HomePage",
  data() {
    return {
      //导航栏数据
      activeIndex2: '1',  //默认1为主页面


      //模糊查询参数
      block_tag: true,

      book_idd: '',
      holderr: '111',

      //增加书籍栏
      ruleForm: {
        book_name: '',
        isbn: '',
        book_author: '',
        intro: '',
      },
      rules: {
        book_name: [
          {required: true, message: '请输入书籍名称', trigger: 'blur'},

        ],
        isbn: [
          {required: true, message: '请输入ISBN编号', trigger: 'blur'},

        ],
        book_author: [
          {required: true, message: '请输入作者名称', trigger: 'blur'},

        ],
        intro: [
          {required: true, message: '请输入书籍介绍', trigger: 'blur'},

        ],
      },
      dialogFormVisible: false,//弹窗添加书籍
      formLabelWidth: '120px',


      //编辑书信息
      dialogFormVisible1: false,
      ruleForm1: {
        book_name1: '',
        isbn1: '',
        book_author1: '',
        intro1: '',

      },
      rules1: {
        book_name1: [
          {required: true, message: '请输入活动名称', trigger: 'blur' },

        ],
        isbn1: [
          {required: true, message: '请输入ISBN编号', trigger: 'blur'},

        ],
        book_author1: [
          {required: true, message: '请输入作者名称', trigger: 'blur'},

        ],
        intro1: [
          {required: true, message: '请输入书籍介绍', trigger: 'blur'},

        ],
      },
      formLabelWidth1: '120px',


      //查询栏
      formInline: {
        user: '',//查询关键词
      },


      //表格数据
      tableData: [],


      //翻页的参数
      currentPage: 1,
      //页面大小
      pageSize: 20,
      //总信息条数
      total: 200,

    }
  },
  mounted:function(){
    this.handleCurrentChange(1)
  },
  methods: {


    handleSelect(key, keyPath) {
      console.log(key, keyPath)
    },//导航栏切换

    onSubmit(input) {
      console.log(input)
      this.$axios.get('/api/books/search/exact', {
        params: {
          keywords: input,
          pagesize:this.pagesize,
          pagenum:this.currentPage
        }
      })
          .then(
              res => {
                console.log(res)
                this.tableData = res.data
                this.total=res.data.length//
              }
          )
    },//查询按钮


    //新建书籍//立即创建
    submitForm(formName) {
      if(this.ruleForm.book_name!=='' &&this.ruleForm.book_author!=='')
      {
          alert('提交成功')
          var toserver = this.$qs.stringify({
            book_name: this.ruleForm.book_name,
            book_author: this.ruleForm.book_author,
            isbn: this.ruleForm.isbn,
            intro: this.ruleForm.intro
          })
          console.log(formName.book_name, formName.book_author, formName.isbn, formName.intro)
          this.$axios.post('/api/books/insert', toserver)
              .then(
                  res => {
                    console.log(res)
                    this.handleCurrentChange(1)
                  }
              )
      }
      else{
        alert('请把信息填写完整')
      }

    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    }, //重置


    //表格里面的编辑框
    handleEdit(index, row) {
      this.dialogFormVisible1 = true
      console.log(index, row)
      this.holderr = row.holder
      this.book_idd = row.book_id

    },
    //修改书籍
    submitForm1() {

          alert('提交成功')
          console.log('book change info：',{
            book_name: this.ruleForm.book_name,
            book_author: this.ruleForm.book_author,
            isbn: this.ruleForm.isbn,
            intro: this.ruleForm.intro,
            book_id: this.book_idd,
            holder: this.holderr
          } )
          var toserver = this.$qs.stringify({
            book_name: this.ruleForm.book_name,
            book_author: this.ruleForm.book_author,
            isbn: this.ruleForm.isbn,
            intro: this.ruleForm.intro,
            book_id: this.book_idd,
            holder: this.holderr
          })
          //console.log(formName.book_name, formName.book_author, formName.isbn, formName.intro)

          this.$axios.put('/api/books/update', toserver)
              .then(
                  res => {
                    console.log(res)
                    this.holderr = ''
                    this.book_idd = ''
                  }
              )
        },
    resetForm1(formName1) {
      this.$refs[formName1].resetFields()
    }, //重置
    //立即创建


    //删除按钮
    handleDelete(index, row) {
      console.log(index, row)
      console.log(row.book_id)
      this.$axios.delete('/api/books/delete', {
            params: {
              //category:this.category,
              book_id: row.book_id,
            }
          }
      ).then(
          res => {
            console.log(res)
            this.tableData = res.data.content
            this.total = res.data.totalElements
          }
      )
      this.handleCurrentChange(1)
    },

    handleNum(){
      if (this.total<500){
        this.dialogFormVisible =true

      }
      else {
        alert('书架已满')
        this.dialogFormVisible =false
      }
    },
    //翻页处理
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
      this.$axios.get('/api/books/all', {
        params: {
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
    goto_paper() {
      this.$router.replace('/paper_manage')
    },
    goto_borrow() {
      this.$router.replace('/borrow_manage')
    },
    goto_people() {
      this.$router.replace('/people_manage')
    },


  },
}
</script>

<style scoped>
#app {

  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>