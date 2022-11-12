<template>
  <div style="background:linear-gradient(to bottom left,#f5f2e9,#108b96)">
    <el-row class="top" >
      <el-col :span="8">
        <!--      <img class="headlogo" src="../assets/pics/whulogo.png" alt=""-->
        <!--      style="position:absolute; max-width: inherit;left: 5%">-->
      <img src="../assets/pics/sleep.png" alt="" style="max-width:40px">
      </el-col>
      <el-col :span="16">
        <img class="headlab" src="../assets/pics/userHead.png" alt=""
             style="max-width: inherit;"
        >
      </el-col>
    </el-row>
    <div class="main" style="height: 100%">
      <el-row>
        <el-col :span="4">
          <el-menu
              default-active="1"
              class="menu-vertical"
              background-color=#4182a4
              text-color=white
              height=2860px
              style="border: #aed0ee"
          >
            <el-sub-menu index="1">
              <template #title>
                <i class="el-icon-location"></i>
                <span style="font-size: 20px; font-weight: normal;">资料类型</span>
              </template>
              <el-menu-item-group>
                <el-menu-item index="1-1" @click="getRemoteData('book')" style="font-size: 16px">图书</el-menu-item>
                <el-menu-item index="1-2" @click= "note" style="font-size: 16px">论文</el-menu-item>

              </el-menu-item-group>
            </el-sub-menu>
          </el-menu>
        </el-col>
        <el-col :span="20">
          <el-card body-style="position: relative; padding: 0px;margin: 0">
            <el-container class="search_container"
                          style="position: relative;
                    width:80%;left:10%;top: 0
">  <div style="width:80%;top: 2px">

              <el-input class="key_search" type="text" v-model="input" placeholder="请输入资料关键字(书名,作者,ISBN)"
                        style="width: 80%;"></el-input>
              <el-button class="search_btn" @click="searchInTable(input)">搜索</el-button>
            </div>
            </el-container>
            <el-container class="table-container" direction="vertical" style="
position: relative;min-height: 695px">
              <el-table :data="tableData" stripe border class="data-table" max-height="700px">

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
                        <span>{{ props.row.author}}</span>
                        <el-form-item label="书本介绍:">
                          <span>{{ props.row.book_intro }}</span>
                        </el-form-item>
                        <el-form-item label="书本状态:">
                          <span>{{ props.row.book_state }}</span>
                        </el-form-item>
                      </el-form-item>
                      <el-form-item label="出版时间:">
                        <span>{{ props.row.time }}</span>
                      </el-form-item>
                    </el-form>
                  </template>
                </el-table-column>
                <el-table-column prop="book_id" label="book-id"
                                 :filters=year_filter
                                 :filter-method="filterHandler"
                                 style="font-size: 15px">
                </el-table-column>
                <el-table-column prop="book_name" label="book-name"
                                 style="font-size: 15px"
                >
                </el-table-column>
                <el-table-column prop="book_state" label="book-state"
                                 :filters=state_filter
                                 :filter-method="filterHandler"
                                 style=""></el-table-column>
              </el-table>
              <div class="pagination">
                <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-size="10"
                    layout="total, prev, pager, next"
                    :total="200"
                    style="position: relative; left: 15%; border-color: white"
                >
                </el-pagination>
              </div>
            </el-container>
          </el-card>
        </el-col>
      </el-row>

    </div>
  </div>

