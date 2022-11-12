<template>
  <div id="app">
    <meta content="width=device-width; initial-scale=1.0">
    <router-view/>
  </div>
</template>
<script>
import { defineComponent } from 'vue'

import 'element-plus/dist/index.css'
export default defineComponent({
  name: 'app',
  components: {

  },
})
</script>

<style>
*{
  margin: 0;
  padding: 0;
}
html,body,#app{
  height: 100%;
  width: 100%;
}
</style>