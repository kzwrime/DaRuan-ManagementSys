<template>
  <el-input class="key_search" type="text" v-model="accKey" placeholder="请输入资料关键字(书名,作者,ISBN)"
            style="width: 60%; margin-bottom: 10px"></el-input>
  <el-button type="success" class="search_btn" @click="keywordSearch(accKey)">
    <el-icon size="20px">
      <search />
    </el-icon>

  </el-button>
</template>

<script>
export default {
  name: "searchBox",
  props:{
    category:{
      type:String,
      required:true
    }
  },
  data(){
    return {
      accKey:'',
      retData:[]
    }
  },
  methods:{
    keywordSearch(accKey){
      if (accKey===''){
        this.$emit('returnData',-1)
        return
      }
      if (this.category==='book'){
      this.$axios.get('/api/books/search/exact',{
        params:{
          keywords:accKey
        }
      }).then(
          res=>{
            console.log(res)
            this.retData=res.data
            this.$emit('returnData',this.retData)//=访问OK，数据过不来
          }
      )
    }
      else if (this.category==='paper'){
        var toServer=this.$qs.stringify({
            keywords:accKey
          })
        this.$axios.post('/api/papers/search/exact',toServer)
            .then(
            res=>{
              console.log(res)
              this.retData=res.data
              this.$emit('returnData',this.retData)
            }
        )
      }
      else {
        alert('Father value transfer error')
      }
    }
  }
}
</script>

<style scoped>

</style>