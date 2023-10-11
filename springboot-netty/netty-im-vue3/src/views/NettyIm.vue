<template>
  <div style="height: 100dvh">
    <el-container>
      <el-header>
        <h3>WebSocket聊天</h3>
        <div class="flex items-center">
          <span class="text-lg font-medium mr-4"> 连接状态: </span>
          <el-tag :color="getTagColor">{{ status }}</el-tag>
        </div>
        <el-form :model="websocketForm" :rules="rules" ref="ruleFormRef">
          <el-form-item style="align-items: center" required label="websocket地址：" prop="server">
            <el-select v-model="websocketForm.server" style="width: 350px" filterable allow-create clearable
                       placeholder="websocket地址">
              <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
            <el-button type="primary" @click="toggle(ruleFormRef)">{{ getIsOpen ? '关闭连接' : '连接' }}</el-button>
          </el-form-item>
          <el-form-item>
            <div style="margin-top: 10px">
              <span>昵称：</span>
              <el-input v-model="command.nickname" style="width: 150px;margin-right: 10px"></el-input>
              <el-button type="primary" @click="connect">上线</el-button>
            </div>
          </el-form-item>
        </el-form>
      </el-header>
      <el-container>
        <el-aside>
          <el-card style="margin: 0px auto" shadow="always">
            <template #header>
              <span>私聊人员名单</span>
            </template>
            <el-row :gutter="20" v-for="item in userList" :key="item" style="margin-bottom: 5px">
              <el-col :span="8"></el-col>
              <el-col :span="8">
                <el-button type="primary" plain style="width: 100%;">
                  {{ item }}
                </el-button>
              </el-col>
              <el-col :span="8"></el-col>
            </el-row>
          </el-card>
        </el-aside>
        <el-container>
          <el-main>
            <el-card style="height: 98%;" shadow="always">
              <template #header>
                <span>消息记录</span>
              </template>
              <div class="news" v-for="item in state.recordList" :key="item.time">
                <el-tag :style="newsStyle">{{ item }}</el-tag>
              </div>
            </el-card>
          </el-main>
          <el-footer>Footer</el-footer>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import {computed, reactive, ref, watch} from 'vue';
import {useWebSocket} from '@vueuse/core'
import type {FormInstance, FormRules} from "element-plus";
import {ElMessage} from "element-plus";

const address = ref<string>()
const options = ref([
  {
    label: 'ws://localhost:8088',
    value: 'ws://localhost:8088'
  }
])

interface Command {
  code: number,
  nickname: string,
  target: string,
  content: string,
  type: number
}

const command = reactive<Command>({
  code: NaN,
  nickname: '',
  target: '',
  content: '',
  type: NaN
})

const ruleFormRef = ref<FormInstance>()

interface RuleForm {
  server: string
}

const websocketForm = reactive<RuleForm>({
  server: ''
})

const rules = reactive<FormRules<RuleForm>>({
  server: [
    {
      required: true,
      message: '请选择或填写websocket地址',
      trigger: 'change',
    }
  ]
})

const {data, status, close, open, send, ws} = useWebSocket(address, {
  heartbeat: false,
  autoReconnect: false
});


const getIsOpen = computed(() => status.value === 'OPEN')
const getTagColor = computed(() => (getIsOpen.value ? '#67C23A' : '#F56C6C'))

const toggle = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      console.log('submit!')
      // 'CONNECTING' | 'CLOSED'
      if (status.value === 'OPEN') {
        close()
      } else {
        address.value = websocketForm.server
        open()
      }
    } else {
      console.log('error submit!', fields)
    }
  })
}

const connect = async () => {
  command.code = 10001
  const value = JSON.stringify(command)
  send(value)
}

const newsStyle = ref()
const userList = reactive<string[]>([])

watch(data, () => {
  console.log(data, 'data')
  if (data.value) {
    try {
      const res = JSON.parse(data.value)
      console.log(res)
      if (res.code === 20001) {
        ElMessage({
          type: "success",
          showClose: true,
          message: res.data
        })
      }
      if (res.code === 20002) {
        JSON.parse(res.data).forEach((key: string) => {
          userList.push(key)
        });
          newsStyle.value = {
            float: 'right'
          }
        state.recordList.push(res.data)
      }
    } catch (error) {
      console.log('报错')
    }
  }
})

const state = reactive({
  recordList: [] as {
    msg: string
    data: string
    time: string
    type: number
  }[]
})


const content = reactive({
  code: 10001,
  nickname: "张三"
})

const form = reactive({
  name: ""
})

</script>

<style scoped lang="scss">
.el-container {
  height: 800px;
}

.el-header {
  background-color: #c6e2ff;
  height: auto;
}

.el-aside {
  width: 600px;
  background-color: #d9ecff;
}

.el-main {
  height: 600px;
  background-color: #ecf5ff;
}

.el-footer {
  height: 200px;
  background-color: #c6e2ff;
}

.news {
  .el-tag {
    word-break: break-word;
    height: auto;
    margin-bottom: 8px;
    text-align: left;
    line-height: 2;
    width: 51%;
    float: left;
  }
}
</style>