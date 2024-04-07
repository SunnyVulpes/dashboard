<script>
import Temp from "@/components/temp.vue";

export default {
  name: "MainDashboard",
  components: {Temp}
}
</script>

<template>
  <div>这里是主页面的入口</div>
  <div>整体dashboard的布局写在这里</div>

<!--  temp为components文件夹下的组件，以下代码仅作示例-->
  <el-card><temp></temp></el-card>
</template>

<style scoped>

</style>