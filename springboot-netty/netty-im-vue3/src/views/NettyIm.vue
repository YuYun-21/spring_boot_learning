<template>
  <div class="common-layout">
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
        </el-form>
      </el-header>
      <el-container>
        <el-aside width="500px">
          <div style="margin-top: 10px">
            <span>昵称：</span>
            <el-input style="width: 150px;margin-right: 10px"></el-input>
            <el-button type="primary" @click="toggle">上线</el-button>
          </div>

        </el-aside>
        <el-container>
          <el-main>
            <div>

            </div>
          </el-main>
          <el-footer>Footer</el-footer>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import {computed, reactive, ref} from 'vue';
import {useWebSocket} from '@vueuse/core'
import type {FormInstance, FormRules} from "element-plus";

// const wsClient = ref(new WebSocket("ws://localhost:8088"))

const server = ref<string>()
const address = ref<string>()
const options = ref([
  {
    label: 'ws://localhost:8088',
    value: 'ws://localhost:8088'
  }
])

const command = {
  code: 11,
  nickname: '大声道',
  target: '',
  content: '',
  type: 1
}

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
  height: auto;
}

.el-header {
  background-color: #c6e2ff;
  height: auto;
}

.el-aside {
  background-color: #d9ecff;
}

.el-main {
  height: 300px;
  background-color: #ecf5ff;
}

.el-footer {
  background-color: #c6e2ff;
}
</style>