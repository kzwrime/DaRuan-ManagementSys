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
    <el-menu-item @click="goto_people()" >用户管理</el-menu-item>
    <el-menu-item >借阅管理</el-menu-item>
  </el-menu>

  <h1></h1>

  <!--搜索框-->
  <el-row>
  <!--搜索选择栏-->
  <el-select v-model="value" placeholder="搜索对象"  class="sel">
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
      <el-button type="primary" @click="onSubmit(formInline.user,value)">查询</el-button>
    </el-form-item>
  </el-form>
  </el-row>

  <!--卡片框-->
  <div class="block">
    <el-timeline>
      <el-timeline-item v-for="(activity, index) in activities"
                        :key="index"
                        :timestamp="activity.action_date"
                        :color="activity.action_id"
      >
        <el-card>
          <h2>{{activity.user_name}}&emsp;{{activity.action_type}}《{{activity.book_name}}》</h2>
          <el-row>
          <p>book-id：{{activity.book_id}} &emsp;</p>
          <p>book-author：{{activity.book_author}} &emsp;</p>
          <p>book-ISBN：{{activity.isbn}}</p>
          </el-row>
        </el-card>
      </el-timeline-item>
    </el-timeline>
  </div>

  <!--翻页框-->
  <div class="block1">
    <span class="demonstration"></span>
    <el-pagination
        @current-change="handleCurrentChange(currentPage)"
        v-model:currentPage="currentPage"
        :page-size="5"
        layout="total, prev, pager, next"
        :total=this.total
    >
    </el-pagination>
  </div>

</template>

<script>
export default {

  name: "borrow_manage",
  data(){

    return{
      //导航栏数据
      activeIndex2:'1',  //默认1为主页面

      //查询选择栏
      options: [
        {
          value: 'book_name',
          label: '书籍名称',
        },
        {
          value: 'user_id',
          label: '用户名称',
        },
      ],
      value: '',


      formInline: {
        user: '',//查询关键词
      },

      //翻页的参数
      currentPage: 1,
      //页面大小
      pageSize:5,
      //总信息条数
      total:20,

      activities:[

  ],
    }
  },

  mounted:function(){
    this.handleCurrentChange(1)
  },

  methods :{
    convertState(raw_data){
      console.log(raw_data)
      var display_data=[]
      for(var i=0;i<raw_data.length;i++){
        display_data.push(raw_data[i])
        if(raw_data[i].action_type===0){
          display_data[i].action_type='借阅了'
          display_data[i].action_id='#F56C6C'
        }
        else{
          display_data[i].action_type='归还了'
          display_data[i].action_id='#67C23A'
        }
      }
      return display_data
    },

    //导航栏切换
    handleSelect(key, keyPath) {
      console.log(key, keyPath)
    },

    //提交查询处理
    onSubmit(input,object) {
      console.log(input)
      console.log(object)
      if(object === 'book_name'){       //搜索对象是书籍名称
        console.log('.')
        this.$axios.get('/api/borrowing/search/bookname', {
          params: {
            book_name: input,
          }
        })
            .then(
                res => {
                  console.log(res)
                  this.activities = this.convertState(res.data)
                  this.total=res.data.length
                  this.pageSize=1000
                }
            )
      }
      else{            //搜索对象是用户名称
        console.log('..')
        this.$axios.get('/api/borrowing/search/username', {
          params: {
            user_name: input,
          }
        })
            .then(
                res => {
                  console.log(res)
                  //console.log(this.total)
                  this.activities = this.convertState(res.data)
                  this.total=res.data.length
                  this.pageSize=1000
                }
            )
      }

    },

    //主要是处理页面显示 外带拿数据的功能
    handleCurrentChange(val) {
      this.currentPage = val;//当前页面显示
      console.log(`当前页: ${val}`)
      console.log('Data:' + this.currentPage)
      console.log({
        pageSize: this.pageSize,
        pageNum: val
      })

      this.$axios.get('/api/borrowing/all', {
        params: {
          //category:this.category,
          pagesize: this.pageSize,
          pagenum: val
        }
      })
          .then(
              res => {
                console.log(res)
                var convertData=this.convertState(res.data.content)
                this.activities =convertData //this.convertState(res.data.content)
                this.total = res.data.totalElements
              }
          )
    },

    //路由
    goto_paper() {
      this.$router.replace('/paper_manage')
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
.sel{
  height: 10px;
  width: 100px;
}
.block{
  width: 1500px;
}
</style>