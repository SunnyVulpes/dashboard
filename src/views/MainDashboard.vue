<template>
  <div class="common-layout">
    <el-container>
      <el-header class="header">Header</el-header>
      <el-container>
        <el-aside class="aside">
          <div class="aside-photo">
            <el-card style="width: 100%;">

              <img src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"
                style="width: 50%;;" />
              <img src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"
                style="width: 50%" />
              <img src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"
                style="width: 50%" />
              <img src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"
                style="width: 50%;" />
            </el-card>

          </div>
          <el-card style="width:100%;">

          </el-card>
        </el-aside>
        <el-container>
          <el-main style="max-height:50%;" class="main">
            <el-card style="width: 20%;height: 45%;">

            </el-card>
            <el-card style="width: 80%;height: 45%;">
              {{ carData }}
            </el-card>
            <el-card style="width: 20%;height: 45%">

            </el-card>
            <el-card style="width: 80%;height: 45%;">

            </el-card>
          </el-main>
          <el-footer style="height:50%;" class="footer">
            <el-card style="width: 70%;height: 100%;"></el-card>
            <el-card style="width: 30%;height: 100%;"></el-card>
          </el-footer>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import http from '../axios/index.js';
import { getTempList, getCurTemp } from '../axios/api/api.js'
import Temp from "@/components/temp.vue";

export default {
  name: "MainDashboard",
  components: { Temp },
  // data() 返回的属性将会成为响应式的状态
  // 并且暴露在 `this` 上
  data() {
    return {
      count: 0,
      tempList: [], // 用于存储温度列表的数据
      curTemp: null, // 用于存储当前温度的数据
    }
  },

  // methods 是一些用来更改状态与触发更新的函数
  // 它们可以在模板中作为事件处理器绑定
  methods: {
    increment() {
      this.count++
    }
  },

  data() {
    return {
      carData: null,
    };
  },
  mounted() {
    this.fetchCarData();
  },
  methods: {
    async fetchCarData() {
      try {
        const response = await http.get('/car/get');
        this.carData = response.data;
      } catch (error) {
        console.error('Error fetching car data:', error);
        // 处理错误情况，例如显示错误消息
      }
    },
  },
};
</script>

<style scoped>
.common-layout {
  /* display: flex;
  flex-wrap: wrap; */
  height: 100vh;
}

.header {
  height: 10vh;
  background-color: yellow;
  width: 100%;
}

.aside {
  background-color: blue;
  width: 30%;
  height: 90vh;
}

.main {
  display: flex;
  flex-wrap: wrap;
}

.footer {
  display: flex;
  flex-wrap: wrap;
}

.aside-photo {
  display: flex;
  flex-wrap: wrap;
  background-color: orange;
  width: 100%;
  height: 65%;
}

.aside-photo-arr {
  background-color: white;
  border: black 1px solid;
  width: 50%;
  height: 50%;
}

.aside-speed {
  background-color: pink;
  width: 100%;
  height: 25%;
}
</style>