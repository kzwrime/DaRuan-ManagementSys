<template>
  <el-table :data=this.data stripe border class="data-table" max-height="690px"
  min-height="685px">
    <el-table-column type="expand">
      <template #default="props">
        <el-form label-position="center" inline class="table-expand"
                 style="position: relative;left:5%;width: 90%">
          <el-form-item label="ID:">
            <span>{{ props.row.paper_id }}</span>
          </el-form-item>
          <el-form-item label="名称:">
            <span>{{ props.row.paper_name }}</span>
          </el-form-item>
          <el-form-item label="论文作者:">
            <span>{{ props.row.paper_author }}</span>
          </el-form-item>
          <el-form-item label="论文介绍:">
            <span>{{ props.row.paper_intro }}</span>
            <br>
          </el-form-item>
          <el-form-item label="论文网址:">
            <span>{{ props.row.paper_url }}</span>
          </el-form-item>
          <el-form-item label="出版时间:">
            <span>{{ props.row.paper_year }}</span>
          </el-form-item>
        </el-form>
      </template>
    </el-table-column>
    <el-table-column prop="paper_year" label="paper-year"
                     :filters=filter
                     :filter-method="filterHandler"
                     style=""></el-table-column>

    <el-table-column prop="paper_name" label="paper-name"
                     style="font-size: 15px"
    >
    </el-table-column>
    <el-table-column prop="paper_author" label="paper-author"
                     style=""></el-table-column>
    <el-table-column style="" label="Source">
      <template v-slot="scope">
        <el-button round type="success" @click="paperUse(scope.row)">
          <el-icon>
            <d-arrow-right />
          </el-icon>
        </el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

import {Edit} from "@element-plus/icons";
<script lang="js">
export default {
  name: "paper_table",
  components: {},

  props: {
    data: {
      type: Array,
      required: true
    },
    filter: {
      type: Array,
      required: true
    }
  },
  methods: {
    filterHandler(value, row, column) {
      const property = column['property']
      return row[property] === value
    },
    paperUse(row) {
      console.log(row.paper_id)
      this.$axios.get('/api/papers/clickclick', {
        params:
            {
              paper_id: row.paper_id
            }
      })
      window.open(row.paper_url)
    }
  }
}
</script>

<style scoped>

</style>