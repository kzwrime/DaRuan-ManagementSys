<template>
  <el-table
      :data=this.data
      stripe border
      class="data-table"
      max-height="690px"
      min-height="685px">
    <el-table-column type="expand">
      <template #default="props">
        <el-form label-position="center" inline class="table-expand"
                 style="position: relative;left:5%;width: 80%">
          <el-form-item label="ID:">
            <span>{{ props.row.book_id }}</span>
          </el-form-item>
          <el-form-item label="名称:">
            <span>{{ props.row.book_name }}</span>
          </el-form-item>
          <el-form-item label="书本作者:">
            <span>{{ props.row.book_author }}</span>
          </el-form-item>
            <el-form-item label="书本介绍:">
              <span>{{ props.row.intro }}</span>
            </el-form-item>
            <el-form-item label="书本状态:">
              <span>{{ props.row.state }}</span>
            </el-form-item>

<!--          <el-form-item label="出版时间:">-->
<!--            <span>{{ props.row.time }}</span>-->
<!--          </el-form-item>-->
        </el-form>
      </template>
    </el-table-column>
    <el-table-column prop="locate" label="locate"
                     style="font-size: 15px">
    </el-table-column>
    <el-table-column prop="book_name" label="book-name"
                     style="font-size: 15px"
    >
    </el-table-column>
    <el-table-column prop="state" label="book-state"
                     :filters=state_filter
                     :filter-method="filterHandler"
                     style=""></el-table-column>
    <el-table-column prop="table_btn" label="操作">
      <template v-slot="scope">
      <el-button-group>
        <el-button  plain @click="reserveBook(scope.row)" type="primary" style="border: #108b96">借出
        </el-button>

      </el-button-group>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
export default {
  name: "book_table",

  props: {
    data:{
      type:Array,
      required:true
    },
    user_email:{
      required: true
    },
    },
    data(){
      const state_filter=[{text: '在架上', value: '在架上'},{text: '已借出', value: '已借出'}]
      return {
        state_filter
      }

  },
  methods: {
  filterHandler(value, row, column) {
    const property = column['property']
    return row[property] === value
  },
    reserveBook(row){
    var bookid=row.book_id
    console.log(bookid,this.user_email)
      //post请求必须用qs包一层
      // var toServerData=this.$qs.stringify({
      //   email:this.email,
      //   book_id:book_id
      // })
      this.$axios.get('/api/books/borrowbook',{
        params:{
          user_email:this.user_email,
          book_id:row.book_id

        }
      })
      .then(res=>{
        console.log(res)
        if(res.data===1){
          alert('借阅成功，去书架取书把')
          location.reload()
        }
        else if (res.data===-1){
          alert('已经被人借了，你看看是不是你借的')
        }
        else{
          alert('服务器忙，请刷新页面后重试')
        }
      })
    }
}
}

</script>

<style scoped>

</style>