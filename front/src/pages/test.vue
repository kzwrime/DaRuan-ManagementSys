<template>
  <div class="background" style="background-color: #aed0ee">
  <el-row>
    <el-col :xs="8" :sm="6" :md="4" :lg="3" :xl="1" style="background-color: #aed0ee">
      <div class="grid-content">
        <el-menu
            default-active="1"
            class="menu-vertical"
            background-color="#545c64"
            text-color="#fff"
        >
          <el-sub-menu index="1">
            <template #title>
              <i class="el-icon-location"></i>
              <span style="font-size: 20px; font-weight: normal;border-radius: 30px">资料类型</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="1-1" @click="getRemoteData('book')" style="font-size: 16px">图书</el-menu-item>
              <el-menu-item index="1-2" @click="getRemoteData('paper')" style="font-size: 16px">论文</el-menu-item>
              <el-menu-item index="1-3" @click="getRemoteData('ins')" style="font-size: 16px">设备</el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
        </el-menu>
    </div>
    </el-col>
    <el-col :xs="16" :sm="18" :md="20" :lg="21" :xl="23">
      <div class="right-content">
        <el-container class="search_container">
          <el-input class="key_search" type="text" v-model="input" placeholder="请输入想查找资料名称关键字"></el-input>
          <el-button class="search_btn" @click="searchInTable(input)">搜索</el-button>
        </el-container>
        <el-table :data="tableData" stripe border class="data-table">
          <el-table-column type="expand">
            <template #default="props">
              <el-form label-position="center" inline class="table-expand">
                <el-form-item label="ID:">
                  <span>{{ props.row.book_id }}</span>
                </el-form-item>
                <el-form-item label="名称:">
                  <span>{{ props.row.book_name }}</span>
                </el-form-item>
                <el-form-item label="书本介绍:">
                  <span>{{ props.row.book_intro }}</span>
                </el-form-item>
                <el-form-item label="书本状态:">
                  <span>{{ props.row.book_state }}</span>
                </el-form-item>
                <el-form-item label="书本状态:">
                  <span>{{ props.row.holder }}</span>
                </el-form-item>
                <el-form-item label="出版时间:">
                  <span>{{ props.row.time }}</span>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column prop="book_id" label="book-id" width="160"
                           :filters=year_filter
                           :filter-method="filterHandler"
                           style="font-size: 15px">
          </el-table-column>
          <el-table-column prop="book_name" label="book-name" width="160"
                           style="font-size: 15px"
          >
          </el-table-column>
          <el-table-column prop="book_state" label="book-state" width="160"
                           :filters=state_filter
                           :filter-method="filterHandler"
                           style=""></el-table-column>
          <el-table-column prop="table_btn" label=" " width="200">
            <el-button-group>
              <el-button>借出</el-button>
              <el-button>还书</el-button>
            </el-button-group>
          </el-table-column>
        </el-table>
      </div>
    </el-col>
  </el-row>
  </div>
</template>

<!-- comments are here -->
<!-- nothing to comment on... -->
<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "test"
}
</script>

<style>
.el-col {
  border-radius: 32px;
}
.con2{
  height: 100px;
}
.con1{
  height: 200px;
  background-color: #45493d;
}
.bg-purple-dark {
  background: #99a9bf;
}
.bg-purple {
  background: #d3dce6;
}
.bg-purple-light {
  background: #e5e9f2;
}
.grid-content {
  border-radius: 0px;
  height: 100%;
  min-width: 120px;
  /*min-height:100px;*/
  /*max-height: 100%;*/

}
.right-content{

  min-height: 100px;
  min-width: 120px;
}
.left{
  height: 100%;
  background-color: green;
}
.right{

}
</style>
<style scoped>
.top {
  position: absolute;
  height: 15%;
  width: 100%;
  background-color: #aed0ee;
}

.head_img {
  position: relative;
  height: 80%;
  left: 15%;
  margin: 0;
  padding: 0;
}

.head_logo {
  position: relative;
  margin: 0;
  padding: 0;
  height: 70%;
  left: 5%;
}

.menu-container {
  position: relative;
  left: 0%;
  width: 100%;
  height: 100%;
  background-color: #545c64;
  border-radius: 30px;
}



.main {
  position: absolute;
  top: 15%;
  left: 0%;
  width: 100%;
  height: 70%;
  background-color: #aed0ee;
}

.search_container {
  position: relative;
  left: 0;
  width: 100%;
  height: 8%;
  background-color: white;
}

.search_btn {
  position: relative;
  left: 5%;
  top: 2%;
  background-color: #ffdeeb;
  color: #45493d;
}

/*.data-table {*/
/*  position: absolute;*/
/*  top: 8%;*/
/*  width: 75%;*/
/*  left: 20%;*/
/*  border-radius: 0px;*/
/*  min-height: 500px;*/
/*}*/

.pagination {
  position: absolute;
  bottom: 1%;
  width: 75%;
  left: 20%;
  background-color: white;
  border-radius: 10px;
}

.table-expand {
  position: relative;
  left: 10%;
  font-size: 0;
  width: 50%;
}

.table-expand label {
  width: 90px;
  color: #99a9bf;
  left: 10%;
}

.table-expand el-form-item {
  position: absolute;
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
  left: 10%;
}

.key_search {
  left: 5%;
  width: 30%;
  border-width: 5px;
  top: 2%;
  height: 7%;
}

.bottom {
  position: absolute;
  top: 85%;
  height: 15%;
  width: 100%;
  background-color: #aed0ee;
}

</style>