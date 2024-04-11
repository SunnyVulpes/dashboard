<template>
  <div class="common-layout">
    <el-container>
      <el-header class="header">
        <p style="margin-top: 30px">智能Dashboard</p>
      </el-header>
      <el-container>
        <el-aside class="aside">
          <div class="aside-photo">
            <el-card style="width: 100%;">

              <img :src=photourl style="width: 50%;height:250px;" />
              <img src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"
                style="width: 50%" />
              <img src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"
                style="width: 50%" />
              <img src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"
                style="width: 50%;" />
            </el-card>

          </div>
          <el-card style="width:100%;height: 290px;">

            <div style="display: flex;justify-content: center;align-items: center;margin-top: 20px;">
              <div ref="speedpanel0" style="width:250px;height: 250px;"></div>
              <div ref="speedpanel1" style="width:250px;height: 250px;"></div>
            </div>

          </el-card>
        </el-aside>
        <el-container>
          <el-main style="max-height:50%;" class="main">
            <el-card style="width: 20%;height: 45%;">
              <div style="display: flex;justify-content: center;align-items: center;margin-top: -20px">
                <div ref="temppanel" style="width:250px;height: 250px;"></div>
              </div>
            </el-card>
            <!-- 温度折线图 -->
            <el-card style="width: 80%;height: 45%;">
              <div style="display: flex;justify-content: center;align-items: center;margin-top: -30px;">
                <div ref="tempLineChart" style="width: 1400px;height: 250px;"></div>
              </div>
            </el-card>
            <el-card style="width: 20%;height: 45%">
              <div style="display: flex;justify-content: center;align-items: center;margin-top: -20px">
                <div ref="humiditypanel" style="width:250px;height: 250px;"></div>
              </div>
            </el-card>
            <el-card style="width: 80%;height: 45%;">
              <div style="display: flex;justify-content: center;align-items: center;margin-top: -30px;">
                <div ref="humidityLineChart" style="width: 1400px;height: 250px;"></div>
              </div>
            </el-card>
          </el-main>
          <el-footer style="height:50%;" class="footer">
            <!-- 这里是日志模块 -->
            <el-card style="width: 70%;height: 100%;">
              <el-scrollbar height="200px">
                <p v-for="item in logList" :key="item.updatedDate" class="scrollbar-demo-item" :class="item.level">
                  {{formatLog(item)}}
<!--                  <div>`[{formatDate(new Date(item.updatedDate))}] ${"info".padEnd(5)} {{}}`</div>-->
<!--                  <div>[{{formatDate(new Date(item.updatedDate))}}]</div>-->
<!--                  <div style="margin-left: 16px">{{item.level}}</div>-->
<!--                  <div style="margin-left: 16px">{{item.comment}}</div>-->
                </p>
              </el-scrollbar>
            </el-card>
            <!-- 人数 -->
            <el-card style="width: 30%;height: 100%;">
              <div style="display: flex;justify-content: center;align-items: center;margin-top: 40px;">
                <div ref="peopleNumChart" style="width:350px;height: 350px;"></div>
              </div>
            </el-card>
          </el-footer>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>
<script>
import * as echarts from "echarts";
import { getspeed,getTempList, getCurTemp, getHumidityList, getCurHumidity, countPeople, getPhotoUrl, getPhotoUrl_4, getPeopleNum, getlog } from '../axios/api/api.js'
import Temp from "@/components/temp.vue";
import { ElDatePicker } from "element-plus";