</template>
<script>
export default {
  name: "userPage",
  data() {
    const item = [
      {
        book_id: "A1-309",
        book_name: "my implementation image colorization synthesis",
        book_author: "Leo dalkdaj jadjasjdja",
        book_state: "已借出",
        book_instro: "This is an test data",
        time: "2020-12-12 23:59:20"
      },
      {
        book_id: "A1-309",
        book_name: "self implementation image colorization synthesis",
        book_author: "Leo dalkdaj jadjasjdja",
        book_state: "已借出",
        book_instro: "This is an test data",
        time: "2020-12-12 23:59:20"
      },
      {
        book_id: "A1-309",
        book_name: "self implementation image colorization synthesis",
        book_author: "Leo dalkdaj jadjasjdja",
        book_state: "已借出",
        book_instro: "This is an test data",
        time: "2020-12-12 23:59:20"
      },
      {
        book_id: "A1-309",
        book_name: "self implementation image colorization synthesis",
        book_author: "Leo dalkdaj jadjasjdja",
        book_state: "已借出",
        book_instro: "This is an test data",
        time: "2020-12-12 23:59:20"
      },
      {
        book_id: "A1-309",
        book_name: "self implementation image colorization synthesis",
        book_author: "Leo dalkdaj jadjasjdja",
        book_state: "已借出",
        book_instro: "This is an test data",
        time: "2020-12-12 23:59:20"
      },
      {
        book_id: "A1-309",
        book_name: "self implementation image colorization synthesis",
        book_author: "Leo dalkdaj jadjasjdja",
        book_state: "已借出",
        book_instro: "This is an test data",
        time: "2020-12-12 23:59:20"
      },
      {
        book_id: "A1-309",
        book_name: "self implementation image colorization synthesis",
        book_author: "Leo dalkdaj jadjasjdja",
        book_state: "已借出",
        book_instro: "This is an test data",
        time: "2020-12-12 23:59:20"
      },
      {
        book_id: "A1-309",
        book_name: "self implementation image colorization synthesis",
        book_author: "Leo dalkdaj jadjasjdja",
        book_state: "已借出",
        book_instro: "This is an test data",
        time: "2020-12-12 23:59:20"
      },
      {
        book_id: "A1-309",
        book_name: "self implementation image colorization synthesis",
        book_author: "Leo dalkdaj jadjasjdja",
        book_state: "已借出",
        book_instro: "This is an test data",
        time: "2020-12-12 23:59:20"
      },
      {
        book_id: "A1-309",
        book_name: "self implementation image colorization synthesis",
        book_author: "Leo dalkdaj jadjasjdja",
        book_state: "已借出",
        book_instro: "This is an test data",
        time: "2020-12-12 23:59:20"
      },
      {
        book_id: "A1-309",
        book_name: "self implementation image colorization synthesis",
        book_author: "Leo dalkdaj jadjasjdja",
        book_state: "已借出",
        book_instro: "This is an test data",
        time: "2020-12-12 23:59:20"
      },
      {
        book_id: "A1-309",
        book_name: "self implementation image colorization synthesis",
        book_author: "Leo dalkdaj jadjasjdja",
        book_state: "已借出",
        book_instro: "This is an test data",
        time: "2020-12-12 23:59:20"
      },
      {
        book_id: "A1-309",
        book_name: "self implementation image colorization synthesis",
        book_author: "Leo dalkdaj jadjasjdja",
        book_state: "已借出",
        book_instro: "This is an test data",
        time: "2020-12-12 23:59:20"
      },
      {
        book_id: "A1-309",
        book_name: "self implementation image colorization synthesis",
        book_author: "Leo dalkdaj jadjasjdja",
        book_state: "已借出",
        book_instro: "This is an test data",
        time: "2020-12-12 23:59:20"
      },
      {
        book_id: "A1-309",
        book_name: "self implementation image colorization synthesis",
        book_author: "Leo dalkdaj jadjasjdja",
        book_state: "已借出",
        book_instro: "This is an test data",
        time: "2020-12-12 23:59:20"
      },
      {
        book_id: "A1-309",
        book_name: "self implementation image colorization synthesis",
        book_author: "Leo dalkdaj jadjasjdja",
        book_state: "已借出",
        book_instro: "This is an test data",
        time: "2020-12-12 23:59:20"
      },
      {
        book_id: "A1-309",
        book_name: "self implementation image colorization synthesis",
        book_author: "Leo dalkdaj jadjasjdja",
        book_state: "已借出",
        book_instro: "This is an test data",
        time: "2020-12-12 23:59:20"
      },
      {
        book_id: "A1-309",
        book_name: "self implementation image colorization synthesis",
        book_author: "Leo dalkdaj jadjasjdja",
        book_state: "已借出",
        book_instro: "This is an test data",
        time: "2020-12-12 23:59:20"
      },
      {
        book_id: "A1-309",
        book_name: "self implementation image colorization synthesis",
        book_author: "Leo dalkdaj jadjasjdja",
        book_state: "已借出",
        book_instro: "This is an test data",
        time: "2020-12-12 23:59:20"
      },
      {
        book_id: "A1-309",
        book_name: "self implementation image colorization synthesis",
        book_author: "Leo dalkdaj jadjasjdja",
        book_state: "已借出",
        book_instro: "This is an test data",
        time: "2020-12-12 23:59:20"
      },
      {
        book_id: "A1-309",
        book_name: "self implementation image colorization synthesis",
        book_author: "Leo dalkdaj jadjasjdja",
        book_state: "已借出",
        book_instro: "This is an test data",
        time: "2020-12-12 23:59:20"
      },
      {
        book_id: "A1-309",
        book_name: "self implementation image colorization synthesis",
        book_author: "Leo dalkdaj jadjasjdja",
        book_state: "已借出",
        book_instro: "This is an test data",
        time: "2020-12-12 23:59:20"
      },
      {
        book_id: "A1-309",
        book_name: "self implementation image colorization synthesis",
        book_author: "Leo dalkdaj jadjasjdja",
        book_state: "已借出",
        book_instro: "This is an test data",
        time: "2020-12-12 23:59:20"
      },
      {
        book_id: "A1-309",
        book_name: "self implementation image colorization synthesis",
        book_author: "Leo dalkdaj jadjasjdja",
        book_state: "已借出",
        book_instro: "This is an test data",
        time: "2020-12-12 23:59:20"
      },
      {
        book_id: "A1-309",
        book_name: "self implementation image colorization synthesis",
        book_author: "Leo dalkdaj jadjasjdja",
        book_state: "已借出",
        book_instro: "This is an test data",
        time: "2020-12-12 23:59:20"
      },
      {
        book_id: "A1-309",
        book_name: "self implementation image colorization synthesis",
        book_author: "Leo dalkdaj jadjasjdja",
        book_state: "已借出",
        book_instro: "This is an test data",
        time: "2020-12-12 23:59:20"
      },

    ]

    const year_filter = [
      {text: '2010', value: '2010'},
      {text: '2011', value: '2011'},
      {text: '2012', value: '2012'},
      {text: '2013', value: '2013'},
      {text: '2014', value: '2014'},
      {text: '2015', value: '2015'},
      {text: '2016', value: '2016'},
      {text: '2017', value: '2017'},
      {text: '2018', value: '2018'},
      {text: '2019', value: '2019'},
      {text: '2020', value: '2020'},
      {text: '2021', value: '2021'},
      {text: '2022', value: '2022'},
    ]
    const state_filter = [{text: '在架上', value: '在架上'},
      {text: '已借出', value: '已借出'}]
    return {
      DataRaw: item,
      tableData: item,
      year_filter,
      state_filter,
      input: '',
      currentPage: 2,

    }
  },
  methods: {
    filterHandler(value, row, column) {
      const property = column['property']
      return row[property] === value
    },
    remoteMethod(query) {
      if (query !== '') {
        this.loading = true
        setTimeout(() => {
          this.loading = false
          this.options = this.list.filter((item) => {
            return item.label.toLowerCase().indexOf(query.toLowerCase()) > -1
          })
        }, 200)
      } else {
        this.options = []
      }
    },
    getRemoteData(item_class) {
      console.log(item_class)
      if (item_class === 'book') {
        console.log('Request for Book Infos')
        this.$axios.get('/api/book')
            .then(
                res => {
                  console.log(res.data)
                  this.tableData = res.data
                  this.DataRaw = res.data
                }
            )
        //console.log(this.DataRaw)
      } else if (item_class === 'ins') {
        //@Todo 远程访问数据
      } else {
        console.log('已经展示了 啥也不用做')
      }
      return []
    },
    searchInTable(keyword) {
      var new_TableData = [];
      for (let i = 0; i < this.DataRaw.length; i++) {
        if (this.DataRaw[i].book_name.indexOf(keyword) !== -1) {
          new_TableData.push(this.DataRaw[i])
        }
      }
      this.tableData = new_TableData;
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`)

    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`)
    },
    note() {
      this.$alert('查看其余实验室资料信息，请注册后登录查看', '提示', {
        confirmButtonText: '确定',
        type: 'info',
        center: true,
      })
    }
  }
}
</script>

<style scoped>


</style>