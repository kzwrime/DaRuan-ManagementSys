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
html,body{
  height: 100%;
  width: 100%;
  margin: 0;
  padding: 0;
}
#app{
  width:100%;
  height: 100%;
  margin: 0;
  padding: 0;
}
</style>