export default {
  name: "MainDashboard",
  components: { Temp },
  // data() 返回的属性将会成为响应式的状态
  // 并且暴露在 `this` 上
  data() {
    return {
      calen: new Date(),
      tempChart: null,
      humidityChart: null,
      tempLineChart: null,
      humidityLineChart: null,
      peopleNumChart: null,
      myChart0: null,
      myChart1: null,
      count: 0,
      tempList: [],
      humidityList: [], // 用于存储温度列表的数据
      curTemp: 0, // 用于存储当前温度的数据
      curhumidity: 0,
      photourl: [],
      photourlarr: [],
      peoplenum: 0,
      logList: [
        {
          updatedDate: Date.now(),
          level: "info",
          comment: "皮卡丘钻进了仓库",
        },
        {
          updatedDate: Date.now(),
          level: "warn",
          comment: "皮卡丘钻进了仓库",
        }
      ],
      carspeed:0,
    }
  },

  // methods 是一些用来更改状态与触发更新的函数
  // 它们可以在模板中作为事件处理器绑定
  mounted() {
    this.initCharts()
    this.loadData()
    this.myEcharts()
  },

  methods: {
    formatLog(log) {
      const date = new Date(log.updatedDate).toISOString();
      const level = log.level.padEnd(5); // 确保日志级别占用5个字符宽度
      return `[${date}]   ${level}   ${log.comment}`;
    },

    formatDate(date) {
      const year = date.getFullYear()
      const month = String(date.getMonth() + 1).padStart(2, '0'); // 月份从0开始
      const day = String(date.getDate()).padStart(2, '0');
      const hours = String(date.getHours()).padStart(2, '0');
      const minutes = String(date.getMinutes()).padStart(2, '0');
      const seconds = String(date.getSeconds()).padStart(2, '0');

      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    },

    initCharts() {
      this.tempChart = echarts.init(this.$refs.temppanel);
      this.humidityChart = echarts.init(this.$refs.humiditypanel);
      this.tempLineChart = echarts.init(this.$refs.tempLineChart);
      this.humidityLineChart = echarts.init(this.$refs.humidityLineChart);
      this.peopleNumChart = echarts.init(this.$refs.peopleNumChart);
      this.myChart0 = echarts.init(this.$refs.speedpanel0);
      this.myChart1 = echarts.init(this.$refs.speedpanel1);
    },
    loadData() {
      getTempList().then((res) => {
        this.tempList = res
      })
      getCurTemp().then((res) => {
        this.curTemp = res
      })
      getHumidityList().then((res) => {
        this.humidityList = res
      })
      getCurHumidity().then((res) => {
        this.curhumidity = res
      })
      countPeople().then((res) => {
        this.countpeople = res
      })
      getPhotoUrl().then((res) => {
        this.photourl = res
      })
      getPhotoUrl_4().then((res) => {
        this.photourlarr = res
      })
      getPeopleNum().then((res) => {
        this.peoplenum = res
      })
      // getlog().then((res) => {
      //   this.logList = res
      // })
      getspeed().then((res) => {
        this.carspeed = res.speed
      })
    },
    myEcharts() {
      // 基于准备好的dom，初始化echarts实例

      let that = this
      let oneHour = 60 * 60 * 1000;  // 一小时的毫秒数
      let oneMinu = 60 * 1000;  // 一分钟的毫秒数
      let now = new Date();
      let base = +now - oneHour;  // 当前时间减去一小时
      let date = this.tempList.map(item => item.updateTime);
      let data = this.tempList.map(item => item.temperature);
      let humiditydata = this.humidityList.map(item => item.shidu);

      // setInterval(() => {
      //   this.curTemp++; // 每秒自增
      // }, 1000); // 设置定时器，每1000毫秒（即1秒）执行一次

      // 指定图表的配置项和数据
      this.myChart0.setOption({
        series: [
          {
            type: 'gauge',
            progress: {
              show: true,
              width: 12,
            },
            axisLine: {
              lineStyle: {
                width: 12
              }
            },
            axisTick: {
              show: false
            },
            splitLine: {
              length: 10,
              lineStyle: {
                width: 1.5,
                color: '#999'
              }
            },
            axisLabel: {
              distance: 15,
              color: '#999',
              fontSize: 12
            },
            anchor: {
              show: true,
              showAbove: true,
              size: 16,
              itemStyle: {
                borderWidth: 6
              }
            },
            title: {
              show: false
            },
            detail: {
              valueAnimation: true,
              fontSize: 18,
              offsetCenter: [0, '50%']
            },
            data: [
              {
                value: this.carspeed
              }
            ]
          }
        ]
      });
      this.myChart1.setOption({
        series: [
          {
            type: 'gauge',
            progress: {
              show: true,
              width: 12
            },
            axisLine: {
              lineStyle: {
                width: 12
              }
            },
            axisTick: {
              show: false
            },
            splitLine: {
              length: 10,
              lineStyle: {
                width: 1.5,
                color: '#999'
              }
            },
            axisLabel: {
              distance: 15,
              color: '#999',
              fontSize: 12
            },
            anchor: {
              show: true,
              showAbove: true,
              size: 15,
              itemStyle: {
                borderWidth: 6
              }
            },
            title: {
              show: false
            },
            detail: {
              valueAnimation: true,
              fontSize: 18,
              offsetCenter: [0, '50%']
            },
            data: [
              {
                value: this.curTemp
              }
            ]
          }
        ]
      });
      this.tempChart.setOption({
        series: [
          {
            type: 'gauge',
            center: ['50%', '60%'],
            startAngle: 200,
            endAngle: -20,
            min: 0,
            max: 60,
            splitNumber: 12,
            itemStyle: {
              color: '#FFAB91'
            },
            progress: {
              show: true,
              width: 10
            },
            pointer: {
              show: false
            },
            axisLine: {
              lineStyle: {
                width: 30
              }
            },
            axisTick: {
              distance: -15,
              splitNumber: 5,
              lineStyle: {
                width: 2,
                color: '#999'
              }
            },
            splitLine: {
              distance: -17,
              length: 5,
              lineStyle: {
                width: 3,
                color: '#999'
              }
            },
            axisLabel: {
              distance: -5,
              color: '#999',
              fontSize: 12
            },
            anchor: {
              show: false
            },
            title: {
              show: false
            },
            detail: {
              valueAnimation: true,
              width: '60%',
              lineHeight: 20,
              borderRadius: 8,
              offsetCenter: [0, '-10%'],
              fontSize: 20,
              fontWeight: 'bolder',
              formatter: '{value} °C',
              color: 'inherit'
            },
            data: [
              {
                value: this.curTemp
              }
            ]
          },
          {
            type: 'gauge',
            center: ['50%', '60%'],
            startAngle: 200,
            endAngle: -20,
            min: 0,
            max: 60,
            itemStyle: {
              color: '#FD7347'
            },
            progress: {
              show: true,
              width: 3
            },
            pointer: {
              show: false
            },
            axisLine: {
              show: false
            },
            axisTick: {
              show: false
            },
            splitLine: {
              show: false
            },
            axisLabel: {
              show: false
            },
            detail: {
              show: false
            },
            data: [
              {
                value: 20
              }
            ]
          }
        ]
      })
      this.humidityChart.setOption({
        series: [
          {
            type: 'gauge',
            center: ['50%', '60%'],
            startAngle: 200,
            endAngle: -20,
            min: 0,
            max: 100,
            splitNumber: 10,
            itemStyle: {
              color: '#B0E0E6'
            },
            progress: {
              show: true,
              width: 10
            },
            pointer: {
              show: false
            },
            axisLine: {
              lineStyle: {
                width: 30
              }
            },
            axisTick: {
              distance: -15,
              splitNumber: 5,
              lineStyle: {
                width: 2,
                color: '#999'
              }
            },
            splitLine: {
              distance: -17,
              length: 5,
              lineStyle: {
                width: 3,
                color: '#999'
              }
            },
            axisLabel: {
              distance: -5,
              color: '#999',
              fontSize: 12
            },
            anchor: {
              show: false
            },
            title: {
              show: false
            },
            detail: {
              valueAnimation: true,
              width: '60%',
              lineHeight: 20,
              borderRadius: 8,
              offsetCenter: [0, '-10%'],
              fontSize: 20,
              fontWeight: 'bolder',
              formatter: '{value} %',
              color: 'inherit'
            },
            data: [
              {
                value: this.curhumidity
              }
            ]
          },
          {
            type: 'gauge',
            center: ['50%', '60%'],
            startAngle: 200,
            endAngle: -20,
            min: 0,
            max: 90,
            itemStyle: {
              color: '#87CEEB'
            },
            progress: {
              show: true,
              width: 3
            },
            pointer: {
              show: false
            },
            axisLine: {
              show: false
            },
            axisTick: {
              show: false
            },
            splitLine: {
              show: false
            },
            axisLabel: {
              show: false
            },
            detail: {
              show: false
            },
            data: [
              {
                value: 20
              }
            ]
          }
        ]
      })
      this.tempLineChart.setOption({
        tooltip: {
          trigger: 'axis',
          position: function (pt) {
            return [pt[0], '10%'];
          }
        },

        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: date,  // 使用上面生成的 date 数组
          axisLabel: {
            fontSize: 10,  // 调整 X 轴标签字体大小
            formatter: function (value) {
              return value;  // 直接返回已格式化的时间字符串
            }
          }
        },

        yAxis: {
          type: 'value',
          min: 15,
          max: 25,
          interval: 1,
          axisLabel: {
            fontSize: 10  // 调整 Y 轴标签字体大小
          }
        },

        series: [
          {
            name: 'Fake Data',
            type: 'line',
            symbol: 'none',
            sampling: 'lttb',
            itemStyle: {
              color: 'rgb(255, 70, 131)'
            },

            areaStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 0,
                  color: 'rgb(255, 158, 68)'
                },
                {
                  offset: 1,
                  color: 'rgb(255, 70, 131)'
                }
              ])
            },
            data: data
          }
        ]
      })
      this.humidityLineChart.setOption({
        tooltip: {
          trigger: 'axis',
          position: function (pt) {
            return [pt[0], '10%'];
          }
        },

        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: date,  // 使用上面生成的 date 数组
          axisLabel: {
            fontSize: 10,  // 调整 X 轴标签字体大小
            formatter: function (value) {
              return value;  // 直接返回已格式化的时间字符串
            }
          }
        },

        yAxis: {
          type: 'value',
          min: 55,
          max: 65,
          interval: 1,
          axisLabel: {
            fontSize: 10  // 调整 Y 轴标签字体大小
          }
        },

        series: [
          {
            name: 'Fake Data',
            type: 'line',
            symbol: 'none',
            sampling: 'lttb',
            itemStyle: {
              color: '#87CEEB'
            },

            areaStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 0,
                  color: '#B0E0E6'
                },
                {
                  offset: 1,
                  color: 'rgb(255, 70, 131)'
                }
              ])
            },
            data: humiditydata
          }
        ]
      })
      this.peopleNumChart.setOption({
        tooltip: {
          trigger: 'item'
        },
        legend: {
          show: false,
        },
        series: [
          {
            name: 'Access From',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            label: {
              show: true,  // 确保标签总是显示
              position: 'center',  // 将标签位置设置在中心
              formatter: function (params) {
                // 这里直接返回了 peoplenum 的值和标签
                // 注意: 这里的 this.peoplenum 需要根据你的 Vue 组件中的数据属性来调整
                return `人数${this.peoplenum}/10`;
              }.bind(this),  // 使用 .bind(this) 以确保函数内部可以访问 Vue 组件的数据
              fontSize: 14,
              fontWeight: 'bold',
            },
            emphasis: {
              label: {
                show: true,
                fontSize: 14,
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: [
              { value: this.peoplenum, name: 'nowpeople', itemStyle: { color: '#ffe97a' } },  // 使用 peoplenum 作为值
              { value: 10 - this.peoplenum, name: 'Direct', itemStyle: { color: '#ff8e60' } }  // 假设总数是 10，计算 Direct 的值
            ]
          }
        ]
      }
      )
    }
  },
  watch: {
    curTemp(newVal) {
      this.myEcharts()
    },
    curhumidity(newVal) {
      this.myEcharts()
    },
    peoplenum(newVal) {
      this.myEcharts()
    },
    carspeed(newVal) {
      this.myEcharts()
    },
    humidityList(newVal) {
      this.myEcharts()
    },
    tempList(newVal) {
      this.myEcharts()
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
  display: flex;
  justify-content: center;
  align-content: center;
  height: 10vh;
  width: 100%;
  font-size: 40px;
  font-family: 阿里妈妈方圆体 VF Regular, sans-serif;
}

/* 在线链接服务仅供平台体验和调试使用，平台不承诺服务的稳定性，企业客户需下载字体包自行发布使用并做好备份。 */
@font-face {
  font-family: "阿里妈妈方圆体 VF Regular";src: url("//at.alicdn.com/wf/webfont/NvHjKPz9AuKF/VYIyKYykcdTe.woff2") format("woff2"),
url("//at.alicdn.com/wf/webfont/NvHjKPz9AuKF/7LXjWIl4g4Ob.woff") format("woff");
  font-display: swap;
}

.aside {
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

.scrollbar-demo-item {
  display: flex;
  align-items: center;
  justify-content: start;
  height: 32px;
  margin: 4px;
  text-align: center;
  border-radius: 4px;
  font-family: 'Courier New', Courier, monospace;
}

.info{
  background-color: rgba(107, 204, 104, 0.3);
}

.warn{
  background-color: rgba(205, 188, 95, 0.3);
}
</style>