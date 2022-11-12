<template>
  <!--子组件传值-->
  <el-card class="vagueChild" shadow="never" body-style="padding:0px"
           style="border:0px;position: relative;width: 60%;margin-bottom: 5px">
      <el-input class="vague_search" type="text" v-model="vagueKey.author"
                placeholder="作者名 作者名之间用#分隔 如: 小明#雷军"
                style="width: 100%;">
      </el-input>
    <br>
      <el-input class="vague_search" type="text" v-model="vagueKey.name"
                placeholder="资料名 关键词之间用#分隔 如：postgre#源码解析"
                style="width: 100%;">

      </el-input>
    <br>

      <el-button  type="primary"   class="vague_button"
                  @click="vagueSearch"
                  >
        <el-icon size="20px">
          <search />
        </el-icon>
      </el-button>
  </el-card>
</template>

<script>
export default {
  name: "VagueSearchBox",
  props:{
    category:{
      type:String,
      required:true
    },
    pagesize:{
      type:Number,
      required: true
    },
    pagenum:{
      type:Number,
      required:true
    }
  },
  data() {
    return {
      vagueKey: {
        author: '',
        year: '',
        name: '',
        dialogFormVisible: false,
      },
      res_data:[]
    }
  },
  methods: {
    vagueSearch() {
      var pattern = /[A-Za-z]+///字符串中出现了英文
      var nameInput=this.vagueKey.name.split('#')
      var authorInput=this.vagueKey.author.replace(/#/gi,',')


      var authorString=authorInput.slice(0,50>authorInput.length?authorInput.length:50)//取前4个关键字


      var nameArray=nameInput.slice(0,4>nameInput.length?nameInput.length:4)

      let split_name=''
      for(var i=0;i<nameArray.length;i++){
        var name=nameArray[i];
        if(pattern.test(name))//如果出现了英文 那么直接放进来
        {
          split_name+=name
          split_name+=','
        }
        else{//只有中文 需要做切分
          var split_CN_name=name.split('')
          for (var j=0;j<split_CN_name.length;j++){

            split_name+=split_CN_name[j]
            split_name+= ','
          }
        }
      }
      // console.log({
      //   authors: authorArray,
      //   names: nameArray,
      //   years: yearArray
      // })
      // this.$emit('returnData', [{
      //   book_id: "A1-309",
      //   book_name: "my implementation image colorization synthesis",
      //   book_author: "Leo dalkdaj jadjasjdja",
      //   book_state: "已借出",
      //   book_instro: "This is an test data",
      //   time: "2020-12-12 23:59:20"
      // }])
      console.log({
        category:this.category,
        authors:authorString,
        names:split_name,

      })
        var str={
          //category:this.category,
          pagesize:this.pagesize,
          pagenum:1,
          authors:authorString===''?'-1':authorString,
          names:split_name===''?'-1':split_name,
        }
        var toServer=this.$qs.stringify(str)
      console.log(toServer)
      if(this.category==='book'){
      this.$axios.post('/api/books/searchVague',toServer)
          .then(res=>
          {
            console.log(res)
            this.res_data=res.data
          this.$emit('returnData',this.res_data)
          }
      )
      }
      else if(this.category==='paper'){
        this.$axios.post('/api/papers/searchVague',toServer)
            .then(res=>
            {
              console.log(res)
              this.res_data=res.data
        this.$emit('returnData',this.res_data)
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