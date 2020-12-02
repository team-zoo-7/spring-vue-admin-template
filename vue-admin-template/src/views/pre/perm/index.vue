<!--  -->
<template>
  <div v-loading="loading" id="pmn">
    <div v-title>권한관리</div>
    <el-row :gutter="20">
      <el-col :span="10">
        <el-tree :data="treeData" :props="defaultProps" accordion @node-click="handleNodeClick" />
      </el-col>
      <el-col :span="14">
        <el-card class="box-card" shadow="never">
          <div slot="header" class="clearfix">
            <span class="card-header">권한 세부 정보</span>
            <el-button-group style="float: right;">
              <el-button
                v-has="'pre_perm:update'"
                type="warning"
                size="mini"
                icon="el-icon-edit"
                @click=" isEdit = !isEdit "
              >이 권한 편집</el-button>
              <el-button
                v-has="'pre_perm:create'"
                type="primary"
                size="mini"
                icon="el-icon-share"
                @click="addEntity"
              >하위 권한 추가</el-button>
              <el-button
                v-has="'pre_perm:delete'"
                type="danger"
                size="mini"
                icon="el-icon-delete"
                @click="deleteEntity"
              >삭제권한</el-button>
            </el-button-group>
          </div>

          <el-form ref="pmnForm" :rules="rules" :model="pmnForm" label-width="100px">
            <el-form-item label="권한ID">
              <el-input v-model="pmnForm.pid" :disabled="true" />
            </el-form-item>

            <el-form-item label="권한 제목" prop="title">
              <el-input :disabled="isEdit" v-model="pmnForm.title" clearable />
            </el-form-item>

            <template v-if="pmnForm.type === 'menu'">
              <el-form-item label="권한 아이콘" prop="icon">
                <el-input :disabled="isEdit" v-model="pmnForm.icon" clearable />
              </el-form-item>
            </template>

            <el-form-item label="고유식별자" prop="resources">
              <el-input
                :disabled="isEdit"
                v-model="pmnForm.resources"
                clearable
                placeholder="유일한 영문자 여야 함을 강조"
              />
            </el-form-item>

            <el-form-item label="상위권한" prop="parentId">
              <el-cascader
                :disabled="isEdit"
                :show-all-levels="false"
                v-model="pmnForm.parentId"
                :options="menuTreeData"
                :props="cascaderProps"
                @change="handleChange"
              >
                <template slot-scope="{ node, data }">
                  <span>{{ data.title }}</span>
                  <span v-if="!node.isLeaf">({{ data.children.length }})</span>
                </template>
              </el-cascader>
            </el-form-item>

            <el-form-item label="권한 유형" prop="type">
              <el-select v-model="pmnForm.type" :disabled="isEdit" placeholder="유형 선택">
                <el-option
                  v-for="item in pmnType"
                  :key="item.type"
                  :label="item.name"
                  :value="item.type"
                />
              </el-select>
            </el-form-item>

            <el-form-item label="권한기술" prop="description">
              <el-input :disabled="isEdit" v-model="pmnForm.description" clearable />
            </el-form-item>

            <el-form-item v-if="isEdit === false">
              <el-button type="primary" @click="saveAndFlush">저장</el-button>
              <el-button @click=" isEdit = !isEdit ">취소</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {
  getPermissionTree,
  savePermission,
  updatePermission,
  removePermissionById
} from '@/api/sysPermission'
export default {
  components: {},
  data() {
    return {
      pmnForm: {
        pid: 0,
        parentId: '',
        resources: '',
        title: '',
        icon: '',
        type: '',
        description: ''
      },
      loading: false,
      isEdit: true, // 편집 가능
      treeData: [],
      menuTreeData: [],
      pmnType: [
        { type: 'button', name: '버튼' },
        { type: 'menu', name: '메뉴' }
      ],
      defaultProps: {
        children: 'children',
        label: 'title'
      },
      cascaderProps: {
        children: 'children',
        label: 'title',
        value: 'id',
        emitPath: false,
        checkStrictly: true
      },
      // 유효성 검사 규칙
      rules: {
        title: [
          { required: true, message: '권한 제목은 비워 둘 수 없습니다.', trigger: 'blur' }
        ],
        parentId: [
          { required: true, message: '상위 권한은 비워 둘 수 없습니다.', trigger: 'blur' }
        ],
        resources: [
          { required: true, message: 'ID는 전역 적으로 고유해야합니다.', trigger: 'blur' },
          {
            validator: function(rule, value, callback) {
              const regex = /[a-z]{2}$/
              if (!regex.test(value)) {
                callback(new Error('ID는 모두 영문 소문자이고 5 자 이상이어야합니다.'))
              } else {
                callback()
              }
            },
            trigger: 'blur'
          }
        ],
        type: [
          { required: true, message: '권한 유형은 비워 둘 수 없습니다.', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '권한 기술은 비워 둘 수 없습니다.', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getPermissionTreeData()
    this.getMenusTreeData()
  },
  methods: {
    getPermissionTreeData() {
      // 트리 노드로 표시된 권한 열 테이블 가져 오기
      const _this = this
      _this.loading = true
      getPermissionTree({ filter: false })
        .then(result => {
          _this.treeData = result
          _this.loading = false
        })
    },
    getMenusTreeData() {
      // 트리 노드로 표시된 권한 열 테이블 가져 오기
      const _this = this
      _this.loading = true
      getPermissionTree({ filter: true })
        .then(result => {
          _this.menuTreeData = result
          _this.menuTreeData.push({
            id: 'root',
            title: '상단메뉴',
            parentId: 0
          })
          _this.loading = false
        })
    },
    handleChange(data) {
      // 루트 테이블로 표시되면 top 메뉴
      if (data === 'root') {
        this.pmnForm.parentId = 0
      } else {
        this.pmnForm.parentId = data
      }
    },
    handleNodeClick(node) {
      this.pmnForm = node.source
    },
    addEntity() {
      this.pmnForm = {
        pid: 0,
        parentId: 0,
        resources: '',
        title: '',
        icon: '',
        type: '',
        description: ''
      }
      this.isEdit = false
    },
    deleteEntity() {
      const _this = this
      if (_this.pmnForm.pid > 0) {
        _this
          .$confirm(
            '삭제 확인【' +
              _this.pmnForm.title +
              '】이 권한에 하위 권한이없고이 권한을 사용하는 역할이 없는지 확인하세요. 그렇지 않으면 삭제가 불가능합니다. 계속 하시겠습니까?',
            '경고',
            {
              confirmButtonText: '결정',
              cancelButtonText: '취소',
              type: 'warning'
            }
          )
          .then(() => {
            removePermissionById(_this.pmnForm.pid)
              .then(result => {
                _this.$notify({
                  type: 'success',
                  title: '성공',
                  message: '권한 삭제 성공!'
                })
                this.getPermissionTreeData()
                this.getMenusTreeData()
                _this.isEdit = true
              })
          })
      } else {
        _this.$notify.error({
          title: '오류',
          message: '삭제하기 전에 권한을 선택하세요.'
        })
      }
    },
    saveAndFlush() {
      const _this = this
      console.log('this.pmnForm :', this.pmnForm)
      _this.$refs.pmnForm.validate(valid => {
        if (valid) {
          if (_this.pmnForm.pid > 0) {
            updatePermission(_this.pmnForm)
              .then(result => {
                _this.$notify({
                  title: '성공',
                  message: '권한 수정 성공!',
                  type: 'success'
                })
                this.getPermissionTreeData()
                this.getMenusTreeData()
                _this.isEdit = true
              })
          } else {
            savePermission(_this.pmnForm)
              .then(result => {
                _this.$notify({
                  title: '성공',
                  message: '권한 추가 성공!',
                  type: 'success'
                })
                this.getPermissionTreeData()
                this.getMenusTreeData()
                _this.isEdit = true
              })
          }
        }
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang='scss' scoped>
</style>

<style rel="stylesheet/scss" lang="scss">
#pmn {
  .el-tree-node__expand-icon {
    font-size: 1rem;
  }
  .el-tree-node__label {
    font-size: 1rem;
  }
  .el-tree-node__content {
    height: 2.0rem;
  }
  .el-alert__title {
    font-size: 1rem;
  }
  .el-input-number {
    width: 100%;
  }
  .el-select {
    width: 100%;
  }
}
</style>
