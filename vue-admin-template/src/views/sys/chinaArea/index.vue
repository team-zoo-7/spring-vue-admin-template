<template>
  <div id="chinaArea">
    <div v-title>중국행정구역</div>
    <el-row :gutter="20">
      <el-col :span="10">
        <div class="grid-content bg-purple">
          <el-tree
            :data="treeData"
            :props="defaultProps"
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>
      <el-col :span="14">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>세부</span>
          </div>
          <div class="text item">
            관리 코드: {{ chinaArea.areaCode }}
          </div>
          <div class="text item">
            수평: {{ chinaArea.level }}
          </div>
          <div class="text item">
            부모관리 코드: {{ chinaArea.parentCode }}
          </div>
          <div class="text item">
            우편 번호: {{ chinaArea.zipCode }}
          </div>
          <div class="text item">
            지역코드: {{ chinaArea.cityCode }}
          </div>
          <div class="text item">
            이름: {{ chinaArea.name }}
          </div>
          <div class="text item">
            약어: {{ chinaArea.shortName }}
          </div>
          <div class="text item">
            조합 이름: {{ chinaArea.mergerName }}
          </div>
          <div class="text item">
            병음: {{ chinaArea.pinyin }}
          </div>
          <div class="text item">
            경도: {{ chinaArea.lng }}
          </div>
          <div class="text item">
            위도: {{ chinaArea.lat }}
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>

import { getChinaAreaTree } from '@/api/chinaArea'

export default {
  name: 'ChinaArea',
  components: {},
  data() {
    return {
      chinaArea: {
        areaCode: 0,
        level: '',
        parentCode: '',
        zipCode: '',
        cityCode: '',
        name: '',
        shortName: '',
        mergerName: '',
        pinyin: '',
        lng: '',
        lat: ''
      },
      defaultProps: {
        children: 'children',
        label: 'title'
      },
      treeData: [],
      loading: false
    }
  },
  created() {
    this.getTableDate()
  },
  methods: {
    getTableDate() {
      const _this = this
      _this.loading = true
      getChinaAreaTree()
        .then(result => {
          _this.treeData = result
          _this.loading = false
        })
    },
    handleNodeClick(data) {
      this.chinaArea = data.source
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
#chinaArea {
  .el-tree-node__expand-icon {
    font-size: 1.5rem;
  }
  .el-tree-node__label {
    font-size: 1rem;
  }
  .el-tree-node__content {
    height: 1.5rem;
  }
  .el-alert__title {
    font-size: 1rem;
  }
  .text {
    font-size: 1rem;
  }

  .item {
    padding: 10px 0;
  }
}
</style>

<style lang='scss' scoped>

</style>
